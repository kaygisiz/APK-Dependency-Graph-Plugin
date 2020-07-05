/**
 * Copyright (C) 2017 Necati Caner Gaygisiz
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import managers.FileChooserDialogManager;
import managers.PropertiesManager;
import org.apache.commons.lang3.StringUtils;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import utils.*;
import utils.Writer;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GenerateDependencyInjectionGraph extends AnAction {
    private String apkPath;

    private PathHelper pathHelper;

    private String packageName;

    private String isInnerClassEnabled;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        if (project != null) {
            try {
                initFiles(project);
            } catch (IOException e) {
                e.printStackTrace();
                Messages.showInfoMessage(e.getMessage(), Strings.TITLE_ERROR_OPEN_BROWSER);
            }

            if (StringUtils.isEmpty(apkPath)) {
                chooseAndSaveApkFile(project);
            }

            packageName = PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME);

            if (StringUtils.isEmpty(packageName)) {
                setPackageName(project);
            }

            isInnerClassEnabled = PropertiesManager.getData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE);

            if (!StringUtils.isEmpty(apkPath) && !StringUtils.isEmpty(packageName)) {
                Task task = generateDependencyInjectionGraph(project);
                task.queue();
            }
        }
    }

    private void initFiles(Project project) throws IOException {
        pathHelper = new PathHelper(project);
        apkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

        File webDir = new File(pathHelper.getWebDir());
        if (!webDir.exists()) {
            FileUtil.copyDir(
                    new File(pathHelper.replaceCharWithSpace(getClass().getResource("/web").getPath())),
                    webDir);
        }
    }

    private List<String> filterByExtension(final String zipFilePath, final String extension) throws IOException {
        List<String> files = new ArrayList<>();
        ZipFile zipFile = new ZipFile(zipFilePath);
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = zipEntries.nextElement();
            if (!zipEntry.isDirectory()) {
                String fileName = zipEntry.getName();
                if (fileName.endsWith(extension)) {
                    files.add(fileName);
                }
            }
        }
        return files;
    }

    private Task.Backgroundable generateDependencyInjectionGraph(Project project) {
        return new Task.Backgroundable(project, Strings.BACKGROUNDABLE_PROGRESS_TITLE, true) {
            @Override
            public void run(ProgressIndicator progressIndicator) {
                try {
                    //TODO is it important enough to be selectable?
                    int apiVersion = 28;
                    for (String dexFileName : filterByExtension(apkPath, ".dex")) {
                        DexBackedDexFile dexFile = DexFileFactory.loadDexEntry(new File(apkPath), dexFileName, true, Opcodes.forApi(apiVersion)).getDexFile();
                        CustomBaksmali.disassembleDexFile(dexFile, new File(pathHelper.getDecompiledDir()), packageName);
                    }
                    SmaliAnalyzer smaliAnalyzer = new SmaliAnalyzer(project, Boolean.getBoolean(isInnerClassEnabled), packageName);
                    if (smaliAnalyzer.run()) {
                        File resultFile = new File(pathHelper.getAnalyzedJsFile());
                        Writer.write(resultFile, smaliAnalyzer.getDependencies());
                    } else {
                        progressIndicator.setText(Strings.CANCELING_BY_ANALYZER);
                        Messages.showInfoMessage(Strings.ERROR_CANCELED_ANALYZING, Strings.TITLE_ERROR_OPEN_BROWSER);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Messages.showInfoMessage(e.getMessage(), Strings.TITLE_DEFAULT_ERROR);
                }
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                try {
                    FileUtil.asyncDelete(new File(pathHelper.getDecompiledDir()));
                    Desktop.getDesktop().browse(new File(pathHelper.getIndexHtmlFile()).toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                    Messages.showInfoMessage(Strings.ERROR_OPEN_BROWSER, Strings.TITLE_ERROR_OPEN_BROWSER);
                }
            }
        };
    }

    private void chooseAndSaveApkFile(Project project) {
        VirtualFile apkFile = new FileChooserDialogManager.Builder(project)
                .setFileTypes(FileTypes.FILE)
                .setTitle(Strings.TITLE_ASK_APK_FILE)
                .setDescription(Strings.MESSAGE_ASK_APK_FILE)
                .withFileFilter("apk")
                .create()
                .getSelectedFile();

        if (apkFile != null) {
            apkPath = apkFile.getPath();
            PropertiesManager.putData(project, PropertyKeys.APK_PATH, apkPath);
        }
    }

    private void setPackageName(Project project) {
        String packageName = Messages.showInputDialog(
                Strings.MESSAGE_ASK_PACKAGE_NAME_TO_FILTER,
                Strings.TITLE_ASK_PACKAGE_NAME_TO_FILTER,
                Messages.getQuestionIcon(),
                PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME),
                new NonEmptyInputValidator());

        if (!StringUtils.isEmpty(packageName)) {
            this.packageName = packageName;
            PropertiesManager.putData(project, PropertyKeys.PACKAGE_NAME, packageName);
        }
    }

}

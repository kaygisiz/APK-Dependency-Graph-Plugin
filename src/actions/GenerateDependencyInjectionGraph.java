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

import brut.apktool.Main;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import graph.GraphMain;
import managers.FileChooserDialogManager;
import managers.PropertiesManager;
import org.apache.http.util.TextUtils;
import brut.common.BrutException;
import org.jetbrains.annotations.NotNull;
import utils.FileTypes;
import utils.PropertyKeys;
import utils.Strings;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GenerateDependencyInjectionGraph extends AnAction {
    private String apkPath;

    private String digPath;

    private String decompiledFilesPath;

    private String webPath;

    private String analyzedJsPath;

    private String htmlPath;

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
            }

            if (TextUtils.isEmpty(apkPath)) {
                chooseAndSaveApkFile(project);
            }

            packageName = PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME);

            if (TextUtils.isEmpty(packageName)) {
                setPackageName(project);
            }

            isInnerClassEnabled = PropertiesManager.getData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE);

            if (!TextUtils.isEmpty(apkPath) && !TextUtils.isEmpty(packageName)) {
                Task task = generateDependencyInjectionGraph(project);
                task.queue();
            }
        }
    }

    private void initFiles(Project project) throws IOException {
        digPath = project.getBasePath() + "/.dig";

        decompiledFilesPath = digPath + "/decompiled";

        webPath = digPath + "/web";

        analyzedJsPath = webPath + "/analyzed.js";

        htmlPath = webPath + "/index.html";

        apkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

        File webDir = new File(webPath);
        File webResourceDir = new File(getClass().getResource("/web").getPath().replace("%20", " "));
        if (!webDir.exists()) {
            FileUtil.copyDir(webResourceDir, webDir);
        }
    }

    private Task.Backgroundable generateDependencyInjectionGraph(Project project) {
        return new Task.Backgroundable(project, Strings.BACKGROUNDABLE_PROGRESS_TITLE, true) {
            @Override
            public void run(@NotNull ProgressIndicator progIndicator) {
                try {
                    Main.main(new String[]{"d", apkPath, "-o", decompiledFilesPath, "-f"});
                    GraphMain.main(new String[]{"-i", decompiledFilesPath, "-o", analyzedJsPath, "-f", packageName, "-d", isInnerClassEnabled});
                } catch (IOException | BrutException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                try {
                    Desktop.getDesktop().browse(new File(htmlPath).toURI());
                    FileUtil.asyncDelete(new File(decompiledFilesPath));
                } catch (IOException e) {
                    e.printStackTrace();
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
                PropertiesComponent.getInstance(project).getValue(PropertyKeys.PACKAGE_NAME, ""),
                new NonEmptyInputValidator());

        if (!TextUtils.isEmpty(packageName)) {
            this.packageName = packageName;
            PropertiesComponent.getInstance(project).setValue(PropertyKeys.PACKAGE_NAME, packageName);
        }
    }

}

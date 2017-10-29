/**
 *  Copyright (C) 2017 Necati Caner Gaygisiz
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package actions;

import brut.apktool.Main;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DisplayGraphAction extends AnAction {
    private String apkPath;

    private String decompiledFilesPath;

    private String analyzedJsPath;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        decompiledFilesPath = project.getBasePath() + "/OUTPUT";

        analyzedJsPath = getClass().getResource("/web").getPath().replace("%20", " ") + "/analyzed.js";

        apkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

        if (TextUtils.isEmpty(apkPath)) {
            chooseAndSaveApkFile(project);
        }

        String packageName = PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME, Strings.NO_FILTER);

        String isInnerClassEnabled = PropertiesManager.getData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE);

        if (!TextUtils.isEmpty(apkPath)) {
            String[] apktoolArgs = new String[]{"d", apkPath, "-o", decompiledFilesPath, "-f"};
            String[] graphArgs = new String[]{"-i", decompiledFilesPath, "-o", analyzedJsPath, "-f", packageName, "-d", isInnerClassEnabled};
            Task task = new Task.Backgroundable(project, Strings.BACKGROUNDABLE_PROGRESS_TITLE, true) {
                @Override
                public void run(@NotNull ProgressIndicator progIndicator) {
                    try {
                        Main.main(apktoolArgs);
                        GraphMain.main(graphArgs);
                    } catch (IOException | BrutException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                    URL url = getClass().getResource("/web/index.html");
                    try {
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
            task.queue();
        }
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
}

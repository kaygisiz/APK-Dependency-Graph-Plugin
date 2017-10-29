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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import managers.FileChooserDialogManager;
import managers.PropertiesManager;
import org.apache.http.util.TextUtils;
import utils.PropertyKeys;
import utils.Strings;

import java.util.Objects;

public class SetApkPathAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            String currentApkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

            VirtualFile fileToSelectOnCreate =
                    TextUtils.isEmpty(currentApkPath)
                            ? project.getBaseDir()
                            : LocalFileSystem.getInstance().findFileByPath(currentApkPath);

            VirtualFile apkFile = new FileChooserDialogManager.Builder(project, fileToSelectOnCreate)
                    .setTitle(Strings.TITLE_ASK_APK_FILE)
                    .setDescription(Strings.MESSAGE_ASK_APK_FILE)
                    .withFileFilter("apk")
                    .create()
                    .getSelectedFile();

            if (apkFile != null) {
                PropertiesManager.putData(project, PropertyKeys.APK_PATH, apkFile.getPath());
            }
        }
    }
}

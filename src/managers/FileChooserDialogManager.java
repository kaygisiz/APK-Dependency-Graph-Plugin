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
package managers;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;
import utils.FileTypes;

import java.util.Objects;

public class FileChooserDialogManager {

    private FileChooserDescriptor fileChooserDescriptor;

    private VirtualFile selectedFile;

    private boolean isFile = false, isFolder = false, isJar = false, isJarAsFile = false, isJarContent = false, isMultiple = false;

    private FileChooserDialogManager(Project project, VirtualFile fileToSelectOnCreate, String title, String description, String filterExtension, @FileTypes int... fileTypes) {
        if (fileTypes.length > 1) {
            isMultiple = true;
            for (int type : fileTypes) {
                fileTypesSwitchStatement(type);
            }
        } else {
            fileTypesSwitchStatement(fileTypes[0]);
        }

        initFileChooserDescriptor(title, description, filterExtension);
        selectedFile = createFileChooserDialog(project, fileToSelectOnCreate);
    }

    private void initFileChooserDescriptor(String title, String description, String filterExtension) {
        fileChooserDescriptor = new FileChooserDescriptor(isFile, isFolder, isJar, isJarAsFile, isJarContent, isMultiple);
        fileChooserDescriptor.setTitle(title);
        fileChooserDescriptor.setDescription(description);
        if (!TextUtils.isEmpty(filterExtension)) {
            fileChooserDescriptor.withFileFilter(virtualFile -> Objects.equals(virtualFile.getExtension(), "apk"));
        }
    }

    public FileChooserDescriptor getFileChooserDescriptor() {
        return fileChooserDescriptor;
    }

    public VirtualFile createFileChooserDialog(Project project) {
        return createFileChooserDialog(project, null);
    }

    private VirtualFile createFileChooserDialog(Project project, VirtualFile fileToSelectDefault) {
        return fileToSelectDefault == null
                ? FileChooser.chooseFile(fileChooserDescriptor, project, project.getBaseDir())
                : FileChooser.chooseFile(fileChooserDescriptor, project, fileToSelectDefault);
    }

    public VirtualFile getSelectedFile() {
        return selectedFile;
    }

    private void fileTypesSwitchStatement(@FileTypes int fileType) {
        switch (fileType) {
            case FileTypes.FILE:
                isFile = true;
                break;
            case FileTypes.FOLDER:
                isFolder = true;
                break;
            case FileTypes.JAR:
                isJar = true;
                break;
            case FileTypes.JAR_AS_FILE:
                isJarAsFile = true;
                break;
            case FileTypes.JAR_CONTENT:
                isJarContent = true;
                break;
        }
    }

    public static class Builder {
        // Required parameters
        private Project project;

        // Optional parameters
        private VirtualFile fileToSelectOnCreate;
        private String title;
        private String description;
        private String filterExtension;

        private int[] fileTypes;

        public Builder(Project project, VirtualFile fileToSelectOnCreate) {
            this.project = project;
            this.fileToSelectOnCreate = fileToSelectOnCreate;
        }

        public Builder(Project project) {
            this.project = project;
        }

        public Builder setFileTypes(@FileTypes int... fileTypes) {
            this.fileTypes = fileTypes;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withFileFilter(String filterExtension) {
            this.filterExtension = filterExtension;
            return this;
        }

        public FileChooserDialogManager create() {
            return new FileChooserDialogManager(project, fileToSelectOnCreate, title, description, filterExtension, fileTypes);
        }
    }
}

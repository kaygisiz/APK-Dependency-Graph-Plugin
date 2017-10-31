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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import managers.PropertiesManager;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;
import utils.PropertyKeys;
import utils.Strings;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ShowGeneratedDependencies extends AnAction {
    private String webPath;

    private String analyzedJsPath;

    private String htmlPath;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {

            initFiles(project);

            if (new File(analyzedJsPath).exists()) {
                openBrowser();
            } else {
                Messages.showInfoMessage(Strings.ERROR_SHOW_GENERATED_DEPENDENCIES, Strings.TITLE_ERROR_SHOW_GENERATED_DEPENDENCIES);
            }

        }
    }

    private void initFiles(Project project) {
        webPath = project.getBasePath() + "/.dig/web";

        analyzedJsPath = webPath + "/analyzed.js";

        htmlPath = webPath + "/index.html";
    }

    private void openBrowser() {
        try {
            Desktop.getDesktop().browse(new File(htmlPath).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

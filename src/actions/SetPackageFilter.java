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

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import utils.PropertyKeys;
import org.apache.http.util.TextUtils;
import utils.Strings;

public class SetPackageFilter extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        if (project != null) {

            String packageName = Messages.showInputDialog(
                    Strings.MESSAGE_ASK_PACKAGE_NAME_TO_FILTER,
                    Strings.TITLE_ASK_PACKAGE_NAME_TO_FILTER,
                    Messages.getQuestionIcon(),
                    PropertiesComponent.getInstance(project).getValue(PropertyKeys.PACKAGE_NAME, Strings.NO_FILTER),
                    new NonEmptyInputValidator());

            if (!TextUtils.isEmpty(packageName)) {
                PropertiesComponent.getInstance(project).setValue(PropertyKeys.PACKAGE_NAME, packageName);
            }
        }
    }
}
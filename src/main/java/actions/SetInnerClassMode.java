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

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.Project;
import managers.PropertiesManager;
import utils.PropertyKeys;
import utils.Strings;

public class SetInnerClassMode extends ToggleAction {
    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        return anActionEvent.getProject() != null
                && PropertiesManager.getData(anActionEvent.getProject(), PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE).equals(Strings.TRUE);
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            PropertiesManager.putData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, b ? Strings.TRUE : Strings.FALSE);
        }
    }
}

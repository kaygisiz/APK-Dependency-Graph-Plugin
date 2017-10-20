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

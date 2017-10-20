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
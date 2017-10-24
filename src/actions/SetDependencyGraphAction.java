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
import utils.FileTypes;
import utils.PropertyKeys;
import utils.Strings;

public class SetDependencyGraphAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            String currentGraphLibPath = PropertiesManager.getData(PropertyKeys.GRAPH_LIBRARY_PATH);

            VirtualFile fileToSelectOnCreate =
                    TextUtils.isEmpty(currentGraphLibPath)
                            ? project.getBaseDir()
                            : LocalFileSystem.getInstance().findFileByPath(currentGraphLibPath);

            VirtualFile graphLibFolder = new FileChooserDialogManager.Builder(project, fileToSelectOnCreate)
                    .setFileTypes(FileTypes.FOLDER)
                    .setTitle(Strings.TITLE_ASK_GRAPH_LIBRARY_PATH)
                    .setDescription(Strings.MESSAGE_ASK_GRAPH_LIBRARY_PATH)
                    .create()
                    .getSelectedFile();

            if (graphLibFolder != null) {
                PropertiesManager.putData(PropertyKeys.GRAPH_LIBRARY_PATH, graphLibFolder.getPath());
            }
        }
    }
}

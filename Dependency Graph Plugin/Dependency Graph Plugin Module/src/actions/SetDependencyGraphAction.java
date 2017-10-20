package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import managers.PropertiesManager;
import org.apache.http.util.TextUtils;
import utils.PropertyKeys;
import utils.Strings;

public class SetDependencyGraphAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            FileChooserDescriptor folderChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
            folderChooserDescriptor.setDescription(Strings.MESSAGE_ASK_GRAPH_LIBRARY_PATH);
            folderChooserDescriptor.setTitle(Strings.TITLE_ASK_GRAPH_LIBRARY_PATH);

            String currentGraphLibPath = PropertiesManager.getData(PropertyKeys.GRAPH_LIBRARY_PATH);

            VirtualFile graphLibFolder = FileChooser.chooseFile(
                    folderChooserDescriptor,
                    project,
                    TextUtils.isEmpty(currentGraphLibPath)
                            ? project.getBaseDir()
                            : LocalFileSystem.getInstance().findFileByPath(currentGraphLibPath)
            );

            if (graphLibFolder != null) {
                PropertiesManager.putData(PropertyKeys.GRAPH_LIBRARY_PATH, graphLibFolder.getPath());
            }
        }
    }
}

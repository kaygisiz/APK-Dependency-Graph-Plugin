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

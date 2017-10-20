package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import managers.PropertiesManager;
import org.jetbrains.annotations.NotNull;
import utils.PropertyKeys;
import org.apache.http.util.TextUtils;
import utils.Strings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class DisplayGraphAction extends AnAction {

    private String graphLibPath;

    private String apkPath;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
        Project project = anActionEvent.getProject();

        if (project != null) {

            graphLibPath = PropertiesManager.getData(PropertyKeys.GRAPH_LIBRARY_PATH);

            if (TextUtils.isEmpty(graphLibPath)) {
                chooseAndSaveGraphDir(project);
            }

            apkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

            if (TextUtils.isEmpty(apkPath) && !TextUtils.isEmpty(graphLibPath)) {
                chooseAndSaveApkFile(project);
            }

            String packageName = PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME, Strings.NO_FILTER);

            String isInnerClassEnabled = PropertiesManager.getData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE);

            if (!TextUtils.isEmpty(apkPath) && !TextUtils.isEmpty(graphLibPath)) {
                runCommandOnTask(project, "run.bat \"" + apkPath + "\" " + packageName + " " + isInnerClassEnabled);
            }
        }
    }

    private void chooseAndSaveGraphDir(Project project) {
        FileChooserDescriptor folderChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        folderChooserDescriptor.setDescription(Strings.MESSAGE_ASK_GRAPH_LIBRARY_PATH);
        folderChooserDescriptor.setTitle(Strings.TITLE_ASK_GRAPH_LIBRARY_PATH);

        VirtualFile graphLibFolder = FileChooser.chooseFile(folderChooserDescriptor, project, project.getBaseDir());

        if (graphLibFolder != null) {
            graphLibPath = graphLibFolder.getPath();
            PropertiesManager.putData(PropertyKeys.GRAPH_LIBRARY_PATH, graphLibPath);
        }
    }

    private void chooseAndSaveApkFile(Project project) {
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);

        fileChooserDescriptor.setDescription(Strings.MESSAGE_ASK_APK_FILE);
        fileChooserDescriptor.setTitle(Strings.TITLE_ASK_APK_FILE);
        fileChooserDescriptor.withFileFilter(virtualFile -> Objects.equals(virtualFile.getExtension(), "apk"));

        VirtualFile apkFile = FileChooser.chooseFile(fileChooserDescriptor, project, project.getBaseDir());

        if (apkFile != null) {
            apkPath = apkFile.getPath();
            PropertiesManager.putData(project, PropertyKeys.APK_PATH, apkPath);
        }
    }

    private void runCommandOnTask(Project project, String command) {
        ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
        if (progressIndicator != null) {
            progressIndicator.start();
        }
        Task task = new Task.Backgroundable(project, "Generating Dependency Graph", true) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    ProcessBuilder builder = new ProcessBuilder("cmd", "/c", command);
                    System.out.println("Command: " + command);
                    File builderDir = new File(graphLibPath);
                    builder.directory(builderDir);
                    builder.redirectErrorStream(true);
                    builder.start().waitFor();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } finally {
                    progressIndicator.stop();
                }
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                try {
                    File outputFile = new File(graphLibPath + "\\gui\\index.html");
                    Desktop.getDesktop().browse(outputFile.toURI());
                    if (progressIndicator != null) {
                        progressIndicator.stop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (progressIndicator != null && progressIndicator.isRunning()) {
                    progressIndicator.stop();
                }
            }
        };
        task.queue();
    }

    private void printCommandOutput(Process process) throws IOException {
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("\nHere is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }
}

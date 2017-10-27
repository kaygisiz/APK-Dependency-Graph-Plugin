package actions;

import com.google.wireless.android.sdk.stats.GradleAndroidModule;
import com.google.wireless.android.sdk.stats.GradleBuildDetails;
import com.google.wireless.android.sdk.stats.GradleBuildVariant;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import graph.GraphMain;
import managers.PropertiesManager;
import org.apache.http.util.TextUtils;
import org.brut.androlib.AndrolibException;
import org.brut.androlib.ApkDecoder;
import org.brut.apktool.ApktoolMain;
import org.brut.common.BrutException;
import org.jetbrains.annotations.NotNull;
import utils.PropertyKeys;
import utils.Strings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class DisplayGraphAction extends AnAction {

    private final ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();

    private String apkPath;

    private String decompiledFilesPath;

    private String analyzedJsPath;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        decompiledFilesPath = project.getBasePath() + "/OUTPUT";

        analyzedJsPath = getClass().getResource("/web").getPath().replace("%20", " ") + "/analyzed.js";

        apkPath = PropertiesManager.getData(project, PropertyKeys.APK_PATH);

        if (TextUtils.isEmpty(apkPath)) {
            chooseAndSaveApkFile(project);
        }

        String packageName = PropertiesManager.getData(project, PropertyKeys.PACKAGE_NAME, Strings.NO_FILTER);

        String isInnerClassEnabled = PropertiesManager.getData(project, PropertyKeys.IS_INNER_CLASS_ENABLE, Strings.TRUE);
        if (!TextUtils.isEmpty(apkPath)) {
            String[] apktoolArgs = new String[]{"d", apkPath, "-o", decompiledFilesPath, "-f"};
            String[] graphArgs = new String[]{"-i", decompiledFilesPath, "-o", analyzedJsPath, "-f", packageName, "-d", isInnerClassEnabled};
            Task task = new Task.Backgroundable(project, Strings.BACKGROUNDABLE_PROGRESS_TITLE, true) {
                @Override
                public void run(@NotNull ProgressIndicator progIndicator) {
                    try {
                        ApktoolMain.main(apktoolArgs);
                        GraphMain.main(graphArgs);
                    } catch (IOException | BrutException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                    URL url = getClass().getResource("/web/index.html");
                    try {
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                }
            };
            task.queue();
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
}

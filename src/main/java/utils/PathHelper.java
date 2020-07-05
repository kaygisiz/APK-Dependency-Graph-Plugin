package utils;

import com.intellij.openapi.project.Project;

public class PathHelper {

    private final String digPath;

    private final String decompiledFilesPath;

    private final String webPath;

    private final String analyzedJsPath;

    private final String htmlPath;

    public PathHelper(Project project) {
        digPath = replaceCharWithSpace(project.getBasePath()) + "/.dig";
        decompiledFilesPath = digPath + "/decompiled";
        webPath = digPath + "/web";
        analyzedJsPath = webPath + "/analyzed.js";
        htmlPath = webPath + "/index.html";
    }

    public String getDig() {
        return digPath;
    }

    public String getDecompiledDir() {
        return decompiledFilesPath;
    }

    public String getWebDir() {
        return webPath;
    }

    public String getAnalyzedJsFile() {
        return analyzedJsPath;
    }

    public String getIndexHtmlFile() {
        return htmlPath;
    }

    public String replaceCharWithSpace(String path) {
        return path.replace("%20", " ");
    }

}

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intellij.openapi.project.Project;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SmaliAnalyzer {

    private Project project;
    private boolean isProcessingInner;
    private String packageName;

    public SmaliAnalyzer(Project project, boolean isProcessingInner, String packageName) {
        this.project = project;
        this.isProcessingInner = isProcessingInner;
        this.packageName = packageName.replaceAll("\\.", "/");
    }

    private Map<String, Set<String>> dependencies = new HashMap<>();

    public Map<String, Set<String>> getDependencies() {
        if (isProcessingInner) {
            return dependencies;
        }
        return getFilteredDependencies();
    }

    public boolean run() {
        System.out.println("Analyzing dependencies...");

        File projectDir = new File(project.getBasePath());
        if (projectDir.exists()) {
            if (isInstantRunEnabled(project.getBasePath())) {
                System.err.println("Enabled Instant Run feature detected. " +
                        "We cannot decompile it. Please, disable Instant Run and rebuild your app.");
            } else {
                traverseSmaliCodeDir(projectDir);
                return true;
            }
        } else {
            System.err.println(projectDir + " does not exist!");
        }
        return false;
    }

    private void traverseSmaliCodeDir(File dir) {
        File[] listOfFiles = dir.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File currentFile = listOfFiles[i];
            if (isSmaliFile(currentFile)) {
                //isSmaliFile(currentFile)
                processSmaliFile(currentFile);
            } else if (currentFile.isDirectory()) {
                traverseSmaliCodeDir(currentFile);
            }
        }
    }

    private void processSmaliFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

            if (isClassAnonymous(fileName)) {
                fileName = getAnonymousNearestOuter(fileName);
            }

            /*
            if (!isClassFilterOk(fileName)) {
				return;
			}
             */

            Set<String> classNames = new HashSet<>();
            Set<String> dependencyNames = new HashSet<>();

            for (String line; (line = br.readLine()) != null; ) {
                try {
                    classNames.clear();

                    parseAndAddClassNames(classNames, line);

                    // filtering
                    for (String fullClassName : classNames) {
                        if (fullClassName != null && fullClassName.startsWith(packageName)) {
                            String simpleClassName = getClassSimpleName(fullClassName);
                            if (isClassOk(simpleClassName, fileName)) { // && isClassFilterOk(simpleClassName)
                                dependencyNames.add(simpleClassName);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error '" + e.getMessage() + "' occured.");
                }
            }

            // inner/nested class always depends on the outer class
            if (isClassInner(fileName)) {
                dependencyNames.add(getOuterClass(fileName));
            }

            if (!dependencyNames.isEmpty()) {
                addDependencies(fileName, dependencyNames);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot found " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Cannot read " + file.getAbsolutePath());
        }
    }

    /**
     * The last filter. Do not show anonymous classes (their dependencies belongs to outer class),
     * generated classes, avoid circular dependencies
     *
     * @param simpleClassName class name to inspect
     * @param fileName        full class name
     * @return true if class is good with these conditions
     */
    private boolean isClassOk(String simpleClassName, String fileName) {
        return !isClassAnonymous(simpleClassName) && !isClassGenerated(simpleClassName)
                && !fileName.equals(simpleClassName);
    }

    private void parseAndAddClassNames(Set<String> classNames, String line) {
        int index = line.indexOf("L");
        while (index != -1) {
            int colonIndex = line.indexOf(";", index);
            if (colonIndex == -1) {
                break;
            }

            String className = line.substring(index + 1, colonIndex);
            if (className.matches("[\\w\\d/$<>]*")) {
                int startGenericIndex = className.indexOf("<");
                if (startGenericIndex != -1 && className.charAt(startGenericIndex + 1) == 'L') {
                    // generic
                    int startGenericInLineIndex = index + startGenericIndex + 1; // index of "<" in the original string
                    int endGenericInLineIndex = getEndGenericIndex(line, startGenericInLineIndex);
                    String generic = line.substring(startGenericInLineIndex + 1, endGenericInLineIndex);
                    parseAndAddClassNames(classNames, generic);
                    index = line.indexOf("L", endGenericInLineIndex);
                    className = className.substring(0, startGenericIndex);
                } else {
                    index = line.indexOf("L", colonIndex);
                }
            } else {
                index = line.indexOf("L", index + 1);
                continue;
            }

            classNames.add(className);
        }
    }

    private void addDependencies(String className, Set<String> dependenciesList) {
        Set<String> depList = dependencies.get(className);
        if (depList == null) {
            // add this class and its dependencies
            dependencies.put(className, dependenciesList);
        } else {
            // if this class is already added - update its dependencies
            depList.addAll(dependenciesList);
        }
    }

    private Map<String, Set<String>> getFilteredDependencies() {
        Map<String, Set<String>> filteredDependencies = new HashMap<>();
        for (String key : dependencies.keySet()) {
            if (!key.contains("$")) {
                Set<String> dependencySet = new HashSet<>();
                for (String dependency : dependencies.get(key)) {
                    if (!dependency.contains("$")) {
                        dependencySet.add(dependency);
                    }
                }
                if (dependencySet.size() > 0) {
                    filteredDependencies.put(key, dependencySet);
                }
            }
        }
        return filteredDependencies;
    }

    private boolean isClassGenerated(String className) {
        return className != null && className.contains("$$");
    }

    private boolean isClassInner(String className) {
        return className != null && className.contains("$") && !isClassAnonymous(className) && !isClassGenerated(className);
    }

    private String getOuterClass(String className) {
        return className.substring(0, className.lastIndexOf("$"));
    }

    private boolean isClassAnonymous(String className) {
        return className != null && className.contains("$")
                && isNumeric(className.substring(className.lastIndexOf("$") + 1, className.length()));
    }

    private String getAnonymousNearestOuter(String className) {
        String[] classes = className.split("\\$");
        for (int i = 0; i < classes.length; i++) {
            if (isNumeric(classes[i])) {
                String anonHolder = "";
                for (int j = 0; j < i; j++) {
                    anonHolder += classes[j] + (j == i - 1 ? "" : "$");
                }
                return anonHolder;
            }
        }
        return null;
    }

    private int getEndGenericIndex(String line, int startGenericIndex) {
        int endIndex = line.indexOf(">", startGenericIndex);
        for (int i = endIndex + 2; i < line.length(); i += 2) {
            if (line.charAt(i) == '>') {
                endIndex = i;
            }
        }
        return endIndex;
    }

    private String getClassSimpleName(String fullClassName) {
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf("/") + 1,
                fullClassName.length());
        int startGenericIndex = simpleClassName.indexOf("<");
        if (startGenericIndex != -1) {
            simpleClassName = simpleClassName.substring(0, startGenericIndex);
        }
        return simpleClassName;
    }

    private boolean isInstantRunEnabled(String projectPath) {
        File unknownDir = new File(projectPath, "unknown");
        if (unknownDir.exists() && unknownDir.isDirectory()) {
            for (File file : unknownDir.listFiles()) {
                if (file.getName().equals("instant-run.zip")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSmaliFile(File file) {
        return file.isFile() && file.getName().endsWith(".smali");
    }
}
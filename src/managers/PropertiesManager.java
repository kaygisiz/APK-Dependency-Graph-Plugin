package managers;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

public class PropertiesManager {

    public static String getData(Project project, String key, String defaultValue) {
        return loadFromPropertiesComponent(project, key, defaultValue);
    }

    public static String getData(Project project, String key) {
        return loadFromPropertiesComponent(project, key, "");
    }

    public static String getData(String key, String defaultValue) {
        return loadFromPropertiesComponent(null, key, defaultValue);
    }

    public static String getData(String key) {
        return loadFromPropertiesComponent(null, key, "");
    }

    public static void putData(Project project, String key, String value) {
        saveToPropertiesComponent(project, key, value);
    }

    public static void putData(String key, String value) {
        saveToPropertiesComponent(null, key, value);
    }

    private static String loadFromPropertiesComponent(Project project, String key, String defaultValue) {
        PropertiesComponent propertiesComponent = project == null ? PropertiesComponent.getInstance() : PropertiesComponent.getInstance(project);

        return propertiesComponent.getValue(key, defaultValue);
    }

    private static void saveToPropertiesComponent(Project project, String key, String value) {
        PropertiesComponent propertiesComponent = project == null ? PropertiesComponent.getInstance() : PropertiesComponent.getInstance(project);

        propertiesComponent.setValue(key, value);
    }
}

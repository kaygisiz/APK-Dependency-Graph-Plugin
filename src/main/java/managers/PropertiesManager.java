/**
 * Copyright (C) 2017 Necati Caner Gaygisiz
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

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
package utils;

public class Strings {
    public final static String TITLE_DEFAULT_ERROR = "APK Dependency Graph Error";
    public final static String MESSAGE_ASK_APK_FILE = "Select apk file of current project: ";
    public final static String TITLE_ASK_APK_FILE = "Select APK File";

    public final static String MESSAGE_ASK_PACKAGE_NAME_TO_FILTER = "Enter a package name to filter dependencies: ";
    public final static String TITLE_ASK_PACKAGE_NAME_TO_FILTER = "Package Filter";

    public final static String TRUE = "true";
    public final static String FALSE = "false";

    public final static String BACKGROUNDABLE_PROGRESS_TITLE = "Generating APK Dependency Graph";

    public final static String TITLE_ERROR_SHOW_GENERATED_DEPENDENCIES = "Generated Dependencies Not Found";
    public final static String ERROR_SHOW_GENERATED_DEPENDENCIES = "You need to generate apk dependency graph first.";

    public final static String TITLE_ERROR_INSTANT_RUN_ENABLED = "Instant Run Error";
    public final static String ERROR_INSTANT_RUN_ENABLED = "Please disable Instant Run feature from settings and build apk again.";

    public final static String TITLE_ERROR_OPEN_BROWSER = "Browser Error";
    public final static String ERROR_OPEN_BROWSER = "Error occurred while open graph on browser.";

    public final static String TITLE_CANCELED_ANALYZING = "Analyzing Error";
    public final static String ERROR_CANCELED_ANALYZING = "Error occurred while analyzing dependencies.";

    public final static String CANCELING_BY_ANALYZER = "Canceling by analyzer...";
}

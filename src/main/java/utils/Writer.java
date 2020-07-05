package utils;

import com.intellij.openapi.ui.Messages;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class Writer {
    public static void write(File resultFile, Map<String, Set<String>> dependencies) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(resultFile))) {
            br.write("var dependencies = {links:[\n");
            for (Map.Entry<String, Set<String>> entry : dependencies.entrySet()) {
                for (String dep : entry.getValue()) {
                    br.write("{\"source\":\"" + entry.getKey() + "\",\"dest\":\"" + dep + "\"},\n");
                }
            }
            br.write("]};");
        } catch (IOException e) {
            e.printStackTrace();
            Messages.showInfoMessage(e.getMessage(), Strings.TITLE_DEFAULT_ERROR);
        }
    }
}

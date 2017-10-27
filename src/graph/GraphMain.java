package graph;


import java.io.File;

import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.source.xml.TagNameVariantCollector;
import graph.io.ArgumentReader;
import graph.io.Arguments;
import graph.io.Writer;

public class GraphMain {

    public static void main(String[] args) {
        Arguments arguments = new ArgumentReader(args).read();
        if (arguments == null) {
            return;
        }

        File resultFile = new File(arguments.getResultPath());
        SmaliAnalyzer analyzer = new SmaliAnalyzer(arguments);
        if (analyzer.run()) {
            new Writer(resultFile).write(analyzer.getDependencies());
            System.out.println("Success! Now open index.html in your browser.");
        }
    }
}
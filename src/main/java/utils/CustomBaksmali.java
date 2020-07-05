/*
 * [The "BSD licence"]
 * Copyright (c) 2010 Ben Gruver (JesusFreke)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package utils;

import com.google.common.collect.Ordering;
import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.dexlib2.analysis.InlineMethodResolver;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.util.ClassFileNameHandler;
import org.jf.util.IndentingWriter;

import javax.annotation.Nullable;
import java.io.*;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CustomBaksmali {
    public static boolean disassembleDexFile(DexFile dexFile, File outputDir, @Nullable String packageName) {
        final BaksmaliOptions options = getSmaliOptions((DexBackedDexFile) dexFile);
        int jobs = getNumberOfAvailableProcessors();
        //sort the classes, so that if we're on a case-insensitive file system and need to handle classes with file
        //name collisions, then we'll use the same name for each class, if the dex file goes through multiple
        //baksmali/smali cycles for some reason. If a class with a colliding name is added or removed, the filenames
        //may still change of course
        List<? extends ClassDef> classDefs = Ordering.natural().sortedCopy(dexFile.getClasses());

        final ClassFileNameHandler fileNameHandler = new ClassFileNameHandler(outputDir, ".smali");

        ExecutorService executor = Executors.newFixedThreadPool(jobs);
        List<Future<Boolean>> tasks = new ArrayList<>();

        for (final ClassDef classDef : classDefs) {
            if (packageName != null && !classDef.getType().contains(packageName.replaceAll("\\.", "/"))) {
                continue;
            }
            tasks.add(executor.submit(() -> disassembleClass(classDef, fileNameHandler, options)));
        }

        boolean errorOccurred = false;
        try {
            for (Future<Boolean> task : tasks) {
                while (true) {
                    try {
                        if (!task.get()) {
                            errorOccurred = true;
                        }
                    } catch (InterruptedException ex) {
                        continue;
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
            }
        } finally {
            executor.shutdown();
        }
        return !errorOccurred;
    }

    private static boolean disassembleClass(ClassDef classDef, ClassFileNameHandler fileNameHandler,
                                            BaksmaliOptions options) {
        /**
         * The path for the disassembly file is based on the package name
         * The class descriptor will look something like:
         * Ljava/lang/Object;
         * Where the there is leading 'L' and a trailing ';', and the parts of the
         * package name are separated by '/'
         */
        String classDescriptor = classDef.getType();

        //validate that the descriptor is formatted like we expect
        if (classDescriptor.charAt(0) != 'L' ||
                classDescriptor.charAt(classDescriptor.length() - 1) != ';') {
            System.err.println("Unrecognized class descriptor - " + classDescriptor + " - skipping class");
            return false;
        }

        File smaliFile = fileNameHandler.getUniqueFilenameForClass(classDescriptor);

        //create and initialize the top level string template
        ClassDefinition classDefinition = new ClassDefinition(options, classDef);

        //write the disassembly
        Writer writer = null;
        try {
            File smaliParent = smaliFile.getParentFile();
            if (!smaliParent.exists()) {
                if (!smaliParent.mkdirs()) {
                    // check again, it's likely it was created in a different thread
                    if (!smaliParent.exists()) {
                        System.err.println("Unable to create directory " + smaliParent.toString() + " - skipping class");
                        return false;
                    }
                }
            }

            if (!smaliFile.exists()) {
                if (!smaliFile.createNewFile()) {
                    System.err.println("Unable to create file " + smaliFile.toString() + " - skipping class");
                    return false;
                }
            }

            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(smaliFile), "UTF8"));

            writer = new IndentingWriter(bufWriter);
            classDefinition.writeTo((IndentingWriter) writer);
        } catch (Exception ex) {
            System.err.println("\n\nError occurred while disassembling class " + classDescriptor.replace('/', '.') + " - skipping class");
            ex.printStackTrace();
            // noinspection ResultOfMethodCallIgnored
            smaliFile.delete();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Throwable ex) {
                    System.err.println("\n\nError occurred while closing file " + smaliFile.toString());
                    ex.printStackTrace();
                }
            }
        }
        return true;
    }

    private static BaksmaliOptions getSmaliOptions(final DexBackedDexFile dexFile) {
        final BaksmaliOptions options = new BaksmaliOptions();

        options.deodex = false;
        options.implicitReferences = false;
        options.parameterRegisters = true;
        options.localsDirective = true;
        options.sequentialLabels = true;
        options.debugInfo = false;
        options.codeOffsets = false;
        options.accessorComments = false;
        options.registerInfo = 0;

        if (dexFile instanceof DexBackedOdexFile) {
            options.inlineResolver =
                    InlineMethodResolver.createInlineMethodResolver(
                            ((DexBackedOdexFile) dexFile).getOdexVersion());
        } else {
            options.inlineResolver = null;
        }

        return options;
    }

    private static int getNumberOfAvailableProcessors() {
        int jobs = Runtime.getRuntime().availableProcessors();
        return Math.min(jobs, 6);
    }
}

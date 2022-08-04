/*
 * blanco Framework
 * Copyright (C) 2004-2017 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
/*
 * Copyright 2017 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blanco.cg.transformer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import blanco.cg.BlancoCgTransformer;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * This is an abstract transformer that spans programming language type.
 *
 * @author IGA Tosiki
 */
abstract class AbstractBlancoCgTransformer implements BlancoCgTransformer {
    /**
     * Message prefix to display on the command line.
     */
    protected static final String CMDLINE_PREFIX = "cg: ";

    /**
     * Gets an extension of the source file.
     *
     * @return An extension.
     */
    protected abstract String getSourceFileExt();

    /**
     * Derives the file name from the class or interface name.
     *
     * This method is called only when the file name has not yet been deterimined.
     *
     * @param argSourceFile
     *            Source file object.
     */
    protected void decideFilenameFromClassOrInterfaceName(
            final BlancoCgSourceFile argSourceFile) {
        // Tries to resolve the file name from class name contained in BlancoCgSourceFile (file) when the file name is not set.
        String className = null;
        for (int index = 0; index < argSourceFile.getClassList().size(); index++) {
            final BlancoCgClass cgClass = argSourceFile.getClassList().get(
                    index);

            className = cgClass.getName();
            break;
        }

        if (className == null) {
            // If the file name has not yet been determined, it will try to derive the class name from the list of interfaces as well.
            for (int index = 0; index < argSourceFile.getInterfaceList().size(); index++) {
                final BlancoCgInterface cgInterface = argSourceFile
                        .getInterfaceList().get(index);

                className = cgInterface.getName();
                break;
            }
        }

        if (className == null) {
            // If the file name has not yet been determined, it will try to derive the class name from the list of Enum as well.
            for (int index = 0; index < argSourceFile.getEnumList().size(); index++) {
                final BlancoCgEnum cgEnum = argSourceFile
                        .getEnumList().get(index);

                className = cgEnum.getName();
                break;
            }
        }

        if (className == null) {
            // If the class name still cannot be determined, it will be treated as an exception.
            throw new IllegalArgumentException(
                    "Tried to determine the class name from the list of classes since the source file name was not specified, but failed.");
        }

        // Determines the source file name.
        // Note that it updates the source file name of the value object.
        argSourceFile.setName(className);
    }

    /**
     * Outputs the source code to the writer.
     *
     * Outputs a list of java.lang.String to the writer.
     *
     * @param argSourceLines
     *            Source code line list.
     * @param writer
     *            Output destination writer.
     * @throws IOException
     *             If an I/O exception occurs.
     */
    protected void source2Writer(final List<java.lang.String> argSourceLines,
            final BufferedWriter writer) throws IOException {
        boolean isPastLineBlank = false;
        boolean isPastBlockStart = false;
        String lineSeparator = System.getProperty("line.separator");
        /*
         * for debugging
         */
        String lineSeparatorMark = "other, use system default.";
        if ("\n".equals(lineSeparator)) {
            lineSeparatorMark = "LF";
        } else if ("\r\n".equals(lineSeparator)) {
            lineSeparatorMark = "CRLF";
        } else if ("\r".equals(lineSeparator)) {
            lineSeparatorMark = "CR";
        } else {
            lineSeparator = System.lineSeparator();
        }
//        System.out.println(CMDLINE_PREFIX + "lineSeparator = " + lineSeparatorMark);
        for (int index = 0; index < argSourceLines.size(); index++) {
            final String line = argSourceLines.get(index);

            // Suppresses the output of consecutive blank lines.
            if (line.length() == 0) {
                if (isPastLineBlank) {
                    // It passes the output this time since it is a blank line again.
                    continue;
                }
                // This time it was a blank line.
                isPastLineBlank = true;
            } else {
                // This time it is not a blank line.
                isPastLineBlank = false;
            }

            if (isPastBlockStart && line.length() == 0) {
                // If the last time was the start of a block and this time is a blank line, it passes the output.
                continue;
            }

            if (line.endsWith("{")) {
                isPastBlockStart = true;
            } else {
                isPastBlockStart = false;
            }

            // Outputs a single line.
            writer.write(line);
//            writer.newLine();
            writer.write(lineSeparator);
        }
    }
}

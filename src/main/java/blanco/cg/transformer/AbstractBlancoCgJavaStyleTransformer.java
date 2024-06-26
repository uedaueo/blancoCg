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

import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoFileUtil;
import blanco.commons.util.BlancoStringUtil;

import java.io.*;
import java.util.List;

/**
 * A Java/C#.net-style abstract transformer.
 *
 * @author IGA Tosiki
 */
public abstract class AbstractBlancoCgJavaStyleTransformer extends
        AbstractBlancoCgTransformer {
    /**
     * Whether to run in debug mode.
     */
    private static final boolean IS_DEBUG = false;

    /**
     * Number of tabs.
     */
    private int tabs = 4;

    public int getTabs() {
        return tabs;
    }

    public void setTabs(int tabs) {
        this.tabs = tabs;
    }

    /**
     * Converts the source file value object to Java source code and outputs it to the destination directory.
     *
     * Considers the package structure as a directory structure in this API.
     *
     * @param argSourceFile
     *            Source file value object.
     * @param outputDirectory
     *            An output destination root directory.
     */
    public void transform(final BlancoCgSourceFile argSourceFile,
            final File outputDirectory) {
        if (argSourceFile == null) {
            throw new IllegalArgumentException("Source file was given as null. Aborts the process.");
        }
        if (outputDirectory == null) {
            throw new IllegalArgumentException(
                    "An output destination root directory was given as null. Aborts the process.");
        }

        if (outputDirectory.exists() == false) {
            if (outputDirectory.mkdirs() == false) {
                throw new IllegalArgumentException("An output destination root directory ["
                        + outputDirectory.getAbsolutePath()
                        + "] does not exist, so we tried to create it, but failed. Aborts the process.");
            }
        }
        if (outputDirectory.isDirectory() == false) {
            throw new IllegalArgumentException("A file [" + outputDirectory.getAbsolutePath()
                + "] that is not a directory was given as the output root directory. Aborts the process.");
        }

        if (argSourceFile.getName() == null) {
            // Since the file name has not been determined, derives it from the class or interface name.
            decideFilenameFromClassOrInterfaceName(argSourceFile);
        }

        try {
            // Converts a package name to a directory name.
            String strSubdirectory = BlancoStringUtil.replaceAll(
                    BlancoStringUtil.null2Blank(argSourceFile.getPackage()),
                    '.', '/');
            if (strSubdirectory.length() > 0) {
                // Adds a slash only if subdirectories exist.
                strSubdirectory = "/" + strSubdirectory;
            }

            final File targetPackageDirectory = new File(outputDirectory
                    .getAbsolutePath()
                    + strSubdirectory);
            if (targetPackageDirectory.exists() == false) {
                if (targetPackageDirectory.mkdirs() == false) {
                    throw new IllegalArgumentException("Failed to generate the output destination package directory ["
                            + targetPackageDirectory.getAbsolutePath() + "].");
                }
            }

            // Finalizes the output destination file.
            final File fileTarget = new File(targetPackageDirectory
                    .getAbsolutePath()
                    + "/" + argSourceFile.getName() + getSourceFileExt());

            // Performs the actual source code output process.
            final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            // Feature to specify encoding for auto-generated source code.
            OutputStreamWriter streamWriter = null;
            if (BlancoStringUtil.null2Blank(argSourceFile.getEncoding())
                    .length() == 0) {
                streamWriter = new OutputStreamWriter(outStream);
            } else {
                streamWriter = new OutputStreamWriter(outStream, argSourceFile
                        .getEncoding());
            }

            final BufferedWriter writer = new BufferedWriter(streamWriter);
            try {
                transform(argSourceFile, writer);
                writer.flush();
                outStream.flush();

                switch (BlancoFileUtil.bytes2FileIfNecessary(outStream
                        .toByteArray(), fileTarget)) {
                case 0:
                    if (IS_DEBUG) {
                        // Outputs "skip" to stdout only when debugging.
                        System.out.println(CMDLINE_PREFIX + "skip  : "
                                + fileTarget.getAbsolutePath());
                    }
                    break;
                case 1:
                    System.out.println(CMDLINE_PREFIX + "create: "
                            + fileTarget.getAbsolutePath());
                    break;
                case 2:
                    System.out.println(CMDLINE_PREFIX + "update: "
                            + fileTarget.getAbsolutePath());
                    break;
                }
            } finally {
                // An instance of ByteArrayOutputStream is automatically closed when the writer is closed, due to the stream chain mechanism.

                if (writer != null) {
                    writer.close();
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("An exception occurred in the process of outputting the source code."
                    + ex.toString());
        }
    }

    /**
     * Formats the source code list.
     *
     * Performs formatting for Java and C#.NET.
     *
     * We judge that the source shaping routines at the moment can be considered common for Java/C#.NET.<br>
     * Note that "{" and "}" have a special meaning in this process. If a comment is added at the end of a line, the expected behavior will not be achieved.<br>
     * TODO: Formatting, such as adding curly brackets to the end of a statement, is not yet implemented.
     *
     * @param argSourceLines
     *            A source code line list.
     */
    protected void formatSource(final List<java.lang.String> argSourceLines) {
        int sourceIndent = 0;
        int curlyIndented = 0;
        int roundIndented = 0;
        for (int index = 0; index < argSourceLines.size(); index++) {
            String strLine = argSourceLines.get(index);
            // Spaces before and after are removed beforehand.
            strLine = strLine.trim();

            if (strLine.length() == 0) {
                // Blank line.
            } else {
                boolean isBeginIndent = false;
                boolean isEndIndent = false;
                boolean isBeginDoubleIndent = false;
                boolean isEndDoubleIndent = false;
                final char startChar = strLine.charAt(0);
                final char endChar = strLine.charAt(strLine.length() - 1);
                if (startChar == '*') {
                    // Considers a comment line and indent one character.
                    strLine = " " + strLine;
                }

                // First, it determines the start string.
                // Note: The start string and the end string must be determined separately.
                // Handles "(" and ")". 2020/01/14 by tueda
                if (startChar == '{') {
                    // Considers as the start of a block and reserves for indentation.
                    isBeginIndent = true;
                    curlyIndented++;
                } else if (startChar == '}') {
                    // Considers as the end of a block and indents.
                    isEndIndent = true;
                } else if (startChar == '(') {
                    // Considers as the start of a block and reserves for indentation.
                    isBeginDoubleIndent = true;
                    roundIndented++;
                } else if (startChar == ')') {
                    // Considers as the end of a block and indents.
                    isEndDoubleIndent = true;
                }

                // Next, it determines the end string.
                // Note: The start string and the end string must be determined separately.
                // Handles "(" and ")". 2020/01/14 by tueda
                if (endChar == '{') {
                    // Considers as the start of a block and reserves for indentation.
                    isBeginIndent = true;
                    curlyIndented++;
                } else if (endChar == '}') {
                    // Considers as the end of a block and indents.
                    isEndIndent = true;
                } else if (endChar == '(') {
                    // Considers as the start of a block and reserves for indentation.
                    isBeginDoubleIndent = true;
                    roundIndented++;
                } else if (endChar == ')') {
                    // Considers as the end of a block and indents.
                    isEndDoubleIndent = true;
                }

                if (isEndIndent && curlyIndented > 0) {
                    // Reflects one indent per flag.
                    sourceIndent--;
                    curlyIndented--;
                }
                if (isEndDoubleIndent && roundIndented > 0) {
                    sourceIndent -= 2;
                    roundIndented--;
                }

                StringBuffer indentWidth = new StringBuffer();
                // Determines the indentation width. The default is 4 tabs.
                for (int width = 0; width < this.tabs; width++) {
                    indentWidth.append(" ");
                }

                // Performs indentation.
                for (int indexIndent = 0; indexIndent < sourceIndent; indexIndent++) {
                    strLine = indentWidth.toString() + strLine;
                }

                if (isBeginIndent) {
                    sourceIndent++;
                }
                if (isBeginDoubleIndent) {
                    sourceIndent += 2;
                }

                // Refreshes the list with the updated line image.
                argSourceLines.set(index, strLine);
            }
        }
    }
}

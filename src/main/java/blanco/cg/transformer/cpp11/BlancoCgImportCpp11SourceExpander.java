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
package blanco.cg.transformer.cpp11;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Expands the import information in the BlancoCgSourceFile.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.<br>
 * import expansion is a surprisingly complex process.
 * 
 * @author IGA Tosiki
 */
class BlancoCgImportCpp11SourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.CS;

    /**
     * An anchor string to expand the import statement.
     */
    private static final String REPLACE_IMPORT_HERE = "/*replace import here*/";

    /**
     * Index of found anchor string.
     * 
     * Whenever the import statement is edited in the process of this class, this value will also be updated.
     */
    private int fFindReplaceImport = -1;

    /**
     * Expands import.
     * 
     * This method is called after the completion of class expansion, method expansion, etc.
     * 
     * @param argSourceFile
     *            A source file instance.
     * @param argSourceLines
     *            A source line image. (java.lang.String will be stored)
     */
    public void transformImport(final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // In C++, it cannot be added in this way.
        // Removes the array representation attached to the end of the class name to be imported.
        // trimArraySuffix(argSourceFile.getImportList());

        // In C++, it cannot be added in this way.
        // Removes the class name from the import (using) list.
        // trimClassName(argSourceFile);

        // First, sorts the import statements to make it easier to process.
        sortImport(argSourceFile.getImportList());

        // Removes duplicate import statements.
        trimRepeatedImport(argSourceFile.getImportList());

        // In C++, it cannot be added in this way.
        // Removes classes that do not need to be imported.
        // trimUnnecessaryImport(argSourceFile.getImportList());

        // In C++, it cannot be added in this way.
        // Suppresses import for packages belonging to its own class.
        // trimMyselfImport(argSourceFile, argSourceFile.getImportList());

        // Searches for an anchor string.
        fFindReplaceImport = findAnchorString(argSourceLines);
        if (fFindReplaceImport < 0) {
            throw new IllegalArgumentException("Could not find the replacement string for the import statement.");
        }

        // First, it expands the "System" package.
        expandImportWithTarget(argSourceFile, "System", argSourceLines);

        // Finally, it expands the packages other than "System".
        expandImportWithTarget(argSourceFile, null, argSourceLines);

        // Removes the anchor string.
        removeAnchorString(argSourceLines);
    }

    /**
     * Expands the import with the target of the expansion in mind.
     * 
     * @param argSourceFile
     * @param argTarget
     *            Specifies java. or javax. or null.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandImportWithTarget(final BlancoCgSourceFile argSourceFile,
            final String argTarget, final List<java.lang.String> argSourceLines) {
        boolean isProcessed = false;
        for (int index = 0; index < argSourceFile.getImportList().size(); index++) {
            final String strImport = argSourceFile.getImportList().get(index);

            if (argTarget == null) {
                // Expands all except "System.".
                if (strImport.startsWith("System")) {
                    // Skips the process since this is not the package to be processed.
                    // Note that "System." is hard-coded.
                    continue;
                }
            } else {
                if (strImport.startsWith(argTarget) == false) {
                    // Skips the process since this is not the package to be processed.
                    continue;
                }
            }

            isProcessed = true;
            argSourceLines.add(fFindReplaceImport++, "#include \"" + strImport+"\"");
        }

        if (isProcessed) {
            // Adds a blank only if the import expansion process exists.
            argSourceLines.add(fFindReplaceImport++, "");
        }
    }

    /**
     * Searches for the number of lines (0-origin) in the replacement anchor string.
     * 
     * @return Position of the found anchor string (0-origin). If not found, -1.
     * @param argSourceLines
     *            The source list.
     */
    private static final int findAnchorString(
            final List<java.lang.String> argSourceLines) {
        for (int index = 0; index < argSourceLines.size(); index++) {
            final String line = argSourceLines.get(index);
            if (line.equals(REPLACE_IMPORT_HERE)) {
                // Found it.
                return index;
            }
        }

        // Could not be found. Returns -1 to indicate it.
        return -1;
    }

    /**
     * Inserts an anchor string.
     * 
     * Since it will reorganize the import statement later in the process, adds an anchor string to refer to it.<br>
     * This method is called by other classes.
     * 
     * @param argSourceLines
     *            The source list.
     */
    public static final void insertAnchorString(
            final List<java.lang.String> argSourceLines) {
        argSourceLines.add(BlancoCgImportCpp11SourceExpander.REPLACE_IMPORT_HERE);
    }

    /**
     * Removes the anchor string.
     * 
     * @param argSourceLines
     *            The source list.
     */
    private static final void removeAnchorString(
            final List<java.lang.String> argSourceLines) {
        // Finally, removes the anchor string itself.
        int findReplaceImport2 = findAnchorString(argSourceLines);
        if (findReplaceImport2 < 0) {
            throw new IllegalArgumentException("Could not find the replacement string for the import statement.");
        }
        argSourceLines.remove(findReplaceImport2);
    }

    /**
     * Sorts the given imports.
     * 
     * If a node type other than the expected one (java.lang.String) is given, an exception is raised.
     * 
     * @param argImport
     *            A list of imports.
     */
    private static final void sortImport(final List<java.lang.String> argImport) {
        Collections.sort(argImport, new Comparator<java.lang.String>() {
            public int compare(final String arg0, final String arg1) {
                if (arg0 instanceof String == false) {
                    throw new IllegalArgumentException("The value ["
                            + arg0 + "] in the import list is a type ["
                            + arg0.getClass().getName() + "] other than java.lang.String.");
                }
                if (arg1 instanceof String == false) {
                    throw new IllegalArgumentException("The value ["
                            + arg1 + "] in the import list is a type ["
                            + arg1.getClass().getName() + "] other than java.lang.String.");
                }
                final String str0 = (String) arg0;
                final String str1 = (String) arg1;
                return str0.compareTo(str1);
            }
        });
    }

    /**
     * Removes a duplicate import.
     * 
     * This method assumes that the given list has already been sorted.
     * 
     * @param argImport
     *            A list of imports.
     */
    private void trimRepeatedImport(final List<java.lang.String> argImport) {
        // Removes a duplicate import.
        String pastImport = "";
        for (int index = argImport.size() - 1; index >= 0; index--) {
            final String strImport = argImport.get(index);
            if (pastImport.equals(strImport)) {
                // This is a already processed import. Removes it.
                argImport.remove(index);
            }
            // The current import is stored as the previous import.
            pastImport = strImport;
        }
    }

    /**
     * For a particular package, removes it from the list.
     * It is used to remove the package belonging to its own class.
     * 
     * @param argSpecificPackage
     *            Package to be processed.
     * @param argImport
     *            A list of imports.
     */
    private static void trimSpecificPackage(final String argSpecificPackage,
            final List<java.lang.String> argImport) {
        for (int index = argImport.size() - 1; index >= 0; index--) {
            // Type checking has been performed at the time of sorting.
            final String strImport = argImport.get(index);

            // In C#.NET, the namespace is stored. Namespaces are directly compared with each other.
            if (argSpecificPackage.equals(strImport)) {
                argImport.remove(index);
            }
        }
    }
}

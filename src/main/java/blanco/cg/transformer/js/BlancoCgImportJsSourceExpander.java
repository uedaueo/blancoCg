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
package blanco.cg.transformer.js;

import java.util.List;

import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Expands the import information in the BlancoCgSourceFile.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.<br>
 * import expansion is a surprisingly complex process.
 * 
 * @author IGA Tosiki
 */
class BlancoCgImportJsSourceExpander {
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
        // Searches for an anchor string.
        fFindReplaceImport = findAnchorString(argSourceLines);
        if (fFindReplaceImport < 0) {
            throw new IllegalArgumentException("Could not find the replacement string for the import statement.");
        }

        // Removes the anchor string.
        removeAnchorString(argSourceLines);
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
        argSourceLines.add(BlancoCgImportJsSourceExpander.REPLACE_IMPORT_HERE);
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
}

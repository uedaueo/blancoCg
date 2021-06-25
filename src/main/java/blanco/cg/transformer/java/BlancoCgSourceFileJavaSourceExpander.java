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
package blanco.cg.transformer.java;

import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgSourceFile into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgSourceFileJavaSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JAVA;

    /**
     * The input source code structure.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * List indicating the source code to be used intermediately. java.lang.String will be stored (It is not BlancoCgLine).
     * 
     * The unformatted source code will be stored in the interim here.
     */
    private List<java.lang.String> fSourceLines = null;

    /**
     * Generates a list of unformatted source code from SourceFile.
     * 
     * @param argSourceFile
     *            A value object representing the source code.
     * @return A list after expansion into source code.
     */
    public List<java.lang.String> transformSourceFile(
            final BlancoCgSourceFile argSourceFile) {
        // Definitely initializes the list of source lines.
        fSourceLines = new ArrayList<java.lang.String>();

        fCgSourceFile = argSourceFile;

        // Outputs the file headers of the source file.
        expandSourceFileHeader();

        // Generates the package part.
        if (BlancoStringUtil.null2Blank(fCgSourceFile.getPackage()).length() > 0) {
            fSourceLines.add("package " + fCgSourceFile.getPackage()
                    + BlancoCgLineUtil.getTerminator(TARGET_LANG));
            fSourceLines.add("");
        }

        if (fCgSourceFile.getImportList() == null) {
            throw new IllegalArgumentException("The list of imports has been given a null value.");
        }

        // Since it will reorganize the import statement later in the process, adds an anchor string to refer to it.
        BlancoCgImportJavaSourceExpander.insertAnchorString(fSourceLines);

        // Performs enumeration expansion.
        for (BlancoCgEnum cgEnum : fCgSourceFile.getEnumList()) {
            new BlancoCgEnumJavaSourceExpander().transformEnum(cgEnum,
                    argSourceFile, fSourceLines);
        }

        // Performs interface expansion.
        if (fCgSourceFile.getInterfaceList() == null) {
            throw new IllegalArgumentException("The list of interfaces has been given a null value.");
        }
        for (BlancoCgInterface cgInterface : fCgSourceFile.getInterfaceList()) {
            new BlancoCgInterfaceJavaSourceExpander().transformInterface(
                    cgInterface, fCgSourceFile, fSourceLines);
        }

        // Performs class expansion.
        if (fCgSourceFile.getClassList() == null) {
            throw new IllegalArgumentException("The list of classes has been given a null value.");
        }
        for (BlancoCgClass cgClass : fCgSourceFile.getClassList()) {
            new BlancoCgClassJavaSourceExpander().transformClass(cgClass,
                    fCgSourceFile, fSourceLines);
        }

        // Expands import.
        // There is a reason why this process is done after the class expansion.
        // This is because the list of import statements can be finalized only after the class expansion, etc.
        new BlancoCgImportJavaSourceExpander().transformImport(fCgSourceFile,
                fSourceLines);

        return fSourceLines;
    }

    /**
     * Outputs the file headers of the source file.
     */
    private void expandSourceFileHeader() {
        if (BlancoStringUtil.null2Blank(fCgSourceFile.getDescription()).length() == 0
                && BlancoStringUtil.null2Blank(fCgSourceFile.getLangDoc().getTitle()).length() == 0
                && fCgSourceFile.getLangDoc().getDescriptionList().size() == 0
                ) {
            // If no language comments are specified at all, output is suppressed.
            // Initially, the default comments were output, but this has been removed.
            return;
        }

        fSourceLines.add("/*");
        if (BlancoStringUtil.null2Blank(fCgSourceFile.getDescription()).length() > 0) {
            fSourceLines.add("* " + fCgSourceFile.getDescription());
        }

        // Generates the intermediate part of a language document.
        new BlancoCgLangDocJavaSourceExpander().transformLangDocBody(
                fCgSourceFile.getLangDoc(), fSourceLines);

        fSourceLines.add("*/");
    }


    /**
     * Adds import statement for fully qualified class name.
     *
     * @param argCgType
     * @param argSourceFile
     * @author tueda
     */
    public static void typeToImport(
            final BlancoCgType argCgType,
            final BlancoCgSourceFile argSourceFile
    ) {
        if (argCgType != null) {
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.JAVA, argCgType.getName())) {
                argSourceFile.getImportList().add(argCgType.getName());
            }
            for (BlancoCgType nextCgType : argCgType.getGenericsTree()) {
                typeToImport(nextCgType, argSourceFile);
            }
        }
    }
}

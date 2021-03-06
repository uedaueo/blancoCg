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

import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgEnumElement;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgEnum into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgEnumJavaSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JAVA;

    /**
     * Expands the enumeration here.
     * 
     * @param cgEnum
     *            The enumeration to be processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            List of lines to output.
     */
    public void transformEnum(final BlancoCgEnum cgEnum,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (BlancoStringUtil.null2Blank(cgEnum.getName()).length() == 0) {
            throw new IllegalArgumentException("The enumeration name is not set to an appropriate value.");
        }

        // Adds a line break inevitably.
        argSourceLines.add("");

        // First, it expands the enum information into LangDoc.
        if (cgEnum.getLangDoc() == null) {
            // Creates an instance here if LangDoc is not specified.
            cgEnum.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgEnum.getLangDoc().getTitle() == null) {
            cgEnum.getLangDoc().setTitle(cgEnum.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocJavaSourceExpander().transformLangDoc(cgEnum
                .getLangDoc(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgEnum.getAccess()).length() > 0) {
            buf.append(cgEnum.getAccess() + " ");
        }

        // Expands the body part of the enumeration generation.
        buf.append("enum " + cgEnum.getName());

        // Expands the elements.
        buf.append(" {");
        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);

        boolean isFirstElement = true;
        for (BlancoCgEnumElement element : cgEnum.getElementList()) {
            if (isFirstElement) {
                isFirstElement = false;
            } else {
                buf.append(", ");
                // Finalizes the line and performs the export.
                argSourceLines.add(buf.toString());
                buf.setLength(0);
            }
            if (BlancoStringUtil.null2Blank(element.getDescription()).length() > 0) {
                buf.append(" /** "
                        + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                        element.getDescription()) + " */");
                // Finalizes the line and performs the export.
                argSourceLines.add(buf.toString());
                buf.setLength(0);
            }
            buf.append(element.getName());
        }
        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);
        buf.append("}");

        buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        argSourceLines.add(buf.toString());
    }
}

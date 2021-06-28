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
package blanco.cg.transformer.vb;

import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgField into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgFieldVbSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.VB;

    /**
     * Expands a field here.
     * 
     * @param cgField
     *            The field to be processed.
     * @param argSourceFile
     *            A source file.
     * @param argSourceLines
     *            List of lines to output.
     * @param argIsInterface
     *            Whether it is an instance or not. False for a class, true for an interface.
     */
    public void transformField(final BlancoCgField cgField,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines,
            final boolean argIsInterface) {
        if (BlancoStringUtil.null2Blank(cgField.getName()).length() == 0) {
            throw new IllegalArgumentException("The field name is not set to an appropriate value.");
        }
        if (BlancoStringUtil.null2Blank(cgField.getType().getName()).length() == 0) {
            throw new IllegalArgumentException("The type of the field [" + cgField.getName()
                    + "] is not set to an appropriate value.");
        }

        // Adds a line break inevitably.
        argSourceLines.add("");

        // First, it expands the field information into LangDoc.
        if (cgField.getLangDoc() == null) {
            // Creates an instance here if LangDoc is not specified.
            cgField.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgField.getLangDoc().getTitle() == null) {
            cgField.getLangDoc().setTitle(cgField.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocVbSourceExpander().transformLangDoc(cgField
                .getLangDoc(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgField.getAccess()).length() > 0) {
            if (argIsInterface && cgField.getAccess().equals("public")) {
                // If it's an interface and public, the output is suppressed.
                // This is a countermeasure to Checkstyle.
            } else {
                buf.append(BlancoNameAdjuster.toClassName(cgField.getAccess())
                        + " ");
            }
        }
        if (cgField.getStatic()) {
            // In VB.NET, there is no equivalent to Static.
            // buf.append("Static ");
        }
        if (cgField.getFinal()) {
            // In VB.NET, the "final" modifier to a field is the Const modifier.
            buf.append("Const ");
        }

        // Adds the type to the import statement.
        argSourceFile.getImportList().add(cgField.getType().getName());

        // Expands the body part of the field generation.
        buf.append(cgField.getName());
        buf.append(" As "
                + BlancoCgTypeVbSourceExpander.toTypeString(cgField.getType()));

        // If a default value is specified, this will be expanded.
        if (BlancoStringUtil.null2Blank(cgField.getDefault()).length() > 0) {
            buf.append(" = " + cgField.getDefault());
        }
        // In fact, the ";" is not given.
        buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        argSourceLines.add(buf.toString());
    }
}

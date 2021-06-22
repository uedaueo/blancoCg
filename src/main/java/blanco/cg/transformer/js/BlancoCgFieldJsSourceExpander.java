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

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgField into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgFieldJsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JS;

    /**
     * Expands a field here.
     * 
     * @param cgClass
     *            The class to be processed.
     * @param cgField
     *            The field to be processed.
     * @param argSourceFile
     *            A source file.
     * @param argSourceLines
     *            List of lines to output.
     */
    public void transformField(final BlancoCgClass cgClass,
            final BlancoCgField cgField,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
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

        if (BlancoStringUtil.null2Blank(cgField.getAccess()).equals("private")
                || BlancoStringUtil.null2Blank(cgField.getAccess()).equals(
                        "protected")) {
            // Expands the scope expression only if it is protected or private.
            cgField.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            cgField.getAccess(), null, ""));
        }

        if (BlancoStringUtil.null2Blank(cgField.getType().getName()).length() > 0
                || BlancoStringUtil.null2Blank(cgField.getType().getName())
                        .equals("void") == false) {
            cgField.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            "type", null, cgField.getType().getName()));
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocJsSourceExpander().transformLangDoc(cgField
                .getLangDoc(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (cgField.getStatic()) {
            // Class fields (static fields) are expanded directly by the <class name>.<field name> as followed.
            buf.append(cgClass.getName() + ".");
        } else {
            // A normal field variable is expanded as this.<field name>.
            buf.append("this.");
        }

        // Expands the body part of the field generation.
        buf.append(cgField.getName());

        // If a default value is specified, this will be expanded.
        if (BlancoStringUtil.null2Blank(cgField.getDefault()).length() > 0) {
            buf.append(" = " + cgField.getDefault()
                    + BlancoCgLineUtil.getTerminator(TARGET_LANG));
        } else {
            buf.append(" = null" + BlancoCgLineUtil.getTerminator(TARGET_LANG));
        }

        argSourceLines.add(buf.toString());

        // Adds the type to the import statement.
        argSourceFile.getImportList().add(cgField.getType().getName());
    }
}

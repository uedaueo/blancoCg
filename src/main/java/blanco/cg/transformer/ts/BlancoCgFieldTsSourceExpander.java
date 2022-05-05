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
 * Copyright 2019 Ueda Ueo
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
package blanco.cg.transformer.ts;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgField into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgFieldTsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.TS;

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
    public void transformField(final BlancoCgField cgField, final BlancoCgSourceFile argSourceFile,
                               final List<String> argSourceLines, final boolean argIsInterface) {
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
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgField.getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgField, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        String access = cgField.getAccess();
        if (BlancoStringUtil.null2Blank(access).length() > 0) {
            // In TypeScript, it defaults to public.
            if (!access.equals("public")) {
                if (!argIsInterface) {
                    buf.append(cgField.getAccess() + " ");
                } else {
                    throw new IllegalArgumentException("TypeScript does not allow to set accessors on Interface (field [" + cgField.getName() + "]).");
                }
            }
        }

        if (cgField.getStatic()) {
            if (!argIsInterface) {
                buf.append("static ");
            } else {
                throw new IllegalArgumentException("TypeScript does not allow to set static field on Interface (field [" + cgField.getName() + "]).");
            }
        }

        /*
         * final:
         * In Java, when you want to define a constant as a class field, you declare final.
         * The same thing can be achieved in TypeScript with readonly.
         */
        if (cgField.getFinal() || cgField.getConst()) {
            buf.append("readonly ");
        }

        /*
         * Adds the type to the import statement. However, this import statement is not used in TypeScript.
         */
        argSourceFile.getImportList().add(cgField.getType().getName());

        /* Set field name. */
        buf.append(cgField.getName());
        /*
         * If Nullable is set, or if it is not an interface but the default value is not set.
         */
        if (!cgField.getNotnull() ||
                (!argIsInterface && BlancoStringUtil.null2Blank(cgField.getDefault()).length() == 0)) {
            // nullable
            buf.append("?");
        }
        /* type inference (omit type declaration) */
        if (!cgField.getTypeInference()) {
            buf.append(": " + BlancoCgTypeTsSourceExpander.toTypeString(cgField.getType()));
        }

        // If a default value is specified, this will be expanded.
        if (BlancoStringUtil.null2Blank(cgField.getDefault()).length() > 0) {
            // Interface in TypeScript cannot have a default value.
            if (!argIsInterface) {
                buf.append(" = " + cgField.getDefault());
            } else {
                throw new IllegalArgumentException("TypeScript does not allow to set default values on Interface (field [" + cgField.getName() + "]).");
            }
        }
        buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        argSourceLines.add(buf.toString());
    }

    /**
     * Expands annotations.
     *
     * @param cgField
     *            The field.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgField cgField, final List<String> argSourceLines) {
        for (String strAnnotation : cgField.getAnnotationList()) {
            // Annotasion in Java is written starting with "@".
            argSourceLines.add("@" + strAnnotation);
        }
    }
}

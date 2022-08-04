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
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgEnumElement;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgEnum into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgEnumTsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.TS;

    /**
     * Expands the EnumClass here.
     *
     * @param argCgEnum
     *            An EnumClass to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformEnum(final BlancoCgEnum argCgEnum,
                              final BlancoCgSourceFile argSourceFile,
                              final List<String> argSourceLines) {
        // First, it expands the class information into a LangDoc.
        if (argCgEnum.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            argCgEnum.setLangDoc(new BlancoCgLangDoc());
        }
        if (argCgEnum.getLangDoc().getTitle() == null) {
            argCgEnum.getLangDoc().setTitle(argCgEnum.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(argCgEnum
                .getLangDoc(), argSourceLines);

        // Expands annotations.
//        expandAnnotationList(argCgEnumClass, argSourceLines);

        final StringBuffer buf = new StringBuffer();
        if (BlancoStringUtil.null2Blank(argCgEnum.getAccess()).length() > 0) {
            /*
             * In TypeScript, it defaults public.
             * If "public" is specified, replaces it with "export".
             * Since blanco expects one class per file, and class is assumed to be named, we will not use export default.
             * by tueda, 2020/03/11
             *
             * Note: default is required in the vue component, so if default is specified, export default will be used.
             * by tueda, 2020/06/17
             */
            if ("public".equals(argCgEnum.getAccess())) {
                buf.append("export ");
            } else if ("default".equals(argCgEnum.getAccess())) {
                buf.append("export default ");
            }
        }

        buf.append("enum " + argCgEnum.getName());
        buf.append(" {");

        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);

        boolean isFirstElement = true;
        for (BlancoCgEnumElement element : argCgEnum.getElementList()) {
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

            // Outputs the default value.
            if (BlancoStringUtil.null2Blank(element.getDefault()).length() > 0) {
                buf.append(" = " + element.getDefault());
            }
        }
        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);
        buf.append("}");

        if (argCgEnum.getLineTerminator()) {
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        }
        argSourceLines.add(buf.toString());
    }
}

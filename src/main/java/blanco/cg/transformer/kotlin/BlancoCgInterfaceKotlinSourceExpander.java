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
package blanco.cg.transformer.kotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgInterface into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgInterfaceKotlinSourceExpander {

    /**
     * Expands the interface here.
     *
     * @param cgInterface
     *            The interface to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformInterface(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // In the case of an interface, "public" is excluded from fields and methods.

        // First, it expands the interface information into LangDoc.
        if (cgInterface.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            cgInterface.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgInterface.getLangDoc().getTitle() == null) {
            cgInterface.getLangDoc().setTitle(cgInterface.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgInterface
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, cgInterface.getAnnotationList(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgInterface.getAccess()).length() > 0) {
            // In Kotlin, it defaults public.
            if (!"public".equals(cgInterface.getAccess())) {
                buf.append(cgInterface.getAccess() + " ");
            }
        }
        // static and final are not expanded.
        buf.append("interface " + cgInterface.getName());

        // Expands the parent class here.
        expandExtendClassList(cgInterface, argSourceFile, buf);

        // Point: The parent interface expansion does not exist in interface.

        buf.append(" {");

        argSourceLines.add(buf.toString());

        // Expands the field here.
        expandFieldList(cgInterface, argSourceFile, argSourceLines);

        // Expands the method here.
        expandMethodList(cgInterface, argSourceFile, argSourceLines);

        argSourceLines.add("}");
    }

    /**
     * Expands the parent class.
     *
     * @param cgClass
     * @param argSourceFile
     * @param argBuf
     * @return
     */
    private void expandExtendClassList(final BlancoCgInterface cgClass,
                                          final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        Boolean isFirst = true;
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getExtendClassList().get(index));

            // Adds a type to the import statement.
            BlancoCgSourceFileKotlinSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" : "
                        + BlancoCgTypeKotlinSourceExpander.toTypeString(type));
                isFirst = false;
            } else {
                // In kotlin multiple extends of interface is permitted.
                argBuf.append(", "
                        + BlancoCgTypeKotlinSourceExpander.toTypeString(type));
//                throw new IllegalArgumentException("In Kotlin, inheritance can only be performed once.");
            }
        }
        if (!isFirst) {
            argBuf.append(" ");
        }
    }

    /**
     * Expands each of the included fields.
     *
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandFieldList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }

        for (BlancoCgField cgField : cgInterface.getFieldList()) {
            new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, true);
        }
    }

    /**
     * Expands each of the included methods.
     *
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandMethodList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of method.");
        }
        for (BlancoCgMethod cgMethod : cgInterface.getMethodList()) {
            new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, true);
        }
    }
}

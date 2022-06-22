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
package blanco.cg.transformer.ts;

import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgInterface into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgInterfaceTsSourceExpander {

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
            final List<String> argSourceLines) {
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
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgInterface
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgInterface, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgInterface.getAccess()).length() > 0) {
            /*
             * In TypeScript, it defaults public.
             * If "public" is specified, replaces it with "export".
             * Since blanco expects one class per file, and class is assumed to be named, we will not use export default.
             *
             * by tueda, 2020/03/11
             */
            if ("public".equals(cgInterface.getAccess())) {
                buf.append("export ");
            }
        }
        // static and final are not expanded.
        buf.append("interface " + cgInterface.getName());

        // Expands the parent class here.
        expandExtendClassList(cgInterface, argSourceFile, buf);

        // Point: The parent interface expansion does not exist in interface.

        buf.append(" {");

        argSourceLines.add(buf.toString());

        // Expands PlainText here.
        expandPlainText(cgInterface, argSourceLines);

        // Expands the field here.
        expandFieldList(cgInterface, argSourceFile, argSourceLines);

        // Expands the method here.
        expandMethodList(cgInterface, argSourceFile, argSourceLines);

        argSourceLines.add("}");
    }

    /**
     * Expands Plain Text.
     *
     * @param cgInterface
     * @param argSourceLines
     */
    private void expandPlainText(
            final BlancoCgInterface cgInterface,
            final List<String> argSourceLines) {
        List<String> plainTextList = cgInterface.getPlainTextList();

        // Adds a line break inevitably.
        argSourceLines.add("");

        for (String planText : plainTextList) {
            argSourceLines.add(planText);
        }
    }

    /**
     * Expands annotations.
     *
     * @param cgInterface
     *            The interface.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgInterface cgInterface,
            final List<String> argSourceLines) {
        for (String strAnnotation : cgInterface.getAnnotationList()) {
            // Annotation in Java is written starting with "@".
            argSourceLines.add("@" + strAnnotation);
        }
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
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = cgClass.getExtendClassList().get(index);

            /*
             * Adds a type to the import statement.
             * We assume that the type is specified in Java format ("." delimited) here.
             * At the time of Transform, the package part is interpreted as information of the location of the definition file, and an import statement is created.
             */
            argSourceFile.getImportList().add(type.getName());

            if (index == 0) {
                argBuf.append(" extends "
                        + BlancoCgTypeTsSourceExpander.toTypeString(type));
            } else {
                /*
                 * Interface in TypeScript looks like we can extend multiple classes.
                 * TODO: Not sure if multiple inheritance will work properly.
                 */
                argBuf.append(", " + BlancoCgTypeTsSourceExpander.toTypeString(type));
            }
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
            final List<String> argSourceLines) {
        if (cgInterface.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }

        for (BlancoCgField cgField : cgInterface.getFieldList()) {
            new BlancoCgFieldTsSourceExpander().transformField(cgField,
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
            final List<String> argSourceLines) {
        if (cgInterface.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of method.");
        }
        for (BlancoCgMethod cgMethod : cgInterface.getMethodList()) {
            new BlancoCgMethodTsSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, true);
        }
    }
}

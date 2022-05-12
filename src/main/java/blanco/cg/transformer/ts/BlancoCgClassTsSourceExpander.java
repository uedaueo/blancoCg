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

import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgClass into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgClassTsSourceExpander {

    /**
     * Expands the class here.
     *
     * @param cgClass
     *            A class to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformClass(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines) {
        // Check class style or not
        boolean isClassStyle = !cgClass.getNoClassDeclare();
        if (isClassStyle) {
            // First, it expands the class information into a LangDoc.
            if (cgClass.getLangDoc() == null) {
                // If LangDoc is not specified, creates an instance here.
                cgClass.setLangDoc(new BlancoCgLangDoc());
            }
            if (cgClass.getLangDoc().getTitle() == null) {
                cgClass.getLangDoc().setTitle(cgClass.getDescription());
            }

            // Next, it expands LangDoc into source code format.
            new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgClass
                    .getLangDoc(), argSourceLines);

            // Expands annotations.
            expandAnnotationList(cgClass, argSourceLines);

            final StringBuffer buf = new StringBuffer();
            if (BlancoStringUtil.null2Blank(cgClass.getAccess()).length() > 0) {
                /*
                 * In TypeScript, it defaults public.
                 * If "public" is specified, replaces it with "export".
                 * Since blanco expects one class per file, and class is assumed to be named, we will not use export default.
                 * by tueda, 2020/03/11
                 *
                 * Note: default is required in the vue component, so if default is specified, export default will be used.
                 * by tueda, 2020/06/17
                 */
                if ("public".equals(cgClass.getAccess())) {
                    buf.append("export ");
                } else if ("default".equals(cgClass.getAccess())) {
                    buf.append("export default ");
                }
            }
            if (cgClass.getAbstract()) {
                buf.append("abstract ");
            }

            // TypeScript does not seem to have a final class.
            buf.append("class " + cgClass.getName());

            // Expands a parent class.
            expandExtendClassList(cgClass, argSourceFile, buf);

            // Expands a parent interface.
            expandImplementInterfaceList(cgClass, argSourceFile, buf);

            // The start of a class block.
            buf.append(" {");

            // Finalizes the line and performs the export.
            argSourceLines.add(buf.toString());
        }

        // Expands PlainText here.
        expandPlainText(cgClass, argSourceLines);

        // Enumeration is not subject to auto-generation for the time being. (tueda)

        // Expands the field here.
        expandFieldList(cgClass, argSourceFile, argSourceLines);

        // Expands the method here.
        expandMethodList(cgClass, argSourceFile, argSourceLines);

        // The end of a class block.
        if (isClassStyle) {
            argSourceLines.add("}");
        }
    }

    /**
     * Expands Plain Text.
     *
     * @param cgClass
     * @param argSourceLines
     */
    private void expandPlainText(
            final BlancoCgClass cgClass,
            final List<String> argSourceLines) {
        List<String> plainTextList = cgClass.getPlainTextList();

        // Adds a line break inevitably.
        argSourceLines.add("");

        for (String planText : plainTextList) {
            argSourceLines.add(planText);
        }
    }

    /**
     * Expands annotations.
     *
     * @param cgClass
     *            The class.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgClass cgClass,
            final List<String> argSourceLines) {
        for (String strAnnotation : cgClass.getAnnotationList()) {
            // Annotation in Java is written starting with "@".
            argSourceLines.add("@" + strAnnotation);
        }
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
            // Annotation in Java is written starting with "@".
            // All annotations to constructor arguments should be newline.
            argSourceLines.add("    @" + strAnnotation);
        }
    }

    /**
     * Expands the parent class.
     *
     * Note: This method must not be called as a common process during BlancoCgInterface expansion.
     * We believe that this commonization will hinder understanding.
     *
     * @param cgClass
     *            A value object of the class.
     * @param argBuf
     *            Output string buffer.
     */
    private void expandExtendClassList(final BlancoCgClass cgClass,
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
                throw new IllegalArgumentException("In TypeScript, inheritance can only be performed once.");
            }
        }
        return;
    }

    /**
     * Expands the parent interface.
     *  @param cgClass
     *            The class being processed.
     * @param argBuf
     */
    private void expandImplementInterfaceList(final BlancoCgClass cgClass,
                                              final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        for (int index = 0; index < cgClass.getImplementInterfaceList().size(); index++) {
            final BlancoCgType type = cgClass.getImplementInterfaceList().get(
                    index);

            /*
             * Adds a type to the import statement.
             * We assume that the type is specified in Java format ("." delimited) here.
             * At the time of Transform, the package part is interpreted as information of the location of the definition file, and an import statement is created.
             */
            argSourceFile.getImportList().add(type.getName());

            if (index == 0) {
                argBuf.append(" implements ");
            } else {
                argBuf.append(", ");
            }
            argBuf.append(BlancoCgTypeTsSourceExpander.toTypeString(type));
        }
    }

    /**
     * Expands each field contained in the class.
     *
     * TODO: It is necessary to give priority to constant declarations, and then expands variable declarations.<br>
     * Currently, the source code is expanded in the order of registration.
     *
     * @param cgClass
     *            The class being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandFieldList(final BlancoCgClass cgClass,
                                 final BlancoCgSourceFile argSourceFile,
                                 final List<java.lang.String> argSourceLines) {
        if (cgClass.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }

        for (BlancoCgField cgField : cgClass.getFieldList()) {
            // Expands as a field of the class.
            new BlancoCgFieldTsSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, false);
        }
    }

    /**
     * Expands each method contained in the class.
     *
     * @param cgClass
     *            The class being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandMethodList(final BlancoCgClass cgClass,
                                  final BlancoCgSourceFile argSourceFile,
                                  final List<java.lang.String> argSourceLines) {
        if (cgClass.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of methods.");
        }
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // Expands as a method of the class.
            new BlancoCgMethodTsSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, false);
        }
    }
}

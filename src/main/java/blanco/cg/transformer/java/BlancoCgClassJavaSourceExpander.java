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

import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgClass into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgClassJavaSourceExpander {

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
            final List<java.lang.String> argSourceLines) {
        // First, it expands the class information into a LangDoc.
        if (cgClass.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            cgClass.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgClass.getLangDoc().getTitle() == null) {
            cgClass.getLangDoc().setTitle(cgClass.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocJavaSourceExpander().transformLangDoc(cgClass
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgClass, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgClass.getAccess()).length() > 0) {
            buf.append(cgClass.getAccess() + " ");
        }
        if (cgClass.getAbstract()) {
            buf.append("abstract ");
        }
        if (cgClass.getFinal()) {
            buf.append("final ");
        }
        buf.append("class " + cgClass.getName());

        // Expands the Generic of the class.
        if (cgClass.getGenerics() != null && cgClass.getGenerics().length() > 0) {
            buf.append("<" + cgClass.getGenerics() + ">");
        }

        // Expands a parent class.
        expandExtendClassList(cgClass, argSourceFile, buf);

        // Expands a parent interface.
        expandImplementInterfaceList(cgClass, argSourceFile, buf);

        // The start of a class block.
        buf.append(" {");

        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());

        // Expands the PlainText here.
        expandPlainText(cgClass, argSourceLines);

        // Expands the enumeration here.
        expandEnumList(cgClass, argSourceFile, argSourceLines);

        // Expands the field here.
        expandFieldList(cgClass, argSourceFile, argSourceLines);

        // Expands the method here.
        expandMethodList(cgClass, argSourceFile, argSourceLines);

        // The end of a class block.
        argSourceLines.add("}");
    }

    /**
     * Expands annotations.
     * 
     * @param cgClass
     *            A class.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgClass cgClass,
            final List<java.lang.String> argSourceLines) {
        for (String strAnnotation : cgClass.getAnnotationList()) {
            // Annotations in Java are written starting with "@".
            argSourceLines.add("@" + strAnnotation);
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
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getExtendClassList().get(index));
            cgClass.getExtendClassList().set(index, type);

            // Adds a type to the import statement.
            BlancoCgSourceFileJavaSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" extends "
                        + BlancoCgTypeJavaSourceExpander.toTypeString(type));
            } else {
                throw new IllegalArgumentException("In Java, inheritance can only be performed once.");
            }
        }
    }

    /**
     * Expands the parent interface.
     * 
     * @param cgClass
     *            The class being processed.
     * @param argBuf
     *            Output string buffer.
     */
    private void expandImplementInterfaceList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        for (int index = 0; index < cgClass.getImplementInterfaceList().size(); index++) {
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getImplementInterfaceList().get(index));
            cgClass.getImplementInterfaceList().set(index, type);

            // Adds a type to the import statement.
            BlancoCgSourceFileJavaSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" implements ");
            } else {
                argBuf.append(", ");
            }
            argBuf.append(BlancoCgTypeJavaSourceExpander.toTypeString(type));
        }
    }

    /**
     * Expands each enumeration contained in the class.
     * 
     * @param cgClass
     *            The class being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandEnumList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getEnumList() == null) {
            return;
        }

        for (BlancoCgEnum cgEnum : cgClass.getEnumList()) {
            new BlancoCgEnumJavaSourceExpander().transformEnum(cgEnum,
                    argSourceFile, argSourceLines);
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
            new BlancoCgFieldJavaSourceExpander().transformField(cgField,
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
            throw new IllegalArgumentException("A null was given for the list of method.");
        }
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // Expands as a method of the class.
            new BlancoCgMethodJavaSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, false);
        }
    }

    /**
     * Expands the Plain Text.
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

}

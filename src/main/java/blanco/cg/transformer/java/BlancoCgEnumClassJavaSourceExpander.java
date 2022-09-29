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

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgClass into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 * @author tueda
 */
class BlancoCgEnumClassJavaSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JAVA;

    /**
     * Expands the class here.
     *
     * @param cgEnumClass
     *            A class to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformEnumClass(final BlancoCgEnum cgEnumClass,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines) {
        // First, it expands the class information into a LangDoc.
        if (cgEnumClass.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            cgEnumClass.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgEnumClass.getLangDoc().getTitle() == null) {
            cgEnumClass.getLangDoc().setTitle(cgEnumClass.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocJavaSourceExpander().transformLangDoc(cgEnumClass
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        // Not support annotations yet.

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgEnumClass.getAccess()).length() > 0) {
            buf.append(cgEnumClass.getAccess() + " ");
        }
        buf.append("enum " + cgEnumClass.getName());

        // No Generics support on enum class

        // No extends suported by enum

        // No implements support on enum class

        // The start of a class block.
        buf.append(" {");

        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());

        // Expands the field and method here.
        expandElementList(cgEnumClass, argSourceFile, argSourceLines);

        // Expands the constructor.
        expandConstructorAndField(cgEnumClass, argSourceFile, argSourceLines);

        // The end of a class block.
        argSourceLines.add("}");
    }

    /**
     * Expands each element contained in the enum.
     *
     * @param cgEnum
     *            The enum being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandElementList(
            final BlancoCgEnum cgEnum,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines) {
        if (cgEnum.getElementList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("At least 1 element is required.");
        }

        boolean hasConstructors = true;
        List<BlancoCgField> constructorArgs = cgEnum.getConstructorArgList();
        if (constructorArgs == null || constructorArgs.size() <= 0) {
            hasConstructors = false;
        }

        // expands the element list.
        boolean isFirst = true;
        StringBuffer buf = new StringBuffer();
        for (BlancoCgEnumElement element : cgEnum.getElementList()) {
            if (isFirst) {
                isFirst = false;
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

            if (hasConstructors) {
                buf.append("(" + element.getDefault() + ")");
            }
        }
        if (!isFirst) {
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        }
        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);
    }

    private void expandConstructorAndField(
            final BlancoCgEnum cgEnumClass,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines) {
        List<BlancoCgField> constructorArgs = cgEnumClass.getConstructorArgList();
        if (constructorArgs == null || constructorArgs.size() <= 0) {
            return;
        }

        /* field */
        for (BlancoCgField cgField : constructorArgs) {
            // Expands as a field of the class.
            new BlancoCgFieldJavaSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, false);
        }

        /* constructor */
        if (cgEnumClass.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of method.");
        }
        for (BlancoCgMethod cgMethod : cgEnumClass.getMethodList()) {
            if (!cgMethod.getConstructor()) {
                return;
            }
            // Expands as a method of the class.
            new BlancoCgMethodJavaSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, false);
        }
    }

    /**
     * Expands each field contained in the class.
     *
     * TODO: It is necessary to give priority to constant declarations, and then expands variable declarations.<br>
     * Currently, the source code is expanded in the order of registration.
     *
     * @param cgEnumClass
     *            The class being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandFieldList(final BlancoCgEnum cgEnumClass,
                                 final BlancoCgSourceFile argSourceFile,
                                 final List<java.lang.String> argSourceLines) {
    }
}

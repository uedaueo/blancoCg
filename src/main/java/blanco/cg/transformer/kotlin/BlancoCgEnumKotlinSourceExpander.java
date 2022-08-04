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
package blanco.cg.transformer.kotlin;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * Expands BlancoCgEnum into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgEnumKotlinSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.KOTLIN;

    /**
     * Expands the Enum here.
     *
     * @param argCgEnum
     *            An Enum to be processed.
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
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(argCgEnum
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        // Not support annotations yet.

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(argCgEnum.getAccess()).length() > 0) {
            // In Kotlin, it defaults public.
            if (!"public".equals(argCgEnum.getAccess())) {
                buf.append(argCgEnum.getAccess() + " ");
            }
        }
        buf.append("enum class " + argCgEnum.getName());

        // Expands the primary constructor.
        /*
         * In Kotlin, the primary constructor is written as part of the class definition.
         */
        expandPrimaryConstructorList(argCgEnum, argSourceFile, buf, argSourceLines);

        // The start of a class block.
        buf.append(" {");

        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());

        // Expands the field and method here.
        expandElementList(argCgEnum, argSourceFile, argSourceLines);

        argSourceLines.add("}");
    }

    /**
     * Expands the primary constructor of Kotlin.
     *
     * @param cgEnum
     *            A value object of the class.
     * @param argSourceFile
     *            Source file.
     * @param argBuf
     *            Output string buffer.
     */
    private void expandPrimaryConstructorList(
            final BlancoCgEnum cgEnum,
            final BlancoCgSourceFile argSourceFile,
            final StringBuffer argBuf,
            final List<String> argSourceLines) {
        List<BlancoCgField> constructorArgs = cgEnum.getConstructorArgList();
        if (constructorArgs == null || constructorArgs.size() <= 0) {
            return;
        }

        argBuf.append(" constructor (");

        // If the Constractor specification exists, a new line is created here.
        argSourceLines.add(argBuf.toString());
        argBuf.delete(0, argBuf.length());

        int count = 0;
        for (BlancoCgField constField : constructorArgs) {
            final BlancoCgType type = constField.getType();
            // Adds a type to the import statement.
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, type.getName())) {
                argSourceFile.getImportList().add(type.getName());
            }

            if (count != 0) {
                argBuf.append(",");
                argSourceLines.add(argBuf.toString());
                argBuf.delete(0, argBuf.length());
            }

        // First, it expands the class information into a LangDoc.
        if (constField.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
                constField.setLangDoc(new BlancoCgLangDoc());
            }
            if (constField.getLangDoc().getTitle() == null) {
                constField.getLangDoc().setTitle(constField.getDescription());
            }

            // Next, it expands LangDoc into source code format.
            new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(constField.getLangDoc(), argSourceLines);

            // Expands annotations.
            BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, constField.getAnnotationList(), argSourceLines);

            // In Kotlin, it defaults final.
            // However, if override is specified, it must be specified as final.
            argBuf.append("    ");
            if (constField.getOverride()) {
                if (constField.getFinal()) {
                    argBuf.append("final ");
                }
                argBuf.append("override "); // "override" is open by default.
            } else if (!constField.getFinal()) {
                argBuf.append("open ");
            }

            // Sets whether the variable is modifiable or not.
            // Since it is a constructor argument, I think val is usually sufficient. (tueda)
            if (constField.getConst()) {
                argBuf.append("val ");
            } else {
                argBuf.append("var ");
            }
            argBuf.append(constField.getName() + " : " + BlancoCgTypeKotlinSourceExpander.toTypeString(type));
            if (!constField.getNotnull()) {
                // nullable
                argBuf.append("?");
            }

            // If a default value is specified, this will be expanded.
            if (BlancoStringUtil.null2Blank(constField.getDefault()).length() > 0) {
                argBuf.append(" = " + constField.getDefault());
            }
            count++;
        }
        if (argBuf.length() > 0) {
            argSourceLines.add(argBuf.toString());
            argBuf.delete(0, argBuf.length());
        }
        argBuf.append(")");
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
        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());
        buf.setLength(0);
    }
}

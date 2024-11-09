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

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Expands BlancoCgMethod into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgMethodTsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.TS;

    /**
     * Expands a method here.
     *
     * @param cgMethod
     *            The method to be processed.
     * @param argSourceFile
     *            A source file.
     * @param argSourceLines
     *            List of lines to output.
     * @param argIsInterface
     *            Whether it is an instance or not. False for a class, true for an interface.
     */
    public void transformMethod(final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines,
            final boolean argIsInterface) {
        if (BlancoStringUtil.null2Blank(cgMethod.getName()).length() == 0) {
            throw new IllegalArgumentException("The method name is not set to an appropriate value.");
        }
        if (cgMethod.getReturn() == null) {
            // It is possible; null is specified in the case of void.
        }

        // Adds a line break.
        argSourceLines.add("");

        prepareExpand(cgMethod, argSourceFile);

        // Now that we have a complete set of information, performs the actual expansion of the source code.

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgMethod
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgMethod, argSourceLines);

        // Expands the body part of the method.
        expandMethodBody(cgMethod, argSourceLines, argIsInterface, argSourceFile);
    }

    /**
     * Before source code expansion, gathers the necessary information.
     *
     * @param cgMethod
     *            A method object.
     * @param argSourceFile
     *            A source file.
     */
    private void prepareExpand(final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile) {
        // First, it expands the method information into LangDoc.
        if (cgMethod.getLangDoc() == null) {
            // Creates an instance here if LangDoc is not specified.
            cgMethod.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgMethod.getLangDoc().getParameterList() == null) {
            cgMethod.getLangDoc().setParameterList(
                    new ArrayList<BlancoCgParameter>());
        }
        if (cgMethod.getLangDoc().getThrowList() == null) {
            cgMethod.getLangDoc().setThrowList(
                    new ArrayList<BlancoCgException>());
        }
        if (cgMethod.getLangDoc().getTitle() == null) {
            cgMethod.getLangDoc().setTitle(cgMethod.getDescription());
        }

        for (BlancoCgParameter cgParameter : cgMethod.getParameterList()) {
            // Adds a type to the import statement.
            argSourceFile.getImportList().add(cgParameter.getType().getName());

            // Adds a parameter to the language document.
            cgMethod.getLangDoc().getParameterList().add(cgParameter);
        }

        if (cgMethod.getReturn() != null) {
            // Adds a type to the import statement.
            argSourceFile.getImportList().add(
                    cgMethod.getReturn().getType().getName());

            // Adds return to the language document.
            cgMethod.getLangDoc().setReturn(cgMethod.getReturn());
        }

        // Expands to LangDoc structure for exceptions.
        for (BlancoCgException cgException : cgMethod.getThrowList()) {
            // Adds a type to the import statement.
            argSourceFile.getImportList().add(cgException.getType().getName());

            // Adds an exception to the language document.
            cgMethod.getLangDoc().getThrowList().add(cgException);
        }
    }

    /**
     * Expands the body part of the method.
     *
     * @param cgMethod
     *            A method object.
     * @param argSourceLines
     *            Source code.
     * @param argIsInterface
     *            Whether it is an instance or not.
     */
    private void expandMethodBody(
            final BlancoCgMethod cgMethod,
            final List<String> argSourceLines,
            final boolean argIsInterface,
            final BlancoCgSourceFile argSourceFile) {
        boolean isSetter = false;
        final StringBuffer buf = new StringBuffer();

        if (cgMethod.getAbstract() && argIsInterface == false) {
            // Note: "abstract" is not given in the case of interface.
            buf.append("abstract ");
        }
        if (cgMethod.getStatic()) {
            buf.append("static ");
        }
        if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
            // In TypeScript, it defaults public.
            if (!cgMethod.getAccess().equals("public")) {
                buf.append(cgMethod.getAccess() + " ");
                if (cgMethod.getAccess().equals("set")) {
                    isSetter = true;
                }
            }
        }

        buf.append(cgMethod.getName() + "(");
        for (int index = 0; index < cgMethod.getParameterList().size(); index++) {
            final BlancoCgParameter cgParameter = cgMethod
                    .getParameterList().get(index);
            if (cgParameter.getType() == null) {
                throw new IllegalArgumentException("The parameter [" + cgParameter.getName()
                        + "] of the method [" + cgMethod.getName()
                        + "] has been given a null.");
            }

            if (index != 0) {
                buf.append(", ");
            }

            buf.append(cgParameter.getName() + ": ");

            buf.append(BlancoCgTypeTsSourceExpander
                    .toTypeString(cgParameter.getType()));

            /*
             * TypeScript allows multiple types, but does not support them at this time.
             * Adds undefiend for Nullable.
             * by tueda, 2020/03/15
             */
            if (!cgParameter.getNotnull()) {
                // nullable
                buf.append(" | undefined");
                if (argSourceFile.getIsStrictNullable()) {
                    buf.append(" | null");
                }
            }

            // If a default value is specified, this will be expanded.
            if (BlancoStringUtil.null2Blank(cgParameter.getDefault()).length() > 0) {
                buf.append(" = " + cgParameter.getDefault());
            }
        }
        buf.append(")");

        if (cgMethod.getConstructor()) {
                // In the case of constructor, there is no return value.
                // For this reason, it will not output anything here.
        } else {
            if (cgMethod.getReturn() != null
                    && cgMethod.getReturn().getType() != null) {
                buf.append(": " + BlancoCgTypeTsSourceExpander.toTypeString(cgMethod
                        .getReturn().getType()));
                /*
                 * TypeScript allows multiple types, but does not support them at this time.
                 * Adds undefiend for Nullable.
                 * by tueda, 2020/03/15
                 */
                if (!cgMethod.getNotnull()) {
                    // nullable
                    buf.append(" | undefined");
                    if (argSourceFile.getIsStrictNullable()) {
                        buf.append(" | null");
                    }
                }
            } else {
                /*
                 * void cannot be specified for Setter.
                 */
                if (!isSetter) {
                    buf.append(": void");
                }
            }
        }

        // The throws clause is not necessary in TypeScript.

        if (cgMethod.getAbstract() || argIsInterface) {
            // In the case of an abstract method or interface, the body of the method is not expanded.
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
            argSourceLines.add(buf.toString());
        } else {
            // The start of a method block.
            buf.append(" {");

            // Fixes the line.
            argSourceLines.add(buf.toString());

            // Expands parent class method execution function.
            if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation())
                    .length() > 0) {
                // This includes super(argument), etc.
                argSourceLines.add(cgMethod.getSuperclassInvocation()
                        + BlancoCgLineUtil.getTerminator(TARGET_LANG));
            }

            // In TypeScript, non-null constraints on parameters are checked at compiling, so there is no need to implement exception handling.

            // Expands a line.
            expandLineList(cgMethod, argSourceLines);

            // The end of a method block.
            argSourceLines.add("}");
        }
    }

    /**
     * Expands annotations.
     *
     * @param cgMethod
     *            The method.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgMethod cgMethod,
            final List<String> argSourceLines) {

        for (String strAnnotation : cgMethod.getAnnotationList()) {
            // Annotation in Java is written starting with "@".
            argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * Expands the line.
     *
     * @param cgMethod
     *            Method information.
     * @param argSourceLines
     *            List of output lines.
     */
    private void expandLineList(final BlancoCgMethod cgMethod,
            final List<String> argSourceLines) {
        for (String strLine : cgMethod.getLineList()) {
            argSourceLines.add(strLine);
        }
    }
}

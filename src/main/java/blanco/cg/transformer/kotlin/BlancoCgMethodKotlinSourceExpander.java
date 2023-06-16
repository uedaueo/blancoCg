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

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
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
class BlancoCgMethodKotlinSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.KOTLIN;

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
            final List<java.lang.String> argSourceLines,
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
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgMethod
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, cgMethod.getAnnotationList(), argSourceLines);

        // Expands the body part of the method.
        expandMethodBody(cgMethod, argSourceLines, argIsInterface);
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
                    new ArrayList<blanco.cg.valueobject.BlancoCgParameter>());
        }
        if (cgMethod.getLangDoc().getThrowList() == null) {
            cgMethod.getLangDoc().setThrowList(
                    new ArrayList<blanco.cg.valueobject.BlancoCgException>());
        }
        if (cgMethod.getLangDoc().getTitle() == null) {
            cgMethod.getLangDoc().setTitle(cgMethod.getDescription());
        }

        for (BlancoCgParameter cgParameter : cgMethod.getParameterList()) {
            // Converts generics to a genericsListTree if available.
            BlancoCgType cgType = BlancoCgSourceUtil.parseTypeWithGenerics(cgParameter.getType());
            cgParameter.setType(cgType);

            // Adds a type to the import statement.
            BlancoCgSourceFileKotlinSourceExpander.typeToImport(cgType, argSourceFile);

            // Adds a parameter to the language document.
            cgMethod.getLangDoc().getParameterList().add(cgParameter);
        }

        if (cgMethod.getReturn() != null) {
            BlancoCgType cgType = BlancoCgSourceUtil.parseTypeWithGenerics(cgMethod.getReturn().getType());
            cgMethod.getReturn().setType(cgType);

            // Adds a type to the import statement.
            BlancoCgSourceFileKotlinSourceExpander.typeToImport(cgType, argSourceFile);

            // Adds return to the language document.
            cgMethod.getLangDoc().setReturn(cgMethod.getReturn());
        }

        // Expands to LangDoc structure for exceptions.
        for (BlancoCgException cgException : cgMethod.getThrowList()) {
            // Adds a type to the import statement.
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, cgException.getType().getName())) {
                argSourceFile.getImportList().add(cgException.getType().getName());
            }

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
    private void expandMethodBody(final BlancoCgMethod cgMethod,
            final List<java.lang.String> argSourceLines,
            final boolean argIsInterface) {
        final StringBuffer buf = new StringBuffer();

        // In the case of static initializer, no modifier is attached.
        if (!cgMethod.getStaticInitializer()) {
            if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
                // In Kotlin, it defaults public.
                if (!cgMethod.getAccess().equals("public")) {
                    if (cgMethod.getStaticInitializer() == false) {
                        buf.append(cgMethod.getAccess() + " ");
                    }
                }
            }

            if (cgMethod.getOverride()) {
                // To override a method of the parent class, the modifier "override" is required.
                buf.append("override ");
            }

            if (cgMethod.getAbstract() && argIsInterface == false) {
                // Note: "abstract" is not given in the case of interface.
                buf.append("abstract ");
            }
            // In Kotlin, methods of normal classes are "final" by default.
            if (!cgMethod.getFinal() && argIsInterface == false) {
                // Note: The default for the interface is open.
                buf.append("open ");
            }

            buf.append("fun ");

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

                /*
                 * Handles parameter annotations.
                 */
                List<String> paramAnn = new ArrayList<>();
                BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, cgParameter.getAnnotationList(), paramAnn);
                String LF = System.getProperty("line.separator", "\n");
                for (String ann : paramAnn) {
                    buf.append(ann + LF);
                }

                buf.append(cgParameter.getName() + " : ");

                buf.append(BlancoCgTypeKotlinSourceExpander
                        .toTypeString(cgParameter.getType()));
                if (!cgParameter.getNotnull()) {
                    // nullable
                    buf.append("?");
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
                    buf.append(" : " + BlancoCgTypeKotlinSourceExpander.toTypeString(cgMethod
                            .getReturn().getType()));
                    if (cgMethod.getReturn().getNullable()) {
                        buf.append("?");
                    };
                }
            }
        } else {
            // static initializer
            buf.append("init");
        }

        // The throws clause is not necessary in Kotlin.

        if (cgMethod.getAbstract()) {
            // In the case of an abstract method or interface, the body of the method is not expanded.
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
            argSourceLines.add(buf.toString());
        } else {
            // If there is no method body, it does not add "{}".
            boolean hasBody = false;
            if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation()).length() > 0 ||
                    cgMethod.getLineList().size() > 0
            ) {
                // The start of a method block.
                buf.append(" {");
                hasBody = true;
            }

            // Fixes the line.
            argSourceLines.add(buf.toString());

            // Expands parent class method execution function.
            if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation())
                    .length() > 0) {
                // This includes super(argument), etc.
                argSourceLines.add(cgMethod.getSuperclassInvocation()
                        + BlancoCgLineUtil.getTerminator(TARGET_LANG));
            }

            // In Kotlin, non-null constraints on parameters are checked at compiling, so there is no need to implement exception handling.

            // Expands a line.
            expandLineList(cgMethod, argSourceLines);

            if (hasBody) {
                // The end of a method block.
                argSourceLines.add("}");
            }
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
            final List<java.lang.String> argSourceLines) {
        for (String strLine : cgMethod.getLineList()) {
            argSourceLines.add(strLine);
        }
    }
}

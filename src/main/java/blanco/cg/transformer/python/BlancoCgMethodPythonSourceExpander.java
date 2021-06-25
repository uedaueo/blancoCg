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
package blanco.cg.transformer.python;

import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgException;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgMethod into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgMethodPythonSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.PYTHON;

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

        // Expands annotations.
        expandAnnotationList(cgMethod, argSourceLines);

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

        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

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

        // Adds self as the first parameter.
        // This is a specification of Python.
        // No input check is performed for the parameter self.

        // Adds self to the beginning of the parameter.
        cgMethod.getParameterList().add(0,
                cgFactory.createParameter("self", "", "The class itself that contains this method."));

        for (int indexParameter = 0; indexParameter < cgMethod
                .getParameterList().size(); indexParameter++) {
            final BlancoCgParameter cgParameter = cgMethod.getParameterList()
                    .get(indexParameter);

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
        for (int index = 0; index < cgMethod.getThrowList().size(); index++) {
            final BlancoCgException cgException = cgMethod.getThrowList().get(
                    index);

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
    private void expandMethodBody(final BlancoCgMethod cgMethod,
            final List<java.lang.String> argSourceLines,
            final boolean argIsInterface) {
        final StringBuffer buf = new StringBuffer();

        // TODO: How does scope work in Python?
        // if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
        // if (argIsInterface && cgMethod.getAccess().equals("public")) {
        // // If it's an interface and public, the output is suppressed.
        // // The output is suppressed in C# as well as in Java.
        // } else {
        // buf.append(cgMethod.getAccess() + " ");
        // }
        // }
        //
        // if (cgMethod.getAbstract() && argIsInterface == false) {
        // // Note: "abstract" is not given in the case of interface.
        // buf.append("abstract ");
        // }
        // if (cgMethod.getStatic()) {
        // buf.append("static ");
        // }
        // if (cgMethod.getFinal() && argIsInterface == false) {
        // // Note: "final" is not given in the case of interface.
        // buf.append("final ");
        // }

        // TODO: What is the type of the return value?
        // if (cgMethod.getConstructor()) {
        // // In the case of the constructor, there is no return value.
        // // For this reason, it will not output anything here.
        // } else {
        // if (cgMethod.getReturn() != null
        // && cgMethod.getReturn().getType() != null) {
        // buf.append(BlancoCgTypeJavaSourceExpander.toTypeString(cgMethod
        // .getReturn().getType())
        // + " ");
        // } else {
        // buf.append("void ");
        // }
        // }

        // Starting of a method. The definition of a method starts with def.
        buf.append("def ");

        buf.append(cgMethod.getName() + "(");
        for (int index = 0; index < cgMethod.getParameterList().size(); index++) {
            final BlancoCgParameter cgParameter = cgMethod.getParameterList()
                    .get(index);
            if (cgParameter.getType() == null) {
                throw new IllegalArgumentException("The parameter [" + cgParameter.getName()
                        + "] of the method [" + cgMethod.getName()
                        + "] has been given a null.");
            }

            if (index != 0) {
                buf.append(", ");
            }

            // if (cgParameter.getFinal()) {
            // buf.append("final ");
            // }
            // buf.append(BlancoCgTypeJavaSourceExpander.toTypeString(cgParameter
            // .getType()));
            // buf.append(" ");
            buf.append(cgParameter.getName());
        }
        buf.append("):");

        // Expands the exception throw.
        expandThrowList(cgMethod, buf);

        // if (cgMethod.getAbstract() || argIsInterface) {
        // // In the case of an abstract method or interface, the body of the method is not expanded.
        // buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        // argSourceLines.add(buf.toString());
        // } else {

        // // The start of a method block.
        // buf.append(" {");

        // Fixes the line.
        argSourceLines.add(buf.toString());

        // Next, expands LangDoc into source code format.
        new BlancoCgLangDocPythonSourceExpander().transformLangDoc(cgMethod
                .getLangDoc(), argSourceLines);

        // Expands parent class method execution function.
        if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation())
                .length() > 0) {
            // This includes super(argument), etc.
            argSourceLines.add(cgMethod.getSuperclassInvocation()
                    + BlancoCgLineUtil.getTerminator(TARGET_LANG));
        }

        // Expands non-null constraints on parameters.
        expandParameterCheck(cgMethod, argSourceLines);

        // Expands a line.
        expandLineList(cgMethod, argSourceLines);

        // The end of a method block.
        argSourceLines.add("#end");

    }

    /**
     * Expands the exception throw.
     * 
     * @param cgMethod
     *            A method.
     * @param buf
     *            Output buffer.
     */
    private void expandThrowList(final BlancoCgMethod cgMethod,
            final StringBuffer buf) {
        for (int index = 0; index < cgMethod.getThrowList().size(); index++) {
            final BlancoCgException cgException = cgMethod.getThrowList().get(
                    index);
            if (index == 0) {
                buf.append(" throws ");
            } else {
                buf.append(", ");
            }
            // For language document processing, common processing for Type of blancoCg cannot be used. 
            // Describes individually.
            buf.append(BlancoNameUtil.trimJavaPackage(cgException.getType()
                    .getName()));
        }
    }

    /**
     * Expands annotations.
     * 
     * @param cgMethod
     *            A method.
     * @param argSourceLines
     *            Source code.
     */
    private void expandAnnotationList(final BlancoCgMethod cgMethod,
            final List<java.lang.String> argSourceLines) {
        if (cgMethod.getOverride()) {
            // In Java, override is represented by the annotation.
            argSourceLines.add("@Override");
        }

        for (int index = 0; index < cgMethod.getAnnotationList().size(); index++) {
            final String strAnnotation = cgMethod.getAnnotationList()
                    .get(index);

            // Annotasion in Java is written starting with @.
            argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * Expands non-null constraints on parameters.
     * 
     * @param cgMethod
     *            A method.
     * @param argSourceLines
     *            Source code.
     */
    private void expandParameterCheck(final BlancoCgMethod cgMethod,
            final List<java.lang.String> argSourceLines) {
        boolean isProcessed = false;
        for (int index = 0; index < cgMethod.getParameterList().size(); index++) {
            final BlancoCgParameter cgParameter = cgMethod.getParameterList()
                    .get(index);
            if (cgParameter.getNotnull()) {
                isProcessed = true;

                argSourceLines.add(BlancoCgLineUtil.getIfBegin(TARGET_LANG,
                        cgParameter.getName() + " is None"));
                argSourceLines.add("raise ValueError, \"The parameter ["
                        + cgParameter.getName() + "] of the method ["
                        + cgMethod.getName() + "] has been given null. However, null cannot be given to this parameter.\"");
                argSourceLines.add(BlancoCgLineUtil.getIfEnd(TARGET_LANG));
            }
        }

        if (isProcessed) {
            // Inserts a blank line if the parameter check is expanded.
            argSourceLines.add("");
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
        for (int indexLine = 0; indexLine < cgMethod.getLineList().size(); indexLine++) {
            final String strLine = cgMethod.getLineList().get(indexLine);
            argSourceLines.add(strLine);
        }
    }
}

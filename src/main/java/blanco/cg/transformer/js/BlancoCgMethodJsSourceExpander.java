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
package blanco.cg.transformer.js;

import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgException;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgMethod into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgMethodJsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JS;

    /**
     * Expands a method here.
     * 
     * @param cgMethod
     *            The method to be processed.
     * @param argSourceFile
     *            A source file.
     * @param argSourceLines
     *            List of lines to output.
     */
    public void transformMethod(final BlancoCgClass cgClass,
            final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (BlancoStringUtil.null2Blank(cgMethod.getName()).length() == 0) {
            throw new IllegalArgumentException("The method name is not set to an appropriate value.");
        }
        if (cgMethod.getReturn() == null) {
            // It is possible; null is specified in the case of void.
        }

        // Adds a line break.
        argSourceLines.add("");

        prepareExpand(cgClass, cgMethod, argSourceFile);

        // Now that we have a complete set of information, performs the actual expansion of the source code.

        // Next, expands LangDoc into source code format.
        new BlancoCgLangDocJsSourceExpander().transformLangDoc(cgMethod
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgMethod, argSourceLines);

        // Expands the body part of the method.
        expandMethodBody(cgClass, cgMethod, argSourceFile, argSourceLines);
    }

    /**
     * Expands the static fields contained in the class.
     * 
     * Currently, the source code is expanded in the order of registration.
     * 
     * @param cgClass
     *            A class to be processed.
     * @param argSourceFile
     *            A source file.
     * @param argSourceLines
     *            Line list of source code.
     */
    public void transformStaticFieldList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }

        for (int index = 0; index < cgClass.getFieldList().size(); index++) {
            final BlancoCgField cgField = cgClass.getFieldList().get(index);

            if (cgField.getStatic()) {
                // Here it will expand only the fields of the class (static fields).
                new BlancoCgFieldJsSourceExpander().transformField(cgClass,
                        cgField, argSourceFile, argSourceLines);
            }
        }
    }

    /**
     * Before source code expansion, gathers the necessary information.
     * 
     * @param cgMethod
     *            A method object.
     * @param argSourceFile
     *            A source file.
     */
    private void prepareExpand(final BlancoCgClass cgClass,
            final BlancoCgMethod cgMethod,
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

        if (cgMethod.getConstructor()) {
            // Expands the function name itself into a LangDoc.
            cgMethod.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            "class", null, cgClass.getDescription()));

            cgMethod.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            "constructor", null, ""));
        } else {
            cgMethod.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            "addon", null, ""));
        }

        if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).equals("private")) {
            cgMethod.getLangDoc().getTagList().add(
                    BlancoCgObjectFactory.getInstance().createLangDocTag(
                            "private", null, ""));
        }

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
     * @param cgClass
     *            A class object.
     * @param cgMethod
     *            A method object.
     * @param argSourceFile
     *            Source code.
     * @param argSourceLines
     *            Whether to expand it as an interface.
     */
    private void expandMethodBody(final BlancoCgClass cgClass,
            final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        final StringBuffer buf = new StringBuffer();

        if (cgMethod.getConstructor()) {
            // In the case of a constructor, it starts with function.
            buf.append("function " + cgClass.getName());
        } else {
            // In the case of a normal method, it is the addition of a function to the prototype of the constructor.
            buf.append(cgClass.getName() + ".prototype." + cgMethod.getName()
                    + " = function");
        }

        // The access flag itself does not exist in JavaScript.
        // It expresses access as a JSDoc description.

        // In JavaScript, it does not output the return value, but only as a JSDoc description.

        buf.append("(");
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

            // The "final" modifier is disabled in JavaScript.

            buf.append("/* "
                    + BlancoCgTypeJsSourceExpander.toTypeString(cgParameter
                            .getType()) + " */");
            buf.append(" ");
            buf.append(cgParameter.getName());
        }
        buf.append(")");

        // Exception throw expansion does not exist in JavaScript.

        if (cgMethod.getAbstract()) {
            // In the case of an abstract method or interface, the body of the method is not expanded.
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
            argSourceLines.add(buf.toString());
        } else {
            // The start of a method block.
            buf.append(" {");

            // Fixes the line.
            argSourceLines.add(buf.toString());

            // Auto-generates argument checks.
            argSourceLines.add("/* Performs number and type checks for parameters. */");
            argSourceLines.add(BlancoCgLineUtil.getIfBegin(TARGET_LANG,
                    "arguments.length !== "
                            + cgMethod.getParameterList().size()));
            argSourceLines
                    .add("throw new Error(\"[ArgumentException]: The number of parameters in "
                            + cgClass.getName()
                            + "."
                            + cgMethod.getName()
                            + " should be ["
                            + cgMethod.getParameterList().size()
                            + "]. But in fact, they were called with [\" + arguments.length +  \"] parameters.\");");
            argSourceLines.add(BlancoCgLineUtil.getIfEnd(TARGET_LANG));

            for (int indexParameter = 0; indexParameter < cgMethod
                    .getParameterList().size(); indexParameter++) {
                final BlancoCgParameter cgParameter = cgMethod
                        .getParameterList().get(indexParameter);
                if (BlancoCgTypeJsSourceExpander
                        .isLanguageReservedKeyword(BlancoStringUtil
                                .null2Blank(cgParameter.getType().getName()))) {
                    argSourceLines.add(BlancoCgLineUtil.getIfBegin(TARGET_LANG,
                            "typeof(" + cgParameter.getName() + ") != \""
                                    + cgParameter.getType().getName() + "\""));
                } else {
                    argSourceLines.add(BlancoCgLineUtil.getIfBegin(TARGET_LANG,
                            cgParameter.getName() + " instanceof "
                                    + cgParameter.getType().getName()
                                    + " == false"));
                }
                argSourceLines.add("throw new Error(\"[ArgumentException]: The parameter of the order "
                        + (indexParameter + 1) + " of "
                        + cgClass.getName() + "." + cgMethod.getName() + " must be of type ["
                        + cgParameter.getType().getName()
                        + "]. But in fact, it was given the type [\" + typeof("
                        + cgParameter.getName() + ") + \"].\");");
                argSourceLines.add(BlancoCgLineUtil.getIfEnd(TARGET_LANG));
            }

            argSourceLines.add("");

            if (cgMethod.getConstructor()) {
                // Expands the field since it is a constructor.
                // In the case of JavaScript, the field expansion exists in the constructor instead of expanding the field in the class.
                expandFieldList(cgClass, argSourceFile, argSourceLines);
            }

            // Expands parent class method execution function.
            if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation())
                    .length() > 0) {
                // This includes super(argument), etc.
                argSourceLines.add(cgMethod.getSuperclassInvocation()
                        + BlancoCgLineUtil.getTerminator(TARGET_LANG));
            }

            // Expands a line.
            expandLineList(cgMethod, argSourceLines);

            // The end of a method block.
            if (cgMethod.getConstructor()) {
                // A semicolon is not given at the end of a class declaration.
                argSourceLines.add("}");
            } else {
                // In JavaScript, a semicolon is given at the end.
                argSourceLines.add("};");
            }
        }
    }

    /**
     * Expands each field contained in the class.
     * 
     * Currently, the source code is expanded in the order of registration.
     * 
     * @param cgClass
     *            The class to be processed.
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

        for (int index = 0; index < cgClass.getFieldList().size(); index++) {
            final BlancoCgField cgField = cgClass.getFieldList().get(index);

            if (cgField.getStatic() == false) {
                // Expands the non-static fields in the constructor.
                new BlancoCgFieldJsSourceExpander().transformField(cgClass,
                        cgField, argSourceFile, argSourceLines);
            }
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
            // The override expression in JavaScript is not supported at this time.
            throw new IllegalArgumentException(
                    "The current version of blancoCg does not support override expressions in JavaScript.");
            // argSourceLines.add("@Override");
        }

        for (int index = 0; index < cgMethod.getAnnotationList().size(); index++) {
            final String strAnnotation = cgMethod.getAnnotationList()
                    .get(index);
            throw new IllegalArgumentException(
                    "The current version of blancoCg does not support annotations in JavaScript."
                            + strAnnotation);
            // Annotation of JavaScript is unknown.
            // argSourceLines.add("@" + strAnnotation);
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

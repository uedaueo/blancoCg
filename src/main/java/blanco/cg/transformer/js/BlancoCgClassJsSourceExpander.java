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

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Expands BlancoCgClass into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgClassJsSourceExpander {

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
        // new BlancoCgLangDocJsSourceExpander().transformLangDoc(cgClass
        // .getLangDoc(), argSourceLines);

        // Expands annotations.
        expandAnnotationList(cgClass, argSourceLines);

        // Checks for the existence of a constructor, or none at all.
        boolean isConstructorExist = false;
        for (int index = 0; index < cgClass.getMethodList().size(); index++) {
            final BlancoCgMethod cgMethod = cgClass.getMethodList().get(index);
            if (cgMethod.getConstructor()) {
                isConstructorExist = true;
                break;
            }
        }
        if (isConstructorExist == false) {
            // If there is no constructor for a class, it is the reponsibility of blancoCg to generate its own default constructor.
            // This is due to a feature of the class structure in the JavaScript specification.
            // Generates a default constructor as a method with empty content.
            final BlancoCgMethod cgMethod = BlancoCgObjectFactory.getInstance()
                    .createMethod(cgClass.getName(), "A default constructor");
            cgMethod.setConstructor(true);
            cgClass.getMethodList().add(cgMethod);
        }

        // Class information is transferred to the constructor of the class.
        // This is due to a feature of the class structure in the JavaScript specification.
        // This is because the declaration part of the class is the constructor itself, so it is necessary to transfer the class information to the constructor.
        for (int index = 0; index < cgClass.getMethodList().size(); index++) {
            final BlancoCgMethod cgMethod = cgClass.getMethodList().get(index);
            if (cgMethod.getConstructor()) {
                // Transfers class information to the constructor.
                // However, the title will not be transfered.
                for (int indexClassLangDoc = 0; indexClassLangDoc < cgClass
                        .getLangDoc().getDescriptionList().size(); indexClassLangDoc++) {
                    cgMethod.getLangDoc().getDescriptionList().add(
                            cgClass.getLangDoc().getDescriptionList().get(
                                    indexClassLangDoc));
                }
                for (int indexClassLangDoc = 0; indexClassLangDoc < cgClass
                        .getLangDoc().getTagList().size(); indexClassLangDoc++) {
                    cgMethod.getLangDoc().getTagList().add(
                            cgClass.getLangDoc().getTagList().get(
                                    indexClassLangDoc));
                }
            }
        }

        // Expands the method here.
        expandMethodList(cgClass, argSourceFile, argSourceLines);

        argSourceLines.add("/* End of class [" + cgClass.getName() + "] declaration. */");
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
        for (int index = 0; index < cgClass.getAnnotationList().size(); index++) {
            final String strAnnotation = cgClass.getAnnotationList().get(index);

            throw new IllegalArgumentException(
                    "The current version of blancoCg does not support annotations in JavaScript."
                            + strAnnotation);
            // Annotation for JavaScript is unknown.
            // argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * Expands each method contained in the class.
     * 
     * @param cgClass
     *            The class to be processed.
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

        // First, expands the constructor.
        for (int index = 0; index < cgClass.getMethodList().size(); index++) {
            final BlancoCgMethod cgMethod = cgClass.getMethodList().get(index);

            if (cgMethod.getConstructor()) {
                // Only the constructor will be expanded first.
                new BlancoCgMethodJsSourceExpander().transformMethod(cgClass,
                        cgMethod, argSourceFile, argSourceLines);
            }
        }

        // Expands class fields (static fields).
        new BlancoCgMethodJsSourceExpander().transformStaticFieldList(cgClass,
                argSourceFile, argSourceLines);

        // Next, expands the general method.
        for (int index = 0; index < cgClass.getMethodList().size(); index++) {
            final BlancoCgMethod cgMethod = cgClass.getMethodList().get(index);

            if (cgMethod.getConstructor() == false) {
                // Expands all but the constructor.
                new BlancoCgMethodJsSourceExpander().transformMethod(cgClass,
                        cgMethod, argSourceFile, argSourceLines);
            }
        }
    }
}

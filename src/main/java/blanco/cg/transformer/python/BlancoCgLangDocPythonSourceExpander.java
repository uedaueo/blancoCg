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

import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgException;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgLangDocTag;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgLangDoc (language document) into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.<br>
 * This is a common process to expand various language documents such as classes, methods, and fields.
 * 
 * @author IGA Tosiki
 */
class BlancoCgLangDocPythonSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.PYTHON;

    /**
     * Expands the source code based on the language document information.
     * 
     * @param langDoc
     *            The language document information.
     * @param argSourceLines
     *            Source code.
     */
    public void transformLangDoc(final BlancoCgLangDoc langDoc,
            final List<java.lang.String> argSourceLines) {
        // argSourceLines.add("\"\"\"");

        // Expands the main body except for the start and end.
        transformLangDocBody(langDoc, argSourceLines);

        argSourceLines.add("\"\"\"");

        // Expands the comment tag.
        transformLangDocCommentTag(langDoc, argSourceLines);
    }

    /**
     * Expands the main body of the language document.
     * 
     * This method is also used from the file header expansion of the source file.
     * 
     * @param langDoc
     * @param argSourceLines
     */
    public void transformLangDocBody(final BlancoCgLangDoc langDoc,
            final List<java.lang.String> argSourceLines) {
        boolean isLangDocTitleStarted = false;

        if (BlancoStringUtil.null2Blank(langDoc.getTitle()).length() > 0) {
            isLangDocTitleStarted = true;
            argSourceLines.add("\"\"\""
                    + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                            langDoc.getTitle()));
        } else {
            argSourceLines.add("\"\"\"");
        }

        // Flag to check if a blank line has already been inserted.
        boolean isLangDocDescriptionStarted = false;

        for (int indexDescription = 0; indexDescription < langDoc
                .getDescriptionList().size(); indexDescription++) {
            final String strDescrption = langDoc.getDescriptionList().get(
                    indexDescription);

            // Inserts a blank line.
            if (isLangDocDescriptionStarted == false) {
                isLangDocDescriptionStarted = true;
                if (isLangDocTitleStarted) {
                    argSourceLines.add("");
                }
            }

            argSourceLines.add("" + strDescrption);
        }

        // Flag to check if a blank line has already been inserted.
        boolean isLangDocTagStarted = false;

        // Expands method parameters.
        for (int indexParameter = 0; indexParameter < langDoc
                .getParameterList().size(); indexParameter++) {
            final BlancoCgParameter cgParameter = langDoc.getParameterList()
                    .get(indexParameter);

            // Inserts a blank line.
            if (isLangDocTagStarted == false) {
                isLangDocTagStarted = true;
                argSourceLines.add("");
            }

            final StringBuffer bufParameter = new StringBuffer();
            bufParameter.append(cgParameter.getName());
            if (BlancoStringUtil.null2Blank(cgParameter.getDescription())
                    .length() > 0) {
                bufParameter.append(" -- "
                        + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                                cgParameter.getDescription()));
            }
            argSourceLines.add(bufParameter.toString());
        }

        if (langDoc.getReturn() != null
                && langDoc.getReturn().getType().getName().equals("void") == false) {

            // Inserts a blank line.
            if (isLangDocTagStarted == false) {
                isLangDocTagStarted = true;
                argSourceLines.add("*");
            }

            final StringBuffer bufReturn = new StringBuffer();
            bufReturn.append("return");
            if (BlancoStringUtil.null2Blank(
                    langDoc.getReturn().getDescription()).length() > 0) {
                bufReturn.append(" -- "
                        + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                                langDoc.getReturn().getDescription()));
            }
            argSourceLines.add(bufReturn.toString());
        }

        // Expands the throws list.
        for (int indexThrow = 0; indexThrow < langDoc.getThrowList().size(); indexThrow++) {
            final BlancoCgException cgException = langDoc.getThrowList().get(
                    indexThrow);

            // Inserts a blank line.
            if (isLangDocTagStarted == false) {
                isLangDocTagStarted = true;
                argSourceLines.add("*");
            }

            final StringBuffer bufThrow = new StringBuffer();

            // For language document processing, blancoCg's common processing for Type cannot be used.
            // Describes individually.
            bufThrow.append("* @throws "
                    + BlancoNameUtil.trimJavaPackage(cgException.getType()
                            .getName()));
            if (BlancoStringUtil.null2Blank(cgException.getDescription())
                    .length() > 0) {
                bufThrow.append(" "
                        + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                                cgException.getDescription()));
            }
            argSourceLines.add(bufThrow.toString());
        }
    }

    /**
     * Expands the main body of the language document.
     * 
     * This method is also used from the file header expansion of the source file.
     * 
     * @param langDoc
     * @param argSourceLines
     */
    public void transformLangDocCommentTag(final BlancoCgLangDoc langDoc,
            final List<java.lang.String> argSourceLines) {

        // Expands additional information such as author.
        if (langDoc.getTagList() != null) {
            for (int index = 0; index < langDoc.getTagList().size(); index++) {
                final BlancoCgLangDocTag langDocTag = langDoc.getTagList().get(
                        index);

                if (langDocTag.getName() == null) {
                    throw new IllegalArgumentException(
                            "A null was given for the name of BlancoCgLangDocTag."
                                    + langDocTag.toString());
                }
                if (langDocTag.getValue() == null) {
                    throw new IllegalArgumentException(
                            "A null was given for the value of BlancoCgLangDocTag."
                                    + langDocTag.toString());
                }

                final StringBuffer buf = new StringBuffer();
                buf.append("__" + langDocTag.getName() + "__ = ");
                if (BlancoStringUtil.null2Blank(langDocTag.getKey()).length() > 0) {
                    buf.append(" " + langDocTag.getKey() + " ");
                }
                buf.append("\"");
                buf.append(BlancoCgSourceUtil.escapeStringAsLangDoc(
                        TARGET_LANG, langDocTag.getValue()));
                buf.append("\"");
                argSourceLines.add(buf.toString());
            }
        }

    }

}

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
package blanco.cg.transformer.cs;

import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgLangDoc (language document) into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.<br>
 * This is a common process to expand various language documents such as classes, methods, and fields.
 * 
 * @author IGA Tosiki
 */
class BlancoCgLangDocCsSourceExpander {
    /**
     * The programming language to be processed by this class.
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.CS;

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
        // Expands the main body except for the start and end.
        transformLangDocBody(langDoc, argSourceLines, false);
    }

    /**
     * Expands the main body of the language document.
     * 
     * This method is also used from the file header expansion of the source file.
     * 
     * @param langDoc
     * @param argSourceLines
     * @param isFileHeader
     *            Whether it is a file header.
     */
    public void transformLangDocBody(final BlancoCgLangDoc langDoc,
            final List<java.lang.String> argSourceLines,
            final boolean isFileHeader) {
        boolean isLangDocTitleStarted = false;

        String commentString = "/// ";
        if (isFileHeader) {
            commentString = BlancoCgLineUtil
                    .getSingleLineCommentPrefix(TARGET_LANG);
        }

        if (BlancoStringUtil.null2Blank(langDoc.getTitle()).length() > 0) {
            isLangDocTitleStarted = true;
            argSourceLines.add(commentString
                    + "<summary>"
                    + BlancoCgSourceUtil.escapeStringAsLangDoc(TARGET_LANG,
                            langDoc.getTitle()) + "</summary>");
        }

        // Flag to check if a blank line has already been inserted.
        boolean isLangDocDescriptionStarted = false;

        for (int indexDescription = 0; indexDescription < langDoc
                .getDescriptionList().size(); indexDescription++) {
            final String strDescrption = langDoc.getDescriptionList().get(
                    indexDescription);

            if (isLangDocDescriptionStarted == false) {
                if (isLangDocTitleStarted == false) {
                    isLangDocTitleStarted = true;
                    argSourceLines.add(commentString
                            + "<summary>"
                            + BlancoCgSourceUtil.escapeStringAsLangDoc(
                                    TARGET_LANG, strDescrption) + "</summary>");
                } else {
                    isLangDocDescriptionStarted = true;
                    argSourceLines.add(commentString + "<remarks>");
                    argSourceLines.add(commentString + "<newpara>"
                            + strDescrption + "</newpara>");
                }
            } else {
                argSourceLines.add(commentString + "<newpara>" + strDescrption
                        + "</newpara>");
            }
        }

        if (isLangDocDescriptionStarted) {
            argSourceLines.add(commentString + "</remarks>");
        }

        // TODO: Expands author.

        // TODO: Considers expanding beyond author.

        // Expands method parameters.
        for (int indexParameter = 0; indexParameter < langDoc
                .getParameterList().size(); indexParameter++) {
            final BlancoCgParameter cgParameter = langDoc.getParameterList()
                    .get(indexParameter);

            final StringBuffer bufParameter = new StringBuffer();
            bufParameter.append(commentString + "<param name=\""
                    + cgParameter.getName() + "\">");
            if (BlancoStringUtil.null2Blank(cgParameter.getDescription())
                    .length() > 0) {
                bufParameter.append(BlancoCgSourceUtil.escapeStringAsLangDoc(
                        TARGET_LANG, cgParameter.getDescription()));
            }
            bufParameter.append("</param>");
            argSourceLines.add(bufParameter.toString());
        }

        if (langDoc.getReturn() != null
                && langDoc.getReturn().getType().getName().equals("void") == false) {

            final StringBuffer bufReturn = new StringBuffer();
            bufReturn.append(commentString + "<returns>");
            if (BlancoStringUtil.null2Blank(
                    langDoc.getReturn().getDescription()).length() > 0) {
                bufReturn.append(BlancoCgSourceUtil.escapeStringAsLangDoc(
                        TARGET_LANG, langDoc.getReturn().getDescription()));
            }
            bufReturn.append("</returns>");
            argSourceLines.add(bufReturn.toString());
        }

        // Note: There is not throws list expansion in C#.NET.
        // TODO: The throw list expansion should be expanded to the description part of the document.
    }
}

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
package blanco.cg.util;

import blanco.cg.BlancoCgSupportedLang;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility of blancoCg for lines.
 *
 * This class is used across programming languages.
 *
 * @author IGA Tosiki
 */
public class BlancoCgLineUtil {
    /**
     * Gets a string indicating the start of an inline comment.
     *
     * Returns the string, followed by a space.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the start of an inline comment.
     */
    public static final String getSingleLineCommentPrefix(
            final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.SWIFT:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "// ";
        case BlancoCgSupportedLang.VB:
            return "' ";
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
            return "# ";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Gets a string enclosing a string literal.
     *
     * Returns double-quote or single-quorte, depending on the programming language of the output.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string enclosing a string literal.
     */
    public static final String getStringLiteralEnclosure(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "\"";
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
            return "'";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Gets an operator to concatenate strings.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return An operator to concatenate strings.
     */
    public static final String getStringConcatenationOperator(
            final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
            return "+";
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.PHP8:
            return ".";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Gets a prefix of a variable.
     *
     * If a prefix is grammatically required for a variable, it will return that string.
     * Otherwise, it returns a zero-length string.
     * In PHP, returns $.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A prefix of a local variable.
     */
    public static final String getVariablePrefix(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.PHP8:
            return "$";
        default:
            return "";
        }
    }

    /**
     * Gets a string indicating a variable declaration.
     *
     * Returns one line of variable declaration.
     * It does not include the character indicating the end of the statement (semicolon in Java).
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argVariableName
     *            Variable name.
     * @param argTypeShortName
     *            Short type name.
     * @param argInitialValue
     *            Initial value; if null or zero length, the variable will not explicitly initialized.
     * @return A string indicating a variable declaration.
     */
    public static final String getVariableDeclaration(final int argTargetLang,
            final String argVariableName, final String argTypeShortName,
            final String argInitialValue) {
        String result = "";
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        default:
            result = argTypeShortName + " " + argVariableName;
            break;
        case BlancoCgSupportedLang.CS:
            result = argTypeShortName + " " + argVariableName;
            break;
        case BlancoCgSupportedLang.JS:
            // The type name is not used.
            result = "var " + argVariableName;
            break;
        case BlancoCgSupportedLang.VB:
            result = "Dim " + argVariableName + " As " + argTypeShortName;
            break;
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.PHP8:
            // The type name is not used.
            result = BlancoCgLineUtil.getVariablePrefix(argTargetLang)
                    + argVariableName;
            break;
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
            // The type name is not used.
            result = argVariableName;
            break;
        case BlancoCgSupportedLang.KOTLIN:
            // Generates only variables here.
            result = "var " + argVariableName + " : " + argTypeShortName;
            break;
        case BlancoCgSupportedLang.TS:
            // Generates only variables here.
            result = "let " + argVariableName + ": " + argTypeShortName;
            break;
        }

        if (BlancoStringUtil.null2Blank(argInitialValue).length() > 0) {
            result += " = " + argInitialValue;
        }
        return result;
    }

    /**
     * Gets a string indicating a variable declaration that cannot be changed after initialization.
     *
     * Returns one line of variable declaration.
     * It does not include the character indicating the end of the statement (semicolon in Java).
     *
     * @param argTargetLang
     * @param argValueName
     * @param argTypeShortName
     * @param argInitialValue
     * @return
     */
    public static final String getValueDeclaration(
            final int argTargetLang,
            final String argValueName,
            final String argTypeShortName,
            final String argInitialValue) {
        String result = "";
        switch (argTargetLang) {
            case BlancoCgSupportedLang.KOTLIN:
                result = "val " + argValueName + " : " + argTypeShortName;
                break;
            case BlancoCgSupportedLang.TS:
                result = "const " + argValueName + ": " + argTypeShortName;
                break;
            default:
                result = argTypeShortName + " " + argValueName;
        }


        if (BlancoStringUtil.null2Blank(argInitialValue).length() > 0) {
            result += " = " + argInitialValue;
        }
        return result;
    }

    // From here on, the facade method.

    /**
     * Gets a string indicating the start of the "if" statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getIfBegin(int, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argExpr
     *            Conditional expression.
     * @return A string indicating the start of the "if" statement.
     */
    public static final String getIfBegin(final int argTargetLang,
            final String argExpr) {
        return BlancoCgStatementUtil.getIfBegin(argTargetLang, argExpr);
    }

    /**
     * Gets a string indicating the end of the "if" statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getIfEnd(int)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of the "if" statement.
     */
    public static final String getIfEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getIfEnd(argTargetLang);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * Java, C#, JavaScript and PHP are supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginJava(int, java.lang.String, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argExpr1
     *            Initialization.
     * @param argExpr2
     *            Condition.
     * @param argExpr3
     *            A statement to be executed at each loop.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginJava(final int argTargetLang,
            final String argExpr1, final String argExpr2, final String argExpr3) {
        return BlancoCgStatementUtil.getForBeginJava(argTargetLang, argExpr1,
                argExpr2, argExpr3);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * VB.NET is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginVb(int, java.lang.String, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCounter
     *            Loop counter. Example: i As Integer = 1
     * @param argTo
     *            End value (not a condition). Example: 10.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginVb(final int argTargetLang,
            final String argCounter, final String argTo) {
        return BlancoCgStatementUtil.getForBeginVb(argTargetLang, argCounter,
                argTo, null);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * VB.NET is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginVb(int, java.lang.String, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCounter
     *            Loop counter. Example: i As Integer = 1
     * @param argTo
     *            End value (not a condition). Example: 10.
     * @param argStep
     *            The value to be used for Step. Example: 2. If it is null, Step is omitted.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginVb(final int argTargetLang,
            final String argCounter, final String argTo, final String argStep) {
        return BlancoCgStatementUtil.getForBeginVb(argTargetLang, argCounter,
                argTo, argStep);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * Ruby is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginRuby(int, java.lang.String, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCounter
     *            Loop counter.
     * @param argFrom
     *            Start value (not a condition). Example: 1.
     * @param argTo
     *            End value (not a condition). Example: 10.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginRuby(final int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        return BlancoCgStatementUtil.getForBeginRuby(argTargetLang, argCounter,
                argFrom, argTo);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * Delphi is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginDelphi(int , java.lang.String,java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCounter
     *            Loop counter.
     * @param argFrom
     *            Start value of the loop.
     * @param argTo
     *            End value of the loop.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginDelphi(final int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        return BlancoCgStatementUtil.getForBeginDelphi(argTargetLang,
                argCounter, argFrom, argTo);
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * Python is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getForBeginPython(int, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argItem
     *            The current value of the object to be looped.
     * @param argItems
     *            An object to be looped.
     * @return A string indicating the start of the "for" statement.
     */
    public static final String getForBeginPython(final int argTargetLang,
            final String argItem, final String argItems) {
        return BlancoCgStatementUtil.getForBeginPython(argTargetLang, argItem,
                argItems);
    }

    /**
     * Gets a string indicating the start of "each" block.
     *
     * Ruby is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getEachBeginRuby(int, java.lang.String, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argObject
     *            The object on which to call the "each" method.
     * @param argVariable
     *            Current value of "each" method.
     * @return A string indicating the start of "each" block.
     */
    public static final String getEachBeginRuby(final int argTargetLang,
            final String argObject, final String argVariable) {
        return BlancoCgStatementUtil.getEachBeginRuby(argTargetLang, argObject,
                argVariable);
    }

    /**
     * Gets a string indicating the end of "each" block.
     *
     * Ruby is supported.
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getEachEnd(int)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of "each" block.
     */
    public static final String getEachEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getEachEnd(argTargetLang);
    }

    /**
     * Gets a string indicating the end of the "for" statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getForEnd(int)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of the "for" statement.
     */
    public static final String getForEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getForEnd(argTargetLang);
    }

    /**
     * Get a string indicating the statement that exits the "for" statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getForExit(int)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return "break" or "Exit For".
     */
    public static final String getForExit(final int argTargetLang) {
        return BlancoCgStatementUtil.getForExit(argTargetLang);
    }

    /**
     * Gets a character indicating the end of a statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getTerminator(int)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A character indicating the end of a statement.
     */
    public static final String getTerminator(final int argTargetLang) {
        return BlancoCgStatementUtil.getTerminator(argTargetLang);
    }

    /**
     * Gets a string indicating the start of the "while" statement.
     *
     * Ruby and Python are supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getWhileBeginRuby(int, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCon
     *            Conditional expression.
     * @return A string indicating the start of the "while" statement.
     */
    public static final String getWhileBeginRuby(final int argTargetLang,
            final String argCon) {
        return BlancoCgStatementUtil.getWhileBeginRuby(argTargetLang, argCon);
    }

    /**
     * Gets a string indicating the start of the "while" statement.
     *
     * Delphi is supported.
     * The actual processing will be delegated to
     * {@link BlancoCgStatementUtil#getWhileBeginDelphi(int, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCon
     *            Conditional expression.
     * @return A string indicating the start of the "while" statement.
     */
    public static final String getWhileBeginDelphi(final int argTargetLang,
            final String argCon) {
        return BlancoCgStatementUtil.getWhileBeginRuby(argTargetLang, argCon);
    }

    /**
     * Gets a string indicating the "return" statement.
     *
     * The actual processing will be delegated to {@link BlancoCgStatementUtil#getReturn(int, java.lang.String)}.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argExpr
     *            A statement to be returned.
     * @return A string indicating the "return" statement.
     */
    public static final String getReturn(final int argTargetLang,
            final String argExpr) {
        return BlancoCgStatementUtil.getReturn(argTargetLang, argExpr);
    }


    /**
     * Expands Annotation.
     *
     * @param argTargetLang
     * @param argAnnotationList
     * @param argSourceLines
     */
    public static void expandAnnotationList(
            final int argTargetLang,
            final List<String> argAnnotationList,
            final List<java.lang.String> argSourceLines
    ) {
        switch (argTargetLang) {
            case BlancoCgSupportedLang.KOTLIN:
            case BlancoCgSupportedLang.TS:
                expandKotlinAnnotationList(argAnnotationList, argSourceLines);
                break;
            default:
                throw new IllegalArgumentException(
                        "BlancoCgStatementUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Expands the Annotation in KotlinStyle.
     * Perhaps Java and TypeScript can be expanded using the same logic.
     *
     * @param argAnnotationList
     * @param argSourceLines
     */
    private static void expandKotlinAnnotationList(
            final List<String> argAnnotationList,
            final List<java.lang.String> argSourceLines
    ) {
        for (String strAnnotation : argAnnotationList) {
            // Sets line breaks in annotation individually.
            String [] ann = BlancoNameUtil.splitString(strAnnotation, '\n');
            List<String> annList = new ArrayList<>(Arrays.asList(ann));
            int i = 0;
            String LF = System.getProperty("line.separator", "\n");
            String myLine = annList.get(0);
            for (String annLine : annList) {
                if (i == 0) {
                    annLine = "@" + annLine;
                } else {
                    argSourceLines.add(myLine + LF);
                }
                myLine = annLine;
                i++;
            }
            argSourceLines.add(myLine);
        }
    }
}

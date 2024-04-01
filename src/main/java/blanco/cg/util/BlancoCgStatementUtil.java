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
import blanco.commons.util.BlancoStringUtil;

/**
 * A utility of blancoCg for statements.
 *
 * This class is used across programming languages.
 *
 * @author IGA Tosiki
 */
public class BlancoCgStatementUtil {
    /**
     * Gets a string indicating the start of the "if" statement.
     *
     * It also includes a string indicating the start of the block (in Java, curly braces).
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argExpr
     *            Conditional expression.
     * @return A string indicating the start of the "if" statement.
     */
    public static final String getIfBegin(final int argTargetLang,
            final String argExpr) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "if (" + argExpr + ") {";
        case BlancoCgSupportedLang.VB:
            return "If (" + argExpr + ") Then";
        case BlancoCgSupportedLang.RUBY:
            return "if " + argExpr;
        case BlancoCgSupportedLang.PYTHON:
            return "if " + argExpr + ":";
        case BlancoCgSupportedLang.DELPHI:
            return "if " + argExpr + " then begin";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the end of the "if" statement.
     *
     * In Python, the end of an "if" statement is not grammatically necessary,
     * but the comment string is returned to format the auto-generated source code.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of the "if" statement.
     */
    public static final String getIfEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "}";
        case BlancoCgSupportedLang.VB:
            return "End If";
        case BlancoCgSupportedLang.RUBY:
            return "end";
        case BlancoCgSupportedLang.DELPHI:
            return "end;";
        case BlancoCgSupportedLang.PYTHON:
            return "#end";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language (" + argTargetLang
                            + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * Java, C#, JavaScript and PHP are supported.
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
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.PHP8:
            return "for (" + argExpr1 + "; " + argExpr2 + "; " + argExpr3
                    + ") {";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginJava: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the start of the "for" statement.
     *
     * VB.NET is supported.
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
        switch (argTargetLang) {
        case BlancoCgSupportedLang.VB:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginVb: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }

        String argLine = "For " + argCounter + " To " + argTo;
        if (BlancoStringUtil.null2Blank(argStep).length() > 0) {
            argLine += " Step " + argStep;
        }

        return argLine;
    }

    /**
     *
     * Gets a string indicating the start of the "for" statement.
     *
     * Ruby is supported.
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
    public static String getForBeginRuby(int argTargetLang, String argCounter,
            String argFrom, String argTo) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }

        String argLine = "for " + argCounter + " in " + argFrom + ".." + argTo;

        return argLine;
    }

    /**
     *
     * Gets a string indicating the start of the "for" statement.
     *
     * Python is supported.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argItem
     *            The current value of the object to be looped.
     * @param argItems
     *            An object to be looped.
     * @return A string indicating the start of the "for" statement.
     */
    public static String getForBeginPython(int argTargetLang, String argItem,
            String argItems) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.PYTHON:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }

        String argLine = "for " + argItem + " in " + argItems + ":";

        return argLine;
    }

    /**
     *
     * Gets a string indicating the start of the "for" statement.
     *
     * Kotlin is supported.
     * If you want to loop over a range of integers, write "1..100" etc. in argItems.
     *
     * @param argTargetLang
     * @param argItem
     * @param argItems
     * @return
     */
    public static String getForBeginKotlin(int argTargetLang, String argItem, String argItems) {
        switch (argTargetLang) {
            case BlancoCgSupportedLang.KOTLIN:
                break;
            default:
                throw new IllegalArgumentException(
                        "BlancoCgStatementUtil.getForBeginKotlin: An unsupported programming language ("
                                + argTargetLang + ") has been given.");
        }

        String argLine = "for (" + argItem + " in " + argItems + ") {";

        return argLine;
    }

    /**
     *
     * Gets a string indicating the start of the "for" statement.
     *
     * Delphi is supported.
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
    public static String getForBeginDelphi(int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.DELPHI:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }

        String argLine = "for " + argCounter + " := " + argFrom + " to "
                + argTo + " do begin";

        return argLine;
    }

    /**
     * Gets a string indicating the end of the "for" statement.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of the "for" statement.
     */
    public static final String getForEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "}";
        case BlancoCgSupportedLang.VB:
            // The loop variable is omitted.
            return "Next";
        case BlancoCgSupportedLang.RUBY:
            return "end";
        case BlancoCgSupportedLang.PYTHON:
            return "#end";
        case BlancoCgSupportedLang.DELPHI:
            return "end;";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Get a string indicating the statement that exits the "for" statement.
     *
     * It does not include the character indicating the end of the statement (semicolon in Java).
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the statement that exits the "for" statement.
     */
    public static final String getForExit(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return "break";
        case BlancoCgSupportedLang.VB:
            return "Exit For";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the start of the "while" statement.
     *
     * Ruby and Python are supported.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCon
     *            Conditional expression.
     * @return A string indicating the start of the "while" statement.
     */
    public static String getWhileBeginRuby(int argTargetLang, String argCon) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return "while " + argCon;
        case BlancoCgSupportedLang.PYTHON:
            return "while " + argCon + ":";
        case BlancoCgSupportedLang.DELPHI:
            return "while " + argCon + " do begin";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getWhileBeginRuby: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the start of the "while" statement.
     *
     * Delphi is supported.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argCon
     *            Conditional expression.
     * @return A string indicating the start of the "while" statement.
     */
    public static String getWhileBeginDelphi(int argTargetLang, String argCon) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.DELPHI:
            return "while " + argCon + " do begin";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getWhileBeginRuby: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the end of the "while" statement.
     *
     * Ruby and Python are supported.
     * In Python, the end of an "while" statement is not grammatically necessary,
     * but the comment string is returned to format the auto-generated source code.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of the "while" statement.
     */
    public static final String getWhileEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return "end";
        case BlancoCgSupportedLang.PYTHON:
            return "#end";
        case BlancoCgSupportedLang.DELPHI:
            return "end;";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the start of "each" block.
     *
     * Ruby is supported.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argObject
     *            The object on which to call the "each" method.
     * @param argVariable
     *            Current value of "each" method.
     * @return A string indicating the start of "each" block.
     */
    public static String getEachBeginRuby(int argTargetLang, String argObject,
            String argVariable) {
        // argArray.each do |arg|
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return argObject + ".each do |" + argVariable + "|";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the end of "each" block.
     *
     * Ruby is supported.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A string indicating the end of "each" block.
     */
    public static final String getEachEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return "end";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a character indicating the end of a statement.
     *
     * In many languages, such as Java, the semicolon is returned.
     * In languages that do not require a character to indicate the end of a statement, such as Ruby, it returns a zero-length string.
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @return A character indicating the end of a statement.
     */
    public static final String getTerminator(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.TS:
        case BlancoCgSupportedLang.PHP8:
            return ";";
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.SWIFT:
        case BlancoCgSupportedLang.KOTLIN:
            return "";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

    /**
     * Gets a string indicating the "return" statement.
     *
     * It does not include the character indicating the end of the statement (semicolon in Java).
     *
     * @param argTargetLang
     *            The programming language of the output target.
     * @param argExpr
     *            A statement to be returned.
     * @return A string indicating the "return" statement.
     */
    public static final String getReturn(final int argTargetLang,
            final String argExpr) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.PHP8:
            // Note: Semicolons are not included.
            return "return " + argExpr;
        case BlancoCgSupportedLang.VB:
            // The loop variables are omitted.
            return "Return " + argExpr;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: An unsupported programming language ("
                            + argTargetLang + ") has been given.");
        }
    }

}

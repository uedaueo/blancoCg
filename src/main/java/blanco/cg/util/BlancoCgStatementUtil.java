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
 * blancoCgのステートメントに関するユーティリティです。
 *
 * このクラスはプログラミング言語を超えて利用されます。
 *
 * @author IGA Tosiki
 */
class BlancoCgStatementUtil {
    /**
     * if文の開始部分を表す文字列を取得します。
     *
     * ブロック開始を表す文字列（Javaの場合、中括弧）も含みます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr
     *            条件式。
     * @return if文の開始部分を表す文字列。
     */
    public static final String getIfBegin(final int argTargetLang,
            final String argExpr) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
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
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * if文の終了部分を表す文字列を取得します。
     *
     * Pythonでは、if文の終了部分は文法的に必要ありませんが、 自動生成されたソースコードを整形するために、コメント文字列 を戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return if文の終了部分を表す文字列。
     */
    public static final String getIfEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
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
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * Java, C#, JavaScript, PHPに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr1
     *            初期化処理。
     * @param argExpr2
     *            継続条件。
     * @param argExpr3
     *            ループのつど実施する処理。
     * @return for文の開始部分を表す文字列。
     */
    public static final String getForBeginJava(final int argTargetLang,
            final String argExpr1, final String argExpr2, final String argExpr3) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
            return "for (" + argExpr1 + "; " + argExpr2 + "; " + argExpr3
                    + ") {";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginJava: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * VB.NETに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            ループカウンタ。「例: i As Integer = 1」
     * @param argTo
     *            終了となるしきい値 (条件ではありません)。「例: 10」
     * @param argStep
     *            Stepに利用される値。「例: 2」。nullの場合には Stepは省略されます。
     * @return for文の開始部分を表す文字列。
     */
    public static final String getForBeginVb(final int argTargetLang,
            final String argCounter, final String argTo, final String argStep) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.VB:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginVb: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }

        String argLine = "For " + argCounter + " To " + argTo;
        if (BlancoStringUtil.null2Blank(argStep).length() > 0) {
            argLine += " Step " + argStep;
        }

        return argLine;
    }

    /**
     *
     * for文の開始部分を表す文字列を取得します。
     *
     * Rubyに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            ループカウンタ。
     * @param argFrom
     *            開始値 (条件ではありません)。「例: 1」
     * @param argTo
     *            終了値 (条件ではありません)。「例: 10」
     * @return for文の開始部分を表す文字列。
     */
    public static String getForBeginRuby(int argTargetLang, String argCounter,
            String argFrom, String argTo) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }

        String argLine = "for " + argCounter + " in " + argFrom + ".." + argTo;

        return argLine;
    }

    /**
     *
     * for文の開始部分を表す文字列を取得します。
     *
     * Pythonに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argItem
     *            ループ対象となるオブジェクトの現在値。
     * @param argItems
     *            ループ対象となるオブジェクト。
     * @return for文の開始部分を表す文字列。
     */
    public static String getForBeginPython(int argTargetLang, String argItem,
            String argItems) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.PYTHON:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }

        String argLine = "for " + argItem + " in " + argItems + ":";

        return argLine;
    }

    /**
     *
     * for文の開始部分を表す文字列を取得します。
     *
     * Kotlin に対応しています。
     * 整数範囲でloopを組みたい場合は argItems に
     *   1..100
     * などと記述します。
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
                        "BlancoCgStatementUtil.getForBeginKotlin: サポートしないプログラミング言語("
                                + argTargetLang + ")が与えられました。");
        }

        String argLine = "for (" + argItem + " in " + argItems + ") {";

        return argLine;
    }

    /**
     *
     * for文の開始部分を表す文字列を取得します。
     *
     * Delphiに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            ループカウンタ。
     * @param argFrom
     *            ループの開始値。
     * @param argTo
     *            ループの終了値。
     * @return for文の開始部分を表す文字列。
     */
    public static String getForBeginDelphi(int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.DELPHI:
            break;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getForBeginRuby: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }

        String argLine = "for " + argCounter + " := " + argFrom + " to "
                + argTo + " do begin";

        return argLine;
    }

    /**
     * for文の終了部分を表す文字列を取得します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return for文の終了部分を表す文字列。
     */
    public static final String getForEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.KOTLIN:
            return "}";
        case BlancoCgSupportedLang.VB:
            // ループ変数は省略します。
            return "Next";
        case BlancoCgSupportedLang.RUBY:
            return "end";
        case BlancoCgSupportedLang.PYTHON:
            return "#end";
        case BlancoCgSupportedLang.DELPHI:
            return "end;";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * for文を抜ける文を表わす文字列を取得します。
     *
     * 文の終わりを示す文字(Javaの場合、セミコロン)は含みません。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return for文を抜ける文を表わす文字列。
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
            return "break";
        case BlancoCgSupportedLang.VB:
            return "Exit For";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * while文の開始部分を表わす文字列を取得します。
     *
     * Ruby, Pythonに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCon
     *            条件式。
     * @return while文の開始部分を表わす文字列。
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
                    "BlancoCgStatementUtil.getWhileBeginRuby: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }
    }

    /**
     * while文の開始部分を表わす文字列を取得します。
     *
     * Delphiに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCon
     *            条件式。
     * @return while文の開始部分を表わす文字列。
     */
    public static String getWhileBeginDelphi(int argTargetLang, String argCon) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.DELPHI:
            return "while " + argCon + " do begin";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil.getWhileBeginRuby: サポートしないプログラミング言語("
                            + argTargetLang + ")が与えられました。");
        }
    }

    /**
     * while文の終了部分を表す文字列を取得します。
     *
     * Ruby, Pythonに対応しています。 Pythonでは、while文の終了部分は文法的に必要ありませんが、
     * 自動生成されたソースコードを整形するために、コメント文字列 を戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return while文の終了部分を表す文字列。
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
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * eachブロックの開始部分を表す文字列を取得します。
     *
     * Rubyに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argObject
     *            eachメソッドを呼び出す対象となるオブジェクト。
     * @param argVariable
     *            eachメソッドの現在値。
     * @return eachブロックの開始部分を表す文字列。
     */
    public static String getEachBeginRuby(int argTargetLang, String argObject,
            String argVariable) {
        // argArray.each do |arg|
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return argObject + ".each do |" + argVariable + "|";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * eachブロックの終了部分を表す文字列を取得します。
     *
     * Rubyに対応しています。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return eachブロックの終了部分を表す文字列。
     */
    public static final String getEachEnd(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.RUBY:
            return "end";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * 文の終わりを示す文字を取得します。
     *
     * Javaなど多くの言語では、セミコロンを戻します。 Rubyなど文の終わりを示す文字が必要ない言語では、長さ0の文字列を 戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return 文の終わりを示す文字。
     */
    public static final String getTerminator(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.DELPHI:
            return ";";
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.SWIFT:
        case BlancoCgSupportedLang.KOTLIN:
            return "";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * return文を表す文字列を取得します。
     *
     * 文の終わりを示す文字(Javaの場合、セミコロン)は含みません。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr
     *            returnされる式。
     * @return return文を表す文字列。
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
            // 注意。セミコロンは含みません。
            return "return " + argExpr;
        case BlancoCgSupportedLang.VB:
            // ループ変数は省略します。
            return "Return " + argExpr;
        default:
            throw new IllegalArgumentException(
                    "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

}

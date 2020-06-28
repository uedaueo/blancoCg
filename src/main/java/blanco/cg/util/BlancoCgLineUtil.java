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
 * blancoCgの行に関するユーティリティです。
 *
 * このクラスはプログラミング言語を超えて利用されます。
 *
 * @author IGA Tosiki
 */
public class BlancoCgLineUtil {
    /**
     * １行コメントの開始を表す文字列を取得します。
     *
     * コメントの開始を表す文字列と、それに続く空白を戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return コメントの開始を表す文字列。
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
            return "// ";
        case BlancoCgSupportedLang.VB:
            return "' ";
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
            return "# ";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * 文字列リテラルを囲む文字列を取得します。
     *
     * 出力対象のプログラミング言語に応じて、ダブルクオートまたは シングルクオートを戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return 文字列リテラルを囲む文字列
     */
    public static final String getStringLiteralEnclosure(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.KOTLIN:
        case BlancoCgSupportedLang.TS:
            return "\"";
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
            return "'";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * 文字列を連結するオペレータを取得します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return 文字列を連結するオペレータ。
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
            return ".";
        default:
            throw new IllegalArgumentException(
                    "BlancoCgLineUtil: サポートしないプログラミング言語(" + argTargetLang
                            + ")が与えられました。");
        }
    }

    /**
     * 変数のプレフィックスを取得します。
     *
     * 文法的に変数にプレフィックスが必要な場合、その文字列を戻します。プレフィックスが必要ない言語では、長さ0の文字列を戻します。
     * PHPの場合、$を戻します。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return ローカル変数のプレフィックス
     */
    public static final String getVariablePrefix(final int argTargetLang) {
        switch (argTargetLang) {
        case BlancoCgSupportedLang.PHP:
            return "$";
        default:
            return "";
        }
    }

    /**
     * 変数宣言を表す文字列を取得します。
     *
     * 変数宣言１行を戻します。行の終端を表す文字（Javaの場合、セミコロン）は 含まれません。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argVariableName
     *            変数名。
     * @param argTypeShortName
     *            短い型名。
     * @param argInitialValue
     *            初期値。nullまたは長さ0の場合、変数の明示的な初期化は行いません。
     * @return 変数宣言を表す文字列。
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
            // 型名は利用しません。
            result = "var " + argVariableName;
            break;
        case BlancoCgSupportedLang.VB:
            result = "Dim " + argVariableName + " As " + argTypeShortName;
            break;
        case BlancoCgSupportedLang.PHP:
            // 型名は利用しません。
            result = BlancoCgLineUtil.getVariablePrefix(argTargetLang)
                    + argVariableName;
            break;
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
            // 型名は利用しません。
            result = argVariableName;
            break;
        case BlancoCgSupportedLang.KOTLIN:
            // ここでは変数のみ生成します。
            result = "var " + argVariableName + " : " + argTypeShortName;
            break;
        case BlancoCgSupportedLang.TS:
                // ここでは変数のみ生成します。
            result = "let " + argVariableName + ": " + argTypeShortName;
            break;
        }

        if (BlancoStringUtil.null2Blank(argInitialValue).length() > 0) {
            result += " = " + argInitialValue;
        }
        return result;
    }

    /**
     * 諸帰化後の変更ができないタイプの変数宣言を表す文字列を取得します。
     *
     * 変数宣言１行を戻します。行の終端を表す文字（Javaの場合、セミコロン）は 含まれません。
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

    // ここ以降はファサードメソッド。

    /**
     * if文の開始部分を表す文字列を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getIfBegin(int, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr
     *            条件式。
     * @return if文の開始部分を表す文字列
     */
    public static final String getIfBegin(final int argTargetLang,
            final String argExpr) {
        return BlancoCgStatementUtil.getIfBegin(argTargetLang, argExpr);
    }

    /**
     * if文の終了部分を表す文字列を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getIfEnd(int)}に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return if文の終了部分を表す文字列
     */
    public static final String getIfEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getIfEnd(argTargetLang);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * Java, C#, JavaScript, PHPに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginJava(int, java.lang.String, java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr1
     *            初期化処理。
     * @param argExpr2
     *            継続条件。
     * @param argExpr3
     *            ループのつど実施する処理。
     * @return for文の開始部分を表す文字列
     */
    public static final String getForBeginJava(final int argTargetLang,
            final String argExpr1, final String argExpr2, final String argExpr3) {
        return BlancoCgStatementUtil.getForBeginJava(argTargetLang, argExpr1,
                argExpr2, argExpr3);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * VB.NETに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginVb(int, java.lang.String, java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            表現。「例: i As Integer = 1」
     * @param argTo
     *            終了となるしきい値 (条件ではありません)。「例: 10」
     * @return for文の開始部分を表す文字列。
     */
    public static final String getForBeginVb(final int argTargetLang,
            final String argCounter, final String argTo) {
        return BlancoCgStatementUtil.getForBeginVb(argTargetLang, argCounter,
                argTo, null);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * VB.NETに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginVb(int, java.lang.String, java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            表現。「例: i As Integer = 1」
     * @param argTo
     *            終了となるしきい値 (条件ではありません)。「例: 10」
     * @param argStep
     *            Stepに利用される値。「例: 2」。nullの場合には Stepは省略されます。
     * @return for文の開始部分の文字列
     */
    public static final String getForBeginVb(final int argTargetLang,
            final String argCounter, final String argTo, final String argStep) {
        return BlancoCgStatementUtil.getForBeginVb(argTargetLang, argCounter,
                argTo, argStep);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * Rubyに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginRuby(int, java.lang.String, java.lang.String, java.lang.String)}
     * に委譲されます。
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
    public static final String getForBeginRuby(final int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        return BlancoCgStatementUtil.getForBeginRuby(argTargetLang, argCounter,
                argFrom, argTo);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * Delphiに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginDelphi(int , java.lang.String,java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCounter
     *            ループカウンタ。
     * @param argFrom
     *            開始値
     * @param argTo
     *            終了値
     * @return for文の開始部分を表す文字列。
     */
    public static final String getForBeginDelphi(final int argTargetLang,
            final String argCounter, final String argFrom, final String argTo) {
        return BlancoCgStatementUtil.getForBeginDelphi(argTargetLang,
                argCounter, argFrom, argTo);
    }

    /**
     * for文の開始部分を表す文字列を取得します。
     *
     * Pythonに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getForBeginPython(int, java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argItem
     *            ループ対象となるオブジェクトの現在値。
     * @param argItems
     *            ループ対象となるオブジェクト。
     * @return for文の開始部分を表す文字列。
     */
    public static final String getForBeginPython(final int argTargetLang,
            final String argItem, final String argItems) {
        return BlancoCgStatementUtil.getForBeginPython(argTargetLang, argItem,
                argItems);
    }

    /**
     * eachブロックの開始部分を表す文字列を取得します。
     *
     * Rubyに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getEachBeginRuby(int, java.lang.String, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argObject
     *            eachメソッドを呼び出す対象となるオブジェクト。
     * @param argVariable
     *            eachメソッドの現在値。
     * @return eachブロックの開始部分を表す文字列。
     */
    public static final String getEachBeginRuby(final int argTargetLang,
            final String argObject, final String argVariable) {
        return BlancoCgStatementUtil.getEachBeginRuby(argTargetLang, argObject,
                argVariable);
    }

    /**
     * eachブロックの終了部分を表す文字列を取得します。
     *
     * Rubyに対応しています。 実際の処理は、{@link BlancoCgStatementUtil#getEachEnd(int)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return eachブロックの終了部分を表す文字列。
     */
    public static final String getEachEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getEachEnd(argTargetLang);
    }

    /**
     * for文の終了部分を表す文字列を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getForEnd(int)}に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return for文の終了部分を表す文字列。
     */
    public static final String getForEnd(final int argTargetLang) {
        return BlancoCgStatementUtil.getForEnd(argTargetLang);
    }

    /**
     * for文を抜ける文を表わす文字列を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getForExit(int)}に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return breakまたは Exit Forが戻ります。
     */
    public static final String getForExit(final int argTargetLang) {
        return BlancoCgStatementUtil.getForExit(argTargetLang);
    }

    /**
     * 文の終わりを示す文字を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getTerminator(int)}に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @return 文の終わりを示す文字。
     */
    public static final String getTerminator(final int argTargetLang) {
        return BlancoCgStatementUtil.getTerminator(argTargetLang);
    }

    /**
     * while文の開始部分を表わす文字列を取得します。
     *
     * Ruby, Pythonに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getWhileBeginRuby(int, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCon
     *            条件式。
     * @return while文の開始部分を表わす文字列。
     */
    public static final String getWhileBeginRuby(final int argTargetLang,
            final String argCon) {
        return BlancoCgStatementUtil.getWhileBeginRuby(argTargetLang, argCon);
    }

    /**
     * while文の開始部分を表わす文字列を取得します。
     *
     * Delphiに対応しています。 実際の処理は、
     * {@link BlancoCgStatementUtil#getWhileBeginDelphi(int, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argCon
     *            条件式。
     * @return while文の開始部分を表わす文字列。
     */
    public static final String getWhileBeginDelphi(final int argTargetLang,
            final String argCon) {
        return BlancoCgStatementUtil.getWhileBeginRuby(argTargetLang, argCon);
    }

    /**
     * return文を表す文字列を取得します。
     *
     * 実際の処理は、{@link BlancoCgStatementUtil#getReturn(int, java.lang.String)}
     * に委譲されます。
     *
     * @param argTargetLang
     *            出力対象のプログラミング言語。
     * @param argExpr
     *            returnされる式。
     * @return return文を表す文字列。
     */
    public static final String getReturn(final int argTargetLang,
            final String argExpr) {
        return BlancoCgStatementUtil.getReturn(argTargetLang, argExpr);
    }


    /**
     * Annotation を展開します。
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
                        "BlancoCgStatementUtil: サポートしないプログラミング言語(" + argTargetLang
                                + ")が与えられました。");
        }
    }

    /**
     * KotlinStyle の Annotation を展開します。
     * 恐らく、Java や Typescript も同じロジックで展開できるはずです。
     *
     * @param argAnnotationList
     * @param argSourceLines
     */
    private static void expandKotlinAnnotationList(
            final List<String> argAnnotationList,
            final List<java.lang.String> argSourceLines
    ) {
        for (String strAnnotation : argAnnotationList) {
            // annotation 内での改行を個別に設定します
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
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add(myLine);
        }
    }
}

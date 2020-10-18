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
package blanco.cg.transformer.java;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgTypeをソースコードに展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgTypeJavaSourceExpander {
    /**
     * プログラミング言語の予約語一覧。
     */
    private static final String[] LANGUAGE_RESERVED_KEYWORD = { "void", "byte",
            "short", "int", "long", "char", "float", "double", "boolean" };

    /**
     * blancoCg型を、具体的な文字列へと変換します。
     *
     * 配列を表す[]やジェネリクスも展開します。<br>
     * TODO 複数パッケージ間での同一クラス名(例:java.util.Dateとjava.sql.Dateなど)は考慮していません。
     * 複数パッケージの同一クラス名を一つのソースファイル内で利用する為の諸機能は未提供です。
     *
     * @param argType
     *            blancoCg上の型。
     * @return プログラミング言語における型を示す文字列。
     */
    public static String toTypeString(final BlancoCgType argType) {
        return BlancoCgSourceUtil.extendTypeWithGenerics(argType);
    }

    /**
     * 与えられた文字列がプログラミング言語の予約語であるかどうかをチェックします。
     *
     * @param argCheck
     *            チェックしたい文字列。
     * @return プログラミング言語の予約語に該当したかどうか。
     * @see <a
     *      href="http://java.sun.com/docs/books/jls/second_edition/html/typesValues.doc.html#85587">4.2
     *      Primitive Types and Values (Java Language Specification - Second
     *      Edition)</a>
     */
    public static boolean isLanguageReservedKeyword(final String argCheck) {
        for (int index = 0; index < LANGUAGE_RESERVED_KEYWORD.length; index++) {
            if (LANGUAGE_RESERVED_KEYWORD[index].equals(argCheck)) {
                // この文字列はプログラミング言語の予約語です。
                return true;
            }
        }

        // キーワードにヒットしませんでした。この文字列はプログラミング言語の予約語ではありません。
        return false;
    }
}

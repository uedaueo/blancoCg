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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import blanco.cg.BlancoCgSupportedLang;
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoPhpSourceUtil;
import blanco.commons.util.BlancoVbSourceUtil;

/**
 * blancoCgのソースコード関連ユーティリティです。
 *
 * このクラスはプログラミング言語を超えて利用されます。
 *
 * @author IGA Tosiki
 */
public class BlancoCgSourceUtil {
    /**
     * 与えられた文字列をソースコード文字列として出力するものとしてエスケープ処理します。
     *
     * ￥/バックスラッシュのエスケープおよび改行コードのエスケープを行います。<br>
     * それ以外の処理は行いません。たとえばインジェクション攻撃などへの耐性は、このメソッドは扱いません。
     *
     * @param targetLang
     *            出力対象のプログラミング言語。
     * @param originalString
     *            入力文字列。
     * @return エスケープ処理が行われた後の文字列。
     */
    public static String escapeStringAsSource(final int targetLang,
            final String originalString) {
        switch (targetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.KOTLIN:
            return BlancoJavaSourceUtil
                    .escapeStringAsJavaSource(originalString);
        case BlancoCgSupportedLang.VB:
            return BlancoVbSourceUtil.escapeStringAsVbSource(originalString);
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY: // TODO 妥当性の確認
        case BlancoCgSupportedLang.PYTHON: // TODO 妥当性の確認
            return BlancoPhpSourceUtil.escapeStringAsPhpSource(originalString);
        case BlancoCgSupportedLang.DELPHI: // TODO 妥当性の確認
            return escapeStringAsDelphiSource(originalString);
        default:
            throw new IllegalArgumentException(
                    "BlancoCgSourceUtil.escapeAsSourceString にサポートされない言語("
                            + targetLang + ")が引数として与えられました。");
        }
    }

    /**
     * 与えられた文字列を言語ドキュメント文字列として扱うことができるように エスケープ処理します。
     *
     * JavaDoc文字列としてエスケープを行います。 HTMLとしてのエスケープと同等の処理が行われます。＜＞＆”がエスケープされます。
     *
     * @param targetLang
     *            出力対象のプログラミング言語。
     * @param originalString
     *            入力文字列
     * @return エスケープ処理が行われた後の文字列。
     */
    public static final String escapeStringAsLangDoc(final int targetLang,
            final String originalString) {
        switch (targetLang) {
        case BlancoCgSupportedLang.JAVA:
        case BlancoCgSupportedLang.CS:
        case BlancoCgSupportedLang.JS:
        case BlancoCgSupportedLang.VB:
        case BlancoCgSupportedLang.PHP:
        case BlancoCgSupportedLang.RUBY:
        case BlancoCgSupportedLang.PYTHON:
        case BlancoCgSupportedLang.DELPHI:
        case BlancoCgSupportedLang.SWIFT:
        case BlancoCgSupportedLang.KOTLIN:
                return BlancoJavaSourceUtil.escapeStringAsJavaDoc(originalString);
        default:
            throw new IllegalArgumentException(
                    "BlancoCgSourceUtil.escapeStringAsLangDoc にサポートされない言語("
                            + targetLang + ")が引数として与えられました。");
        }
    }

    /**
     * 与えられた文字列をDelphiソースコード文字列として出力するためのエスケープ処理をします。
     *
     * ￥/バックスラッシュのエスケープおよび改行コードのエスケープを行います。<br>
     * それ以外の処理は行いません。たとえばインジェクション攻撃などへの耐性は、このメソッドは扱いません。
     *
     * @param originalString
     *            入力文字列
     * @return エスケープ処理が行われた後の文字列
     */
    private static final String escapeStringAsDelphiSource(
            final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "BlancoCgSourceUtil.escapeStringAsDelphiSourceで入力違反が発生。このメソッドにnullがパラメータとして与えられました。null以外の値を入力してください。");
        }

        final StringReader reader = new StringReader(originalString);
        final StringWriter writer = new StringWriter();
        try {
            for (;;) {
                final int iRead = reader.read();
                if (iRead < 0) {
                    break;
                }
                switch (iRead) {
                // Delphi言語では、バックスラッシュをエスケープする必要がありません。
//                case '\\':
//                    writer.write("\\");
//                    break;
                case '\n':
                    writer.write("\\n");
                    break;
                case '\'':
                    writer.write("\'\'");
                    break;
                default:
                    writer.write((char) iRead);
                    break;
                }
            }
            writer.flush();
        } catch (IOException e) {
            // ここに入ってくることは、ありえません。
            e.printStackTrace();
        }
        return writer.toString();
    }

}

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
import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.*;

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
        case BlancoCgSupportedLang.TS:
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
        case BlancoCgSupportedLang.TS:
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

    /**
     * クラス名が正式形かどうかを判定します。
     *
     * @param className
     * @return
     */
    public static boolean isCanonicalClassName(final int targetLang, final String className) {
        boolean result = false;

        if (className == null) {
            return result;
        }

        switch (targetLang) {
            case BlancoCgSupportedLang.JAVA:
            case BlancoCgSupportedLang.KOTLIN:
                final int findLastDot = className.lastIndexOf('.');
                if (findLastDot > 0) {
                    result = true;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "BlancoCgSourceUtil.isCanonicalClassName にサポートされない言語("
                                + targetLang + ")が引数として与えられました。");
        }

        return result;
    }

    /**
     * Make canonical classname into packageName
     *
     * @param argClassNameCanon
     * @return
     */
    public static String getPackageName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        // trim generics
        String simpleCanon = getTypeNameWithoutGenerics(argClassNameCanon);

        String simpleName = "";
        final int findLastDot = simpleCanon.lastIndexOf('.');
        if (findLastDot > 0) {
            simpleName = simpleCanon.substring(0, findLastDot);
        }
        return simpleName;
    }

    public static String getTypeNameWithoutGenerics(final String argFullType) {
        int find = argFullType.indexOf('<');
        if (find > 0) {
            return argFullType.substring(0, find);
        }
        return argFullType;
    }

    public static String getGenericsFromFullName(final String argFullType) {
        String generics = "";
        if (argFullType != null) {
            int find = argFullType.indexOf('<');
            if (find > 0) {
                generics = argFullType.substring(find + 1, argFullType.length() - 1);
            }
        }
        return generics;
    }

    /**
     * parse BlancoCgType and convert generics into genericsListTree,
     * if neccessary.
     *
     * @param argType
     * @return
     */
    public static BlancoCgType parseTypeWithGenerics(final BlancoCgType argType) {
        BlancoCgType targetType = argType;
        if (argType.getGenericsTree() == null || argType.getGenericsTree().size() == 0) {
            /*
             * genericsTree がなかった場合は generics 文字列から作成してみる
             */
            if (BlancoStringUtil.null2Blank(argType.getGenerics()).length() > 0) {
                String strType = argType.getName() + "<" + argType.getGenerics() + ">";
                targetType = BlancoCgSourceUtil.parseTypeWithGenerics(strType, BlancoCgObjectFactory.getInstance());
            }
        }
        return targetType;
    }

    /**
     * parse type string into cgType tree.
     *
     * @param argTypeWithGenerics
     * @param argCgFactory
     * @return
     */
    public static BlancoCgType parseTypeWithGenerics(
            final String argTypeWithGenerics,
            final BlancoCgObjectFactory argCgFactory
    ) {
        BlancoCgType cgType = null;
        if (BlancoStringUtil.null2Blank(argTypeWithGenerics).length() > 0) {
            String simpleType = getTypeNameWithoutGenerics(argTypeWithGenerics);
            String remainedGenerics = getGenericsFromFullName(argTypeWithGenerics);
//            System.out.println("cg: parseTypeWithGenerics simpleType = " + simpleType);
//            System.out.println("cg: parseTypeWithGenerics remainedGenerics = " + remainedGenerics);

            cgType = argCgFactory.createType(simpleType);
            String [] genericsList = divideGenerics(remainedGenerics);
            for ( ; genericsList[0].length() > 0; ) {
//                System.out.println("divided : first = " + genericsList[0]);
//                System.out.println("divided : remained = " + genericsList[1]);
                BlancoCgType nextCgType = parseTypeWithGenerics(genericsList[0], argCgFactory);
                if (nextCgType == null) {
                    break;
                }
                cgType.getGenericsTree().add(nextCgType);
                genericsList = divideGenerics(genericsList[1]);
            }
        }
        return cgType;
    }

    private static String [] divideGenerics(final String argFullString) {
        String [] divided = new String[2];
        String firstGenerics = "";
        String remainedGenerics = "";
        if (argFullString != null && argFullString.length() > 0) {
            int findLt = argFullString.indexOf('<');
            int findComma = argFullString.indexOf(',');
            if (findComma == -1) {
                firstGenerics = argFullString;
            } else if (findLt != -1 && findLt < findComma) {
                int findGt = argFullString.indexOf('>');
                if (findGt == -1) { // Error
                    firstGenerics = argFullString;
                } else {
                    int endPoint = searchParenthesis(argFullString, '<', findLt, '>', findGt, -1);
                    firstGenerics = argFullString.substring(0, endPoint + 1);
                    int nextStartPoint = argFullString.indexOf(',', endPoint + 1);
                    if (nextStartPoint != -1) {
                        remainedGenerics = argFullString.substring(nextStartPoint + 1).trim();
                    }
                }
            } else {
                // ここまでが一区切り
                firstGenerics = argFullString.substring(0, findComma);
                remainedGenerics = argFullString.substring(findComma + 1).trim();
            }
        }
        divided[0] = firstGenerics;
        divided[1] = remainedGenerics;
        return divided;
    }

    private static int searchParenthesis(
            final String targetString,
            final char startChar,
            final int startIndex,
            final char endChar,
            final int endIndex,
            final int prevStartIndex
    ) {
        int nextEndIndex = endIndex;
        int foundPoint = targetString.indexOf(startChar, startIndex + 1);
//        System.out.println("searchParenthesis: " + targetString);
//        System.out.println("startIndex: " + startIndex);
//        System.out.println("endIndex: " + endIndex);
//        System.out.println("prevStartIndex: " + prevStartIndex);
//        System.out.println("foundPoint: " + foundPoint);
        if (foundPoint != -1 && foundPoint < endIndex) {
            nextEndIndex = searchParenthesis(targetString, startChar, foundPoint, endChar, endIndex, startIndex);
            int nextEndPoint = targetString.indexOf(endChar, endIndex + 1);
            if (nextEndPoint != -1) {
                nextEndIndex = searchParenthesis(targetString, startChar, endIndex + 1, endChar, nextEndPoint, prevStartIndex);
            }
        }
        return nextEndIndex;
    }

    /**
     * Expand type like java style with generics
     *
     * @param argType
     * @return
     */
    public static String extendTypeWithGenerics(
            final BlancoCgType argType
    ) {
        final StringBuffer buf = new StringBuffer();
        buf.append(BlancoNameUtil.trimJavaPackage(argType.getName()));

        // 配列を展開します。
        if (argType.getArray()) {
            for (int dimension = 0; dimension < argType.getArrayDimension(); dimension++) {
                buf.append("[]");
            }
        }

        // ジェネリクスを展開します。
        if (argType.getGenericsTree() != null && argType.getGenericsTree().size() > 0) {
            buf.append("<");
            int count = 0;
            for (BlancoCgType nextCgType : argType.getGenericsTree()) {
                if (count > 0) {
                    buf.append(", ");
                }
                buf.append(extendTypeWithGenerics(nextCgType));
                count++;
            }
            buf.append(">");
        }
        return buf.toString();
    }
}

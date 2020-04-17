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
package blanco.cg.transformer.ts;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * BlancoCgMethodをソースコードに展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgMethodTsSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.TS;

    /**
     * ここでメソッドを展開します。
     *
     * @param cgMethod
     *            処理対象となるメソッド。
     * @param argSourceFile
     *            ソースファイル。
     * @param argSourceLines
     *            出力先行リスト。
     * @param argIsInterface
     *            インタフェースかどうか。クラスの場合にはfalse。インタフェースの場合にはtrue。
     */
    public void transformMethod(final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile,
            final List<String> argSourceLines,
            final boolean argIsInterface) {
        if (BlancoStringUtil.null2Blank(cgMethod.getName()).length() == 0) {
            throw new IllegalArgumentException("メソッドの名前に適切な値が設定されていません。");
        }
        if (cgMethod.getReturn() == null) {
            // それはありえます。voidの場合にはnullが指定されるのです。
        }

        // 改行を付与。
        argSourceLines.add("");

        prepareExpand(cgMethod, argSourceFile);

        // 情報が一式そろったので、ソースコードの実際の展開を行います。

        // 次に LangDocをソースコード形式に展開。
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgMethod
                .getLangDoc(), argSourceLines);

        // アノテーションを展開。
        expandAnnotationList(cgMethod, argSourceLines);

        // メソッドの本体部分を展開。
        expandMethodBody(cgMethod, argSourceLines, argIsInterface);
    }

    /**
     * ソースコード展開に先立ち、必要な情報の収集を行います。
     *
     * @param cgMethod
     *            メソッドオブジェクト。
     * @param argSourceFile
     *            ソースファイル。
     */
    private void prepareExpand(final BlancoCgMethod cgMethod,
            final BlancoCgSourceFile argSourceFile) {
        // 最初にメソッド情報をLangDocに展開。
        if (cgMethod.getLangDoc() == null) {
            // LangDoc未指定の場合にはこちら側でインスタンスを生成。
            cgMethod.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgMethod.getLangDoc().getParameterList() == null) {
            cgMethod.getLangDoc().setParameterList(
                    new ArrayList<BlancoCgParameter>());
        }
        if (cgMethod.getLangDoc().getThrowList() == null) {
            cgMethod.getLangDoc().setThrowList(
                    new ArrayList<BlancoCgException>());
        }
        if (cgMethod.getLangDoc().getTitle() == null) {
            cgMethod.getLangDoc().setTitle(cgMethod.getDescription());
        }

        for (BlancoCgParameter cgParameter : cgMethod.getParameterList()) {
            // import文に型を追加。
            argSourceFile.getImportList().add(cgParameter.getType().getName());

            // 言語ドキュメントにパラメータを追加。
            cgMethod.getLangDoc().getParameterList().add(cgParameter);
        }

        if (cgMethod.getReturn() != null) {
            // import文に型を追加。
            argSourceFile.getImportList().add(
                    cgMethod.getReturn().getType().getName());

            // 言語ドキュメントにreturnを追加。
            cgMethod.getLangDoc().setReturn(cgMethod.getReturn());
        }

        // 例外についてLangDoc構造体に展開
        for (BlancoCgException cgException : cgMethod.getThrowList()) {
            // import文に型を追加。
            argSourceFile.getImportList().add(cgException.getType().getName());

            // 言語ドキュメントに例外を追加。
            cgMethod.getLangDoc().getThrowList().add(cgException);
        }
    }

    /**
     * メソッドの本体部分を展開します。
     *
     * @param cgMethod
     *            メソッドオブジェクト。
     * @param argSourceLines
     *            ソースコード。
     * @param argIsInterface
     *            インタフェースとして展開するかどうか。
     */
    private void expandMethodBody(final BlancoCgMethod cgMethod,
            final List<String> argSourceLines,
            final boolean argIsInterface) {

        boolean isSetter = false;
        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
            // TypeScript ではデフォルトでpublicとなります。
            if (!cgMethod.getAccess().equals("public")) {
                buf.append(cgMethod.getAccess() + " ");
                if (cgMethod.getAccess().equals("set")) {
                    isSetter = true;
                }
            }
        }

        if (cgMethod.getAbstract() && argIsInterface == false) {
            // ※インタフェースの場合には abstractは付与しません。
            buf.append("abstract ");
        }

        buf.append(cgMethod.getName() + "(");
        for (int index = 0; index < cgMethod.getParameterList().size(); index++) {
            final BlancoCgParameter cgParameter = cgMethod
                    .getParameterList().get(index);
            if (cgParameter.getType() == null) {
                throw new IllegalArgumentException("メソッド["
                        + cgMethod.getName() + "]のパラメータ["
                        + cgParameter.getName() + "]に型がnullが与えられました。");
            }

            if (index != 0) {
                buf.append(", ");
            }

            buf.append(cgParameter.getName() + ": ");

            buf.append(BlancoCgTypeTsSourceExpander
                    .toTypeString(cgParameter.getType()));

            /*
             * TypeScript では複数の型が指定できるが、現時点では対応しない。
             * Nullable に対しては、undefined を追加する。
             * by tueda, 2020/03/15
             */
            if (!cgParameter.getNotnull()) {
                // nullable
                buf.append(" | undefined");
            }

            // デフォルト値の指定がある場合にはこれを展開します。
            if (BlancoStringUtil.null2Blank(cgParameter.getDefault()).length() > 0) {
                buf.append(" = " + cgParameter.getDefault());
            }
        }
        buf.append(")");

        if (cgMethod.getConstructor()) {
            // コンストラクタの場合には、戻り値は存在しません。
            // このため、ここでは何も出力しません。
        } else {
            if (cgMethod.getReturn() != null
                    && cgMethod.getReturn().getType() != null) {
                buf.append(": " + BlancoCgTypeTsSourceExpander.toTypeString(cgMethod
                        .getReturn().getType()));
                /*
                 * TypeScript では複数の型が指定できるが、現時点では対応しない。
                 * Nullable に対しては、undefined を追加する。
                 * by tueda, 2020/03/15
                 */
                if (!cgMethod.getNotnull()) {
                    // nullable
                    buf.append(" | undefined");
                }
            } else {
                /*
                 * Setter には void は指定できません。
                 */
                if (!isSetter) {
                    buf.append(": void");
                }
            }
        }

        // TypeScript では throws 節は不要です。

        if (cgMethod.getAbstract() || argIsInterface) {
            // 抽象メソッドの場合には、メソッドの本体を展開しません。
            buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
            argSourceLines.add(buf.toString());
        } else {
            // メソッドブロックの開始。
            buf.append(" {");

            // ここでいったん、行を確定。
            argSourceLines.add(buf.toString());

            // 親クラスメソッド実行機能の展開。
            if (BlancoStringUtil.null2Blank(cgMethod.getSuperclassInvocation())
                    .length() > 0) {
                // super(引数) などが含まれます。
                argSourceLines.add(cgMethod.getSuperclassInvocation()
                        + BlancoCgLineUtil.getTerminator(TARGET_LANG));
            }

            // typescript ではパラメータの非null制約はコンパイル時にcheckされるので、例外処理を実装する必要はありません。

            // 行を展開します。
            expandLineList(cgMethod, argSourceLines);

            // メソッドブロックの終了。
            argSourceLines.add("}");
        }
    }

    /**
     * アノテーションを展開します。
     *
     * @param cgMethod
     *            メソッド。
     * @param argSourceLines
     *            ソースコード。
     */
    private void expandAnnotationList(final BlancoCgMethod cgMethod,
            final List<String> argSourceLines) {

        for (String strAnnotation : cgMethod.getAnnotationList()) {
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * 行を展開します。
     *
     * @param cgMethod
     *            メソッド情報。
     * @param argSourceLines
     *            出力行リスト。
     */
    private void expandLineList(final BlancoCgMethod cgMethod,
            final List<String> argSourceLines) {
        for (String strLine : cgMethod.getLineList()) {
            argSourceLines.add(strLine);
        }
    }
}

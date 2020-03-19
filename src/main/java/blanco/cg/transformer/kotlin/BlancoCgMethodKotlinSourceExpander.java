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
package blanco.cg.transformer.kotlin;

import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgException;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgMethodをソースコードに展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgMethodKotlinSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.KOTLIN;

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
            final List<java.lang.String> argSourceLines,
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
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgMethod
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
                    new ArrayList<blanco.cg.valueobject.BlancoCgParameter>());
        }
        if (cgMethod.getLangDoc().getThrowList() == null) {
            cgMethod.getLangDoc().setThrowList(
                    new ArrayList<blanco.cg.valueobject.BlancoCgException>());
        }
        if (cgMethod.getLangDoc().getTitle() == null) {
            cgMethod.getLangDoc().setTitle(cgMethod.getDescription());
        }

        for (BlancoCgParameter cgParameter : cgMethod.getParameterList()) {
            // import文に型を追加。
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, cgParameter.getType().getName())) {
                argSourceFile.getImportList().add(cgParameter.getType().getName());
            }

            // 言語ドキュメントにパラメータを追加。
            cgMethod.getLangDoc().getParameterList().add(cgParameter);
        }

        if (cgMethod.getReturn() != null) {
            // import文に型を追加。
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, cgMethod.getReturn().getType().getName())) {
                argSourceFile.getImportList().add(
                        cgMethod.getReturn().getType().getName());
            }

            // 言語ドキュメントにreturnを追加。
            cgMethod.getLangDoc().setReturn(cgMethod.getReturn());
        }

        // 例外についてLangDoc構造体に展開
        for (BlancoCgException cgException : cgMethod.getThrowList()) {
            // import文に型を追加。
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, cgException.getType().getName())) {
                argSourceFile.getImportList().add(cgException.getType().getName());
            }

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
            final List<java.lang.String> argSourceLines,
            final boolean argIsInterface) {
        final StringBuffer buf = new StringBuffer();

        // static initializer の場合は修飾子はつきません。
        if (!cgMethod.getStaticInitializer()) {
            if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
                // kotlin ではデフォルトでpublicとなります。
                if (!cgMethod.getAccess().equals("public")) {
                    if (cgMethod.getStaticInitializer() == false) {
                        buf.append(cgMethod.getAccess() + " ");
                    }
                }
            }

            if (cgMethod.getOverride()) {
                // 親クラスのメソッドを override する場合は修飾子 override が必要です。
                buf.append("override ");
            }

            if (cgMethod.getAbstract() && argIsInterface == false) {
                // ※インタフェースの場合には abstractは付与しません。
                buf.append("abstract ");
            }
            // kotlin では通常クラスのメソッドはデフォルトで final です。
            if (!cgMethod.getFinal() && argIsInterface == false) {
                // ※インタフェースの場合には デフォルトで open となります。
                buf.append("open ");
            }

            buf.append("fun ");

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

                buf.append(cgParameter.getName() + " : ");

                buf.append(BlancoCgTypeKotlinSourceExpander
                        .toTypeString(cgParameter.getType()));
                if (!cgParameter.getNotnull()) {
                    // nullable
                    buf.append("?");
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
                    buf.append(" : " + BlancoCgTypeKotlinSourceExpander.toTypeString(cgMethod
                            .getReturn().getType()));
                }
            }
        } else {
            // static initializer
            buf.append("init");
        }

        // kotlin では throws 節は不要です。

        if (cgMethod.getAbstract()) {
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

            // kotlin ではパラメータの非null制約はコンパイル時にcheckされるので、例外処理を実装する必要はありません。

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
            final List<java.lang.String> argSourceLines) {

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
            final List<java.lang.String> argSourceLines) {
        for (String strLine : cgMethod.getLineList()) {
            argSourceLines.add(strLine);
        }
    }
}

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

import java.util.ArrayList;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgMethodをソースコードに展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgMethodJavaSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.JAVA;

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
        new BlancoCgLangDocJavaSourceExpander().transformLangDoc(cgMethod
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
            // generics があればそれを genericsListTree に変換
            BlancoCgType cgType = BlancoCgSourceUtil.parseTypeWithGenerics(cgParameter.getType());
            cgParameter.setType(cgType);

            // import文に型を追加。
            BlancoCgSourceFileJavaSourceExpander.typeToImport(cgType, argSourceFile);

            // 言語ドキュメントにパラメータを追加。
            cgMethod.getLangDoc().getParameterList().add(cgParameter);
        }

        // add virtual parameter (generic) to langDoc.
        if (cgMethod.getVirtualParameterList() != null && cgMethod.getVirtualParameterList().size() > 0) {
            cgMethod.getLangDoc().getVirtualParameterList().addAll(cgMethod.getVirtualParameterList());
        }

        if (cgMethod.getReturn() != null) {
            BlancoCgType cgType = BlancoCgSourceUtil.parseTypeWithGenerics(cgMethod.getReturn().getType());
            cgMethod.getReturn().setType(cgType);

            // import文に型を追加。
            BlancoCgSourceFileJavaSourceExpander.typeToImport(cgMethod.getReturn().getType(), argSourceFile);

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
            final List<java.lang.String> argSourceLines,
            final boolean argIsInterface
    ) {
        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgMethod.getAccess()).length() > 0) {
            if (argIsInterface && cgMethod.getAccess().equals("public")) {
                // インタフェース且つpublicの場合には出力を抑制します。
                // これはCheckstyle対策となります。
            } else {
                if (cgMethod.getStaticInitializer() == false) {
                    buf.append(cgMethod.getAccess() + " ");
                }
            }
        }

        if (cgMethod.getAbstract() && argIsInterface == false) {
            // ※インタフェースの場合には abstractは付与しません。
            buf.append("abstract ");
        }
        if (cgMethod.getStatic()) {
            buf.append("static ");
        }
        if (cgMethod.getStaticInitializer()) {
            buf.append("static");
        }
        if (cgMethod.getFinal() && argIsInterface == false) {
            // ※インタフェースの場合には finalは付与しません。
            buf.append("final ");
        }

        if (cgMethod.getConstructor()) {
            // コンストラクタの場合には、戻り値は存在しません。
            // このため、ここでは何も出力しません。
        } else if (cgMethod.getStaticInitializer()) {
            // static initializer の場合には、戻り値は存在しません。
            // このため、ここでは何も出力しません。
        } else {
            if (cgMethod.getVirtualParameterList() != null && cgMethod.getVirtualParameterList().size() > 0 ) {
                // <> は入っていない前提
                buf.append("<");
                int count = 0;
                for (BlancoCgVirtualParameter vparm : cgMethod.getVirtualParameterList()) {
                    if (count > 0) {
                        buf.append(", ");
                    }
                    buf.append(vparm.getType().getName());
                    count++;
                }
                buf.append("> ");
            } else if (cgMethod.getVirtualParameterDefinition() != null && cgMethod.getVirtualParameterDefinition().length() > 0) {
                // < > は入っている前提
                buf.append(cgMethod.getVirtualParameterDefinition() + " ");
            }

            if (cgMethod.getReturn() != null
                    && cgMethod.getReturn().getType() != null) {
                buf.append(BlancoCgTypeJavaSourceExpander.toTypeString(cgMethod
                        .getReturn().getType())
                        + " ");
            } else {
                buf.append("void ");
            }
        }

        if (cgMethod.getStaticInitializer() == false) {
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

                if (cgParameter.getFinal()) {
                    buf.append("final ");
                }
                buf.append(BlancoCgTypeJavaSourceExpander
                        .toTypeString(cgParameter.getType()));
                buf.append(" ");
                buf.append(cgParameter.getName());
            }
            buf.append(")");
        }

        // 例外スローを展開。
        expandThrowList(cgMethod, buf);

        if (cgMethod.getAbstract() || argIsInterface) {
            // 抽象メソッドまたはインタフェースの場合には、メソッドの本体を展開しません。
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

            // パラメータの非null制約の展開。
            expandParameterCheck(cgMethod, argSourceLines);

            // 行を展開します。
            expandLineList(cgMethod, argSourceLines);

            // メソッドブロックの終了。
            argSourceLines.add("}");
        }
    }

    /**
     * 例外スローを展開します。
     *
     * @param cgMethod
     *            メソッド。
     * @param buf
     *            出力バッファ。
     */
    private void expandThrowList(final BlancoCgMethod cgMethod,
            final StringBuffer buf) {
        for (int index = 0; index < cgMethod.getThrowList().size(); index++) {
            final BlancoCgException cgException = cgMethod.getThrowList().get(
                    index);
            if (index == 0) {
                buf.append(" throws ");
            } else {
                buf.append(", ");
            }
            // 言語ドキュメント処理においては、blancoCgのTypeに関する共通処理を利用することはできません。
            // 個別に記述を行います。
            buf.append(BlancoNameUtil.trimJavaPackage(cgException.getType()
                    .getName()));
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
        if (cgMethod.getOverride()) {
            // Java言語では overrideはアノテーションで表現します。
            argSourceLines.add("@Override");
        }

        for (String strAnnotation : cgMethod.getAnnotationList()) {
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * パラメータの非null制約の展開。
     *
     * @param cgMethod
     *            メソッド。
     * @param argSourceLines
     *            ソースコード。
     */
    private void expandParameterCheck(final BlancoCgMethod cgMethod,
            final List<java.lang.String> argSourceLines) {
        boolean isProcessed = false;
        for (BlancoCgParameter cgParameter : cgMethod.getParameterList()) {
            if (cgParameter.getNotnull()) {
                isProcessed = true;

                argSourceLines.add(BlancoCgLineUtil.getIfBegin(TARGET_LANG,
                        cgParameter.getName() + " == null"));
                argSourceLines.add("throw new IllegalArgumentException(\"メソッド["
                        + cgMethod.getName() + "]のパラメータ["
                        + cgParameter.getName()
                        + "]にnullが与えられました。しかし、このパラメータにnullを与えることはできません。\");");
                argSourceLines.add(BlancoCgLineUtil.getIfEnd(TARGET_LANG));
            }
        }

        if (isProcessed) {
            // パラメータチェックが展開された場合には空行を挿入します。
            argSourceLines.add("");
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

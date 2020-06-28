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
import java.util.Arrays;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgInterfaceをソースコードに展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgInterfaceKotlinSourceExpander {

    /**
     * ここでinterfaceを展開します。
     *
     * @param cgInterface
     *            処理対象となるインタフェース。
     * @param argSourceLines
     *            ソースコード。
     */
    public void transformInterface(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // インタフェースの場合には フィールドやメソッドからpublicが除外されます。

        // 最初にインタフェース情報をLangDocに展開。
        if (cgInterface.getLangDoc() == null) {
            // LangDoc未指定の場合にはこちら側でインスタンスを生成。
            cgInterface.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgInterface.getLangDoc().getTitle() == null) {
            cgInterface.getLangDoc().setTitle(cgInterface.getDescription());
        }

        // 次に LangDocをソースコード形式に展開。
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgInterface
                .getLangDoc(), argSourceLines);

        // アノテーションを展開。
        BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, cgInterface.getAnnotationList(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgInterface.getAccess()).length() > 0) {
            // kotlin ではデフォルトがpublic
            if (!"public".equals(cgInterface.getAccess())) {
                buf.append(cgInterface.getAccess() + " ");
            }
        }
        // staticやfinalは展開しません。
        buf.append("interface " + cgInterface.getName());

        // ここで親クラスを展開。
        expandExtendClassList(cgInterface, argSourceFile, buf);

        // ※ポイント: 親インタフェース展開は interfaceには存在しません。

        buf.append(" {");

        argSourceLines.add(buf.toString());

        // ここでフィールドを展開。
        expandFieldList(cgInterface, argSourceFile, argSourceLines);

        // ここでメソッドを展開。
        expandMethodList(cgInterface, argSourceFile, argSourceLines);

        argSourceLines.add("}");
    }

    /**
     * 親クラスを展開します。
     *
     * @param cgClass
     * @param argSourceFile
     * @param argBuf
     * @return
     */
    private void expandExtendClassList(final BlancoCgInterface cgClass,
                                          final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = cgClass.getExtendClassList().get(index);

            // import文に型を追加。
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, type.getName())) {
                argSourceFile.getImportList().add(type.getName());
            }

            if (index == 0) {
                argBuf.append(" : "
                        + BlancoCgTypeKotlinSourceExpander.toTypeString(type) + "()");
            } else {
                throw new IllegalArgumentException("Kotlin 言語では継承は一回しか実施できません。");
            }
        }
    }

    /**
     * 含まれる各々のフィールドを展開します。
     *
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandFieldList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getFieldList() == null) {
            // フィールドのリストにnullが与えられました。
            // かならずフィールドのリストにはListをセットしてください。
            throw new IllegalArgumentException("フィールドのリストにnullが与えられました。");
        }

        for (BlancoCgField cgField : cgInterface.getFieldList()) {
            new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, true);
        }
    }

    /**
     * 含まれる各々のメソッドを展開します。
     *
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandMethodList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getMethodList() == null) {
            throw new IllegalArgumentException("メソッドのリストにnullが与えられました。");
        }
        for (BlancoCgMethod cgMethod : cgInterface.getMethodList()) {
            new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, true);
        }
    }
}

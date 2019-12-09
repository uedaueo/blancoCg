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
 * Copyright 2019 Ueda Ueo
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

import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgFieldをソースコードへと展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgFieldKotlinSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.KOTLIN;

    /**
     * ここでフィールドを展開します。
     *
     * @param cgField
     *            処理対象となるフィールド。
     * @param argSourceFile
     *            ソースファイル。
     * @param argSourceLines
     *            出力先行リスト。
     * @param argIsInterface
     *            インタフェースかどうか。クラスの場合にはfalse。インタフェースの場合にはtrue。
     */
    public void transformField(final BlancoCgField cgField, final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines, final boolean argIsInterface) {
        if (BlancoStringUtil.null2Blank(cgField.getName()).length() == 0) {
            throw new IllegalArgumentException("フィールドの名前に適切な値が設定されていません。");
        }
        if (BlancoStringUtil.null2Blank(cgField.getType().getName()).length() == 0) {
            throw new IllegalArgumentException("フィールド[" + cgField.getName() + "]の型が適切な値が設定されていません。");
        }

        // 有無をいわさず改行を付与します。
        argSourceLines.add("");

        // 最初にフィールド情報をLangDocに展開。
        if (cgField.getLangDoc() == null) {
            // LangDoc未指定の場合にはこちら側でインスタンスを生成。
            cgField.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgField.getLangDoc().getTitle() == null) {
            cgField.getLangDoc().setTitle(cgField.getDescription());
        }

        // 次に LangDocをソースコード形式に展開。
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgField.getLangDoc(), argSourceLines);

        // アノテーションを展開。
        expandAnnotationList(cgField, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgField.getAccess()).length() > 0) {
            // kotlin ではデフォルトで public となります。
            if (!cgField.getAccess().equals("public")) {
                buf.append(cgField.getAccess() + " ");
            }
        }

        // kotlin ではデフォルトでfinalとなります。
        // ただし override が指定されている場合は final と明示しなければなりません。
        if (cgField.getOverride()) {
            if (cgField.getFinal()) {
                buf.append("final ");
            }
            buf.append("override "); // override はデフォルトで open です。
        } else if (!cgField.getFinal()) {
            buf.append("open ");
        }

        // import文に型を追加。
        argSourceFile.getImportList().add(cgField.getType().getName());

        // フィールド生成の本体部分を展開します。
        if (cgField.getConst()) {
            buf.append("val ");
        } else {
            buf.append("var ");
        }
        buf.append(cgField.getName() + " : ");
        buf.append(BlancoCgTypeKotlinSourceExpander.toTypeString(cgField.getType()));

        if (!cgField.getNotnull()) {
            // nullable
            buf.append("?");
        }

        // デフォルト値の指定がある場合にはこれを展開します。
        if (BlancoStringUtil.null2Blank(cgField.getDefault()).length() > 0) {
            buf.append(" = " + cgField.getDefault());
        }
        buf.append(BlancoCgLineUtil.getTerminator(TARGET_LANG));
        argSourceLines.add(buf.toString());
    }

    /**
     * アノテーションを展開します。
     *
     * @param cgField
     *            フィールド。
     * @param argSourceLines
     *            ソースコード。
     */
    private void expandAnnotationList(final BlancoCgField cgField, final List<java.lang.String> argSourceLines) {
        for (String strAnnotation : cgField.getAnnotationList()) {
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add("@" + strAnnotation);
        }
    }
}

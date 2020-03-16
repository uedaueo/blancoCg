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
package blanco.cg.transformer.ts;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;

/**
 * BlancoCgFieldをソースコードへと展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgFieldTsSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.TS;

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
                               final List<String> argSourceLines, final boolean argIsInterface) {
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
        new BlancoCgLangDocTsSourceExpander().transformLangDoc(cgField.getLangDoc(), argSourceLines);

        // アノテーションを展開。
        expandAnnotationList(cgField, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        String access = cgField.getAccess();
        if (BlancoStringUtil.null2Blank(access).length() > 0) {
            // TypeScript ではデフォルトで public となります。
            if (!access.equals("public")) {
                if (!argIsInterface) {
                    buf.append(cgField.getAccess() + " ");
                } else {
                    throw new IllegalArgumentException("TypeScript では Interface にはアクセサを設定できません（フィールド[" + cgField.getName() + "]）。");
                }
            }
        }

        if (cgField.getStatic()) {
            if (!argIsInterface) {
                buf.append("static ");
            } else {
                throw new IllegalArgumentException("TypeScript では Interface には static field を設定できません（フィールド[" + cgField.getName() + "]）。");
            }
        }

        /*
         * final:
         * Java では class field として定数を定義したい時に final を宣言する。
         * TypeScript で同様の事を実現するのは readonly。
         */
        if (cgField.getFinal() || cgField.getConst()) {
            buf.append("readonly ");
        }

        /*
         * import文に型を追加。ただし TypeScript ではこの import 文は使用されません。
         */
        argSourceFile.getImportList().add(cgField.getType().getName());

        /*
         * Nullable が設定されている、または
         * Interface でないのにデフォルト値が設定されていない場合
         */
        if (!cgField.getNotnull() ||
                (!argIsInterface && BlancoStringUtil.null2Blank(cgField.getDefault()).length() == 0)) {
            // nullable
            buf.append(cgField.getName() + "?: ");
        } else {
            buf.append(cgField.getName() + ": ");
        }
        buf.append(BlancoCgTypeTsSourceExpander.toTypeString(cgField.getType()));

        // デフォルト値の指定がある場合にはこれを展開します。
        if (BlancoStringUtil.null2Blank(cgField.getDefault()).length() > 0) {
            // TypeScript の Interface はデフォルト値を持てません。
            if (!argIsInterface) {
                buf.append(" = " + cgField.getDefault());
            } else {
                throw new IllegalArgumentException("TypeScript では Interface にはデフォルト値を設定できません（フィールド[" + cgField.getName() + "]）。");
            }
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
    private void expandAnnotationList(final BlancoCgField cgField, final List<String> argSourceLines) {
        for (String strAnnotation : cgField.getAnnotationList()) {
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add("@" + strAnnotation);
        }
    }
}

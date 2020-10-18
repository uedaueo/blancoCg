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

import java.util.List;

import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.BlancoStringUtil;

/**
 * BlancoCgClassをソースコードへと展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgClassJavaSourceExpander {

    /**
     * ここでClassを展開します。
     *
     * @param cgClass
     *            処理対象となるクラス。
     * @param argSourceLines
     *            ソースコード。
     */
    public void transformClass(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // 最初にクラス情報をLangDocに展開。
        if (cgClass.getLangDoc() == null) {
            // LangDoc未指定の場合にはこちら側でインスタンスを生成。
            cgClass.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgClass.getLangDoc().getTitle() == null) {
            cgClass.getLangDoc().setTitle(cgClass.getDescription());
        }

        // 次に LangDocをソースコード形式に展開。
        new BlancoCgLangDocJavaSourceExpander().transformLangDoc(cgClass
                .getLangDoc(), argSourceLines);

        // アノテーションを展開。
        expandAnnotationList(cgClass, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgClass.getAccess()).length() > 0) {
            buf.append(cgClass.getAccess() + " ");
        }
        if (cgClass.getAbstract()) {
            buf.append("abstract ");
        }
        if (cgClass.getFinal()) {
            buf.append("final ");
        }
        buf.append("class " + cgClass.getName());

        // クラスのGenericを展開
        if (cgClass.getGenerics() != null && cgClass.getGenerics().length() > 0) {
            buf.append("<" + cgClass.getGenerics() + ">");
        }

        // 親クラスを展開。
        expandExtendClassList(cgClass, argSourceFile, buf);

        // 親インタフェースを展開。
        expandImplementInterfaceList(cgClass, argSourceFile, buf);

        // クラスのブロックの開始。
        buf.append(" {");

        // 行を確定して書き出しを実施。
        argSourceLines.add(buf.toString());

        // ここで PlainText を展開
        expandPlainText(cgClass, argSourceLines);

        // ここで列挙体を展開。
        expandEnumList(cgClass, argSourceFile, argSourceLines);

        // ここでフィールドを展開。
        expandFieldList(cgClass, argSourceFile, argSourceLines);

        // ここでメソッドを展開。
        expandMethodList(cgClass, argSourceFile, argSourceLines);

        // クラスのブロックの終了。
        argSourceLines.add("}");
    }

    /**
     * アノテーションを展開します。
     *
     * @param cgClass
     *            クラス。
     * @param argSourceLines
     *            ソースコード。
     */
    private void expandAnnotationList(final BlancoCgClass cgClass,
            final List<java.lang.String> argSourceLines) {
        for (String strAnnotation : cgClass.getAnnotationList()) {
            // Java言語のAnnotationは @ から記述します。
            argSourceLines.add("@" + strAnnotation);
        }
    }

    /**
     * 親クラスを展開します。
     *
     * ※BlancoCgInterface展開の際に、このメソッドを共通処理として呼び出してはなりません。
     * その共通化は、かえって理解を妨げると判断しています。
     *
     * @param cgClass
     *            クラスのバリューオブジェクト。
     * @param argBuf
     *            出力先文字列バッファ。
     */
    private void expandExtendClassList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getExtendClassList().get(index));
            cgClass.getExtendClassList().set(index, type);

            // import文に型を追加。
            BlancoCgSourceFileJavaSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" extends "
                        + BlancoCgTypeJavaSourceExpander.toTypeString(type));
            } else {
                throw new IllegalArgumentException("Java言語では継承は一回しか実施できません。");
            }
        }
    }

    /**
     * 親インタフェースを展開します。
     *
     * @param cgClass
     *            処理中のクラス。
     * @param argBuf
     *            出力先文字列バッファ。
     */
    private void expandImplementInterfaceList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        for (int index = 0; index < cgClass.getImplementInterfaceList().size(); index++) {
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getImplementInterfaceList().get(index));
            cgClass.getImplementInterfaceList().set(index, type);

            // import文に型を追加。
            BlancoCgSourceFileJavaSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" implements ");
            } else {
                argBuf.append(", ");
            }
            argBuf.append(BlancoCgTypeJavaSourceExpander.toTypeString(type));
        }
    }

    /**
     * クラスに含まれる各々の列挙体を展開します。
     *
     * @param cgClass
     *            処理中のクラス。
     * @param argSourceFile
     *            ソースファイル。
     * @param argSourceLines
     *            ソースコード行リスト。
     */
    private void expandEnumList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getEnumList() == null) {
            return;
        }

        for (BlancoCgEnum cgEnum : cgClass.getEnumList()) {
            new BlancoCgEnumJavaSourceExpander().transformEnum(cgEnum,
                    argSourceFile, argSourceLines);
        }
    }

    /**
     * クラスに含まれる各々のフィールドを展開します。
     *
     * TODO 定数宣言を優先して展開し、その後変数宣言を展開するなどの工夫が必要です。<br>
     * 現在は 登録順でソースコード展開します。
     *
     * @param cgClass
     *            処理中のクラス。
     * @param argSourceFile
     *            ソースファイル。
     * @param argSourceLines
     *            ソースコード行リスト。
     */
    private void expandFieldList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getFieldList() == null) {
            // フィールドのリストにnullが与えられました。
            // かならずフィールドのリストにはListをセットしてください。
            throw new IllegalArgumentException("フィールドのリストにnullが与えられました。");
        }

        for (BlancoCgField cgField : cgClass.getFieldList()) {
            // クラスのフィールドとして展開を行います。
            new BlancoCgFieldJavaSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, false);
        }
    }

    /**
     * クラスに含まれる各々のメソッドを展開します。
     *
     * @param cgClass
     *            処理中のクラス。
     * @param argSourceFile
     *            ソースファイル。
     * @param argSourceLines
     *            ソースコード行リスト。
     */
    private void expandMethodList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getMethodList() == null) {
            throw new IllegalArgumentException("メソッドのリストにnullが与えられました。");
        }
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // クラスのメソッドとして展開を行います。
            new BlancoCgMethodJavaSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, false);
        }
    }

    /**
     * Plain Text を展開します。
     *
     * @param cgClass
     * @param argSourceLines
     */
    private void expandPlainText(
            final BlancoCgClass cgClass,
            final List<String> argSourceLines) {
        List<String> plainTextList = cgClass.getPlainTextList();

        // 有無を言わさず改行を入れます
        argSourceLines.add("");

        for (String planText : plainTextList) {
            argSourceLines.add(planText);
        }
    }

}

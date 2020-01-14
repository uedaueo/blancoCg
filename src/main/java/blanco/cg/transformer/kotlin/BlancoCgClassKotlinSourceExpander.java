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

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoStringUtil;

import java.util.List;
import java.util.Map;

/**
 * BlancoCgClassをソースコードへと展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。
 *
 * @author IGA Tosiki
 */
class BlancoCgClassKotlinSourceExpander {

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
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgClass
                .getLangDoc(), argSourceLines);

        // アノテーションを展開。
        expandAnnotationList(cgClass, argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgClass.getAccess()).length() > 0) {
            // kotlin ではデフォルトがpublic
            if (!"public".equals(cgClass.getAccess())) {
                buf.append(cgClass.getAccess() + " ");
            }
        }
        if (cgClass.getAbstract()) {
            buf.append("abstract ");
        }
        // kotlin ではデフォルトが final
        if (!cgClass.getFinal()) {
            buf.append("open ");
        }
        buf.append("class " + cgClass.getName());

        // primary constructor を展開
        /*
         * kotlin ではプライマリコンストラクタは class 定義の一部として記述されます。
         */
        expandPrimaryConstructorList(cgClass, argSourceFile, buf, argSourceLines);

        // 親クラスを展開。
        boolean expanded = expandExtendClassList(cgClass, argSourceFile, buf);

        // 親インタフェースを展開。
        expandImplementInterfaceList(cgClass, argSourceFile, buf, expanded);

        // クラスのブロックの開始。
        buf.append(" {");

        // 行を確定して書き出しを実施。
        argSourceLines.add(buf.toString());

        // kotlinでは列挙型はクラスとして定義されますが、当面、自動生成の対象外とします。(tueda)

        // ここでフィールドとメソッドを展開。
        expandFieldAndMethodList(cgClass, argSourceFile, argSourceLines);

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
            // constractor 引数へのアノテーションはすべて改行する方針とします
            argSourceLines.add("    @" + strAnnotation);
        }
    }

    /**
     * kotlin のプライマリコンストラクタを展開します。
     *
     * @param cgClass
     *            クラスのバリューオブジェクト。
     * @param argSourceFile
     *            ソースファイル。
     * @param argBuf
     *            出力先文字列バッファ。
     */
    private void expandPrimaryConstructorList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final StringBuffer argBuf,
            final List<java.lang.String> argSourceLines) {
        List<blanco.cg.valueobject.BlancoCgField> constructorArgs = cgClass.getConstructorArgList();
        if (constructorArgs == null || constructorArgs.size() <= 0) {
            return;
        }

        argBuf.append(" constructor (");

        // Constractor 指定が存在する場合はここで一旦改行する
        argSourceLines.add(argBuf.toString());
        argBuf.delete(0, argBuf.length());

        int count = 0;
        for (blanco.cg.valueobject.BlancoCgField arg : constructorArgs) {
            final BlancoCgType type = arg.getType();
            // import文に型を追加
            argSourceFile.getImportList().add(type.getName());

            if (count != 0) {
                argBuf.append(",");
                argSourceLines.add(argBuf.toString());
                argBuf.delete(0, argBuf.length());
            }

            // アノテーションを展開。
            expandAnnotationList(arg, argSourceLines);

            // 変数が変更可能か不可かを設定します。
            // コンストラクタ引数なので、通常はvalで良いようには思います, tueda
            if (arg.getConst()) {
                argBuf.append("    val ");
            } else {
                argBuf.append("    var ");
            }
            argBuf.append(arg.getName() + " : " + BlancoCgTypeKotlinSourceExpander.toTypeString(type));
            if (!arg.getNotnull()) {
                // nullable
                argBuf.append("?");
            }

            // デフォルト値の指定がある場合にはこれを展開します。
            if (BlancoStringUtil.null2Blank(arg.getDefault()).length() > 0) {
                argBuf.append(" = " + arg.getDefault());
            }
            count++;
        }
        if (argBuf.length() > 0) {
            argSourceLines.add(argBuf.toString());
            argBuf.delete(0, argBuf.length());
        }
        argBuf.append(")");
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
    private boolean expandExtendClassList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        boolean expanded = false;
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = cgClass.getExtendClassList().get(index);

            // import文に型を追加。
            argSourceFile.getImportList().add(type.getName());

            if (index == 0) {
                argBuf.append(" : "
                        + BlancoCgTypeKotlinSourceExpander.toTypeString(type) + "()");
            } else {
                throw new IllegalArgumentException("Kotlin 言語では継承は一回しか実施できません。");
            }
            expanded = true;
        }
        return expanded;

    }

    /**
     * 親インタフェースを展開します。
     *  @param cgClass
     *            処理中のクラス。
     * @param argBuf
     * @param expanded
     */
    private void expandImplementInterfaceList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final StringBuffer argBuf,
            boolean expanded) {
        /*
         * 委譲リストを取得する
         */
        Map<String, String> delegateMap = cgClass.getDelegateMap();

        for (int index = 0; index < cgClass.getImplementInterfaceList().size(); index++) {
            final BlancoCgType type = cgClass.getImplementInterfaceList().get(
                    index);

            // import文に型を追加。
            argSourceFile.getImportList().add(type.getName());

            if (index == 0 && !expanded) {
                argBuf.append(" : ");
            } else {
                argBuf.append(", ");
            }

            String typeString = BlancoCgTypeKotlinSourceExpander.toTypeString(type);

            /*
             * 委譲があれば設定
             */
            String delegateArg = delegateMap.get(type.getName());
//            System.out.println("type: " + type.getName() + ", delegateArg: " + delegateArg);
            if (delegateArg != null && delegateArg.length() != 0) {
                /*
                 * プライマリコンストラクタに無ければ無視
                 */
                List<blanco.cg.valueobject.BlancoCgField> constructorArgs = cgClass.getConstructorArgList();
                boolean found = false;
                for (BlancoCgField arg : constructorArgs) {
                    if (delegateArg.equals(arg.getName())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    typeString += " by " + delegateArg;
                }
            }
            argBuf.append(typeString);
        }
    }

    /**
     * クラスに含まれる各々のフィールドとメソッドを展開します。
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
    private void expandFieldAndMethodList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getFieldList() == null) {
            // フィールドのリストにnullが与えられました。
            // かならずフィールドのリストにはListをセットしてください。
            throw new IllegalArgumentException("フィールドのリストにnullが与えられました。");
        }
        if (cgClass.getMethodList() == null) {
            throw new IllegalArgumentException("メソッドのリストにnullが与えられました。");
        }

        // kotlin では static なフィールドと Method は companion オブジェクト内に宣言するため、
        // フィールドの展開は2段階に分けます。
        // Java からの呼び出しがある場合には @JvmField アノテーションで対応して頂く想定です。

        // まず static フィールドを展開します。
        boolean foundStatic = false;
        for (BlancoCgField cgField : cgClass.getFieldList()) {
            if (cgField.getStatic()) {
                if (!foundStatic) {
                    argSourceLines.add("companion object {");
                    foundStatic = true;
                }
                new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                        argSourceFile, argSourceLines, false);
            }
        }

        // 次に static Method を展開します。
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // クラスのメソッドとして展開を行います。
            if (cgMethod.getStatic()) {
                if (!foundStatic) {
                    argSourceLines.add("companion object {");
                    foundStatic = true;
                }
                new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                        argSourceFile, argSourceLines, false);
            }
        }
        if (foundStatic) {
            argSourceLines.add("}");
        }

        // 非 static なフィールドを展開します。
        for (BlancoCgField cgField : cgClass.getFieldList()) {
            if (!cgField.getStatic()) {
                new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                        argSourceFile, argSourceLines, false);
            }
        }

        // 最後に非 static Method を展開します。
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // クラスのメソッドとして展開を行います。
            if (!cgMethod.getStatic()) {
                new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                        argSourceFile, argSourceLines, false);
            }
        }
    }
}

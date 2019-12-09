/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.cg;

import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.*;
import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Kotlin言語用の生成試験。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerKotlinTest extends TestCase {
    /**
     * open なクラスの展開試験。
     *
     * @throws Exception
     */
    public void testTransformer() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // ソースファイルを生成します。
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog", "テスト用のクラス");
        cgSourceFile.setEncoding("UTF-8");
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // 同じパッケージのインポート試験。
        cgSourceFile.getImportList().add("myprog.MyClass2");

        // クラスを生成します。
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "このクラスは、テストのためのクラスです。");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("java.lang.Thread"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("java.lang.Runnable"));

        // プライマリコンストラクタのテストです
        BlancoCgParameter constParam = new BlancoCgParameter();
        BlancoCgType constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("first");
        constType.setName("Int");

        // プライマリコンストラクタのテストです
        constParam = new BlancoCgParameter();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("second");
        constType.setName("kotlin.Number");

        // プライマリコンストラクタのテストです
        constParam = new BlancoCgParameter();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("third");
        constType.setName("kotlin.collections.List");
        constType.setGenerics("String");

        // 列挙体（kotlin言語の生成では列挙体は当面無視します）
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "列挙体の試験。");
        cgClass.getEnumList().add(cgEnum);
        cgEnum.getElementList().add(cgFactory.createEnumElement("Red", "あか"));
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yerrow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // フィールドを生成します。
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "日付フィールドの試験です。");
        cgClass.getFieldList().add(cgField);
        cgField.setFinal(true);
        cgField.setDefault("Date()");

        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "java.util.Date", "日付フィールドの試験v2です。");
        cgClass.getFieldList().add(cgField2);
        cgField2.getType().setArray(true);
        cgField2.getType().setArrayDimension(2);

        final BlancoCgField cgField3 = cgFactory.createField("myField3", "String", "staticフィールドの試験です。");
        cgClass.getFieldList().add(cgField3);
        cgField3.setAccess("public");
        cgField3.setStatic(true);
        cgField3.setFinal(true);
        cgField3.setConst(true);
        List<String> annotations = new ArrayList<>();
        annotations.add("JvmField");
        cgField3.setAnnotationList(annotations);
        cgField3.setDefault("\"static and final member!\"");

        // static initializer のテスト
        {
            // メソッドを生成します。
            final BlancoCgMethod cgMethod = cgFactory.createMethod("myStatic",
                    "static initializer の試験です。");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStaticInitializer(true);
            cgMethod.getLineList().add("println()");
        }

        // メソッドを生成します。
        final BlancoCgMethod cgMethod = cgFactory.createMethod("myMethod",
                "メソッドの試験です。");
        cgClass.getMethodList().add(cgMethod);

        // パラメータを追加します。
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "java.lang.String",
                        "文字列引数。"));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "java.util.Date",
                                "日付引数。"));
        // 戻り値を設定します。
        cgMethod.setReturn(cgFactory.createReturn("boolean", "成功ならtrue。"));

        cgMethod.getThrowList().add(
                cgFactory.createException("java.io.IOException",
                        "入出力例外が発生した場合。"));

        // アノテーションの追加。
        cgMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        cgMethod.setOverride(true);

        // メソッドの内容を追加します。
        cgMethod.getLineList().add("// 代入の試験です。");
        cgMethod.getLineList().add("val a : Int = 0");
        cgMethod.getLineList().add("");
        cgMethod.getLineList().add("return true");

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * final なクラスのテストです。
     * @throws Exception
     */
    public void testTransformerFinal() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // ソースファイルを生成します。
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog", "テスト用のクラス");
        cgSourceFile.setEncoding("UTF-8");
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // 同じパッケージのインポート試験。
        cgSourceFile.getImportList().add("myprog.MyClass");

        // クラスを生成します。
        final BlancoCgClass cgClass = cgFactory.createClass("MyClassFinal",
                "このクラスは、テストのためのクラスです。");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("myprog.MyClass"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        cgClass.setFinal(true);

        // 列挙体。kotlin では列挙体の自動生成には対応しません。
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "列挙体の試験。");
        cgClass.getEnumList().add(cgEnum);
        cgEnum.getElementList().add(cgFactory.createEnumElement("Red", "あか"));
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yerrow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // フィールドを生成します。
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "日付フィールドの試験です。");
        cgClass.getFieldList().add(cgField);
        cgField.setDefault("new Date()");

        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "java.util.Date", "日付フィールドの試験v2です。");
        cgClass.getFieldList().add(cgField2);
        cgField2.getType().setArray(true);
        cgField2.getType().setArrayDimension(2);

        // static initializer のテスト
        {
            // メソッドを生成します。
            final BlancoCgMethod cgMethod = cgFactory.createMethod("myStatic",
                    "static initializer の試験です。");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStaticInitializer(true);
            cgMethod.getLineList().add("println()");
        }

        // メソッドを生成します。
        final BlancoCgMethod cgMethod = cgFactory.createMethod("myMethod",
                "メソッドの試験です。");
        cgClass.getMethodList().add(cgMethod);

        // パラメータを追加します。
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "java.lang.String",
                        "文字列引数。"));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "java.util.Date",
                                "日付引数。"));
        // 戻り値を設定します。
        cgMethod.setReturn(cgFactory.createReturn("boolean", "成功ならtrue。"));

        cgMethod.getThrowList().add(
                cgFactory.createException("java.io.IOException",
                        "入出力例外が発生した場合。"));

        // アノテーションの追加。
        cgMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        cgMethod.setOverride(true);

        // メソッドの内容を追加します。
        cgMethod.getLineList().add("// 代入の試験です。");
        cgMethod.getLineList().add("val a : Int = 0");

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * インタフェースの展開試験。
     *
     * @throws Exception
     */
    public void testTransformerInterface() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // ソースファイルを生成します。
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "テスト用のインタフェース");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

        // クラスを生成します。
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyInterface", "このインタフェースは、テストのためのインタフェースです。");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.getExtendClassList().add(
                cgOf.createType("java.lang.Thread"));

        // フィールドを生成します。
        final BlancoCgField cgField = cgOf.createField("myField",
                "java.util.Date", "日付フィールドの試験です。");
        cgInterface.getFieldList().add(cgField);
        cgField.setDefault("new Date()");

        // メソッドを生成します。
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "メソッドの試験です。");
        cgInterface.getMethodList().add(cgMethod);

        // パラメータを追加します。
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argString", "java.lang.String",
                                "文字列引数。"));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argDate", "java.util.Date", "日付引数。"));
        // 戻り値を設定します。
        cgMethod.setReturn(cgOf.createReturn("boolean", "成功ならtrue。"));

        cgMethod.getThrowList().add(
                cgOf.createException("java.io.IOException", "入出力例外が発生した場合。"));

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}
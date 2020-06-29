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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        // クラスのコンストラクタ
        List<String> classAnnotationList = new ArrayList<>();
        classAnnotationList.add("hoge(\n\"fuga: boge\"\n)");
        cgClass.setAnnotationList(classAnnotationList);

        // プライマリコンストラクタのテストです
        BlancoCgField constParam = new BlancoCgField();
        BlancoCgType constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("first");
        constParam.setConst(true);
        constParam.setDefault("0");
        constType.setName("Int");

        // プライマリコンストラクタのテストです
        constParam = new BlancoCgField();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("second");
        constParam.setConst(true);
        constType.setName("kotlin.Number");
        // プライマリコンストラクタのアノテーションテスト
        List<String> primaryConstAnnotationList = new ArrayList<>();
        primaryConstAnnotationList.add("hoge(\n\"fuga: boge\"\n)");
        constParam.setAnnotationList(primaryConstAnnotationList);

        // プライマリコンストラクタのテストです
        constParam = new BlancoCgField();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("third");
        constType.setName("kotlin.collections.List");
        constParam.setConst(false);
        constParam.setNotnull(false);
        constParam.setDefault("null");
        constType.setGenerics("java.lang.String");

        // 委譲のテスト

        constParam = new BlancoCgField();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("fourth");
        constParam.setNotnull(true);
        constType.setName("myprog.MyInterface");

        Map<java.lang.String, java.lang.String> delegateMap = cgClass.getDelegateMap();
        delegateMap.put("myprog.MyInterface", "fourth");
        delegateMap.put("java.lang.Runnable", "fifth"); // ignored.

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
        cgField.setAccess("public");

//        final BlancoCgField cgField2 = cgFactory.createField("myField2",
//                "java.util.Date", "日付フィールドの試験v2です。");
//        cgClass.getFieldList().add(cgField2);
//        cgField2.getType().setArray(true);
//        cgField2.getType().setArrayDimension(2);
//        cgField2.setAccess("public");

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

        final BlancoCgField cgField4 = cgFactory.createField("myField4", "org.Hoge", "staticフィールドの試験です。");
        cgClass.getFieldList().add(cgField4);
        cgField4.setAccess("public");
        cgField4.setStatic(true);
        cgField4.setFinal(true);
        cgField4.setConst(true);

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
                "Copyright(\nvalue=\"blanco Framework\"\n)");
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

        // abstract メソッドを生成します。
        final BlancoCgMethod cgMethodAbst = cgOf.createMethod("myMethodAbst",
                "abstract メソッドの試験です。");
        cgMethodAbst.setAbstract(true);

        cgInterface.getMethodList().add(cgMethodAbst);

        // パラメータを追加します。
        cgMethodAbst.getParameterList()
                .add(
                        cgOf.createParameter("argString", "java.lang.String",
                                "文字列引数。", false));
        cgMethodAbst.getParameterList().add(
                cgOf.createParameter("argDate", "java.util.Date", "日付引数。", true));
        // 戻り値を設定します。
        cgMethodAbst.setReturn(cgOf.createReturn("boolean", "成功ならtrue。"));

        cgMethodAbst.getThrowList().add(
                cgOf.createException("java.io.IOException", "入出力例外が発生した場合。"));

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Generics なクラスのテストです。
     * @throws Exception
     */
    public void testTransformercGenerics() throws Exception {
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
        final BlancoCgClass cgClass = cgFactory.createClass("MyClassGenerics",
                "このクラスは、テストのためのクラスです。");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("myprog.MyClass"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        cgClass.setFinal(true);
        cgClass.setGenerics("T");

        // フィールドを生成します。
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "日付フィールドの試験です。");
        cgClass.getConstructorArgList().add(cgField);
        cgField.setDefault("Date()");
        cgField.setAccess("public");
        cgField.setFinal(true);

        final BlancoCgField cgField2 = cgFactory.createField("myField2", "T", "Genericsの私見です");
        cgClass.getFieldList().add(cgField2);
        cgField2.setNotnull(false);
        cgField2.setAccess("public");
        cgField2.setFinal(false);

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

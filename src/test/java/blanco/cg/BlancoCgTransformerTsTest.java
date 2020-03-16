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
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import junit.framework.TestCase;

import java.io.File;

/**
 * TypeScript言語用の生成試験。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerTsTest extends TestCase {
    /**
     * export なvalueobjectクラスの展開試験。
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
        // 通常のimportの試験。これらはすべて無視されます。
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // 同じパッケージのインポート試験。
        cgSourceFile.getImportList().add("myprog.MyClass2");

        // TypeScript 風の import 文を生成します。
        cgSourceFile.getHeaderList().add("import {MyInterface} from \"./MyInterface\"");

        // クラスを生成します。
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "このクラスは、テストのためのクラスです。");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));

        // 列挙体（TypeScript言語の生成では列挙体は当面無視します）
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "列挙体の試験。");
        cgClass.getEnumList().add(cgEnum);
        cgEnum.getElementList().add(cgFactory.createEnumElement("Red", "あか"));
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yerrow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // フィールドを生成します。
        final BlancoCgField cgField = cgFactory.createField("_myField",
                "string", "文字列フィールドの試験です。");
        cgClass.getFieldList().add(cgField);
        cgField.setFinal(false);
        cgField.setAccess("private");
        cgField.setDefault("\"hoge\"");

        // Getter メソッドを生成します。
        final BlancoCgMethod cgGetterMethod = cgFactory.createMethod("myField",
                "Getterメソッドの試験です。");
        cgClass.getMethodList().add(cgGetterMethod);
        // TypeScript では get アクセサを用います。
        cgGetterMethod.setAccess("get");

        // 戻り値を設定します。
        cgGetterMethod.setReturn(cgFactory.createReturn("string", "値を戻します。"));

        // アノテーションの追加。
        cgGetterMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        // override は無視されます。
        cgGetterMethod.setOverride(true);

        // メソッドの内容を追加します。
        cgGetterMethod.getLineList().add("return this._myField");

        // Setter メソッドの試験です。
        final BlancoCgMethod cgSetterMethod = cgFactory.createMethod("myField",
                "Setterメソッドの試験です。");
        cgClass.getMethodList().add(cgSetterMethod);
        cgSetterMethod.setAccess("set");

        // パラメータを追加します。
        cgSetterMethod.getParameterList().add(
                cgFactory.createParameter("myField", "string",
                        "文字列引数。"));

        // メソッドの内容を追加します。
        cgSetterMethod.getLineList().add("this._myField = myField");

        // フィールドを生成します。
        final BlancoCgField cgField2 = cgFactory.createField("_myField2",
                "string", "文字列フィールドの試験2です。");
        cgClass.getFieldList().add(cgField2);
        cgField2.setFinal(false);
        cgField2.setAccess("private");
        cgField2.setNotnull(true);
        cgField2.setDefault("\"fuga\"");

        // Getter メソッドを生成します。
        final BlancoCgMethod cgGetterMethod2 = cgFactory.createMethod("myField2",
                "Getterメソッドの試験です。");
        cgClass.getMethodList().add(cgGetterMethod2);
        // TypeScript では get アクセサを用います。
        cgGetterMethod2.setAccess("get");

        // 戻り値を設定します。
        cgGetterMethod2.setReturn(cgFactory.createReturn("string", "値を戻します。"));
        cgGetterMethod2.setNotnull(true);

        // アノテーションの追加。
        cgGetterMethod2.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        // override は無視されます。
        cgGetterMethod2.setOverride(true);

        // メソッドの内容を追加します。
        cgGetterMethod2.getLineList().add("return this._myField2");

        // Setter メソッドの試験です。
        final BlancoCgMethod cgSetterMethod2 = cgFactory.createMethod("myField2",
                "Setterメソッドの試験です。");
        cgClass.getMethodList().add(cgSetterMethod2);
        cgSetterMethod2.setAccess("set");

        // パラメータを追加します。
        BlancoCgParameter param2 = cgFactory.createParameter("myField", "string",
                "文字列引数。");
        param2.setNotnull(true);

        cgSetterMethod2.getParameterList().add(param2);

        // メソッドの内容を追加します。
        cgSetterMethod2.getLineList().add("this._myField2 = myField");

        final BlancoCgTransformer cgTransformerTs = BlancoCgTransformerFactory
                .getTsSourceTransformer();
        cgTransformerTs.transform(cgSourceFile, new File("./tmp/blanco"));
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

        // フィールドを生成します。
        final BlancoCgField cgField = cgOf.createField("myField",
                "string", "文字列フィールドの試験です。");
        cgInterface.getFieldList().add(cgField);
        cgField.setAccess("public");

        // メソッドを生成します。
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "メソッドの試験です。");
        cgInterface.getMethodList().add(cgMethod);

        // パラメータを追加します。
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argString", "string",
                                "文字列引数。"));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argNumber", "number", "数値引数。"));
        // 戻り値を設定します。
        cgMethod.setReturn(cgOf.createReturn("boolean", "成功ならtrue。"));

        // TypeScript では無視されます。
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
                        cgOf.createParameter("argString", "string",
                                "文字列引数。", false));
        cgMethodAbst.getParameterList().add(
                cgOf.createParameter("argNumber", "number", "数値引数。", true));
        // 戻り値を設定します。
        cgMethodAbst.setReturn(cgOf.createReturn("boolean", "成功ならtrue。"));

        // TypeScript では無視されます
        cgMethodAbst.getThrowList().add(
                cgOf.createException("java.io.IOException", "入出力例外が発生した場合。"));

        final BlancoCgTransformer cgTransformerTs = BlancoCgTransformerFactory
                .getTsSourceTransformer();
        cgTransformerTs.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

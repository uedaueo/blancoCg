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
import java.util.ArrayList;
import java.util.List;

/**
 * Generation test for TypeScript.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerTsTest extends TestCase {
    /**
     * Expansion test of export valueobject class.
     *
     * @throws Exception
     */
    public void testTransformer() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog", "Class for testing");
        cgSourceFile.setEncoding("UTF-8");
        // Two tabs are common in TypeScript.
        cgSourceFile.setTabs(2);
        // Normal import test. These will all be ignored.
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog.MyClass2");

        // Generates a TypeScript-style import statement.
        cgSourceFile.getHeaderList().add("import {MyInterface} from \"./MyInterface\"");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.setAccess("default");
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));

        // Generates Plain Text.
        List<String> plainTextList = new ArrayList<>();
        plainTextList.add("[i: string]: any;");
        plainTextList.add("[j: number]: any;");
        cgClass.setPlainTextList(plainTextList);

        // Enumeration (enums are ignored for the time being in the generation of TypeScript).
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "Testing enumerated type.");
        cgClass.getEnumList().add(cgEnum);
        cgEnum.getElementList().add(cgFactory.createEnumElement("Red", "あか"));
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yellow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("_myField",
                "string", "Testing a string field.");
        cgClass.getFieldList().add(cgField);
        cgField.setFinal(false);
        cgField.setAccess("private");
        cgField.setDefault("\"hoge\"");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod = cgFactory.createMethod("myField",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod);
        // TypeScript uses the "get" accessor.
        cgGetterMethod.setAccess("get");
        cgGetterMethod.setStatic(true);

        // Sets the return value.
        cgGetterMethod.setReturn(cgFactory.createReturn("string", "Returns the value."));

        // Adds an annotation.
        cgGetterMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        // override will be ignored.
        cgGetterMethod.setOverride(true);

        // Adds the contents of the method.
        cgGetterMethod.getLineList().add("return this._myField");

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod = cgFactory.createMethod("myField",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod);
        cgSetterMethod.setAccess("set");

        // Adds a parameter.
        cgSetterMethod.getParameterList().add(
                cgFactory.createParameter("myField", "string",
                        "String argument."));

        // Adds the contents of the method.
        cgSetterMethod.getLineList().add("this._myField = myField");

        // Generates a field.
        final BlancoCgField cgField2 = cgFactory.createField("_myField2",
                "string", "This is test2 for a string field.");
        cgClass.getFieldList().add(cgField2);
        cgField2.setFinal(false);
        cgField2.setAccess("private");
        cgField2.setNotnull(true);
        cgField2.setDefault("\"fuga\"");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod2 = cgFactory.createMethod("myField2",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod2);
        // TypeScript uses the "get" accessor.
        cgGetterMethod2.setAccess("get");

        // Sets the return value.
        cgGetterMethod2.setReturn(cgFactory.createReturn("string", "Returns the value."));
        cgGetterMethod2.setNotnull(true);

        // Adds an annotation.
        cgGetterMethod2.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        // override will be ignored.
        cgGetterMethod2.setOverride(true);

        // Adds the contents of the method.
        cgGetterMethod2.getLineList().add("return this._myField2");

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod2 = cgFactory.createMethod("myField2",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod2);
        cgSetterMethod2.setAccess("set");

        // Adds a parameter
        BlancoCgParameter param2 = cgFactory.createParameter("myField", "string",
                "String argument.");
        param2.setNotnull(true);

        cgSetterMethod2.getParameterList().add(param2);

        // Adds the contents of the method.
        cgSetterMethod2.getLineList().add("this._myField2 = myField");

        //--- Testing Generic ---
        // Generates a field.
        final BlancoCgField cgField3 = cgFactory.createField("_myGeneric",
                "Array", "Testing a Generics field.");
        cgClass.getFieldList().add(cgField3);
        cgField3.getType().setGenerics("string");
        cgField3.setFinal(false);
        cgField3.setAccess("private");
        cgField3.setNotnull(true);
        cgField3.setDefault("[\"generics!\"]");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod3 = cgFactory.createMethod("myGeneric",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod3);
        // TypeScript uses the "get" accessor.
        cgGetterMethod3.setAccess("get");

        // Sets the return value.
        cgGetterMethod3.setReturn(cgFactory.createReturn("Array", "Returns the value."));
        cgGetterMethod3.getReturn().getType().setGenerics("string");
        cgGetterMethod3.setNotnull(true);

        // Adds the contents of the method.
        cgGetterMethod3.getLineList().add("return this._myGeneric");

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod3 = cgFactory.createMethod("myGeneric",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod3);
        cgSetterMethod3.setAccess("set");

        // Adds a parameter.
        BlancoCgParameter param3 = cgFactory.createParameter("myGeneric", "Array",
                "String argument.");
        param3.getType().setGenerics("string");
        param3.setNotnull(true);

        cgSetterMethod3.getParameterList().add(param3);

        // Adds the contents of the method.
        cgSetterMethod3.getLineList().add("this._myGeneric = myGeneric");

        final BlancoCgTransformer cgTransformerTs = BlancoCgTransformerFactory
                .getTsSourceTransformer();
        cgTransformerTs.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Interface expansion test.
     *
     * @throws Exception
     */
    public void testTransformerInterface() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "Interface for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Sets to two tabs.
        cgSourceFile.setTabs(2);

        // Generates the class.
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyInterface", "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));

        // Generates Plain Text.
        List<String> plainTextList = new ArrayList<>();
        plainTextList.add("[i: string]: any;");
        plainTextList.add("[j: number]: any;");
        cgInterface.setPlainTextList(plainTextList);

        // Generates a field.
        final BlancoCgField cgField = cgOf.createField("myField",
                "string", "Testing a string field.");
        cgInterface.getFieldList().add(cgField);
        cgField.setAccess("public");

        // Generates methods.
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "Testing method.");
        cgInterface.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argString", "string",
                                "String argument."));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argNumber", "number", "Numeric argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("boolean", "True if success."));

        // Ignored in TypeScript.
        cgMethod.getThrowList().add(
                cgOf.createException("java.io.IOException", "If an I/O exception occurs."));

        // Generates an abstract method.
        final BlancoCgMethod cgMethodAbst = cgOf.createMethod("myMethodAbst",
                "Testing an abstract method.");
        cgMethodAbst.setAbstract(true);

        cgInterface.getMethodList().add(cgMethodAbst);

        // Adds parameters.
        cgMethodAbst.getParameterList()
                .add(
                        cgOf.createParameter("argString", "string",
                                "String argument.", false));
        cgMethodAbst.getParameterList().add(
                cgOf.createParameter("argNumber", "number", "Numeric argument.", true));
        // Sets the return value.
        cgMethodAbst.setReturn(cgOf.createReturn("boolean", "True if success."));

        // Ignored in TypeScript.
        cgMethodAbst.getThrowList().add(
                cgOf.createException("java.io.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerTs = BlancoCgTransformerFactory
                .getTsSourceTransformer();
        cgTransformerTs.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

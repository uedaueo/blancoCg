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
import blanco.cg.util.BlancoCgStatementUtil;
import blanco.cg.valueobject.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Generation test for TypeScript.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerPhp8Test {
    /**
     * Expansion test of export valueobject class.
     *
     * @throws Exception
     */
    @Test
    public void testTransformer() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog.space", "Class for testing");
        cgSourceFile.setNamespace("Blanco\\myprog\\space");
        cgSourceFile.setEncoding("UTF-8");
        // Two tabs are common in TypeScript.
        cgSourceFile.setTabs(4);
        // Normal import test. These will all be ignored.
        cgSourceFile.getImportList().add("java\\text\\NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog\\space\\MyClass2");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.setAccess("default");
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog\\MyInterface"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "string", "Testing a string field.");
        cgClass.getFieldList().add(cgField);
        cgField.setFinal(false);
        cgField.setAccess("private");
        cgField.setDefault("\"hoge\"");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod = cgFactory.createMethod("getMyField",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod);
        // TypeScript uses the "get" accessor.
        cgGetterMethod.setAccess("public");

        // Sets the return value.
        cgGetterMethod.setReturn(cgFactory.createReturn("string", "Returns the value."));

        // Adds the contents of the method.
        cgGetterMethod.getLineList().add("return $this->myField" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod = cgFactory.createMethod("setMyField",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod);
        cgSetterMethod.setAccess("public");

        // Adds a parameter.
        cgSetterMethod.getParameterList().add(
                cgFactory.createParameter("myField", "string",
                        "String argument."));

        // Adds the contents of the method.
        cgSetterMethod.getLineList().add("$this->myField = $myField" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        // Generates a field.
        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "string", "This is test2 for a string field.");
        cgClass.getFieldList().add(cgField2);
        cgField2.setAccess("private");
        cgField2.setDefault("\"fuga\"");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod2 = cgFactory.createMethod("getMyField2",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod2);
        // TypeScript uses the "get" accessor.
        cgGetterMethod2.setAccess("public");

        // Sets the return value.
        cgGetterMethod2.setReturn(cgFactory.createReturn("string", "Returns the value."));
//        cgGetterMethod2.setNotnull(true);

        // Adds the contents of the method.
        cgGetterMethod2.getLineList().add("return $this->myField2" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod2 = cgFactory.createMethod("setMyField2",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod2);
        cgSetterMethod2.setAccess("public");

        // Adds a parameter
        BlancoCgParameter param2 = cgFactory.createParameter("myField", "string",
                "String argument.");
//        param2.setNotnull(true);

        cgSetterMethod2.getParameterList().add(param2);

        // Adds the contents of the method.
        cgSetterMethod2.getLineList().add("$this->myField2 = $myField" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));


        // Generates a field.
        final BlancoCgField cgField3 = cgFactory.createField("myField3",
                "int", "This is test3 for a integer field.");
        cgClass.getFieldList().add(cgField3);
        cgField3.setAccess("private");
        cgField3.setDefault("0");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod3 = cgFactory.createMethod("getMyField3",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod3);
        // TypeScript uses the "get" accessor.
        cgGetterMethod3.setAccess("public");

        // Sets the return value.
        cgGetterMethod3.setReturn(cgFactory.createReturn("int", "Returns the value."));
//        cgGetterMethod2.setNotnull(true);

        // Adds the contents of the method.
        cgGetterMethod3.getLineList().add("return $this->myField3" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod3 = cgFactory.createMethod("setMyField3",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod3);
        cgSetterMethod3.setAccess("public");

        // Adds a parameter
        BlancoCgParameter param3 = cgFactory.createParameter("myField", "int",
                "String argument.");
//        param2.setNotnull(true);

        cgSetterMethod3.getParameterList().add(param3);

        // Adds the contents of the method.
        cgSetterMethod3.getLineList().add("$this->myField3 = $myField" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        //--- Testing Generic ---
        // Generates a field.
        final BlancoCgField cgField4 = cgFactory.createField("myGeneric",
                "array", "Testing a Generics field.");
        cgClass.getFieldList().add(cgField4);
        cgField4.getType().setGenerics("<string>");
        cgField4.setAccess("private");
        cgField4.setDefault("[\"generics!\"]");

        // Generates a Getter method.
        final BlancoCgMethod cgGetterMethod4 = cgFactory.createMethod("getMyGeneric",
                "Testing a Getter method.");
        cgClass.getMethodList().add(cgGetterMethod4);
        // TypeScript uses the "get" accessor.
        cgGetterMethod4.setAccess("public");

        // Sets the return value.
        cgGetterMethod4.setReturn(cgFactory.createReturn("array", "Returns the value."));
        cgGetterMethod4.getReturn().getType().setGenerics("string");
//        cgGetterMethod3.setNotnull(true);

        // Adds the contents of the method.
        cgGetterMethod4.getLineList().add("return $this->myGeneric" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        // Generates a Setter method.
        final BlancoCgMethod cgSetterMethod4 = cgFactory.createMethod("setMyGeneric",
                "Testing a Setter method.");
        cgClass.getMethodList().add(cgSetterMethod4);
        cgSetterMethod4.setAccess("public");

        // Adds a parameter.
        BlancoCgParameter param4 = cgFactory.createParameter("myGeneric", "array",
                "String argument.");
        param4.getType().setGenerics("string");
//        param3.setNotnull(true);

        cgSetterMethod4.getParameterList().add(param4);

        // Adds the contents of the method.
        cgSetterMethod4.getLineList().add("$this->myGeneric = $myGeneric" + BlancoCgStatementUtil.getTerminator(BlancoCgSupportedLang.PHP8));

        final BlancoCgTransformer cgTransformerPhp8 = BlancoCgTransformerFactory
                .getPhp8SourceTransformer();
        cgTransformerPhp8.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    @Test
    public void testTransformerInterface() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog.space", "Class for testing");
        cgSourceFile.setNamespace("Blanco\\myprog\\space");
        cgSourceFile.setEncoding("UTF-8");
        // Two tabs are common in TypeScript.
        cgSourceFile.setTabs(4);
        // Normal import test. These will all be ignored.
        cgSourceFile.getImportList().add("java\\text\\NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog\\space\\MyClass2");

        // Generates the class.
        final BlancoCgInterface cgInterface = cgFactory.createInterface("MyInterface",
                "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.setAccess("");
        cgInterface.getExtendClassList().add(
                cgFactory.createType("myprog\\MyClass"));

        // Generates a method.
        final BlancoCgMethod cgDoPostMethod = cgFactory.createMethod("doPost",
                "Testing a method.");
        cgInterface.getMethodList().add(cgDoPostMethod);
        // TypeScript uses the "get" accessor.
        cgDoPostMethod.setAccess("public");

        // Adds a parameter.
        cgDoPostMethod.getParameterList().add(
                cgFactory.createParameter("request", "LoginPostRequest",
                        "String argument."));

        // Sets the return value.
        cgDoPostMethod.setReturn(cgFactory.createReturn("LoginPostResponse", "Returns the value."));

        // Generates a method.
        final BlancoCgMethod cgDoGetMethod = cgFactory.createMethod("doGet",
                "Testing a Setter method.");
        cgInterface.getMethodList().add(cgDoGetMethod);
        cgDoGetMethod.setAccess("public");

        // Adds a parameter.
        cgDoGetMethod.getParameterList().add(
                cgFactory.createParameter("request", "LoginPostRequest",
                        "String argument."));

        // Sets the return value.
        cgDoPostMethod.setReturn(cgFactory.createReturn("JsonResponse|LoginPostResponse", "Returns the value."));

        final BlancoCgTransformer cgTransformerPhp8 = BlancoCgTransformerFactory
                .getPhp8SourceTransformer();
        cgTransformerPhp8.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

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

import java.io.File;

import org.junit.jupiter.api.Test;

import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Generation test for Delphi.
 * 
 * @author YAMAMOTO Koji
 */
public class BlancoCgTransformerDelphiTest {
    /**
     * The test for Delphi.
     * 
     * @throws Exception
     */
    @Test
    public void testTransformerDelphi() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.

        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "Unit1", "Class for testing");
        cgSourceFile.setName("Unit1");

        cgSourceFile.getImportList().add("System.Text.DummyText");
        // Import test of the same package.
        cgSourceFile.getImportList().add("Myprog.MyClass2");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("TMyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        // cgClass.getLangDoc().getTagList().add(
        // cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(cgFactory.createType("TObject"));
        // cgClass.getImplementInterfaceList().add(
        // cgFactory.createType("System.WebException"));
        // cgClass.getImplementInterfaceList().add(
        // cgFactory.createType("System.WebException2"));

        // Enumeration
        // final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
        // "Testing enumerated type.");
        // cgClass.getEnumList().add(cgEnum);
        // final BlancoCgEnumElement cgEnumElementFirst = cgFactory
        // .createEnumElement("Red", "あか");
        // cgEnumElementFirst.setDefault("1");
        // cgEnum.getElementList().add(cgEnumElementFirst);
        // cgEnum.getElementList().add(
        // cgFactory.createEnumElement("Yellow", "きいろ"));
        // cgEnum.getElementList().add(cgFactory.createEnumElement("Blue",
        // "あお"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("MyField",
                "String", "Testing a String field.");
        cgClass.getFieldList().add(cgField);
        // cgField.setDefault("new DateTime()");
        //
        // final BlancoCgField cgField2 = cgFactory.createField("myField2",
        // "java.util.Date", "Testing a date field v2.");
        // cgClass.getFieldList().add(cgField2);
        // cgField2.getType().setArray(true);

        // Generates a procedure.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("MyMethod",
                "Testing a procedure.");
        cgClass.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "String", "String argument."));
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argInt", "integer", "Integer argument."));

        // Generates a function.
        final BlancoCgMethod cgFunction = cgFactory.createMethod("MyFunction",
                "Testing method.");
        cgClass.getMethodList().add(cgFunction);

        // Adds parameters.
        cgFunction.getParameterList().add(
                cgFactory.createParameter("argString", "String", "String argument."));
        cgFunction.getParameterList().add(
                cgFactory.createParameter("argInt", "integer", "Integer argument."));

        // Sets the return value.
        cgFunction.setReturn(cgFactory.createReturn("boolean", "True if success."));

        // cgMethod.getThrowList().add(
        // cgFactory.createException("System.IO.IOException",
        // "If an I/O exception occurs."));

        // Adds an annotation.
        // cgMethod.getAnnotationList().add(
        // "Copyright(value=\"blanco Framework\")");

        // Adds the contents of the method.
        // cgMethod.getLineList().add("// Testing assignment.");
        // cgMethod.getLineList().add("int a = 0;");

        final BlancoCgTransformer cgTransformerDelphi = BlancoCgTransformerFactory
                .getDelphiSourceTransformer();
        cgTransformerDelphi.transform(cgSourceFile, new File("./tmp/blanco"));
    }

}

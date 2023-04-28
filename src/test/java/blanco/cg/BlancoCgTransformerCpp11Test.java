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
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgEnumElement;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Generation test for C++11.
 * 
 * @author IGA Tosiki
 */
public class BlancoCgTransformerCpp11Test {
    /**
     * The test for C++11.
     * 
     * @throws Exception
     */
    @Test
    public void testTransformerCpp11() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "Myprog", "Class for testing");
        cgSourceFile.getImportList().add("stdio.h");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("java.lang.Thread"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("System.WebException"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("System.WebException2"));

        // Enumeration
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "Testing enumerated type.");
        cgClass.getEnumList().add(cgEnum);
        final BlancoCgEnumElement cgEnumElementFirst = cgFactory
                .createEnumElement("Red", "あか");
        cgEnumElementFirst.setDefault("1");
        cgEnum.getElementList().add(cgEnumElementFirst);
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yellow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgClass.getFieldList().add(cgField);
        cgField.setDefault("new DateTime()");

        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "java.util.Date", "Testing a date field v2.");
        cgClass.getFieldList().add(cgField2);
        cgField2.getType().setArray(true);

        // Generates a method.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("MyMethod",
                "Testing method.");
        cgClass.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "System.String",
                        "String argument."));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "System.DateTime",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("bool", "True if success."));

        cgMethod.getThrowList().add(
                cgFactory.createException("System.IO.IOException",
                        "If an I/O exception occurs."));

        // Adds an annotation.
        cgMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");

        // Adds the contents of the method.
        cgMethod.getLineList().add("// Testing assignment.");
        cgMethod.getLineList().add("int a = 0;");

        final BlancoCgTransformer cgTransformerCpp11 = BlancoCgTransformerFactory
                .getCpp11SourceTransformer();
        cgTransformerCpp11.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Interface expansion test.
     * 
     * @throws Exception
     */
    @Test
    public void testTransformerInterface() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("Myprog",
                "Interface for testing");
        cgSourceFile.getImportList().add("Myprog.Class2");
        cgSourceFile.getImportList().add("Myprog2.ClassOther");

        // Generates the class.
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyInterface", "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.getExtendClassList().add(
                cgOf.createType("System.IO.IOException"));

        // Generates a field.
        final BlancoCgField cgField = cgOf.createField("myField",
                "System.DateTime", "Testing a date field.");
        cgInterface.getFieldList().add(cgField);
        cgField.setDefault("new DateTime()");

        // Generates methods.
        final BlancoCgMethod cgMethod = cgOf.createMethod("MyMethod",
                "Testing method.");
        cgInterface.getMethodList().add(cgMethod);

        // Adds parameters
        cgMethod.getParameterList().add(
                cgOf.createParameter("argString", "System.String", "String argument."));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argDate", "System.DateTime", "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("bool", "True if success."));

        cgMethod.getThrowList().add(
                cgOf.createException("System.IO.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerCpp11 = BlancoCgTransformerFactory
                .getCpp11SourceTransformer();
        cgTransformerCpp11.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

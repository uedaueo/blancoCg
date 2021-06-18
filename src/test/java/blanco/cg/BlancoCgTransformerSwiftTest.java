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

import junit.framework.TestCase;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgEnumElement;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * Generation test for Swift.
 * 
 * @author IGA Tosiki
 */
public class BlancoCgTransformerSwiftTest extends TestCase {
    /**
     * The test for Swift.
     * 
     * @throws Exception
     */
    public void testTransformerSwift() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "Myprog", "Class for testing");
//        cgSourceFile.getImportList().add("Foundation");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("Foundation.Thread"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("Foundation.WebException"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("Foundation.WebException2"));

if(false){
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
}

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "Foundation.Date", "Testing a date field.");
        cgClass.getFieldList().add(cgField);
        cgField.setDefault("DateTime()");

        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "Foundation.Integer", "Testing an int field.");
        cgClass.getFieldList().add(cgField2);

        // Generates a method.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("MyMethod",
                "Testing method.");
        cgClass.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "Foundation.String",
                        "String argument."));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "Foundation.DateTime",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("Foundation.Boolean", "True if success."));

        cgMethod.getThrowList().add(
                cgFactory.createException("Foundation.IOException",
                        "If an I/O exception occurs."));

        // Adds an annotation.
 // TODO      cgMethod.getAnnotationList().add(
 //               "Copyright(value=\"blanco Framework\")");

        // Adds the contents of the method.
        cgMethod.getLineList().add("// Testing assignment.");
        cgMethod.getLineList().add("let a = 0;");

        final BlancoCgTransformer cgTransformerSwift = BlancoCgTransformerFactory
                .getSwiftSourceTransformer();
        cgTransformerSwift.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Interface expansion test.
     * 
     * @throws Exception
     */
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

        // Generates a method.
        final BlancoCgMethod cgMethod = cgOf.createMethod("MyMethod",
                "Testing method.");
        cgInterface.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgOf.createParameter("argString", "System.String", "String argument."));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argDate", "System.DateTime", "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("bool", "True if success."));

        cgMethod.getThrowList().add(
                cgOf.createException("System.IO.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerSwift = BlancoCgTransformerFactory
                .getSwiftSourceTransformer();
        cgTransformerSwift.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

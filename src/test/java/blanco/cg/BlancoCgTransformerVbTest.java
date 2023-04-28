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
 * Generation test for VB.NET.
 * 
 * @author IGA Tosiki
 */
public class BlancoCgTransformerVbTest {
    /**
     * The test for VB.NET.
     * 
     * @throws Exception
     */
    @Test
    public void testTransformerJs() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "Myprog", "Class for testing");
        cgSourceFile.getImportList().add("System.Text.DummyText");
        // Import test of the same package.
        cgSourceFile.getImportList().add("Myprog.MyClass2");
        cgSourceFile.getLangDoc().getDescriptionList().add(
                "This class was auto-generated by blanco Framework.");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MySampleClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("System.WebException"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("System.WebException"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("System.WebException2"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "System.DateTime", "Testing a date field.");
        cgClass.getFieldList().add(cgField);
        cgField.setDefault("New DateTime()");

        // Generates a method.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("MyMethod",
                "Testing method.");
        cgClass.getMethodList().add(cgMethod);

        cgMethod.setAccess("private");

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "System.String",
                        "String argument."));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "System.DateTime",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("Boolean", "True if success."));

        cgMethod.getThrowList().add(
                cgFactory.createException("System.IO.IOException",
                        "If an I/O exception occurs."));

        // Adds the contents of the method.
        cgMethod.getLineList().add("' Testing assignment.");
        cgMethod.getLineList().add("Return True");

        final BlancoCgTransformer cgTransformerVb = BlancoCgTransformerFactory
                .getVbSourceTransformer();
        cgTransformerVb.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

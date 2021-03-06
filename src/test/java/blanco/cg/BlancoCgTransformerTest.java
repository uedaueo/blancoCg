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
import java.util.ArrayList;
import java.util.List;

import blanco.cg.valueobject.*;
import junit.framework.TestCase;
import blanco.cg.transformer.BlancoCgTransformerFactory;

/**
 * Generation test for Java.
 *
 * @author IGA Tosiki
 */
public class BlancoCgTransformerTest extends TestCase {
    /**
     * The test of class expansion.
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
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog.MyClass2");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClass",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("java.lang.Thread"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("java.lang.Runnable"));

        // Generates Generics of the class.
        cgClass.setGenerics("T");

        // Generates Plain Text.
        List<String> plainTextList = new ArrayList<>();
        plainTextList.add("protected Map<String, String> mymap = new HashMap<String, String>() {");
        plainTextList.add("{");
        plainTextList.add("put(\"hoge\", \"fuga\");");
        plainTextList.add("put(\"hoge\", \"fuga\");");
        plainTextList.add("put(\"hoge\", \"fuga\");");
        plainTextList.add("}");
        plainTextList.add("};");
        cgClass.setPlainTextList(plainTextList);

        // Enumeration
        final BlancoCgEnum cgEnum = cgFactory.createEnum("FavorColor",
                "Testing enumerated type.");
        cgClass.getEnumList().add(cgEnum);
        cgEnum.getElementList().add(cgFactory.createEnumElement("Red", "あか"));
        cgEnum.getElementList().add(
                cgFactory.createEnumElement("Yellow", "きいろ"));
        cgEnum.getElementList().add(cgFactory.createEnumElement("Blue", "あお"));

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgClass.getFieldList().add(cgField);
        cgField.setDefault("new Date()");

        final BlancoCgField cgField2 = cgFactory.createField("myField2",
                "java.util.Date", "Testing a date field v2.");
        cgClass.getFieldList().add(cgField2);
        cgField2.getType().setArray(true);
        cgField2.getType().setArrayDimension(2);

        // Testing the generics of the field.
        final BlancoCgField cgField3 = cgFactory.createField("myField3",
                "java.util.Map<java.lang.String, blanco.test.BlancoList<blanco.test.BlancoMap<S,T>>>", "This is the test for Generics of the field.");
        cgClass.getFieldList().add(cgField3);

        final BlancoCgField cgField4 = cgFactory.createField("myField3",
                "java.util.Map", "This is the test for Generics of the field.");
        cgClass.getFieldList().add(cgField4);
        cgField4.getType().setGenerics("java.lang.String, blanco.test.BlancoList<blanco.test.BlancoMap<S,T>>");

        // Testing static initializer.
        {
            // Generates methods.
            final BlancoCgMethod cgMethod = cgFactory.createMethod("myStatic",
                    "Testing static initializer.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStaticInitializer(true);
            cgMethod.getLineList().add("System.out.println();");
        }

        // Generates a method.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("myMethod",
                "Testing method.");
        cgClass.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgFactory.createParameter("argString", "java.lang.String",
                        "String argument."));
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "java.util.Date",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("boolean", "True if success."));

        // Defines the virtual parameters.
//        cgMethod.setVirtualParameterDefinition("<T>");
        BlancoCgVirtualParameter vparam = cgFactory.createVirtualParameter("typeT", "T", "This is a virtual parameter of the generic type T.");
        cgMethod.getVirtualParameterList().add(vparam);
        vparam = cgFactory.createVirtualParameter("typeS", "S", "This is a virtual parameter of the generic type S.");
        cgMethod.getVirtualParameterList().add(vparam);

        cgMethod.getThrowList().add(
                cgFactory.createException("java.io.IOException",
                        "If an I/O exception occurs."));

        // Adds an annotation.
        cgMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        cgMethod.setOverride(true);

        // Adds the contents of the method.
        cgMethod.getLineList().add("// Testing assignment.");
        cgMethod.getLineList().add("int a = 0;");

        // Generics testing part 2.
        final BlancoCgMethod cgMethod2 = cgFactory.createMethod("myMethod2",
                "Testing generics.");
        cgClass.getMethodList().add(cgMethod2);

        // Adds a parameter.
        BlancoCgParameter cgParameter = cgFactory.createParameter("argMap", "java.util.Map", "K,V map type");
        cgMethod2.getParameterList().add(cgParameter);
        cgParameter.getType().setGenerics(
                "blanco.cg.test.BlancoKey, blanco.cg.test.BlancoMap<java.lang.String, blanco.cg.BlancoDummy<S, T>>"
        );

        final BlancoCgTransformer cgTransformerJava = BlancoCgTransformerFactory
                .getJavaSourceTransformer();
        cgTransformerJava.transform(cgSourceFile, new File("./tmp/blanco"));
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

        // Generates the class.
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyInterface", "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.getExtendClassList().add(
                cgOf.createType("java.lang.Thread"));

        // Generates a field.
        final BlancoCgField cgField = cgOf.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgInterface.getFieldList().add(cgField);
        cgField.setDefault("new Date()");

        // Generates a method.
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "Testing method.");
        cgInterface.getMethodList().add(cgMethod);

        // Adds parameters
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argString", "java.lang.String",
                                "String argument."));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argDate", "java.util.Date", "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("boolean", "True if success."));

        cgMethod.getThrowList().add(
                cgOf.createException("java.io.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerJava = BlancoCgTransformerFactory
                .getJavaSourceTransformer();
        cgTransformerJava.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Expansion test of proprietary annotation definition.
     *
     * @throws Exception
     */
    public void testTransformerAnnotationInterface() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "Interface for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

        // Generates the class.
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyAnnotation", "This interface is for annotation definition.");
        cgInterface.setDefineAnnotation(true);
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.getExtendClassList().add(
                cgOf.createType("java.lang.Thread"));

        // Generates a field.
        final BlancoCgField cgField = cgOf.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgInterface.getFieldList().add(cgField);
        cgField.setDefault("new Date()");

        // Generates a method.
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "Testing method.");
        cgInterface.getMethodList().add(cgMethod);

        // Adds parameters
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argString", "java.lang.String",
                                "String argument."));
        cgMethod.getParameterList().add(
                cgOf.createParameter("argDate", "java.util.Date", "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("boolean", "True if success."));

        cgMethod.getThrowList().add(
                cgOf.createException("java.io.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerJava = BlancoCgTransformerFactory
                .getJavaSourceTransformer();
        cgTransformerJava.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

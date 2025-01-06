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
import java.util.Map;

import blanco.cg.valueobject.*;
import org.junit.jupiter.api.Test;

import blanco.cg.transformer.BlancoCgTransformerFactory;

/**
 * Generation test for Kotilin.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerKotlinTest {
    /**
     * An open class expansion test.
     *
     * @throws Exception
     */
    @Test
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
        BlancoCgType extType = cgFactory.createType("java.lang.Thread");
        cgClass.getExtendClassList().add(extType);
        extType.setGenerics("MyGenerics<S, T>");
        extType.setConstructorArgs("vc");
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("java.lang.Runnable"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        // Constructor of the class
        List<String> classAnnotationList = new ArrayList<>();
        classAnnotationList.add("hoge(\n\"fuga: boge\"\n)");
        cgClass.setAnnotationList(classAnnotationList);
        // Add JsonCreator annotation
        cgClass.setJsonCreator(true);

        // Testing the primary constructor.
        BlancoCgField constParam = new BlancoCgField();
        BlancoCgType constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("first");
        constParam.setConst(true);
        constParam.setDefault("0");
        constType.setName("Int");

        // Testing the primary constructor.
        constParam = new BlancoCgField();
        constType = new BlancoCgType();
        cgClass.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("second");
        constParam.setConst(true);
        constParam.setOverride(true);
        constType.setName("kotlin.Number");
        // Annotation test of the primary constructor.
        List<String> primaryConstAnnotationList = new ArrayList<>();
        primaryConstAnnotationList.add("hoge(\n\"fuga: boge\"\n)");
        constParam.setAnnotationList(primaryConstAnnotationList);

        // Testing the primary constructor.
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

        // Delegation test

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

        // Enumeration (enums are ignored for the time being in the generation of kotlin).
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
        cgField.setFinal(true);
        cgField.setDefault("Date()");
        cgField.setAccess("public");

//        final BlancoCgField cgField2 = cgFactory.createField("myField2",
//                "java.util.Date", "Testing a date field v2.");
//        cgClass.getFieldList().add(cgField2);
//        cgField2.getType().setArray(true);
//        cgField2.getType().setArrayDimension(2);
//        cgField2.setAccess("public");

        final BlancoCgField cgField3 = cgFactory.createField("myField3", "String", "Testing static fields.");
        cgClass.getFieldList().add(cgField3);
        cgField3.setAccess("public");
        cgField3.setStatic(true);
        cgField3.setFinal(true);
        cgField3.setConst(true);
        List<String> annotations = new ArrayList<>();
        annotations.add("JvmField");
        cgField3.setAnnotationList(annotations);
        cgField3.setDefault("\"static and final member!\"");

        final BlancoCgField cgField4 = cgFactory.createField("myField4", "org.Hoge", "Testing static fields.");
        cgClass.getFieldList().add(cgField4);
        cgField4.setAccess("public");
        cgField4.setStatic(true);
        cgField4.setFinal(true);
        cgField4.setConst(true);
        cgField4.getType().setGenerics("kotlin.String");

        // Testing static initializer.
        {
            // Generates methods.
            final BlancoCgMethod cgMethod = cgFactory.createMethod("myStatic",
                    "Testing static initializer.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStaticInitializer(true);
            cgMethod.getLineList().add("println()");
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

        // Defines the virtual parameters.
//        cgMethod.setVirtualParameterDefinition("<T>");
        BlancoCgVirtualParameter vparam = cgFactory.createVirtualParameter("typeT", "T", "This is a virtual parameter of the generic type T.");
        cgMethod.getVirtualParameterList().add(vparam);
        vparam = cgFactory.createVirtualParameter("typeS", "S", "This is a virtual parameter of the generic type S.");
        cgMethod.getVirtualParameterList().add(vparam);

        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("boolean", "True if success."));

        cgMethod.getThrowList().add(
                cgFactory.createException("java.io.IOException",
                        "If an I/O exception occurs."));

        // Adds an annotation.
        cgMethod.getAnnotationList().add(
                "Copyright(\nvalue=\"blanco Framework\"\n)");
        cgMethod.setOverride(true);

        // Adds the contents of the method.
        cgMethod.getLineList().add("// Testing assignment.");
        cgMethod.getLineList().add("val a : Int = 0");
        cgMethod.getLineList().add("");
        cgMethod.getLineList().add("return true");

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * A test for a class that is final.
     * @throws Exception
     */
    @Test
    public void testTransformerFinal() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog", "Class for testing");
        cgSourceFile.setEncoding("UTF-8");
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog.MyClass");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClassFinal",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.setGenerics("S");
        BlancoCgType extendsStr = cgFactory.createType("myprog.MyClass");
        extendsStr.setConstructorArgs("hoge");
        extendsStr.setGenerics("S");
        cgClass.getExtendClassList().add(extendsStr);
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        cgClass.setFinal(true);

        // Enumeration. Does not support auto-generation of enumerations in kotlin.
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
        cgField.setDefault("Date()");

//        final BlancoCgField cgField2 = cgFactory.createField("myField2",
//                "java.util.Date", "Testing a date field v2.");
//        cgClass.getFieldList().add(cgField2);
//        cgField2.getType().setArray(true);
//        cgField2.getType().setArrayDimension(1);

        // Testing static initializer.
        {
            // Generates methods.
            final BlancoCgMethod cgMethod = cgFactory.createMethod("myStatic",
                    "Testing static initializer.");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStaticInitializer(true);
            cgMethod.getLineList().add("println()");
        }

        // Generates a method.
        final BlancoCgMethod cgMethod = cgFactory.createMethod("myMethod",
                "Testing method.");
        cgClass.getMethodList().add(cgMethod);

        // Adds parameters.
        BlancoCgParameter param01 = cgFactory.createParameter("argString", "java.lang.String",
                "String argument.");
        cgMethod.getParameterList().add(param01);
        param01.getAnnotationList().add("Body");
        param01.getAnnotationList().add("NotNull");
        cgMethod.getParameterList()
                .add(
                        cgFactory.createParameter("argDate", "java.util.Date",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgFactory.createReturn("boolean", "True if success."));
//        cgMethod.getReturn().setNullable(true);

        cgMethod.getThrowList().add(
                cgFactory.createException("java.io.IOException",
                        "If an I/O exception occurs."));

        // Adds an annotation.
        cgMethod.getAnnotationList().add(
                "Copyright(value=\"blanco Framework\")");
        cgMethod.setOverride(true);

        // Adds the contents of the method.
        cgMethod.getLineList().add("// Testing assignment.");
        cgMethod.getLineList().add("val a : Int = 0");

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
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
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "Interface for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

        // Generates the class.
        final BlancoCgInterface cgInterface = cgOf.createInterface(
                "MyInterface", "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
//        cgInterface.getExtendClassList().add(
//                cgOf.createType("java.lang.Thread"));
        cgInterface.getExtendClassList().add(
                cgOf.createType("java.util.ArrayList"));

        // Generates a field.
        final BlancoCgField cgField = cgOf.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgInterface.getFieldList().add(cgField);
        cgField.setDefault("Date()");

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

        // Generates an abstract method.
        final BlancoCgMethod cgMethodAbst = cgOf.createMethod("myMethodAbst",
                "Testing an abstract method.");
        cgMethodAbst.setAbstract(true);

        cgInterface.getMethodList().add(cgMethodAbst);

        // Adds parameters.
        cgMethodAbst.getParameterList()
                .add(
                        cgOf.createParameter("argString", "java.lang.String",
                                "String argument.", false));
        cgMethodAbst.getParameterList().add(
                cgOf.createParameter("argDate", "java.util.Date", "Date argument.", true));
        // Sets the return value.
        cgMethodAbst.setReturn(cgOf.createReturn("boolean", "True if success."));

        cgMethodAbst.getThrowList().add(
                cgOf.createException("java.io.IOException", "If an I/O exception occurs."));

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * Generics class test.
     * @throws Exception
     */
    @Test
    public void testTransformercGenerics() throws Exception {
        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgFactory.createSourceFile(
                "myprog", "Class for testing");
        cgSourceFile.setEncoding("UTF-8");
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog.MyClass");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyClassGenerics",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.getExtendClassList().add(
                cgFactory.createType("myprog.MyClass"));
        cgClass.getImplementInterfaceList().add(
                cgFactory.createType("myprog.MyInterface"));
        cgClass.setFinal(true);
        cgClass.setGenerics("T");

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myField",
                "java.util.Date", "Testing a date field.");
        cgClass.getConstructorArgList().add(cgField);
        cgField.setDefault("Date()");
        cgField.setAccess("public");
        cgField.setFinal(true);

        final BlancoCgField cgField2 = cgFactory.createField("myField2", "T", "Testing Generics.");
        cgClass.getFieldList().add(cgField2);
        cgField2.setNotnull(false);
        cgField2.setAccess("public");
        cgField2.setFinal(false);

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    /**
     * enum expansion test.
     *
     * @throws Exception
     */
    @Test
    public void testTransformerEnum() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "enum for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

        // Generates the class.
        final BlancoCgEnum cgEnum = cgOf.createEnum(
                "MyEnum", "This enum is for testing.");
        cgSourceFile.getEnumList().add(cgEnum);
        cgEnum.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));

        // Testing annotation
        cgEnum.getAnnotationList().add("Introspected");
        cgSourceFile.getImportList().add("io.micronaut.core.annotation.Introspected");

        // Testing the primary constructor.
        BlancoCgField constParam = new BlancoCgField();
        BlancoCgType constType = new BlancoCgType();
        cgEnum.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("first");
        constParam.setConst(true);
        constParam.setFinal(true);
        constParam.setNotnull(true);
        constType.setName("Int");

        // Testing the primary constructor.
        constParam = new BlancoCgField();
        constType = new BlancoCgType();
        cgEnum.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("second");
        constParam.setConst(true);
        constParam.setFinal(true);
        constParam.setNotnull(true);
        constType.setName("String");
        // Annotation test of the primary constructor.
        List<String> primaryConstAnnotationList = new ArrayList<>();
        primaryConstAnnotationList.add("hoge(\n\"fuga: boge\"\n)");
        constParam.setAnnotationList(primaryConstAnnotationList);

        // Generates a enumerates.
        final BlancoCgEnumElement cgEnumElement01 = cgOf.createEnumElement("ENUM01", "First Enumerate");
        cgEnumElement01.setDefault("0, \"myEnumerate01\"");
        cgEnum.getElementList().add(cgEnumElement01);

        final BlancoCgEnumElement cgEnumElement02 = cgOf.createEnumElement("ENUM02", "Second Enumerate");
        cgEnumElement02.setDefault("1, \"myEnumerate02\"");
        cgEnum.getElementList().add(cgEnumElement02);

        final BlancoCgEnumElement cgEnumElement03 = cgOf.createEnumElement("ENUM03", "Third Enumerate");
        cgEnumElement03.setDefault("2, \"myEnumerate03\"");
        cgEnum.getElementList().add(cgEnumElement03);

        // No default simple enumerate
        final BlancoCgEnum cgEnum02 = cgOf.createEnum(
                "MyEnum02", "This enum is for testing.");
        cgSourceFile.getEnumList().add(cgEnum02);
        cgEnum02.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));

        // enumerates
        final BlancoCgEnumElement cgEnumElement04 = cgOf.createEnumElement("ENUM01", "First Enumerate");
        cgEnum02.getElementList().add(cgEnumElement04);

        final BlancoCgEnumElement cgEnumElement05 = cgOf.createEnumElement("ENUM02", "Second Enumerate");
        cgEnum02.getElementList().add(cgEnumElement05);

        final BlancoCgEnumElement cgEnumElement06 = cgOf.createEnumElement("ENUM03", "Third Enumerate");
        cgEnum02.getElementList().add(cgEnumElement06);

        final BlancoCgTransformer cgTransformerKotlin = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        cgTransformerKotlin.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

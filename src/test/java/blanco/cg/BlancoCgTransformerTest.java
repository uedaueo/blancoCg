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

import org.junit.jupiter.api.Test;

import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgEnum;
import blanco.cg.valueobject.BlancoCgEnumElement;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgParameter;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.cg.valueobject.BlancoCgType;
import blanco.cg.valueobject.BlancoCgVirtualParameter;

/**
 * Generation test for Java.
 *
 * @author IGA Tosiki
 */
public class BlancoCgTransformerTest {
    /**
     * The test of class expansion.
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
    @Test
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

    /**
     * enum expansion test.
     *
     * @throws Exception
     */
    @Test
    public void testTransformerEnum01() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "enum for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

        // Generates the class.
        final BlancoCgEnum cgEnum = cgOf.createEnum(
                "MyEnum01", "This enum is for testing.");
        cgSourceFile.getEnumList().add(cgEnum);
        cgEnum.getLangDoc().getTagList().add(
                cgOf.createLangDocTag("author", null, "blanco Framework"));
        cgEnum.setAccess("public");

        // Generate Constructor
        BlancoCgMethod cgConst = cgOf.createMethod(cgEnum.getName(), "Constructor for enum.");
        cgEnum.getMethodList().add(cgConst);
        cgConst.setConstructor(true);
        cgConst.setAccess("");
        List<String> constLines = cgConst.getLineList();

        // Testing the primary constructor.
        BlancoCgField constParam = new BlancoCgField();
        BlancoCgType constType = new BlancoCgType();
        cgEnum.getConstructorArgList().add(constParam);
        constParam.setType(constType);
        constParam.setName("first");
        constParam.setConst(true);
        constParam.setFinal(true);
        constParam.setNotnull(true);
        constType.setName("Integer");

        BlancoCgParameter cgParam01 = cgOf.createParameter(constParam.getName(), constParam.getType().getName(), constParam.getDescription());
        cgConst.getParameterList().add(cgParam01);
        cgParam01.setType(constParam.getType());
        constLines.add("this." + constParam.getName() + " = " + constParam.getName() + ";");

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

        BlancoCgParameter cgParam02 = cgOf.createParameter(constParam.getName(), constParam.getType().getName(), constParam.getDescription());
        cgConst.getParameterList().add(cgParam02);
        cgParam02.setType(constParam.getType());
        constLines.add("this." + constParam.getName() + " = " + constParam.getName() + ";");

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

        // Generates a method.
        final BlancoCgMethod cgMethod = cgOf.createMethod("myMethod",
                "Testing method.");
        cgEnum.getMethodList().add(cgMethod);

        // Adds parameters.
        cgMethod.getParameterList().add(
                cgOf.createParameter("argString", "java.lang.String",
                        "String argument."));
        cgMethod.getParameterList()
                .add(
                        cgOf.createParameter("argDate", "java.util.Date",
                                "Date argument."));
        // Sets the return value.
        cgMethod.setReturn(cgOf.createReturn("boolean", "True if success."));

        final BlancoCgTransformer cgTransformerJava = BlancoCgTransformerFactory
                .getJavaSourceTransformer();
        cgTransformerJava.transform(cgSourceFile, new File("./tmp/blanco"));
    }

    @Test
    public void testTransformerEnum02() throws Exception {
        final BlancoCgObjectFactory cgOf = BlancoCgObjectFactory.getInstance();

        // Generates a source file.
        final BlancoCgSourceFile cgSourceFile = cgOf.createSourceFile("myprog",
                "enum for testing");
        cgSourceFile.getImportList().add("java.text.NumberFormat");

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

        final BlancoCgTransformer cgTransformerJava = BlancoCgTransformerFactory
                .getJavaSourceTransformer();
        cgTransformerJava.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

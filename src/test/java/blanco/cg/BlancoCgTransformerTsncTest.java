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
import blanco.cg.valueobject.*;
import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Generation test for TypeScript NoClass style notation,
 * like as for vue3 components.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoCgTransformerTsncTest extends TestCase {
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
        cgSourceFile.setTabs(4);
        // Normal import test. These will all be ignored.
        cgSourceFile.getImportList().add("java.text.NumberFormat");
        // Import test of the same package.
        cgSourceFile.getImportList().add("myprog.MyClass2");

        // Generates a TypeScript-style import statement.
        cgSourceFile.getHeaderList().add("import {MyInterface} from \"./MyInterface\"");
        cgSourceFile.getHeaderList().add("import {_GettersTree, DefineStoreOptions, StateTree} from \"pinia\"");

        // Generates the class.
        final BlancoCgClass cgClass = cgFactory.createClass("MyTestComponent",
                "This class is for testing.");
        cgSourceFile.getClassList().add(cgClass);
        cgClass.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgClass.setAccess("default");
        cgClass.setNoClassDeclare(true);

        // Generates Plain Text.
        List<String> plainTextList = new ArrayList<>();
        plainTextList.add("export default defineComponent({");
        plainTextList.add("name: 'MyTestComponent',");
        plainTextList.add("props: myTestComponentPropsOptions,");
        plainTextList.add("setup: myTestComponentSetupOptions");
        plainTextList.add("});");
        cgClass.setPlainTextList(plainTextList);

        // Generates a field.
        final BlancoCgField cgField = cgFactory.createField("myTestComponentField",
                "string", "Testing a string field.");
        cgClass.getFieldList().add(cgField);
        cgField.setAccess("export const");
        String arrowFunc =
                "() => {\n" +
                        "\treturn {\n" +
                        "\t\tdummy++;\n" +
                        "\t}\n" +
                        "}";
        cgField.setDefault(arrowFunc);
        cgField.setNotnull(true);
        cgField.setTypeInference(true);

        // Generate a type
        final BlancoCgField cgDeclType = cgFactory.createField("HelloInputEmitsOptions",
                "dummy", "Testing a string field.");
        cgClass.getFieldList().add(cgDeclType);
        cgDeclType.setAccess("export declare type");
        String typeDefine =
                "{\n" +
                        "\t'update': (value: string) => boolean,\n" +
                        "\t'downgrade': (value: number) => boolean\n" +
                        "} | ObjectEmitsOptions";
        cgDeclType.setDefault(typeDefine);
        cgDeclType.setNotnull(true);
        cgDeclType.setTypeInference(true);

        // Generates a function.
        final BlancoCgMethod cgFunction = cgFactory.createMethod("defineStoreOptions<Id extends string, S extends StateTree = {}, G extends _GettersTree<S> = {}, A = {}>",
                "Testing a function.");
        cgClass.getMethodList().add(cgFunction);
        // TypeScript uses the "get" accessor.
        cgFunction.setAccess("export function");

        // Sets the return value.
        BlancoCgReturn cgReturn = cgFactory.createReturn("Omit", "Returns the value.");
        cgFunction.setReturn(cgReturn);
        BlancoCgType cgType = cgReturn.getType();
        cgType.setGenerics("DefineStoreOptions<Id, S, G, A>, 'id'");
        cgReturn.setNullable(false);

        // Adds a parameter.
        BlancoCgParameter cgParameter = cgFactory.createParameter("options", "Omit",
                "String argument.");
        cgFunction.getParameterList().add(cgParameter);
        BlancoCgType cgParamType = cgParameter.getType();
        cgParamType.setGenerics("DefineStoreOptions<Id, S, G, A>, 'id'");
        cgParameter.setNotnull(true);

        // Adds the contents of the method.
        cgFunction.getLineList().add("return options;");

        // Generate interface within the same file
        // Generates the class.
        final BlancoCgInterface cgInterface = cgFactory.createInterface(
                "HelloMessagePropsOptions", "This interface is for testing.");
        cgSourceFile.getInterfaceList().add(cgInterface);
        cgInterface.getLangDoc().getTagList().add(
                cgFactory.createLangDocTag("author", null, "blanco Framework"));
        cgInterface.setAccess("public");

        // Generate fields
        final BlancoCgField cgPropertiy = cgFactory.createField("message", "string", "Testing method.");
        cgInterface.getFieldList().add(cgPropertiy);
        cgPropertiy.setNotnull(false);
        cgPropertiy.setAccess("public");
        final BlancoCgField cgPropertiy2 = cgFactory.createField("name", "string", "Testing method.");
        cgInterface.getFieldList().add(cgPropertiy2);
        cgPropertiy2.setNotnull(true);
        cgPropertiy2.setAccess("public");

        final BlancoCgTransformer cgTransformerTs = BlancoCgTransformerFactory
                .getTsSourceTransformer();
        cgTransformerTs.transform(cgSourceFile, new File("./tmp/blanco"));
    }
}

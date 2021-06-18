/*
 * blanco Framework
 * Copyright (C) 2004-2017 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
/*
 * Copyright 2017 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blanco.cg;

import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;

/**
 * A factory class for creating blancoCg value objects.
 *
 * This class is used across programming languages.<br>
 * It is recommended that blancoCg value objects be generated via this factory class. <br>
 * However, it does not prohibit the creation of value objects individually.
 *
 * Note: This class is assumed to be non-final. It is assumed that users will extend this class by inheriting from it.
 *
 * There used to be a method called createLine, but it has been deprecated.
 *
 * @author IGA Tosiki
 */
public class BlancoCgObjectFactory {

    /**
     * Object factory constructor.
     *
     * It is made private so that it can only be created anew through a factory.
     */
    private BlancoCgObjectFactory() {
    }

    /**
     * Gets an instance of BlancoCg object factory.
     *
     * @return An instance of BlancoCg object factory.
     */
    public static BlancoCgObjectFactory getInstance() {
        return new BlancoCgObjectFactory();
    }

    /**
     * Creates a source file instance.
     *
     * When you call this method, please note that the file name is not explicitly specified. <br>
     * The source file name is derived from the class name.
     *
     * @param argPackageName
     *            Package name. This package name determines the directory structure during auto-generation.
     * @param argDescription
     *            Source file description.
     * @return Source file instance.
     */
    public BlancoCgSourceFile createSourceFile(final String argPackageName,
            final String argDescription) {
        final BlancoCgSourceFile cgSourceFile = new BlancoCgSourceFile();
        cgSourceFile.setPackage(argPackageName);
        cgSourceFile.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgSourceFile.setLangDoc(new BlancoCgLangDoc());

        return cgSourceFile;
    }

    /**
     * Creates a type instance.
     *
     * As for the array flag and generics specification, please sest them in the generated object.
     *
     * @param argTypeName
     *            Type name. Note that the class and interface names that include the package name are specified.
     * @return Type instance.
     */
    public BlancoCgType createType(final String argTypeName) {
        final BlancoCgType cgType = new BlancoCgType();
        cgType.setName(BlancoCgSourceUtil.getTypeNameWithoutGenerics(argTypeName));

        // Description is not set when it is generated from the factory.

        // Stores generics, if any.
        cgType.setGenerics(BlancoCgSourceUtil.getGenericsFromFullName(argTypeName));

        return cgType;
    }

    /**
     * Creates a class instance.
     *
     * @param argClassName
     *            Class name. Note that the class name is specified excluding the package name.
     *            The package name is derived by referring to the source file instance.
     * @param argDescription
     *            Class description.
     * @return Class instance.
     */
    public BlancoCgClass createClass(final String argClassName,
            final String argDescription) {
        final BlancoCgClass cgClass = new BlancoCgClass();
        cgClass.setName(argClassName);
        cgClass.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgClass.setLangDoc(new BlancoCgLangDoc());

        return cgClass;
    }

    /**
     * Creates an interface instance.
     *
     * @param argInterfaceName
     *            Interface name. Note that the interface name is specified excluding the package name.
     *            The package name is derived by referring to the source file instance.
     * @param argDescription
     *            Interface description.
     * @return Interface instance.
     */
    public BlancoCgInterface createInterface(final String argInterfaceName,
            final String argDescription) {
        final BlancoCgInterface cgInterface = new BlancoCgInterface();
        cgInterface.setName(argInterfaceName);
        cgInterface.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgInterface.setLangDoc(new BlancoCgLangDoc());

        return cgInterface;
    }

    /**
     * Creates a field instance.
     *
     * @param argName
     *            Variable name of the field.
     * @param argTypeNameWithPackage
     *            Type name with package name.
     * @param argDescription
     *            Field description.
     * @return Field instance.
     */
    public BlancoCgField createField(final String argName,
            final String argTypeNameWithPackage, final String argDescription) {
        final BlancoCgField cgField = new BlancoCgField();
        cgField.setName(argName);
        cgField.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgField.setLangDoc(new BlancoCgLangDoc());

        // Creates a type object and sets the information.
        cgField.setType(createType(argTypeNameWithPackage));

        return cgField;
    }

    /**
     * Creates a method instance.
     *
     * @param methodName
     *            Method name.
     * @param argDescription
     *            Method description.
     * @return Method instance.
     */
    public BlancoCgMethod createMethod(final String methodName,
            final String argDescription) {
        final BlancoCgMethod cgMethod = new BlancoCgMethod();
        cgMethod.setName(methodName);
        cgMethod.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgMethod.setLangDoc(new BlancoCgLangDoc());

        return cgMethod;
    }

    /**
     * Creates a parameter instance.
     *
     * @param argName
     *            Parameter argument name.
     * @param argFullTypeName
     *            Full type name.
     * @param argDescription
     *            Description.
     * @return Parameter instance.
     */
    public BlancoCgParameter createParameter(final String argName,
            final String argFullTypeName, final String argDescription) {
        return createParameter(argName, argFullTypeName, argDescription, false);
    }

    /**
     * Creates a parameter instance.
     *
     * @param argName
     *            Parameter argument name.
     * @param argFullTypeName
     *            Full type name.
     * @param argDescription
     *            Description.
     * @param argNotNull
     *            Whether or not a non-null constraint will be given.
     * @return Parameter instance.
     */
    public BlancoCgParameter createParameter(final String argName,
            final String argFullTypeName, final String argDescription,
            final boolean argNotNull) {
        final BlancoCgParameter cgParameter = new BlancoCgParameter();
        cgParameter.setName(argName);
        cgParameter.setDescription(argDescription);
        cgParameter.setNotnull(argNotNull);

        // An instance of language document does not exist in the parameter instance.

        // Creates a type object and sets the information.
        cgParameter.setType(createType(argFullTypeName));

        return cgParameter;
    }

    /**
     * Creates instance of virtual parameter (generic).
     *
     * @param argName
     *            Name of virtual parameter.
     * @param argFullTypeName
     *            Full qualified type name.
     * @param argDescription
     *            Description.
     * @return An instance of virtual parameter.
     */
    public BlancoCgVirtualParameter createVirtualParameter(
            final String argName,
            final String argFullTypeName,
            final String argDescription
    ) {
        final BlancoCgVirtualParameter cgParameter = new BlancoCgVirtualParameter();
        cgParameter.setName(argName);
        cgParameter.setDescription(argDescription);
        // Creates type object and sets informations.
        cgParameter.setType(createType(argFullTypeName));

        return cgParameter;
    }

    /**
     * Creates a local variable definition instance.
     *
     */
    public BlancoCgLocalVariable createLocalVariable(final String argName,
            final String argType) {
        final BlancoCgLocalVariable cgLocalVariable = new BlancoCgLocalVariable();
        cgLocalVariable.setName(argName);

        // An instance of language document does not exist in the local variable definition instance.

        // Creates type object and sets informations.
        cgLocalVariable.setType(createType(argType));

        return cgLocalVariable;
    }

    /**
     * Creates a Return instance.
     *
     * @param argFullTypeName
     *            Full type name.
     * @param argDescription
     *            Return value description.
     * @return Return instance.
     */
    public BlancoCgReturn createReturn(final String argFullTypeName,
            final String argDescription) {
        final BlancoCgReturn cgReturn = new BlancoCgReturn();
        cgReturn.setDescription(argDescription);

        // An instance of language document does not exist in the Return instance.

        // Creates type object and sets informations.
        cgReturn.setType(createType(argFullTypeName));

        return cgReturn;
    }

    /**
     * Creates an exception instance.
     *
     * @param argFullTypeName
     *            Full type name.
     * @param argDescription
     *            Description.
     * @return Exception instance.
     */
    public BlancoCgException createException(final String argFullTypeName,
            final String argDescription) {
        final BlancoCgException cgException = new BlancoCgException();
        cgException.setDescription(argDescription);

        // An instance of language document does not exist in the exception instance.

        // Creates type object and sets informations.
        cgException.setType(createType(argFullTypeName));

        return cgException;
    }

    /**
     * Creates an enumeration instance.
     *
     * @param argEnumName
     *            Enumeration name.
     * @param argDescription
     *            Enumeration description.
     * @return Enumeration instance.
     */
    public BlancoCgEnum createEnum(final String argEnumName,
            final String argDescription) {
        final BlancoCgEnum cgEnum = new BlancoCgEnum();
        cgEnum.setName(argEnumName);
        cgEnum.setDescription(argDescription);

        // Creates an instance of language document by default.
        cgEnum.setLangDoc(new BlancoCgLangDoc());

        return cgEnum;
    }

    /**
     * Creates an instance of an element of the enumeration.
     *
     * @param argEnumElementName
     *            Enumeration element name.
     * @param argDescription
     *            Enumeration element description.
     * @return Enumeration element instance.
     */
    public BlancoCgEnumElement createEnumElement(
            final String argEnumElementName, final String argDescription) {
        final BlancoCgEnumElement cgEnumElement = new BlancoCgEnumElement();
        cgEnumElement.setName(argEnumElementName);
        cgEnumElement.setDescription(argDescription);

        return cgEnumElement;
    }

    /**
     * Generates a tag for language documentation.
     *
     * @param argName
     *            Tag name.
     * @param argKey
     *            The key name of the tag. Gives null if you don't want to specify it.
     * @param argValue
     *            Value of the tag.
     * @return Row instance.
     */
    public BlancoCgLangDocTag createLangDocTag(final String argName,
            final String argKey, final String argValue) {
        final BlancoCgLangDocTag cgTag = new BlancoCgLangDocTag();
        cgTag.setName(argName);
        cgTag.setKey(argKey);
        cgTag.setValue(argValue);

        return cgTag;
    }
}

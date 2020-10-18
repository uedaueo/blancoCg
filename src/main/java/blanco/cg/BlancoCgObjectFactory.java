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
 * blancoCgのバリューオブジェクトを作成するためのファクトリクラスです。
 *
 * このクラスはプログラミング言語を超えて利用されます。<br>
 * blancoCgのバリューオブジェクトは、このファクトリクラスを経由して生成することが推奨されます。 <br>
 * とはいえ個別にバリューオブジェクトを生成することは禁止していません。
 *
 * ※このクラスは 非finalとします。利用者がこのクラスを継承して拡張することを想定します。
 *
 * 以前 createLine というメソッドがありましたが、廃止されました。
 *
 * @author IGA Tosiki
 */
public class BlancoCgObjectFactory {

    /**
     * オブジェクトファクトリのコンストラクタ。
     *
     * private化して、ファクトリを通じてしか新規作成できないようにしています。
     */
    private BlancoCgObjectFactory() {
    }

    /**
     * BlancoCgオブジェクトファクトリのインスタンスを取得します。
     *
     * @return BlancoCgオブジェクトファクトリのインスタンス。
     */
    public static BlancoCgObjectFactory getInstance() {
        return new BlancoCgObjectFactory();
    }

    /**
     * ソースファイルインスタンスを生成します。
     *
     * ファイル名は明示的に指定していないという点に注意して呼び出してください。<br>
     * ソースファイル名はクラス名から導出されます。
     *
     * @param argPackageName
     *            パッケージ名。このパッケージ名から自動生成時のディレクトリ構造が決定されます。
     * @param argDescription
     *            ソースファイルの説明。
     * @return ソースファイルインスタンス。
     */
    public BlancoCgSourceFile createSourceFile(final String argPackageName,
            final String argDescription) {
        final BlancoCgSourceFile cgSourceFile = new BlancoCgSourceFile();
        cgSourceFile.setPackage(argPackageName);
        cgSourceFile.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgSourceFile.setLangDoc(new BlancoCgLangDoc());

        return cgSourceFile;
    }

    /**
     * 型インスタンスを生成します。
     *
     * 配列フラグやジェネリクス指定については、生成後のオブジェクトにセットしてください。
     *
     * @param argTypeName
     *            型名。パッケージ名を含んだクラス名・インタフェース名を指定する点に注意してください。
     * @return 型インスタンス。
     */
    public BlancoCgType createType(final String argTypeName) {
        final BlancoCgType cgType = new BlancoCgType();
        cgType.setName(BlancoCgSourceUtil.getTypeNameWithoutGenerics(argTypeName));

        // Descriptionについては、ファクトリからの生成時にはセットしません。

        // ジェネリクスがあるばあいには、それを格納
        cgType.setGenerics(BlancoCgSourceUtil.getGenericsFromFullName(argTypeName));

        return cgType;
    }

    /**
     * クラスインスタンスを生成します。
     *
     * @param argClassName
     *            クラス名。パッケージ名を除くクラス名を指定する点に注意してください。パッケージ名はソースファイルインスタンスを参照した上で導出されます
     *            。
     * @param argDescription
     *            クラスの説明。
     * @return クラスインスタンス。
     */
    public BlancoCgClass createClass(final String argClassName,
            final String argDescription) {
        final BlancoCgClass cgClass = new BlancoCgClass();
        cgClass.setName(argClassName);
        cgClass.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgClass.setLangDoc(new BlancoCgLangDoc());

        return cgClass;
    }

    /**
     * インタフェースインスタンスを生成します。
     *
     * @param argInterfaceName
     *            インタフェース名。パッケージ名を除くインタフェース名を指定する点に注意してください。
     *            パッケージ名はソースファイルインスタンスを参照した上で導出されます。
     * @param argDescription
     *            インタフェースの説明。
     * @return インタフェースインスタンス。
     */
    public BlancoCgInterface createInterface(final String argInterfaceName,
            final String argDescription) {
        final BlancoCgInterface cgInterface = new BlancoCgInterface();
        cgInterface.setName(argInterfaceName);
        cgInterface.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgInterface.setLangDoc(new BlancoCgLangDoc());

        return cgInterface;
    }

    /**
     * フィールドインスタンスを生成します。
     *
     * @param argName
     *            フィールドの変数名。
     * @param argTypeNameWithPackage
     *            パッケージ名付きの型名。
     * @param argDescription
     *            フィールドの説明。
     * @return フィールドインスタンス。
     */
    public BlancoCgField createField(final String argName,
            final String argTypeNameWithPackage, final String argDescription) {
        final BlancoCgField cgField = new BlancoCgField();
        cgField.setName(argName);
        cgField.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgField.setLangDoc(new BlancoCgLangDoc());

        // 型オブジェクトを作成して、情報をセットします。
        cgField.setType(createType(argTypeNameWithPackage));

        return cgField;
    }

    /**
     * メソッドインスタンスを生成します。
     *
     * @param methodName
     *            メソッド名。
     * @param argDescription
     *            メソッドの説明。
     * @return メソッドインスタンス。
     */
    public BlancoCgMethod createMethod(final String methodName,
            final String argDescription) {
        final BlancoCgMethod cgMethod = new BlancoCgMethod();
        cgMethod.setName(methodName);
        cgMethod.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgMethod.setLangDoc(new BlancoCgLangDoc());

        return cgMethod;
    }

    /**
     * パラメータインスタンスを生成します。
     *
     * @param argName
     *            パラメータの引数名。
     * @param argFullTypeName
     *            フル型名。
     * @param argDescription
     *            説明。
     * @return パラメータインスタンス。
     */
    public BlancoCgParameter createParameter(final String argName,
            final String argFullTypeName, final String argDescription) {
        return createParameter(argName, argFullTypeName, argDescription, false);
    }

    /**
     * パラメータインスタンスを生成します。
     *
     * @param argName
     *            パラメータの引数名。
     * @param argFullTypeName
     *            フル型名。
     * @param argDescription
     *            説明。
     * @param argNotNull
     *            非null制約が付与されるかどうか。
     * @return パラメータインスタンス。
     */
    public BlancoCgParameter createParameter(final String argName,
            final String argFullTypeName, final String argDescription,
            final boolean argNotNull) {
        final BlancoCgParameter cgParameter = new BlancoCgParameter();
        cgParameter.setName(argName);
        cgParameter.setDescription(argDescription);
        cgParameter.setNotnull(argNotNull);

        // 言語ドキュメントのインスタンスは、パラメータインスタンスには存在しません。

        // 型オブジェクトを作成して、情報をセットします。
        cgParameter.setType(createType(argFullTypeName));

        return cgParameter;
    }

    /**
     * create instance of virtual parameter (generic)
     *
     * @param argName
     *            name of virtual parameter
     * @param argFullTypeName
     *            full qualified type name
     * @param argDescription
     *            description
     * @return instance of virtual parameter
     */
    public BlancoCgVirtualParameter createVirtualParameter(
            final String argName,
            final String argFullTypeName,
            final String argDescription
    ) {
        final BlancoCgVirtualParameter cgParameter = new BlancoCgVirtualParameter();
        cgParameter.setName(argName);
        cgParameter.setDescription(argDescription);
        // create type object and set informations.
        cgParameter.setType(createType(argFullTypeName));

        return cgParameter;
    }

    /**
     * ローカル変数定義インスタンスを生成します。
     *
     */
    public BlancoCgLocalVariable createLocalVariable(final String argName,
            final String argType) {
        final BlancoCgLocalVariable cgLocalVariable = new BlancoCgLocalVariable();
        cgLocalVariable.setName(argName);

        // 言語ドキュメントのインスタンスは、ローカル変数定義インスタンスには存在しません。

        // 型オブジェクトを作成して、情報をセットします。
        cgLocalVariable.setType(createType(argType));

        return cgLocalVariable;
    }

    /**
     * Returnインスタンスを生成します。
     *
     * @param argFullTypeName
     *            フル型名。
     * @param argDescription
     *            戻り値の説明。
     * @return Returnインスタンス。
     */
    public BlancoCgReturn createReturn(final String argFullTypeName,
            final String argDescription) {
        final BlancoCgReturn cgReturn = new BlancoCgReturn();
        cgReturn.setDescription(argDescription);

        // 言語ドキュメントのインスタンスは、Returnインスタンスには存在しません。

        // 型オブジェクトを作成して、情報をセットします。
        cgReturn.setType(createType(argFullTypeName));

        return cgReturn;
    }

    /**
     * 例外インスタンスを生成します。
     *
     * @param argFullTypeName
     *            フル型名。
     * @param argDescription
     *            説明。
     * @return 例外インスタンス。
     */
    public BlancoCgException createException(final String argFullTypeName,
            final String argDescription) {
        final BlancoCgException cgException = new BlancoCgException();
        cgException.setDescription(argDescription);

        // 言語ドキュメントのインスタンスは、例外インスタンスには存在しません。

        // 型オブジェクトを作成して、情報をセットします。
        cgException.setType(createType(argFullTypeName));

        return cgException;
    }

    /**
     * 列挙体インスタンスを生成します。
     *
     * @param argEnumName
     *            列挙体の名前。
     * @param argDescription
     *            列挙体の説明。
     * @return 列挙体インスタンス。
     */
    public BlancoCgEnum createEnum(final String argEnumName,
            final String argDescription) {
        final BlancoCgEnum cgEnum = new BlancoCgEnum();
        cgEnum.setName(argEnumName);
        cgEnum.setDescription(argDescription);

        // 言語ドキュメントのインスタンスをデフォルトで生成します。
        cgEnum.setLangDoc(new BlancoCgLangDoc());

        return cgEnum;
    }

    /**
     * 列挙体の要素のインスタンスを生成します。
     *
     * @param argEnumElementName
     *            列挙体の要素の名前。
     * @param argDescription
     *            列挙体の要素の説明。
     * @return 列挙体要素インスタンス。
     */
    public BlancoCgEnumElement createEnumElement(
            final String argEnumElementName, final String argDescription) {
        final BlancoCgEnumElement cgEnumElement = new BlancoCgEnumElement();
        cgEnumElement.setName(argEnumElementName);
        cgEnumElement.setDescription(argDescription);

        return cgEnumElement;
    }

    /**
     * 言語ドキュメントのタグを生成します。
     *
     * @param argName
     *            タグの名前。
     * @param argKey
     *            タグのキー名。指定したく無い場合にはnullを与えます。
     * @param argValue
     *            タグの値。
     * @return 行インスタンス。
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

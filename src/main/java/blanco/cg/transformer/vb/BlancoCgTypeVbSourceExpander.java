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
package blanco.cg.transformer.vb;

import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgType into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgTypeVbSourceExpander {
    /**
     * A list of reserved words for programming languages.
     */
    private static final String[] LANGUAGE_RESERVED_KEYWORD = { "Void", "Byte",
            "Short", "Integer", "Long", "Single", "Double", "Decimal",
            "Boolean", "Date", "Char", "String", "Variant" };

    /**
     * Converts blancoCg type to a concrete string.
     * 
     * It also expands "[]", which indicates arrays, and generics.<br>
     * TODO: The same class name of multiple packages (e.g., java.util.Date and java.sql.Date) is not considered.
     * The feature to use the same class name of multiple packages in a single source file is not provided.
     * 
     * @param argType
     *            Type on blancoCg.
     * @return A string indicading a type in a programming language.
     */
    public static String toTypeString(final BlancoCgType argType) {
        final StringBuffer buf = new StringBuffer();
        buf.append(BlancoNameUtil.trimJavaPackage(argType.getName()));

        // Expands an array.
        if (argType.getArray()) {
            buf.append("[]");
        }

        // Expands generics.
        if (BlancoStringUtil.null2Blank(argType.getGenerics()).length() > 0) {
            // TODO: Generics of VB.NET need to be scrutinized again.
            buf.append(argType.getGenerics());
        }

        return buf.toString();
    }

    /**
     * Checks if the given string is a reserved word of a programming language.
     * 
     * @param argCheck
     *            A string to be checked.
     * @return Whether or not it corresponded to a reserved word.
     */
    public static boolean isLanguageReservedKeyword(final String argCheck) {
        for (int index = 0; index < LANGUAGE_RESERVED_KEYWORD.length; index++) {
            if (LANGUAGE_RESERVED_KEYWORD[index].equals(argCheck)) {
                // This string is a reserved word.
                return true;
            }
        }

        // Does not match for the keywords. This string is not a reserved word.
        return false;
    }
}

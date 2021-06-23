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
package blanco.cg.transformer.cs;

import java.util.List;

import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgLangDoc;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.cg.valueobject.BlancoCgType;
import blanco.commons.util.BlancoStringUtil;

/**
 * Expands BlancoCgInterface into source code.
 * 
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * @author IGA Tosiki
 */
class BlancoCgInterfaceCsSourceExpander {

    /**
     * Expands the interface here.
     * 
     * C#.NET has an interface similar to that of Java.
     * 
     * @param cgInterface
     *            The interface to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformInterface(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // In the case of an interface, "public" is excluded from fields and methods as in the Java.

        // First, it expands the interface information into LangDoc.
        if (cgInterface.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            cgInterface.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgInterface.getLangDoc().getTitle() == null) {
            cgInterface.getLangDoc().setTitle(cgInterface.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocCsSourceExpander().transformLangDoc(cgInterface
                .getLangDoc(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgInterface.getAccess()).length() > 0) {
            buf.append(cgInterface.getAccess() + " ");
        }
        // Static and final are not expanded.
        buf.append("interface " + cgInterface.getName());

        // Expands the parent class here.
        expandExtendClassList(cgInterface, buf);

        // Point: The parent interface expansion does not exist in interface.

        argSourceLines.add(buf.toString());

        argSourceLines.add("{");

        // Expands the field here.
        expandFieldList(cgInterface, argSourceFile, argSourceLines);

        // Expands the method here.
        expandMethodList(cgInterface, argSourceFile, argSourceLines);

        argSourceLines.add("}");
    }

    /**
     * Expands the parent class.
     * 
     * @param cgClass
     * @param buf
     */
    private void expandExtendClassList(final BlancoCgInterface cgClass,
            final StringBuffer buf) {
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType type = cgClass.getExtendClassList().get(index);

            if (index == 0) {
                buf.append(" : "
                        + BlancoCgTypeCsSourceExpander.toTypeString(type));
            } else {
                // TODO: Checks to see if the inheritance in C#.NET is a one-time event.
                buf.append(", "
                        + BlancoCgTypeCsSourceExpander.toTypeString(type));
            }
        }
    }

    /**
     * Expands each of the included fields.
     * 
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandFieldList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }

        for (int index = 0; index < cgInterface.getFieldList().size(); index++) {
            final BlancoCgField cgField = cgInterface.getFieldList().get(index);
            new BlancoCgFieldCsSourceExpander().transformField(cgField,
                    argSourceFile, argSourceLines, true);
        }
    }

    /**
     * Expands each of the included methods.
     * 
     * @param cgInterface
     * @param argSourceFile
     * @param argSourceLines
     */
    private void expandMethodList(final BlancoCgInterface cgInterface,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgInterface.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of method.");
        }
        for (int index = 0; index < cgInterface.getMethodList().size(); index++) {
            final BlancoCgMethod cgMethod = cgInterface.getMethodList().get(
                    index);
            new BlancoCgMethodCsSourceExpander().transformMethod(cgMethod,
                    argSourceFile, argSourceLines, true);
        }
    }
}

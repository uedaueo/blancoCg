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
 * Copyright 2019 Ueda Ueo
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
package blanco.cg.transformer.kotlin;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Expands BlancoCgClass into source code.
 *
 * This class is a separate expansion feature of the transformer that auto-generates source code from blancoCg value objects.
 *
 * @author IGA Tosiki
 */
class BlancoCgClassKotlinSourceExpander {

    /**
     * Expands the class here.
     *
     * @param cgClass
     *            A class to be processed.
     * @param argSourceLines
     *            Source code.
     */
    public void transformClass(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // First, it expands the class information into a LangDoc.
        if (cgClass.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
            cgClass.setLangDoc(new BlancoCgLangDoc());
        }
        if (cgClass.getLangDoc().getTitle() == null) {
            cgClass.getLangDoc().setTitle(cgClass.getDescription());
        }

        // Next, it expands LangDoc into source code format.
        new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(cgClass
                .getLangDoc(), argSourceLines);

        // Expands annotations.
        BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, cgClass.getAnnotationList(), argSourceLines);

        final StringBuffer buf = new StringBuffer();

        if (BlancoStringUtil.null2Blank(cgClass.getAccess()).length() > 0) {
            // In Kotlin, it defaults public.
            if (!"public".equals(cgClass.getAccess())) {
                buf.append(cgClass.getAccess() + " ");
            }
        }
        if (cgClass.getAbstract()) {
            buf.append("abstract ");
        }
        // In Kotlin, it defaults final.
        if (!cgClass.getFinal()) {
            buf.append("open ");
        }
        buf.append("class " + cgClass.getName());

        // Expands the Generic of the class.
        if (cgClass.getGenerics() != null && cgClass.getGenerics().length() > 0) {
            buf.append("<" + cgClass.getGenerics() + ">");
        }

        // Expands the primary constructor.
        /*
         * In Kotlin, the primary constructor is written as part of the class definition.
         */
        expandPrimaryConstructorList(cgClass, argSourceFile, buf, argSourceLines);

        // Expands a parent class.
        boolean expanded = expandExtendClassList(cgClass, argSourceFile, buf);

        // Expands a parent interface.
        expandImplementInterfaceList(cgClass, argSourceFile, buf, expanded);

        boolean hasFields = false;
        if (cgClass.getMethodList().size() > 0 ||
                cgClass.getEnumList().size() > 0 ||
                cgClass.getFieldList().size() > 0
        ) {
            // The start of a class block.
            buf.append(" {");
            hasFields = true;
        }

        // Finalizes the line and performs the export.
        argSourceLines.add(buf.toString());

        // In Kotlin, an enumeration is defined as a class, but for the time being, it is not subject to auto-generation. (tueda)

        // Expands the field and method here.
        expandFieldAndMethodList(cgClass, argSourceFile, argSourceLines);

        if (hasFields) {
            // The end of a class block.
            argSourceLines.add("}");
        }
    }

    /**
     * Expands the primary constructor of Kotlin.
     *
     * @param cgClass
     *            A value object of the class.
     * @param argSourceFile
     *            Source file.
     * @param argBuf
     *            Output string buffer.
     */
    private void expandPrimaryConstructorList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final StringBuffer argBuf,
            final List<java.lang.String> argSourceLines) {
        List<blanco.cg.valueobject.BlancoCgField> constructorArgs = cgClass.getConstructorArgList();
        if (constructorArgs == null || constructorArgs.size() <= 0) {
            return;
        }

        argBuf.append(" constructor (");

        // If the Constractor specification exists, a new line is created here.
        argSourceLines.add(argBuf.toString());
        argBuf.delete(0, argBuf.length());

        int count = 0;
        for (blanco.cg.valueobject.BlancoCgField constField : constructorArgs) {
            final BlancoCgType cgConstArgType = BlancoCgSourceUtil.parseTypeWithGenerics(constField.getType());
            constField.setType(cgConstArgType);

            // Adds a type to the import statement.
            BlancoCgSourceFileKotlinSourceExpander.typeToImport(cgConstArgType, argSourceFile);

            if (count != 0) {
                argBuf.append(",");
                argSourceLines.add(argBuf.toString());
                argBuf.delete(0, argBuf.length());
            }

        // First, it expands the class information into a LangDoc.
        if (constField.getLangDoc() == null) {
            // If LangDoc is not specified, creates an instance here.
                constField.setLangDoc(new BlancoCgLangDoc());
            }
            if (constField.getLangDoc().getTitle() == null) {
                constField.getLangDoc().setTitle(constField.getDescription());
            }

            // Next, it expands LangDoc into source code format.
            new BlancoCgLangDocKotlinSourceExpander().transformLangDoc(constField.getLangDoc(), argSourceLines);

            // Expands annotations.
            BlancoCgLineUtil.expandAnnotationList(BlancoCgSupportedLang.KOTLIN, constField.getAnnotationList(), argSourceLines);

            // In Kotlin, it defaults final.
            // However, if override is specified, it must be specified as final.
            argBuf.append("    ");
            if (constField.getOverride()) {
                if (constField.getFinal()) {
                    argBuf.append("final ");
                }
                argBuf.append("override "); // "override" is open by default.
            } else if (!constField.getFinal()) {
                argBuf.append("open ");
            }

            // Sets whether the variable is modifiable or not.
            // Since it is a constructor argument, I think val is usually sufficient. (tueda)
            if (constField.getConst()) {
                argBuf.append("val ");
            } else {
                argBuf.append("var ");
            }
            argBuf.append(constField.getName() + " : " + BlancoCgTypeKotlinSourceExpander.toTypeString(cgConstArgType));
            if (!constField.getNotnull()) {
                // nullable
                argBuf.append("?");
            }

            // If a default value is specified, this will be expanded.
            if (BlancoStringUtil.null2Blank(constField.getDefault()).length() > 0) {
                argBuf.append(" = " + constField.getDefault());
            }
            count++;
        }
        if (argBuf.length() > 0) {
            argSourceLines.add(argBuf.toString());
            argBuf.delete(0, argBuf.length());
        }
        argBuf.append(")");
    }

    /**
     * Expands the parent class.
     *
     * Note: This method must not be called as a common process during BlancoCgInterface expansion.
     * We believe that this commonization will hinder understanding.
     *
     * @param cgClass
     *            A value object of the class.
     * @param argBuf
     *            Output string buffer.
     */
    private boolean expandExtendClassList(final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile, final StringBuffer argBuf) {
        boolean expanded = false;
        for (int index = 0; index < cgClass.getExtendClassList().size(); index++) {
            final BlancoCgType orgType = cgClass.getExtendClassList().get(index);
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(orgType);
            type.setConstructorArgs(orgType.getConstructorArgs());
            final String constractorArg = type.getConstructorArgs();

            // Adds a type to the import statement.
            BlancoCgSourceFileKotlinSourceExpander.typeToImport(type, argSourceFile);

            if (index == 0) {
                argBuf.append(" : "
                        + BlancoCgTypeKotlinSourceExpander.toTypeString(type) + "(" + (constractorArg == null ? "" : constractorArg) + ")");
            } else {
                throw new IllegalArgumentException("In Kotlin, inheritance can only be performed once.");
            }
            expanded = true;
        }
        return expanded;

    }

    /**
     * Expands the parent interface.
     *
     * @param cgClass
     *            The class being processed.
     * @param argBuf
     * @param expanded
     */
    private void expandImplementInterfaceList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final StringBuffer argBuf,
            boolean expanded) {
        /*
         * Gets a list of delegation.
         */
        Map<String, String> delegateMap = cgClass.getDelegateMap();

        for (int index = 0; index < cgClass.getImplementInterfaceList().size(); index++) {
            final BlancoCgType type = BlancoCgSourceUtil.parseTypeWithGenerics(cgClass.getImplementInterfaceList().get(index));

            // Adds a type to the import statement.
            if (BlancoCgSourceUtil.isCanonicalClassName(BlancoCgSupportedLang.KOTLIN, type.getName())) {
                argSourceFile.getImportList().add(type.getName());
            }

            if (index == 0 && !expanded) {
                argBuf.append(" : ");
            } else {
                argBuf.append(", ");
            }

            String typeString = BlancoCgTypeKotlinSourceExpander.toTypeString(type);

            /*
             * Sets up if there is delegation.
             */
            String delegateArg = delegateMap.get(type.getName());
//            System.out.println("type: " + type.getName() + ", delegateArg: " + delegateArg);
            if (delegateArg != null && delegateArg.length() != 0) {
                /*
                 * Ignores if not in primary constructor.
                 */
                List<blanco.cg.valueobject.BlancoCgField> constructorArgs = cgClass.getConstructorArgList();
                boolean found = false;
                for (BlancoCgField arg : constructorArgs) {
                    if (delegateArg.equals(arg.getName())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    typeString += " by " + delegateArg;
                }
            }
            argBuf.append(typeString);
        }
    }

    /**
     * Expands each field contained in the class.
     *
     * TODO: It is necessary to give priority to constant declarations, and then expands variable declarations.<br>
     * Currently, the source code is expanded in the order of registration.
     *
     * @param cgClass
     *            The class being processed.
     * @param argSourceFile
     *            Source file.
     * @param argSourceLines
     *            A source code line list.
     */
    private void expandFieldAndMethodList(
            final BlancoCgClass cgClass,
            final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        if (cgClass.getFieldList() == null) {
            // A null was given for the list of fields.
            // Make sure to set the list of fields to List.
            throw new IllegalArgumentException("A null was given for the list of fields.");
        }
        if (cgClass.getMethodList() == null) {
            throw new IllegalArgumentException("A null was given for the list of methods.");
        }

        // In Kotlin, static fields and Method are declared in the companion object, so the field expansion is divided into two steps.
        // If there is a call from Java, it is assumed to be handled by @JvmField annotation.

        // First, expands the static field.
        boolean foundStatic = false;
        for (BlancoCgField cgField : cgClass.getFieldList()) {
            if (cgField.getStatic()) {
                if (!foundStatic) {
                    argSourceLines.add("companion object {");
                    foundStatic = true;
                }
                new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                        argSourceFile, argSourceLines, false);
            }
        }

        // Next, expands the static Method.
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // Expands as a method of the class.
            if (cgMethod.getStatic()) {
                if (!foundStatic) {
                    argSourceLines.add("companion object {");
                    foundStatic = true;
                }
                new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                        argSourceFile, argSourceLines, false);
            }
        }
        if (foundStatic) {
            argSourceLines.add("}");
        }

        // Expands non-static field.
        for (BlancoCgField cgField : cgClass.getFieldList()) {
            if (!cgField.getStatic()) {
                new BlancoCgFieldKotlinSourceExpander().transformField(cgField,
                        argSourceFile, argSourceLines, false);
            }
        }

        // Finally, expands the non-static Method.
        for (BlancoCgMethod cgMethod : cgClass.getMethodList()) {
            // Expands as a method of the class.
            if (!cgMethod.getStatic()) {
                new BlancoCgMethodKotlinSourceExpander().transformMethod(cgMethod,
                        argSourceFile, argSourceLines, false);
            }
        }
    }
}

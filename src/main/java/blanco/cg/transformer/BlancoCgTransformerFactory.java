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
package blanco.cg.transformer;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.BlancoCgTransformer;
import blanco.cg.transformer.cpp11.BlancoCgCpp11SourceTransformer;
import blanco.cg.transformer.cs.BlancoCgCsSourceTransformer;
import blanco.cg.transformer.delphi.BlancoCgDelphiSourceTransformer;
import blanco.cg.transformer.java.BlancoCgJavaSourceTransformer;
import blanco.cg.transformer.js.BlancoCgJsSourceTransformer;
import blanco.cg.transformer.kotlin.BlancoCgKotlinSourceTransformer;
import blanco.cg.transformer.php.BlancoCgPhpSourceTransformer;
import blanco.cg.transformer.python.BlancoCgPythonSourceTransformer;
import blanco.cg.transformer.ruby.BlancoCgRubySourceTransformer;
import blanco.cg.transformer.swift.BlancoCgSwiftSourceTransformer;
import blanco.cg.transformer.ts.BlancoCgTsSourceTransformer;
import blanco.cg.transformer.vb.BlancoCgVbSourceTransformer;

/**
 * A factory to get the BlancoCgTransformer.
 *
 * BlancoCgTransformer converts blancoCg value objects to source code.
 * Note that in the current specification, the source code conversion can only be performed once, since the contents of the value object are updated during the conversion.
 *
 * @author IGA Tosiki
 */
public class BlancoCgTransformerFactory {
    /**
     * Gets a transformer corresponding to the specified programming language.
     *
     * @param targetLang
     *            A programming language of the transformer you want to get, specified by BlancoCgSupportedLang.
     * @return A transformer for source code conversion.
     */
    public static final BlancoCgTransformer getSourceTransformer(
            final int targetLang) {
        switch (targetLang) {
        case BlancoCgSupportedLang.JAVA:
            return BlancoCgTransformerFactory.getJavaSourceTransformer();
        case BlancoCgSupportedLang.CS:
            return BlancoCgTransformerFactory.getCsSourceTransformer();
        case BlancoCgSupportedLang.JS:
            return BlancoCgTransformerFactory.getJsSourceTransformer();
        case BlancoCgSupportedLang.VB:
            return BlancoCgTransformerFactory.getVbSourceTransformer();
        case BlancoCgSupportedLang.PHP:
            return BlancoCgTransformerFactory.getPhpSourceTransformer();
        case BlancoCgSupportedLang.RUBY:
            return BlancoCgTransformerFactory.getRubySourceTransformer();
        case BlancoCgSupportedLang.PYTHON:
            return BlancoCgTransformerFactory.getPythonSourceTransformer();
        case BlancoCgSupportedLang.DELPHI:
            return BlancoCgTransformerFactory.getDelphiSourceTransformer();
        case BlancoCgSupportedLang.CPP11:
            return BlancoCgTransformerFactory.getCpp11SourceTransformer();
        case BlancoCgSupportedLang.SWIFT:
            return BlancoCgTransformerFactory.getSwiftSourceTransformer();
        case BlancoCgSupportedLang.KOTLIN:
            return BlancoCgTransformerFactory.getKotlinSourceTransformer();
        case BlancoCgSupportedLang.TS:
            return BlancoCgTransformerFactory.getTsSourceTransformer();
        default:
            throw new IllegalArgumentException(
                    "BlancoCgTransformerFactory.getSourceTransformer: An unsupported programming language ("
                            + targetLang + ") was specified.");
        }
    }

    /**
     * Gets a transformer that generates the Java source code.
     *
     * @return A transformer that generates the Java source code.
     */
    public static BlancoCgTransformer getJavaSourceTransformer() {
        return new BlancoCgJavaSourceTransformer();
    }

    /**
     * Gets a transformer that generates the C#.NET source code.
     *
     * @return A transformer that generates the C#.NET source code.
     */
    public static BlancoCgTransformer getCsSourceTransformer() {
        return new BlancoCgCsSourceTransformer();
    }

    /**
     * Gets a transformer that generates the JavaScript source code.
     *
     * @return A transformer that generates the JavaScript source code.
     */
    public static BlancoCgTransformer getJsSourceTransformer() {
        return new BlancoCgJsSourceTransformer();
    }

    /**
     * Gets a transformer that generates the VB.NET source code.
     *
     * @return A transformer that generates the VB.NET source code.
     */
    public static BlancoCgTransformer getVbSourceTransformer() {
        return new BlancoCgVbSourceTransformer();
    }

    /**
     * Gets a transformer that generates the PHP source code.
     *
     * @return A transformer that generates the PHP source code.
     */
    public static BlancoCgTransformer getPhpSourceTransformer() {
        return new BlancoCgPhpSourceTransformer();
    }

    /**
     *
     * Gets a transformer that generates the Ruby source code.
     *
     * @return A transformer that generates the Ruby source code.
     */
    public static BlancoCgTransformer getRubySourceTransformer() {
        return new BlancoCgRubySourceTransformer();
    }

    /**
     *
     * Gets a transformer that generates the Python source code.
     *
     * @return A transformer that generates the Python source code.
     */
    public static BlancoCgTransformer getPythonSourceTransformer() {
        return new BlancoCgPythonSourceTransformer();
    }

    /**
     * Gets a transformer that generates the Delphi source code.
     *
     * @return A transformer that generates the Delphi source code.
     */
    public static BlancoCgTransformer getDelphiSourceTransformer() {
        return new BlancoCgDelphiSourceTransformer();
    }

    /**
     * Gets a transformer that generates the C++11 source code.
     *
     * @return A transformer that generates the C++11 source code.
     */
    public static BlancoCgTransformer getCpp11SourceTransformer() {
        return new BlancoCgCpp11SourceTransformer();
    }

    /**
     * Gets a transformer that generates the Swift source code.
     *
     * @return A transformer that generates the Swift source code.
     */
    public static BlancoCgTransformer getSwiftSourceTransformer() {
        return new BlancoCgSwiftSourceTransformer();
    }

    /**
     * Gets a transformer that generates the Kotlin source code.
     *
     * @return A transformer that generates the Kotlin source code.
     */
    public static BlancoCgTransformer getKotlinSourceTransformer() {
        return new BlancoCgKotlinSourceTransformer();
    }

    /**
     * Gets a transformer that generates the TypeScript source code.
     *
     * @return A transformer that generates the TypeScript source code.
     */
    public static BlancoCgTransformer getTsSourceTransformer() {
        return new BlancoCgTsSourceTransformer();
    }
}

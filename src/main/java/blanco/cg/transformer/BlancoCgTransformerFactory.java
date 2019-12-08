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
import blanco.cg.transformer.vb.BlancoCgVbSourceTransformer;

/**
 * BlancoCgTransformerを取得するためのファクトリです。
 *
 * BlancoCgTransformerは、blancoCgのバリューオブジェクトをソースコードに変換します。
 * 現在の仕様では、変換時にバリューオブジェクトの内容が更新されるため、ソースコード変換は１度しか実行できない点にご注意下さい。
 *
 * @author IGA Tosiki
 */
public class BlancoCgTransformerFactory {
    /**
     * 指定されたプログラミング言語に対応したトランスフォーマーを取得します。
     *
     * @param targetLang
     *            取得したいトランスフォーマのプログラミング言語。BlancoCgSupportedLangで指定します。
     * @return ソースコード変換のためのトランスフォーマー。
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
        default:
            throw new IllegalArgumentException(
                    "BlancoCgTransformerFactory.getSourceTransformer: 対応しないプログラミング言語("
                            + targetLang + ")が指定されました。");
        }
    }

    /**
     * Javaソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Java言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getJavaSourceTransformer() {
        return new BlancoCgJavaSourceTransformer();
    }

    /**
     * C#.NETソースコードを生成するトランスフォーマーを取得します。
     *
     * @return C#.NET言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getCsSourceTransformer() {
        return new BlancoCgCsSourceTransformer();
    }

    /**
     * JavaScriptソースコードを生成するトランスフォーマーを取得します。
     *
     * @return JavaScript言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getJsSourceTransformer() {
        return new BlancoCgJsSourceTransformer();
    }

    /**
     * VB.NETソースコードを生成するトランスフォーマーを取得します。
     *
     * @return VB.NET言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getVbSourceTransformer() {
        return new BlancoCgVbSourceTransformer();
    }

    /**
     * PHPソースコードを生成するトランスフォーマーを取得します。
     *
     * @return PHP言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getPhpSourceTransformer() {
        return new BlancoCgPhpSourceTransformer();
    }

    /**
     *
     * Rubyソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Ruby言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getRubySourceTransformer() {
        return new BlancoCgRubySourceTransformer();
    }

    /**
     *
     * Pythonソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Python言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getPythonSourceTransformer() {
        return new BlancoCgPythonSourceTransformer();
    }

    /**
     * Delphiソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Delphi言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getDelphiSourceTransformer() {
        return new BlancoCgDelphiSourceTransformer();
    }

    /**
     * C++11 ソースコードを生成するトランスフォーマーを取得します。
     *
     * @return C++11 言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getCpp11SourceTransformer() {
        return new BlancoCgCpp11SourceTransformer();
    }

    /**
     * Swift ソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Swift 言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getSwiftSourceTransformer() {
        return new BlancoCgSwiftSourceTransformer();
    }

    /**
     * Kotlin ソースコードを生成するトランスフォーマーを取得します。
     *
     * @return Kotlin 言語ソースコードを生成するトランスフォーマー。
     */
    public static BlancoCgTransformer getKotlinSourceTransformer() {
        return new BlancoCgKotlinSourceTransformer();
    }
}

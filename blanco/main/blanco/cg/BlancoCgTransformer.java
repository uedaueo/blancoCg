/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.cg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * blancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーのインタフェースです。
 *
 * 個別の言語用のソースコード自動生成部分は、このインタフェースを実装して実現されます。
 *
 * @author IGA Tosiki
 */
public interface BlancoCgTransformer {
    /**
     * ソースファイル・バリューオブジェクトをJavaソースコードに変換して出力先ディレクトリに出力します。
     *
     * このAPIではパッケージ構造をディレクトリ構造として考慮します。
     *
     * @param sourceFile ソースファイル・バリューオブジェクト。
     * @param outputDirectory 出力先ルートディレクトリ。
     */
    void transform(final BlancoCgSourceFile sourceFile, final File outputDirectory);

    /**
     * ソースファイル・バリューオブジェクトをソースコードに変換してライターに出力します。
     *
     * このAPIではパッケージ構造をディレクトリ構造とは考慮しません。
     *
     * @param sourceFile ソースファイル・バリューオブジェクト。
     * @param writer 出力先のライター。
     * @throws IOException 入出力例外が発生した場合。
     */
    void transform(final BlancoCgSourceFile sourceFile, final BufferedWriter writer) throws IOException;
}

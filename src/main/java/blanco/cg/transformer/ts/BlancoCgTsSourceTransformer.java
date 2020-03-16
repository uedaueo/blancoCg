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
package blanco.cg.transformer.ts;

import blanco.cg.transformer.AbstractBlancoCgJavaStyleTransformer;
import blanco.cg.valueobject.BlancoCgSourceFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * blancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーのエントリポイントです。
 *
 * BlancoCgTransformerFactoryを経由して生成することを推奨します。<br>
 * このトランスフォーマーではバリューオブジェクトを TypeScript ソースコードへと変換します。
 *
 * @author IGA Tosiki
 */
public class BlancoCgTsSourceTransformer extends
        AbstractBlancoCgJavaStyleTransformer {

    /**
     * ソースファイル・バリューオブジェクトをkotlinソースコードに変換してライターに出力します。
     *
     * このAPIではパッケージ構造をディレクトリ構造とは考慮しません。この処理の中ではライターに向けて出力するだけです。
     *
     * @param argSourceFile
     *            ソースファイル・バリューオブジェクト。
     * @param argWriter
     *            出力先のライター。
     * @throws IOException 入出力例外が発生した場合
     */
    public void transform(final BlancoCgSourceFile argSourceFile,
            final BufferedWriter argWriter) throws IOException {
        if (argSourceFile == null) {
            throw new IllegalArgumentException("ソースファイルにnullが与えられました。処理中断します。");
        }
        if (argWriter == null) {
            throw new IllegalArgumentException("出力先ライターにnullが与えられました。処理中断します。");
        }

        final List<String> sourceLines = new BlancoCgSourceFileTsSourceExpander()
                .transformSourceFile(argSourceFile);

        // ソースコードを整形します。
        formatSource(sourceLines);

        // ソースコードをライタへと出力します。
        source2Writer(sourceLines, argWriter);

        // 念のためフラッシュを実施。
        argWriter.flush();
    }

    /**
     * ソースコードに付けられる拡張子を取得します。
     *
     * @return ソースコードに付けられる拡張子。
     */
    protected String getSourceFileExt() {
        return ".ts";
    }
}

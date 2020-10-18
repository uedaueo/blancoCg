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

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import blanco.cg.BlancoCgTransformer;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * プログラミング言語の種類をまたがる抽象的なトランスフォーマーです。
 *
 * @author IGA Tosiki
 */
abstract class AbstractBlancoCgTransformer implements BlancoCgTransformer {
    /**
     * コマンドラインに表示する際のメッセージプレフィックス。
     */
    protected static final String CMDLINE_PREFIX = "cg: ";

    /**
     * ソースファイルの拡張子を取得します。
     *
     * @return 拡張子。
     */
    protected abstract String getSourceFileExt();

    /**
     * ファイル名をクラス名またはインタフェース名から導出します。
     *
     * このメソッドは、まだファイル名が確定していない場合にのみ呼び出します。
     *
     * @param argSourceFile
     *            ソースファイルオブジェクト。
     */
    protected void decideFilenameFromClassOrInterfaceName(
            final BlancoCgSourceFile argSourceFile) {
        // ファイル名が未設定の場合に、BlancoCgSourceFile(ファイル)の中に含まれるクラス名からファイル名の解決を試みます。
        String className = null;
        for (int index = 0; index < argSourceFile.getClassList().size(); index++) {
            final BlancoCgClass cgClass = argSourceFile.getClassList().get(
                    index);

            className = cgClass.getName();
            break;
        }

        if (className == null) {
            // まだファイル名が決定していない場合には、インタフェースの一覧からもクラス名の導出を試みます。
            for (int index = 0; index < argSourceFile.getInterfaceList().size(); index++) {
                final BlancoCgInterface cgInterface = argSourceFile
                        .getInterfaceList().get(index);

                className = cgInterface.getName();
                break;
            }
        }

        if (className == null) {
            // それでもクラス名が確定しない場合には例外として扱います。
            throw new IllegalArgumentException(
                    "ソースファイル名の指定がなかったのでクラスのリストからクラス名の確定を試みましたが、クラス名は確定できませんでした。");
        }

        // ソースファイル名の確定をおこないます。
        // バリューオブジェクトのソースファイル名を更新している点に注意してください。
        argSourceFile.setName(className);
    }

    /**
     * ソースコードをライターへ出力します。
     *
     * java.lang.Stringのリストをライターへと出力します。
     *
     * @param argSourceLines
     *            ソースコード行リスト。
     * @param writer
     *            出力先ライタ。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    protected void source2Writer(final List<java.lang.String> argSourceLines,
            final BufferedWriter writer) throws IOException {
        boolean isPastLineBlank = false;
        boolean isPastBlockStart = false;
        String lineSeparator = System.getProperty("line.separator");
        /*
         * for debugging
         */
        String lineSeparatorMark = "other, use system default.";
        if ("\n".equals(lineSeparator)) {
            lineSeparatorMark = "LF";
        } else if ("\r\n".equals(lineSeparator)) {
            lineSeparatorMark = "CRLF";
        } else if ("\r".equals(lineSeparator)) {
            lineSeparatorMark = "CR";
        } else {
            lineSeparator = System.lineSeparator();
        }
//        System.out.println(CMDLINE_PREFIX + "lineSeparator = " + lineSeparatorMark);
        for (int index = 0; index < argSourceLines.size(); index++) {
            final String line = argSourceLines.get(index);

            // 連続する空行の出力を抑制します。
            if (line.length() == 0) {
                if (isPastLineBlank) {
                    // 前回に引き続き今回も空行であったため 今回は出力を見送ります。
                    continue;
                }
                // 今回は空行でした。
                isPastLineBlank = true;
            } else {
                // 今回は空行ではありません。
                isPastLineBlank = false;
            }

            if (isPastBlockStart && line.length() == 0) {
                // 前回がブロックのスタートで、今回が空行の場合には出力を見送ります。
                continue;
            }

            if (line.endsWith("{")) {
                isPastBlockStart = true;
            } else {
                isPastBlockStart = false;
            }

            // 行を 1行 出力します。
            writer.write(line);
//            writer.newLine();
            writer.write(lineSeparator);
        }
    }
}

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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameUtil;

/**
 * BlancoCgSourceFileのなかの import情報を展開します。
 *
 * このクラスはblancoCgのバリューオブジェクトからソースコードを自動生成するトランスフォーマーの個別の展開機能です。<br>
 * import展開は意外にも複雑な処理です。
 *
 * @author IGA Tosiki
 */
class BlancoCgImportKotlinSourceExpander {
    /**
     * このクラスが処理対象とするプログラミング言語。
     */
    protected static final int TARGET_LANG = BlancoCgSupportedLang.KOTLIN;

    /**
     * ソート時に優先して処理されるパッケージ一覧。
     */
    private static final String[] PREFERRED_PACKAGE = { "java.", "javax.",
            "org.", "blanco.", "com." };

    /**
     * import文を展開するためのアンカー文字列。
     */
    private static final String REPLACE_IMPORT_HERE = "/*replace import here*/";

    /**
     * 発見されたアンカー文字列のインデックス。
     *
     * このクラスの処理の過程で import文が編集されますが、その都度 この値も更新されます。
     */
    private int fFindReplaceImport = -1;

    /**
     * importを展開します。
     *
     * このメソッドはクラス展開・メソッド展開など一式が終了した後に呼び出すようにします。
     *
     * @param argSourceFile
     *            ソースファイルインスタンス。
     * @param argSourceLines
     *            ソース行イメージ。(java.lang.Stringが格納されます)
     */
    public void transformImport(final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argSourceLines) {
        // import対象のクラス名終端に付与されている配列表現を除去します。
        trimArraySuffix(argSourceFile.getImportList());

        // 最初にimport文をソートして処理を行いやすくします。
        sortImport(argSourceFile.getImportList());

        // 重複するimport文を除去します。
        trimRepeatedImport(argSourceFile.getImportList());

        // importする必要のないクラスを除去します
        trimUnnecessaryImport(argSourceFile.getImportList());

        // 自クラスが所属するパッケージに対するimportを抑制します。
        trimMyselfImport(argSourceFile, argSourceFile.getImportList());

        // アンカー文字列を検索します。
        fFindReplaceImport = findAnchorString(argSourceLines);
        if (fFindReplaceImport < 0) {
            throw new IllegalArgumentException("import文の置換文字列を発見することができませんでした。");
        }

        for (int indexPreferredPackage = 0; indexPreferredPackage < PREFERRED_PACKAGE.length; indexPreferredPackage++) {
            // 優先パッケージを最初に展開します。
            expandImportWithTarget(argSourceFile,
                    PREFERRED_PACKAGE[indexPreferredPackage], argSourceLines);
        }

        // 最後に優先パッケージ以外 (「java.」「javax.」など以外)のパッケージを展開します。
        expandImportWithTarget(argSourceFile, null, argSourceLines);

        // アンカー文字列を除去します。
        removeAnchorString(argSourceLines);
    }

    /**
     * 展開対象となるターゲットを意識してインポートを展開します。
     *
     * @param argSourceFile
     * @param argTarget
     *            java. または javax. または nullを指定します。
     * @param argSourceLines
     *            ソースコード行リスト。
     */
    private void expandImportWithTarget(final BlancoCgSourceFile argSourceFile,
            final String argTarget, final List<java.lang.String> argSourceLines) {
        boolean isProcessed = false;
        for (int index = 0; index < argSourceFile.getImportList().size(); index++) {
            final String strImport = argSourceFile.getImportList().get(index);

            if (argTarget == null) {
                // 優先パッケージ以外 (java. javax. 以外) を展開します。
                if (isPreferredPackage(strImport)) {
                    // 処理対象とするパッケージ以外であるので、処理をスキップします。
                    // ※java. および javax. はハードコードされている点に注意してください。
                    continue;
                }
            } else {
                if (strImport.startsWith(argTarget) == false) {
                    // 処理対象とするパッケージ以外であるので、処理をスキップします。
                    continue;
                }
            }

            isProcessed = true;
            argSourceLines.add(fFindReplaceImport++, "import " + strImport
                    + BlancoCgLineUtil.getTerminator(TARGET_LANG));
        }

        if (isProcessed) {
            // import展開処理が存在した場合にのみ空白を付与します。
            argSourceLines.add(fFindReplaceImport++, "");
        }
    }

    /**
     * 置換アンカー文字列の行数(0オリジン)を検索します。
     *
     * @return 発見したアンカー文字列の位置(0オリジン)。発見できなかった場合には-1。
     * @param argSourceLines
     *            ソースリスト。
     */
    private static final int findAnchorString(
            final List<java.lang.String> argSourceLines) {
        for (int index = 0; index < argSourceLines.size(); index++) {
            final String line = argSourceLines.get(index);
            if (line.equals(REPLACE_IMPORT_HERE)) {
                // 発見しました。
                return index;
            }
        }

        // 発見できませんでした。発見できなかったことを示す -1 を戻します。
        return -1;
    }

    /**
     * アンカー文字列を挿入します。
     *
     * 処理の後半でインポート文を編成しなおしますが、その際に参照するアンカー文字列を追加しておきます。<br>
     * このメソッドは他のクラスから呼び出されます。
     *
     * @param argSourceLines
     *            ソースリスト。
     */
    public static final void insertAnchorString(
            final List<java.lang.String> argSourceLines) {
        argSourceLines
                .add(BlancoCgImportKotlinSourceExpander.REPLACE_IMPORT_HERE);
    }

    /**
     * アンカー文字列を除去します。
     *
     * @param argSourceLines
     *            ソースリスト。
     */
    private static final void removeAnchorString(
            final List<java.lang.String> argSourceLines) {
        // 最後にアンカー文字列そのものを除去。
        int findReplaceImport2 = findAnchorString(argSourceLines);
        if (findReplaceImport2 < 0) {
            throw new IllegalArgumentException("import文の置換文字列を発見することができませんでした。");
        }
        argSourceLines.remove(findReplaceImport2);
    }

    /**
     * 与えられたimportをソートします。
     *
     * 想定されるノードの型(java.lang.String)以外が与えられると、例外が発生します。
     *
     * @param argImport
     *            インポートリスト。
     */
    private static final void sortImport(final List<java.lang.String> argImport) {
        Collections.sort(argImport, new Comparator<java.lang.String>() {
            public int compare(final String arg0, final String arg1) {
                if (arg0 instanceof String == false) {
                    throw new IllegalArgumentException("importのリストの値ですが、["
                            + arg0 + "]ですが java.lang.String以外の型["
                            + arg0.getClass().getName() + "]になっています。");
                }
                if (arg1 instanceof String == false) {
                    throw new IllegalArgumentException("importのリストの値ですが、["
                            + arg1 + "]ですが java.lang.String以外の型["
                            + arg1.getClass().getName() + "]になっています。");
                }
                final String str0 = (String) arg0;
                final String str1 = (String) arg1;
                return str0.compareTo(str1);
            }
        });
    }

    /**
     * import対象のクラス名終端に付与されている配列表現を除去します。
     *
     * @param argImport
     *            インポートリスト。
     */
    private void trimArraySuffix(final List<java.lang.String> argImport) {
        for (int index = 0; index < argImport.size(); index++) {
            String strImport = argImport.get(index);
            for (;;) {
                // 配列表現で終了している限り繰り返します。
                if (strImport.endsWith("[]")) {
                    strImport = strImport.substring(0, strImport.length() - 2);
                    argImport.set(index, strImport);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 重複する不要なimportを除去します。
     *
     * このメソッドは、与えられたListが既にソート済みであることを前提とします。
     *
     * @param argImport
     *            インポートリスト。
     */
    private void trimRepeatedImport(final List<java.lang.String> argImport) {
        // 重複するimportを除去。
        String pastImport = "";
        for (int index = argImport.size() - 1; index >= 0; index--) {
            final String strImport = argImport.get(index);
            if (pastImport.equals(strImport)) {
                // 既に処理されている重複するimportです。不要なのでこれを除去します。
                argImport.remove(index);
            }
            // 今回のimportを前回分importとして記憶します。
            pastImport = strImport;
        }
    }

    /**
     * importする必要のないクラスを除去します。
     *
     * 具体的には java.lang や プリミティブ型が不要と判断される対象です。
     *
     * @param argImport
     *            インポートリスト。
     */
    private void trimUnnecessaryImport(final List<java.lang.String> argImport) {
        // まずはプリミティブ型を除去します。
        for (int index = argImport.size() - 1; index >= 0; index--) {
            // ソート時点で型チェックは実施済みです。
            final String strImport = argImport.get(index);

            if (BlancoCgTypeKotlinSourceExpander
                    .isLanguageReservedKeyword(strImport)) {
                argImport.remove(index);
            }
        }

        // 次に java.langを除去します。
        // これは Java言語において java.langパッケージは暗黙のうちにインポートされるパッケージであるからです。
        trimSpecificPackage("java.lang", argImport);

        // 次に kotlin を除去します。
        // これは kotlin 言語において kotlin パッケージは暗黙のうちにインポートされるパッケージであるからです。
        // ただし Int, Long, Float, Double, Number, String はプリミティブ型と同様にパッケージ指定なしでも型として指定できるようにしておきます。
        trimSpecificPackage("kotlin", argImport);

        // 次に kotlin.collections を除去します。
        // これは kotlin 言語において kotlin.collections パッケージは暗黙のうちにインポートされるパッケージであるからです。
        trimSpecificPackage("kotlin.collections", argImport);
    }

    /**
     * 与えられた文字列が優先パッケージであるかどうかをチェックします。
     *
     * @param argCheck
     *            チェックしたい文字列。
     * @return 優先パッケージに該当したかどうか。
     */
    private boolean isPreferredPackage(final String argCheck) {
        for (int index = 0; index < PREFERRED_PACKAGE.length; index++) {
            if (argCheck.startsWith(PREFERRED_PACKAGE[index])) {
                // この文字列は優先パッケージに該当します。
                return true;
            }
        }

        // キーワードにヒットしませんでした。この文字列はプログラミング言語の予約語ではありません。
        return false;
    }

    /**
     * 自分自身が所属するパッケージのimportを除去します。
     *
     * @param argSourceFile
     *            ソースファイルインスタンス。
     * @param argImport
     *            インポートリスト。
     */
    private void trimMyselfImport(final BlancoCgSourceFile argSourceFile,
            final List<java.lang.String> argImport) {
        trimSpecificPackage(argSourceFile.getPackage(), argImport);
    }

    /**
     * 特定のパッケージについて、これをリストから除去します。
     *
     * java.langの除去および自クラスが所属するパッケージの除去に利用されます。
     *
     * @param argSpecificPackage
     *            処理対象とするパッケージ。
     * @param argImport
     *            インポートのリスト。
     */
    private static void trimSpecificPackage(final String argSpecificPackage,
            final List<java.lang.String> argImport) {
        for (int index = argImport.size() - 1; index >= 0; index--) {
            // ソート時点で型チェックは実施済みです。
            final String strImport = argImport.get(index);

            if (strImport.indexOf(".") < 0) {
                // パッケージ構造を持たないため、削除候補からはずします。
                continue;
            }

            // import処理においては、blancoCgのTypeに関する共通処理を利用することはできません。
            // 個別に記述を行います。
            final String strImportWithoutPackage = BlancoNameUtil
                    .trimJavaPackage(strImport);
            final String strPackage = strImport.substring(0, strImport.length()
                    - strImportWithoutPackage.length());

            if ((argSpecificPackage + ".").equals(strPackage)) {
                // java.lang.Stringなどは除去します。
                argImport.remove(index);
            }
        }
    }
}

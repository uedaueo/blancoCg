package blanco.cg;

/**
 * blancoCgが対応する言語の一覧を保持します。
 */
public class BlancoCgSupportedLang {
    /**
     * No.1 説明:Java言語。
     */
    public static final int JAVA = 1;

    /**
     * No.2 説明:C#.NET言語。
     */
    public static final int CS = 2;

    /**
     * No.3 説明:JavaScript言語。
     */
    public static final int JS = 3;

    /**
     * No.4 説明:VB.NET言語。
     */
    public static final int VB = 4;

    /**
     * No.5 説明:PHP言語。
     */
    public static final int PHP = 5;

    /**
     * No.6 説明:Ruby言語。
     */
    public static final int RUBY = 6;

    /**
     * No.7 説明:Python言語。
     */
    public static final int PYTHON = 7;

    /**
     * No.8 説明:Delphi言語。
     */
    public static final int DELPHI = 8;

    /**
     * No.9 説明:C++11言語。
     */
    public static final int CPP11 = 9;

    /**
     * No.10 説明:Swift言語。
     */
    public static final int SWIFT = 10;

    /**
     * No.11 説明:Kotlin言語。
     */
    public static final int KOTLIN = 11;

    /**
     * 未定義。文字列グループ以外の文字列または定数が未定義のもの。
     */
    public static final int NOT_DEFINED = -1;

    /**
     * 文字列グループに含まれる文字列であるかどうかを判定します。
     *
     * @param argCheck チェックを行いたい文字列。
     * @return 文字列グループに含まれていればture。グループに含まれない文字列であればfalse。
     */
    public boolean match(final String argCheck) {
        // No.1
        // 説明:Java言語。
        if ("java".equals(argCheck)) {
            return true;
        }
        // No.2
        // 説明:C#.NET言語。
        if ("cs".equals(argCheck)) {
            return true;
        }
        // No.3
        // 説明:JavaScript言語。
        if ("js".equals(argCheck)) {
            return true;
        }
        // No.4
        // 説明:VB.NET言語。
        if ("vb".equals(argCheck)) {
            return true;
        }
        // No.5
        // 説明:PHP言語。
        if ("php".equals(argCheck)) {
            return true;
        }
        // No.6
        // 説明:Ruby言語。
        if ("ruby".equals(argCheck)) {
            return true;
        }
        // No.7
        // 説明:Python言語。
        if ("python".equals(argCheck)) {
            return true;
        }
        // No.8
        // 説明:Delphi言語。
        if ("delphi".equals(argCheck)) {
            return true;
        }
        // No.9
        // 説明:C++11言語。
        if ("cpp11".equals(argCheck)) {
            return true;
        }
        // No.10
        // 説明:Swift言語。
        if ("swift".equals(argCheck)) {
            return true;
        }
        // No.11
        // 説明:Kotlin言語。
        if ("kotlin".equals(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * 文字列グループに含まれる文字列であるかどうかを、大文字小文字を区別せず判定します。
     *
     * @param argCheck チェックを行いたい文字列。
     * @return 文字列グループに含まれていればture。グループに含まれない文字列であればfalse。
     */
    public boolean matchIgnoreCase(final String argCheck) {
        // No.1
        // 説明:Java言語。
        if ("java".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.2
        // 説明:C#.NET言語。
        if ("cs".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.3
        // 説明:JavaScript言語。
        if ("js".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.4
        // 説明:VB.NET言語。
        if ("vb".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.5
        // 説明:PHP言語。
        if ("php".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.6
        // 説明:Ruby言語。
        if ("ruby".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.7
        // 説明:Python言語。
        if ("python".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.8
        // 説明:Delphi言語。
        if ("delphi".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.9
        // 説明:C++11言語。
        if ("cpp11".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.10
        // 説明:Swift言語。
        if ("swift".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.11
        // 説明:Kotlin言語。
        if ("kotlin".equalsIgnoreCase(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * 文字列から定数に変換します。
     *
     * 定数が未定義の場合や 与えられた文字列が文字列グループ外の場合には NOT_DEFINED を戻します。
     *
     * @param argCheck 変換を行いたい文字列。
     * @return 定数に変換後の値。
     */
    public int convertToInt(final String argCheck) {
        // No.1
        // 説明:Java言語。
        if ("java".equals(argCheck)) {
            return JAVA;
        }
        // No.2
        // 説明:C#.NET言語。
        if ("cs".equals(argCheck)) {
            return CS;
        }
        // No.3
        // 説明:JavaScript言語。
        if ("js".equals(argCheck)) {
            return JS;
        }
        // No.4
        // 説明:VB.NET言語。
        if ("vb".equals(argCheck)) {
            return VB;
        }
        // No.5
        // 説明:PHP言語。
        if ("php".equals(argCheck)) {
            return PHP;
        }
        // No.6
        // 説明:Ruby言語。
        if ("ruby".equals(argCheck)) {
            return RUBY;
        }
        // No.7
        // 説明:Python言語。
        if ("python".equals(argCheck)) {
            return PYTHON;
        }
        // No.8
        // 説明:Delphi言語。
        if ("delphi".equals(argCheck)) {
            return DELPHI;
        }
        // No.9
        // 説明:C++11言語。
        if ("cpp11".equals(argCheck)) {
            return CPP11;
        }
        // No.10
        // 説明:Swift言語。
        if ("swift".equals(argCheck)) {
            return SWIFT;
        }
        // No.11
        // 説明:Kotlin言語。
        if ("kotlin".equals(argCheck)) {
            return KOTLIN;
        }

        // 該当する定数が見つかりませんでした。
        return NOT_DEFINED;
    }

    /**
     * 定数から文字列に変換します。
     *
     * 定数と対応づく文字列に変換します。
     *
     * @param argCheck 変換を行いたい文字定数。
     * @return 文字列に変換後の値。NOT_DEFINEDの場合には長さ0の文字列。
     */
    public String convertToString(final int argCheck) {
        // No.1
        // 説明:Java言語。
        if (argCheck == JAVA) {
            return "java";
        }
        // No.2
        // 説明:C#.NET言語。
        if (argCheck == CS) {
            return "cs";
        }
        // No.3
        // 説明:JavaScript言語。
        if (argCheck == JS) {
            return "js";
        }
        // No.4
        // 説明:VB.NET言語。
        if (argCheck == VB) {
            return "vb";
        }
        // No.5
        // 説明:PHP言語。
        if (argCheck == PHP) {
            return "php";
        }
        // No.6
        // 説明:Ruby言語。
        if (argCheck == RUBY) {
            return "ruby";
        }
        // No.7
        // 説明:Python言語。
        if (argCheck == PYTHON) {
            return "python";
        }
        // No.8
        // 説明:Delphi言語。
        if (argCheck == DELPHI) {
            return "delphi";
        }
        // No.9
        // 説明:C++11言語。
        if (argCheck == CPP11) {
            return "cpp11";
        }
        // No.10
        // 説明:Swift言語。
        if (argCheck == SWIFT) {
            return "swift";
        }
        // No.11
        // 説明:Kotlin言語。
        if (argCheck == KOTLIN) {
            return "kotlin";
        }
        // 未定義。
        if (argCheck == NOT_DEFINED) {
            return "";
        }

        // いずれにも該当しませんでした。
        throw new IllegalArgumentException("与えられた値(" + argCheck + ")は文字列グループ[BlancoCgSupportedLang]では定義されない値です。");
    }
}

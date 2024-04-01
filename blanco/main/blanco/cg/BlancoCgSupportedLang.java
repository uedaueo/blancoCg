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
     * No.12 説明:TypeScript言語。
     */
    public static final int TS = 12;

    /**
     * No.13 説明:PHP言語（型宣言対応版）。
     */
    public static final int PHP8 = 13;

    /**
     * Undefined. A string or constant other than a string group that is undefined.
     */
    public static final int NOT_DEFINED = -1;

    /**
     * Determines if a string is part of a string group.
     *
     * @param argCheck A string to be checked.
     * @return true is the string is part of a string group, false otherwise.
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
        // No.12
        // 説明:TypeScript言語。
        if ("ts".equals(argCheck)) {
            return true;
        }
        // No.13
        // 説明:PHP言語（型宣言対応版）。
        if ("php8".equals(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if a string is part of a string group in a case-insentive manner.
     *
     * @param argCheck A string to be checked.
     * @return true is the string is part of a string group, false otherwise.
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
        // No.12
        // 説明:TypeScript言語。
        if ("ts".equalsIgnoreCase(argCheck)) {
            return true;
        }
        // No.13
        // 説明:PHP言語（型宣言対応版）。
        if ("php8".equalsIgnoreCase(argCheck)) {
            return true;
        }
        return false;
    }

    /**
     * Converts a string to a constant.
     *
     * Returns NOT_DEFINED if the constant is undefined or if the given string is outside the string group.
     *
     * @param argCheck A string to be converted.
     * @return The value after conversion to a constant.
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
        // No.12
        // 説明:TypeScript言語。
        if ("ts".equals(argCheck)) {
            return TS;
        }
        // No.13
        // 説明:PHP言語（型宣言対応版）。
        if ("php8".equals(argCheck)) {
            return PHP8;
        }

        // No matching constants were found.
        return NOT_DEFINED;
    }

    /**
     * Converts a constant to a string.
     *
     * Converts to a string corresponding to a constant.
     *
     * @param argCheck A constant to be converted.
     * @return The value after conversion to a string, or a zero-length string if NOT_DEFINED.
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
        // No.12
        // 説明:TypeScript言語。
        if (argCheck == TS) {
            return "ts";
        }
        // No.13
        // 説明:PHP言語（型宣言対応版）。
        if (argCheck == PHP8) {
            return "php8";
        }
        // Undefined.
        if (argCheck == NOT_DEFINED) {
            return "";
        }

        // None of them were applicable.
        throw new IllegalArgumentException("The given value (" + argCheck + ") is a value that is not defined in the string group [BlancoCgSupportedLang].");
    }
}

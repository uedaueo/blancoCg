package blanco.cg.resourcebundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * blancoCg のリソースバンドル。
 *
 * リソースバンドル定義: [BlancoCg]。<BR>
 * このクラスはリソースバンドル定義書から自動生成されたリソースバンドルクラスです。<BR>
 * 既知のロケール<BR>
 * <UL>
 * <LI>ja
 * </UL>
 */
public class BlancoCgResourceBundle {
    /**
     * リソースバンドルオブジェクト。
     *
     * 内部的に実際に入力を行うリソースバンドルを記憶します。
     */
    private ResourceBundle fResourceBundle;

    /**
     * BlancoCgResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoCg]、デフォルトのロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     */
    public BlancoCgResourceBundle() {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/cg/resourcebundle/BlancoCg");
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoCgResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoCg]、指定されたロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     */
    public BlancoCgResourceBundle(final Locale locale) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/cg/resourcebundle/BlancoCg", locale);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoCgResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoCg]、指定されたロケール、指定されたクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     * @param loader クラスローダの指定
     */
    public BlancoCgResourceBundle(final Locale locale, final ClassLoader loader) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/cg/resourcebundle/BlancoCg", locale, loader);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * 内部的に保持しているリソースバンドルオブジェクトを取得します。
     *
     * @return 内部的に保持しているリソースバンドルオブジェクト。
     */
    public ResourceBundle getResourceBundle() {
        return fResourceBundle;
    }

    /**
     * bundle[BlancoCg], key[DEFAULT_FILE_COMMENT]
     *
     * [このソースコードは blanco Frameworkにより自動生成されました。] (ja)<br>
     *
     * @return key[DEFAULT_FILE_COMMENT]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getDefaultFileComment() {
        // 初期値として定義書の値を利用します。
        String strFormat = "このソースコードは blanco Frameworkにより自動生成されました。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("DEFAULT_FILE_COMMENT");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoCg], key[FILE_HEADER_PATH]
     *
     * [./meta/program/fileheader.txt] (ja)<br>
     *
     * @return key[FILE_HEADER_PATH]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getFileHeaderPath() {
        // 初期値として定義書の値を利用します。
        String strFormat = "./meta/program/fileheader.txt";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("FILE_HEADER_PATH");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }
}

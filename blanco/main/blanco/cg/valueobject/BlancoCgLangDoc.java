package blanco.cg.valueobject;

import java.util.List;

/**
 * 言語用のドキュメントを表現するためのバリューオブジェクト。
 *
 * Java言語の場合には JavaDocを表します。自動生成時に他の「説明」フィールドやメソッドのパラメータなどから情報が構築される場合があります。
 * ※ポイント：コメント文字列のエスケープ処理などは、blancoCgに与える前に実施されている必要があります。
 */
public class BlancoCgLangDoc {
    /**
     * この言語ドキュメントのタイトル説明です。他の原料のdescriptionから自動生成されることが多いです。
     *
     * フィールド: [title]。
     */
    private String fTitle;

    /**
     * この言語ドキュメントの詳細説明です。(java.lang.String)のリストです。
     *
     * ここで与えられた文字列がそのままドキュメント説明部に展開されるため、通常は文字参照エンコーディングを実施したあとの値をセットします。(エンコーディング後のものを与えるからこそ、&lt;pre&gt;などを実現することができるのです。)
     * フィールド: [descriptionList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

    /**
     * 推奨されない場合に、非推奨の理由が記載されます。
     *
     * フィールド: [deprecated]。
     */
    private String fDeprecated;

    /**
     * パラメータのリストです。メソッドの場合にのみ利用されます。
     *
     * フィールド: [parameterList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgParameter&gt;()]。
     */
    private List<BlancoCgParameter> fParameterList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgParameter>();

    /**
     * 仮想パラメータ(generic)のリストです。メソッドの場合にのみ利用されます。
     *
     * フィールド: [virtualParameterList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgVirtualParameter&gt;()]。
     */
    private List<BlancoCgVirtualParameter> fVirtualParameterList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgVirtualParameter>();

    /**
     * このメソッドの戻り値です。
     *
     * 戻り値が無い (void)の場合には nullをセットします。
     * フィールド: [return]。
     */
    private BlancoCgReturn fReturn;

    /**
     * 発生しうる例外の一覧です。メソッドの場合にのみ利用されます。
     *
     * フィールド: [throwList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgException&gt;()]。
     */
    private List<BlancoCgException> fThrowList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgException>();

    /**
     * 言語ドキュメントのタグのリスト。BlancoCgLangDocTagがリストに格納されます。
     *
     * フィールド: [tagList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgLangDocTag&gt;()]。
     */
    private List<BlancoCgLangDocTag> fTagList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgLangDocTag>();

    /**
     * フィールド [title] の値を設定します。
     *
     * フィールドの説明: [この言語ドキュメントのタイトル説明です。他の原料のdescriptionから自動生成されることが多いです。]。
     *
     * @param argTitle フィールド[title]に設定する値。
     */
    public void setTitle(final String argTitle) {
        fTitle = argTitle;
    }

    /**
     * フィールド [title] の値を取得します。
     *
     * フィールドの説明: [この言語ドキュメントのタイトル説明です。他の原料のdescriptionから自動生成されることが多いです。]。
     *
     * @return フィールド[title]から取得した値。
     */
    public String getTitle() {
        return fTitle;
    }

    /**
     * フィールド [descriptionList] の値を設定します。
     *
     * フィールドの説明: [この言語ドキュメントの詳細説明です。(java.lang.String)のリストです。]。
     * ここで与えられた文字列がそのままドキュメント説明部に展開されるため、通常は文字参照エンコーディングを実施したあとの値をセットします。(エンコーディング後のものを与えるからこそ、&lt;pre&gt;などを実現することができるのです。)
     *
     * @param argDescriptionList フィールド[descriptionList]に設定する値。
     */
    public void setDescriptionList(final List<String> argDescriptionList) {
        fDescriptionList = argDescriptionList;
    }

    /**
     * フィールド [descriptionList] の値を取得します。
     *
     * フィールドの説明: [この言語ドキュメントの詳細説明です。(java.lang.String)のリストです。]。
     * ここで与えられた文字列がそのままドキュメント説明部に展開されるため、通常は文字参照エンコーディングを実施したあとの値をセットします。(エンコーディング後のものを与えるからこそ、&lt;pre&gt;などを実現することができるのです。)
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[descriptionList]から取得した値。
     */
    public List<String> getDescriptionList() {
        return fDescriptionList;
    }

    /**
     * フィールド [deprecated] の値を設定します。
     *
     * フィールドの説明: [推奨されない場合に、非推奨の理由が記載されます。]。
     *
     * @param argDeprecated フィールド[deprecated]に設定する値。
     */
    public void setDeprecated(final String argDeprecated) {
        fDeprecated = argDeprecated;
    }

    /**
     * フィールド [deprecated] の値を取得します。
     *
     * フィールドの説明: [推奨されない場合に、非推奨の理由が記載されます。]。
     *
     * @return フィールド[deprecated]から取得した値。
     */
    public String getDeprecated() {
        return fDeprecated;
    }

    /**
     * フィールド [parameterList] の値を設定します。
     *
     * フィールドの説明: [パラメータのリストです。メソッドの場合にのみ利用されます。]。
     *
     * @param argParameterList フィールド[parameterList]に設定する値。
     */
    public void setParameterList(final List<BlancoCgParameter> argParameterList) {
        fParameterList = argParameterList;
    }

    /**
     * フィールド [parameterList] の値を取得します。
     *
     * フィールドの説明: [パラメータのリストです。メソッドの場合にのみ利用されます。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgParameter&gt;()]。
     *
     * @return フィールド[parameterList]から取得した値。
     */
    public List<BlancoCgParameter> getParameterList() {
        return fParameterList;
    }

    /**
     * フィールド [virtualParameterList] の値を設定します。
     *
     * フィールドの説明: [仮想パラメータ(generic)のリストです。メソッドの場合にのみ利用されます。]。
     *
     * @param argVirtualParameterList フィールド[virtualParameterList]に設定する値。
     */
    public void setVirtualParameterList(final List<BlancoCgVirtualParameter> argVirtualParameterList) {
        fVirtualParameterList = argVirtualParameterList;
    }

    /**
     * フィールド [virtualParameterList] の値を取得します。
     *
     * フィールドの説明: [仮想パラメータ(generic)のリストです。メソッドの場合にのみ利用されます。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgVirtualParameter&gt;()]。
     *
     * @return フィールド[virtualParameterList]から取得した値。
     */
    public List<BlancoCgVirtualParameter> getVirtualParameterList() {
        return fVirtualParameterList;
    }

    /**
     * フィールド [return] の値を設定します。
     *
     * フィールドの説明: [このメソッドの戻り値です。]。
     * 戻り値が無い (void)の場合には nullをセットします。
     *
     * @param argReturn フィールド[return]に設定する値。
     */
    public void setReturn(final BlancoCgReturn argReturn) {
        fReturn = argReturn;
    }

    /**
     * フィールド [return] の値を取得します。
     *
     * フィールドの説明: [このメソッドの戻り値です。]。
     * 戻り値が無い (void)の場合には nullをセットします。
     *
     * @return フィールド[return]から取得した値。
     */
    public BlancoCgReturn getReturn() {
        return fReturn;
    }

    /**
     * フィールド [throwList] の値を設定します。
     *
     * フィールドの説明: [発生しうる例外の一覧です。メソッドの場合にのみ利用されます。]。
     *
     * @param argThrowList フィールド[throwList]に設定する値。
     */
    public void setThrowList(final List<BlancoCgException> argThrowList) {
        fThrowList = argThrowList;
    }

    /**
     * フィールド [throwList] の値を取得します。
     *
     * フィールドの説明: [発生しうる例外の一覧です。メソッドの場合にのみ利用されます。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgException&gt;()]。
     *
     * @return フィールド[throwList]から取得した値。
     */
    public List<BlancoCgException> getThrowList() {
        return fThrowList;
    }

    /**
     * フィールド [tagList] の値を設定します。
     *
     * フィールドの説明: [言語ドキュメントのタグのリスト。BlancoCgLangDocTagがリストに格納されます。]。
     *
     * @param argTagList フィールド[tagList]に設定する値。
     */
    public void setTagList(final List<BlancoCgLangDocTag> argTagList) {
        fTagList = argTagList;
    }

    /**
     * フィールド [tagList] の値を取得します。
     *
     * フィールドの説明: [言語ドキュメントのタグのリスト。BlancoCgLangDocTagがリストに格納されます。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgLangDocTag&gt;()]。
     *
     * @return フィールド[tagList]から取得した値。
     */
    public List<BlancoCgLangDocTag> getTagList() {
        return fTagList;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.cg.valueobject.BlancoCgLangDoc[");
        buf.append("title=" + fTitle);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append(",deprecated=" + fDeprecated);
        buf.append(",parameterList=" + fParameterList);
        buf.append(",virtualParameterList=" + fVirtualParameterList);
        buf.append(",return=" + fReturn);
        buf.append(",throwList=" + fThrowList);
        buf.append(",tagList=" + fTagList);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoCgLangDoc target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgLangDoc#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fTitle
        // Type: java.lang.String
        target.fTitle = this.fTitle;
        // Name: fDescriptionList
        // Type: java.util.List
        // フィールド[fDescriptionList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fDeprecated
        // Type: java.lang.String
        target.fDeprecated = this.fDeprecated;
        // Name: fParameterList
        // Type: java.util.List
        // フィールド[fParameterList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgParameter]です。
        // Name: fVirtualParameterList
        // Type: java.util.List
        // フィールド[fVirtualParameterList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgVirtualParameter]です。
        // Name: fReturn
        // Type: blanco.cg.valueobject.BlancoCgReturn
        // フィールド[fReturn]はサポート外の型[blanco.cg.valueobject.BlancoCgReturn]です。
        // Name: fThrowList
        // Type: java.util.List
        // フィールド[fThrowList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgException]です。
        // Name: fTagList
        // Type: java.util.List
        // フィールド[fTagList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgLangDocTag]です。
    }
}

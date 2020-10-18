package blanco.cg.valueobject;

import java.util.List;

/**
 * enumを表現するためのバリューオブジェクト。※Java, C#.NET のみで対応。それ以外の言語では未対応。
 */
public class BlancoCgEnum {
    /**
     * この列挙体の名前です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * この列挙体の説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * アクセスコントロールを指定します。public/protected/privateなどを指定します。
     *
     * フィールド: [access]。
     */
    private String fAccess;

    /**
     * enum エレメントをあらわします。
     *
     * フィールド: [elementList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgEnumElement&gt;()]。
     */
    private List<BlancoCgEnumElement> fElementList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgEnumElement>();

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。なおBlancoCgObjectFactoryを経由してインスタンスを取得した際には、既にオブジェクトはセット済みです。
     *
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [この列挙体の名前です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [この列挙体の名前です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [この列挙体の説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [この列挙体の説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [access] の値を設定します。
     *
     * フィールドの説明: [アクセスコントロールを指定します。public/protected/privateなどを指定します。]。
     *
     * @param argAccess フィールド[access]に設定する値。
     */
    public void setAccess(final String argAccess) {
        fAccess = argAccess;
    }

    /**
     * フィールド [access] の値を取得します。
     *
     * フィールドの説明: [アクセスコントロールを指定します。public/protected/privateなどを指定します。]。
     *
     * @return フィールド[access]から取得した値。
     */
    public String getAccess() {
        return fAccess;
    }

    /**
     * フィールド [elementList] の値を設定します。
     *
     * フィールドの説明: [enum エレメントをあらわします。]。
     *
     * @param argElementList フィールド[elementList]に設定する値。
     */
    public void setElementList(final List<BlancoCgEnumElement> argElementList) {
        fElementList = argElementList;
    }

    /**
     * フィールド [elementList] の値を取得します。
     *
     * フィールドの説明: [enum エレメントをあらわします。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgEnumElement&gt;()]。
     *
     * @return フィールド[elementList]から取得した値。
     */
    public List<BlancoCgEnumElement> getElementList() {
        return fElementList;
    }

    /**
     * フィールド [langDoc] の値を設定します。
     *
     * フィールドの説明: [言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。なおBlancoCgObjectFactoryを経由してインスタンスを取得した際には、既にオブジェクトはセット済みです。]。
     *
     * @param argLangDoc フィールド[langDoc]に設定する値。
     */
    public void setLangDoc(final BlancoCgLangDoc argLangDoc) {
        fLangDoc = argLangDoc;
    }

    /**
     * フィールド [langDoc] の値を取得します。
     *
     * フィールドの説明: [言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。なおBlancoCgObjectFactoryを経由してインスタンスを取得した際には、既にオブジェクトはセット済みです。]。
     *
     * @return フィールド[langDoc]から取得した値。
     */
    public BlancoCgLangDoc getLangDoc() {
        return fLangDoc;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.cg.valueobject.BlancoCgEnum[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",access=" + fAccess);
        buf.append(",elementList=" + fElementList);
        buf.append(",langDoc=" + fLangDoc);
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
    public void copyTo(final BlancoCgEnum target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgEnum#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fAccess
        // Type: java.lang.String
        target.fAccess = this.fAccess;
        // Name: fElementList
        // Type: java.util.List
        // フィールド[fElementList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgEnumElement]です。
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // フィールド[fLangDoc]はサポート外の型[blanco.cg.valueobject.BlancoCgLangDoc]です。
    }
}

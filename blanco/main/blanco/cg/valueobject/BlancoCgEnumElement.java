package blanco.cg.valueobject;

/**
 * enumの各要素を表現するためのバリューオブジェクト。
 */
public class BlancoCgEnumElement {
    /**
     * この列挙体の要素の名前です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * この列挙体の要素の説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * (C#.NETのみ対応) デフォルト値をあらわします。
     *
     * Stringなら ""、intなら 3 などのように実際の文を指定します。
     * (ダブルクオートなども含んだ形で表現します。)
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [この列挙体の要素の名前です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [この列挙体の要素の名前です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [この列挙体の要素の説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [この列挙体の要素の説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * フィールドの説明: [(C#.NETのみ対応) デフォルト値をあらわします。]。
     * Stringなら ""、intなら 3 などのように実際の文を指定します。
     * (ダブルクオートなども含んだ形で表現します。)
     *
     * @param argDefault フィールド[default]に設定する値。
     */
    public void setDefault(final String argDefault) {
        fDefault = argDefault;
    }

    /**
     * フィールド [default] の値を取得します。
     *
     * フィールドの説明: [(C#.NETのみ対応) デフォルト値をあらわします。]。
     * Stringなら ""、intなら 3 などのように実際の文を指定します。
     * (ダブルクオートなども含んだ形で表現します。)
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
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
        buf.append("blanco.cg.valueobject.BlancoCgEnumElement[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",default=" + fDefault);
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
    public void copyTo(final BlancoCgEnumElement target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgEnumElement#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
    }
}

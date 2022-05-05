package blanco.cg.valueobject;

/**
 * 戻り値を表現するためのバリューオブジェクト。
 *
 * なお、メソッドの戻り値がvoidである場合には、設定する必要はありません。
 */
public class BlancoCgReturn {
    /**
     * この戻り値の説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * この戻り値の型です。java.lang.Stringなどを指定します。
     *
     * フィールド: [type]。
     */
    private BlancoCgType fType;

    /**
     * 戻り値がnullであることを許容します。現時点ではkotlinでのみ使用されます。
     *
     * フィールド: [nullable]。
     * デフォルト: [false]。
     */
    private Boolean fNullable = false;

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [この戻り値の説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [この戻り値の説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [type] の値を設定します。
     *
     * フィールドの説明: [この戻り値の型です。java.lang.Stringなどを指定します。]。
     *
     * @param argType フィールド[type]に設定する値。
     */
    public void setType(final BlancoCgType argType) {
        fType = argType;
    }

    /**
     * フィールド [type] の値を取得します。
     *
     * フィールドの説明: [この戻り値の型です。java.lang.Stringなどを指定します。]。
     *
     * @return フィールド[type]から取得した値。
     */
    public BlancoCgType getType() {
        return fType;
    }

    /**
     * フィールド [nullable] の値を設定します。
     *
     * フィールドの説明: [戻り値がnullであることを許容します。現時点ではkotlinでのみ使用されます。]。
     *
     * @param argNullable フィールド[nullable]に設定する値。
     */
    public void setNullable(final Boolean argNullable) {
        fNullable = argNullable;
    }

    /**
     * フィールド [nullable] の値を取得します。
     *
     * フィールドの説明: [戻り値がnullであることを許容します。現時点ではkotlinでのみ使用されます。]。
     * デフォルト: [false]。
     *
     * @return フィールド[nullable]から取得した値。
     */
    public Boolean getNullable() {
        return fNullable;
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
        buf.append("blanco.cg.valueobject.BlancoCgReturn[");
        buf.append("description=" + fDescription);
        buf.append(",type=" + fType);
        buf.append(",nullable=" + fNullable);
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
    public void copyTo(final BlancoCgReturn target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgReturn#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fType
        // Type: blanco.cg.valueobject.BlancoCgType
        // フィールド[fType]はサポート外の型[blanco.cg.valueobject.BlancoCgType]です。
        // Name: fNullable
        // Type: java.lang.Boolean
        target.fNullable = this.fNullable;
    }
}

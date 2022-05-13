package blanco.cg.valueobject;

import java.util.List;

/**
 * フィールドを表現するためのバリューオブジェクト。
 *
 * プログラミング言語によってはプロパティと呼ばれることもあります。
 * ※ポイント：クラス名の名前変形や文字列のエスケープ処理などは、blancoCgに与える前に実施されている必要があります。
 */
public class BlancoCgField {
    /**
     * このフィールドの名前です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * このフィールドの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * このフィールドの型です。java.lang.Stringなどを指定します。
     *
     * フィールド: [type]。
     */
    private BlancoCgType fType;

    /**
     * アクセスコントロールを指定します。public/protected/privateなどを指定します。
     *
     * フィールド: [access]。
     * デフォルト: [&quot;private&quot;]。
     */
    private String fAccess = "private";

    /**
     * staticかどうかをあらわします。
     *
     * フィールド: [static]。
     * デフォルト: [false]。
     */
    private boolean fStatic = false;

    /**
     * finalかどうかをあらわします。（kotlinではフィールドのoverrideが可能なので、この修飾子は定数を表しません。）
     *
     * フィールド: [final]。
     * デフォルト: [false]。
     */
    private boolean fFinal = false;

    /**
     * 親クラスのfieldをoverrideしているかどうかを表します。（kotlin専用）
     *
     * フィールド: [override]。
     * デフォルト: [false]。
     */
    private boolean fOverride = false;

    /**
     * 定数かどうかを表します。（Javaではfinalを使用します。）
     *
     * フィールド: [const]。
     * デフォルト: [false]。
     */
    private boolean fConst = false;

    /**
     * このフィールドにnullを与えられた際に引数例外を発生させるかどうか。
     *
     * フィールド: [notnull]。
     * デフォルト: [false]。
     */
    private boolean fNotnull = false;

    /**
     * デフォルト値をあらわします。
     *
     * Stringなら ""、intなら 3 などのように実際の文を指定します。
     * (ダブルクオートなども含んだ形で表現します。)
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * このフィールドに付与されているアノテーションのリストです。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。なおBlancoCgObjectFactoryを経由してインスタンスを取得した際には、既にオブジェクトはセット済みです。
     *
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * 型推論（型宣言の省略）をする場合はtrue
     *
     * フィールド: [typeInference]。
     * デフォルト: [false]。
     */
    private boolean fTypeInference = false;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [このフィールドの名前です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [このフィールドの名前です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [このフィールドの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [このフィールドの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [type] の値を設定します。
     *
     * フィールドの説明: [このフィールドの型です。java.lang.Stringなどを指定します。]。
     *
     * @param argType フィールド[type]に設定する値。
     */
    public void setType(final BlancoCgType argType) {
        fType = argType;
    }

    /**
     * フィールド [type] の値を取得します。
     *
     * フィールドの説明: [このフィールドの型です。java.lang.Stringなどを指定します。]。
     *
     * @return フィールド[type]から取得した値。
     */
    public BlancoCgType getType() {
        return fType;
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
     * デフォルト: [&quot;private&quot;]。
     *
     * @return フィールド[access]から取得した値。
     */
    public String getAccess() {
        return fAccess;
    }

    /**
     * フィールド [static] の値を設定します。
     *
     * フィールドの説明: [staticかどうかをあらわします。]。
     *
     * @param argStatic フィールド[static]に設定する値。
     */
    public void setStatic(final boolean argStatic) {
        fStatic = argStatic;
    }

    /**
     * フィールド [static] の値を取得します。
     *
     * フィールドの説明: [staticかどうかをあらわします。]。
     * デフォルト: [false]。
     *
     * @return フィールド[static]から取得した値。
     */
    public boolean getStatic() {
        return fStatic;
    }

    /**
     * フィールド [final] の値を設定します。
     *
     * フィールドの説明: [finalかどうかをあらわします。（kotlinではフィールドのoverrideが可能なので、この修飾子は定数を表しません。）]。
     *
     * @param argFinal フィールド[final]に設定する値。
     */
    public void setFinal(final boolean argFinal) {
        fFinal = argFinal;
    }

    /**
     * フィールド [final] の値を取得します。
     *
     * フィールドの説明: [finalかどうかをあらわします。（kotlinではフィールドのoverrideが可能なので、この修飾子は定数を表しません。）]。
     * デフォルト: [false]。
     *
     * @return フィールド[final]から取得した値。
     */
    public boolean getFinal() {
        return fFinal;
    }

    /**
     * フィールド [override] の値を設定します。
     *
     * フィールドの説明: [親クラスのfieldをoverrideしているかどうかを表します。（kotlin専用）]。
     *
     * @param argOverride フィールド[override]に設定する値。
     */
    public void setOverride(final boolean argOverride) {
        fOverride = argOverride;
    }

    /**
     * フィールド [override] の値を取得します。
     *
     * フィールドの説明: [親クラスのfieldをoverrideしているかどうかを表します。（kotlin専用）]。
     * デフォルト: [false]。
     *
     * @return フィールド[override]から取得した値。
     */
    public boolean getOverride() {
        return fOverride;
    }

    /**
     * フィールド [const] の値を設定します。
     *
     * フィールドの説明: [定数かどうかを表します。（Javaではfinalを使用します。）]。
     *
     * @param argConst フィールド[const]に設定する値。
     */
    public void setConst(final boolean argConst) {
        fConst = argConst;
    }

    /**
     * フィールド [const] の値を取得します。
     *
     * フィールドの説明: [定数かどうかを表します。（Javaではfinalを使用します。）]。
     * デフォルト: [false]。
     *
     * @return フィールド[const]から取得した値。
     */
    public boolean getConst() {
        return fConst;
    }

    /**
     * フィールド [notnull] の値を設定します。
     *
     * フィールドの説明: [このフィールドにnullを与えられた際に引数例外を発生させるかどうか。]。
     *
     * @param argNotnull フィールド[notnull]に設定する値。
     */
    public void setNotnull(final boolean argNotnull) {
        fNotnull = argNotnull;
    }

    /**
     * フィールド [notnull] の値を取得します。
     *
     * フィールドの説明: [このフィールドにnullを与えられた際に引数例外を発生させるかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[notnull]から取得した値。
     */
    public boolean getNotnull() {
        return fNotnull;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * フィールドの説明: [デフォルト値をあらわします。]。
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
     * フィールドの説明: [デフォルト値をあらわします。]。
     * Stringなら ""、intなら 3 などのように実際の文を指定します。
     * (ダブルクオートなども含んだ形で表現します。)
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [このフィールドに付与されているアノテーションのリストです。]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [このフィールドに付与されているアノテーションのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
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
     * フィールド [typeInference] の値を設定します。
     *
     * フィールドの説明: [型推論（型宣言の省略）をする場合はtrue]。
     *
     * @param argTypeInference フィールド[typeInference]に設定する値。
     */
    public void setTypeInference(final boolean argTypeInference) {
        fTypeInference = argTypeInference;
    }

    /**
     * フィールド [typeInference] の値を取得します。
     *
     * フィールドの説明: [型推論（型宣言の省略）をする場合はtrue]。
     * デフォルト: [false]。
     *
     * @return フィールド[typeInference]から取得した値。
     */
    public boolean getTypeInference() {
        return fTypeInference;
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
        buf.append("blanco.cg.valueobject.BlancoCgField[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",type=" + fType);
        buf.append(",access=" + fAccess);
        buf.append(",static=" + fStatic);
        buf.append(",final=" + fFinal);
        buf.append(",override=" + fOverride);
        buf.append(",const=" + fConst);
        buf.append(",notnull=" + fNotnull);
        buf.append(",default=" + fDefault);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",langDoc=" + fLangDoc);
        buf.append(",typeInference=" + fTypeInference);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoCgField target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgField#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fType
        // Type: blanco.cg.valueobject.BlancoCgType
        // Field[fType] is an unsupported type[blanco.cg.valueobject.BlancoCgType].
        // Name: fAccess
        // Type: java.lang.String
        target.fAccess = this.fAccess;
        // Name: fStatic
        // Type: boolean
        target.fStatic = this.fStatic;
        // Name: fFinal
        // Type: boolean
        target.fFinal = this.fFinal;
        // Name: fOverride
        // Type: boolean
        target.fOverride = this.fOverride;
        // Name: fConst
        // Type: boolean
        target.fConst = this.fConst;
        // Name: fNotnull
        // Type: boolean
        target.fNotnull = this.fNotnull;
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
        // Name: fAnnotationList
        // Type: java.util.List
        // Field[fAnnotationList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // Field[fLangDoc] is an unsupported type[blanco.cg.valueobject.BlancoCgLangDoc].
        // Name: fTypeInference
        // Type: boolean
        target.fTypeInference = this.fTypeInference;
    }
}

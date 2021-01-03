package blanco.cg.valueobject;

import java.util.List;

/**
 * インタフェースを表現するためのバリューオブジェクト。
 *
 * インタフェースを作成したい場合に利用されます。
 * ※ポイント：クラス名の名前変形や文字列のエスケープ処理などは、blancoCgに与える前に実施されている必要があります。
 */
public class BlancoCgInterface {
    /**
     * このインタフェースの名前です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * このインタフェースの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * ジェネリクスを指定します。
     *
     * フィールド: [generics]。
     */
    private String fGenerics;

    /**
     * 継承元クラスのリストです。
     *
     * Java言語では多重継承が禁止されているため、ひとつだけ指定する必要があります。
     * フィールド: [extendClassList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgType&gt;()]。
     */
    private List<BlancoCgType> fExtendClassList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgType>();

    /**
     * このインタフェースのアクセスコントロールを指定します。
     *
     * public/protected/privateなどを指定します。
     * フィールド: [access]。
     * デフォルト: [&quot;public&quot;]。
     */
    private String fAccess = "public";

    /**
     * このインタフェースに付与されているアノテーションのリストです。(java.lang.String)
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * このインタフェースに含まれるフィールドのリストです。
     *
     * フィールド: [fieldList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     */
    private List<BlancoCgField> fFieldList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgField>();

    /**
     * このインタフェースに含まれるメソッドのリストです。
     *
     * フィールド: [methodList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgMethod&gt;()]。
     */
    private List<BlancoCgMethod> fMethodList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgMethod>();

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。
     *
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * クラスのフィールド中に自由に記述できるテキストです。フィールド領域の先頭に、一度だけ記述できます。
     *
     * フィールド: [plainTextList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fPlainTextList = new java.util.ArrayList<java.lang.String>();

    /**
     * 独自アノテーションを定義するためのインタフェースの場合はtrueを設定してください。
     *
     * フィールド: [defineAnnotation]。
     * デフォルト: [false]。
     */
    private Boolean fDefineAnnotation = false;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [このインタフェースの名前です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [このインタフェースの名前です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [このインタフェースの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [このインタフェースの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [generics] の値を設定します。
     *
     * フィールドの説明: [ジェネリクスを指定します。]。
     *
     * @param argGenerics フィールド[generics]に設定する値。
     */
    public void setGenerics(final String argGenerics) {
        fGenerics = argGenerics;
    }

    /**
     * フィールド [generics] の値を取得します。
     *
     * フィールドの説明: [ジェネリクスを指定します。]。
     *
     * @return フィールド[generics]から取得した値。
     */
    public String getGenerics() {
        return fGenerics;
    }

    /**
     * フィールド [extendClassList] の値を設定します。
     *
     * フィールドの説明: [継承元クラスのリストです。]。
     * Java言語では多重継承が禁止されているため、ひとつだけ指定する必要があります。
     *
     * @param argExtendClassList フィールド[extendClassList]に設定する値。
     */
    public void setExtendClassList(final List<BlancoCgType> argExtendClassList) {
        fExtendClassList = argExtendClassList;
    }

    /**
     * フィールド [extendClassList] の値を取得します。
     *
     * フィールドの説明: [継承元クラスのリストです。]。
     * Java言語では多重継承が禁止されているため、ひとつだけ指定する必要があります。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgType&gt;()]。
     *
     * @return フィールド[extendClassList]から取得した値。
     */
    public List<BlancoCgType> getExtendClassList() {
        return fExtendClassList;
    }

    /**
     * フィールド [access] の値を設定します。
     *
     * フィールドの説明: [このインタフェースのアクセスコントロールを指定します。]。
     * public/protected/privateなどを指定します。
     *
     * @param argAccess フィールド[access]に設定する値。
     */
    public void setAccess(final String argAccess) {
        fAccess = argAccess;
    }

    /**
     * フィールド [access] の値を取得します。
     *
     * フィールドの説明: [このインタフェースのアクセスコントロールを指定します。]。
     * public/protected/privateなどを指定します。
     * デフォルト: [&quot;public&quot;]。
     *
     * @return フィールド[access]から取得した値。
     */
    public String getAccess() {
        return fAccess;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [このインタフェースに付与されているアノテーションのリストです。(java.lang.String)]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [このインタフェースに付与されているアノテーションのリストです。(java.lang.String)]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [fieldList] の値を設定します。
     *
     * フィールドの説明: [このインタフェースに含まれるフィールドのリストです。]。
     *
     * @param argFieldList フィールド[fieldList]に設定する値。
     */
    public void setFieldList(final List<BlancoCgField> argFieldList) {
        fFieldList = argFieldList;
    }

    /**
     * フィールド [fieldList] の値を取得します。
     *
     * フィールドの説明: [このインタフェースに含まれるフィールドのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     *
     * @return フィールド[fieldList]から取得した値。
     */
    public List<BlancoCgField> getFieldList() {
        return fFieldList;
    }

    /**
     * フィールド [methodList] の値を設定します。
     *
     * フィールドの説明: [このインタフェースに含まれるメソッドのリストです。]。
     *
     * @param argMethodList フィールド[methodList]に設定する値。
     */
    public void setMethodList(final List<BlancoCgMethod> argMethodList) {
        fMethodList = argMethodList;
    }

    /**
     * フィールド [methodList] の値を取得します。
     *
     * フィールドの説明: [このインタフェースに含まれるメソッドのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgMethod&gt;()]。
     *
     * @return フィールド[methodList]から取得した値。
     */
    public List<BlancoCgMethod> getMethodList() {
        return fMethodList;
    }

    /**
     * フィールド [langDoc] の値を設定します。
     *
     * フィールドの説明: [言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。]。
     *
     * @param argLangDoc フィールド[langDoc]に設定する値。
     */
    public void setLangDoc(final BlancoCgLangDoc argLangDoc) {
        fLangDoc = argLangDoc;
    }

    /**
     * フィールド [langDoc] の値を取得します。
     *
     * フィールドの説明: [言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。]。
     *
     * @return フィールド[langDoc]から取得した値。
     */
    public BlancoCgLangDoc getLangDoc() {
        return fLangDoc;
    }

    /**
     * フィールド [plainTextList] の値を設定します。
     *
     * フィールドの説明: [クラスのフィールド中に自由に記述できるテキストです。フィールド領域の先頭に、一度だけ記述できます。]。
     *
     * @param argPlainTextList フィールド[plainTextList]に設定する値。
     */
    public void setPlainTextList(final List<String> argPlainTextList) {
        fPlainTextList = argPlainTextList;
    }

    /**
     * フィールド [plainTextList] の値を取得します。
     *
     * フィールドの説明: [クラスのフィールド中に自由に記述できるテキストです。フィールド領域の先頭に、一度だけ記述できます。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[plainTextList]から取得した値。
     */
    public List<String> getPlainTextList() {
        return fPlainTextList;
    }

    /**
     * フィールド [defineAnnotation] の値を設定します。
     *
     * フィールドの説明: [独自アノテーションを定義するためのインタフェースの場合はtrueを設定してください。]。
     *
     * @param argDefineAnnotation フィールド[defineAnnotation]に設定する値。
     */
    public void setDefineAnnotation(final Boolean argDefineAnnotation) {
        fDefineAnnotation = argDefineAnnotation;
    }

    /**
     * フィールド [defineAnnotation] の値を取得します。
     *
     * フィールドの説明: [独自アノテーションを定義するためのインタフェースの場合はtrueを設定してください。]。
     * デフォルト: [false]。
     *
     * @return フィールド[defineAnnotation]から取得した値。
     */
    public Boolean getDefineAnnotation() {
        return fDefineAnnotation;
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
        buf.append("blanco.cg.valueobject.BlancoCgInterface[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",generics=" + fGenerics);
        buf.append(",extendClassList=" + fExtendClassList);
        buf.append(",access=" + fAccess);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",fieldList=" + fFieldList);
        buf.append(",methodList=" + fMethodList);
        buf.append(",langDoc=" + fLangDoc);
        buf.append(",plainTextList=" + fPlainTextList);
        buf.append(",defineAnnotation=" + fDefineAnnotation);
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
    public void copyTo(final BlancoCgInterface target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgInterface#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fGenerics
        // Type: java.lang.String
        target.fGenerics = this.fGenerics;
        // Name: fExtendClassList
        // Type: java.util.List
        // フィールド[fExtendClassList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgType]です。
        // Name: fAccess
        // Type: java.lang.String
        target.fAccess = this.fAccess;
        // Name: fAnnotationList
        // Type: java.util.List
        // フィールド[fAnnotationList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fFieldList
        // Type: java.util.List
        // フィールド[fFieldList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgField]です。
        // Name: fMethodList
        // Type: java.util.List
        // フィールド[fMethodList]はサポート外の型[java.util.Listblanco.cg.valueobject.BlancoCgMethod]です。
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // フィールド[fLangDoc]はサポート外の型[blanco.cg.valueobject.BlancoCgLangDoc]です。
        // Name: fPlainTextList
        // Type: java.util.List
        // フィールド[fPlainTextList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fDefineAnnotation
        // Type: java.lang.Boolean
        target.fDefineAnnotation = this.fDefineAnnotation;
    }
}

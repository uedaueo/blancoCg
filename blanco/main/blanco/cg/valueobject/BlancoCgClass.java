package blanco.cg.valueobject;

import java.util.List;
import java.util.Map;

/**
 * クラスを表現するためのバリューオブジェクト。
 *
 * クラスを自動生成したいときに利用します。
 * ※ポイント：クラス名の名前変形や文字列のエスケープ処理などは、blancoCgに与える前に実施されている必要があります。
 */
public class BlancoCgClass {
    /**
     * このクラスの名前です。パッケージ名を除くクラス名を指定する点に注意してください。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * このクラスの説明です。BlancoCgLangDoc生成時には、この値が利用されます。
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
     * 継承元インタフェースのリストです。
     *
     * フィールド: [implementInterfaceList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgType&gt;()]。
     */
    private List<BlancoCgType> fImplementInterfaceList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgType>();

    /**
     * このクラスのアクセスコントロールを指定します。
     *
     * public/protected/privateなどを指定します。
     * フィールド: [access]。
     * デフォルト: [&quot;public&quot;]。
     */
    private String fAccess = "public";

    /**
     * 抽象クラスかどうか。
     *
     * フィールド: [abstract]。
     * デフォルト: [false]。
     */
    private boolean fAbstract = false;

    /**
     * finalかどうか。
     *
     * フィールド: [final]。
     * デフォルト: [false]。
     */
    private boolean fFinal = false;

    /**
     * このクラスが実装しているインタフェイスの委譲元となる変数名を、インタフェイスの型名をキーとして指定します。
     *
     * フィールド: [delegateMap]。
     * デフォルト: [new java.util.HashMap&lt;&gt;()]。
     */
    private Map<String, String> fDelegateMap = new java.util.HashMap<>();

    /**
     * このクラスに付与されているアノテーションのリストです。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * このファイルに含まれる列挙体のリストです。
     *
     * フィールド: [enumList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgEnum&gt;()]。
     */
    private List<BlancoCgEnum> fEnumList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgEnum>();

    /**
     * このクラスに含まれるフィールドのリストです。
     *
     * フィールド: [fieldList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     */
    private List<BlancoCgField> fFieldList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgField>();

    /**
     * このクラスに含まれるメソッドのリストです。
     *
     * フィールド: [methodList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgMethod&gt;()]。
     */
    private List<BlancoCgMethod> fMethodList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgMethod>();

    /**
     * trueの場合、クラス宣言を省略します。
     *
     * フィールド: [noClassDeclare]。
     * デフォルト: [false]。
     */
    private boolean fNoClassDeclare = false;

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。
     *
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * クラス定義に含めるコンストラクタの引数マップです。&lt;引数名, 型&gt; となります。
     *
     * フィールド: [constructorArgList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     */
    private List<BlancoCgField> fConstructorArgList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgField>();

    /**
     * クラスのフィールド中に自由に記述できるテキストです。フィールド領域の先頭に、一度だけ記述できます。
     *
     * フィールド: [plainTextList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fPlainTextList = new java.util.ArrayList<java.lang.String>();

    /**
     * primary constructor に JsonCreator アノテーションを付与します。
     *
     * フィールド: [jsonCreator]。
     * デフォルト: [false]。
     */
    private boolean fJsonCreator = false;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [このクラスの名前です。パッケージ名を除くクラス名を指定する点に注意してください。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [このクラスの名前です。パッケージ名を除くクラス名を指定する点に注意してください。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [このクラスの説明です。BlancoCgLangDoc生成時には、この値が利用されます。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [このクラスの説明です。BlancoCgLangDoc生成時には、この値が利用されます。]。
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
     * フィールド [implementInterfaceList] の値を設定します。
     *
     * フィールドの説明: [継承元インタフェースのリストです。]。
     *
     * @param argImplementInterfaceList フィールド[implementInterfaceList]に設定する値。
     */
    public void setImplementInterfaceList(final List<BlancoCgType> argImplementInterfaceList) {
        fImplementInterfaceList = argImplementInterfaceList;
    }

    /**
     * フィールド [implementInterfaceList] の値を取得します。
     *
     * フィールドの説明: [継承元インタフェースのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgType&gt;()]。
     *
     * @return フィールド[implementInterfaceList]から取得した値。
     */
    public List<BlancoCgType> getImplementInterfaceList() {
        return fImplementInterfaceList;
    }

    /**
     * フィールド [access] の値を設定します。
     *
     * フィールドの説明: [このクラスのアクセスコントロールを指定します。]。
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
     * フィールドの説明: [このクラスのアクセスコントロールを指定します。]。
     * public/protected/privateなどを指定します。
     * デフォルト: [&quot;public&quot;]。
     *
     * @return フィールド[access]から取得した値。
     */
    public String getAccess() {
        return fAccess;
    }

    /**
     * フィールド [abstract] の値を設定します。
     *
     * フィールドの説明: [抽象クラスかどうか。]。
     *
     * @param argAbstract フィールド[abstract]に設定する値。
     */
    public void setAbstract(final boolean argAbstract) {
        fAbstract = argAbstract;
    }

    /**
     * フィールド [abstract] の値を取得します。
     *
     * フィールドの説明: [抽象クラスかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[abstract]から取得した値。
     */
    public boolean getAbstract() {
        return fAbstract;
    }

    /**
     * フィールド [final] の値を設定します。
     *
     * フィールドの説明: [finalかどうか。]。
     *
     * @param argFinal フィールド[final]に設定する値。
     */
    public void setFinal(final boolean argFinal) {
        fFinal = argFinal;
    }

    /**
     * フィールド [final] の値を取得します。
     *
     * フィールドの説明: [finalかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[final]から取得した値。
     */
    public boolean getFinal() {
        return fFinal;
    }

    /**
     * フィールド [delegateMap] の値を設定します。
     *
     * フィールドの説明: [このクラスが実装しているインタフェイスの委譲元となる変数名を、インタフェイスの型名をキーとして指定します。]。
     *
     * @param argDelegateMap フィールド[delegateMap]に設定する値。
     */
    public void setDelegateMap(final Map<String, String> argDelegateMap) {
        fDelegateMap = argDelegateMap;
    }

    /**
     * フィールド [delegateMap] の値を取得します。
     *
     * フィールドの説明: [このクラスが実装しているインタフェイスの委譲元となる変数名を、インタフェイスの型名をキーとして指定します。]。
     * デフォルト: [new java.util.HashMap&lt;&gt;()]。
     *
     * @return フィールド[delegateMap]から取得した値。
     */
    public Map<String, String> getDelegateMap() {
        return fDelegateMap;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [このクラスに付与されているアノテーションのリストです。]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [このクラスに付与されているアノテーションのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [enumList] の値を設定します。
     *
     * フィールドの説明: [このファイルに含まれる列挙体のリストです。]。
     *
     * @param argEnumList フィールド[enumList]に設定する値。
     */
    public void setEnumList(final List<BlancoCgEnum> argEnumList) {
        fEnumList = argEnumList;
    }

    /**
     * フィールド [enumList] の値を取得します。
     *
     * フィールドの説明: [このファイルに含まれる列挙体のリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgEnum&gt;()]。
     *
     * @return フィールド[enumList]から取得した値。
     */
    public List<BlancoCgEnum> getEnumList() {
        return fEnumList;
    }

    /**
     * フィールド [fieldList] の値を設定します。
     *
     * フィールドの説明: [このクラスに含まれるフィールドのリストです。]。
     *
     * @param argFieldList フィールド[fieldList]に設定する値。
     */
    public void setFieldList(final List<BlancoCgField> argFieldList) {
        fFieldList = argFieldList;
    }

    /**
     * フィールド [fieldList] の値を取得します。
     *
     * フィールドの説明: [このクラスに含まれるフィールドのリストです。]。
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
     * フィールドの説明: [このクラスに含まれるメソッドのリストです。]。
     *
     * @param argMethodList フィールド[methodList]に設定する値。
     */
    public void setMethodList(final List<BlancoCgMethod> argMethodList) {
        fMethodList = argMethodList;
    }

    /**
     * フィールド [methodList] の値を取得します。
     *
     * フィールドの説明: [このクラスに含まれるメソッドのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgMethod&gt;()]。
     *
     * @return フィールド[methodList]から取得した値。
     */
    public List<BlancoCgMethod> getMethodList() {
        return fMethodList;
    }

    /**
     * フィールド [noClassDeclare] の値を設定します。
     *
     * フィールドの説明: [trueの場合、クラス宣言を省略します。]。
     *
     * @param argNoClassDeclare フィールド[noClassDeclare]に設定する値。
     */
    public void setNoClassDeclare(final boolean argNoClassDeclare) {
        fNoClassDeclare = argNoClassDeclare;
    }

    /**
     * フィールド [noClassDeclare] の値を取得します。
     *
     * フィールドの説明: [trueの場合、クラス宣言を省略します。]。
     * デフォルト: [false]。
     *
     * @return フィールド[noClassDeclare]から取得した値。
     */
    public boolean getNoClassDeclare() {
        return fNoClassDeclare;
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
     * フィールド [constructorArgList] の値を設定します。
     *
     * フィールドの説明: [クラス定義に含めるコンストラクタの引数マップです。<引数名, 型> となります。]。
     *
     * @param argConstructorArgList フィールド[constructorArgList]に設定する値。
     */
    public void setConstructorArgList(final List<BlancoCgField> argConstructorArgList) {
        fConstructorArgList = argConstructorArgList;
    }

    /**
     * フィールド [constructorArgList] の値を取得します。
     *
     * フィールドの説明: [クラス定義に含めるコンストラクタの引数マップです。<引数名, 型> となります。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     *
     * @return フィールド[constructorArgList]から取得した値。
     */
    public List<BlancoCgField> getConstructorArgList() {
        return fConstructorArgList;
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
     * フィールド [jsonCreator] の値を設定します。
     *
     * フィールドの説明: [primary constructor に JsonCreator アノテーションを付与します。]。
     *
     * @param argJsonCreator フィールド[jsonCreator]に設定する値。
     */
    public void setJsonCreator(final boolean argJsonCreator) {
        fJsonCreator = argJsonCreator;
    }

    /**
     * フィールド [jsonCreator] の値を取得します。
     *
     * フィールドの説明: [primary constructor に JsonCreator アノテーションを付与します。]。
     * デフォルト: [false]。
     *
     * @return フィールド[jsonCreator]から取得した値。
     */
    public boolean getJsonCreator() {
        return fJsonCreator;
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
        buf.append("blanco.cg.valueobject.BlancoCgClass[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",generics=" + fGenerics);
        buf.append(",extendClassList=" + fExtendClassList);
        buf.append(",implementInterfaceList=" + fImplementInterfaceList);
        buf.append(",access=" + fAccess);
        buf.append(",abstract=" + fAbstract);
        buf.append(",final=" + fFinal);
        buf.append(",delegateMap=" + fDelegateMap);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",enumList=" + fEnumList);
        buf.append(",fieldList=" + fFieldList);
        buf.append(",methodList=" + fMethodList);
        buf.append(",noClassDeclare=" + fNoClassDeclare);
        buf.append(",langDoc=" + fLangDoc);
        buf.append(",constructorArgList=" + fConstructorArgList);
        buf.append(",plainTextList=" + fPlainTextList);
        buf.append(",jsonCreator=" + fJsonCreator);
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
    public void copyTo(final BlancoCgClass target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgClass#copyTo(target): argument 'target' is null");
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
        // Field[fExtendClassList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgType].
        // Name: fImplementInterfaceList
        // Type: java.util.List
        // Field[fImplementInterfaceList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgType].
        // Name: fAccess
        // Type: java.lang.String
        target.fAccess = this.fAccess;
        // Name: fAbstract
        // Type: boolean
        target.fAbstract = this.fAbstract;
        // Name: fFinal
        // Type: boolean
        target.fFinal = this.fFinal;
        // Name: fDelegateMap
        // Type: java.util.Map
        // Field[fDelegateMap] is an unsupported type[java.util.Mapjava.lang.String, java.lang.String].
        // Name: fAnnotationList
        // Type: java.util.List
        // Field[fAnnotationList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fEnumList
        // Type: java.util.List
        // Field[fEnumList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgEnum].
        // Name: fFieldList
        // Type: java.util.List
        // Field[fFieldList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgField].
        // Name: fMethodList
        // Type: java.util.List
        // Field[fMethodList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgMethod].
        // Name: fNoClassDeclare
        // Type: boolean
        target.fNoClassDeclare = this.fNoClassDeclare;
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // Field[fLangDoc] is an unsupported type[blanco.cg.valueobject.BlancoCgLangDoc].
        // Name: fConstructorArgList
        // Type: java.util.List
        // Field[fConstructorArgList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgField].
        // Name: fPlainTextList
        // Type: java.util.List
        // Field[fPlainTextList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fJsonCreator
        // Type: boolean
        target.fJsonCreator = this.fJsonCreator;
    }
}

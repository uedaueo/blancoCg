package blanco.cg.valueobject;

import java.util.List;

/**
 * ソースファイルを表現するためのバリューオブジェクト。
 */
public class BlancoCgSourceFile {
    /**
     * このファイルのファイル名です。
     *
     * なお、この値は明示的に指定しなくとも、多くの場合はクラス名・インタフェース名から名称が導出されるようになっています。
     * フィールド: [name]。
     */
    private String fName;

    /**
     * このファイルの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * このファイルが所属するパッケージです。
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * このファイルが所属する名前空間です。名前空間は本来的にはファイルに所属するものではありませんが、便宜上、ファイル単位での定義とします。
     *
     * フィールド: [namespace]。
     */
    private String fNamespace;

    /**
     * このファイルの文字エンコーディングです。
     *
     * フィールド: [encoding]。
     */
    private String fEncoding;

    /**
     * タブをwhite spaceいくつで置き換えるか、という値です。
     *
     * フィールド: [tabs]。
     * デフォルト: [4]。
     */
    private int fTabs = 4;

    /**
     * このファイルが参照する他のパッケージのリストです。java.lang.Stringのリスト。
     *
     * フィールド: [importList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fImportList = new java.util.ArrayList<java.lang.String>();

    /**
     * このファイルの先頭に書かれるコードのリストです。TypeScriptのimportなどに使用します。
     *
     * フィールド: [headerList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * このファイルに含まれる列挙体のリストです。
     *
     * フィールド: [enumList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgEnum&gt;()]。
     */
    private List<BlancoCgEnum> fEnumList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgEnum>();

    /**
     * このファイルに含まれるインタフェースのリストです。
     *
     * フィールド: [interfaceList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgInterface&gt;()]。
     */
    private List<BlancoCgInterface> fInterfaceList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgInterface>();

    /**
     * このファイルに含まれるクラスのリストです。
     *
     * フィールド: [classList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgClass&gt;()]。
     */
    private List<BlancoCgClass> fClassList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgClass>();

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。
     *
     * なお、このバリューオブジェクトはソースファイルの言語ドキュメントに該当するため、ここでセットした値が実際のソースコードに反映される場合の影響範囲は限定されてしまう場合があります。
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * import文を生成するかどうかのフラグです
     *
     * フィールド: [isImport]。
     * デフォルト: [true]。
     */
    private boolean fIsImport = true;

    /**
     * nullableな型に対して | undefined | null を追加付与するかどうかのフラグです
     *
     * フィールド: [isStrictNullable]。
     * デフォルト: [false]。
     */
    private boolean fIsStrictNullable = false;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [このファイルのファイル名です。]。
     * なお、この値は明示的に指定しなくとも、多くの場合はクラス名・インタフェース名から名称が導出されるようになっています。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [このファイルのファイル名です。]。
     * なお、この値は明示的に指定しなくとも、多くの場合はクラス名・インタフェース名から名称が導出されるようになっています。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [このファイルの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [このファイルの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [package] の値を設定します。
     *
     * フィールドの説明: [このファイルが所属するパッケージです。]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [このファイルが所属するパッケージです。]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [namespace] の値を設定します。
     *
     * フィールドの説明: [このファイルが所属する名前空間です。名前空間は本来的にはファイルに所属するものではありませんが、便宜上、ファイル単位での定義とします。]。
     *
     * @param argNamespace フィールド[namespace]に設定する値。
     */
    public void setNamespace(final String argNamespace) {
        fNamespace = argNamespace;
    }

    /**
     * フィールド [namespace] の値を取得します。
     *
     * フィールドの説明: [このファイルが所属する名前空間です。名前空間は本来的にはファイルに所属するものではありませんが、便宜上、ファイル単位での定義とします。]。
     *
     * @return フィールド[namespace]から取得した値。
     */
    public String getNamespace() {
        return fNamespace;
    }

    /**
     * フィールド [encoding] の値を設定します。
     *
     * フィールドの説明: [このファイルの文字エンコーディングです。]。
     *
     * @param argEncoding フィールド[encoding]に設定する値。
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * フィールド [encoding] の値を取得します。
     *
     * フィールドの説明: [このファイルの文字エンコーディングです。]。
     *
     * @return フィールド[encoding]から取得した値。
     */
    public String getEncoding() {
        return fEncoding;
    }

    /**
     * フィールド [tabs] の値を設定します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     *
     * @param argTabs フィールド[tabs]に設定する値。
     */
    public void setTabs(final int argTabs) {
        fTabs = argTabs;
    }

    /**
     * フィールド [tabs] の値を取得します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     * デフォルト: [4]。
     *
     * @return フィールド[tabs]から取得した値。
     */
    public int getTabs() {
        return fTabs;
    }

    /**
     * フィールド [importList] の値を設定します。
     *
     * フィールドの説明: [このファイルが参照する他のパッケージのリストです。java.lang.Stringのリスト。]。
     *
     * @param argImportList フィールド[importList]に設定する値。
     */
    public void setImportList(final List<String> argImportList) {
        fImportList = argImportList;
    }

    /**
     * フィールド [importList] の値を取得します。
     *
     * フィールドの説明: [このファイルが参照する他のパッケージのリストです。java.lang.Stringのリスト。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[importList]から取得した値。
     */
    public List<String> getImportList() {
        return fImportList;
    }

    /**
     * フィールド [headerList] の値を設定します。
     *
     * フィールドの説明: [このファイルの先頭に書かれるコードのリストです。TypeScriptのimportなどに使用します。]。
     *
     * @param argHeaderList フィールド[headerList]に設定する値。
     */
    public void setHeaderList(final List<String> argHeaderList) {
        fHeaderList = argHeaderList;
    }

    /**
     * フィールド [headerList] の値を取得します。
     *
     * フィールドの説明: [このファイルの先頭に書かれるコードのリストです。TypeScriptのimportなどに使用します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[headerList]から取得した値。
     */
    public List<String> getHeaderList() {
        return fHeaderList;
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
     * フィールド [interfaceList] の値を設定します。
     *
     * フィールドの説明: [このファイルに含まれるインタフェースのリストです。]。
     *
     * @param argInterfaceList フィールド[interfaceList]に設定する値。
     */
    public void setInterfaceList(final List<BlancoCgInterface> argInterfaceList) {
        fInterfaceList = argInterfaceList;
    }

    /**
     * フィールド [interfaceList] の値を取得します。
     *
     * フィールドの説明: [このファイルに含まれるインタフェースのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgInterface&gt;()]。
     *
     * @return フィールド[interfaceList]から取得した値。
     */
    public List<BlancoCgInterface> getInterfaceList() {
        return fInterfaceList;
    }

    /**
     * フィールド [classList] の値を設定します。
     *
     * フィールドの説明: [このファイルに含まれるクラスのリストです。]。
     *
     * @param argClassList フィールド[classList]に設定する値。
     */
    public void setClassList(final List<BlancoCgClass> argClassList) {
        fClassList = argClassList;
    }

    /**
     * フィールド [classList] の値を取得します。
     *
     * フィールドの説明: [このファイルに含まれるクラスのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgClass&gt;()]。
     *
     * @return フィールド[classList]から取得した値。
     */
    public List<BlancoCgClass> getClassList() {
        return fClassList;
    }

    /**
     * フィールド [langDoc] の値を設定します。
     *
     * フィールドの説明: [言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。]。
     * なお、このバリューオブジェクトはソースファイルの言語ドキュメントに該当するため、ここでセットした値が実際のソースコードに反映される場合の影響範囲は限定されてしまう場合があります。
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
     * なお、このバリューオブジェクトはソースファイルの言語ドキュメントに該当するため、ここでセットした値が実際のソースコードに反映される場合の影響範囲は限定されてしまう場合があります。
     *
     * @return フィールド[langDoc]から取得した値。
     */
    public BlancoCgLangDoc getLangDoc() {
        return fLangDoc;
    }

    /**
     * フィールド [isImport] の値を設定します。
     *
     * フィールドの説明: [import文を生成するかどうかのフラグです]。
     *
     * @param argIsImport フィールド[isImport]に設定する値。
     */
    public void setIsImport(final boolean argIsImport) {
        fIsImport = argIsImport;
    }

    /**
     * フィールド [isImport] の値を取得します。
     *
     * フィールドの説明: [import文を生成するかどうかのフラグです]。
     * デフォルト: [true]。
     *
     * @return フィールド[isImport]から取得した値。
     */
    public boolean getIsImport() {
        return fIsImport;
    }

    /**
     * フィールド [isStrictNullable] の値を設定します。
     *
     * フィールドの説明: [nullableな型に対して | undefined | null を追加付与するかどうかのフラグです]。
     *
     * @param argIsStrictNullable フィールド[isStrictNullable]に設定する値。
     */
    public void setIsStrictNullable(final boolean argIsStrictNullable) {
        fIsStrictNullable = argIsStrictNullable;
    }

    /**
     * フィールド [isStrictNullable] の値を取得します。
     *
     * フィールドの説明: [nullableな型に対して | undefined | null を追加付与するかどうかのフラグです]。
     * デフォルト: [false]。
     *
     * @return フィールド[isStrictNullable]から取得した値。
     */
    public boolean getIsStrictNullable() {
        return fIsStrictNullable;
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
        buf.append("blanco.cg.valueobject.BlancoCgSourceFile[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",package=" + fPackage);
        buf.append(",namespace=" + fNamespace);
        buf.append(",encoding=" + fEncoding);
        buf.append(",tabs=" + fTabs);
        buf.append(",importList=" + fImportList);
        buf.append(",headerList=" + fHeaderList);
        buf.append(",enumList=" + fEnumList);
        buf.append(",interfaceList=" + fInterfaceList);
        buf.append(",classList=" + fClassList);
        buf.append(",langDoc=" + fLangDoc);
        buf.append(",isImport=" + fIsImport);
        buf.append(",isStrictNullable=" + fIsStrictNullable);
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
    public void copyTo(final BlancoCgSourceFile target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgSourceFile#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fNamespace
        // Type: java.lang.String
        target.fNamespace = this.fNamespace;
        // Name: fEncoding
        // Type: java.lang.String
        target.fEncoding = this.fEncoding;
        // Name: fTabs
        // Type: int
        target.fTabs = this.fTabs;
        // Name: fImportList
        // Type: java.util.List
        // Field[fImportList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fHeaderList
        // Type: java.util.List
        // Field[fHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fEnumList
        // Type: java.util.List
        // Field[fEnumList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgEnum].
        // Name: fInterfaceList
        // Type: java.util.List
        // Field[fInterfaceList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgInterface].
        // Name: fClassList
        // Type: java.util.List
        // Field[fClassList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgClass].
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // Field[fLangDoc] is an unsupported type[blanco.cg.valueobject.BlancoCgLangDoc].
        // Name: fIsImport
        // Type: boolean
        target.fIsImport = this.fIsImport;
        // Name: fIsStrictNullable
        // Type: boolean
        target.fIsStrictNullable = this.fIsStrictNullable;
    }
}

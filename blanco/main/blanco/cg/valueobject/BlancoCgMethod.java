package blanco.cg.valueobject;

import java.util.List;

/**
 * メソッドを表現するためのバリューオブジェクト。
 *
 * ※ポイント：メソッド名の名前変形は、blancoCgに与える前に実施されている必要があります。
 */
public class BlancoCgMethod {
    /**
     * このメソッドの名前です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * このメソッドの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * このメソッドのアクセスコントロールを指定します。
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
     * staticかどうか。
     *
     * フィールド: [static]。
     * デフォルト: [false]。
     */
    private boolean fStatic = false;

    /**
     * オーバライドしているかどうか。
     *
     * フィールド: [override]。
     * デフォルト: [false]。
     */
    private boolean fOverride = false;

    /**
     * finalかどうか。
     *
     * フィールド: [final]。
     * デフォルト: [false]。
     */
    private boolean fFinal = false;

    /**
     * コンストラクタかどうか。
     *
     * フィールド: [constructor]。
     * デフォルト: [false]。
     */
    private boolean fConstructor = false;

    /**
     * static initializer かどうか。現時点(2009-05-18)では Java 言語でのみ有効です。
     *
     * フィールド: [staticInitializer]。
     * デフォルト: [false]。
     */
    private boolean fStaticInitializer = false;

    /**
     * このメソッドのパラメータのリストです。
     *
     * フィールド: [parameterList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgParameter&gt;()]。
     */
    private List<blanco.cg.valueobject.BlancoCgParameter> fParameterList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgParameter>();

    /**
     * このメソッドの戻り値です。
     *
     * フィールド: [return]。
     */
    private BlancoCgReturn fReturn;

    /**
     * このメソッドの戻り値にnullを許容するかどうか。（kotlin, typescript用）
     *
     * フィールド: [notnull]。
     * デフォルト: [false]。
     */
    private boolean fNotnull = false;

    /**
     * このメソッドが発生しうる例外の一覧です。
     *
     * フィールド: [throwList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgException&gt;()]。
     */
    private List<blanco.cg.valueobject.BlancoCgException> fThrowList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgException>();

    /**
     * このメソッドに付与されているアノテーションのリストです。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<java.lang.String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * このメソッド内で使用するローカル変数のリストです。Delphi言語など、インラインでローカル変数定義ができない言語でのみ使用します。
     *
     * フィールド: [localVariableList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgLocalVariable&gt;()]。
     */
    private List<blanco.cg.valueobject.BlancoCgLocalVariable> fLocalVariableList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgLocalVariable>();

    /**
     * このメソッドに含まれる行のリストです。
     *
     * フィールド: [lineList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<java.lang.String> fLineList = new java.util.ArrayList<java.lang.String>();

    /**
     * コンストラクタの場合には、Java言語だと super(引数)、C#.NETだと base(引数)の文字列全てを指定します。一般メソッドの場合には、Java言語だと super(引数)の文字列全てを指定します。セミコロンは含みません。
     *
     * フィールド: [superclassInvocation]。
     */
    private String fSuperclassInvocation;

    /**
     * 言語ドキュメントを蓄えます。デフォルト以上の表現を追加する場合には、インスタンスを生成して値をセットしてから自動生成します。
     *
     * フィールド: [langDoc]。
     */
    private BlancoCgLangDoc fLangDoc;

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [このメソッドの名前です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [このメソッドの名前です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [このメソッドの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [このメソッドの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [access] の値を設定します。
     *
     * フィールドの説明: [このメソッドのアクセスコントロールを指定します。]。
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
     * フィールドの説明: [このメソッドのアクセスコントロールを指定します。]。
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
     * フィールド [static] の値を設定します。
     *
     * フィールドの説明: [staticかどうか。]。
     *
     * @param argStatic フィールド[static]に設定する値。
     */
    public void setStatic(final boolean argStatic) {
        fStatic = argStatic;
    }

    /**
     * フィールド [static] の値を取得します。
     *
     * フィールドの説明: [staticかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[static]から取得した値。
     */
    public boolean getStatic() {
        return fStatic;
    }

    /**
     * フィールド [override] の値を設定します。
     *
     * フィールドの説明: [オーバライドしているかどうか。]。
     *
     * @param argOverride フィールド[override]に設定する値。
     */
    public void setOverride(final boolean argOverride) {
        fOverride = argOverride;
    }

    /**
     * フィールド [override] の値を取得します。
     *
     * フィールドの説明: [オーバライドしているかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[override]から取得した値。
     */
    public boolean getOverride() {
        return fOverride;
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
     * フィールド [constructor] の値を設定します。
     *
     * フィールドの説明: [コンストラクタかどうか。]。
     *
     * @param argConstructor フィールド[constructor]に設定する値。
     */
    public void setConstructor(final boolean argConstructor) {
        fConstructor = argConstructor;
    }

    /**
     * フィールド [constructor] の値を取得します。
     *
     * フィールドの説明: [コンストラクタかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[constructor]から取得した値。
     */
    public boolean getConstructor() {
        return fConstructor;
    }

    /**
     * フィールド [staticInitializer] の値を設定します。
     *
     * フィールドの説明: [static initializer かどうか。現時点(2009-05-18)では Java 言語でのみ有効です。]。
     *
     * @param argStaticInitializer フィールド[staticInitializer]に設定する値。
     */
    public void setStaticInitializer(final boolean argStaticInitializer) {
        fStaticInitializer = argStaticInitializer;
    }

    /**
     * フィールド [staticInitializer] の値を取得します。
     *
     * フィールドの説明: [static initializer かどうか。現時点(2009-05-18)では Java 言語でのみ有効です。]。
     * デフォルト: [false]。
     *
     * @return フィールド[staticInitializer]から取得した値。
     */
    public boolean getStaticInitializer() {
        return fStaticInitializer;
    }

    /**
     * フィールド [parameterList] の値を設定します。
     *
     * フィールドの説明: [このメソッドのパラメータのリストです。]。
     *
     * @param argParameterList フィールド[parameterList]に設定する値。
     */
    public void setParameterList(final List<blanco.cg.valueobject.BlancoCgParameter> argParameterList) {
        fParameterList = argParameterList;
    }

    /**
     * フィールド [parameterList] の値を取得します。
     *
     * フィールドの説明: [このメソッドのパラメータのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgParameter&gt;()]。
     *
     * @return フィールド[parameterList]から取得した値。
     */
    public List<blanco.cg.valueobject.BlancoCgParameter> getParameterList() {
        return fParameterList;
    }

    /**
     * フィールド [return] の値を設定します。
     *
     * フィールドの説明: [このメソッドの戻り値です。]。
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
     *
     * @return フィールド[return]から取得した値。
     */
    public BlancoCgReturn getReturn() {
        return fReturn;
    }

    /**
     * フィールド [notnull] の値を設定します。
     *
     * フィールドの説明: [このメソッドの戻り値にnullを許容するかどうか。（kotlin, typescript用）]。
     *
     * @param argNotnull フィールド[notnull]に設定する値。
     */
    public void setNotnull(final boolean argNotnull) {
        fNotnull = argNotnull;
    }

    /**
     * フィールド [notnull] の値を取得します。
     *
     * フィールドの説明: [このメソッドの戻り値にnullを許容するかどうか。（kotlin, typescript用）]。
     * デフォルト: [false]。
     *
     * @return フィールド[notnull]から取得した値。
     */
    public boolean getNotnull() {
        return fNotnull;
    }

    /**
     * フィールド [throwList] の値を設定します。
     *
     * フィールドの説明: [このメソッドが発生しうる例外の一覧です。]。
     *
     * @param argThrowList フィールド[throwList]に設定する値。
     */
    public void setThrowList(final List<blanco.cg.valueobject.BlancoCgException> argThrowList) {
        fThrowList = argThrowList;
    }

    /**
     * フィールド [throwList] の値を取得します。
     *
     * フィールドの説明: [このメソッドが発生しうる例外の一覧です。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgException&gt;()]。
     *
     * @return フィールド[throwList]から取得した値。
     */
    public List<blanco.cg.valueobject.BlancoCgException> getThrowList() {
        return fThrowList;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [このメソッドに付与されているアノテーションのリストです。]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<java.lang.String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [このメソッドに付与されているアノテーションのリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<java.lang.String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [localVariableList] の値を設定します。
     *
     * フィールドの説明: [このメソッド内で使用するローカル変数のリストです。Delphi言語など、インラインでローカル変数定義ができない言語でのみ使用します。]。
     *
     * @param argLocalVariableList フィールド[localVariableList]に設定する値。
     */
    public void setLocalVariableList(final List<blanco.cg.valueobject.BlancoCgLocalVariable> argLocalVariableList) {
        fLocalVariableList = argLocalVariableList;
    }

    /**
     * フィールド [localVariableList] の値を取得します。
     *
     * フィールドの説明: [このメソッド内で使用するローカル変数のリストです。Delphi言語など、インラインでローカル変数定義ができない言語でのみ使用します。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgLocalVariable&gt;()]。
     *
     * @return フィールド[localVariableList]から取得した値。
     */
    public List<blanco.cg.valueobject.BlancoCgLocalVariable> getLocalVariableList() {
        return fLocalVariableList;
    }

    /**
     * フィールド [lineList] の値を設定します。
     *
     * フィールドの説明: [このメソッドに含まれる行のリストです。]。
     *
     * @param argLineList フィールド[lineList]に設定する値。
     */
    public void setLineList(final List<java.lang.String> argLineList) {
        fLineList = argLineList;
    }

    /**
     * フィールド [lineList] の値を取得します。
     *
     * フィールドの説明: [このメソッドに含まれる行のリストです。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[lineList]から取得した値。
     */
    public List<java.lang.String> getLineList() {
        return fLineList;
    }

    /**
     * フィールド [superclassInvocation] の値を設定します。
     *
     * フィールドの説明: [コンストラクタの場合には、Java言語だと super(引数)、C#.NETだと base(引数)の文字列全てを指定します。一般メソッドの場合には、Java言語だと super(引数)の文字列全てを指定します。セミコロンは含みません。]。
     *
     * @param argSuperclassInvocation フィールド[superclassInvocation]に設定する値。
     */
    public void setSuperclassInvocation(final String argSuperclassInvocation) {
        fSuperclassInvocation = argSuperclassInvocation;
    }

    /**
     * フィールド [superclassInvocation] の値を取得します。
     *
     * フィールドの説明: [コンストラクタの場合には、Java言語だと super(引数)、C#.NETだと base(引数)の文字列全てを指定します。一般メソッドの場合には、Java言語だと super(引数)の文字列全てを指定します。セミコロンは含みません。]。
     *
     * @return フィールド[superclassInvocation]から取得した値。
     */
    public String getSuperclassInvocation() {
        return fSuperclassInvocation;
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
        buf.append("blanco.cg.valueobject.BlancoCgMethod[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",access=" + fAccess);
        buf.append(",abstract=" + fAbstract);
        buf.append(",static=" + fStatic);
        buf.append(",override=" + fOverride);
        buf.append(",final=" + fFinal);
        buf.append(",constructor=" + fConstructor);
        buf.append(",staticInitializer=" + fStaticInitializer);
        buf.append(",parameterList=" + fParameterList);
        buf.append(",return=" + fReturn);
        buf.append(",notnull=" + fNotnull);
        buf.append(",throwList=" + fThrowList);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",localVariableList=" + fLocalVariableList);
        buf.append(",lineList=" + fLineList);
        buf.append(",superclassInvocation=" + fSuperclassInvocation);
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
    public void copyTo(final BlancoCgMethod target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCgMethod#copyTo(target): argument 'target' is null");
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
        // Name: fAbstract
        // Type: boolean
        target.fAbstract = this.fAbstract;
        // Name: fStatic
        // Type: boolean
        target.fStatic = this.fStatic;
        // Name: fOverride
        // Type: boolean
        target.fOverride = this.fOverride;
        // Name: fFinal
        // Type: boolean
        target.fFinal = this.fFinal;
        // Name: fConstructor
        // Type: boolean
        target.fConstructor = this.fConstructor;
        // Name: fStaticInitializer
        // Type: boolean
        target.fStaticInitializer = this.fStaticInitializer;
        // Name: fParameterList
        // Type: java.util.List
        if (this.fParameterList != null) {
            final java.util.Iterator<blanco.cg.valueobject.BlancoCgParameter> iterator = this.fParameterList.iterator();
            for (; iterator.hasNext();) {
                blanco.cg.valueobject.BlancoCgParameter loopSource = iterator.next();
                blanco.cg.valueobject.BlancoCgParameter loopTarget = null;
                // フィールド[generics]はサポート外の型[blanco.cg.valueobject.BlancoCgParameter]です。
                target.fParameterList.add(loopTarget);
            }
        }
        // Name: fReturn
        // Type: blanco.cg.valueobject.BlancoCgReturn
        // フィールド[fReturn]はサポート外の型[blanco.cg.valueobject.BlancoCgReturn]です。
        // Name: fNotnull
        // Type: boolean
        target.fNotnull = this.fNotnull;
        // Name: fThrowList
        // Type: java.util.List
        if (this.fThrowList != null) {
            final java.util.Iterator<blanco.cg.valueobject.BlancoCgException> iterator = this.fThrowList.iterator();
            for (; iterator.hasNext();) {
                blanco.cg.valueobject.BlancoCgException loopSource = iterator.next();
                blanco.cg.valueobject.BlancoCgException loopTarget = null;
                // フィールド[generics]はサポート外の型[blanco.cg.valueobject.BlancoCgException]です。
                target.fThrowList.add(loopTarget);
            }
        }
        // Name: fAnnotationList
        // Type: java.util.List
        if (this.fAnnotationList != null) {
            final java.util.Iterator<java.lang.String> iterator = this.fAnnotationList.iterator();
            for (; iterator.hasNext();) {
                java.lang.String loopSource = iterator.next();
                java.lang.String loopTarget = null;
                loopTarget = loopSource;
                target.fAnnotationList.add(loopTarget);
            }
        }
        // Name: fLocalVariableList
        // Type: java.util.List
        if (this.fLocalVariableList != null) {
            final java.util.Iterator<blanco.cg.valueobject.BlancoCgLocalVariable> iterator = this.fLocalVariableList.iterator();
            for (; iterator.hasNext();) {
                blanco.cg.valueobject.BlancoCgLocalVariable loopSource = iterator.next();
                blanco.cg.valueobject.BlancoCgLocalVariable loopTarget = null;
                // フィールド[generics]はサポート外の型[blanco.cg.valueobject.BlancoCgLocalVariable]です。
                target.fLocalVariableList.add(loopTarget);
            }
        }
        // Name: fLineList
        // Type: java.util.List
        if (this.fLineList != null) {
            final java.util.Iterator<java.lang.String> iterator = this.fLineList.iterator();
            for (; iterator.hasNext();) {
                java.lang.String loopSource = iterator.next();
                java.lang.String loopTarget = null;
                loopTarget = loopSource;
                target.fLineList.add(loopTarget);
            }
        }
        // Name: fSuperclassInvocation
        // Type: java.lang.String
        target.fSuperclassInvocation = this.fSuperclassInvocation;
        // Name: fLangDoc
        // Type: blanco.cg.valueobject.BlancoCgLangDoc
        // フィールド[fLangDoc]はサポート外の型[blanco.cg.valueobject.BlancoCgLangDoc]です。
    }
}

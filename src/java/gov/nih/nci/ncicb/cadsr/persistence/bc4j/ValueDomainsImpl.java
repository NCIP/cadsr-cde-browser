package gov.nih.nci.ncicb.cadsr.persistence.bc4j;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Date;
import oracle.jbo.Key;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class ValueDomainsImpl extends EntityImpl 
{
  public static final int VDIDSEQ = 0;
  public static final int VERSION = 1;
  public static final int PREFERREDNAME = 2;
  public static final int CONTEIDSEQ = 3;
  public static final int PREFERREDDEFINITION = 4;
  public static final int DTLNAME = 5;
  public static final int BEGINDATE = 6;
  public static final int CDIDSEQ = 7;
  public static final int ENDDATE = 8;
  public static final int VDTYPEFLAG = 9;
  public static final int ASLNAME = 10;
  public static final int CHANGENOTE = 11;
  public static final int UOMLNAME = 12;
  public static final int LONGNAME = 13;
  public static final int FORMLNAME = 14;
  public static final int MAXLENGTHNUM = 15;
  public static final int MINLENGTHNUM = 16;
  public static final int DECIMALPLACE = 17;
  public static final int LATESTVERSIONIND = 18;
  public static final int DELETEDIND = 19;
  public static final int DATECREATED = 20;
  public static final int CREATEDBY = 21;
  public static final int DATEMODIFIED = 22;
  public static final int MODIFIEDBY = 23;
  public static final int CHARSETNAME = 24;
  public static final int HIGHVALUENUM = 25;
  public static final int LOWVALUENUM = 26;
  public static final int REPIDSEQ = 27;
  public static final int QUALIFIERNAME = 28;
  public static final int ORIGIN = 29;
  public static final int VDID = 30;
  public static final int CONTEXTS = 31;
  public static final int CONCEPTUALDOMAINS = 32;
  public static final int DATAELEMENTS = 33;
  public static final int VDPVS = 34;



  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public ValueDomainsImpl()
  {
  }

  /**
   * 
   *  Retrieves the definition object for this instance class.
   */
  public static synchronized EntityDefImpl getDefinitionObject()
  {
    if (mDefinitionObject == null)
    {
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.persistence.bc4j.ValueDomains");
    }
    return mDefinitionObject;
  }










  /**
   * 
   * Gets the attribute value for VdIdseq, using the alias name VdIdseq
   */
  public String getVdIdseq()
  {
    return (String)getAttributeInternal(VDIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for VdIdseq
   */
  public void setVdIdseq(String value)
  {
    setAttributeInternal(VDIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for Version, using the alias name Version
   */
  public Number getVersion()
  {
    return (Number)getAttributeInternal(VERSION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Version
   */
  public void setVersion(Number value)
  {
    setAttributeInternal(VERSION, value);
  }

  /**
   * 
   * Gets the attribute value for PreferredName, using the alias name PreferredName
   */
  public String getPreferredName()
  {
    return (String)getAttributeInternal(PREFERREDNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PreferredName
   */
  public void setPreferredName(String value)
  {
    setAttributeInternal(PREFERREDNAME, value);
  }

  /**
   * 
   * Gets the attribute value for ConteIdseq, using the alias name ConteIdseq
   */
  public String getConteIdseq()
  {
    return (String)getAttributeInternal(CONTEIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ConteIdseq
   */
  public void setConteIdseq(String value)
  {
    setAttributeInternal(CONTEIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for PreferredDefinition, using the alias name PreferredDefinition
   */
  public String getPreferredDefinition()
  {
    return (String)getAttributeInternal(PREFERREDDEFINITION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PreferredDefinition
   */
  public void setPreferredDefinition(String value)
  {
    setAttributeInternal(PREFERREDDEFINITION, value);
  }

  /**
   * 
   * Gets the attribute value for DtlName, using the alias name DtlName
   */
  public String getDtlName()
  {
    return (String)getAttributeInternal(DTLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DtlName
   */
  public void setDtlName(String value)
  {
    setAttributeInternal(DTLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for BeginDate, using the alias name BeginDate
   */
  public Date getBeginDate()
  {
    return (Date)getAttributeInternal(BEGINDATE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for BeginDate
   */
  public void setBeginDate(Date value)
  {
    setAttributeInternal(BEGINDATE, value);
  }

  /**
   * 
   * Gets the attribute value for CdIdseq, using the alias name CdIdseq
   */
  public String getCdIdseq()
  {
    return (String)getAttributeInternal(CDIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CdIdseq
   */
  public void setCdIdseq(String value)
  {
    setAttributeInternal(CDIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for EndDate, using the alias name EndDate
   */
  public Date getEndDate()
  {
    return (Date)getAttributeInternal(ENDDATE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for EndDate
   */
  public void setEndDate(Date value)
  {
    setAttributeInternal(ENDDATE, value);
  }

  /**
   * 
   * Gets the attribute value for VdTypeFlag, using the alias name VdTypeFlag
   */
  public String getVdTypeFlag()
  {
    return (String)getAttributeInternal(VDTYPEFLAG);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for VdTypeFlag
   */
  public void setVdTypeFlag(String value)
  {
    setAttributeInternal(VDTYPEFLAG, value);
  }

  /**
   * 
   * Gets the attribute value for AslName, using the alias name AslName
   */
  public String getAslName()
  {
    return (String)getAttributeInternal(ASLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for AslName
   */
  public void setAslName(String value)
  {
    setAttributeInternal(ASLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for ChangeNote, using the alias name ChangeNote
   */
  public String getChangeNote()
  {
    return (String)getAttributeInternal(CHANGENOTE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ChangeNote
   */
  public void setChangeNote(String value)
  {
    setAttributeInternal(CHANGENOTE, value);
  }

  /**
   * 
   * Gets the attribute value for UomlName, using the alias name UomlName
   */
  public String getUomlName()
  {
    return (String)getAttributeInternal(UOMLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for UomlName
   */
  public void setUomlName(String value)
  {
    setAttributeInternal(UOMLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for LongName, using the alias name LongName
   */
  public String getLongName()
  {
    return (String)getAttributeInternal(LONGNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LongName
   */
  public void setLongName(String value)
  {
    setAttributeInternal(LONGNAME, value);
  }

  /**
   * 
   * Gets the attribute value for FormlName, using the alias name FormlName
   */
  public String getFormlName()
  {
    return (String)getAttributeInternal(FORMLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for FormlName
   */
  public void setFormlName(String value)
  {
    setAttributeInternal(FORMLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for MaxLengthNum, using the alias name MaxLengthNum
   */
  public Number getMaxLengthNum()
  {
    return (Number)getAttributeInternal(MAXLENGTHNUM);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for MaxLengthNum
   */
  public void setMaxLengthNum(Number value)
  {
    setAttributeInternal(MAXLENGTHNUM, value);
  }

  /**
   * 
   * Gets the attribute value for MinLengthNum, using the alias name MinLengthNum
   */
  public Number getMinLengthNum()
  {
    return (Number)getAttributeInternal(MINLENGTHNUM);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for MinLengthNum
   */
  public void setMinLengthNum(Number value)
  {
    setAttributeInternal(MINLENGTHNUM, value);
  }

  /**
   * 
   * Gets the attribute value for DecimalPlace, using the alias name DecimalPlace
   */
  public Number getDecimalPlace()
  {
    return (Number)getAttributeInternal(DECIMALPLACE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DecimalPlace
   */
  public void setDecimalPlace(Number value)
  {
    setAttributeInternal(DECIMALPLACE, value);
  }

  /**
   * 
   * Gets the attribute value for LatestVersionInd, using the alias name LatestVersionInd
   */
  public String getLatestVersionInd()
  {
    return (String)getAttributeInternal(LATESTVERSIONIND);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LatestVersionInd
   */
  public void setLatestVersionInd(String value)
  {
    setAttributeInternal(LATESTVERSIONIND, value);
  }

  /**
   * 
   * Gets the attribute value for DeletedInd, using the alias name DeletedInd
   */
  public String getDeletedInd()
  {
    return (String)getAttributeInternal(DELETEDIND);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DeletedInd
   */
  public void setDeletedInd(String value)
  {
    setAttributeInternal(DELETEDIND, value);
  }

  /**
   * 
   * Gets the attribute value for DateCreated, using the alias name DateCreated
   */
  public Date getDateCreated()
  {
    return (Date)getAttributeInternal(DATECREATED);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DateCreated
   */
  public void setDateCreated(Date value)
  {
    setAttributeInternal(DATECREATED, value);
  }

  /**
   * 
   * Gets the attribute value for CreatedBy, using the alias name CreatedBy
   */
  public String getCreatedBy()
  {
    return (String)getAttributeInternal(CREATEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CreatedBy
   */
  public void setCreatedBy(String value)
  {
    setAttributeInternal(CREATEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for DateModified, using the alias name DateModified
   */
  public Date getDateModified()
  {
    return (Date)getAttributeInternal(DATEMODIFIED);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DateModified
   */
  public void setDateModified(Date value)
  {
    setAttributeInternal(DATEMODIFIED, value);
  }

  /**
   * 
   * Gets the attribute value for ModifiedBy, using the alias name ModifiedBy
   */
  public String getModifiedBy()
  {
    return (String)getAttributeInternal(MODIFIEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ModifiedBy
   */
  public void setModifiedBy(String value)
  {
    setAttributeInternal(MODIFIEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for CharSetName, using the alias name CharSetName
   */
  public String getCharSetName()
  {
    return (String)getAttributeInternal(CHARSETNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CharSetName
   */
  public void setCharSetName(String value)
  {
    setAttributeInternal(CHARSETNAME, value);
  }

  /**
   * 
   * Gets the attribute value for HighValueNum, using the alias name HighValueNum
   */
  public String getHighValueNum()
  {
    return (String)getAttributeInternal(HIGHVALUENUM);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for HighValueNum
   */
  public void setHighValueNum(String value)
  {
    setAttributeInternal(HIGHVALUENUM, value);
  }

  /**
   * 
   * Gets the attribute value for LowValueNum, using the alias name LowValueNum
   */
  public String getLowValueNum()
  {
    return (String)getAttributeInternal(LOWVALUENUM);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LowValueNum
   */
  public void setLowValueNum(String value)
  {
    setAttributeInternal(LOWVALUENUM, value);
  }
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case VDIDSEQ:
        return getVdIdseq();
      case VERSION:
        return getVersion();
      case PREFERREDNAME:
        return getPreferredName();
      case CONTEIDSEQ:
        return getConteIdseq();
      case PREFERREDDEFINITION:
        return getPreferredDefinition();
      case DTLNAME:
        return getDtlName();
      case BEGINDATE:
        return getBeginDate();
      case CDIDSEQ:
        return getCdIdseq();
      case ENDDATE:
        return getEndDate();
      case VDTYPEFLAG:
        return getVdTypeFlag();
      case ASLNAME:
        return getAslName();
      case CHANGENOTE:
        return getChangeNote();
      case UOMLNAME:
        return getUomlName();
      case LONGNAME:
        return getLongName();
      case FORMLNAME:
        return getFormlName();
      case MAXLENGTHNUM:
        return getMaxLengthNum();
      case MINLENGTHNUM:
        return getMinLengthNum();
      case DECIMALPLACE:
        return getDecimalPlace();
      case LATESTVERSIONIND:
        return getLatestVersionInd();
      case DELETEDIND:
        return getDeletedInd();
      case DATECREATED:
        return getDateCreated();
      case CREATEDBY:
        return getCreatedBy();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      case CHARSETNAME:
        return getCharSetName();
      case HIGHVALUENUM:
        return getHighValueNum();
      case LOWVALUENUM:
        return getLowValueNum();
      case REPIDSEQ:
        return getRepIdseq();
      case QUALIFIERNAME:
        return getQualifierName();
      case ORIGIN:
        return getOrigin();
      case VDID:
        return getVdId();
      case DATAELEMENTS:
        return getDataElements();
      case VDPVS:
        return getVdPvs();
      case CONTEXTS:
        return getContexts();
      case CONCEPTUALDOMAINS:
        return getConceptualDomains();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case VDIDSEQ:
        setVdIdseq((String)value);
        return;
      case VERSION:
        setVersion((Number)value);
        return;
      case PREFERREDNAME:
        setPreferredName((String)value);
        return;
      case CONTEIDSEQ:
        setConteIdseq((String)value);
        return;
      case PREFERREDDEFINITION:
        setPreferredDefinition((String)value);
        return;
      case DTLNAME:
        setDtlName((String)value);
        return;
      case BEGINDATE:
        setBeginDate((Date)value);
        return;
      case CDIDSEQ:
        setCdIdseq((String)value);
        return;
      case ENDDATE:
        setEndDate((Date)value);
        return;
      case VDTYPEFLAG:
        setVdTypeFlag((String)value);
        return;
      case ASLNAME:
        setAslName((String)value);
        return;
      case CHANGENOTE:
        setChangeNote((String)value);
        return;
      case UOMLNAME:
        setUomlName((String)value);
        return;
      case LONGNAME:
        setLongName((String)value);
        return;
      case FORMLNAME:
        setFormlName((String)value);
        return;
      case MAXLENGTHNUM:
        setMaxLengthNum((Number)value);
        return;
      case MINLENGTHNUM:
        setMinLengthNum((Number)value);
        return;
      case DECIMALPLACE:
        setDecimalPlace((Number)value);
        return;
      case LATESTVERSIONIND:
        setLatestVersionInd((String)value);
        return;
      case DELETEDIND:
        setDeletedInd((String)value);
        return;
      case DATECREATED:
        setDateCreated((Date)value);
        return;
      case CREATEDBY:
        setCreatedBy((String)value);
        return;
      case DATEMODIFIED:
        setDateModified((Date)value);
        return;
      case MODIFIEDBY:
        setModifiedBy((String)value);
        return;
      case CHARSETNAME:
        setCharSetName((String)value);
        return;
      case HIGHVALUENUM:
        setHighValueNum((String)value);
        return;
      case LOWVALUENUM:
        setLowValueNum((String)value);
        return;
      case REPIDSEQ:
        setRepIdseq((String)value);
        return;
      case QUALIFIERNAME:
        setQualifierName((String)value);
        return;
      case ORIGIN:
        setOrigin((String)value);
        return;
      case VDID:
        setVdId((Number)value);
        return;
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }



  /**
   * 
   * Gets the associated entity ContextsImpl
   */
  public ContextsImpl getContexts()
  {
    return (ContextsImpl)getAttributeInternal(CONTEXTS);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity ContextsImpl
   */
  public void setContexts(ContextsImpl value)
  {
    setAttributeInternal(CONTEXTS, value);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getDataElements()
  {
    return (RowIterator)getAttributeInternal(DATAELEMENTS);
  }


  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getVdPvs()
  {
    return (RowIterator)getAttributeInternal(VDPVS);
  }


  /**
   * 
   * Gets the attribute value for RepIdseq, using the alias name RepIdseq
   */
  public String getRepIdseq() {
    return (String)getAttributeInternal(REPIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for RepIdseq
   */
  public void setRepIdseq(String value) {
    setAttributeInternal(REPIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for QualifierName, using the alias name QualifierName
   */
  public String getQualifierName() {
    return (String)getAttributeInternal(QUALIFIERNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for QualifierName
   */
  public void setQualifierName(String value) {
    setAttributeInternal(QUALIFIERNAME, value);
  }

  /**
   * 
   * Gets the attribute value for Origin, using the alias name Origin
   */
  public String getOrigin() {
    return (String)getAttributeInternal(ORIGIN);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Origin
   */
  public void setOrigin(String value) {
    setAttributeInternal(ORIGIN, value);
  }

  /**
   * 
   * Gets the attribute value for VdId, using the alias name VdId
   */
  public Number getVdId() {
    return (Number)getAttributeInternal(VDID);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for VdId
   */
  public void setVdId(Number value) {
    setAttributeInternal(VDID, value);
  }

  /**
   * 
   * Gets the associated entity ConceptualDomainsImpl
   */
  public ConceptualDomainsImpl getConceptualDomains() {
    return (ConceptualDomainsImpl)getAttributeInternal(CONCEPTUALDOMAINS);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity ConceptualDomainsImpl
   */
  public void setConceptualDomains(ConceptualDomainsImpl value) {
    setAttributeInternal(CONCEPTUALDOMAINS, value);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String vdIdseq)
  {
    return new Key(new Object[] {vdIdseq});
  }







}
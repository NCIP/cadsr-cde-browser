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

public class DataElementsImpl extends EntityImpl 
{
  protected static final int DEIDSEQ = 0;
  protected static final int VERSION = 1;
  protected static final int CONTEIDSEQ = 2;
  protected static final int PREFERREDNAME = 3;
  protected static final int VDIDSEQ = 4;
  protected static final int DECIDSEQ = 5;
  protected static final int PREFERREDDEFINITION = 6;
  protected static final int ASLNAME = 7;
  protected static final int LONGNAME = 8;
  protected static final int LATESTVERSIONIND = 9;
  protected static final int DELETEDIND = 10;
  protected static final int DATECREATED = 11;
  protected static final int BEGINDATE = 12;
  protected static final int CREATEDBY = 13;
  protected static final int ENDDATE = 14;
  protected static final int DATEMODIFIED = 15;
  protected static final int MODIFIEDBY = 16;
  protected static final int CHANGENOTE = 17;
  protected static final int ORIGIN = 18;
  protected static final int CDEID = 19;
  protected static final int QUESTION = 20;
  protected static final int CONTEXTS = 21;
  protected static final int DATAELEMENTCONCEPTS = 22;
  protected static final int VALUEDOMAINS = 23;
  protected static final int QUESTCONTENTSEXT = 24;








  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public DataElementsImpl()
  {
  }

  /**
   * 
   * Retrieves the definition object for this instance class.
   */
  public static synchronized EntityDefImpl getDefinitionObject()
  {
    if (mDefinitionObject == null)
    {
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.persistence.bc4j.DataElements");
    }
    return mDefinitionObject;
  }









  /**
   * 
   * Gets the attribute value for DeIdseq, using the alias name DeIdseq
   */
  public String getDeIdseq()
  {
    return (String)getAttributeInternal(DEIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DeIdseq
   */
  public void setDeIdseq(String value)
  {
    setAttributeInternal(DEIDSEQ, value);
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
   * Gets the attribute value for DecIdseq, using the alias name DecIdseq
   */
  public String getDecIdseq()
  {
    return (String)getAttributeInternal(DECIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DecIdseq
   */
  public void setDecIdseq(String value)
  {
    setAttributeInternal(DECIDSEQ, value);
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
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case DEIDSEQ:
        return getDeIdseq();
      case VERSION:
        return getVersion();
      case CONTEIDSEQ:
        return getConteIdseq();
      case PREFERREDNAME:
        return getPreferredName();
      case VDIDSEQ:
        return getVdIdseq();
      case DECIDSEQ:
        return getDecIdseq();
      case PREFERREDDEFINITION:
        return getPreferredDefinition();
      case ASLNAME:
        return getAslName();
      case LONGNAME:
        return getLongName();
      case LATESTVERSIONIND:
        return getLatestVersionInd();
      case DELETEDIND:
        return getDeletedInd();
      case DATECREATED:
        return getDateCreated();
      case BEGINDATE:
        return getBeginDate();
      case CREATEDBY:
        return getCreatedBy();
      case ENDDATE:
        return getEndDate();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      case CHANGENOTE:
        return getChangeNote();
      case ORIGIN:
        return getOrigin();
      case CDEID:
        return getCdeId();
      case QUESTION:
        return getQuestion();
      case QUESTCONTENTSEXT:
        return getQuestContentsExt();
      case CONTEXTS:
        return getContexts();
      case DATAELEMENTCONCEPTS:
        return getDataElementConcepts();
      case VALUEDOMAINS:
        return getValueDomains();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case DEIDSEQ:
        setDeIdseq((String)value);
        return;
      case VERSION:
        setVersion((Number)value);
        return;
      case CONTEIDSEQ:
        setConteIdseq((String)value);
        return;
      case PREFERREDNAME:
        setPreferredName((String)value);
        return;
      case VDIDSEQ:
        setVdIdseq((String)value);
        return;
      case DECIDSEQ:
        setDecIdseq((String)value);
        return;
      case PREFERREDDEFINITION:
        setPreferredDefinition((String)value);
        return;
      case ASLNAME:
        setAslName((String)value);
        return;
      case LONGNAME:
        setLongName((String)value);
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
      case BEGINDATE:
        setBeginDate((Date)value);
        return;
      case CREATEDBY:
        setCreatedBy((String)value);
        return;
      case ENDDATE:
        setEndDate((Date)value);
        return;
      case DATEMODIFIED:
        setDateModified((Date)value);
        return;
      case MODIFIEDBY:
        setModifiedBy((String)value);
        return;
      case CHANGENOTE:
        setChangeNote((String)value);
        return;
      case ORIGIN:
        setOrigin((String)value);
        return;
      case CDEID:
        setCdeId((Number)value);
        return;
      case QUESTION:
        setQuestion((String)value);
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
   * Gets the associated entity DataElementConceptsImpl
   */
  public DataElementConceptsImpl getDataElementConcepts()
  {
    return (DataElementConceptsImpl)getAttributeInternal(DATAELEMENTCONCEPTS);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity DataElementConceptsImpl
   */
  public void setDataElementConcepts(DataElementConceptsImpl value)
  {
    setAttributeInternal(DATAELEMENTCONCEPTS, value);
  }


  /**
   * 
   * Gets the associated entity ValueDomainsImpl
   */
  public ValueDomainsImpl getValueDomains()
  {
    return (ValueDomainsImpl)getAttributeInternal(VALUEDOMAINS);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity ValueDomainsImpl
   */
  public void setValueDomains(ValueDomainsImpl value)
  {
    setAttributeInternal(VALUEDOMAINS, value);
  }


  /**
   * 
   * Gets the attribute value for Origin, using the alias name Origin
   */
  public String getOrigin()
  {
    return (String)getAttributeInternal(ORIGIN);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Origin
   */
  public void setOrigin(String value)
  {
    setAttributeInternal(ORIGIN, value);
  }

  /**
   * 
   * Gets the attribute value for CdeId, using the alias name CdeId
   */
  public Number getCdeId()
  {
    return (Number)getAttributeInternal(CDEID);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CdeId
   */
  public void setCdeId(Number value)
  {
    setAttributeInternal(CDEID, value);
  }

  /**
   * 
   * Gets the attribute value for Question, using the alias name Question
   */
  public String getQuestion()
  {
    return (String)getAttributeInternal(QUESTION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Question
   */
  public void setQuestion(String value)
  {
    setAttributeInternal(QUESTION, value);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getQuestContentsExt()
  {
    return (RowIterator)getAttributeInternal(QUESTCONTENTSEXT);
  }

  /**
   * 
   * Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String deIdseq)
  {
    return new Key(new Object[] {deIdseq});
  }





}
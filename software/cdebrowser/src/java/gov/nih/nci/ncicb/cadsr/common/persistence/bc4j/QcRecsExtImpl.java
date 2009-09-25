package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Date;
import oracle.jbo.Key;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class QcRecsExtImpl extends EntityImpl 
{
  public static final int QRIDSEQ = 0;
  public static final int PQCIDSEQ = 1;
  public static final int CQCIDSEQ = 2;
  public static final int DISPLAYORDER = 3;
  public static final int RLNAME = 4;
  public static final int DATECREATED = 5;
  public static final int CREATEDBY = 6;
  public static final int DATEMODIFIED = 7;
  public static final int MODIFIEDBY = 8;
  public static final int QUESTCONTENTSEXT = 9;
  public static final int QUESTCONTENTSEXT1 = 10;






  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public QcRecsExtImpl()
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
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.QcRecsExt");
    }
    return mDefinitionObject;
  }







  /**
   * 
   * Gets the attribute value for QrIdseq, using the alias name QrIdseq
   */
  public String getQrIdseq()
  {
    return (String)getAttributeInternal(QRIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for QrIdseq
   */
  public void setQrIdseq(String value)
  {
    setAttributeInternal(QRIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for PQcIdseq, using the alias name PQcIdseq
   */
  public String getPQcIdseq()
  {
    return (String)getAttributeInternal(PQCIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PQcIdseq
   */
  public void setPQcIdseq(String value)
  {
    setAttributeInternal(PQCIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for CQcIdseq, using the alias name CQcIdseq
   */
  public String getCQcIdseq()
  {
    return (String)getAttributeInternal(CQCIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CQcIdseq
   */
  public void setCQcIdseq(String value)
  {
    setAttributeInternal(CQCIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for DisplayOrder, using the alias name DisplayOrder
   */
  public Number getDisplayOrder()
  {
    return (Number)getAttributeInternal(DISPLAYORDER);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DisplayOrder
   */
  public void setDisplayOrder(Number value)
  {
    setAttributeInternal(DISPLAYORDER, value);
  }

  /**
   * 
   * Gets the attribute value for RlName, using the alias name RlName
   */
  public String getRlName()
  {
    return (String)getAttributeInternal(RLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for RlName
   */
  public void setRlName(String value)
  {
    setAttributeInternal(RLNAME, value);
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
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case QRIDSEQ:
        return getQrIdseq();
      case PQCIDSEQ:
        return getPQcIdseq();
      case CQCIDSEQ:
        return getCQcIdseq();
      case DISPLAYORDER:
        return getDisplayOrder();
      case RLNAME:
        return getRlName();
      case DATECREATED:
        return getDateCreated();
      case CREATEDBY:
        return getCreatedBy();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      case QUESTCONTENTSEXT:
        return getQuestContentsExt();
      case QUESTCONTENTSEXT1:
        return getQuestContentsExt1();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case QRIDSEQ:
        setQrIdseq((String)value);
        return;
      case PQCIDSEQ:
        setPQcIdseq((String)value);
        return;
      case CQCIDSEQ:
        setCQcIdseq((String)value);
        return;
      case DISPLAYORDER:
        setDisplayOrder((Number)value);
        return;
      case RLNAME:
        setRlName((String)value);
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
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }

  /**
   * 
   * Gets the associated entity QuestContentsExtImpl
   */
  public QuestContentsExtImpl getQuestContentsExt()
  {
    return (QuestContentsExtImpl)getAttributeInternal(QUESTCONTENTSEXT);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity QuestContentsExtImpl
   */
  public void setQuestContentsExt(QuestContentsExtImpl value)
  {
    setAttributeInternal(QUESTCONTENTSEXT, value);
  }

  /**
   * 
   * Gets the associated entity QuestContentsExtImpl
   */
  public QuestContentsExtImpl getQuestContentsExt1()
  {
    return (QuestContentsExtImpl)getAttributeInternal(QUESTCONTENTSEXT1);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity QuestContentsExtImpl
   */
  public void setQuestContentsExt1(QuestContentsExtImpl value)
  {
    setAttributeInternal(QUESTCONTENTSEXT1, value);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String qrIdseq)
  {
    return new Key(new Object[] {qrIdseq});
  }







}
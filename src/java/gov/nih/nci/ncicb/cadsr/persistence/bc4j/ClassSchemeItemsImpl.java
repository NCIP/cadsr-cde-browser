package gov.nih.nci.ncicb.cadsr.persistence.bc4j;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.Key;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class ClassSchemeItemsImpl extends EntityImpl 
{
  protected static final int CSIIDSEQ = 0;
  protected static final int CSINAME = 1;
  protected static final int CSITLNAME = 2;
  protected static final int DESCRIPTION = 3;
  protected static final int COMMENTS = 4;
  protected static final int DATECREATED = 5;
  protected static final int CREATEDBY = 6;
  protected static final int DATEMODIFIED = 7;
  protected static final int MODIFIEDBY = 8;
  protected static final int CSCSI = 9;

  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public ClassSchemeItemsImpl()
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
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.persistence.bc4j.ClassSchemeItems");
    }
    return mDefinitionObject;
  }


  /**
   * 
   * Gets the attribute value for CsiIdseq, using the alias name CsiIdseq
   */
  public String getCsiIdseq()
  {
    return (String)getAttributeInternal(CSIIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CsiIdseq
   */
  public void setCsiIdseq(String value)
  {
    setAttributeInternal(CSIIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for CsiName, using the alias name CsiName
   */
  public String getCsiName()
  {
    return (String)getAttributeInternal(CSINAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CsiName
   */
  public void setCsiName(String value)
  {
    setAttributeInternal(CSINAME, value);
  }

  /**
   * 
   * Gets the attribute value for CsitlName, using the alias name CsitlName
   */
  public String getCsitlName()
  {
    return (String)getAttributeInternal(CSITLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CsitlName
   */
  public void setCsitlName(String value)
  {
    setAttributeInternal(CSITLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for Description, using the alias name Description
   */
  public String getDescription()
  {
    return (String)getAttributeInternal(DESCRIPTION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Description
   */
  public void setDescription(String value)
  {
    setAttributeInternal(DESCRIPTION, value);
  }

  /**
   * 
   * Gets the attribute value for Comments, using the alias name Comments
   */
  public String getComments()
  {
    return (String)getAttributeInternal(COMMENTS);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Comments
   */
  public void setComments(String value)
  {
    setAttributeInternal(COMMENTS, value);
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
      case CSIIDSEQ:
        return getCsiIdseq();
      case CSINAME:
        return getCsiName();
      case CSITLNAME:
        return getCsitlName();
      case DESCRIPTION:
        return getDescription();
      case COMMENTS:
        return getComments();
      case DATECREATED:
        return getDateCreated();
      case CREATEDBY:
        return getCreatedBy();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      case CSCSI:
        return getCsCsi();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case CSIIDSEQ:
        setCsiIdseq((String)value);
        return;
      case CSINAME:
        setCsiName((String)value);
        return;
      case CSITLNAME:
        setCsitlName((String)value);
        return;
      case DESCRIPTION:
        setDescription((String)value);
        return;
      case COMMENTS:
        setComments((String)value);
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
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getCsCsi()
  {
    return (RowIterator)getAttributeInternal(CSCSI);
  }

  /**
   * 
   * Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String csiIdseq)
  {
    return new Key(new Object[] {csiIdseq});
  }
}
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

public class VdPvsImpl extends EntityImpl 
{
  public static final int VPIDSEQ = 0;
  public static final int VDIDSEQ = 1;
  public static final int PVIDSEQ = 2;
  public static final int CONTEIDSEQ = 3;
  public static final int DATECREATED = 4;
  public static final int CREATEDBY = 5;
  public static final int DATEMODIFIED = 6;
  public static final int MODIFIEDBY = 7;
  public static final int CONTEXTS = 8;
  public static final int PERMISSIBLEVALUES = 9;
  public static final int VALUEDOMAINS = 10;
  public static final int QUESTCONTENTSEXT = 11;








  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public VdPvsImpl()
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
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.persistence.bc4j.VdPvs");
    }
    return mDefinitionObject;
  }









  /**
   * 
   * Gets the attribute value for VpIdseq, using the alias name VpIdseq
   */
  public String getVpIdseq()
  {
    return (String)getAttributeInternal(VPIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for VpIdseq
   */
  public void setVpIdseq(String value)
  {
    setAttributeInternal(VPIDSEQ, value);
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
   * Gets the attribute value for PvIdseq, using the alias name PvIdseq
   */
  public String getPvIdseq()
  {
    return (String)getAttributeInternal(PVIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PvIdseq
   */
  public void setPvIdseq(String value)
  {
    setAttributeInternal(PVIDSEQ, value);
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
      case VPIDSEQ:
        return getVpIdseq();
      case VDIDSEQ:
        return getVdIdseq();
      case PVIDSEQ:
        return getPvIdseq();
      case CONTEIDSEQ:
        return getConteIdseq();
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
      case CONTEXTS:
        return getContexts();
      case PERMISSIBLEVALUES:
        return getPermissibleValues();
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
      case VPIDSEQ:
        setVpIdseq((String)value);
        return;
      case VDIDSEQ:
        setVdIdseq((String)value);
        return;
      case PVIDSEQ:
        setPvIdseq((String)value);
        return;
      case CONTEIDSEQ:
        setConteIdseq((String)value);
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
   * Gets the associated entity PermissibleValuesImpl
   */
  public PermissibleValuesImpl getPermissibleValues()
  {
    return (PermissibleValuesImpl)getAttributeInternal(PERMISSIBLEVALUES);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity PermissibleValuesImpl
   */
  public void setPermissibleValues(PermissibleValuesImpl value)
  {
    setAttributeInternal(PERMISSIBLEVALUES, value);
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
   *  Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getQuestContentsExt()
  {
    return (RowIterator)getAttributeInternal(QUESTCONTENTSEXT);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String vpIdseq)
  {
    return new Key(new Object[] {vpIdseq});
  }







}
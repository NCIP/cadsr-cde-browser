/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Date;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class AcCsiViewRowImpl extends ViewRowImpl 
{


  public static final int ACCSIIDSEQ = 0;
  public static final int CSCSIIDSEQ = 1;
  public static final int ACIDSEQ = 2;
  public static final int DATECREATED = 3;
  public static final int CREATEDBY = 4;
  public static final int DATEMODIFIED = 5;
  public static final int MODIFIEDBY = 6;
  /**
   * 
   * This is the default constructor (do not remove)
   */
  public AcCsiViewRowImpl()
  {
  }

  /**
   * 
   * Gets AcCsi entity object.
   */
  public AcCsiImpl getAcCsi()
  {
    return (AcCsiImpl)getEntity(0);
  }

  /**
   * 
   * Gets the attribute value for AC_CSI_IDSEQ using the alias name AcCsiIdseq
   */
  public String getAcCsiIdseq()
  {
    return (String)getAttributeInternal(ACCSIIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for AC_CSI_IDSEQ using the alias name AcCsiIdseq
   */
  public void setAcCsiIdseq(String value)
  {
    setAttributeInternal(ACCSIIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for CS_CSI_IDSEQ using the alias name CsCsiIdseq
   */
  public String getCsCsiIdseq()
  {
    return (String)getAttributeInternal(CSCSIIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for CS_CSI_IDSEQ using the alias name CsCsiIdseq
   */
  public void setCsCsiIdseq(String value)
  {
    setAttributeInternal(CSCSIIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for AC_IDSEQ using the alias name AcIdseq
   */
  public String getAcIdseq()
  {
    return (String)getAttributeInternal(ACIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for AC_IDSEQ using the alias name AcIdseq
   */
  public void setAcIdseq(String value)
  {
    setAttributeInternal(ACIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for DATE_CREATED using the alias name DateCreated
   */
  public Date getDateCreated()
  {
    return (Date)getAttributeInternal(DATECREATED);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for DATE_CREATED using the alias name DateCreated
   */
  public void setDateCreated(Date value)
  {
    setAttributeInternal(DATECREATED, value);
  }

  /**
   * 
   * Gets the attribute value for CREATED_BY using the alias name CreatedBy
   */
  public String getCreatedBy()
  {
    return (String)getAttributeInternal(CREATEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy
   */
  public void setCreatedBy(String value)
  {
    setAttributeInternal(CREATEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for DATE_MODIFIED using the alias name DateModified
   */
  public Date getDateModified()
  {
    return (Date)getAttributeInternal(DATEMODIFIED);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for DATE_MODIFIED using the alias name DateModified
   */
  public void setDateModified(Date value)
  {
    setAttributeInternal(DATEMODIFIED, value);
  }

  /**
   * 
   * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy
   */
  public String getModifiedBy()
  {
    return (String)getAttributeInternal(MODIFIEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy
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
      case ACCSIIDSEQ:
        return getAcCsiIdseq();
      case CSCSIIDSEQ:
        return getCsCsiIdseq();
      case ACIDSEQ:
        return getAcIdseq();
      case DATECREATED:
        return getDateCreated();
      case CREATEDBY:
        return getCreatedBy();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
    switch (index)
      {
      case ACCSIIDSEQ:
        setAcCsiIdseq((String)value);
        return;
      case CSCSIIDSEQ:
        setCsCsiIdseq((String)value);
        return;
      case ACIDSEQ:
        setAcIdseq((String)value);
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
}
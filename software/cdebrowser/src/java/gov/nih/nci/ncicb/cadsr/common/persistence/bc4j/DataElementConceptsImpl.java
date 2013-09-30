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

public class DataElementConceptsImpl extends EntityImpl 
{
  public static final int DECIDSEQ = 0;
  public static final int VERSION = 1;
  public static final int PREFERREDNAME = 2;
  public static final int CONTEIDSEQ = 3;
  public static final int CDIDSEQ = 4;
  public static final int PROPLNAME = 5;
  public static final int OCLNAME = 6;
  public static final int PREFERREDDEFINITION = 7;
  public static final int ASLNAME = 8;
  public static final int LONGNAME = 9;
  public static final int LATESTVERSIONIND = 10;
  public static final int DELETEDIND = 11;
  public static final int DATECREATED = 12;
  public static final int BEGINDATE = 13;
  public static final int CREATEDBY = 14;
  public static final int ENDDATE = 15;
  public static final int DATEMODIFIED = 16;
  public static final int MODIFIEDBY = 17;
  public static final int OBJCLASSQUALIFIER = 18;
  public static final int PROPERTYQUALIFIER = 19;
  public static final int CHANGENOTE = 20;
  public static final int OCIDSEQ = 21;
  public static final int PROPIDSEQ = 22;
  public static final int ORIGIN = 23;
  public static final int DECID = 24;
  public static final int CONTEXTS = 25;
  public static final int OBJECTCLASSESEXT = 26;
  public static final int PROPERTIESEXT = 27;
  public static final int CONCEPTUALDOMAINS = 28;
  public static final int DATAELEMENTS = 29;



















  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public DataElementConceptsImpl()
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
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.DataElementConcepts");
    }
    return mDefinitionObject;
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
   * Gets the attribute value for ProplName, using the alias name ProplName
   */
  public String getProplName()
  {
    return (String)getAttributeInternal(PROPLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ProplName
   */
  public void setProplName(String value)
  {
    setAttributeInternal(PROPLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for OclName, using the alias name OclName
   */
  public String getOclName()
  {
    return (String)getAttributeInternal(OCLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for OclName
   */
  public void setOclName(String value)
  {
    setAttributeInternal(OCLNAME, value);
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
   * Gets the attribute value for ObjClassQualifier, using the alias name ObjClassQualifier
   */
  public String getObjClassQualifier()
  {
    return (String)getAttributeInternal(OBJCLASSQUALIFIER);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ObjClassQualifier
   */
  public void setObjClassQualifier(String value)
  {
    setAttributeInternal(OBJCLASSQUALIFIER, value);
  }

  /**
   * 
   * Gets the attribute value for PropertyQualifier, using the alias name PropertyQualifier
   */
  public String getPropertyQualifier()
  {
    return (String)getAttributeInternal(PROPERTYQUALIFIER);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PropertyQualifier
   */
  public void setPropertyQualifier(String value)
  {
    setAttributeInternal(PROPERTYQUALIFIER, value);
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
      case DECIDSEQ:
        return getDecIdseq();
      case VERSION:
        return getVersion();
      case PREFERREDNAME:
        return getPreferredName();
      case CONTEIDSEQ:
        return getConteIdseq();
      case CDIDSEQ:
        return getCdIdseq();
      case PROPLNAME:
        return getProplName();
      case OCLNAME:
        return getOclName();
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
      case OBJCLASSQUALIFIER:
        return getObjClassQualifier();
      case PROPERTYQUALIFIER:
        return getPropertyQualifier();
      case CHANGENOTE:
        return getChangeNote();
      case OCIDSEQ:
        return getOcIdseq();
      case PROPIDSEQ:
        return getPropIdseq();
      case ORIGIN:
        return getOrigin();
      case DECID:
        return getDecId();
      case DATAELEMENTS:
        return getDataElements();
      case CONTEXTS:
        return getContexts();
      case OBJECTCLASSESEXT:
        return getObjectClassesExt();
      case PROPERTIESEXT:
        return getPropertiesExt();
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
      case DECIDSEQ:
        setDecIdseq((String)value);
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
      case CDIDSEQ:
        setCdIdseq((String)value);
        return;
      case PROPLNAME:
        setProplName((String)value);
        return;
      case OCLNAME:
        setOclName((String)value);
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
      case OBJCLASSQUALIFIER:
        setObjClassQualifier((String)value);
        return;
      case PROPERTYQUALIFIER:
        setPropertyQualifier((String)value);
        return;
      case CHANGENOTE:
        setChangeNote((String)value);
        return;
      case OCIDSEQ:
        setOcIdseq((String)value);
        return;
      case PROPIDSEQ:
        setPropIdseq((String)value);
        return;
      case ORIGIN:
        setOrigin((String)value);
        return;
      case DECID:
        setDecId((Number)value);
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
   * Gets the attribute value for OcIdseq, using the alias name OcIdseq
   */
  public String getOcIdseq() {
    return (String)getAttributeInternal(OCIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for OcIdseq
   */
  public void setOcIdseq(String value) {
    setAttributeInternal(OCIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for PropIdseq, using the alias name PropIdseq
   */
  public String getPropIdseq() {
    return (String)getAttributeInternal(PROPIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PropIdseq
   */
  public void setPropIdseq(String value) {
    setAttributeInternal(PROPIDSEQ, value);
  }

  /**
   * 
   * Gets the associated entity ObjectClassesExtImpl
   */
  public ObjectClassesExtImpl getObjectClassesExt() {
    return (ObjectClassesExtImpl)getAttributeInternal(OBJECTCLASSESEXT);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity ObjectClassesExtImpl
   */
  public void setObjectClassesExt(ObjectClassesExtImpl value) {
    setAttributeInternal(OBJECTCLASSESEXT, value);
  }


  /**
   * 
   * Gets the associated entity PropertiesExtImpl
   */
  public PropertiesExtImpl getPropertiesExt() {
    return (PropertiesExtImpl)getAttributeInternal(PROPERTIESEXT);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity PropertiesExtImpl
   */
  public void setPropertiesExt(PropertiesExtImpl value) {
    setAttributeInternal(PROPERTIESEXT, value);
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
   * Gets the attribute value for DecId, using the alias name DecId
   */
  public Number getDecId()
  {
    return (Number)getAttributeInternal(DECID);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DecId
   */
  public void setDecId(Number value)
  {
    setAttributeInternal(DECID, value);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String decIdseq)
  {
    return new Key(new Object[] {decIdseq});
  }















}
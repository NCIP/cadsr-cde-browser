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
import oracle.jbo.AttributeList;
import oracle.jbo.server.TransactionEvent;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Date;
import oracle.jbo.Key;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class PropertiesExtImpl extends EntityImpl  {
  public static final int PROPIDSEQ = 0;
  public static final int PREFERREDNAME = 1;
  public static final int LONGNAME = 2;
  public static final int PREFERREDDEFINITION = 3;
  public static final int CONTEIDSEQ = 4;
  public static final int VERSION = 5;
  public static final int ASLNAME = 6;
  public static final int LATESTVERSIONIND = 7;
  public static final int CHANGENOTE = 8;
  public static final int BEGINDATE = 9;
  public static final int ENDDATE = 10;
  public static final int DATECREATED = 11;
  public static final int CREATEDBY = 12;
  public static final int DELETEDIND = 13;
  public static final int DATEMODIFIED = 14;
  public static final int MODIFIEDBY = 15;
  public static final int ORIGIN = 16;
  public static final int DEFINITIONSOURCE = 17;
  public static final int PROPID = 18;
  public static final int CONTEXTS = 19;
  public static final int DATAELEMENTCONCEPTS = 20;








  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public PropertiesExtImpl() {
  }

  /**
   * 
   *  Retrieves the definition object for this instance class.
   */
  public static synchronized EntityDefImpl getDefinitionObject()
  {
    if (mDefinitionObject == null)
    {
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.PropertiesExt");
    }
    return mDefinitionObject;
  }









  /**
   * 
   * Add attribute defaulting logic in this method.
   */
  protected void create(AttributeList attributeList) {
    super.create(attributeList);
  }

  /**
   * 
   * Add entity remove logic in this method.
   */
  public void remove() {
    super.remove();
  }

  /**
   * 
   * Add Entity validation code in this method.
   */
  protected void validateEntity() {
    super.validateEntity();
  }

  /**
   * 
   * Add locking logic here.
   */
  public void lock() {
    super.lock();
  }

  /**
   * 
   * Custom DML update/insert/delete logic here.
   */
  protected void doDML(int operation, TransactionEvent e) {
    super.doDML(operation, e);
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
   * Gets the attribute value for PreferredName, using the alias name PreferredName
   */
  public String getPreferredName() {
    return (String)getAttributeInternal(PREFERREDNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PreferredName
   */
  public void setPreferredName(String value) {
    setAttributeInternal(PREFERREDNAME, value);
  }

  /**
   * 
   * Gets the attribute value for LongName, using the alias name LongName
   */
  public String getLongName() {
    return (String)getAttributeInternal(LONGNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LongName
   */
  public void setLongName(String value) {
    setAttributeInternal(LONGNAME, value);
  }

  /**
   * 
   * Gets the attribute value for PreferredDefinition, using the alias name PreferredDefinition
   */
  public String getPreferredDefinition() {
    return (String)getAttributeInternal(PREFERREDDEFINITION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PreferredDefinition
   */
  public void setPreferredDefinition(String value) {
    setAttributeInternal(PREFERREDDEFINITION, value);
  }

  /**
   * 
   * Gets the attribute value for ConteIdseq, using the alias name ConteIdseq
   */
  public String getConteIdseq() {
    return (String)getAttributeInternal(CONTEIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ConteIdseq
   */
  public void setConteIdseq(String value) {
    setAttributeInternal(CONTEIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for Version, using the alias name Version
   */
  public Number getVersion() {
    return (Number)getAttributeInternal(VERSION);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Version
   */
  public void setVersion(Number value) {
    setAttributeInternal(VERSION, value);
  }

  /**
   * 
   * Gets the attribute value for AslName, using the alias name AslName
   */
  public String getAslName() {
    return (String)getAttributeInternal(ASLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for AslName
   */
  public void setAslName(String value) {
    setAttributeInternal(ASLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for LatestVersionInd, using the alias name LatestVersionInd
   */
  public String getLatestVersionInd() {
    return (String)getAttributeInternal(LATESTVERSIONIND);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LatestVersionInd
   */
  public void setLatestVersionInd(String value) {
    setAttributeInternal(LATESTVERSIONIND, value);
  }

  /**
   * 
   * Gets the attribute value for ChangeNote, using the alias name ChangeNote
   */
  public String getChangeNote() {
    return (String)getAttributeInternal(CHANGENOTE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ChangeNote
   */
  public void setChangeNote(String value) {
    setAttributeInternal(CHANGENOTE, value);
  }

  /**
   * 
   * Gets the attribute value for BeginDate, using the alias name BeginDate
   */
  public Date getBeginDate() {
    return (Date)getAttributeInternal(BEGINDATE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for BeginDate
   */
  public void setBeginDate(Date value) {
    setAttributeInternal(BEGINDATE, value);
  }

  /**
   * 
   * Gets the attribute value for EndDate, using the alias name EndDate
   */
  public Date getEndDate() {
    return (Date)getAttributeInternal(ENDDATE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for EndDate
   */
  public void setEndDate(Date value) {
    setAttributeInternal(ENDDATE, value);
  }

  /**
   * 
   * Gets the attribute value for DateCreated, using the alias name DateCreated
   */
  public Date getDateCreated() {
    return (Date)getAttributeInternal(DATECREATED);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DateCreated
   */
  public void setDateCreated(Date value) {
    setAttributeInternal(DATECREATED, value);
  }

  /**
   * 
   * Gets the attribute value for CreatedBy, using the alias name CreatedBy
   */
  public String getCreatedBy() {
    return (String)getAttributeInternal(CREATEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for CreatedBy
   */
  public void setCreatedBy(String value) {
    setAttributeInternal(CREATEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for DeletedInd, using the alias name DeletedInd
   */
  public String getDeletedInd() {
    return (String)getAttributeInternal(DELETEDIND);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DeletedInd
   */
  public void setDeletedInd(String value) {
    setAttributeInternal(DELETEDIND, value);
  }

  /**
   * 
   * Gets the attribute value for DateModified, using the alias name DateModified
   */
  public Date getDateModified() {
    return (Date)getAttributeInternal(DATEMODIFIED);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DateModified
   */
  public void setDateModified(Date value) {
    setAttributeInternal(DATEMODIFIED, value);
  }

  /**
   * 
   * Gets the attribute value for ModifiedBy, using the alias name ModifiedBy
   */
  public String getModifiedBy() {
    return (String)getAttributeInternal(MODIFIEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for ModifiedBy
   */
  public void setModifiedBy(String value) {
    setAttributeInternal(MODIFIEDBY, value);
  }
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case PROPIDSEQ:
        return getPropIdseq();
      case PREFERREDNAME:
        return getPreferredName();
      case LONGNAME:
        return getLongName();
      case PREFERREDDEFINITION:
        return getPreferredDefinition();
      case CONTEIDSEQ:
        return getConteIdseq();
      case VERSION:
        return getVersion();
      case ASLNAME:
        return getAslName();
      case LATESTVERSIONIND:
        return getLatestVersionInd();
      case CHANGENOTE:
        return getChangeNote();
      case BEGINDATE:
        return getBeginDate();
      case ENDDATE:
        return getEndDate();
      case DATECREATED:
        return getDateCreated();
      case CREATEDBY:
        return getCreatedBy();
      case DELETEDIND:
        return getDeletedInd();
      case DATEMODIFIED:
        return getDateModified();
      case MODIFIEDBY:
        return getModifiedBy();
      case ORIGIN:
        return getOrigin();
      case DEFINITIONSOURCE:
        return getDefinitionSource();
      case PROPID:
        return getPropId();
      case DATAELEMENTCONCEPTS:
        return getDataElementConcepts();
      case CONTEXTS:
        return getContexts();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case PROPIDSEQ:
        setPropIdseq((String)value);
        return;
      case PREFERREDNAME:
        setPreferredName((String)value);
        return;
      case LONGNAME:
        setLongName((String)value);
        return;
      case PREFERREDDEFINITION:
        setPreferredDefinition((String)value);
        return;
      case CONTEIDSEQ:
        setConteIdseq((String)value);
        return;
      case VERSION:
        setVersion((Number)value);
        return;
      case ASLNAME:
        setAslName((String)value);
        return;
      case LATESTVERSIONIND:
        setLatestVersionInd((String)value);
        return;
      case CHANGENOTE:
        setChangeNote((String)value);
        return;
      case BEGINDATE:
        setBeginDate((Date)value);
        return;
      case ENDDATE:
        setEndDate((Date)value);
        return;
      case DATECREATED:
        setDateCreated((Date)value);
        return;
      case CREATEDBY:
        setCreatedBy((String)value);
        return;
      case DELETEDIND:
        setDeletedInd((String)value);
        return;
      case DATEMODIFIED:
        setDateModified((Date)value);
        return;
      case MODIFIEDBY:
        setModifiedBy((String)value);
        return;
      case ORIGIN:
        setOrigin((String)value);
        return;
      case DEFINITIONSOURCE:
        setDefinitionSource((String)value);
        return;
      case PROPID:
        setPropId((Number)value);
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
  public ContextsImpl getContexts() {
    return (ContextsImpl)getAttributeInternal(CONTEXTS);
  }

  /**
   * 
   * Sets <code>value</code> as the associated entity ContextsImpl
   */
  public void setContexts(ContextsImpl value) {
    setAttributeInternal(CONTEXTS, value);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getDataElementConcepts() {
    return (RowIterator)getAttributeInternal(DATAELEMENTCONCEPTS);
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
   * Gets the attribute value for DefinitionSource, using the alias name DefinitionSource
   */
  public String getDefinitionSource() {
    return (String)getAttributeInternal(DEFINITIONSOURCE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for DefinitionSource
   */
  public void setDefinitionSource(String value) {
    setAttributeInternal(DEFINITIONSOURCE, value);
  }

  /**
   * 
   * Gets the attribute value for PropId, using the alias name PropId
   */
  public Number getPropId() {
    return (Number)getAttributeInternal(PROPID);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PropId
   */
  public void setPropId(Number value) {
    setAttributeInternal(PROPID, value);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String propIdseq)
  {
    return new Key(new Object[] {propIdseq});
  }








}
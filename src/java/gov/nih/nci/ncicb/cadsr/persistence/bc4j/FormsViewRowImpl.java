package gov.nih.nci.ncicb.cadsr.persistence.bc4j;

import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JContextTransferObject;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------
public class FormsViewRowImpl extends ViewRowImpl {
  public static final int QCIDSEQ = 0;
  public static final int VERSION = 1;
  public static final int QTLNAME = 2;
  public static final int CONTEIDSEQ = 3;
  public static final int ASLNAME = 4;
  public static final int PREFERREDNAME = 5;
  public static final int PREFERREDDEFINITION = 6;
  public static final int PROTOIDSEQ = 7;
  public static final int QCDLNAME = 8;
  public static final int LONGNAME = 9;
  public static final int LATESTVERSIONIND = 10;
  public static final int DELETEDIND = 11;
  public static final int BEGINDATE = 12;
  public static final int ENDDATE = 13;
  public static final int ORIGIN = 14;
  public static final int CONTEXT = 15;
  public static final int PROTOCOL = 16;
  public static final int MODULES = 17;
  public static final int FORMMODULERECS = 18;

  /**
   * This is the default constructor (do not remove)
   */
  public FormsViewRowImpl() {
  }

  /**
   * Gets form entity object.
   */
  public QuestContentsExtImpl getform() {
    return (QuestContentsExtImpl) getEntity(0);
  }

  /**
   * Gets the attribute value for QC_IDSEQ using the alias name QcIdseq
   */
  public String getQcIdseq() {
    return (String) getAttributeInternal(QCIDSEQ);
  }

  /**
   * Sets <code>value</code> as attribute value for QC_IDSEQ using the alias
   * name QcIdseq
   */
  public void setQcIdseq(String value) {
    setAttributeInternal(QCIDSEQ, value);
  }

  /**
   * Gets the attribute value for VERSION using the alias name Version
   */
  public Number getVersion() {
    return (Number) getAttributeInternal(VERSION);
  }

  /**
   * Sets <code>value</code> as attribute value for VERSION using the alias
   * name Version
   */
  public void setVersion(Number value) {
    setAttributeInternal(VERSION, value);
  }

  /**
   * Gets the attribute value for QTL_NAME using the alias name QtlName
   */
  public String getQtlName() {
    return (String) getAttributeInternal(QTLNAME);
  }

  /**
   * Sets <code>value</code> as attribute value for QTL_NAME using the alias
   * name QtlName
   */
  public void setQtlName(String value) {
    setAttributeInternal(QTLNAME, value);
  }

  /**
   * Gets the attribute value for CONTE_IDSEQ using the alias name ConteIdseq
   */
  public String getConteIdseq() {
    return (String) getAttributeInternal(CONTEIDSEQ);
  }

  /**
   * Sets <code>value</code> as attribute value for CONTE_IDSEQ using the alias
   * name ConteIdseq
   */
  public void setConteIdseq(String value) {
    setAttributeInternal(CONTEIDSEQ, value);
  }

  /**
   * Gets the attribute value for ASL_NAME using the alias name AslName
   */
  public String getAslName() {
    return (String) getAttributeInternal(ASLNAME);
  }

  /**
   * Sets <code>value</code> as attribute value for ASL_NAME using the alias
   * name AslName
   */
  public void setAslName(String value) {
    setAttributeInternal(ASLNAME, value);
  }

  /**
   * Gets the attribute value for PREFERRED_NAME using the alias name
   * PreferredName
   */
  public String getPreferredName() {
    return (String) getAttributeInternal(PREFERREDNAME);
  }

  /**
   * Sets <code>value</code> as attribute value for PREFERRED_NAME using the
   * alias name PreferredName
   */
  public void setPreferredName(String value) {
    setAttributeInternal(PREFERREDNAME, value);
  }

  /**
   * Gets the attribute value for PREFERRED_DEFINITION using the alias name
   * PreferredDefinition
   */
  public String getPreferredDefinition() {
    return (String) getAttributeInternal(PREFERREDDEFINITION);
  }

  /**
   * Sets <code>value</code> as attribute value for PREFERRED_DEFINITION using
   * the alias name PreferredDefinition
   */
  public void setPreferredDefinition(String value) {
    setAttributeInternal(PREFERREDDEFINITION, value);
  }

  /**
   * Gets the attribute value for PROTO_IDSEQ using the alias name ProtoIdseq
   */
  public String getProtoIdseq() {
    return (String) getAttributeInternal(PROTOIDSEQ);
  }

  /**
   * Sets <code>value</code> as attribute value for PROTO_IDSEQ using the alias
   * name ProtoIdseq
   */
  public void setProtoIdseq(String value) {
    setAttributeInternal(PROTOIDSEQ, value);
  }

  /**
   * Gets the attribute value for QCDL_NAME using the alias name QcdlName
   */
  public String getQcdlName() {
    return (String) getAttributeInternal(QCDLNAME);
  }

  /**
   * Sets <code>value</code> as attribute value for QCDL_NAME using the alias
   * name QcdlName
   */
  public void setQcdlName(String value) {
    setAttributeInternal(QCDLNAME, value);
  }

  /**
   * Gets the attribute value for LONG_NAME using the alias name LongName
   */
  public String getLongName() {
    return (String) getAttributeInternal(LONGNAME);
  }

  /**
   * Sets <code>value</code> as attribute value for LONG_NAME using the alias
   * name LongName
   */
  public void setLongName(String value) {
    setAttributeInternal(LONGNAME, value);
  }

  /**
   * Gets the attribute value for LATEST_VERSION_IND using the alias name
   * LatestVersionInd
   */
  public String getLatestVersionInd() {
    return (String) getAttributeInternal(LATESTVERSIONIND);
  }

  /**
   * Sets <code>value</code> as attribute value for LATEST_VERSION_IND using
   * the alias name LatestVersionInd
   */
  public void setLatestVersionInd(String value) {
    setAttributeInternal(LATESTVERSIONIND, value);
  }

  /**
   * Gets the attribute value for DELETED_IND using the alias name DeletedInd
   */
  public String getDeletedInd() {
    return (String) getAttributeInternal(DELETEDIND);
  }

  /**
   * Sets <code>value</code> as attribute value for DELETED_IND using the alias
   * name DeletedInd
   */
  public void setDeletedInd(String value) {
    setAttributeInternal(DELETEDIND, value);
  }

  /**
   * Gets the attribute value for BEGIN_DATE using the alias name BeginDate
   */
  public Date getBeginDate() {
    return (Date) getAttributeInternal(BEGINDATE);
  }

  /**
   * Sets <code>value</code> as attribute value for BEGIN_DATE using the alias
   * name BeginDate
   */
  public void setBeginDate(Date value) {
    setAttributeInternal(BEGINDATE, value);
  }

  /**
   * Gets the attribute value for END_DATE using the alias name EndDate
   */
  public Date getEndDate() {
    return (Date) getAttributeInternal(ENDDATE);
  }

  /**
   * Sets <code>value</code> as attribute value for END_DATE using the alias
   * name EndDate
   */
  public void setEndDate(Date value) {
    setAttributeInternal(ENDDATE, value);
  }

  /**
   * Gets the attribute value for ORIGIN using the alias name Origin
   */
  public String getOrigin() {
    return (String) getAttributeInternal(ORIGIN);
  }

  /**
   * Sets <code>value</code> as attribute value for ORIGIN using the alias name
   * Origin
   */
  public void setOrigin(String value) {
    setAttributeInternal(ORIGIN, value);
  }

  //  Generated method. Do not modify.
  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef)
    throws Exception {
    switch (index)
      {
      case QCIDSEQ:
        return getQcIdseq();
      case VERSION:
        return getVersion();
      case QTLNAME:
        return getQtlName();
      case CONTEIDSEQ:
        return getConteIdseq();
      case ASLNAME:
        return getAslName();
      case PREFERREDNAME:
        return getPreferredName();
      case PREFERREDDEFINITION:
        return getPreferredDefinition();
      case PROTOIDSEQ:
        return getProtoIdseq();
      case QCDLNAME:
        return getQcdlName();
      case LONGNAME:
        return getLongName();
      case LATESTVERSIONIND:
        return getLatestVersionInd();
      case DELETEDIND:
        return getDeletedInd();
      case BEGINDATE:
        return getBeginDate();
      case ENDDATE:
        return getEndDate();
      case ORIGIN:
        return getOrigin();
      case MODULES:
        return getModules();
      case FORMMODULERECS:
        return getFormModuleRecs();
      case CONTEXT:
        return getContext();
      case PROTOCOL:
        return getProtocol();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }

  //  Generated method. Do not modify.
  protected void setAttrInvokeAccessor(
    int index, Object value, AttributeDefImpl attrDef)
    throws Exception {
    switch (index)
      {
      case VERSION:
        setVersion((Number)value);
        return;
      case QTLNAME:
        setQtlName((String)value);
        return;
      case CONTEIDSEQ:
        setConteIdseq((String)value);
        return;
      case ASLNAME:
        setAslName((String)value);
        return;
      case PREFERREDNAME:
        setPreferredName((String)value);
        return;
      case PREFERREDDEFINITION:
        setPreferredDefinition((String)value);
        return;
      case PROTOIDSEQ:
        setProtoIdseq((String)value);
        return;
      case QCDLNAME:
        setQcdlName((String)value);
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
      case BEGINDATE:
        setBeginDate((Date)value);
        return;
      case ENDDATE:
        setEndDate((Date)value);
        return;
      case ORIGIN:
        setOrigin((String)value);
        return;
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }

  /**
   * Gets the associated <code>RowIterator</code> using master-detail link
   * Modules
   */
  public oracle.jbo.RowIterator getModules() {
    return (oracle.jbo.RowIterator) getAttributeInternal(MODULES);
  }

  /**
   * Gets the associated <code>RowIterator</code> using master-detail link
   * FormModuleRecs
   */
  public oracle.jbo.RowIterator getFormModuleRecs() {
    return (oracle.jbo.RowIterator) getAttributeInternal(FORMMODULERECS);
  }

/**
  public List getModuleTranferObjects() {
    List modules = new ArrayList(25);
    oracle.jbo.RowIterator modRows = getModules();

    while (modRows.hasNext()) {
      modules.add(
        new BC4JModuleTransferObject((ModulesViewRowImpl) modRows.next()));
    }

    return modules;
  }
  **/

  /**
   * Gets the associated <code>Row</code> using master-detail link Context
   */
  public oracle.jbo.Row getContext() {
    return (oracle.jbo.Row) getAttributeInternal(CONTEXT);
  }
/**
  public Context getContextTransferObject() {
    Context conte =
      new BC4JContextTransferObject((ContextsViewRowImpl) getContext());

    return conte;
  }
**/
  /**
   * 
   * Gets the associated <code>Row</code> using master-detail link Protocol
   */
  public oracle.jbo.Row getProtocol() {
    return (oracle.jbo.Row)getAttributeInternal(PROTOCOL);
  }
}

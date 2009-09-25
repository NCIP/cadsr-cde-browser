package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;

import gov.nih.nci.ncicb.cadsr.common.dto.bc4j.BC4JContextTransferObject;

import gov.nih.nci.ncicb.cadsr.common.resource.Context;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;


//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------
public class QuestionsViewRowImpl extends ViewRowImpl {
  public static final int QCIDSEQ = 0;
  public static final int VERSION = 1;
  public static final int QTLNAME = 2;
  public static final int CONTEIDSEQ = 3;
  public static final int ASLNAME = 4;
  public static final int PREFERREDNAME = 5;
  public static final int PREFERREDDEFINITION = 6;
  public static final int DEIDSEQ = 7;
  public static final int LONGNAME = 8;
  public static final int PMODIDSEQ = 9;
  public static final int QRIDSEQ = 10;
  public static final int PQCIDSEQ = 11;
  public static final int CQCIDSEQ = 12;
  public static final int DISPLAYORDER = 13;
  public static final int RLNAME = 14;
  public static final int MODULE = 15;
  public static final int CONTEXT = 16;
  public static final int FORMVALIDVALUES = 17;
  public static final int QUESTIONFORMVVRECS = 18;

  /**
   * This is the default constructor (do not remove)
   */
  public QuestionsViewRowImpl() {
  }

  /**
   * Gets qc entity object.
   */
  public QuestContentsExtImpl getqc() {
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
   * Gets the attribute value for DE_IDSEQ using the alias name DeIdseq
   */
  public String getDeIdseq() {
    return (String) getAttributeInternal(DEIDSEQ);
  }

  /**
   * Sets <code>value</code> as attribute value for DE_IDSEQ using the alias
   * name DeIdseq
   */
  public void setDeIdseq(String value) {
    setAttributeInternal(DEIDSEQ, value);
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
   * Gets the attribute value for P_MOD_IDSEQ using the alias name PModIdseq
   */
  public String getPModIdseq() {
    return (String) getAttributeInternal(PMODIDSEQ);
  }

  /**
   * Sets <code>value</code> as attribute value for P_MOD_IDSEQ using the alias
   * name PModIdseq
   */
  public void setPModIdseq(String value) {
    setAttributeInternal(PMODIDSEQ, value);
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
      case DEIDSEQ:
        return getDeIdseq();
      case LONGNAME:
        return getLongName();
      case PMODIDSEQ:
        return getPModIdseq();
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
      case FORMVALIDVALUES:
        return getFormValidValues();
      case QUESTIONFORMVVRECS:
        return getQuestionFormVVRecs();
      case MODULE:
        return getModule();
      case CONTEXT:
        return getContext();
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
      case DEIDSEQ:
        setDeIdseq((String)value);
        return;
      case LONGNAME:
        setLongName((String)value);
        return;
      case PMODIDSEQ:
        setPModIdseq((String)value);
        return;
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
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }

  /**
   * Gets the associated <code>Row</code> using master-detail link Module
   */
  public oracle.jbo.Row getModule() {
    return (oracle.jbo.Row) getAttributeInternal(MODULE);
  }

  /**
   * Gets qr entity object.
   */
  public QcRecsExtImpl getqr() {
    return (QcRecsExtImpl) getEntity(1);
  }

  /**
   * Gets the attribute value for QR_IDSEQ using the alias name QrIdseq
   */
  public String getQrIdseq() {
    return (String) getAttributeInternal(QRIDSEQ);
  }

  public void setQrIdseq(String value) {
  }

  /**
   * Gets the attribute value for P_QC_IDSEQ using the alias name PQcIdseq
   */
  public String getPQcIdseq() {
    return (String) getAttributeInternal(PQCIDSEQ);
  }

  public void setPQcIdseq(String value) {
  }

  /**
   * Gets the attribute value for C_QC_IDSEQ using the alias name CQcIdseq
   */
  public String getCQcIdseq() {
    return (String) getAttributeInternal(CQCIDSEQ);
  }

  public void setCQcIdseq(String value) {
  }

  /**
   * Gets the attribute value for DISPLAY_ORDER using the alias name
   * DisplayOrder
   */
  public Number getDisplayOrder() {
    return (Number) getAttributeInternal(DISPLAYORDER);
  }

  public void setDisplayOrder(Number value) {
  }

  /**
   * Gets the attribute value for RL_NAME using the alias name RlName
   */
  public String getRlName() {
    return (String) getAttributeInternal(RLNAME);
  }

  public void setRlName(String value) {
  }

  /**
   * Gets the associated <code>RowIterator</code> using master-detail link
   * FormValidValues
   */
  public oracle.jbo.RowIterator getFormValidValues() {
    return (oracle.jbo.RowIterator) getAttributeInternal(FORMVALIDVALUES);
  }

  /**
   * Gets the associated <code>RowIterator</code> using master-detail link
   * QuestionFormVVRecs
   */
  public oracle.jbo.RowIterator getQuestionFormVVRecs() {
    return (oracle.jbo.RowIterator) getAttributeInternal(QUESTIONFORMVVRECS);
  }
/**
  public List getFormVVTranferObjects() {
    List formVVs = new ArrayList(25);
    oracle.jbo.RowIterator vvRows = getFormValidValues();

    while (vvRows.hasNext()) {
      formVVs.add(
        new BC4JFormValueTransferObject(
          (FormValidValuesViewRowImpl) vvRows.next()));
    }

    return formVVs;
  }
**/
  /**
   * 
   * Gets the associated <code>Row</code> using master-detail link Context
   */
  public oracle.jbo.Row getContext() {
    return (oracle.jbo.Row)getAttributeInternal(CONTEXT);
  }

  public Context getContextTransferObject() {
    Context conte =
      new BC4JContextTransferObject((ContextsViewRowImpl) getContext());

    return conte;
  }
}

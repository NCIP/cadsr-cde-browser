package gov.nih.nci.ncicb.cadsr.persistence.bc4j;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Date;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class ContextsViewRowImpl extends ViewRowImpl  {


  public static final int CONTEIDSEQ = 0;
  public static final int NAME = 1;
  public static final int LLNAME = 2;
  public static final int PALNAME = 3;
  public static final int DESCRIPTION = 4;
  public static final int LANGUAGE = 5;
  public static final int VERSION = 6;
  public static final int CREATEDBY = 7;
  public static final int DATECREATED = 8;
  public static final int MODIFIEDBY = 9;
  public static final int DATEMODIFIED = 10;
  public static final int DATAELEMENTSVIEW = 11;
  public static final int DATAELEMENTCONCEPTSROWS = 12;
  public static final int VALUEDOMAINSVIEW = 13;
  public static final int VDPVSVIEW = 14;
  public static final int CLASSIFICATIONSCHEMESVIEW = 15;
  public static final int QUESTCONTENTSEXTROWS = 16;
  public static final int OBJECTCLASSESEXTROWS = 17;
  public static final int PROPERTIESEXTROWS = 18;
  public static final int CONCEPTUALDOMAINSROWS = 19;
  public static final int DESIGNATIONSROWS = 20;
  public static final int PROTOCOLS = 21;
  protected static final int DATAELEMENTCONCEPTSVIEW = 12;
  /**
   * 
   * This is the default constructor (do not remove)
   */
  public ContextsViewRowImpl() {
  }

  /**
   * 
   * Gets Contexts entity object.
   */
  public ContextsImpl getContexts() {
    return (ContextsImpl)getEntity(0);
  }

  /**
   * 
   * Gets the attribute value for CONTE_IDSEQ using the alias name ConteIdseq
   */
  public String getConteIdseq() {
    return (String)getAttributeInternal(CONTEIDSEQ);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for CONTE_IDSEQ using the alias name ConteIdseq
   */
  public void setConteIdseq(String value) {
    setAttributeInternal(CONTEIDSEQ, value);
  }

  /**
   * 
   * Gets the attribute value for NAME using the alias name Name
   */
  public String getName() {
    return (String)getAttributeInternal(NAME);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for NAME using the alias name Name
   */
  public void setName(String value) {
    setAttributeInternal(NAME, value);
  }

  /**
   * 
   * Gets the attribute value for LL_NAME using the alias name LlName
   */
  public String getLlName() {
    return (String)getAttributeInternal(LLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for LL_NAME using the alias name LlName
   */
  public void setLlName(String value) {
    setAttributeInternal(LLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for PAL_NAME using the alias name PalName
   */
  public String getPalName() {
    return (String)getAttributeInternal(PALNAME);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for PAL_NAME using the alias name PalName
   */
  public void setPalName(String value) {
    setAttributeInternal(PALNAME, value);
  }

  /**
   * 
   * Gets the attribute value for DESCRIPTION using the alias name Description
   */
  public String getDescription() {
    return (String)getAttributeInternal(DESCRIPTION);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for DESCRIPTION using the alias name Description
   */
  public void setDescription(String value) {
    setAttributeInternal(DESCRIPTION, value);
  }

  /**
   * 
   * Gets the attribute value for LANGUAGE using the alias name Language
   */
  public String getLanguage() {
    return (String)getAttributeInternal(LANGUAGE);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for LANGUAGE using the alias name Language
   */
  public void setLanguage(String value) {
    setAttributeInternal(LANGUAGE, value);
  }

  /**
   * 
   * Gets the attribute value for VERSION using the alias name Version
   */
  public Number getVersion() {
    return (Number)getAttributeInternal(VERSION);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for VERSION using the alias name Version
   */
  public void setVersion(Number value) {
    setAttributeInternal(VERSION, value);
  }

  /**
   * 
   * Gets the attribute value for CREATED_BY using the alias name CreatedBy
   */
  public String getCreatedBy() {
    return (String)getAttributeInternal(CREATEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy
   */
  public void setCreatedBy(String value) {
    setAttributeInternal(CREATEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for DATE_CREATED using the alias name DateCreated
   */
  public Date getDateCreated() {
    return (Date)getAttributeInternal(DATECREATED);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for DATE_CREATED using the alias name DateCreated
   */
  public void setDateCreated(Date value) {
    setAttributeInternal(DATECREATED, value);
  }

  /**
   * 
   * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy
   */
  public String getModifiedBy() {
    return (String)getAttributeInternal(MODIFIEDBY);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy
   */
  public void setModifiedBy(String value) {
    setAttributeInternal(MODIFIEDBY, value);
  }

  /**
   * 
   * Gets the attribute value for DATE_MODIFIED using the alias name DateModified
   */
  public Date getDateModified() {
    return (Date)getAttributeInternal(DATEMODIFIED);
  }

  /**
   * 
   * Sets <code>value</code> as attribute value for DATE_MODIFIED using the alias name DateModified
   */
  public void setDateModified(Date value) {
    setAttributeInternal(DATEMODIFIED, value);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link DataElementsView
   */
  public RowIterator getDataElementsView() {
    return (RowIterator)getAttributeInternal(DATAELEMENTSVIEW);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link DataElementConceptsView
   */
  public RowIterator getDataElementConceptsView() {
    return (RowIterator)getAttributeInternal(DATAELEMENTCONCEPTSVIEW);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link ValueDomainsView
   */
  public RowIterator getValueDomainsView() {
    return (RowIterator)getAttributeInternal(VALUEDOMAINSVIEW);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link VdPvsView
   */
  public RowIterator getVdPvsView() {
    return (RowIterator)getAttributeInternal(VDPVSVIEW);
  }
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case CONTEIDSEQ:
        return getConteIdseq();
      case NAME:
        return getName();
      case LLNAME:
        return getLlName();
      case PALNAME:
        return getPalName();
      case DESCRIPTION:
        return getDescription();
      case LANGUAGE:
        return getLanguage();
      case VERSION:
        return getVersion();
      case CREATEDBY:
        return getCreatedBy();
      case DATECREATED:
        return getDateCreated();
      case MODIFIEDBY:
        return getModifiedBy();
      case DATEMODIFIED:
        return getDateModified();
      case DATAELEMENTSVIEW:
        return getDataElementsView();
      case DATAELEMENTCONCEPTSROWS:
        return getDataElementConceptsRows();
      case VALUEDOMAINSVIEW:
        return getValueDomainsView();
      case VDPVSVIEW:
        return getVdPvsView();
      case CLASSIFICATIONSCHEMESVIEW:
        return getClassificationSchemesView();
      case QUESTCONTENTSEXTROWS:
        return getQuestContentsExtRows();
      case OBJECTCLASSESEXTROWS:
        return getObjectClassesExtRows();
      case PROPERTIESEXTROWS:
        return getPropertiesExtRows();
      case CONCEPTUALDOMAINSROWS:
        return getConceptualDomainsRows();
      case DESIGNATIONSROWS:
        return getDesignationsRows();
      case PROTOCOLS:
        return getProtocols();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case CONTEIDSEQ:
        setConteIdseq((String)value);
        return;
      case NAME:
        setName((String)value);
        return;
      case LLNAME:
        setLlName((String)value);
        return;
      case PALNAME:
        setPalName((String)value);
        return;
      case DESCRIPTION:
        setDescription((String)value);
        return;
      case LANGUAGE:
        setLanguage((String)value);
        return;
      case VERSION:
        setVersion((Number)value);
        return;
      case CREATEDBY:
        setCreatedBy((String)value);
        return;
      case DATECREATED:
        setDateCreated((Date)value);
        return;
      case MODIFIEDBY:
        setModifiedBy((String)value);
        return;
      case DATEMODIFIED:
        setDateModified((Date)value);
        return;
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link ClassificationSchemesView
   */
  public RowIterator getClassificationSchemesView() {
    return (RowIterator)getAttributeInternal(CLASSIFICATIONSCHEMESVIEW);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link QuestContentsExtRows
   */
  public RowIterator getQuestContentsExtRows() {
    return (RowIterator)getAttributeInternal(QUESTCONTENTSEXTROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link ObjectClassesExtRows
   */
  public RowIterator getObjectClassesExtRows() {
    return (RowIterator)getAttributeInternal(OBJECTCLASSESEXTROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link PropertiesExtRows
   */
  public RowIterator getPropertiesExtRows() {
    return (RowIterator)getAttributeInternal(PROPERTIESEXTROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link DataElementConceptsRows
   */
  public RowIterator getDataElementConceptsRows() {
    return (RowIterator)getAttributeInternal(DATAELEMENTCONCEPTSROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link ConceptualDomainsRows
   */
  public RowIterator getConceptualDomainsRows() {
    return (RowIterator)getAttributeInternal(CONCEPTUALDOMAINSROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link DesignationsRows
   */
  public RowIterator getDesignationsRows() {
    return (RowIterator)getAttributeInternal(DESIGNATIONSROWS);
  }

  /**
   * 
   * Gets the associated <code>RowIterator</code> using master-detail link Protocols
   */
  public RowIterator getProtocols() {
    return (RowIterator)getAttributeInternal(PROTOCOLS);
  }
}
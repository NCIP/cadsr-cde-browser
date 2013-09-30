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

public class ContextsImpl extends EntityImpl 
{
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
  public static final int DATAELEMENTS = 11;
  public static final int DATAELEMENTCONCEPTS = 12;
  public static final int VALUEDOMAINS = 13;
  public static final int VDPVS = 14;
  public static final int ADMINISTEREDCOMPONENTS = 15;
  public static final int CLASSIFICATIONSCHEMES = 16;
  public static final int QUESTCONTENTSEXT = 17;
  public static final int OBJECTCLASSESEXT = 18;
  public static final int PROPERTIESEXT = 19;
  public static final int CONCEPTUALDOMAINS = 20;
  public static final int DESIGNATIONS = 21;
  public static final int PROTOCOL = 22;
  public static final int REPRESENTATION = 23;
  public static final int CONCEPTSEXT = 24;
  public static final int REFERENCEDOCUMENTS = 25;


















  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   * This is the default constructor (do not remove)
   */
  public ContextsImpl()
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
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.Contexts");
    }
    return mDefinitionObject;
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
   * Gets the attribute value for Name, using the alias name Name
   */
  public String getName()
  {
    return (String)getAttributeInternal(NAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Name
   */
  public void setName(String value)
  {
    setAttributeInternal(NAME, value);
  }

  /**
   * 
   * Gets the attribute value for LlName, using the alias name LlName
   */
  public String getLlName()
  {
    return (String)getAttributeInternal(LLNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for LlName
   */
  public void setLlName(String value)
  {
    setAttributeInternal(LLNAME, value);
  }

  /**
   * 
   * Gets the attribute value for PalName, using the alias name PalName
   */
  public String getPalName()
  {
    return (String)getAttributeInternal(PALNAME);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for PalName
   */
  public void setPalName(String value)
  {
    setAttributeInternal(PALNAME, value);
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
   * Gets the attribute value for Language, using the alias name Language
   */
  public String getLanguage()
  {
    return (String)getAttributeInternal(LANGUAGE);
  }

  /**
   * 
   * Sets <code>value</code> as the attribute value for Language
   */
  public void setLanguage(String value)
  {
    setAttributeInternal(LANGUAGE, value);
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
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception
  {
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
      case DATAELEMENTS:
        return getDataElements();
      case DATAELEMENTCONCEPTS:
        return getDataElementConcepts();
      case VALUEDOMAINS:
        return getValueDomains();
      case VDPVS:
        return getVdPvs();
      case ADMINISTEREDCOMPONENTS:
        return getAdministeredComponents();
      case CLASSIFICATIONSCHEMES:
        return getClassificationSchemes();
      case QUESTCONTENTSEXT:
        return getQuestContentsExt();
      case OBJECTCLASSESEXT:
        return getObjectClassesExt();
      case PROPERTIESEXT:
        return getPropertiesExt();
      case CONCEPTUALDOMAINS:
        return getConceptualDomains();
      case DESIGNATIONS:
        return getDesignations();
      case PROTOCOL:
        return getProtocol();
      case REPRESENTATION:
        return getRepresentation();
      case CONCEPTSEXT:
        return getConceptsExt();
      case REFERENCEDOCUMENTS:
        return getReferenceDocuments();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception
  {
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
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getDataElements()
  {
    return (RowIterator)getAttributeInternal(DATAELEMENTS);
  }


  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getDataElementConcepts()
  {
    return (RowIterator)getAttributeInternal(DATAELEMENTCONCEPTS);
  }


  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getValueDomains()
  {
    return (RowIterator)getAttributeInternal(VALUEDOMAINS);
  }


  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getVdPvs()
  {
    return (RowIterator)getAttributeInternal(VDPVS);
  }


  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getAdministeredComponents() {
    return (RowIterator)getAttributeInternal(ADMINISTEREDCOMPONENTS);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getClassificationSchemes() {
    return (RowIterator)getAttributeInternal(CLASSIFICATIONSCHEMES);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getQuestContentsExt() {
    return (RowIterator)getAttributeInternal(QUESTCONTENTSEXT);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getObjectClassesExt() {
    return (RowIterator)getAttributeInternal(OBJECTCLASSESEXT);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getPropertiesExt() {
    return (RowIterator)getAttributeInternal(PROPERTIESEXT);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getConceptualDomains() {
    return (RowIterator)getAttributeInternal(CONCEPTUALDOMAINS);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getDesignations() {
    return (RowIterator)getAttributeInternal(DESIGNATIONS);
  }

  /**
   * 
   * Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getProtocol() {
    return (RowIterator)getAttributeInternal(PROTOCOL);
  }


  /**
   * 
   *  Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getRepresentation()
  {
    return (RowIterator)getAttributeInternal(REPRESENTATION);
  }

  /**
   * 
   *  Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getConceptsExt()
  {
    return (RowIterator)getAttributeInternal(CONCEPTSEXT);
  }


  /**
   * 
   *  Gets the associated entity oracle.jbo.RowIterator
   */
  public RowIterator getReferenceDocuments()
  {
    return (RowIterator)getAttributeInternal(REFERENCEDOCUMENTS);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(String conteIdseq)
  {
    return new Key(new Object[] {conteIdseq});
  }












}
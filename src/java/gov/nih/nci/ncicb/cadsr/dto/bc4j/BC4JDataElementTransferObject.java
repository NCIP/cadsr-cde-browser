package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.DataElementsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.resource.DerivedDataElement;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;

import java.io.Serializable;

import java.sql.Date;
import java.sql.SQLException;

import java.util.List;


public class BC4JDataElementTransferObject extends AdminComponentTransferObject
  implements DataElement, Serializable {
  protected String deIdseq;
  protected String vdIdseq;
  protected String decIdseq;
  protected ValueDomain valueDomain;
  protected DataElementConcept dec;
  protected String vdName;
  protected String longCDEName;
  protected String cdeId;
  protected String contextName;
  protected String usingContexts = "";
  protected DerivedDataElement dde;
  protected List classifications;
  protected List otherVersions;

  public BC4JDataElementTransferObject() {
  }

  public BC4JDataElementTransferObject(
    DataElementsViewRowImpl dataElementsViewRowImpl) throws Exception {
    deIdseq = dataElementsViewRowImpl.getDeIdseq();
    idseq = deIdseq;
    preferredDefinition = dataElementsViewRowImpl.getPreferredDefinition();
    preferredName = dataElementsViewRowImpl.getPreferredName();
    longName = checkForNull(dataElementsViewRowImpl.getLongName());
    createdBy = dataElementsViewRowImpl.getCreatedBy();

    //createdDate = (Date)dataElementsViewRowImpl.getDateCreated().getData();
    modifiedBy = dataElementsViewRowImpl.getModifiedBy();

    //modifiedDate = (Date)(dataElementsViewRowImpl.getDateModified().getData());
    aslName = dataElementsViewRowImpl.getAslName();
    version = new Float(dataElementsViewRowImpl.getVersion().floatValue());
    deletedInd = checkForNull(dataElementsViewRowImpl.getDeletedInd());
    latestVerInd = checkForNull(dataElementsViewRowImpl.getLatestVersionInd());
    vdIdseq = dataElementsViewRowImpl.getVdIdseq();
    valueDomain = dataElementsViewRowImpl.getValueDomain();
    context = dataElementsViewRowImpl.getContext();
    conteIdseq = dataElementsViewRowImpl.getConteIdseq();
    contextName = context.getName();
    decIdseq = dataElementsViewRowImpl.getDecIdseq();
    longCDEName = checkForNull(dataElementsViewRowImpl.getLongCDEName());
    dec = dataElementsViewRowImpl.getDataElementConcept();
    cdeId = checkForNull(dataElementsViewRowImpl.getCDEId());
    refDocs = dataElementsViewRowImpl.getReferenceDocs();
    designations = dataElementsViewRowImpl.getDesignations();
    usingContexts = dataElementsViewRowImpl.getUsingContexts();

    if (dataElementsViewRowImpl.getCdeId() != null) {
      publicId = dataElementsViewRowImpl.getCdeId().intValue();
    }

    origin = checkForNull(dataElementsViewRowImpl.getOrigin());
    registrationStatus =
      checkForNull(dataElementsViewRowImpl.getRegistrationStatus());
  }

  public String getDeIdseq() {
    return deIdseq;
  }

  public String getVdIdseq() {
    return vdIdseq;
  }

  public String getDecIdseq() {
    return decIdseq;
  }

  public ValueDomain getValueDomain() {
    return valueDomain;
  }

  public String getVdName() {
    return valueDomain.getLongName();
  }

  public String getContextName() {
    //return context.getName();
    return contextName;
  }

  public String getLongCDEName() {
    return longCDEName;
  }

  public String getCDEId() {
    return cdeId;
  }

  public String getDecName() {
    return dec.getLongName();
  }

  public DataElementConcept getDataElementConcept() {
    return dec;
  }

  public String getUsingContexts() {
    return usingContexts;
  }

  //setter methods
  public void setVdIdseq(String pVdIdseq) {
    vdIdseq = pVdIdseq;
  }

  public void setDeIdseq(String pDeIdseq) {
    deIdseq = pDeIdseq;
    idseq = pDeIdseq;
  }

  public void setValueDomain(ValueDomain pValueDomain) {
    valueDomain = pValueDomain;
  }

  public void setDecIdseq(String pDecIdseq) {
    decIdseq = pDecIdseq;
  }
   //end method

  public void setDataElementConcept(DataElementConcept pDataElementConcept) {
    dec = pDataElementConcept;
  }
   //end method

  public void setLongCDEName(String pLongCDEName) {
    longCDEName = pLongCDEName;
  }

  public void setCDEId(String pCDEId) {
    cdeId = pCDEId;
    if (pCDEId != null && !"".equals(pCDEId))
      publicId = Integer.parseInt(pCDEId);
  }

  public void setContextName(String pConteName) {
    contextName = pConteName;
  }

  public void setUsingContexts(String usingContexts) {
    this.usingContexts = usingContexts;
  }

  public void setIdseq(String pDeIdseq) {
    deIdseq = pDeIdseq;
    idseq = pDeIdseq;
  }


   public void setDerivedDataElement(DerivedDataElement derivedDe) {
      this.dde = derivedDe;
   }


   public DerivedDataElement getDerivedDataElement() {
      return dde;
   }
   public List getClassifications()
   {
     return classifications;
   }
   public void setClassifications(List newClassifications)
   {
     classifications=newClassifications;
   }   
    public List getOtherVersions()
    {
        return otherVersions;
    }
    public void setOtherVersions(List deList)
    {
        otherVersions=deList;
    }  
    
    /**
     * This equals method only compares the Idseq to define equals
     * @param obj
     * @return 
     */  
    public boolean equals(Object obj)
    {
     if(obj == null)
      return false;
     if(!(obj instanceof DataElement))
      return false;
     DataElement cde = (DataElement)obj;

    if(this.getDeIdseq().equals(cde.getDeIdseq()))
        return true;
      else
        return false;
    }    
}

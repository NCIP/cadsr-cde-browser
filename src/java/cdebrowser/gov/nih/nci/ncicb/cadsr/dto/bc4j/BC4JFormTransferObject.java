package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import java.io.Serializable;
import java.util.List;
import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.FormsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Form;

public class BC4JFormTransferObject extends AdminComponentTransferObject 
                          implements Form,Serializable  {
  protected String formIdseq;
  protected String formType;
  protected List modules;
  protected String protoIdseq;
  private FormsViewRowImpl formRow;

  public BC4JFormTransferObject() {
    super();
    idseq = formIdseq;
  }
  
  public BC4JFormTransferObject(FormsViewRowImpl formRow ) {
    super();
    this.formRow = formRow;
    formIdseq = formRow.getQcIdseq();
    idseq = formIdseq;
    formType = formRow.getQcdlName();
    preferredDefinition = formRow.getPreferredDefinition();
		preferredName = formRow.getPreferredName();
		longName = checkForNull(formRow.getLongName());
    conteIdseq = formRow.getConteIdseq();
		//createdBy = formRow.getCreatedBy();
		//createdDate = (Date)dataElementsViewRowImpl.getDateCreated().getData();
		//modifiedBy = formRow.getModifiedBy();
		//modifiedDate = (Date)(dataElementsViewRowImpl.getDateModified().getData());
		aslName = formRow.getAslName();
		version = new Float(formRow.getVersion().floatValue());
		deletedInd = checkForNull(formRow.getDeletedInd());
		latestVerInd = checkForNull(formRow.getLatestVersionInd());
    protoIdseq = checkForNull(formRow.getProtoIdseq());
    modules = formRow.getModuleTranferObjects();
    context = formRow.getContextTransferObject();
  }

  public String getFormIdseq() {
    return formIdseq;
  }

  public void setFormIdseq(String idseq) {
    formIdseq = idseq;
  }

  public String getFormType() {
    return formType;
  }

  public void setFormType(String formType) {
    this.formType = formType;
  }

  public Protocol getProtocol() {
    return null;
  }

  public void setProtocol(Protocol protocol){
  }
  

  public List getModules() {
    return modules;
  }

  public String getProtoIdseq() {
    return protoIdseq;
  }
  public void setProtoIdseq(String idseq) {
    protoIdseq = idseq;
  }

  public void setModules(List blocks) {
  }

  public String getFormCategory() {
    return null;
  }

  public void setFormCategory(String formCategory) {
  }
}
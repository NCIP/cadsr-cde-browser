package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.sql.Date;

import java.util.List;


public class FormTransferObject extends AdminComponentTransferObject
  implements Form {
  private Protocol protocol = null;
  private String formType = null;
  private List modules;

  public FormTransferObject() {
  }

  public String getFormIdseq() {
    return null;
  }

  public void setFormIdseq(String p0) {
  }

  public String getFormType() {
    return formType;
  }

  public void setFormType(String newFormType) {
    formType = newFormType;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public void setProtocol(Protocol newProtocol) {
    this.protocol = newProtocol;
  }

  public String getProtoIdseq() {
    return null;
  }

  public void setProtoIdseq(String p0) {
  }

  public List getModules() {
    return modules;
  }

  public void setModules(List p0) {
    modules = p0;
  }
}

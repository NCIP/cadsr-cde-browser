package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.sql.Date;

import java.util.Iterator;
import java.util.List;


public class FormTransferObject extends AdminComponentTransferObject
  implements Form {
  private Protocol protocol = null;
  private String formType = null;
  private List modules;
  private String formIdseq = null;

  public FormTransferObject() {
  }

  public String getFormIdseq() {
    return formIdseq;
  }

  public void setFormIdseq(String formIdseq) {
    this.formIdseq = formIdseq;
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
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"formIdseq="+getFormIdseq());
    sb.append(ATTR_SEPARATOR+"formType="+getFormType());
    Protocol protocol = getProtocol();
    if(protocol!=null)
      sb.append(ATTR_SEPARATOR+"Protocol="+protocol.toString());
    else
      sb.append(ATTR_SEPARATOR+"Protocol=null");

    List modules = getModules();
    if(modules!=null) 
    {      
      sb.append(ATTR_SEPARATOR+"Modules="+modules);
    } 
    else
    {
      sb.append(ATTR_SEPARATOR+"Modules="+null);
    }
    sb.append(OBJ_SEPARATOR_END);
    return sb.toString();
  }
}

package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;

public class FormTransferObject extends AdminComponentTransferObject implements Form 
{

  Protocol protocol = null;
  String formType = null;
  
  public FormTransferObject()
  {
  }

  public String getFormIdseq()
  {
    return null;
  }

  public void setFormIdseq(String p0)
  {
  }

  public String getFormType()
  {
    return formType;
  }

  public void setFormType(String newFormType)
  {
    formType=newFormType;
  }

  public Protocol getProtocol()
  {
    return protocol;
  }
  
  public void setProtocol(Protocol newProtocol)
  {
    this.protocol=newProtocol;
  }  

  public String getProtoIdseq()
  {
    return null;
  }

  public void setProtoIdseq(String p0)
  {
  }

  public List getModules()
  {
    return null;
  }

  public void setModules(List p0)
  {
  }

}
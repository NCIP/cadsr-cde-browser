package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Context;

public class ContextTransferObject extends AdminComponentTransferObject implements Context
{
  private String conteIdSeq = null;
  private String name = null;

  public ContextTransferObject()
  {
  }

  public ContextTransferObject(String name)
  {
	  this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getConteIdseq() {
    return conteIdSeq;
  }

  public void setConteIdseq(String newConteIdseq) {
    conteIdSeq = newConteIdseq;
  }

  public String getLlName()
  {
    return null;
  }

  public void setLlName(String p0)
  {
  }

  public String getPalName()
  {
    return null;
  }

  public void setPalName(String p0)
  {
  }

  public String getDescription()
  {
    return null;
  }

  public void setDescription(String p0)
  {
  }

  public String getLanguage()
  {
    return null;
  }

  public void setLanguage(String p0)
  {
  }
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"longName="+getLongName());
    sb.append(ATTR_SEPARATOR+"conteIdSeq="+getConteIdseq());   
    sb.append(OBJ_SEPARATOR_END);
    sb.toString();
    return sb.toString();
  }  
}
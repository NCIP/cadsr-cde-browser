package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Module;

public class ModuleTransferObject extends AdminComponentTransferObject implements Module
{
  Form crf;
  List terms;
  String moduleIdseq;
  int dispOrder;
  
  public ModuleTransferObject()
  {
  }

  public String getModuleIdseq()
  {
    return moduleIdseq;
  }

  public void setModuleIdseq(String idseq)
  {
    this.moduleIdseq = idseq;
  }

  public Form getForm()
  {
    return crf;
  }
  
  public void setForm(Form crf)
  {
    this.crf = crf;
  }  

  public List getQuestions()
  {
    return terms;
  }

  public void setQuestions(List terms)
  {
    this.terms = terms;
  }

  public int getDisplayOrder()
  {
    return dispOrder;
  }
  
  public void setDisplayOrder(int dispOrder)
  {
    this.dispOrder = dispOrder;
  }

}
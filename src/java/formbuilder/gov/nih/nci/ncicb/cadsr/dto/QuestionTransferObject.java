package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Question;

public class QuestionTransferObject extends AdminComponentTransferObject implements Question
{
  Form crf;
  Module module;
  List validValues;
  String quesIdseq;
  int dispOrder;

  public QuestionTransferObject()
  {
  }
  
  public String getQuesIdseq()
  {
    return quesIdseq;
  }

  public void setQuesIdseq(String idseq)
  {
    this.quesIdseq = idseq;
  }

  public Module getModule()
  {
    return module;
  }
  
  public void setModule(Module module)
  {
    this.module = module;
  }  

  public Form getForm()
  {
    return crf;
  }
  
  public void setForm(Form crf)
  {
    this.crf = crf;
  }
  
  public List getValidValues()
  {
    return validValues;
  }

  public void setValidValues(List validValues)
  {
    this.validValues = validValues;
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
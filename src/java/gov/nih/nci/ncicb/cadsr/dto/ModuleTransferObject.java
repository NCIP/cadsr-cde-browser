package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import gov.nih.nci.ncicb.cadsr.util.DebugStringBuffer;
import java.sql.Date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class ModuleTransferObject extends AdminComponentTransferObject
  implements Module {
  private Form crf;
  private List terms;
  private String moduleIdseq;
  private int dispOrder;
  private Collection instructions;

  public ModuleTransferObject() {
    idseq = moduleIdseq;
  }

  public String getModuleIdseq() {
    return moduleIdseq;
  }

  public void setModuleIdseq(String idseq) {
    this.moduleIdseq = idseq;
  }

  public Form getForm() {
    return crf;
  }

  public void setForm(Form crf) {
    this.crf = crf;
  }

  public List getQuestions() {
    return terms;
  }

  public void setQuestions(List terms) {
    this.terms = terms;
  }

  public int getDisplayOrder() {
    return dispOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.dispOrder = dispOrder;
  }
  
  public Collection getInstructions()
  {
    return instructions;
  }
  public void setInstructions(Collection newInstructions)
  {
    instructions=newInstructions;
  }
  
  /**
   * Clones the object
   * Makes a deep copy of the Questions
   * @return 
   */
  public Object clone() throws CloneNotSupportedException {
    Module copy = null;
      copy = (Module)super.clone();
      // make the copy a little deeper
     if(getQuestions()!=null)
     {
       List questionsCopy = new ArrayList();
       ListIterator it = getQuestions().listIterator();
       while(it.hasNext())
       {
         Question question = (Question)it.next();
         Question questionClone = (Question)question.clone();
         questionClone.setModule(copy);
         questionsCopy.add(questionClone);        
       }
       copy.setQuestions(questionsCopy);
       copy.setForm(null);
       copy.setQuestions(questionsCopy);
     }     
      return copy;
  }
  public String toString()
  {
    DebugStringBuffer sb = new DebugStringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"moduleIdseq="+getModuleIdseq(),getModuleIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    List questions = getQuestions();
    if(questions!=null) 
    {      
      sb.append(ATTR_SEPARATOR+"Questions="+questions);
    } 
    else
    {
      sb.append(ATTR_SEPARATOR+"Questions="+null);
    }    
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();
  }  
  
  public static void main(String args[]) throws Exception
  {

    Module module = new ModuleTransferObject();
    module.setLongName("Module");
   
    
    Module clone = (Module)module.clone();
    module.setLongName("ChangedModule");  

  
    
    System.out.println(module);
    System.out.println(clone);
    
  }  
}

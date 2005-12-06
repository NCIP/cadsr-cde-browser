package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FormActionUtil 
{
  public FormActionUtil()
  {
  }
  
  /**
   * increments the display order of Elements by 1 starting from "startIndex" of
   * the list
   *
   * @param List of Orderable elements
   * @param startIndex
   */
  public static void incrementDisplayOrder(
    List list,
    int startIndex) {
    ListIterator iterate = list.listIterator(startIndex);

    while (iterate.hasNext()) {
      Orderable orderableObj = (Orderable) iterate.next();
      int displayOrder = orderableObj.getDisplayOrder();
      if(displayOrder >++displayOrder)
        return;
      orderableObj.setDisplayOrder(++displayOrder);
    }
  }

  /**
   * Decrments the display order of Elements by 1 starting from "startIndex" of
   * the list
   *
   * @param List of Orderable elements
   * @param startIndex
   */
  public static void decrementDisplayOrder(
    List list,
    int startIndex) {
    ListIterator iterate = list.listIterator(startIndex);

    while (iterate.hasNext()) {
      Orderable orderable = (Orderable) iterate.next();
      int displayOrder = orderable.getDisplayOrder();
      if(displayOrder < --displayOrder)
        return;      
      orderable.setDisplayOrder(--displayOrder);
    }
  }

  public static void setInitDisplayOrders(List orderables)
  {
    if(orderables==null) return;
    int displayOrder = 0;
    for (Iterator it = orderables.iterator(); it.hasNext();) {
        Orderable element = (Orderable)it.next();
        element.setDisplayOrder(displayOrder);
        displayOrder++;
    }
  }  
    public static void removeAllIdSeqs(Module module)
    {
        module.setIdseq(null);
        module.setModuleIdseq(null);
        if(module.getInstruction()!=null)
        {
            module.getInstruction().setIdseq(null);
        }
            
        List qs = module.getQuestions();
        if(qs==null)
            return;
        for (Iterator it = qs.iterator(); it.hasNext();) {
            Question element = (Question)it.next();
            removeAllIdSeqs(element);
        }         
        return;        
    }  
    public static void  removeAllIdSeqs(Question question)
    {
        question.setIdseq(null);
        question.setQuesIdseq(null);
        if(question.getInstruction()!=null)
            question.getInstruction().setIdseq(null);
        List vvs = question.getValidValues();
        if(vvs==null)
            return;
        for (Iterator it = vvs.iterator(); it.hasNext();) {
            FormValidValue element = (FormValidValue)it.next();
            element.setIdseq(null);
            element.setValueIdseq(null);
            if(element.getInstruction()!=null)
                element.getInstruction().setIdseq(null);
        }         
        return;
    }      
}
package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;

import gov.nih.nci.ncicb.cadsr.resource.ValidValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FormActionUtil 
{
    private static final String PROTOCOL = "protocol";
    private static final String CSI = "CSI";
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


    public static List<TriggerActionChanges> findFormSkipPatternByProtocol(Form crf, String protocolId){
        //check module first
        List modules = crf.getModules();
        List retList = new ArrayList();
        if (modules!=null && !modules.isEmpty()){
            Iterator it = modules.iterator();
            while (it.hasNext()){
                Module module = (Module)it.next();
                retList.addAll(findModuleSkipPattern(module, protocolId, PROTOCOL));
                retList.addAll(findModuleQuestionSkipPattern(module, protocolId, PROTOCOL));
            }    
        }
        return retList;
    }
    public static List<TriggerActionChanges> findFormSkipPatternByClassification(Form crf, String acCsiId){
        //check module first
        List modules = crf.getModules();
        List retList = new ArrayList();
        if (modules!=null && !modules.isEmpty()){
            Iterator it = modules.iterator();
            while (it.hasNext()){
                Module module = (Module)it.next();
                retList.addAll(findModuleSkipPattern(module, acCsiId, CSI));
                retList.addAll(findModuleQuestionSkipPattern(module, acCsiId, CSI));
            }    
        }
        return retList;
    }
    
    public static List<TriggerActionChanges> findModuleSkipPattern(Module module, String id, String type){
        List retList = new ArrayList();
        List triggers = module.getTriggerActions();
        if (triggers!=null && !triggers.isEmpty()){
            Iterator it = triggers.iterator();
            while (it.hasNext()){
                TriggerAction trigger = (TriggerAction)it.next();
                if (PROTOCOL.equals(type)){
                    if (hasProtocol(trigger.getProtocols(), id)){
                        retList.add(makeTriggerActionChanges(trigger.getIdSeq(), id, type));
                    }
                }    
                if (CSI.equals(type)){
                    String acCsiId = getAcCsiId(trigger.getClassSchemeItems(), id);
                    if (acCsiId !=null){
                        retList.add(makeTriggerActionChanges(trigger.getIdSeq(), acCsiId, type));
                    }
                }    
            }    
        }
        return retList;
    }
    
    public static  TriggerActionChanges makeTriggerActionChanges(String trigerId, String id, String type){
        TriggerActionChanges changes = new TriggerActionChangesTransferObject();
        changes.setTriggerActionId(trigerId);
        
        //protocols
        if (PROTOCOL.equals(type)){
            List protocols = new ArrayList();
            protocols.add(id);
            changes.setDeleteProtocols(protocols );
        }    
        if (CSI.equals(type)){
            List csiList = new ArrayList();
            csiList.add(id);
            changes.setDeleteCsis(csiList);
        }    
        return changes;
    }
    
    public static List<TriggerActionChanges> findModuleQuestionSkipPattern(Module module, String id, String type){
        List retList = new ArrayList();
        List questions = module.getQuestions();
        if (questions!=null && !questions.isEmpty()){
            Iterator it = questions.iterator();
            while (it.hasNext()){                
                retList.addAll(findQuestionSkipPattern((Question)it.next(), id, type));                    
            }    
        }
        return retList;
    }

    public static List<TriggerActionChanges> findQuestionSkipPattern(Question question, String id, String type){
        List retList = new ArrayList();
        /*
        List triggers = question.getTriggerActions();
        if (triggers!=null && !triggers.isEmpty()){
            Iterator it = triggers.iterator();
            while (it.hasNext()){   
                TriggerAction trigger = (TriggerAction)it.next();
                if (hasProtocol(trigger.getProtocols(), protocolId)){
                    retList.add(makeTriggerActionChanges(trigger.getIdSeq(), protocolId));    
                }
            }    
        }*/
        List vvList = question.getValidValues();
        if (vvList!=null && !vvList.isEmpty()){
            Iterator it = vvList.iterator();
            while (it.hasNext()){   
                FormValidValue vv = (FormValidValue)it.next();
                List<TriggerAction> triggers = (List<TriggerAction>)vv.getTriggerActions();
                if (triggers==null || triggers.isEmpty()){
                    continue;
                }
                Iterator it1 = triggers.iterator();
                while (it1.hasNext()){
                    TriggerAction trigger = (TriggerAction)it1.next();
                    if (CSI.equals(type)){
                        String acCsiId = getAcCsiId(trigger.getClassSchemeItems(), id);
                        if (acCsiId != null){
                            retList.add(makeTriggerActionChanges(trigger.getIdSeq(), acCsiId, type));    
                        }    
                    }
                    if (PROTOCOL.equals(type)){
                        if (hasProtocol(trigger.getProtocols(), id)){
                            retList.add(makeTriggerActionChanges(trigger.getIdSeq(), id, type));    
                        }    
                    }
                    
                }
            }    
        }
        return retList;
    }
    public static boolean hasProtocol(List<Protocol> protocols, String protocolId){
        if (protocols == null || protocols.isEmpty()){
            return false;
        }
        
        Iterator it = protocols.iterator();
        while (it.hasNext()){
            Protocol p = (Protocol)it.next();
            if (p.getProtoIdseq().equals(protocolId)){
                return true;
            }
        }
        return false;
    }
    
    public static String getAcCsiId(List<ClassSchemeItem> csiList, String csCsiId){
        if (csiList == null || csiList.isEmpty()){
            return null;
        }
        
        Iterator it = csiList.iterator();
        while (it.hasNext()){
            ClassSchemeItem csi = (ClassSchemeItem)it.next();
            if (csi.getCsCsiIdseq().equals(csCsiId)){
                return csi.getAcCsiIdseq();
            }
        }
        return null;
    }
    
    public static void  updateSkipPatternInSession(Form crf, List<TriggerActionChanges> associatedTriggers){
        //TODO        
        return;
    }
    
    public static List<TriggerActionChanges> findFormSkipPatternForByClassificiation(Form crf, String cscsiId){
        //check module first
        List modules = crf.getModules();
        List retList = new ArrayList();
        if (modules!=null && !modules.isEmpty()){
            Iterator it = modules.iterator();
            while (it.hasNext()){
                Module module = (Module)it.next();
                retList.addAll(findModuleSkipPattern(module, cscsiId, CSI));
                retList.addAll(findModuleQuestionSkipPattern(module, cscsiId, CSI));
            }    
        }
        return retList;
    }



}

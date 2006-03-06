package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;

import gov.nih.nci.ncicb.cadsr.resource.ValidValue;

import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
  /**
     * Creates repition of module looking at the questionRepititions and
     * Also set defaults values for each repitition
     * @param module
     * @return
     */
    public static List<Module> getRepetitions(Module module)
    {
        List<Module> moduleRepeats = new ArrayList<Module>();
        List questions = module.getQuestions();
        if (questions == null)
            return moduleRepeats;
        if (module.getNumberOfRepeats() == 0)
            return moduleRepeats;

        Map<Integer, Map<String, QuestionRepitition>> questionRepMap =
            getQuestionRepititionsMap(questions);
        for (int i = 0; i < module.getNumberOfRepeats(); ++i)
        {
            Module tempModuleClone = null;
            try
            {
                tempModuleClone = (Module)module.clone();
            } catch (Exception e)
            {
                throw new RuntimeException("Error Cloning Module",e);
            }
            Map<String, QuestionRepitition> qMap =
                questionRepMap.get(new Integer(i + 1));
            if (qMap == null)
              {
                moduleRepeats.add(tempModuleClone);
                continue;
              }

            setDefaults(qMap, tempModuleClone);
            moduleRepeats.add(tempModuleClone);
        }
        return moduleRepeats;
    }

    /**
     *
     * @param questions
     * @return a Map <repeantSequence#,Map<questionId,QuestionRepitition>>
     */
    public static Map getQuestionRepititionsMap(List questions)
    {
        Iterator qIt = questions.iterator();
        Map<Integer, Map<String, QuestionRepitition>> questionRepMap =
            new HashMap<Integer, Map<String, QuestionRepitition>>();
        while (qIt.hasNext())
        {
            Question q = (Question)qIt.next();
            List<QuestionRepitition> questionRepList =
                q.getQuestionRepititions();
            if (questionRepList == null)
                continue;

            for (QuestionRepitition repitition : questionRepList)
            {
                Map<String, QuestionRepitition> qMap =
                    questionRepMap.get(new Integer(repitition.getRepeatSequence()));
                if (qMap == null)
                {
                    Map<String, QuestionRepitition> tempQMap =
                        new HashMap<String, QuestionRepitition>();
                    tempQMap.put(q.getQuesIdseq(), repitition);
                    questionRepMap
                    .put(new Integer(repitition.getRepeatSequence()),
                                       tempQMap);

                } else
                {
                    qMap.put(q.getQuesIdseq(), repitition);
                }

            }
        }
        return questionRepMap;
    }

    public static void setDefaults(Map<String, QuestionRepitition> qMap,
                             Module module)
    {
        List questions = module.getQuestions();
        ListIterator qIt = questions.listIterator();
        while (qIt.hasNext())
        {
            Question q = (Question)qIt.next();
            QuestionRepitition qRep = qMap.get(q.getQuesIdseq());
            if(qRep!=null)
            {
                q.setDefaultValue(qRep.getDefaultValue());
                q.setDefaultValidValue(qRep.getDefaultValidValue());
            }
        }
    }

        /** return possible trigger action target
	     */
	   public static Map<String,FormElement> getTriggerActionPossibleTargetMap(Form form){
		   if (form == null){
			   return null;
	  	   }

	       Map<String,FormElement> possibleTargets = new HashMap<String,FormElement>();
	       List modules = form.getModules();
	       if (modules == null || modules.isEmpty()){
	           return null;
	       }

	       Iterator mIter = modules.iterator();
	       while (mIter.hasNext())
	       {
	            //module itself
	           Module block = (Module)mIter.next();
	           block.setForm(form);
	           possibleTargets.put(block.getModuleIdseq(), block);

	            //questoins in this module
	           List questions = block.getQuestions();
	           if (questions == null || questions.isEmpty()){
	               continue;
	           }

	           Iterator qIter = questions.iterator();

	           while (qIter.hasNext())
	           {
	               Question term = (Question)qIter.next();
	               term.setModule(block);
	               String termId = term.getQuesIdseq();
	               possibleTargets.put(termId,term); //one of the possible targets
	           }
	       }//end of while
	       return possibleTargets;
	   }
           

    public static List<TriggerAction> getModuleAllTriggerActions(Module module){
        List<TriggerAction> moduleTriggerActions = module.getTriggerActions();
        
        List questions = module.getQuestions();
        if (questions ==null || questions.isEmpty()){
            return moduleTriggerActions;
        }
        
        List<TriggerAction> allActions = new ArrayList(moduleTriggerActions);
        Iterator qIter = questions.iterator();
        while (qIter.hasNext()) {
            Question term = (Question)qIter.next();
            List validValues = term.getValidValues();
            if (validValues == null || validValues.isEmpty()){
                continue;
            }
            Iterator vvIter = validValues.iterator();    
            while (vvIter.hasNext())
            {
                FormValidValue vv = (FormValidValue)vvIter.next();
               //Set Skip Patterns
               List<TriggerAction> vvActions= vv.getTriggerActions();
               if (vvActions==null || vvActions.isEmpty()){
                   continue;
               }
               allActions.addAll(vvActions);
           }
        }
        return allActions;
    }        
    
    static public void setTargetsForTriggerActions(Map<String,FormElement> possibleTargetMap, List<TriggerAction> actions)
    {
        for(TriggerAction action : actions)
        {
            FormElement element = possibleTargetMap.get(action.getActionTarget().getIdseq());
            //In this case just keep the idseq and fire a warning
            if (element!=null){
                action.setActionTarget(element);
            }else{
                System.err.println("could not find the target FormElement for idseq = " + action.getActionTarget().getIdseq());
            }

        }
    }

}

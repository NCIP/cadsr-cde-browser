
package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.FormInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionRepititionTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;


public class ModuleRepetitionAction extends FormBuilderSecureBaseDispatchAction
{


    public ActionForward getModuleToRepeat(ActionMapping mapping,
                                           ActionForm editForm,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException,
                                                                                                            ServletException
    {
        Form crf = null;
        DynaActionForm dynaForm = (DynaActionForm)editForm;
        Integer moduleIndex = (Integer)dynaForm.get(MODULE_INDEX);
        crf = (Form)getSessionObject(request, CRF);
        List modules = crf.getModules();
        Module selectedModule = (Module)modules.get(moduleIndex.intValue());
        selectedModule.setForm(crf);
        List<Module> modRepetitions = FormActionUtil.getRepetitions(selectedModule);
        setQuestionDefaultArrays(dynaForm, modRepetitions, selectedModule);

        setSessionObject(request, MODULE, selectedModule, true);
        setSessionObject(request, MODULE_REPETITIONS, modRepetitions, true);
        dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0)); 

        return mapping.findForward("viewRepetitions");
    }

    public ActionForward addRepetitions(ActionMapping mapping,
                                        ActionForm editForm,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws IOException,
                                                                                                         ServletException
    {
        Form crf = null;
        DynaActionForm dynaForm = (DynaActionForm)editForm;
        Integer numberOfRepetitions = null;
        numberOfRepetitions =
            (Integer)dynaForm.get(NUMBER_OF_MODULE_REPETITIONS);
        List<Module> currRepeats =
            (List<Module>)getSessionObject(request, MODULE_REPETITIONS);
        String[] defaultValueArr = (String[])dynaForm.get(QUESTION_DEFAULTS);
        String[] defaultValueIds =
            (String[])dynaForm.get(QUESTION_DEFAULT_VV_IDS);


        if (numberOfRepetitions < 1)
        {
            saveError("cadsr.formbuilder.module.repetition.add.invalidCount",
                      request);
            return mapping.findForward("viewRepetitions");
        }


        Module module = (Module)getSessionObject(request, MODULE);
        List<Module> newReps = getRepetitions(module, numberOfRepetitions);
        currRepeats.addAll(newReps);

        addToQuestionDefaultArrays(dynaForm, numberOfRepetitions, module);
        // adds both Defauts and DefaultVVIds

         dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0)); 
        setSessionObject(request, MODULE_REPETITIONS, currRepeats, true);
        saveMessage("cadsr.formbuilder.module.repetition.add.success",
                    request);
        return mapping.findForward("viewRepetitions");
    }

    public ActionForward deleteRepetitions(ActionMapping mapping,
                                           ActionForm deleteForm,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException,
                                                                                                            ServletException
    {
        DynaActionForm dynaForm = (DynaActionForm)deleteForm;
        List<Module> moduleList =
            (List<Module>)getSessionObject(request, MODULE_REPETITIONS);

        String[] selectedIndexes =
            (String[])request.getParameterValues(SELECTED_ITEMS);
            
        if(selectedIndexes==null)
            return mapping.findForward("viewRepetitions");
            
        for (int i = selectedIndexes.length - 1; i > -1; --i)
        {
            int currIndex = (new Integer(selectedIndexes[i])).intValue();
            if ((moduleList != null) && (moduleList.size() > 0))
            {
                moduleList.remove(currIndex);
            }
        }
        dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0)); 
        setQuestionDefaultsAsArray(moduleList,dynaForm);
        saveMessage("cadsr.formbuilder.module.repetition.delete.success",
                    request);
        return mapping.findForward("viewRepetitions");
    }




    /**
     * View Modules in the module list
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     *
     * @return
     *
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward doneManageRepetitions(ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws IOException,
                                                                                                                ServletException
    {

        DynaActionForm dynaForm = (DynaActionForm)form;
        removeSessionObject(request, MODULE);
        removeSessionObject(request, MODULE_REPETITIONS);
        dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0));        

        return mapping.findForward(SUCCESS);
    }
    
    /**
     * Set Session Object to show repitions in edit and details screens
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     *
     * @return
     *
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward showRepetitions(ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws IOException,
                                                                                                                ServletException
    {

        setSessionObject(request, SHOW_MODULE_REPEATS,"True");
        
        return mapping.findForward(SUCCESS);
    }    
    
    /**
     * Remove Session Object to hide repetitions in edit and details screens
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     *
     * @return
     *
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward hideRepetitions(ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws IOException,
                                                                                                                ServletException
    {

        removeSessionObject(request, SHOW_MODULE_REPEATS);
        return mapping.findForward(SUCCESS);
    }     

    /**
     * Save Repititions
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     *
     * @return
     *
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward saveRepetitions(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException,
                                                                                                          ServletException
    {

        DynaActionForm dynaForm = (DynaActionForm)form;
        Integer index = (Integer)dynaForm.get(MODULE_INDEX);
        Form crf = (Form) getSessionObject(request, CRF);
        Form orgCrf = (Form) getSessionObject(request, this.CLONED_CRF);
        
        String[] defaultValueArr = (String[])dynaForm.get(QUESTION_DEFAULTS);
        String[] defaultValueIds =
            (String[])dynaForm.get(QUESTION_DEFAULT_VV_IDS);
        List<Module> repeats =
            (List<Module>)getSessionObject(request, MODULE_REPETITIONS);
        Module module = (Module)getSessionObject(request, MODULE);
        
        Map<String,List<QuestionRepitition>> questionRepeatMap =null;
        List<String> noRepQIdList = new ArrayList<String>();
        if(repeats.size()<1&&module.getNumberOfRepeats()<1)
        {
            saveMessage("cadsr.formbuilder.module.repetition.nochange.success",
                        request);
            removeSessionObject(request, MODULE);
            removeSessionObject(request, MODULE_REPETITIONS);
            dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0));                            
            return mapping.findForward(SUCCESS);             
        }
        int numberOfRepeats = 0;
        if(!haveQuestions(module))
        {
            numberOfRepeats = repeats.size();
        }
         else if(doesQuestionsHaveRepeats(module)&&repeats.isEmpty())
        {
            noRepQIdList = getQuestionIdsWithRepeats(module);
            numberOfRepeats = 0;
        }
        else
        {
            List<String[]> defaultArrList = getArrayByRepitition(defaultValueArr,module.getQuestions().size());
            List<String[]> defaultArrIdList = getArrayByRepitition(defaultValueIds,module.getQuestions().size());
            numberOfRepeats = repeats.size();
            questionRepeatMap = getQuestionRepeatMap(module,defaultArrList,defaultArrIdList,noRepQIdList);
        }
        FormBuilderServiceDelegate service = getFormBuilderService();
        Module savedModeule = null;
        try
        {
            savedModeule = service.saveQuestionRepititons(module.getModuleIdseq(),repeats.size(),questionRepeatMap,noRepQIdList);
            //put the triggeractions' target. 
            //Only the idseq is put in the triggeraction target by the EJB, not the target object.
            FormActionUtil.setTargetsForTriggerActions(FormActionUtil.getTriggerActionPossibleTargetMap(crf),FormActionUtil.getModuleAllTriggerActions(savedModeule));
        }
        catch (FormBuilderException e)
        {
            if (log.isDebugEnabled()) {
              log.debug("Exception saving question repitition  " + e);
            }
            saveError(ERROR_SAVE_QUESTION_REPITITON, request);
            saveError(e.getErrorCode(), request);
            return mapping.findForward(FAILURE);        
        }
        crf.getModules().remove(index.intValue());
        crf.getModules().add(index.intValue(),savedModeule);
        orgCrf.getModules().remove(index.intValue());
        try{
        Module newClonedModule = (Module)savedModeule.clone();
        orgCrf.getModules().add(index.intValue(),newClonedModule);
        }
        catch (CloneNotSupportedException clexp) {
        saveError(ERROR_SAVE_QUESTION_REPITITON, request);
         if (log.isErrorEnabled()) {
           log.error("Exception while cloning module  " + savedModeule,clexp);
         }
         return mapping.findForward(FAILURE);
        }
        
        saveMessage("cadsr.formbuilder.module.repetition.save.success",
                    request);
        removeSessionObject(request, MODULE);
        removeSessionObject(request, MODULE_REPETITIONS);
        dynaForm.set(NUMBER_OF_MODULE_REPETITIONS,new Integer(0));                        
        return mapping.findForward(SUCCESS); 
    }
    
   private Map<String,List<QuestionRepitition>> getQuestionRepeatMap(Module module,List<String[]> defaultArrList,List<String[]> defaultArrIdList,
                List<String> noRepQIdList)
   {
       Map<String,List<QuestionRepitition>> map = new HashMap<String,List<QuestionRepitition>>();

       if(module.getQuestions()==null)
        return map;
       
       
       int repeatNumber = defaultArrList.size();
       for(int j=0;j<repeatNumber;++j)
       {
          String[] defautArr = defaultArrList.get(j);
          String[] defautIdArr = defaultArrIdList.get(j);
          Iterator it = module.getQuestions().iterator();
           int i=0;
           while(it.hasNext())
           {
               Question q = (Question)it.next();

               String vvId = defautIdArr[i];
               String value = defautArr[i];
               if(vvId==null) continue;
               QuestionRepitition qr = null;
               if(!vvId.equalsIgnoreCase(""))
               {
                   qr = new QuestionRepititionTransferObject();
                   FormValidValue vv = new FormValidValueTransferObject();
                   vv.setValueIdseq(vvId);
                   qr.setDefaultValidValue(vv);
                   qr.setRepeatSequence(j+1);
               }
               else if(!value.equalsIgnoreCase(""))
               {
                   qr = new QuestionRepititionTransferObject();
                   qr.setDefaultValue(value);
                   qr.setRepeatSequence(j+1);
               }

               i++;
               if(qr!=null)
               {
                   List<QuestionRepitition> qrList = map.get(q.getQuesIdseq());
                   if(qrList==null)
                       qrList = new ArrayList<QuestionRepitition>();
                    qrList.add(qr);
                   map.put(q.getQuesIdseq(),qrList);
               }
               else
               {
                   noRepQIdList.add(q.getQuesIdseq());
               }               
               
           }
       }
       return map;
   }
   private boolean haveQuestions(Module module)
   {
       if(module==null)
            return false;
       if(module.getQuestions()==null)
            return false;
        if(module.getQuestions().size()<1)
            return false;
        return true;
   }
   
    private List<String> getQuestionIdsWithRepeats(Module module)
    {
       List<String> list = new ArrayList<String>();
        if(module.getQuestions()==null)
         return list;
        if(module.getQuestions().isEmpty())
         return list;      
        Iterator it = module.getQuestions().iterator();
        while(it.hasNext())
        {
            Question q = (Question)it.next();
            List<QuestionRepitition> repList = q.getQuestionRepititions();
            if((repList!=null)&&(!repList.isEmpty()))
            list.add(q.getQuesIdseq());               
        }
        return list;   
    }
    
   private boolean doesQuestionsHaveRepeats(Module module)
   {
       if(module.getQuestions()==null)
        return false;
       if(module.getQuestions().isEmpty())
        return false;      
       Iterator it = module.getQuestions().iterator();
       boolean result = false;
       while(it.hasNext())
       {
           Question q = (Question)it.next();
           List<QuestionRepitition> repList = q.getQuestionRepititions();
           if((repList!=null)&&(!repList.isEmpty()))
            return true;               
       }
       return result;
   }
    /*
     * Group defaults by repitition
     */
    private List<String[]> getArrayByRepitition(String[] defArr, int itemsInaSet)
    {
       List<String[]> list = new ArrayList<String[]>();
       for(int j=0;j<defArr.length;++j)
       {
        String[] tempArr = new String[itemsInaSet];
        for(int i=0;(i<itemsInaSet&&j<defArr.length);++i)
        {
            tempArr[i]=defArr[j];
            if(i<(itemsInaSet-1))
                ++j;
        }
        list.add(tempArr);
       }
       return list;
    }


    private List<Module> getRepetitions(Module module,
                                        int numberOfRepetititons)
    {
        List<Module> moduleRepetitions = new ArrayList<Module>();

        if (numberOfRepetititons == 0)
            return moduleRepetitions;
        Module tempModuleClone = null;
        for (int i = 0; i < numberOfRepetititons; ++i)
        {
            try
            {
                tempModuleClone = (Module)module.clone();
            } catch (Exception e)
            {
                if (log.isErrorEnabled())
                {
                    log.error("Exception Clonning Module", e);
                }
                return moduleRepetitions;
            }
            moduleRepetitions.add(tempModuleClone);
        }
        return moduleRepetitions;
    }

    private List<QuestionRepitition> getChangedQuestionRepititions(Module module,
                                                                   List<Module> repeats)
    {
        List<QuestionRepitition> qRepeats =
            new ArrayList<QuestionRepitition>();
        return qRepeats;
    }

    private Integer getNewRepititionCount(Module module, List<Module> repeats)
    {
        return repeats.size();
    }

    private void setQuestionDefaultArrays(DynaActionForm dynaForm,
                                          List<Module> modRepetitions,
                                          Module module)
    {
        if (modRepetitions == null)
            return;
        if (modRepetitions.isEmpty())
            return;
        List qList = module.getQuestions();
        if (qList == null)
            return;
        if (qList.isEmpty())
            return;

        int arrSize = modRepetitions.size() * qList.size();
        String[] defaults = new String[arrSize];
        String[] defaultVVIds = new String[arrSize];

        int index = 0;
        for (Module repitition : modRepetitions)
        {
            
            if (repitition != null && repitition.getQuestions() != null)
            {
                qList = repitition.getQuestions();
                ListIterator qIterate = qList.listIterator();
                while (qIterate.hasNext())
                {
                    Question question = (Question)qIterate.next();
                    FormValidValue defaultValidValue =
                        question.getDefaultValidValue();
                    if (defaultValidValue != null)
                    {
                        defaults[index] = defaultValidValue.getLongName();
                        defaultVVIds[index] =
                            defaultValidValue.getValueIdseq();
                    } else
                    {
                        defaults[index] = question.getDefaultValue();
                        defaultVVIds[index] = "";
                    }
                    index++;
                }
            }
        }
        dynaForm.set(QUESTION_DEFAULTS, defaults);
        dynaForm.set(QUESTION_DEFAULT_VV_IDS, defaultVVIds);

    }

    private void addToQuestionDefaultArrays(DynaActionForm dynaForm,
                                            int numberOfRepeats, Module module)
    {

        if (module.getQuestions() == null)
            return;
        if (module.getQuestions().isEmpty())
            return;

        String[] defaults = (String[])dynaForm.get(QUESTION_DEFAULTS);
        String[] defaultvvids =
            (String[])dynaForm.get(QUESTION_DEFAULT_VV_IDS);
        int totalSize =
            defaults.length + (numberOfRepeats * module.getQuestions().size());
        String[] newDefaults = new String[totalSize];
        String[] newDefaultvvids = new String[totalSize];

        for (int i = 0; i < totalSize; ++i)
        {
            if (i < defaults.length)
            {
                newDefaults[i] = defaults[i];
                newDefaultvvids[i] = defaultvvids[i];
            } else
            {
                newDefaults[i] = "";
                newDefaultvvids[i] = "";
            }

        }
        dynaForm.set(QUESTION_DEFAULTS, newDefaults);
        dynaForm.set(QUESTION_DEFAULT_VV_IDS, newDefaultvvids);


    }


    private void setDefaultsFromArray(List modules, String[] defaultArr)
    {

        if (modules == null)
        {
            modules = new ArrayList();
        }
        int index = 0;
        for (int i = 0; i < modules.size(); i++)
        {
            Module currModule = (Module)modules.get(i);
            List qList = currModule.getQuestions();
            if (qList == null)
            {
                qList = new ArrayList();
            }
            for (int j = 0; j < qList.size(); j++)
            {
                String defaultStr = defaultArr[index];
                Question currQ = (Question)qList.get(j);
                currQ.setDefaultValue(defaultStr);
                ++index;
            }
        }
    }

    private void setQuestionDefaultsAsArray(List modules,DynaActionForm dynaForm)
    {
        String[] defaultArr = null;
        String[] defaultArrIds = null;
        if (modules == null)
        {
            defaultArr = new String[0];
            defaultArrIds = new String[0];
        }
        else
        {
            ListIterator iterate = modules.listIterator();
            defaultArr = new String[getMaxDefaultSize(modules)];
            defaultArrIds = new String[getMaxDefaultSize(modules)];
            int defaultIndex = 0;
            while (iterate.hasNext())
            {
                int index = iterate.nextIndex();
                Module module = (Module)iterate.next();
                if (module != null && module.getQuestions() != null)
                {
                    List qList = module.getQuestions();
                    ListIterator qIterate = qList.listIterator();
                    while (qIterate.hasNext())
                    {
                        Question question = (Question)qIterate.next();
                        String defaultValue = question.getDefaultValue();
                        if(defaultValue==null) defaultValue ="";
                        FormValidValue fvv = question.getDefaultValidValue();
                        if (fvv!=null)
                        {
                            defaultArr[defaultIndex] = fvv.getLongName();
                            defaultArrIds[defaultIndex]=fvv.getValueIdseq();
                        } else
                        {
                            defaultArr[defaultIndex] = defaultValue;
                            defaultArrIds[defaultIndex]="";
                        }
                        ++defaultIndex;
                    }
                }
            }
        }
        dynaForm.set(QUESTION_DEFAULTS, defaultArr);
        dynaForm.set(QUESTION_DEFAULT_VV_IDS, defaultArrIds);        
    }

    private int getMaxDefaultSize(List modules)
    {
        if (modules == null)
            return 0;
        int maxSize = 0;
        ListIterator iterate = modules.listIterator();
        while (iterate.hasNext())
        {
            Module module = (Module)iterate.next();
            if (module != null && module.getQuestions() != null)
            {
                List qList = module.getQuestions();
                if (qList != null)
                    maxSize = maxSize + qList.size();
            }
        }
        return maxSize;
    }

}


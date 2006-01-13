
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
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;


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
        List<Module> modRepetitions = getRepetitions(selectedModule);
        setQuestionDefaultArrays(dynaForm, modRepetitions, selectedModule);
        dynaForm.set(QUESTION_DEFAULTS, new String[0]);
        dynaForm.set(QUESTION_DEFAULT_VV_IDS, new String[0]);

        setSessionObject(request, MODULE, selectedModule, true);
        setSessionObject(request, MODULE_REPETITIONS, modRepetitions, true);


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


        setSessionObject(request, MODULE_REPETITIONS, currRepeats, true);
        saveMessage("cadsr.formbuilder.module.repetition.add.success",
                    request);
        return mapping.findForward("viewRepetitions");
    }

    public ActionForward deleteRepetitions(ActionMapping mapping,
                                           ActionForm editForm,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException,
                                                                                                            ServletException
    {
        List<Module> moduleList =
            (List<Module>)getSessionObject(request, MODULE_REPETITIONS);

        String[] selectedIndexes =
            (String[])request.getParameterValues(SELECTED_ITEMS);
        for (int i = selectedIndexes.length - 1; i > -1; --i)
        {
            int currIndex = (new Integer(selectedIndexes[i])).intValue();
            if ((moduleList != null) && (moduleList.size() > 0))
            {
                moduleList.remove(currIndex);
            }
        }
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

        return mapping.findForward("done");
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
        
        if(!haveElemetsToSave(module,repeats))
        {
            saveMessage("cadsr.formbuilder.module.repetition.save.success",
                        request);
            return mapping.findForward("done");            
        }
       
                
        List<String[]> defaultArrList = getArrayByRepitition(defaultValueArr,module.getQuestions().size());
        List<String[]> defaultArrIdList = getArrayByRepitition(defaultValueIds,module.getQuestions().size());
        List<String> noRepQIdList = new ArrayList<String>();
        Map<String,List<QuestionRepitition>> questionRepeatMap = getQuestionRepeatMap(module,defaultArrList,defaultArrIdList,noRepQIdList);
        
        FormBuilderServiceDelegate service = getFormBuilderService();
        Module savedModeule = null;
        try
        {
            savedModeule = service.saveQuestionRepititons(module.getModuleIdseq(),repeats.size(),questionRepeatMap,noRepQIdList);
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
        return mapping.findForward("done"); 
    }
    
   private Map<String,List<QuestionRepitition>> getQuestionRepeatMap(Module module,List<String[]> defaultArrList,List<String[]> defaultArrIdList,
                List<String> noRepQIdList)
   {
       Map<String,List<QuestionRepitition>> map = new HashMap<String,List<QuestionRepitition>>();

       if(module.getQuestions()==null)
        return map;
       Iterator it = module.getQuestions().iterator();
       int i=0;
       int repeatNumber = defaultArrList.size();
       for(int j=0;j<repeatNumber;++j)
       {
          String[] defautArr = defaultArrList.get(0);
          String[] defautIdArr = defaultArrIdList.get(0);
           while(it.hasNext())
           {
               Question q = (Question)it.next();
               String vvId = defautIdArr[i];
               String value = defautArr[i];
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
   private boolean haveElemetsToSave(Module module, List<Module> repeats)
   {
       if(module==null)
            return false;
       if(module.getQuestions()==null)
            return false;
        if(module.getQuestions().size()<1)
            return false;
        return true;
        //if(doesQuestionsHaveRepeats(module)&&repeats)
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
        for(int i=0;i<itemsInaSet;++i)
        {
            tempArr[i]=defArr[j];
            ++j;
        }
        list.add(tempArr);
       }
       return list;
    }

    private List<Module> getRepetitions(Module module)
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
                if (log.isErrorEnabled())
                {
                    log.error("Exception Clonning Module", e);
                }
                return moduleRepeats;
            }
            Map<String, QuestionRepitition> qMap =
                questionRepMap.get(new Integer(i + 1));
            if (qMap == null)
                continue;

            setDefaults(qMap, tempModuleClone);
            moduleRepeats.add(tempModuleClone);
        }
        return moduleRepeats;
    }

    private void setDefaults(Map<String, QuestionRepitition> qMap,
                             Module module)
    {
        List questions = module.getQuestions();
        ListIterator qIt = questions.listIterator();
        while (qIt.hasNext())
        {
            Question q = (Question)qIt.next();
            QuestionRepitition qRep = qMap.get(q.getQuesIdseq());
            q.setDefaultValue(qRep.getDefaultValue());
            q.setDefaultValidValue(qRep.getDefaultValidValue());
        }
    }

    /**
     *
     * @param questions
     * @return a Map<repeantSequence#,Map<questionId,QuestionRepitition>>
     */
    private Map getQuestionRepititionsMap(List questions)
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


        for (Module repitition : modRepetitions)
        {
            int index = 0;
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

    private String[] getQuestionDefaultsAsArray(List modules)
    {
        if (modules == null)
        {
            return null;
        }

        ListIterator iterate = modules.listIterator();
        String[] defaultArr = new String[getMaxDefaultSize(modules)];
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
                    if (defaultValue != null)
                    {
                        defaultArr[defaultIndex] = defaultValue;
                    } else
                    {
                        defaultArr[defaultIndex] = "";
                    }
                    ++defaultIndex;
                }
            }
        }
        return defaultArr;
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


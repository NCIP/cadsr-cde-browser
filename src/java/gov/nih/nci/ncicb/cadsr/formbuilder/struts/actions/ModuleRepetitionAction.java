
package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.FormInstructionTransferObject;
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


public class ModuleRepetitionAction extends FormBuilderSecureBaseDispatchAction {


    public ActionForward getModuleToRepeat (
      ActionMapping mapping,
      ActionForm editForm,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      Form crf = null;
      DynaActionForm dynaForm = (DynaActionForm) editForm;
      Integer moduleIndex = (Integer) dynaForm.get(MODULE_INDEX);
      crf = (Form) getSessionObject(request, CRF);
      List modules = crf.getModules();
      Module selectedModule = (Module) modules.get(moduleIndex.intValue());
      selectedModule.setForm(crf);
      List<Module> modRepetitions = getRepetitions(selectedModule,selectedModule.getNumberOfRepeats());
      String[] defaultArr =getQuestionDefaultsAsArray(modRepetitions);
      dynaForm.set(QUESTION_DEFAULTS,defaultArr);
      
      setSessionObject(request, MODULE, selectedModule,true);
      setSessionObject(request, MODULE_REPETITIONS, modRepetitions,true);
      
      
     return mapping.findForward("viewRepetitions");
    }
    
    public ActionForward addRepetitions (
      ActionMapping mapping,
      ActionForm editForm,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      Form crf = null;
      DynaActionForm dynaForm = (DynaActionForm) editForm;
      Integer numberOfRepetitions = null;
        

      numberOfRepetitions = (Integer) dynaForm.get(NUMBER_OF_MODULE_REPETITIONS);
      List<Module> currRepeats = (List<Module>)getSessionObject(request, MODULE_REPETITIONS);
      String[] defaultValueArr = (String[]) dynaForm.get(QUESTION_DEFAULTS);
      setDefaultsFromArray(currRepeats,defaultValueArr);
     
     if(numberOfRepetitions<1)
     {
         saveError("cadsr.formbuilder.module.repetition.add.invalidCount", request);
         return mapping.findForward("viewRepetitions");
     }

      
      Module module = (Module)getSessionObject(request, MODULE);
      List<Module> newReps = getRepetitions(module,numberOfRepetitions);
      currRepeats.addAll(newReps);
      
      String[] defaultArr =getQuestionDefaultsAsArray(currRepeats);
      dynaForm.set(QUESTION_DEFAULTS,defaultArr);      
      
      setSessionObject(request, MODULE_REPETITIONS, currRepeats,true);
      saveMessage("cadsr.formbuilder.module.repetition.add.success", request);
     return mapping.findForward("viewRepetitions");
    }    
    public ActionForward deleteRepetitions (
      ActionMapping mapping,
      ActionForm editForm,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      List<Module> moduleList = (List<Module>)getSessionObject(request,MODULE_REPETITIONS);
        
        String[] selectedIndexes = (String[])request.getParameterValues(SELECTED_ITEMS);
         for(int i=selectedIndexes.length-1;i>-1;--i)
            {
              int currIndex = (new Integer(selectedIndexes[i])).intValue();
              if ((moduleList != null) && (moduleList.size() > 0)) {
                   moduleList.remove(currIndex);
              }
            }
           saveMessage("cadsr.formbuilder.module.repetition.delete.success", request);
            return mapping.findForward("viewRepetitions");
        } 
    
    
    private List<Module> getRepetitions(Module module,int numberOfRepetititons)
    {
        List<Module> moduleRepetitions = new ArrayList<Module>();
        
        if(numberOfRepetititons==0)
            return moduleRepetitions;
        for(int i=0;i<numberOfRepetititons;++i)
        {
            //TODO Add code to set defaults and remove skip patterns
            moduleRepetitions.add(module);
        }
        return moduleRepetitions;
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
    public ActionForward doneManageRepetitions(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

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
    public ActionForward saveRepetitions(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      
      List<Module> repeats = (List<Module>)getSessionObject(request, MODULE_REPETITIONS);
      Module module = (Module)getSessionObject(request, MODULE);
      Integer repCount = getNewRepititionCount(module,repeats);
      List<QuestionRepitition> qRepeats = getChangedQuestionRepititions(module,repeats);
      //TODO get QuestionRepetitions
      
      removeSessionObject(request, MODULE);
      removeSessionObject(request, MODULE_REPETITIONS);
      
       return mapping.findForward("done");
      }
      
    private List<QuestionRepitition> getChangedQuestionRepititions(Module module,List<Module> repeats)
    {
        List<QuestionRepitition> qRepeats = new ArrayList<QuestionRepitition>();
        return qRepeats;
    }
    private Integer getNewRepititionCount(Module module,List<Module> repeats)
    {
        //TODO check with module repitions
        return repeats.size();
    }
    private String[] getQuestionDefaultsAsArray(List modules) {
      if (modules == null) {
        return null;
      }

      ListIterator iterate = modules.listIterator();
      String[] defaultArr = new String[getMaxDefaultSize(modules)];
       int defaultIndex = 0;
      while (iterate.hasNext()) {
        int index = iterate.nextIndex();
        Module module = (Module) iterate.next();
        if(module!=null&&module.getQuestions()!=null)
        {
          List qList = module.getQuestions();
          ListIterator qIterate = qList.listIterator();
          while(qIterate.hasNext())
          {
            Question question = (Question) qIterate.next();
            String defaultValue = question.getDefaultValue();
            if(defaultValue!=null)
            {
              defaultArr[defaultIndex] = defaultValue;
            }
            else
            {
              defaultArr[defaultIndex] =  "";
            }
           ++defaultIndex;
          }
         }
        }
      return defaultArr;
    }      
    
    
    private int getMaxDefaultSize(List modules)
    {
      if(modules==null)
        return 0;
      int maxSize = 0;
      ListIterator iterate = modules.listIterator();
      while (iterate.hasNext()) {
        Module module = (Module) iterate.next();
        if(module!=null&&module.getQuestions()!=null)
        {
          List qList = module.getQuestions();
          if(qList!=null)
            maxSize=maxSize+qList.size();
         }
        }
      return maxSize;
    }    
    
    private void setDefaultsFromArray(
      List modules,
      String[] defaultArr) {
    
      if(modules==null)
      {
        modules = new ArrayList();
      }
      int index=0;
      for (int i = 0; i < modules.size(); i++) {
          Module currModule = (Module) modules.get(i);
          List qList = currModule.getQuestions();
          if(qList==null)
          {
            qList = new ArrayList();
          }
         for (int j = 0; j < qList.size(); j++) {
              String defaultStr = defaultArr[index];
              Question currQ = (Question) qList.get(j);
              currQ.setDefaultValue(defaultStr);
              ++index;
         }
      }
    }    
    
}


package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;
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


public class ManageProtocolsAction
  extends FormBuilderSecureBaseDispatchAction {
  
  public ActionForward manageProtocols(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
        return mapping.findForward("success");
    }
    
    
  

  public ActionForward gotoAddProtocols(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward("success");
  }

    public ActionForward doneProtocol(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      
      FormBuilderBaseDynaFormBean bean = (FormBuilderBaseDynaFormBean)form;
      Form crf = (Form)getSessionObject(request, FormConstants.CRF); 
      bean.set(FormConstants.PROTOCOLS_LOV_NAME_FIELD, crf.getDelimitedProtocolLongNames());
      
      String backTo = (String)getSessionObject(request, "backTo");
      removeSessionObject(request, "backTo");
      if (backTo.equals(NavigationConstants.FORM_COPY)){
          return mapping.findForward("successGotoFormCopy");
      }
      if (backTo.equals(NavigationConstants.FORM_COPY)){
          return mapping.findForward("successGotoFormEdit");
      }
      //request.setAttribute("showCached", CaDSRConstants.YES); //TODO
      return mapping.findForward(SUCCESS);
    }

  public ActionForward addProtocol(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

        DynaActionForm dynaForm = (DynaActionForm) form;    
        String id = (String)dynaForm.get("protocolIdSeq");
        if (id.length()==0){
           saveError("cadsr.formbuilder.form.edit.form.manageProtocol.protocol", request);
           return mapping.findForward(FAILURE);
         }
         
        Form crf = (Form) getSessionObject(request, CRF);
        List oldList = crf.getProtocols();

        FormBuilderServiceDelegate service = getFormBuilderService();
        try{
            if (!alreadyExist(oldList, id)){
                Protocol p = service.getProtocolByPK(id);                
                oldList.add(p);
            }
            crf.setProtocols(oldList);
            setSessionObject(request, CRF, crf);
            
       }catch(FormBuilderException exp){
           if (log.isDebugEnabled()) {
             log.debug("Exception on getting protocol by PK  " + exp);
           }
           saveError(exp.getErrorCode(), request);
           return mapping.findForward(FAILURE);
       }
    return mapping.findForward(SUCCESS);
  }

  public ActionForward removeProtocol(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;

    String id = (String) dynaForm.get(FormConstants.PROTOCOL_ID_SEQ);

      Form crf = (Form) getSessionObject(request, CRF);
      
      List protocols = crf.getProtocols();
      if (protocols == null || protocols.isEmpty()){
          log.info("Nothing to remove. There is no existing protocols.");
          return mapping.findForward("success"); 
      }
      
      
      Iterator it = protocols.iterator();
      List removed = new ArrayList();;
      while (it.hasNext()){
        Protocol p = (Protocol)it.next();
        if (p.getProtoIdseq().equals(id)){
            removed.add(p);
        }
      }
      protocols.removeAll(removed);
      //removed from crf.
      crf.setProtocols(protocols);
      setSessionObject(request, CRF, crf);
      
      //check the skip pattern
      List<TriggerActionChanges> triggerChangesList = findFormSkipPatternByProtocol(crf, id);
      if (triggerChangesList!=null && !triggerChangesList.isEmpty()){
        setSessionObject(request, PROTOCOL_ASSOCIATED_TRIGGERS, triggerChangesList);
        return mapping.findForward("hasSkipPattern");          
      }
      
      removeSessionObject(request, PROTOCOL_ASSOCIATED_TRIGGERS);
      return mapping.findForward("success");
  }
    
    public ActionForward updateSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      
      DynaActionForm formBean = (DynaActionForm)form;
      String choice = (String)formBean.get("choice");//TODO: need to update the CRF for display
      if ("yes".equalsIgnoreCase(choice)){
          //save to session
          List<String> updateTriggers = (List<String>)getSessionObject(request, UPDATE_SKIP_PATTERN_TRIGGERS);
          List<String> associatedTriggers = (List<String>)getSessionObject(request, PROTOCOL_ASSOCIATED_TRIGGERS);
          if (updateTriggers !=null){
              updateTriggers.addAll(associatedTriggers);          
          }else{
              updateTriggers = associatedTriggers;
          }
          setSessionObject(request, UPDATE_SKIP_PATTERN_TRIGGERS, updateTriggers);
          removeSessionObject(request,PROTOCOL_ASSOCIATED_TRIGGERS);
          
    
      }
      return mapping.findForward(SUCCESS);
      }


    private boolean alreadyExist(List old, String newId){
        for (int i=0; i<old.size(); i++){
            Protocol p = (Protocol)old.get(i);
            if (p.getProtoIdseq().equals(newId)){
                return true;
            }
        }
        return false;
    }
    
    private List<TriggerActionChanges> findFormSkipPatternByProtocol(Form crf, String protocolId){
        //check module first
        List modules = crf.getModules();
        List retList = new ArrayList();
        if (modules!=null && !modules.isEmpty()){
            Iterator it = modules.iterator();
            while (it.hasNext()){
                Module module = (Module)it.next();
                retList.addAll(findModuleSkipPattern(module, protocolId));
                retList.addAll(findModuleQuestionSkipPattern(module, protocolId));
            }    
        }
        return retList;
    }
    
    private List<TriggerActionChanges> findModuleSkipPattern(Module module, String protocolId){
        List retList = new ArrayList();
        List triggers = module.getTriggerActions();
        if (triggers!=null && !triggers.isEmpty()){
            Iterator it = triggers.iterator();
            while (it.hasNext()){
                TriggerAction trigger = (TriggerAction)it.next();                
                if (hasProtocol(trigger.getProtocols(), protocolId)){
                    retList.add(makeTriggerActionChanges(trigger.getIdSeq(), protocolId));
                }
            }    
        }
        return retList;
    }
    
    private TriggerActionChanges makeTriggerActionChanges(String trigerId, String protocolId){
        TriggerActionChanges changes = new TriggerActionChangesTransferObject();
        changes.setTriggerActionId(trigerId);
        //protocols
        List protocols = new ArrayList();
        Protocol p = new ProtocolTransferObject();
        p.setProtoIdseq(protocolId);
        protocols.add(p);
        changes.setDeleteProtocols(protocols );
        return changes;
    }
    
    private List<TriggerAction> findModuleQuestionSkipPattern(Module module, String protocolId){
        List retList = new ArrayList();
        List questions = module.getQuestions();
        if (questions!=null && !questions.isEmpty()){
            Iterator it = questions.iterator();
            while (it.hasNext()){                
                retList.addAll(findQuestionSkipPattern((Question)it.next(), protocolId));                    
            }    
        }
        return retList;
    }

    private List<TriggerAction> findQuestionSkipPattern(Question question, String protocolId){
        List retList = new ArrayList();
        List triggers = question.getTriggerActions();
        if (triggers!=null && !triggers.isEmpty()){
            Iterator it = triggers.iterator();
            while (it.hasNext()){   
                TriggerAction trigger = (TriggerAction)it.next();
                if (hasProtocol(trigger.getProtocols(), protocolId)){
                    retList.add(trigger);    
                }    
            }    
        }
        return retList;
    }
    private boolean hasProtocol(List<Protocol> protocols, String protocolId){
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
    
    
    private boolean validValueHasSkipPattern(){
        boolean ret = false;
        return ret;
    }
}

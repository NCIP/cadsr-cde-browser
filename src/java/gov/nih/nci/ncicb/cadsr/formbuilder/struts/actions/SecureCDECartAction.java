package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.util.DTOTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecureCDECartAction extends FormBuilderSecureBaseDispatchAction {

  public ActionForward gotoChangeDEAssociation (
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm) form;
    int questionIndex =
      Integer.parseInt((String) dynaForm.get("questionIndex"));
    List questions =
      ((Module) getSessionObject(request, MODULE)).getQuestions();
    Question q = (Question) questions.get(questionIndex);

    if(q.getDataElement() == null) 
      request.setAttribute("removeButton", "no");

    return displayCDECart(mapping, form, request, response);
  }

  public ActionForward addQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);

    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List questions = module.getQuestions();   
    List newQuestions = new ArrayList(selectedItems.length);

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

    //for getting reference docs
    FormBuilderServiceDelegate service = getFormBuilderService();
    List refDocs = null;
    
    int length = selectedItems.length;
    List deIdList = new ArrayList(length);
    List vdIdList = new ArrayList(length);
    DataElement de = null;   
    for (int i = 0; i < selectedItems.length; i++) {
        de =
            (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();
        deIdList.add(de.getDeIdseq());
        if (de.getValueDomain()!=null && de.getValueDomain().getVdIdseq()!=null){
            vdIdList.add(de.getValueDomain().getVdIdseq());            
        }    
    }
    
    try {
    Map vvMap = service.getValidValues(vdIdList);
    for (int i = 0; i < selectedItems.length; i++) {      
      de = (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();      
      String vdIdSeq = de.getValueDomain().getVdIdseq();
      //may refactor the following code for better performance 
            refDocs = service.getRreferenceDocuments(de.getDeIdseq());
            de.setReferenceDocs(refDocs);
      String questionLongName = de.getLongCDEName();
      if (!isValidCDE(de)){
         saveError("cadsr.formbuilder.form.question.add.badCDE", request, de.getLongName());
         questionLongName = "Data Element " + de.getLongName() + " does not have Preferred Question Text";         
          //user can still continue
         //return mapping.findForward(FAILURE);
      }
    
      Question q = new QuestionTransferObject();
      module.setForm(crf);
      q.setModule(module);

      //set valid value
      List values = (List)vvMap.get(vdIdSeq);
      de.getValueDomain().setValidValues(values);
      List newValidValues = DTOTransformer.toFormValidValueList(values, q);
      q.setQuesIdseq(new Date().getTime() + "" + i);
      q.setValidValues(newValidValues);
      q.setDataElement(de);
      q.setLongName(questionLongName);

      q.setVersion(crf.getVersion());
      q.setAslName(crf.getAslName());
      q.setPreferredDefinition(de.getPreferredDefinition());
      q.setContext(crf.getContext());

      q.setDisplayOrder(displayOrder);

      newQuestions.add(q);        
    }//end of for
    }catch (FormBuilderException exp){
            if (log.isErrorEnabled()) {
              log.error("Exception on getting reference documents or Permissible values for the Data Element de Idseq=" + de.getIdseq() , exp);
            }
            saveError(exp.getErrorCode(), request);
            return mapping.findForward(FAILURE);
    }       
        
    
    //only when all CDE are valid to be added to a form then add new questions to form.module.questions
    if (displayOrder < questions.size()) {
        questions.addAll(displayOrder, newQuestions);
    }else{
        questions.addAll(newQuestions);
    }
    FormActionUtil.setInitDisplayOrders(questions); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                            
    // Jump to the update location on the screen
    request.setAttribute(CaDSRConstants.ANCHOR,"Q"+displayOrder);    
        
    saveMessage("cadsr.formbuilder.question.add.success",request);
    return mapping.findForward("success");
  }

  public ActionForward changeAssociation(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    CDECart cart = new CDECartTransferObject();

    DynaActionForm dynaForm = (DynaActionForm) form;
    String selectedText = (String) dynaForm.get("selectedText");

    DataElement de = null;
    List newValidValues = null;

    int questionIndex =
      Integer.parseInt((String) dynaForm.get("questionIndex"));

    List questions =
      ((Module) getSessionObject(request, MODULE)).getQuestions();
    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
    Question q = (Question) questions.get(questionIndex);

    if ((selectedText == null) | (selectedText.indexOf(",") == -1)) {
      // this is the remove association case
      saveMessage("cadsr.formbuilder.question.changeAssociation.remove",request);
    }
    else {
      int ind = selectedText.indexOf(',');
      int deIndex = Integer.parseInt(selectedText.substring(0, ind).trim());
      String newLongName = "";

      if (selectedText.length() > ind) {
        newLongName = selectedText.substring(ind + 1).trim();
      }

      Collection col = sessionCart.getDataElements();
      ArrayList al = new ArrayList(col);

      de = (DataElement) ((CDECartItem) al.get(deIndex)).getItem();
      if (newLongName==null || "null".equals(newLongName) || newLongName.length()==0){
          //newLongName = "";
           newLongName = "Data Element " + de.getLongName() + " does not have Preferred Question Text";
      }

      //get reference docs
      FormBuilderServiceDelegate service = getFormBuilderService();
      List refDocs = null;
      
      try {
          refDocs = service.getRreferenceDocuments(de.getDeIdseq());          
          de.setReferenceDocs(refDocs);
       }catch (FormBuilderException exp){
           if (log.isErrorEnabled()) {
             log.error("Exception on getting reference documents for the Data Element " , exp);
           }
           saveError(exp.getErrorCode(), request);
           return mapping.findForward(FAILURE);
       }       
        
      if (!isValidCDE(de)){
          saveError("cadsr.formbuilder.form.question.add.badCDE", request, de.getLongName());
          //return mapping.findForward(FAILURE);
          //user can still continue
      }
      
      //set valid values with value meaning
      List vdIdList = new ArrayList();
      try{
          if (de.getValueDomain()!=null && de.getValueDomain().getVdIdseq()!=null ){
              String vdIdSeq = de.getValueDomain().getVdIdseq();
              vdIdList.add(vdIdSeq);
              Map vvMap = service.getValidValues(vdIdList);
              List vvList = (List)vvMap.get(vdIdSeq);
              de.getValueDomain().setValidValues(vvList);
              newValidValues = DTOTransformer.toFormValidValueList(vvList, q);
          }    
          
      }catch (FormBuilderException fbe){
          log.error("Exception on getting valid values for the Data Element Value Doamin , vdIdSeq=" 
                                +  de.getValueDomain().getVdIdseq(), fbe);
          saveError("cadsr.formbuilder.question.changeAssociation.newAssociation.fail", request);
          return mapping.findForward(FAILURE);
      }
      
/*      
      //clear out the form valid value Id
      Iterator it = newValidValues.iterator();
      while (it.hasNext()){
          FormValidValue fvv = (FormValidValue)it.next();
          fvv.setValueIdseq(FormConstants.UNKNOWN_VV_ID);
      }
*/
      q.setLongName(newLongName);
      
      q.setValidValues(newValidValues);
      saveMessage("cadsr.formbuilder.question.changeAssociation.newAssociation",request);
    }

    q.setDataElement(de);
    //clear out the default value
    q.setDefaultValidValue(null);
    q.setDefaultValue("");

    // Jump to the update location on the screen
    request.setAttribute(CaDSRConstants.ANCHOR,"Q"+questionIndex);     

    return mapping.findForward("success");
  }

  /**
   * Displays CDE Cart.
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
  public ActionForward displayCDECart(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //CDECart cart = new CDECartTransferObject();
    CDECart cart = null;
     
    try {
      String userName = getLoggedInUsername(request);
      FormBuilderServiceDelegate service = getFormBuilderService();
      NCIUser user =
        (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

      cart = service.retrieveCDECart(userName);

      //Merge two carts
      //sessionCart.mergeCart(cart);
      if (sessionCart != null) {
        cart.mergeCart(sessionCart);
      }

      sessionCart = null;
      this.setSessionObject(request, CaDSRConstants.CDE_CART, cart);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on displayCDECart", exp);
      }
      saveError(exp.getErrorCode(), request);
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * Adds items to CDE Cart.
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
  public ActionForward addItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      String userName = getLoggedInUsername(request);
      FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedSaveItems = myForm.getSelectedSaveItems();
      Collection items = new ArrayList();

      for (int i = 0; i < selectedSaveItems.length; i++) {
        CDECartItem cartItem = new CDECartItemTransferObject();
        cartItem.setId(selectedSaveItems[i]);
        cartItem.setType("DATAELEMENT");
        items.add(cartItem);
      }

      service.addToCDECart(items,userName);

      CDECart cart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
      saveMessage("cadsr.cdecart.save.success",request);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on addItems " , exp);
      }
      saveError(exp.getErrorCode(), request);
    }

    return mapping.findForward("addDeleteSuccess");
  }

  /**
   * Delete items from the CDE Cart.
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
  public ActionForward removeItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      String userName = getLoggedInUsername(request);
      FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedDeleteItems = myForm.getSelectedDeleteItems();
      Collection savedItems = new ArrayList();

      //Collection unsavedItems = new ArrayList();
      Collection items = new ArrayList();

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
      CDECartItem item = null;

      for (int i = 0; i < selectedDeleteItems.length; i++) {
        item = sessionCart.findDataElement(selectedDeleteItems[i]);

        if (item.getPersistedInd()) {
          savedItems.add(selectedDeleteItems[i]);
        }

        items.add(selectedDeleteItems[i]);
      }
      
      service.removeFromCDECart(savedItems,userName);
      sessionCart.removeDataElements(items);
      saveMessage("cadsr.cdecart.delete.success",request);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on removeItems " , exp);
      }
      saveError(exp.getErrorCode(), request);
    }

    return mapping.findForward("addDeleteSuccess");
  }
 
 
  public ActionForward subsetQuestionValidValues(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);

    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List selectedDataElements = new ArrayList();

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

    for (int i = 0; i < selectedItems.length; i++) {
      DataElement de =
        (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();
      selectedDataElements.add(de);
    }
    
    this.setSessionObject(request,SELECTED_DATAELEMENTS,selectedDataElements,true);
    return mapping.findForward(SUBSET_VALIDVALUES);
  }
 
  public ActionForward addSubsettedValidValuesQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    
    /**
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);


    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List questions = module.getQuestions();

    //Get the list of Valid Values here
    
      int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

      DataElement de = (DataElement)getSessionObject(request,"selectedDataElement");

      Question q = new QuestionTransferObject();
      module.setForm(crf);
      q.setModule(module);

      List values = de.getValueDomain().getValidValues();
      List newValidValues = DTOTransformer.toFormValidValueList(values, q);
      q.setQuesIdseq(new Date().getTime()+"0");
      q.setValidValues(newValidValues);
      q.setDataElement(de);
      q.setLongName(de.getLongName());
      q.setVersion(crf.getVersion());
      q.setAslName(crf.getAslName());
      q.setPreferredDefinition(de.getPreferredDefinition());
      q.setContext(crf.getContext());

      q.setDisplayOrder(displayOrder);

      if (displayOrder < questions.size()) {
        questions.add(displayOrder, q);
        FormActionUtil.incrementDisplayOrder(questions, displayOrder + 1);
      }
      else {
        questions.add(q);
      }
   **/
    saveMessage("cadsr.formbuilder.question.add.success",request);
    return mapping.findForward("success");
  } 
  
  
  public ActionForward cancelAddSubsettedValidValuesQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    
    removeSessionObject(request,SELECTED_DATAELEMENTS);
    return mapping.findForward(CANCEL);
  }
  
  private boolean  isValidCDE(DataElement de){
    if (de.getLongCDEName()==null || de.getLongCDEName().length()==0){
         if (log.isDebugEnabled()) {
           log.debug("User is trying to add a CDE without Preferred Question Text. The Data Element deIdseq=" + de.getIdseq());
         }
        return false;
    }
    return true;
  }  
}

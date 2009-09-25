package gov.nih.nci.ncicb.cadsr.common.struts.formbeans;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class CDECartFormBean extends ActionForm  {
  private String [] selectedSaveItems;
  private String [] selectedDeleteItems;
  private String [] selectedItems;
  private String moduleIndex;
  private String questionIndex;
  private String src;
  
  
  public String[] getSelectedDeleteItems() {
    return selectedDeleteItems;
  }

  public void setSelectedDeleteItems(String[] newSelectedDeleteItems) {
    selectedDeleteItems = newSelectedDeleteItems;
  }

  public String[] getSelectedSaveItems() {
    return selectedSaveItems;
  }

  public void setSelectedSaveItems(String[] newSelectedSaveItems) {
    selectedSaveItems = newSelectedSaveItems;
  }

  public String[] getSelectedItems() {
    return selectedItems;
  }

  public void setSelectedItems(String[] newSelectedItems) {
    selectedItems = newSelectedItems;
  }
  
  /**
   * Reset all properties to their default values.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(
    ActionMapping mapping,
    HttpServletRequest request) {
    super.reset(mapping, request);
  }

  /**
   * Validate all properties to their default values.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   *
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(
    ActionMapping mapping,
    HttpServletRequest request) {
    return super.validate(mapping, request);
  }

  public String getModuleIndex() {
    return moduleIndex;
  }

  public void setModuleIndex(String newModuleIndex) {
    moduleIndex = newModuleIndex;
  }

  public String getQuestionIndex() {
    return questionIndex;
  }

  public void setQuestionIndex(String newQuestionIndex) {
    questionIndex = newQuestionIndex;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String newSrc) {
    src = newSrc;
  }

}
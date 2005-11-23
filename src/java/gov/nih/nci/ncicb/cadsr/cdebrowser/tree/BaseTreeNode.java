package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

import java.util.Hashtable;


public class BaseTreeNode implements TreeConstants, CaDSRConstants,TreeFunctions  {

  protected Hashtable treeParams = null;
  
  
  public BaseTreeNode(Hashtable params) {

    treeParams = params;
  }

  public String getJsFunctionName() {
    String functionName = (String)treeParams.get(FUNCTION_NAME_URL_PARAM);
    if (functionName == null) functionName = "performAction";
    return functionName;
  }

  public String getTreeType() {
    String treeType = (String)treeParams.get(TREE_TYPE_URL_PARAM);
    if (treeType == null) treeType = DE_SEARCH_TREE;
    return treeType;
  }
  public String getExtraURLParameters() {
    String extraURLParameters = (String)treeParams.get(EXTRA_URL_PARAMS);
    if ( extraURLParameters == null || getTreeType().equals(DE_SEARCH_TREE) ) {
      extraURLParameters = 
        "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes";
    }

    if (getTreeType().equals(DE_SEARCH_TREE) ) {
      String src = (String)treeParams.get("src");
      if (src != null) {
        String modIndex = (String)treeParams.get("moduleIndex");
        String quesIndex = (String)treeParams.get("questionIndex");
        extraURLParameters += "&src="+src+"&moduleIndex="+modIndex+
                              "&questionIndex="+quesIndex;
      }
    }
    
    // Add bread crumbs info
    if(extraURLParameters==null)
      extraURLParameters=TREE_BREADCRUMBS+"="+TREE_BREADCRUMBS_HOLDER;
    else
      extraURLParameters = extraURLParameters + "&"+TREE_BREADCRUMBS+"="+TREE_BREADCRUMBS_HOLDER;
      
    return extraURLParameters;
  }

  public String getFormJsFunctionName() {
    String functionName = "performAction";
    if (this.FORM_SEARCH_TREE.equals(this.getTreeType()))
      functionName = this.FORM_DETAILS_FUNCTION;
    else 
      functionName = (String)treeParams.get(FUNCTION_NAME_URL_PARAM);
    if (functionName == null) functionName = "performAction";
    return functionName;
  }

  public String isCTEPUser() {
    String ctepUser = CaDSRConstants.NO;
    if (treeParams.containsKey(this.CTEP_USER_FLAG))
      ctepUser = (String)treeParams.get(this.CTEP_USER_FLAG);

    return ctepUser;
  }
  
}
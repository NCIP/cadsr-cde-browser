package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import java.sql.Connection;
import java.util.Hashtable;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;

public class BaseTreeNode implements TreeConstants  {
  protected Connection myConn = null;
  protected DBUtil myDbUtil = null;
  protected Hashtable treeParams = null;
  
  
  public BaseTreeNode(DBUtil dbUtil
                    ,Hashtable params) {
    myDbUtil = dbUtil;
    myConn = dbUtil.getConnection();
    treeParams = params;
  }

  protected String getJsFunctionName() {
    String functionName = (String)treeParams.get(FUNCTION_NAME_URL_PARAM);
    if (functionName == null) functionName = "performAction";
    return functionName;
  }

  protected String getTreeType() {
    String treeType = (String)treeParams.get(TREE_TYPE_URL_PARAM);
    if (treeType == null) treeType = DE_SEARCH_TREE;
    return treeType;
  }
  protected String getExtraURLParameters() {
    String extraURLParameters = (String)treeParams.get(EXTRA_URL_PARAMS);
    if ( extraURLParameters == null || getTreeType().equals(DE_SEARCH_TREE) ) {
      extraURLParameters = 
        "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes";
    }
    return extraURLParameters;
  }
  
}
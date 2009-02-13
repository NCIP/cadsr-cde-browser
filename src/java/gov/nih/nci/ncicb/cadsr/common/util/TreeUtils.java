package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.contexttree.TreeConstants;
import gov.nih.nci.ncicb.webtree.WebNode;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.tree.DefaultMutableTreeNode;


public class TreeUtils {
  public static Hashtable parseParameters(String params)
    throws Exception {
    // parses a string of parameters defined with the following syntax:
    // name1:value1;name2:value2;
    Hashtable results = new Hashtable();

    if ((params != null) && !params.equals("null")) {
      StringTokenizer st = new StringTokenizer(params, ";");

      while (st.hasMoreTokens()) {
        String pair = st.nextToken();
        StringTokenizer stPair = new StringTokenizer(pair, ":");

        if (stPair.countTokens() == 2) {
          String name = stPair.nextToken();
          String value = stPair.nextToken();
          results.put(name, value);
        }
        else {
          System.err.println("invalid 'name=value' pair parameter");
          throw (new Exception("invalid 'name=value' pair parameter"));
        }
      }
    }

    return results;
  }

  /*
   * Get the Complete bread crumbs and set it as a parameter in getAction
   * of webnode
   */
  public static void setBreadCrumbsInfo(DefaultMutableTreeNode tree,WebNode webNode)
  {
    Object[]  objArr = tree.getUserObjectPath();
    StringBuffer crumbs = new StringBuffer();
    for(int i=0;i<objArr.length;i++)
    {
      WebNode currNode = (WebNode)objArr[i];
      if(currNode!=null)
      {
        crumbs.append(currNode.getName().replace("&","&amp")+">>");
      }
    }
    
      if (crumbs==null)
        crumbs.append("caDSR Context");

    //remove Context Description
 
    crumbs  = removeContextDesc(crumbs);
    //remove the extra ">>"
    crumbs.delete(crumbs.length()-2,crumbs.length());
    String crumbsStr = crumbs.toString();

    //This is done to get around ' in the names

    crumbsStr = StringUtils.strReplace(crumbsStr,"'","*??*");
    //This is done to get around " in the names
    crumbsStr = StringUtils.strReplace(crumbsStr,"\"","&quot;");
    //This is done to get around & in the names
    crumbsStr = StringUtils.strReplace(crumbsStr,"&","&amp");

    //crumbsStr = URLEncoder.encode(crumbsStr);
    String actionValue = webNode.getAction();
    String newActionStr = StringUtils.strReplace(actionValue,TreeConstants.TREE_BREADCRUMBS_HOLDER,crumbsStr);
    webNode.setAction(newActionStr);
  }
    private static StringBuffer removeContextDesc(StringBuffer crumbs)
  {
     if(crumbs.indexOf("(")>0)
     {
      int startIndex = crumbs.indexOf("(");
      int endIndex = crumbs.indexOf(")");
      crumbs.delete(startIndex,endIndex+1);
     }
      return crumbs;

  }
}

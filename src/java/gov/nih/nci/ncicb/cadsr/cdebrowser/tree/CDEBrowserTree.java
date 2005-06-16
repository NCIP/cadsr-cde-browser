package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.ContextHolder;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;
import gov.nih.nci.ncicb.webtree.WebNode;
import gov.nih.nci.ncicb.webtree.WebTree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.tree.DefaultMutableTreeNode;

public class CDEBrowserTree
 extends WebTree
 implements TreeConstants {
 final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
 private String treeType;
 private String functionName;
 private String extraURLParameters = "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes";
 private String contextExcludeListStr = null;

 private boolean showFormsAlphebetically = false;

 public CDEBrowserTree() {
  CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");

  showFormsAlphebetically = new Boolean(params.getShowFormsAlphebetical()).booleanValue();
 }

 public DefaultMutableTreeNode getTree(Hashtable params) throws Exception {
  treeType = (String)params.get("treeType");

  functionName = (String)params.get("functionName");
  contextExcludeListStr = (String)params.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);
  return buildTree(params);
 }

 public DefaultMutableTreeNode buildTree(Hashtable treeParams) throws Exception {
  DBUtil dbHelper = new DBUtil();

  PreparedStatement pstmt = null;
  ResultSet rs = null;
  Connection conn = null;
  Context ctx = null;
  DefaultMutableTreeNode tree = null;
  BaseTreeNode baseNode = null;

  //TimeUtils.recordStartTime("Tree");
  try {
   System.out.println("Tree Start " + TimeUtils.getEasternTime());

   CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
   //String datasourceName = params.getSbrDSN();

   if (dbHelper.getConnectionFromContainer()) {
    conn = dbHelper.getConnection();
   } else {
    throw new Exception("Unable to connect to the database");
   }

   baseNode = new BaseTreeNode(dbHelper, treeParams);
   CDEBrowserTreeCache cache = CDEBrowserTreeCache.getAnInstance(conn, baseNode);
   cache.init(baseNode, treeParams);
   WebNode contexts = new WebNode(dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                  "caDSR Contexts",
                                  "javascript:" + baseNode.getJsFunctionName()
                                     + "('P_PARAM_TYPE=P_PARAM_TYPE&P_IDSEQ=P_IDSEQ&" + baseNode.getExtraURLParameters()
                                     + "')");
   tree = new DefaultMutableTreeNode(contexts);
   List allContexts = cache.getAllContextHolders();

   if (allContexts == null)
    return tree;

   ListIterator contextIt = allContexts.listIterator();

   while (contextIt.hasNext()) {
    ContextHolder currContextHolder = (ContextHolder)contextIt.next();

    Context currContext = currContextHolder.getContext();
    DefaultMutableTreeNode contextNode = currContextHolder.getNode();
    //Need to be removed
    ContextNode ctxNode = new ContextNode(currContext, dbHelper, treeParams);
    //DefaultMutableTreeNode ctxTreeNode = ctxNode.getTreeNode();

    //Adding data template nodes

    DefaultMutableTreeNode tmpLabelNode;
    DefaultMutableTreeNode disLabelNode;
    DefaultMutableTreeNode phaseLabelNode;
    DefaultMutableTreeNode [] templateNodes;
    List otherTempNodes;

    if (Context.CTEP.equals(currContext.getName())) {
     //          cache.setCtepIdSeq(currContext.getConteIdseq());
     //Need to add this to DAO
     cache.initCtepInfo(conn, baseNode, ctxNode.getTemplateTypes(), currContext);

     tmpLabelNode = new DefaultMutableTreeNode(
                       new WebNode(dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));
     List ctepNodes = cache.getAllTemplatesForCtep();
     tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(0));
     tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(1));
     contextNode.add(tmpLabelNode);

     System.out.println("CTEP Templates End " + TimeUtils.getEasternTime());
    } else {
     System.out.println("Other Templates Start " + TimeUtils.getEasternTime());

     otherTempNodes = cache.getTemplateNodes(currContext.getConteIdseq());

     if (otherTempNodes != null && !otherTempNodes.isEmpty()) {
      tmpLabelNode = new DefaultMutableTreeNode(
                        new WebNode(dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));

      Iterator tempIter = otherTempNodes.iterator();

      while (tempIter.hasNext()) {
       tmpLabelNode.add((DefaultMutableTreeNode)tempIter.next());
      }

      contextNode.add(tmpLabelNode);
     }

     System.out.println("Other Templates End " + TimeUtils.getEasternTime());
    }

    //Adding classification nodes
    long startingTime = System.currentTimeMillis();
    System.out.println("Classification Start " + TimeUtils.getEasternTime());
    DefaultMutableTreeNode csNode = cache.getClassificationNodes(currContext.getConteIdseq());

    if (csNode != null)
     contextNode.add(csNode);

    long timeElsp = System.currentTimeMillis() - startingTime;
    System.out.println("Classification Took " + timeElsp);
    //End Adding Classification Node

    //Adding protocols nodes
    //Filtering CTEP context in data element search tree
    System.out.println("Proto forms Start " + TimeUtils.getEasternTime());

    if ((!currContext.getName().equals(Context.CTEP) && treeType.equals(TreeConstants.DE_SEARCH_TREE))
    //Publish Change order
      || (baseNode.isCTEPUser().equals("Yes") && treeType.equals(TreeConstants.DE_SEARCH_TREE))
      || (treeType.equals(TreeConstants.FORM_SEARCH_TREE))) {
     if ((currContext.getName().equals(
             Context.CTEP) && baseNode.isCTEPUser().equals("Yes")) || (!currContext.getName().equals(Context.CTEP))) {
      List protoNodes = cache.getProtocolNodes(currContext.getConteIdseq());

   /** for release 3.0.1, forms without protocol is not displayed, uncomment this
    * code to display them
      DefaultMutableTreeNode noProtocolFormNode =
      cache.getProtocolFormNodeWithNoProtocol(currContext.getConteIdseq());
*/
      DefaultMutableTreeNode protocolFormsLabelNode = null;
      DefaultMutableTreeNode formsLabelNode = null;

   /** for release 3.0.1, forms without protocol is not displayed, uncomment this
    * code to display them
      if ((protoNodes != null && !protoNodes.isEmpty()) || noProtocolFormNode != null ) {
  */
      if ((protoNodes != null && !protoNodes.isEmpty()) ) {
       protocolFormsLabelNode = new DefaultMutableTreeNode(
                                   new WebNode(dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Forms"));
   /** for release 3.0.1, forms without protocol is not displayed, uncomment this
    * code to display them

       // Add form with no protocol
       if (noProtocolFormNode != null ) {
         protocolFormsLabelNode.add(noProtocolFormNode);
       }
*/
       // Add form with protocol
       if (protoNodes != null && !protoNodes.isEmpty()) {
        Iterator tmpIter = protoNodes.iterator();

        while (tmpIter.hasNext()) {
         protocolFormsLabelNode.add((DefaultMutableTreeNode)tmpIter.next());
        }
       }

       contextNode.add(protocolFormsLabelNode);
      }
     }
    }

    System.out.println("Proto forms End " + TimeUtils.getEasternTime());
    //End Add Protocol Nodes

    //Display Catalog

    //Get Publishing Node info
    System.out.println("Publish strat " + TimeUtils.getEasternTime());
    DefaultMutableTreeNode publishNode = cache.getPublishNode(baseNode, currContext, showFormsAlphebetically);

    if (publishNode != null)
     contextNode.add(publishNode);

    System.out.println("Publish end " + TimeUtils.getEasternTime());
    //End Catalog

    tree.add(contextNode);
   }

   System.out.println("Tree End " + TimeUtils.getEasternTime());
  } catch (Exception ex) {
   ex.printStackTrace();

   throw ex;
  } finally {
   try {
    if (rs != null) {
     rs.close();
    }

    if (pstmt != null) {
     pstmt.close();
    }

    if (conn != null) {
     dbHelper.returnConnection();
    }
   } catch (Exception ex) {
    ex.printStackTrace();
   }
  }

  return tree;
 }
}

package gov.nih.nci.ncicb.cadsr.contexttree;

import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.ContextHolder;
import gov.nih.nci.ncicb.cadsr.common.util.TimeUtils;
import gov.nih.nci.ncicb.webtree.WebNode;
import gov.nih.nci.ncicb.webtree.WebTree;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CDEBrowserTree
 extends WebTree
 implements TreeConstants {

 protected Log log =  LogFactory.getLog(CDEBrowserTree.class.getName());
 
 private String treeType;
 private String functionName;
 private String extraURLParameters = StringEscapeUtils.escapeHtml("&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes");
 private String contextExcludeListStr = null;


 public CDEBrowserTree() {
 }

 public DefaultMutableTreeNode getTree(Hashtable params) throws Exception {
  treeType = StringEscapeUtils.escapeHtml((String)params.get("treeType"));

  functionName = StringEscapeUtils.escapeHtml((String)params.get("functionName"));
  contextExcludeListStr = StringEscapeUtils.escapeHtml((String)params.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR));
  return buildTree(params);
 }

 public DefaultMutableTreeNode buildTree(Hashtable treeParams) throws Exception {

  DefaultMutableTreeNode tree = null;

  BaseTreeNode baseNode = null;

  //TimeUtils.recordStartTime("Tree");
  try {
   log.info("Tree Start " + TimeUtils.getEasternTime());

   baseNode = new BaseTreeNode(treeParams);
   CDEBrowserTreeCache cache = CDEBrowserTreeCache.getAnInstance();
   cache.init(baseNode, treeParams);
   WebNode contexts = new WebNode(cache.getIdGen().getNewId(),
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

    //Adding data template nodes

    DefaultMutableTreeNode tmpLabelNode;
    DefaultMutableTreeNode otherTempNodes;

    if (Context.CTEP.equals(currContext.getName())) {

     cache.initCtepInfo(baseNode,currContext);

     tmpLabelNode = new DefaultMutableTreeNode(
                       new WebNode(cache.getIdGen().getNewId(), "Protocol Form Templates"));
     List ctepNodes = cache.getAllTemplatesForCtep();
     tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(0));
     tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(1));
     contextNode.add(tmpLabelNode);

     log.info("CTEP Templates End " + TimeUtils.getEasternTime());
    } else {
     log.info("Other Templates Start " + TimeUtils.getEasternTime());

     otherTempNodes = cache.getTemplateNodes(currContext.getConteIdseq());

     if (otherTempNodes != null ) {
      contextNode.add(otherTempNodes);
     }

     log.info("Other Templates End " + TimeUtils.getEasternTime());
    }

    //Adding classification nodes
    long startingTime = System.currentTimeMillis();
    log.info("Classification Start " + TimeUtils.getEasternTime());
    DefaultMutableTreeNode csNode = cache.getClassificationNodes(currContext.getConteIdseq());

    if (csNode != null)
     contextNode.add(csNode);

    long timeElsp = System.currentTimeMillis() - startingTime;
    log.info("Classification Took " + timeElsp);
    //End Adding Classification Node

    //Adding protocols nodes
    //Filtering CTEP context in data element search tree
    log.info("Proto forms Start " + TimeUtils.getEasternTime());

   /** Remove to TT 1892
    if ((!currContext.getName().equals(Context.CTEP) && treeType.equals(TreeConstants.DE_SEARCH_TREE))
    //Publish Change order
      || (baseNode.isCTEPUser().equals("Yes") && treeType.equals(TreeConstants.DE_SEARCH_TREE))
      || (treeType.equals(TreeConstants.FORM_SEARCH_TREE))) {
     if ((currContext.getName().equals(
             Context.CTEP) && baseNode.isCTEPUser().equals("Yes")) || (!currContext.getName().equals(Context.CTEP)))
             {
             
    **/             
      List protoNodes = cache.getProtocolNodes(currContext.getConteIdseq());

   /** for release 3.0.1, forms without protocol is not displayed, uncomment this
    * code to display them
      DefaultMutableTreeNode noProtocolFormNode =
      cache.getProtocolFormNodeWithNoProtocol(currContext.getConteIdseq());
*/
      DefaultMutableTreeNode protocolFormsLabelNode = null;

   /** for release 3.0.1, forms without protocol is not displayed, uncomment this
    * code to display them
      if ((protoNodes != null && !protoNodes.isEmpty()) || noProtocolFormNode != null ) {
  */
      if ((protoNodes != null && !protoNodes.isEmpty()) ) {
       protocolFormsLabelNode = new DefaultMutableTreeNode(
                                   new WebNode(cache.getIdGen().getNewId(), "Protocol Forms"));
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
    /** } TT 1892 
    }**/

    log.info("Proto forms End " + TimeUtils.getEasternTime());
    //End Add Protocol Nodes

    //Display Catalog

    //Get Publishing Node info
    log.info("Publish strat " + TimeUtils.getEasternTime());
    DefaultMutableTreeNode publishNode = cache.getPublishNode(currContext);

    if (publishNode != null)
     contextNode.add(publishNode);

    log.info("Publish end " + TimeUtils.getEasternTime());
    //End Catalog

    tree.add(contextNode);
   }

   log.info("Tree End " + TimeUtils.getEasternTime());
  } catch (Exception ex) {
   ex.printStackTrace();

   throw ex;
  }

  return tree;
 }
}

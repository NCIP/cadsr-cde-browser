package gov.nih.nci.ncicb.webtree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProtocolFormTemplateNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ProtocolFormTemplateNode.class.getName());
   public ProtocolFormTemplateNode() {
   }
   public ProtocolFormTemplateNode(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
   protected void loadChildren() {
              // create dummy tree nodes for example
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
      try {
      List protoFormNodes = treeService.getAllTemplateNodesForCTEP(getAction());
      for (Iterator nodeIter = protoFormNodes.iterator(); nodeIter.hasNext(); ) 
         super.addLeaf((LazyActionTreeNode)nodeIter.next());
      
      } catch (Exception e) {
      log.error("Unable to retrieve CTEP protocol forms", e);   
      }
      isChildrenLoaded = true;

   }
   
  @Override
  public int getChildCount()  {
    if (!isChildrenLoaded) loadChildren();
    List childs = super.getChildren();
    if (childs == null) return 0;    
    else return childs.size(); 
  }
}

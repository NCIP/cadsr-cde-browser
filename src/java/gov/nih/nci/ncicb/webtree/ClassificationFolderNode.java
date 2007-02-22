package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassificationFolderNode extends LazyActionTreeNode {
    
   protected Log log = LogFactory.getLog(ClassificationFolderNode.class);
   
   public ClassificationFolderNode() {
   }
   
   public ClassificationFolderNode(String type, String description, String actionURL, boolean leaf) {
      super(type, description, leaf);
      setAction(actionURL);
   }
   
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
      try {
         treeService.addClassificationNode(this, this.getAction());
      } catch (Exception e) {
       log.error("Unable to retrieve classification schemes for" + this.getDescription(), e);
      }

      isChildrenLoaded = true;
  }
  
  
}





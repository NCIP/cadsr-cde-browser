package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CSIRegStatusNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(CSIRegStatusNode.class.getName());
   public CSIRegStatusNode() {
   }
   public CSIRegStatusNode(String type, String description, String actionURL, String id, boolean leaf) {
           super(type, description, actionURL, id, leaf);
   }
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
         treeService.loadCSIRegStatusNodes(this);
      } catch (Exception e) {
       log.error("Unable to classification scheme items for " + this.getDescription(), e);
    }

     isChildrenLoaded = true;
  }
}




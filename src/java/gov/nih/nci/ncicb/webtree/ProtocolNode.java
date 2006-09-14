package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProtocolNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ProtocolNode.class.getName());
   public ProtocolNode() {
   }
   public ProtocolNode(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
         treeService.addProtocolNodes(this, this.getAction());
      } catch (Exception e) {
       log.error("Unable to retrieve protocol forms for" + this.getDescription(), e);
    }

     isChildrenLoaded = true;
  }
}





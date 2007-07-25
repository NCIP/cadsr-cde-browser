package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassSchemeNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ClassSchemeNode.class.getName());
   public ClassSchemeNode() {
   }
   public ClassSchemeNode(String type, String description, String actionURL, String id, boolean leaf) {
           super(type, description,actionURL, id, leaf);
   }
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
         treeService.loadCSNodes(this, this.getIdentifier());
      } catch (Exception e) {
       log.error("Unable to retrieve protocol forms for" + this.getDescription(), e);
    }

     isChildrenLoaded = true;
  }
  
  public void markChildrenLoaded() {
      isChildrenLoaded = true;      
  }
  
  protected List<LazyActionTreeNode> loadChildNodes() {
    this.loadChildren();
    return super.getChildrenList();
  }  
  
//  @Override
//  public int getChildCount() {
//    if (isLoaded()) return super.getChildCount();
//    if (isExpanded()) return getChildren().size();
//    // return 1 to initiate lazy loading
//    return 1;
//  }
}





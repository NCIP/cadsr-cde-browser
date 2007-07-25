package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassSchemeRegStatusNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ClassSchemeRegStatusNode.class.getName());
   public ClassSchemeRegStatusNode() {
   }
   public ClassSchemeRegStatusNode(String type, String description, String actionURL, String id, boolean leaf) {
           super(type, description, actionURL, id, leaf);
   }
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
         treeService.loadRegStatusCSNodes(this);
      } catch (Exception e) {
       log.error("Unable to retrieve classification scheme items for" + this.getDescription(), e);
    }

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





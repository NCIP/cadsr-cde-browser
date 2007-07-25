package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PublishedFormNodeByProtocol extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(PublishedFormNodeByProtocol.class.getName());
   public PublishedFormNodeByProtocol() {
   }
   public PublishedFormNodeByProtocol(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
   protected void loadChildren() {
       CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
       treeService.addPublishedFormbyProtocolNode(this, this.getAction());
      } catch (Exception e) {
       log.error("Unable to retrieve CTEP protocol forms order by protocol", e);   
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





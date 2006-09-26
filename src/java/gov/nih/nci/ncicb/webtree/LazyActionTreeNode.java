package gov.nih.nci.ncicb.webtree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTreeData;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;

import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

public class LazyActionTreeNode extends TreeNodeBase {
   /**
    * serial id for serialisation versioning
    */
   private static final long serialVersionUID = 1L;
   private static ApplicationServiceLocator appServiceLocator = null;
   private String    _action;      // * optional - url action for node *
   protected Log log = LogFactory.getLog(LazyActionTreeNode.class.getName());
   String treeCrumbs;
   protected boolean isChildrenLoaded = false;
   private String _toolTip;
   
   public LazyActionTreeNode() {
   }

   public LazyActionTreeNode(String type, String description, boolean leaf) {
      super( type, description, leaf );
       
   }
   public LazyActionTreeNode(String type, String description, String actionURL, boolean leaf) {
      super(type, description, leaf );
      _action = actionURL;
   }

   public LazyActionTreeNode(String type, String description, String actionURL, String identifier, boolean leaf) {
     super(type, description, identifier, leaf);
    _action = actionURL;
   }
/**
   public List getChildren() {
        // if there are no children, try and retrieve them
           if (super.getChildren().size() == 0) {
              // create dummy tree nodes for example
              int id = Integer.parseInt(getIdentifier());
                   for(int i = 0; i < id; i++) {
                  super.getChildren().add(new LazyActionTreeNode("document","" + id, "" + id,false));
              }
           }

           return super.getChildren();
   }
*/
   public void setAction(String action) {
      this._action = action;
   }

   public String getAction() {
   if (_action == null) return null;
   String actionWithTreeCrumbs = _action.trim();
      if ( actionWithTreeCrumbs.endsWith("')")) {
         actionWithTreeCrumbs = actionWithTreeCrumbs.substring(0, actionWithTreeCrumbs.length()-2).trim()
         + "&treeBreadCrumbs=" + getTreeCrumbs() +"')";
         
      }
      return actionWithTreeCrumbs;
   }
   
   public String getPath() {
      return "";
   }
   public List getChildren() {
        // if there are no children, try and retrieve them
        if (!isChildrenLoaded)
            loadChildren();
       return super.getChildren();
   }
   public void setAppServiceLocator(ApplicationServiceLocator appServiceLocator) {
      this.appServiceLocator = appServiceLocator;
   }

   public ApplicationServiceLocator getAppServiceLocator() {
      return appServiceLocator;
   }
   
   public void addLeaf (LazyActionTreeNode leafNode) {
      super.getChildren().add(leafNode);
      leafNode.setTreeCrumbs(this.getTreeCrumbs() + ">>" + leafNode.getTreeCrumbs());
   }

   public void setTreeCrumbs(String treeCrumbs) {
   
      treeCrumbs = StringUtils.strReplace(treeCrumbs,"'","*??*");
      //This is done to get around " in the names
      treeCrumbs = StringUtils.strReplace(treeCrumbs,"\"","&quot;");
         
      this.treeCrumbs = treeCrumbs;
   }

   public String getTreeCrumbs() {
      //set default root tree crumbs to be the same as the node description
      if (treeCrumbs == null)
         treeCrumbs = this.getDescription();
      return treeCrumbs;
   }
   
   protected void loadChildren() {
   }
   
   public int getChildCount()  {
   if (!isChildrenLoaded)
      loadChildren();
   
   List children = super.getChildren();
      
   if (children == null)
     return 0;    
   else 
      return children.size(); 
   }


   public void setToolTip(String toolTip) {
      this._toolTip = toolTip;
   }

   public String getToolTip() {
      return _toolTip;
   }
}

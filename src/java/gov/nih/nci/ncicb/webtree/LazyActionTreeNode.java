package gov.nih.nci.ncicb.webtree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTreeData;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;

import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.tree2.TreeModel;
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
   private LazyActionTreeModel treeModel;
   private LazyActionTreeNode parent = null;
   private boolean loaded = false;
   private boolean expanded = false;
   private boolean selected = false;
   private List children = null;
   
   public LazyActionTreeNode() {
   }

   public LazyActionTreeNode(String type, String description, boolean leaf) {
      super( type, description, leaf );
      children = new ArrayList(); 
   }
   public LazyActionTreeNode(String type, String description, String actionURL, boolean leaf) {
      super(type, description, leaf );
      _action = actionURL;
      children = new ArrayList();
   }

   public LazyActionTreeNode(String type, String description, String actionURL, String identifier, boolean leaf) {
     super(type, description, identifier, leaf);
    _action = actionURL;
    children = new ArrayList();
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
   
/* -----------------------------------------------------------------------------
   public List getChildren() {
        // if there are no children, try and retrieve them
        if (!isChildrenLoaded)
            loadChildren();
       return super.getChildren();
   }
----------------------------------------------------------------------------- */

   public void setAppServiceLocator(ApplicationServiceLocator appServiceLocator) {
      this.appServiceLocator = appServiceLocator;
   }

   public ApplicationServiceLocator getAppServiceLocator() {
      return appServiceLocator;
   }
   
   public void addLeaf (LazyActionTreeNode leafNode) {
      super.getChildren().add(leafNode);
      leafNode.setTreeCrumbs(this.getTreeCrumbs() + ">>" + leafNode.getTreeCrumbs());
      leafNode.setParent(this);
      leafNode.setTreeModel(this.getTreeModel());
      if (!children.contains(leafNode)) children.add(leafNode);
      setLoaded(true);
   }
   
   public void addChild (LazyActionTreeNode child) {
      //super.getChildren().add(leafNode);
      child.setTreeCrumbs(this.getTreeCrumbs() + ">>" + child.getTreeCrumbs());
      child.setParent(this);
      child.setTreeModel(this.getTreeModel());
      if (!children.contains(child)) children.add(child);
      setLoaded(true);
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
   
  @Override
  public int getChildCount() {
    if (loaded) return super.getChildCount();
    if (expanded) return getChildren().size();
    // return 1 to initiate lazy loading
    return 1;
  }
   
/* -----------------------------------------------------------------------------
   public int getChildCount()  {
   if (!isChildrenLoaded)
      loadChildren();
   
   List childs = super.getChildren();
      
   if (childs == null)
     return 0;    
   else 
      return childs.size(); 
   }
//----------------------------------------------------------------------------- */

   public void setToolTip(String toolTip) {
      this._toolTip = toolTip;
   }

   public String getToolTip() {
      return _toolTip;
   }

  public LazyActionTreeModel getTreeModel() {
    return treeModel;
  }

  public void setTreeModel(LazyActionTreeModel treeModel) {
    this.treeModel = treeModel;
  }

  public LazyActionTreeNode getParent() {
    return parent;
  }

  public void setParent(LazyActionTreeNode parent) {
    this.parent = parent;
  }
  
  public int getIndex() {
    return parent == null ? 0 : parent.getChildren().indexOf(this); 
  }
  
  public String getNodePath()
  {
    if (parent == null) return "0";
    return parent.getNodePath().concat(TreeModel.SEPARATOR).concat(Integer.toString(getIndex()));
  }
  
  public boolean isExpanded() {
//    if (treeModel == null) return true;
//    
//    return treeModel.getTreeState().isNodeExpanded(getNodePath());
    return this.expanded;
  }
  
  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
    if (expanded && !loaded)
    {
      getChildren().addAll(loadChildNodes());
      loaded = true;
    }
  }
  
  public boolean isLoaded() {
    return this.loaded;
  }
  
  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public List<LazyActionTreeNode> getChildren() {
    return (List<LazyActionTreeNode>)super.getChildren();
  }
  
  public boolean isSelected() {
    return this.selected;
  }
  
  public void setSelected(boolean selected) {
    this.selected = selected;
  }
  
  protected List<LazyActionTreeNode> loadChildNodes() {
    return Collections.emptyList();
  }
  
  public List getChildrenList() {
    return this.children;
  }
}

package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;


import gov.nih.nci.ncicb.cadsr.servicelocator.spring.SpringObjectLocatorImpl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeState;

/**
 * @author Jane Jiang
 * @version: $Id: TreeBacker.java,v 1.2 2007-02-22 21:45:26 rokickik Exp $
 */
public class TreeBacker implements Serializable {
    
   private static final long serialVersionUID = 1L;

   private final CDEBrowserTreeData treeData = CDEBrowserTreeData.getInstance();
   
   private TreeModelBase treeModel;

   private HtmlTree tree;

   private String selectedNode;

   public TreeBacker() {
   }

   public TreeModel getTreeModel() {
      if (treeModel == null) {
          resetTree();
      }
      return treeModel;
   }

   public String resetTree()   {
       treeModel = new TreeModelBase(treeData.getTreeData());
       treeModel.getTreeState().toggleExpanded("0");
       treeModel.getTreeState().setTransient(true);
       return null;
   }
   
   public String refreshTree()   {
       treeData.refreshTree();
       resetTree();
       return null;
   }

   public void setTree(HtmlTree tree) {
      this.tree = tree;
   }

   public HtmlTree getTree() {
      return tree;
   }

}

package gov.nih.nci.ncicb.cadsr.contexttree;

import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;

import java.util.Map;

public class CsCSICatetegoryHolder {
   public CsCSICatetegoryHolder() {
   }
   private LazyActionTreeNode node= null;
   private Map categoryHolder = null;

   public Map getCategoryHolder()
   {
     return categoryHolder;
   }

   public void setCategoryHolder(Map categoryHolder)
   {
     this.categoryHolder = categoryHolder;
   }

   public LazyActionTreeNode getNode()
   {
     return node;
   }

   public void setNode(LazyActionTreeNode node)
   {
     this.node = node;
   }
}

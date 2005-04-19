package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.ContextHolder;

import javax.swing.tree.DefaultMutableTreeNode;

public class ContextHolderTransferObject implements ContextHolder
{
  private Context context;
  private DefaultMutableTreeNode node; 
  

  public void setContext(Context context)
  {
    this.context = context;
  }


  public Context getContext()
  {
    return context;
  }


  public void setNode(DefaultMutableTreeNode node)
  {
    this.node = node;
  }


  public DefaultMutableTreeNode getNode()
  {
    return node;
  }
}
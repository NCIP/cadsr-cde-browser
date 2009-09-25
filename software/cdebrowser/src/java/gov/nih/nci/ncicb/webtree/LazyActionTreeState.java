/*
 * LazyActionTreeState.java
 *
 * Created on July 4, 2007, 5:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.nih.nci.ncicb.webtree;

import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeState;

/**
 *
 * @author bollers
 */
public class LazyActionTreeState implements TreeState
{
  private LazyActionTreeNode rootNode;
  private boolean trans = true;
  
  /**
   * Constructor
   * @param rootNode
   */
  public LazyActionTreeState(LazyActionTreeNode rootNode)
  {
    this.rootNode = rootNode;
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#collapsePath(java.lang.String[])
   */
  public void collapsePath(String[] nodePath)
  {
    for (String path : nodePath)
      findNode(path).setExpanded(false);
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#expandPath(java.lang.String[])
   */
  public void expandPath(String[] nodePath)
  {
    for (String path : nodePath)
      findNode(path).setExpanded(true);
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#isNodeExpanded(java.lang.String)
   */
  public boolean isNodeExpanded(String nodePath)
  {
    return findNode(nodePath).isExpanded();
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#isSelected(java.lang.String)
   */
  public boolean isSelected(String nodePath)
  {
    return findNode(nodePath).isSelected();
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#setSelected(java.lang.String)
   */
  public void setSelected(String nodePath)
  {
    findNode(nodePath).setSelected(true);
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#isTransient()
   */
  public boolean isTransient()
  {
    return trans;
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#setTransient(boolean)
   */
  public void setTransient(boolean trans)
  {
    this.trans = trans;
  }
  
  /**
   * @see org.apache.myfaces.custom.tree2.TreeState#toggleExpanded(java.lang.String)
   */
  public void toggleExpanded(String nodePath)
  {
    LazyActionTreeNode node = findNode(nodePath);
    node.setExpanded(!node.isExpanded());
  }
  
  private LazyActionTreeNode findNode(String nodePath)
  {
    LazyActionTreeNode currentNode = null; 
    for (String elem : nodePath.split(TreeModel.SEPARATOR))
    {
      int index = Integer.parseInt(elem);
      if (currentNode == null)
      {
        if (index != 0) return null;
        currentNode = rootNode;
      }
      else
        currentNode = currentNode.getChildren().get(index);
    }
    
    return currentNode;
  }
}

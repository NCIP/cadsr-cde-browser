/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.contexttree;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

public class CsCsiCategorytHolder 
{
  
  private DefaultMutableTreeNode node= null;
  private Map categoryHolder = null;
  public CsCsiCategorytHolder()
  {
  }

  public Map getCategoryHolder()
  {
    return categoryHolder;
  }

  public void setCategoryHolder(Map categoryHolder)
  {
    this.categoryHolder = categoryHolder;
  }

  public DefaultMutableTreeNode getNode()
  {
    return node;
  }

  public void setNode(DefaultMutableTreeNode node)
  {
    this.node = node;
  }
}
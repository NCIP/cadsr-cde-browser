/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

/*
 * LazyActionTreeModel.java
 *
 * Created on July 4, 2007, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.nih.nci.ncicb.webtree;

import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeStateBase;
/**
 *
 * @author bollers
 */
public class LazyActionTreeModel extends TreeModelBase {
  
  /** Creates a new instance of LazyActionTreeModel */
  public LazyActionTreeModel(LazyActionTreeNode root) {
    super(root);
    super.setTreeState(new LazyActionTreeState(root));
  }
}

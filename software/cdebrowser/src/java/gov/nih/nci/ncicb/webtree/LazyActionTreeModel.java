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

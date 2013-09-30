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

package gov.nih.nci.ncicb.webtree;
import gov.nih.nci.ncicb.cadsr.contexttree.service.CDEBrowserTreeService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassificationFolderNode extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(ClassificationFolderNode.class.getName());
   public ClassificationFolderNode() {
   }
   public ClassificationFolderNode(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
   public void loadChildren() {
      CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
       try {
       //to do change this line
         treeService.addClassificationNode(this, this.getAction());
      } catch (Exception e) {
       log.error("Unable to retrieve classification schemes for" + this.getDescription(), e);
    }

     isChildrenLoaded = true;
  }
  
  protected List<LazyActionTreeNode> loadChildNodes() {
    this.loadChildren();
    return super.getChildrenList();
  }  
  
//  @Override
//  public int getChildCount() {
//    if (isLoaded()) return super.getChildCount();
//    if (isExpanded()) return getChildren().size();
//    // return 1 to initiate lazy loading
//    return 1;
//  }
}





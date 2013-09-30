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

public class PublishedFormNodeAlpha extends LazyActionTreeNode {
   protected Log log = LogFactory.getLog(PublishedFormNodeAlpha.class.getName());
   public PublishedFormNodeAlpha() {
   }
   public PublishedFormNodeAlpha(String type, String description, String actionURL, boolean leaf) {
           super(type, description, leaf);
           setAction(actionURL);
   }
  protected void loadChildren()     {   // if there are no children, try and retrieve them
         CDEBrowserTreeService treeService = getAppServiceLocator().findTreeService();
         try {
         //to do change this line
         treeService.addPublishedFormbyAlphaNode(this, this.getAction());
        } catch (Exception e) {
         log.error("Unable to retrieve CTEP protocol forms by alphabetic order", e);   
      }
      
     isChildrenLoaded = true;
      
  }
   
  @Override
  public int getChildCount()  {
    if (!isChildrenLoaded) loadChildren();
    List childs = super.getChildren();
    if (childs == null) return 0;    
    else return childs.size(); 
  }
}




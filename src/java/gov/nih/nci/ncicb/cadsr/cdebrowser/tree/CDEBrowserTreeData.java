package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.servicelocator.spring.SpringObjectLocatorImpl;
import gov.nih.nci.ncicb.cadsr.util.SessionHelper;
import gov.nih.nci.ncicb.webtree.ContextNode;
import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CDEBrowserTreeData implements Serializable {
    
   private static final long serialVersionUID = 1L;

   private static Log log = LogFactory.getLog(CDEBrowserTreeData.class);
   
   private final ReentrantLock refreshLock = new ReentrantLock();
   
   private AbstractDAOFactory daoFactory = null;

   private LazyActionTreeNode root;
   private LazyActionTreeNode rootTest;
   private LazyActionTreeNode rootTraining;
   private LazyActionTreeNode rootTestAndTraining;
   
   public CDEBrowserTreeData() {
   }
   
   public static CDEBrowserTreeData getInstance() {
       return (CDEBrowserTreeData)SpringObjectLocatorImpl.getObject("treeData");
   }
   
   private LazyActionTreeNode createRoot() {
       return new LazyActionTreeNode("Context Folder", "caDSR Contexts",
        "javascript:performAction('P_PARAM_TYPE=CONTEXT" +
        "&NOT_FIRST_DISPLAY=1&performQuery=yes')",
        false);
   }
   
   private LazyActionTreeNode buildTree() {
      log.info("Building CDE Browser tree start ....");
      ContextDAO dao = daoFactory.getContextDAO();

      LazyActionTreeNode root = createRoot();
      LazyActionTreeNode rootTest = createRoot();
      LazyActionTreeNode rootTraining = createRoot();
      LazyActionTreeNode rootTestAndTraining = createRoot();
             
      Collection contexts = dao.getAllContexts();
      try {
 
          //first build a text folder node context.
          for (Iterator iter = contexts.iterator(); iter.hasNext(); ) {
             Context context = (Context)iter.next();
             
             LazyActionTreeNode contextNode =
                new ContextNode("Context Folder", context.getName() + " (" + context.getDescription() + ")",
                  "javascript:performAction('P_PARAM_TYPE=CONTEXT&P_IDSEQ=" +
                  context.getConteIdseq() +
                  "&P_CONTE_IDSEQ=" +  context.getConteIdseq() +
                  "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes"
                  +"')", context.getConteIdseq(), false);
             
             String desc = context.getName().toLowerCase();
             if (desc.equals("test")) {
                 rootTest.addLeaf(contextNode);
                 rootTestAndTraining.addLeafWithoutCrumbs(contextNode);
             }
             else if (desc.equals("training")) {
                 rootTraining.addLeaf(contextNode);
                 rootTestAndTraining.addLeafWithoutCrumbs(contextNode);
             }
             else {
                 root.addLeaf(contextNode);
                 rootTest.addLeafWithoutCrumbs(contextNode);
                 rootTraining.addLeafWithoutCrumbs(contextNode);
                 rootTestAndTraining.addLeafWithoutCrumbs(contextNode);
             }
             
             log.info("Loading all descendants for "+context.getName());
             loadDescendants(contextNode);
           }

      } 
      catch (Exception e) {
         log.error("Exception caught when building the tree", e);
         throw new RuntimeException(e);
      }

      this.root = root;
      this.rootTest = rootTest;
      this.rootTraining = rootTraining;
      this.rootTestAndTraining = rootTestAndTraining;
          
      log.info("Finished Building UML Browser trees");
      
      return root;
   }

   private void loadDescendants(LazyActionTreeNode node) {
       List<LazyActionTreeNode> children = node.getChildren();
       for(LazyActionTreeNode child : children) {
           loadDescendants(child);
       }
   }
   
   /**
    * Completely reload the tree from the persistent storage.
    */
   public void refreshTree() {
      if (refreshLock.tryLock()) {
          try {
              buildTree();
          }
          finally {
              refreshLock.unlock();
          }
          return;
      }

      try {
          // wait for the other thread to refresh the tree
          refreshLock.lock();
      }
      finally {
          // we didn't really want the lock, so release it
          refreshLock.unlock();
      }
   }

   /**
    * Return the root node of the tree which is customized to the
    * user's preference. 
    */
   public LazyActionTreeNode getTreeData() {
       
       if (root == null) {
           log.warn("Tree not initialized, trying to init now...");
           refreshTree();
       }
       
       boolean excludeTraining = true;
       boolean excludeTest = true;
       FacesContext facesContext = FacesContext.getCurrentInstance();
       HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
       DataElementSearchBean desb =(DataElementSearchBean) (SessionHelper.getInfoBean(session, "desb"));
       if (desb != null) {
          excludeTest = desb.isExcludeTestContext();
          excludeTraining = desb.isExcludeTrainingContext();
       }
       
       // TODO: this could be more robust as a map, but for now...
       if (excludeTest) {
           if (excludeTraining) {
               return root;
           }
           return rootTraining;
       }
       else if (excludeTraining) {
           return rootTest;
       }
       else {
           return rootTestAndTraining;
       }
   }
   
   /**
    * Injected by Spring configuration.
    */
   public void setDaoFactory(AbstractDAOFactory daoFactory) {
     this.daoFactory = daoFactory;
   }
}

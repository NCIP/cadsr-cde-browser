package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.SessionHelper;
import gov.nih.nci.ncicb.webtree.ContextNode;
import gov.nih.nci.ncicb.webtree.LazyActionTreeModel;
import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.tree2.TreeModelBase;


//import org.apache.catalina.connector.Request;


public class CDEBrowserTreeData implements Serializable {
   private static final long serialVersionUID = 1L;

   protected Log log = LogFactory.getLog(CDEBrowserTreeData.class.getName());
   private static AbstractDAOFactory daoFactory = null;
   private static ApplicationServiceLocator appServiceLocator = null;
   LazyActionTreeNode treeData = null;
   TreeModelBase treeModel;

   public CDEBrowserTreeData() {
   }
   public CDEBrowserTreeData(TreeModelBase treeMdl) {
      treeModel = treeMdl;
   }

   private LazyActionTreeNode buildTree() {
      log.info("Building CDE Browser tree start ....");
      ContextDAO dao = daoFactory.getContextDAO();

      LazyActionTreeNode contextFolder =
         new LazyActionTreeNode("Context Folder", "caDSR Contexts",
             "javascript:performAction('P_PARAM_TYPE=CONTEXT" +
             "&NOT_FIRST_DISPLAY=1&performQuery=yes')",
             false);
      contextFolder.setParent(null);
      contextFolder.setTreeModel(new LazyActionTreeModel(contextFolder));
      boolean excludeTraining = true;
      boolean excludeTest = true;
      boolean noBuildException = true;
             
      Collection contexts = dao.getAllContexts();
      try {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
      DataElementSearchBean desb =(DataElementSearchBean) (SessionHelper.getInfoBean(session, "desb"));
         if (desb != null) {
            excludeTest = desb.isExcludeTestContext();
            excludeTraining = desb.isExcludeTrainingContext();
      }
 
      //first build a text folder node context.

      for (Iterator iter = contexts.iterator(); iter.hasNext(); ) {
         Context context = (Context)iter.next();
         // if this context is not excluded by user preference
         if ((context.getName().equalsIgnoreCase("test") && excludeTest)
         || (context.getName().equalsIgnoreCase("training") && excludeTraining))
            continue;
         
         LazyActionTreeNode contextNode =
            new ContextNode("Context Folder", context.getName() + " (" + context.getDescription() + ")",
              "javascript:performAction('P_PARAM_TYPE=CONTEXT&P_IDSEQ=" +
              context.getConteIdseq() +
              "&P_CONTE_IDSEQ=" +  context.getConteIdseq() +
              "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes"
              +"')",
              context.getConteIdseq(),  false);
         contextFolder.addLeaf(contextNode);
       }

      } catch (Exception e) {
         noBuildException = false;
         log.error("Exception caught when building the tree", e);
         throw new RuntimeException(e);
      }
      contextFolder.setLoaded(noBuildException);
      contextFolder.setExpanded(noBuildException);
      log.info("Finished Building UML Browser tree");

      return contextFolder;

   }
   public void setAppServiceLocator(ApplicationServiceLocator appServiceLocator) {
      this.appServiceLocator = appServiceLocator;
   }

   public ApplicationServiceLocator getAppServiceLocator() {
      return appServiceLocator;
   }

   public void refreshTree()   {
      setTreeData(this.buildTree());
   }

   public synchronized  void setTreeData(LazyActionTreeNode treeData) {
      this.treeData = treeData;
   }

   public LazyActionTreeNode getTreeData() {
      if (treeData == null)
         setTreeData(buildTree());
      return treeData;
   }
   public void setDaoFactory(AbstractDAOFactory daoFactory) {
     this.daoFactory = daoFactory;
   }

   public AbstractDAOFactory getDaoFactory() {
     return daoFactory;
   }
}

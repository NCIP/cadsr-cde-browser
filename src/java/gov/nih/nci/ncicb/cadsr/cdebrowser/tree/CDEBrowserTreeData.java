package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.TreeUtils;
import gov.nih.nci.ncicb.webtree.ContextNode;
import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;

import gov.nih.nci.ncicb.webtree.ProtocolFormTemplateNode;
//import org.apache.catalina.connector.Request;

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.context.servlet.SessionMap;
import org.apache.myfaces.custom.tree2.TreeModelBase;

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
             "javascript:classSearchAction('P_PARAM_TYPE=Context')",
             false);
             
      Collection contexts = dao.getAllContexts();
      try {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ServletRequest request =  (ServletRequest) facesContext.getExternalContext().getRequest();
      String treeParam = request.getParameter("treeParams");
      String contextToExclude =(String) TreeUtils.parseParameters(treeParam).get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);

      //first build a text folder node context.

         for (Iterator iter = contexts.iterator(); iter.hasNext(); ) {
            Context context = (Context)iter.next();
            // if this context is not excluded by user preference
            if (contextToExclude.indexOf(context.getName().toUpperCase()) <0) {
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

          }

      } catch (Exception e) {
         log.error("Exception caught when building the tree", e);
         throw new RuntimeException(e);
      }
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

package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.impl.CDEBrowserTreeServiceImpl;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;

import java.sql.Connection;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

public class CDEBrowserTreeCache 
{
                                   
  private final String CTEP="CTEP";
  
  
  public CDEBrowserTreeCache()
  {
  }
  //All crf by contextId - Protocols(List) -crf(Collection) 
  private Map allFormsWithProtocol = null;
  private Map allFormsWithNoProtocol = null;
  private Map allTemplatesByContext = null;
  private List allTemplatesForCtep = null;  
  private List allContextHolders = null;
  private TreeIdGenerator idGen = new TreeIdGenerator();
  private DefaultMutableTreeNode publishNode = null;
  private Map allClassificationNodes = null;
  private static ApplicationServiceLocator appServiceLocator = null;
  
  public static CDEBrowserTreeCache getAnInstance(Connection conn,BaseTreeNode baseTree) throws Exception
  {

      CDEBrowserTreeCache cache = new CDEBrowserTreeCache();
      return cache;
  }
  public List getProtocolNodes(String contextIdSeq)
  {
    return (List)allFormsWithProtocol.get(contextIdSeq);

  }
  
  public DefaultMutableTreeNode  getTemplateNodes(String contextIdSeq)
  {
    return (DefaultMutableTreeNode)allTemplatesByContext.get(contextIdSeq);
  }
  
 public DefaultMutableTreeNode getClassificationNodes(String contextIdSeq)
  {
    return (DefaultMutableTreeNode)allClassificationNodes.get(contextIdSeq);
  }
  
  public DefaultMutableTreeNode getProtocolFormNodeWithNoProtocol(String contextIdSeq)
  {
    return (DefaultMutableTreeNode)allFormsWithNoProtocol.get(contextIdSeq);
  }
  public void init(BaseTreeNode baseTree,Hashtable treeParams) throws Exception
  {
   System.out.println("Init start"+TimeUtils.getEasternTime());
    String contextExcludeListStr = (String)treeParams.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);   
    allContextHolders = appServiceLocator.findTreeService().getContextNodeHolders(baseTree,idGen,contextExcludeListStr);
    allTemplatesByContext = appServiceLocator.findTreeService().getAllContextTemplateNodes(baseTree,idGen);
    //setDataTemplateNodes(conn,baseTree);
    //setFormNodes(conn,baseTree);
    //setFormByProtocolNodes(conn,baseTree);
    List protocolNodes = appServiceLocator.findTreeService().getAllContextProtocolNodes(baseTree,idGen);
    allFormsWithNoProtocol = (Map) protocolNodes.get(0);
    allFormsWithProtocol = (Map) protocolNodes.get(1);
    
    //create classification nodes
    allClassificationNodes = appServiceLocator.findTreeService().getAllClassificationNodes(baseTree, idGen);
    
    //setCtepTemplateCtepNodes(conn,baseTree);
  System.out.println("Init end"+TimeUtils.getEasternTime());
  }
  
  public void initCtepInfo(Connection conn,BaseTreeNode baseTree,List templateTypes, Context ctepContext) throws Exception
  {
   System.out.println("InitCtep start"+TimeUtils.getEasternTime());
//    setCtepTemplateCtepNodes(conn,baseTree,templateTypes, ctepContext);
   allTemplatesForCtep = appServiceLocator.findTreeService().getAllTemplateNodesForCTEP(baseTree, idGen, ctepContext, templateTypes);
   System.out.println("InitCtep end"+TimeUtils.getEasternTime());
  }  
  
  private void clearCache()
  {
    
  }


  public List getAllTemplatesForCtep()
  {
    return allTemplatesForCtep;
  }

  public void setAllTemplatesForCtep(List allTemplatesForCtep)
  {
    this.allTemplatesForCtep = allTemplatesForCtep;
  }


  public void setAllContextHolders(List allContextHolders)
  {
    this.allContextHolders = allContextHolders;
  }


  public List getAllContextHolders()
  {
    return allContextHolders;
  }


  public DefaultMutableTreeNode getPublishNode(BaseTreeNode baseTree, 
  Context currContext,  boolean showFormsAlphebetically) throws Exception
  {
   
    return appServiceLocator.findTreeService().getPublishingNode(baseTree, idGen, currContext, showFormsAlphebetically);
  }


  public void set_allContextHolders(List allContextHolders)
  {
    this.allContextHolders = allContextHolders;
  }


  public List get_allContextHolders()
  {
    return allContextHolders;
  }


  public void setAppServiceLocator(ApplicationServiceLocator appServiceLocator)
  {
    this.appServiceLocator = appServiceLocator;
  }


  public ApplicationServiceLocator getAppServiceLocator()
  {
    return appServiceLocator;
  }




}
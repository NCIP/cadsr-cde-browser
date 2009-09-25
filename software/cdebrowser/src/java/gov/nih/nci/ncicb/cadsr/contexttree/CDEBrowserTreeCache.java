package gov.nih.nci.ncicb.cadsr.contexttree;

import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.common.util.TimeUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CDEBrowserTreeCache 
{
                                   
  protected Log log =  LogFactory.getLog(CDEBrowserTreeCache.class.getName());
  
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
  private Map allClassificationNodes = null;
  private static ApplicationServiceLocator appServiceLocator = null;
  private Map allPublishingNode = null;
  
  public static CDEBrowserTreeCache getAnInstance() throws Exception
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
   log.info("Init start"+TimeUtils.getEasternTime());
    String contextExcludeListStr = (String)treeParams.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);   
    allContextHolders = appServiceLocator.findTreeService().getContextNodeHolders(baseTree,idGen,contextExcludeListStr);
    allTemplatesByContext = appServiceLocator.findTreeService().getAllContextTemplateNodes(baseTree,idGen);

    List protocolNodes = appServiceLocator.findTreeService().getAllContextProtocolNodes(baseTree,idGen);
    allFormsWithNoProtocol = (Map) protocolNodes.get(0);
    allFormsWithProtocol = (Map) protocolNodes.get(1);
    
    //create classification nodes
    allClassificationNodes = appServiceLocator.findTreeService().getAllClassificationNodes(baseTree, idGen);
    CDEBrowserParams params = CDEBrowserParams.getInstance();

    boolean showFormsAlphebetically = new Boolean(params.getShowFormsAlphebetical()).booleanValue();
    
    //creating publishing nodes
    allPublishingNode = appServiceLocator.findTreeService().getAllPublishingNode(baseTree, idGen, showFormsAlphebetically);
    //setCtepTemplateCtepNodes(conn,baseTree);
    log.info("Init end"+TimeUtils.getEasternTime());
  }
  
  public void initCtepInfo(BaseTreeNode baseTree,Context ctepContext) throws Exception
  {
   log.info("InitCtep start"+TimeUtils.getEasternTime());
//    setCtepTemplateCtepNodes(conn,baseTree,templateTypes, ctepContext);
   allTemplatesForCtep = appServiceLocator.findTreeService().getAllTemplateNodesForCTEP(baseTree, idGen, ctepContext);
   log.info("InitCtep end"+TimeUtils.getEasternTime());
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

  public DefaultMutableTreeNode getPublishNode( Context currContext) throws Exception
  {
    return (DefaultMutableTreeNode) allPublishingNode.get(currContext.getConteIdseq());
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


  public TreeIdGenerator getIdGen()
  {
    return idGen;
  }




}
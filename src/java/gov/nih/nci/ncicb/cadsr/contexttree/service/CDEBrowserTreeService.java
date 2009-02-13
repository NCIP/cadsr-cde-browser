package gov.nih.nci.ncicb.cadsr.contexttree.service;
import gov.nih.nci.ncicb.cadsr.contexttree.TreeFunctions;
import gov.nih.nci.ncicb.cadsr.contexttree.TreeIdGenerator;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.webtree.CSIRegStatusNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeContainerNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeItemNode;
import gov.nih.nci.ncicb.webtree.ClassSchemeNode;
import gov.nih.nci.ncicb.webtree.LazyActionTreeNode;

import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This service provivides operations for building the CDEBrowser tree
 */
public interface CDEBrowserTreeService 
{

  /**
   * Returns all Context nodes as webnodes
   */
  public List getContextNodeHolders(TreeFunctions treeFunctions,TreeIdGenerator idGen,String excludeList) throws Exception;
  public List getAllContextProtocolNodes(TreeFunctions treeFunctions,TreeIdGenerator idGen) throws Exception;
  public Map getAllContextTemplateNodes(TreeFunctions treeFunctions,TreeIdGenerator idGen) throws Exception;
  public Map getAllClassificationNodes(TreeFunctions treeFunctions,TreeIdGenerator idGen) throws Exception;
  public List getAllTemplateNodesForCTEP(TreeFunctions treeFunctions, TreeIdGenerator idGen, Context ctepContext) throws Exception ;
  public Map getAllPublishingNode(TreeFunctions treeFunctions, 
                   TreeIdGenerator idGen, boolean showFormsAlphebetically) throws Exception ;
  public List<LazyActionTreeNode> getAllTemplateNodesForCTEP(String contextId) throws Exception;
  public void addClassificationNode(LazyActionTreeNode parentNode, String contextId) throws Exception;
  public void addProtocolNodes(LazyActionTreeNode pNode, String contextIdseq) throws Exception;
  public void addPublishedFormbyAlphaNode(LazyActionTreeNode pNode, String contextId) throws Exception;
  public void addPublishedFormbyProtocolNode(LazyActionTreeNode pNode, String contextId) throws Exception;
  public void addPublishedFormNodesByProtocol(LazyActionTreeNode pNode, String protocolId) throws Exception;
  public void addPublishedTemplates(LazyActionTreeNode pNode, String contextId) throws Exception ;
  public void loadCSNodes(ClassSchemeNode pNode, String csId) throws Exception ;
  public void loadRegStatusCSNodes(LazyActionTreeNode pNode) throws Exception ;
  public void loadCSINodes(ClassSchemeItemNode pNode) throws Exception;
  public void loadCSIRegStatusNodes(CSIRegStatusNode pNode) throws Exception;
  public void loadCSContainerNodes(ClassSchemeContainerNode pNode, String csId) throws Exception;
  
  

}
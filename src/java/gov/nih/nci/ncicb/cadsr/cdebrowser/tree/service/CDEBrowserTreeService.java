package gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeFunctions;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeIdGenerator;
import java.util.List;
import java.util.Map;

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
  public List getAllClassificationNodes(TreeFunctions treeFunctions,TreeIdGenerator idGen) throws Exception;

}
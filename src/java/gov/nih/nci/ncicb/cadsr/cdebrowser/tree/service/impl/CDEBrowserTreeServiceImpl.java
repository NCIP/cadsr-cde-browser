package gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.impl;

import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.BaseTreeNode;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CsCsiCategorytHolder;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeFunctions;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeIdGenerator;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextHolderTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TreeProtocolNodesTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.ProtocolValueObject;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.ContextHolder;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;
import gov.nih.nci.ncicb.webtree.WebNode;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

public class CDEBrowserTreeServiceImpl
 implements CDEBrowserTreeService {
 ServiceLocator locator;
 AbstractDAOFactory daoFactory;

 /**
  * Currently the locator classname is hard code need to be refactored
  */
 public CDEBrowserTreeServiceImpl() {
  locator = ServiceLocatorFactory.getLocator("gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl");

  daoFactory = AbstractDAOFactory.getDAOFactory(locator);
 }

 /**
  * @returns a Context holder that contains a contect object and context we node
  */
 public List getContextNodeHolders(TreeFunctions treeFunctions, TreeIdGenerator idGen,
                                   String excludeList) throws Exception {
  ContextDAO dao = daoFactory.getContextDAO();

  List contexts = dao.getAllContexts(excludeList);
  ListIterator it = contexts.listIterator();
  List holders = new ArrayList();

  while (it.hasNext()) {
   Context context = (Context)it.next();

   ContextHolder holder = new ContextHolderTransferObject();
   holder.setContext(context);
   holder.setNode(getContextNode(idGen.getNewId(), context, treeFunctions));
   holders.add(holder);
  }

  return holders;
 }
 /**
 * @returns two maps with contextid as key and value a holder object containing web node
 * one containg the forms with no protocol and other with protocols
 */
 public List getAllContextProtocolNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {
  Map allFormsWithProtocol = new HashMap();

  Map allFormsWithNoProtocol = new HashMap();
  FormDAO dao = daoFactory.getFormDAO();
  List forms = dao.getAllFormsOrderByContextProtocol();
  Map protocolHolder = new HashMap();

  Iterator iter = forms.iterator();

  while (iter.hasNext()) {
   Form currForm = (Form)iter.next();

   String currContextId = currForm.getContext().getConteIdseq();
   String currProtoIdSeq = null;

   currProtoIdSeq = currForm.getProtoIdseq();
   DefaultMutableTreeNode formNode = getFormNode(idGen.getNewId(), 
                                            currForm, treeFunctions, false);

   //
   if (currProtoIdSeq != null && !currProtoIdSeq.equals("")) {
    List protocolList = (List)allFormsWithProtocol.get(currContextId);

    if (protocolList == null) {
     protocolList = new ArrayList();

     allFormsWithProtocol.put(currContextId, protocolList);
    }

    DefaultMutableTreeNode protoNode = (DefaultMutableTreeNode)protocolHolder.get(currProtoIdSeq);

    if (protoNode == null) {
     protoNode = getProtocolNode(idGen.getNewId(), currForm.getProtocol(), currContextId, treeFunctions);

     protocolHolder.put(currProtoIdSeq, protoNode);
     protocolList.add(protoNode);
    }

    protoNode.add(formNode);
   } else {
    List formWithNoProto = (List)allFormsWithNoProtocol.get(currContextId);

    if (formWithNoProto == null) {
     formWithNoProto = new ArrayList();

     allFormsWithNoProtocol.put(currContextId, formWithNoProto);
    }

    formWithNoProto.add(formNode);
   }
  }

  List resultList = new ArrayList();
  resultList.add(0, allFormsWithNoProtocol);
  resultList.add(1, allFormsWithProtocol);
  return resultList;
 }
 /**
 * @returns a map with contextid as key and value a list of template nodes
 */
 public Map getAllContextTemplateNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {
  Map allTemplatesByContext = new HashMap();

  FormDAO dao = daoFactory.getFormDAO();
  List templates = dao.getAllTemplatesOrderByContext();

  Iterator iter = templates.iterator();

  while (iter.hasNext()) {
   Form currTemplate = (Form)iter.next();

   String currContextId = currTemplate.getContext().getConteIdseq();
   DefaultMutableTreeNode tmpNode = getTemplateNode(idGen.getNewId(), currTemplate, treeFunctions);

   List nodes = (List)allTemplatesByContext.get(currContextId);

   if (nodes == null) {
    nodes = new ArrayList();

    allTemplatesByContext.put(currContextId, nodes);
   }

   nodes.add(tmpNode);
  }

  return allTemplatesByContext;
 }

 /**
* @returns a map with contextid as key and value a list of template nodes
* CTEP nodes are further categorized by classification 'CRF_DISEASE' and 'Phase'
*/
 public List getAllTemplateNodesForCTEP(TreeFunctions treeFunctions, TreeIdGenerator idGen, Context currContext,
                                        List templateTypes) throws Exception {
  List allTemplatesForCtep = new ArrayList();

  Map disCscsiHolder = new HashMap();
  Map phaseCscsiHolder = new HashMap();

  DefaultMutableTreeNode phaseNode = getWebNode("Phase", idGen.getNewId());
  DefaultMutableTreeNode diseaseNode = getWebNode("Disease", idGen.getNewId());
  allTemplatesForCtep.add(phaseNode);
  allTemplatesForCtep.add(diseaseNode);

  FormDAO dao = daoFactory.getFormDAO();
  Collection csiList = dao.getCSIForContextId(currContext.getConteIdseq());

  Map cscsiMap = new HashMap();
  List phaseCsCsiList = new ArrayList();
  List diseaseCsCsiList = new ArrayList();

  Iterator iter = csiList.iterator();

  while (iter.hasNext()) {
   CSITransferObject csiTO = (CSITransferObject)iter.next();

   cscsiMap.put(csiTO.getCsCsiIdseq(), csiTO);

   if (csiTO.getClassSchemeLongName().equals("CRF_DISEASE")) {
    diseaseCsCsiList.add(csiTO.getCsCsiIdseq());
   }

   if (csiTO.getClassSchemeLongName().equals("Phase")) {
    phaseCsCsiList.add(csiTO.getCsCsiIdseq());
   }
  }

  Collection templates = dao.getAllTemplatesForContextId(currContext.getConteIdseq());
  String currContextId = currContext.getConteIdseq();
  //Add all the csi nodes 
  addAllcscsiNodes(phaseCsCsiList, cscsiMap, currContextId, phaseNode, templateTypes, phaseCscsiHolder);
  addAllcscsiNodes(diseaseCsCsiList, cscsiMap, currContextId, diseaseNode, templateTypes, disCscsiHolder);
  iter = templates.iterator();

  while (iter.hasNext()) {
   Form currTemplate = (Form)iter.next();

   Collection csColl = currTemplate.getClassifications();
   String currCsCsiIdseq = null;
   Iterator csIter = csColl.iterator();

   if (csIter.hasNext()) {
    ClassSchemeItem currCsi = (ClassSchemeItem)csIter.next();

    currCsCsiIdseq = currCsi.getCsCsiIdseq();
   }

   String currContextName = currContext.getName();
   String currTemplateId = currTemplate.getIdseq();
   String currCategory = currTemplate.getFormCategory();

   //
   if (currCategory != null && !currCategory.equals("") && currCsCsiIdseq != null) {
    ClassSchemeItem currcscsi = (ClassSchemeItem)cscsiMap.get(currCsCsiIdseq);

    if (currcscsi == null)
     continue;

    if (phaseCsCsiList.contains(currCsCsiIdseq)) {
     CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)phaseCscsiHolder.get(currCsCsiIdseq);

     DefaultMutableTreeNode cscsiNode = cscsiCategoryHolder.getNode();
     Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
     DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);
     DefaultMutableTreeNode
        templateNode = getTemplateNode(idGen.getNewId(), currTemplate, currcscsi, currContext, treeFunctions);
     categoryNode.add(templateNode);
    } else if (diseaseCsCsiList.contains(currCsCsiIdseq)) {
     CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)disCscsiHolder.get(currCsCsiIdseq);

     DefaultMutableTreeNode cscsiNode = cscsiCategoryHolder.getNode();
     Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
     DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);
     DefaultMutableTreeNode
        templateNode = getTemplateNode(idGen.getNewId(), currTemplate, currcscsi, currContext, treeFunctions);
     categoryNode.add(templateNode);
    }
   }
  }

  return allTemplatesForCtep;
 }

 //Get Publishing Node info
 public DefaultMutableTreeNode getPublishingNode(TreeFunctions treeFunctions, 
                   TreeIdGenerator idGen, Context currContext,
                   boolean showFormsAlphebetically) throws Exception {
                   
  CSITransferObject publishFormCSI = null, publishTemplateCSI = null;
  DefaultMutableTreeNode publishNode = null;  

  
  FormDAO dao = daoFactory.getFormDAO();
  List formCSIs = dao.getPublishingCSCSIsForForm(currContext.getConteIdseq());

  if (formCSIs != null && !formCSIs.isEmpty())
   publishFormCSI = (CSITransferObject)formCSIs.get(0);

  List templateCSIs = dao.getPublishingCSCSIsForTemplate(currContext.getConteIdseq());

  if (templateCSIs != null && !templateCSIs.isEmpty())
   publishTemplateCSI = (CSITransferObject)templateCSIs.get(0);

  if (publishFormCSI != null || publishTemplateCSI != null) {
    publishNode = new DefaultMutableTreeNode(new WebNode(idGen.getNewId(), publishFormCSI.getClassSchemeLongName()));

   List publishedProtocols = null;
   List publishedTemplates = null;
   List publishedForms = null;


   if (publishFormCSI != null) {
    publishedProtocols = dao.getAllProtocolsForPublishedForms(currContext.getConteIdseq());

    publishedForms = new ArrayList();

    if (showFormsAlphebetically)
     publishedForms = dao.getAllPublishedForms(currContext.getConteIdseq());

    if (!publishedProtocols.isEmpty() || !publishedForms.isEmpty()) {
     DefaultMutableTreeNode publishFormNode = new DefaultMutableTreeNode(
             new WebNode(idGen.getNewId(), publishFormCSI.getClassSchemeItemName()));

     publishNode.add(publishFormNode);

     if (!publishedForms.isEmpty() && showFormsAlphebetically) {
      DefaultMutableTreeNode
         listedAlphabetically = new DefaultMutableTreeNode(new WebNode(idGen.getNewId(), "Listed Alphabetically"));

      Iterator formsIt = publishedForms.iterator();

      while (formsIt.hasNext()) {
       Form currForm = (Form)formsIt.next();
       listedAlphabetically.add(getFormNode(idGen.getNewId(), currForm, 
       treeFunctions, true));
      }

      publishFormNode.add(listedAlphabetically);
     }

     //starting here
     if (!publishedProtocols.isEmpty()) {
      DefaultMutableTreeNode
         listedByProtocol = new DefaultMutableTreeNode(
                               new WebNode(idGen.getNewId(), "Listed by Protocol"));

      Iterator protocolIt = publishedProtocols.iterator();

      while (protocolIt.hasNext()) {
       Protocol currProto = (Protocol) protocolIt.next();
       // first create protocol node for each protocol
       DefaultMutableTreeNode currProtoNode = getProtocolNode(idGen.getNewId(),
       currProto, currContext.getConteIdseq(), treeFunctions);
       
       // then add all form nodes
       List formsForProtocol = dao.getAllPublishedFormsForProtocol(currProto.getIdseq());
       
       Iterator protocolFormsIt = formsForProtocol.iterator();
       while (protocolFormsIt.hasNext()) {
         Form currProtocolForm = (Form) protocolFormsIt.next();
         currProtocolForm.setProtocol(currProto);
         currProtoNode.add(this.getFormNode(idGen.getNewId(), 
         currProtocolForm, treeFunctions, true));
       }
       
       listedByProtocol.add(currProtoNode);
      }

      publishFormNode.add(listedByProtocol);
     }

    }
    
   }

   if (publishTemplateCSI  != null) {
    publishedTemplates = dao.getAllPublishedTemplates(currContext.getConteIdseq());

    if (publishedTemplates != null && !publishedTemplates.isEmpty()) {
     DefaultMutableTreeNode
        publishTemplateNode = new DefaultMutableTreeNode(
                                 new WebNode(idGen.getNewId(),
          publishTemplateCSI.getClassSchemeItemName()));

     Iterator templateIt = publishedTemplates.iterator();

     while (templateIt.hasNext()) {
      Form currTemplate = (Form)templateIt.next();
      publishTemplateNode.add(this.getTemplateNode(idGen.getNewId(), 
      currTemplate, treeFunctions));
     }

     publishNode.add(publishTemplateNode);
    }
   }

  }
  return publishNode;
 }

 /**
  * @returns a map with contextid as key and value a list of Classification nodes
  */
 public Map getAllClassificationNodes(TreeFunctions treeFunctions, TreeIdGenerator idGen) throws Exception {

  Map csNodeByContextMap = new HashMap();
  FormDAO dao = daoFactory.getFormDAO();
  List allCscsi = dao.getCSCSIHierarchy();
  Map csMap = new HashMap(); //this map stores the webnode for cs given cs_idseq
  Map csiMap = new HashMap();
  
  Iterator iter = allCscsi.iterator();
  
  while (iter.hasNext()) 
  {
  
    ClassSchemeItem cscsi = (ClassSchemeItem) iter.next();
    String csContextId = cscsi.getCsConteIdseq();

    
    DefaultMutableTreeNode classifcationNode 
        =(DefaultMutableTreeNode) csNodeByContextMap.get(csContextId);
        
    if (classifcationNode == null) 
    {
      //create a classification node for this context
      classifcationNode = getWebNode("Classifications", idGen.getNewId());
      csNodeByContextMap.put(csContextId, classifcationNode);
      csiMap.clear();
      csMap.clear();
    }
    
    String csId = cscsi.getCsIdseq();
    // create classification scheme node if necessary
    DefaultMutableTreeNode csNode = (DefaultMutableTreeNode) csMap.get(csId);
    if (csNode == null) 
    {
      csNode = getClassificationSchemeNode(idGen.getNewId(), cscsi, treeFunctions);
      classifcationNode.add(csNode);
      csMap.put(csId, csNode);
    }
    
    // add csi node
      DefaultMutableTreeNode csiNode = 
      getClassificationSchemeItemNode(idGen.getNewId(), cscsi, treeFunctions);
      
      String parentId = cscsi.getParentCscsiId();
      DefaultMutableTreeNode parentNode = null;
      if ( parentId != null) 
        parentNode =(DefaultMutableTreeNode) csiMap.get(parentId);
      else 
        parentNode = csNode;
      
      if (parentNode != null) 
          parentNode.add(csiNode);
      csiMap.put(cscsi.getCsCsiIdseq(), csiNode);
    
      // for CTEP disease, add core, none core sub node
      if (treeFunctions.getTreeType().equals(TreeConstants.DE_SEARCH_TREE))
      {
        if (cscsi.getClassSchemeItemType().equals("DISEASE_TYPE")) 
        {
          if (cscsi.getClassSchemePrefName().equals("DISEASE")) 
          {
            csiNode.add(this.getDiseaseSubNode(idGen.getNewId(), cscsi,
            treeFunctions, "Core Data Set" ));
            csiNode.add(this.getDiseaseSubNode(idGen.getNewId(), cscsi,
            treeFunctions, "Non-Core Data Set" ));
          }
        }
      }
  }
  return csNodeByContextMap;
 }
 private DefaultMutableTreeNode getClassificationSchemeNode(String nodeId, ClassSchemeItem csi,
 TreeFunctions treeFunctions) throws Exception {

    return new DefaultMutableTreeNode(
      new WebNode(nodeId
        ,csi.getClassSchemeLongName()
        ,"javascript:"+treeFunctions.getJsFunctionName()+"('P_PARAM_TYPE=CLASSIFICATION&P_IDSEQ="+
        csi.getCsIdseq()+"&P_CONTE_IDSEQ="+csi.getCsConteIdseq()
        +treeFunctions.getExtraURLParameters()+"')"
        ,csi.getClassSchemeDefinition()));


 }

 private DefaultMutableTreeNode getClassificationSchemeItemNode(String nodeId, ClassSchemeItem csi,
 TreeFunctions treeFunctions) throws Exception {

    return new DefaultMutableTreeNode(
      new WebNode(nodeId
        ,csi.getClassSchemeItemName()
        ,"javascript:"+treeFunctions.getJsFunctionName()+"('P_PARAM_TYPE=CSI&P_IDSEQ="+
         csi.getCsCsiIdseq() +"&P_CONTE_IDSEQ="+csi.getCsConteIdseq()
        +treeFunctions.getExtraURLParameters()+"')"
        ,csi.getCsiDescription()));

 }
 
 private DefaultMutableTreeNode getContextNode(String nodeId, Context context,
                                               TreeFunctions treeFunctions) throws Exception {
  String currContextId = context.getConteIdseq();

  String name = context.getName();
  String desc = context.getDescription();

  DefaultMutableTreeNode
     contextNode = new DefaultMutableTreeNode(
                      new WebNode(nodeId,
                                  name + " (" + desc + ")",
                                  "javascript:" + treeFunctions.getJsFunctionName() + "('P_PARAM_TYPE=CONTEXT&P_IDSEQ="
                                     + currContextId + "&P_CONTE_IDSEQ=" + currContextId
                                     + treeFunctions.getExtraURLParameters() + "')",
                                  desc + " (" + name + ")"));
  return contextNode;
 }

 private DefaultMutableTreeNode getDiseaseSubNode(String nodeId, ClassSchemeItem csi,
 TreeFunctions treeFunctions, String nodeName) throws Exception {
 
 int firstSpace = nodeName.indexOf(" ");
 String nodeType = nodeName.substring(0, firstSpace).toUpperCase();
 return new DefaultMutableTreeNode(
              new WebNode(nodeId, nodeName,
                "javascript:"+treeFunctions.getJsFunctionName()
                +"('P_PARAM_TYPE=" + nodeType
                + "&P_IDSEQ="+ csi.getCsiIdseq()
                +"&P_CONTE_IDSEQ="+csi.getCsConteIdseq()+
                "&P_CS_CSI_IDSEQ="+csi.getCsCsiIdseq()+
                "&diseaseName="+URLEncoder.encode(csi.getClassSchemeItemName())+
                "&csName="+URLEncoder.encode(csi.getClassSchemeLongName())
                +treeFunctions.getExtraURLParameters()+"')"
                ,nodeName));
 }
 private DefaultMutableTreeNode getFormNode(String nodeId, Form form,
 TreeFunctions treeFunctions, boolean showContextName) throws Exception {
  String formIdseq = form.getFormIdseq();

  String displayName = form.getLongName();
  String preferred_definition = form.getPreferredDefinition();
  String currContextId = form.getConteIdseq();
  String contextName = "";
  String formLongName = "";
  
  if (form.getLongName() != null)
    formLongName = URLEncoder.encode(form.getLongName());
    
  if (form.getContext() != null)
   contextName = form.getContext().getName();
   
  if (contextName != null)
    contextName = URLEncoder.encode(contextName);

   
  if (showContextName) 
    displayName = displayName + " (" + contextName + ")";

  String protocolId = "";

  if (form.getProtoIdseq() != null)
   protocolId = form.getProtoIdseq();

  DefaultMutableTreeNode
     formNode = new DefaultMutableTreeNode(
                   new WebNode(nodeId,
                               displayName,
                               "javascript:" + treeFunctions.getFormJsFunctionName() + "('P_PARAM_TYPE=CRF&P_IDSEQ="
                                  + formIdseq + "&P_CONTE_IDSEQ=" + currContextId + "&P_PROTO_IDSEQ=" + protocolId
                          //        + "&templateName=" + formLongName
                          //        + "&contextName=" + contextName
                                  + treeFunctions.getExtraURLParameters() + "')",
                               preferred_definition));
  return formNode;
 }

 private DefaultMutableTreeNode getTemplateNode(String nodeId, Form template,
                                                TreeFunctions treeFunctions) throws Exception {
  String templateIdseq = template.getFormIdseq();

  String longName = template.getLongName();
  String preferred_definition = template.getPreferredDefinition();
  String contextName = template.getContext().getName();
  String currContextId = template.getContext().getConteIdseq();
  DefaultMutableTreeNode tmpNode =
                            new DefaultMutableTreeNode(
                            new WebNode(nodeId, longName,
                         "javascript:" + treeFunctions.getFormJsFunctionName()
                            + "('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+ templateIdseq 
                            + "&P_CONTE_IDSEQ=" + currContextId  //context idseq
                            + "&templateName=" + URLEncoder.encode(longName)    //longname
                            +"&contextName=" + URLEncoder.encode(contextName) + // context name
                            treeFunctions.getExtraURLParameters() + "')",
                         preferred_definition));                               //preffered definition      
  return tmpNode;
 }

 private DefaultMutableTreeNode getTemplateNode(String nodeId, Form template, ClassSchemeItem csi, Context currContext,
                                                TreeFunctions treeFunctions) throws Exception {
  String templateIdseq = template.getFormIdseq();

  String longName = template.getLongName();
  String prefferedDefinition = template.getPreferredDefinition();

  DefaultMutableTreeNode
     tmpNode = new DefaultMutableTreeNode(
                  new WebNode(nodeId,
                              longName,
                              "javascript:" + treeFunctions.getFormJsFunctionName() + "('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="
                                 + templateIdseq + "&P_CONTE_IDSEQ=" + currContext.getConteIdseq() + "&csName="
                                 + URLEncoder.encode(
                                      csi.getClassSchemeLongName()) + "&diseaseName=" + URLEncoder.encode(
                                                                                           csi.getClassSchemeItemName())
                                 + "&templateType="
                                 + URLEncoder.encode(
                                      template.getFormCategory()) + "&templateName=" + URLEncoder.encode(
                                                                                          longName) + "&contextName="
                                 + URLEncoder.encode(
                                      currContext.getName()) + treeFunctions.getExtraURLParameters() + "')",
                              prefferedDefinition));

  return tmpNode;
 }

 private DefaultMutableTreeNode getProtocolNode(String nodeId, Protocol protocol, String currContextId,
                                                TreeFunctions treeFunctions) throws Exception {
  String protoIdseq = protocol.getProtoIdseq();

  String longName = protocol.getLongName();
  String preferred_definition = protocol.getPreferredDefinition();

  DefaultMutableTreeNode
     protocolNode = new DefaultMutableTreeNode(
                       new WebNode(nodeId,
                                   longName,
                                   "javascript:" + treeFunctions.getJsFunctionName()
                                      + "('P_PARAM_TYPE=PROTOCOL&P_IDSEQ=" + protoIdseq + "&P_CONTE_IDSEQ="
                                      + currContextId + "&protocolLongName=" + longName
                                      + treeFunctions.getExtraURLParameters() + "')",
                                   preferred_definition));
  return protocolNode;
 }

 private DefaultMutableTreeNode getWebNode(String name, String id) {
  return new DefaultMutableTreeNode(new WebNode(id, name));
 }

 private Map addInitialCategoryNodes(DefaultMutableTreeNode cscsiNode, String uniqueIdPrefix, List templateTypes) {
  if (templateTypes == null)
   return new HashMap();

  Map holderMap = new HashMap(); // Map holding catagory to  catagory Node
  ListIterator it = templateTypes.listIterator();

  while (it.hasNext()) {
   String type = (String)it.next();

   DefaultMutableTreeNode node = getWebNode(type, uniqueIdPrefix + type);
   cscsiNode.add(node);
   holderMap.put(type, node);
  }

  return holderMap;
 }

 private void addAllcscsiNodes(List cscsiList,     Map cscsiMap, String contextId, DefaultMutableTreeNode csNode,
                               List templateTypes, Map cscsiholderMap) {
  if (cscsiList == null || cscsiMap == null || csNode == null || cscsiholderMap == null)
   return;

  ListIterator it = cscsiList.listIterator();

  while (it.hasNext()) {
   String cscsiId = (String)it.next();

   ClassSchemeItem cscsi = (ClassSchemeItem)cscsiMap.get(cscsiId);
   String aUniquesId = contextId + cscsi.getCsCsiIdseq() + System.currentTimeMillis();
   DefaultMutableTreeNode node = this.getWebNode(cscsi.getClassSchemeItemName(), aUniquesId);
   csNode.add(node);
   aUniquesId = contextId + cscsi.getCsCsiIdseq() + System.currentTimeMillis();
   Map categoryMap = addInitialCategoryNodes(node, aUniquesId, templateTypes);
   CsCsiCategorytHolder cscsiCatHolder = new CsCsiCategorytHolder();
   cscsiCatHolder.setNode(node);
   cscsiCatHolder.setCategoryHolder(categoryMap);
   cscsiholderMap.put(cscsiId, cscsiCatHolder);
  }
 }
}

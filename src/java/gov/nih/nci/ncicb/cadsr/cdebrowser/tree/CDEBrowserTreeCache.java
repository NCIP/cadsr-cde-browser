package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.impl.CDEBrowserTreeServiceImpl;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.net.URLEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class CDEBrowserTreeCache 
{
                                   
 final String templateQueryForCtep = " SELECT distinct qc.qc_idseq "
                                       +" ,qc.long_name "
                                       +" ,qc.preferred_name "
                                       +" ,qc.preferred_definition "
                                       +" ,qc.qcdl_name "
                                       +" ,acs.cs_csi_idseq "
                                       +" , name "
                                       +" FROM  sbrext.quest_contents_ext qc, contexts context"
                                       +" ,sbr.ac_csi acs "
                                       +" WHERE "
                                       +" context.CONTE_IDSEQ=qc.CONTE_IDSEQ "
                                       +" AND    qcdl_name is not null "
                                       +" AND    qc.conte_idseq =  ? "
                                       +" AND   qc.deleted_ind = 'No' "
                                       +" AND   qc.latest_version_ind = 'Yes'  "
                                       +" AND   qc.qtl_name = 'TEMPLATE' "
                                       +" AND   qc.qc_idseq = acs.ac_idseq "
                                       +" ORDER BY cs_csi_idseq,qc.qcdl_name,long_name ";      

  final String csiQueryStmt = "SELECT  csi.csi_idseq "
                               +"       ,csi_name "
                               +"       ,csitl_name "
                               +"       ,description "
                               +"       ,csc.cs_csi_idseq "
                               +"       ,cs.preferred_name "
                               +"FROM   sbr.class_scheme_items csi "
                               +"      ,sbr.cs_csi csc "
                               +"      ,sbr.classification_schemes cs "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               +"AND csc.cs_idseq = cs.cs_idseq "
                               +"AND   cs.preferred_name in( 'CRF_DISEASE','Phase') "
                               +"ORDER BY upper(csi.csi_name) " ;
                               
                               
  private String ctepIdSeq = "";
  private final String CTEP="CTEP";
  
  private CDEBrowserTreeService treeService = new CDEBrowserTreeServiceImpl();
  
  public CDEBrowserTreeCache()
  {
  }
  //All crf by contextId - Protocols(List) -crf(Collection) 
  private Map allFormsWithProtocol = new HashMap();
  private Map allFormsWithNoProtocol = new HashMap();
  private Map allTemplatesByContext = null;
  private List allTemplatesForCtep = null;  
  private List allContextHolders = null;

  
  public static CDEBrowserTreeCache getAnInstance(Connection conn,BaseTreeNode baseTree) throws Exception
  {

      CDEBrowserTreeCache cache = new CDEBrowserTreeCache();
      return cache;
  }
  public List getProtocolNodes(String contextIdSeq)
  {
    return (List)allFormsWithProtocol.get(contextIdSeq);

  }
  
  public List getTemplateNodes(String contextIdSeq)
  {
    return (List)allTemplatesByContext.get(contextIdSeq);
  }
  
  public List getFormNodesWithNoProtocol(String contextIdSeq)
  {
    return (List)allFormsWithNoProtocol.get(contextIdSeq);
  }
  public void init(Connection conn,BaseTreeNode baseTree,Hashtable treeParams) throws Exception
  {
   System.out.println("Init start"+TimeUtils.getEasternTime());
    CDEBrowserTreeService service = new CDEBrowserTreeServiceImpl();
    TreeIdGenerator idGen = new TreeIdGenerator();
    String contextExcludeListStr = (String)treeParams.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);   
    allContextHolders = service.getContextNodeHolders(baseTree,idGen,contextExcludeListStr);
    allTemplatesByContext = service.getAllContextTemplateNodes(baseTree,idGen);
    //setDataTemplateNodes(conn,baseTree);
    //setFormNodes(conn,baseTree);
    //setFormByProtocolNodes(conn,baseTree);
    List protocolNodes = service.getAllContextProtocolNodes(baseTree,idGen);
    allFormsWithNoProtocol = (Map) protocolNodes.get(0);
    allFormsWithProtocol = (Map) protocolNodes.get(1);
    
    //setCtepTemplateCtepNodes(conn,baseTree);
  System.out.println("Init end"+TimeUtils.getEasternTime());
  }
  
  public void initCtepInfo(Connection conn,BaseTreeNode baseTree,List templateTypes) throws Exception
  {
   System.out.println("InitCtep start"+TimeUtils.getEasternTime());
    setCtepTemplateCtepNodes(conn,baseTree,templateTypes);
   System.out.println("InitCtep end"+TimeUtils.getEasternTime());
  }  
 
  
    /**
   * Returns Map With cscisid and List of Nodes with Templates
   * Nodes are Catagories
   */
  public void setCtepTemplateCtepNodes(Connection conn,BaseTreeNode baseTree,List templateTypes) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map disCscsiHolder = new HashMap(); 
    
    Map phaseCscsiHolder = new HashMap(); 
    
    
    allTemplatesForCtep = new ArrayList();
    
    Map cscsiMap = getCTEPCSCSIs(conn);
    List phaseCsCsiList = (List)cscsiMap.get("Phase");
    List diseaseCsCsiList = (List)cscsiMap.get("Disease");
    
    
    if(cscsiMap==null)
      cscsiMap = new HashMap(); 
    
    DefaultMutableTreeNode phaseNode = getWebNode(conn,"Phase");
    DefaultMutableTreeNode diseaseNode = getWebNode(conn,"Disease");    
    
    allTemplatesForCtep.add(phaseNode);
    allTemplatesForCtep.add(diseaseNode);
    
    try {
          
          pstmt =
             (PreparedStatement)conn.prepareStatement(templateQueryForCtep);
          pstmt.setString(1,this.getCtepIdSeq());
          pstmt.setFetchSize(25);
          rs = pstmt.executeQuery();
            String currContextId = getCtepIdSeq();
           //Add all the csi nodes 
          addAllcscsiNodes(phaseCsCsiList,cscsiMap,currContextId,phaseNode
                ,templateTypes,phaseCscsiHolder);
          addAllcscsiNodes(diseaseCsCsiList,cscsiMap,currContextId,diseaseNode
                ,templateTypes,disCscsiHolder);
          
          while (rs.next()){
            String currCsCsiIdseq = rs.getString("cs_csi_idseq");
            String currContextName = rs.getString("name");
            String currTemplateId = rs.getString("qc_idseq");
            String currCategory = rs.getString("qcdl_name");
            //
            if(currCategory!=null&&!currCategory.equals("")&&currCsCsiIdseq!=null)
            {
              ClassSchemeItem currcscsi= (ClassSchemeItem)cscsiMap.get(currCsCsiIdseq);
              if(currcscsi==null)
                continue;
              if(phaseCsCsiList.contains(currCsCsiIdseq))
              {
                CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)phaseCscsiHolder.get(currCsCsiIdseq);
                DefaultMutableTreeNode cscsiNode = cscsiCategoryHolder.getNode();
                Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
                DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);      
                DefaultMutableTreeNode templateNode = getTemplateNode(rs,currcscsi,currContextId,currCategory,baseTree);
                categoryNode.add(templateNode);
                
              }
              else if(diseaseCsCsiList.contains(currCsCsiIdseq))
              {
                CsCsiCategorytHolder cscsiCategoryHolder = (CsCsiCategorytHolder)disCscsiHolder.get(currCsCsiIdseq);
                DefaultMutableTreeNode cscsiNode = cscsiCategoryHolder.getNode();
                Map categoryHolder = cscsiCategoryHolder.getCategoryHolder();
                DefaultMutableTreeNode categoryNode = (DefaultMutableTreeNode)categoryHolder.get(currCategory);      
                DefaultMutableTreeNode templateNode = getTemplateNode(rs,currcscsi,currContextId,currCategory,baseTree);
                categoryNode.add(templateNode);
              }
            }
          }        
     }
      catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
    }
  }  

 private void addAllcscsiNodes(List cscsiList,Map cscsiMap,String contextId
               ,DefaultMutableTreeNode csNode,List templateTypes,Map cscsiholderMap)
 {
   if(cscsiList==null||cscsiMap==null||csNode==null||cscsiholderMap==null)
    return;
   
   ListIterator it = cscsiList.listIterator();
   while(it.hasNext())
   {
     String cscsiId = (String)it.next();
     ClassSchemeItem cscsi = (ClassSchemeItem)cscsiMap.get(cscsiId);
     String aUniquesId = contextId+cscsi.getCsCsiIdseq()+System.currentTimeMillis();
     DefaultMutableTreeNode node = this.getWebNode(cscsi.getClassSchemeItemName()
                                                  ,aUniquesId);
     csNode.add(node);
     aUniquesId = contextId+cscsi.getCsCsiIdseq()+System.currentTimeMillis() ;                                                 
     Map categoryMap = addInitialCategoryNodes(node,aUniquesId,templateTypes);
     CsCsiCategorytHolder cscsiCatHolder = new CsCsiCategorytHolder();
     cscsiCatHolder.setNode(node);
     cscsiCatHolder.setCategoryHolder(categoryMap);
     cscsiholderMap.put(cscsiId,cscsiCatHolder);
   }
 }
 private Map addInitialCategoryNodes(DefaultMutableTreeNode cscsiNode, String uniqueIdPrefix, List templateTypes)
 {
   if(templateTypes==null)
    return new HashMap();
   Map holderMap =  new HashMap(); // Map holding catagory to  catagory Node
   ListIterator it = templateTypes.listIterator();
   while(it.hasNext())
   {
     String type = (String)it.next();
     DefaultMutableTreeNode node = getWebNode(type,uniqueIdPrefix+type);
     cscsiNode.add(node);
      holderMap.put(type,node);
   }
   return holderMap;
 }
  private Map getCTEPCSCSIs(Connection conn) throws Exception
  {
    ResultSet rs= null;
    PreparedStatement pstmt =null;
    Map cscsiMap = new HashMap();
    List phasecscsiList = new ArrayList();
    List diseasecscsiList =  new ArrayList();
    cscsiMap.put("Phase",phasecscsiList);
    cscsiMap.put("Disease",diseasecscsiList);
    
    try {  
      ClassSchemeItem csiTO =null;
      List cscsiList = new ArrayList();
      pstmt =
             (PreparedStatement)conn.prepareStatement(csiQueryStmt);

      pstmt.setFetchSize(25);      
      rs= pstmt.executeQuery();

      while (rs.next()){
        csiTO = new CSITransferObject();
        csiTO.setCsiIdseq(rs.getString("csi_idseq"));
        csiTO.setClassSchemeItemName(rs.getString("csi_name"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("preferred_name"));
        cscsiMap.put(rs.getString("cs_csi_idseq"),csiTO);
        if(rs.getString("preferred_name").equals("CRF_DISEASE"))
        {
          diseasecscsiList.add(rs.getString("cs_csi_idseq"));
        }
        if(rs.getString("preferred_name").equals("Phase"))
        {
          phasecscsiList.add(rs.getString("cs_csi_idseq"));
        }

      }
      
    }
      catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();  
    }
    return cscsiMap;
  }
  
  //Moved to service start
  private DefaultMutableTreeNode getContextNode(ResultSet rs,BaseTreeNode baseTree) throws Exception
  {
     
        String currContextId = rs.getString(1);
        String name = rs.getString(2);
        String desc = rs.getString(3);
        
      DefaultMutableTreeNode contextNode = new DefaultMutableTreeNode
                (new WebNode(currContextId
                            ,name+ " ("+desc+")"
                            ,"javascript:"+baseTree.getJsFunctionName()+"('P_PARAM_TYPE=CONTEXT&P_IDSEQ="+
                            currContextId+"&P_CONTE_IDSEQ="+currContextId
                            +baseTree.getExtraURLParameters()+"')"
                            ,desc+ " ("+name+")"
                            ));
      return contextNode;
  }
  private DefaultMutableTreeNode getFormNode(ResultSet rs,String currContextId,BaseTreeNode baseTree) throws Exception
  {
          String formIdseq = rs.getString("qc_idseq");
          String longName = rs.getString("long_name");
          String preferred_definition = rs.getString("preferred_definition");
          
          DefaultMutableTreeNode formNode = new DefaultMutableTreeNode(
            new WebNode(formIdseq+currContextId  //Do ne to make id unique could have same protocol in diffrent
                     ,longName
                     ,"javascript:"+baseTree.getFormJsFunctionName()+"('P_PARAM_TYPE=CRF&P_IDSEQ="+
                       formIdseq+"&P_CONTE_IDSEQ="+" "+
                       "&P_PROTO_IDSEQ="+""+
                       baseTree.getExtraURLParameters()+"')"
                     ,preferred_definition));
          return formNode;
                     
  }       
  
 private DefaultMutableTreeNode getTemplateNode(ResultSet rs,String currContextId,BaseTreeNode baseTree) throws Exception
  {
  
          String templateIdseq = rs.getString("qc_idseq");
          String longName = rs.getString("long_name");
          String preferred_definition = rs.getString("preferred_definition");
          String contextName = rs.getString("NAME");
          
        DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(templateIdseq+currContextId//idseq
                     ,longName//long name
                     ,"javascript:"+baseTree.getFormJsFunctionName()+"('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       templateIdseq+"&P_CONTE_IDSEQ="+currContextId+//context idseq
                       "&templateName="+URLEncoder.encode(longName)+//longname
                       "&contextName="+URLEncoder.encode(contextName)+// context name
                       baseTree.getExtraURLParameters()+"')"
                     ,preferred_definition));  //preffered definition      
          return tmpNode;         
                     
  }     
  
 private DefaultMutableTreeNode getTemplateNode(ResultSet rs,ClassSchemeItem csi,String currContextId
                             ,String categoryName,BaseTreeNode baseTree) throws Exception
  {
  
          String templateIdseq = rs.getString("qc_idseq");
          String longName = rs.getString("long_name");
          String prefferedDefinition = rs.getString("preferred_definition");
          String currContextName = rs.getString("name");
          String currCategory = rs.getString("qcdl_name");
            
          DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(currContextId+csi.getCsCsiIdseq()+templateIdseq
                     ,longName
                     ,"javascript:"+baseTree.getFormJsFunctionName()+"('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+currContextId+
                       "&csName="+URLEncoder.encode(csi.getClassSchemeLongName())+
                       "&diseaseName="+URLEncoder.encode(csi.getClassSchemeItemName())+
                       "&templateType="+URLEncoder.encode(categoryName)+
                       "&templateName="+URLEncoder.encode(longName)+
                       "&contextName="+URLEncoder.encode(currContextName)+
                       baseTree.getExtraURLParameters()+"')"
                     ,prefferedDefinition));      
                     
            return tmpNode;
  }     
  

  private  DefaultMutableTreeNode getWebNode(Connection conn, String name) throws Exception
  {
       return new DefaultMutableTreeNode
              (new WebNode(DBUtil.getUniqueId(conn,TreeConstants.IDSEQ_GENERATOR),name));
  }
  
  private  DefaultMutableTreeNode getWebNode(String name, String id)
  {
       return new DefaultMutableTreeNode
              (new WebNode(id,name));
  }
  
  // Moved to service end
  private void clearCache()
  {
    
  }

  public String getCtepIdSeq()
  {
    return ctepIdSeq;
  }

  public void setCtepIdSeq(String ctepIdSeq)
  {
    this.ctepIdSeq = ctepIdSeq;
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

}
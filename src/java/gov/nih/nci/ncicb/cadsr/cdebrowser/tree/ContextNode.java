package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.ClassSchemeValueObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.ProtocolValueObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.webtree.WebNode;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.tree.DefaultMutableTreeNode;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import java.net.URLEncoder;

public class ContextNode extends BaseTreeNode  {


  final String templateTypesQueryStmt = "SELECT distinct qcdl_name "
                                       +"FROM   quest_contents_ext "
                                       +"WHERE  qtl_name = 'TEMPLATE' "
                                       +"AND    conte_idseq = ? "
                                       +"AND    deleted_ind = 'No' "
                                       +"AND    latest_version_ind = 'Yes' "
                                       +"AND    qcdl_name is not null "
                                       +"ORDER BY upper(qcdl_name) ";
  final String protoQueryStmt =     "SELECT  proto_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbrext.protocols_ext "
                                    +"WHERE conte_idseq = ? "
                                    +"AND   deleted_ind = 'No' "
                                    +"AND   latest_version_ind = 'Yes' "
                                    +"ORDER BY upper(long_name) ";

  final String publishedFormProtoQueryStmt = " select distinct proto_idseq, proto.preferred_name "
                                            +" ,proto.long_name ,proto.preferred_definition "
                                            +" ,proto.conte_idseq "
                                            +" from protocols_ext proto "
                                            +" , published_forms_view "
                                            +" where "
                                            +" proto.PROTO_IDSEQ=published_forms_view.PROTOCOL_IDSEQ "
                                            +" and proto.PROTO_IDSEQ=published_forms_view.PROTOCOL_IDSEQ "
                                            +" and   proto.deleted_ind = 'No' "
                                            +" and	 proto.latest_version_ind = 'Yes' "
                                            +" and   PUBLISH_CONTE_IDSEQ=? "
                                            +" order by upper(proto.long_name) ";
                                    
  final String csiQueryStmt = "SELECT  csi.csi_idseq "
                               +"       ,csi_name "
                               +"       ,csitl_name "
                               +"       ,description "
                               +"       ,csc.cs_csi_idseq "
                               +"FROM   sbr.class_scheme_items csi "
                               +"      ,sbr.cs_csi csc "
                               +"      ,sbr.classification_schemes cs "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               +"AND csc.cs_idseq = cs.cs_idseq "
                               +"AND   cs.preferred_name = ? "
                               +"ORDER BY upper(csi.csi_name) ";

  final String templateQueryStmt =  "SELECT  qc_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbrext.quest_contents_ext "
                                    +"WHERE conte_idseq = ? "
                                    +"AND   deleted_ind = 'No' "
                                    +"AND   latest_version_ind = 'Yes' "
                                    +"AND   qtl_name = 'TEMPLATE' "
                                    +"ORDER BY upper(long_name) ";
  
  //Publish Change order
  final String publishedTemplateQuery = " select  published.QC_IDSEQ "
                                    +" ,published.QC_LONG_NAME "
                                    +" ,published.QC_PREFERRED_NAME "
                                    +" ,published.QC_PREFERRED_DEFINITION "
                                    +" ,published.FORM_CONTE_IDSEQ "
                                    +" ,published.FORM_CONTEXT "
                                    +" from published_forms_view published "
                                    +" where "
                                    +" published.QTL_NAME = '"+PersistenceConstants.FORM_TYPE_TEMPLATE+"' " 
                                    +" and   published.PUBLISH_CONTE_IDSEQ=? "
                                    +" order by upper(QC_LONG_NAME) ";  
                                    
  final String publishedFormQuery = " select  published.QC_IDSEQ "
                                    +" ,published.QC_LONG_NAME "
                                    +",published.QC_PREFERRED_NAME"
                                    +" ,published.QC_PREFERRED_DEFINITION "
                                    +" ,published.FORM_CONTE_IDSEQ "
                                    +" ,published.FORM_CONTEXT "
                                    +" from published_forms_view published "
                                    +" where "
                                    +" published.QTL_NAME = '"+PersistenceConstants.FORM_TYPE_CRF+"' " 
                                    +" and   published.PUBLISH_CONTE_IDSEQ=? "
                                    +" order by upper(QC_LONG_NAME) ";                                     
                                          
 final String publishingNodeInfo =  " select csi.CSITL_NAME , cs.LONG_NAME,cscsi.LABEL,cscsi.CS_CSI_IDSEQ"
                                             + " from classification_schemes cs, class_scheme_items csi, cs_csi cscsi "
                                             + " where cs.CS_IDSEQ=cscsi.CS_IDSEQ "
                                             + " and csi.CSI_IDSEQ =cscsi.CSI_IDSEQ "
                                             + " and cs.CSTL_NAME='Publishing' "
                                             + " and csi.CSITL_NAME in ('"+PersistenceConstants.CSI_TYPE_PUBLISH_FORM +"'"
                                                                        + ",'"+PersistenceConstants.CSI_TYPE_PUBLISH_TEMPLATE+"') "
                                             + " and cs.CONTE_IDSEQ = ? ";
		                                        

 final String crfQueryStmt =  "SELECT qc_idseq "
                                +"      ,long_name "
                                +"      ,preferred_name "
                                +"      ,preferred_definition "
                                +"FROM  sbrext.quest_contents_ext "
                                +"WHERE conte_idseq = ? "
                                +"AND   qtl_name = 'CRF' "
                                +"AND   deleted_ind = 'No' "
                                +"AND   latest_version_ind = 'Yes' "
                                +"ORDER BY upper(long_name) ";
                                
  Context myContext = null;
  DefaultMutableTreeNode myContextNode = null;
  List templateTypes;


  /**
   * Constructor creates DefaultMutableTreeNode object based on info provided
   * by Context resource.
   */
  public ContextNode(Context pContext
                    ,DBUtil dbUtil
                    ,Hashtable params) {
    super(dbUtil,params);
    myContext = pContext;
    myContextNode = new DefaultMutableTreeNode
    (new WebNode(myContext.getConteIdseq()
                ,myContext.getName()+ " ("+myContext.getDescription()+")"
                ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=CONTEXT&P_IDSEQ="+
                myContext.getConteIdseq()+"&P_CONTE_IDSEQ="+myContext.getConteIdseq()
                +getExtraURLParameters()+"')"
                ,myContext.getDescription()+ " ("+myContext.getName()+")"
                ));
    if ("CTEP".equals(myContext.getName())) {
      templateTypes = this.getTemplateTypes();
    }
    
  }

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a classification scheme
   * node for this context in the tree.
   *
   */

  public List getClassificationNodes(String treeType) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List csNodes = null;

    String classSchemeQueryStmt = "";
    if(treeType.equalsIgnoreCase(this.DE_SEARCH_TREE))
    {

    /** Used to filter our no relevent Classifications
     * Reverted back due to performance problems
     * classSchemeQueryStmt = "SELECT cs_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM   BR_CDE_CS_FILTER_VIEW  "
                                    +"WHERE conte_idseq = ? "                                  
                                    +"ORDER BY upper(long_name) "; 
                               
       classSchemeQueryStmt = "SELECT cs_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbr.classification_schemes "
                                    +"WHERE conte_idseq = ? "
                                    +"AND    deleted_ind = 'No' "
                                    +"AND    latest_version_ind = 'Yes' "
                                    +"AND    asl_name = 'RELEASED' "
                                    +" AND cs_idseq in "
                                    +" ( select filter.CS_IDSEQ from BR_CDE_CS_FILTER_VIEW filter) "
                                    +" AND classification_schemes.CSTL_NAME!='Publishing'"                                   
                                    +"ORDER BY upper(long_name) ";  
                                    
                                    **/
    
    
      classSchemeQueryStmt = "SELECT cs_idseq "
                                          +"      ,preferred_name "
                                          +"      ,long_name "
                                          +"      ,preferred_definition "
                                          +"FROM  sbr.classification_schemes "
                                          +"WHERE conte_idseq = ? "
                                          +"AND    deleted_ind = 'No' "
                                          +"AND    latest_version_ind = 'Yes' "
                                          +"AND    asl_name = 'RELEASED' "  
                                          +"AND    CSTL_NAME!='Publishing'"
                                          +"ORDER BY upper(long_name) "; 
          
    }
    if(treeType.equalsIgnoreCase(this.FORM_SEARCH_TREE))
    {
       /**
      *  Used to filter out no-relevent Classifications
      * Reverted back due to performance problems 
        * classSchemeQueryStmt = "SELECT cs_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  FB_FORMS_CS_FILTER_VIEW "
                                    +" WHERE conte_idseq = ? "                             
                                    +"ORDER BY upper(long_name) ";   
                                    
        classSchemeQueryStmt = "SELECT cs_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbr.classification_schemes "
                                    +"WHERE conte_idseq = ? "
                                    +"AND    deleted_ind = 'No' "
                                    +"AND    latest_version_ind = 'Yes' "
                                    +"AND    asl_name = 'RELEASED' "
                                    +" AND cs_idseq in "
                                    +" ( select filter.CS_IDSEQ from FB_FORMS_CS_FILTER_VIEW filter) "
                                    +" AND classification_schemes.CSTL_NAME!='Publishing'"                                   
                                    +"ORDER BY upper(long_name) ";      
                                    
                                    **/
      classSchemeQueryStmt = "SELECT cs_idseq "
                                          +"      ,preferred_name "
                                          +"      ,long_name "
                                          +"      ,preferred_definition "
                                          +"FROM  sbr.classification_schemes "
                                          +"WHERE conte_idseq = ? "
                                          +"AND    deleted_ind = 'No' "
                                          +"AND    latest_version_ind = 'Yes' "
                                          +"AND    asl_name = 'RELEASED' "  
                                          +"AND    CSTL_NAME!='Publishing'"
                                          +"ORDER BY upper(long_name) ";                                     
    }
                                                                    

    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(classSchemeQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
  
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
      ClassificationScheme csVO = new ClassSchemeValueObject();
      csNodes = new ArrayList(11);

      while (rs.next()){
        csVO.setCsIdseq(rs.getString(1));
        csVO.setPreferredName(rs.getString(2));
        csVO.setLongName(rs.getString(3));
        csVO.setPreferredDefinition(rs.getString(4));
        csVO.setContextName(myContext.getName());
        csVO.setConteIdseq(myContext.getConteIdseq());
        ClassificationNode cn = new ClassificationNode(csVO,myDbUtil,treeParams);
        DefaultMutableTreeNode csNode = cn.getTreeNode();
        List csiNodes = cn.getClassSchemeItems(treeType);
        Iterator iter = csiNodes.iterator();
        while (iter.hasNext()){
          csNode.add((DefaultMutableTreeNode)iter.next());
        }
        csNodes.add(csNode);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    return csNodes;
  }

  public DefaultMutableTreeNode[] getCTEPDataTemplateNodes()throws Exception {
    DefaultMutableTreeNode[] templateNodes = new DefaultMutableTreeNode[2];
    templateNodes[0] = getDataTemplateNodesByDisease();
    templateNodes[1] = getDataTemplateNodesByPhase();
    return templateNodes;
  }

  /**
   * This method returns a DefaultMutableTreeNode. It displays Protocol Form
   * templates by disease.
   *
   */
  public DefaultMutableTreeNode getDataTemplateNodesByDisease()throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpNodes = new ArrayList(11);
    List tmpTypes = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    DefaultMutableTreeNode diseaseLabelNode = null;
    ClassSchemeItem csiTO = new CSITransferObject();
    try {
      //Getting the CRF Disease types
      pstmt1 =
         (PreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      //pstmt1.defineColumnType(1,Types.VARCHAR);
      //pstmt1.defineColumnType(2,Types.VARCHAR);
      //pstmt1.defineColumnType(3,Types.VARCHAR);
      //pstmt1.defineColumnType(4,Types.VARCHAR);
      //pstmt1.defineColumnType(5,Types.VARCHAR);
      pstmt1.setFetchSize(25);
      pstmt1.setString(1,"CRF_DISEASE");
      rs1 = pstmt1.executeQuery();

      diseaseLabelNode = new DefaultMutableTreeNode
              (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR),"Disease"));

      TemplateNode tmp;
      List tmpList = null;
      Iterator tmpIter = null;

      //DefaultMutableTreeNode diseaseNode;
      while (rs1.next()){
        DefaultMutableTreeNode diseaseNode = new DefaultMutableTreeNode
          (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR),rs1.getString(2)));

        csiTO.setCsiIdseq(rs1.getString(1));
        csiTO.setClassSchemeItemName(rs1.getString(2));
        csiTO.setClassSchemeItemType(rs1.getString(3));
        csiTO.setCsCsiIdseq(rs1.getString(5));
        csiTO.setClassSchemeLongName("Disease");

        //tmpNodes = this.getDataTemplatesForADisease(rs1.getString(5));
        tmpNodes = this.getDataTemplatesForACSI(csiTO);
        Iterator tempNodeIter = tmpNodes.iterator();
        while(tempNodeIter.hasNext()) {
          diseaseNode.add((DefaultMutableTreeNode)tempNodeIter.next());
        }
        diseaseLabelNode.add(diseaseNode);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs1 != null) rs1.close();
        if (pstmt1 != null) pstmt1.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }

    return diseaseLabelNode;
  }

  /**
   * This method returns a DefaultMutableTreeNode. It displays Protocol Form
   * templates by Phase.
   *
   */
  public DefaultMutableTreeNode getDataTemplateNodesByPhase()throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpNodes = new ArrayList(11);
    List tmpTypes = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    DefaultMutableTreeNode phaseLabelNode = null;
    ClassSchemeItem csiTO = new CSITransferObject();
    try {
      //Getting the CRF Disease types
      pstmt1 =
         (PreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      //pstmt1.defineColumnType(1,Types.VARCHAR);
      //pstmt1.defineColumnType(2,Types.VARCHAR);
      //pstmt1.defineColumnType(3,Types.VARCHAR);
      //pstmt1.defineColumnType(4,Types.VARCHAR);
      //pstmt1.defineColumnType(5,Types.VARCHAR);
      pstmt1.setFetchSize(25);
      pstmt1.setString(1,"Phase");
      rs1 = pstmt1.executeQuery();

      phaseLabelNode = new DefaultMutableTreeNode
              (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR),"Phase"));

      TemplateNode tmp;
      List tmpList = null;
      Iterator tmpIter = null;

      while (rs1.next()){
        DefaultMutableTreeNode phaseNode = new DefaultMutableTreeNode
          (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR),rs1.getString(2)));

        csiTO.setCsiIdseq(rs1.getString(1));
        csiTO.setClassSchemeItemName(rs1.getString(2));
        csiTO.setClassSchemeItemType(rs1.getString(3));
        csiTO.setCsCsiIdseq(rs1.getString(5));
        csiTO.setClassSchemeLongName("Phase");

        tmpNodes = this.getDataTemplatesForACSI(csiTO);
        Iterator tempNodeIter = tmpNodes.iterator();
        while(tempNodeIter.hasNext()) {
          phaseNode.add((DefaultMutableTreeNode)tempNodeIter.next());
        }
        phaseLabelNode.add(phaseNode);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs1 != null) rs1.close();
        if (pstmt1 != null) pstmt1.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }

    return phaseLabelNode;
  }

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a Protocol
   * node for this context in the tree.
   *
   */
  public List getProtocolNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List protoNodes = new ArrayList(11);
    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(protoQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
      Protocol proto = new ProtocolValueObject();
      DefaultMutableTreeNode protoNode;
      ProtocolNode pn;
      List crfNodes;

      while (rs.next()){
        proto.setProtoIdseq(rs.getString(1));
        proto.setLongName(rs.getString(3));
        proto.setPreferredName(rs.getString(2));
        proto.setPreferredDefinition(rs.getString(4));
        proto.setConteIdseq(myContext.getConteIdseq());
        pn = new ProtocolNode(proto,myDbUtil,treeParams,myContext.getConteIdseq());

        protoNode = pn.getTreeNode();
        crfNodes = pn.getCRFs();
        for (Iterator it = crfNodes.iterator(); it.hasNext();){
          protoNode.add((DefaultMutableTreeNode)it.next());
        }
        if(!crfNodes.isEmpty())
            protoNodes.add(protoNode);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return protoNodes;
  }
  


  public List getFormNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List _crfNodes = new ArrayList(7);

    try {
      pstmt =  
         (PreparedStatement)myConn.prepareStatement(crfQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
      
      while (rs.next()){
        DefaultMutableTreeNode crfNode = new DefaultMutableTreeNode(
          new WebNode(rs.getString(1)
                     ,rs.getString(2)
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=CRF&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+" "+
                       "&P_PROTO_IDSEQ="+""+
                       getExtraURLParameters()+"')"
                     ,rs.getString(4)));
        _crfNodes.add(crfNode);
      }
    } 
    catch (Exception ex) {
      ex.printStackTrace();
    } 
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();  
      } 
      catch (Exception ex) {
        ex.printStackTrace();
      } 
    }
    return _crfNodes;
    
  }
  
//Publish Change order
  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a Protocol
   * node of a published CRF.
   *
   */
  public List getPublishedFormProtocolNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List protoNodes = new ArrayList(11);
    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(publishedFormProtoQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      //pstmt.defineColumnType(5,Types.VARCHAR);
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
      Protocol proto = new ProtocolValueObject();
      DefaultMutableTreeNode protoNode;
      ProtocolNode pn;
      List crfNodes;

      while (rs.next()){
        proto.setProtoIdseq(rs.getString(1));
        proto.setLongName(rs.getString(3));
        proto.setPreferredName(rs.getString(2));
        proto.setPreferredDefinition(rs.getString(4));
        proto.setConteIdseq(rs.getString(5));
        pn = new ProtocolNode(proto,myDbUtil,treeParams,myContext.getConteIdseq());

        protoNode = pn.getTreeNode();
        crfNodes = pn.getPublishedForms();
        for (Iterator it = crfNodes.iterator(); it.hasNext();){
          protoNode.add((DefaultMutableTreeNode)it.next());
        }
        protoNodes.add(protoNode);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return protoNodes;
  }
  
  /**
   * This method returns ContextNode object as DefaultMutableTreeNode object.
   */
  public DefaultMutableTreeNode getTreeNode(){
    return myContextNode;
  }
  public static void main(String[] args) {
    //ContextNode contextNode = new ContextNode();
  }

  private List getDataTemplatesForACSI(ClassSchemeItem csiTO) throws Exception {
    List tmpNodes = new ArrayList(11);
    List tmpTypes = null;
    TemplateNode tmp;
    List tmpList = null;
    Iterator tmpIter = null;
    try {
      for (int i=0 ;i < templateTypes.size();i++ )  {
        tmp = new TemplateNode(myContext
                              ,(String)templateTypes.get(i)
                              ,csiTO
                              ,myDbUtil
                              ,treeParams);
        DefaultMutableTreeNode tmpTypeNode = tmp.getTreeNode();
        tmpList = tmp.getDataTemplateNodes();
        tmpIter = tmpList.iterator();
        while(tmpIter.hasNext()){
          tmpTypeNode.add((DefaultMutableTreeNode)tmpIter.next());
        }
        tmpNodes.add(tmpTypeNode);
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {

    }
    return tmpNodes;

  }


  private List getTemplateTypes() {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpTypes = new ArrayList(11);

    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(templateTypesQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
      while (rs.next()){
        tmpTypes.add(rs.getString(1));
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    return tmpTypes;
  }

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a Protocol Form Template
   * node for a context other than CTEP.
   *
   */
  public List getDataTemplateNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List templateNodes = new ArrayList(11);
    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(templateQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();


      while (rs.next()){
        DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                     ,rs.getString(2)
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+myContext.getConteIdseq()+
                       "&templateName="+URLEncoder.encode(rs.getString(2))+
                       "&contextName="+URLEncoder.encode(myContext.getName())+
                       getExtraURLParameters()+"')"
                     ,rs.getString(4)));
        templateNodes.add(tmpNode);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return templateNodes;
  }
//Publish Change Order
  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a Form Template 
   * which have been published.
   *
   */
  public List getPublishedTemplateNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List templateNodes = new ArrayList();

    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(publishedTemplateQuery);
         
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      //pstmt.defineColumnType(5,Types.VARCHAR);
     // pstmt.defineColumnType(6,Types.VARCHAR);
      
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();

      while (rs.next()){
        DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                     ,rs.getString(2)+" ("+rs.getString(6)+")"
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+rs.getString(5)+
                       "&templateName="+URLEncoder.encode(rs.getString(2))+
                       "&contextName="+URLEncoder.encode(rs.getString(6))+
                       getExtraURLParameters()+"')"
                     ,rs.getString(4)));
        templateNodes.add(tmpNode);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return templateNodes;
  }
  
//Publish Change Order
  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each
   * DefaultMutableTreeNode object in the list represents a Form Template 
   * which have been published.
   *
   */
  public List getPublishedFormNodes() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List templateNodes = new ArrayList();

    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(publishedFormQuery);
         
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      //pstmt.defineColumnType(5,Types.VARCHAR);
      //pstmt.defineColumnType(6,Types.VARCHAR);
      
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());
      rs = pstmt.executeQuery();
                                   
                                          
      while (rs.next()){
        DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                     ,rs.getString(2)+" ("+rs.getString(6)+")"
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=CRF&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+rs.getString(5)+
                       "&templateName="+URLEncoder.encode(rs.getString(2))+
                       "&contextName="+URLEncoder.encode(rs.getString(6))+
                       getExtraURLParameters()+"')"
                     ,rs.getString(4)));
        templateNodes.add(tmpNode);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return templateNodes;
  }
  
//Publish Change Order
   /**
   * This method returns the  CSI for a Context for a Function.
   *
   */
  public Map getPublisingNodeInfo() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map info = new HashMap();
    try {
      pstmt =
         (PreparedStatement)myConn.prepareStatement(publishingNodeInfo);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      
      pstmt.setFetchSize(25);
      pstmt.setString(1,myContext.getConteIdseq());      
      rs = pstmt.executeQuery();

      while(rs.next())
      {
        Map values = new HashMap();
        values.put("publishNodeLabel",rs.getString(2));
        values.put("publishChildNodeLabel",rs.getString(3));
        values.put("cscsi",rs.getString(4));
        info.put(rs.getString(1),values);
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return info;
  }


}

package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.ClassSchemeValueObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.ProtocolValueObject;
import gov.nih.nci.ncicb.cadsr.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.tree.DefaultMutableTreeNode;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import oracle.jdbc.OraclePreparedStatement;
import java.net.URLEncoder;

public class ContextNode extends BaseTreeNode  {


  final String templateTypesQueryStmt = "SELECT distinct qcdl_name "
                                       +"FROM   quest_contents_ext "
                                       +"WHERE  qtl_name = 'TEMPLATE' "
                                       +"AND    conte_idseq = ? "
                                       +"AND    deleted_ind = 'No' "
                                       +"AND    latest_version_ind = 'Yes' "
                                       +"AND    qcdl_name is not null "
                                       +"ORDER BY qcdl_name ";
  final String protoQueryStmt =     "SELECT  proto_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbrext.protocols_ext "
                                    +"WHERE conte_idseq = ? "
                                    +"AND   deleted_ind = 'No' "
                                    +"AND   latest_version_ind = 'Yes' "
                                    +"ORDER BY long_name ";

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
                               +"ORDER BY csi.csi_name ";

  final String templateQueryStmt =  "SELECT  qc_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbrext.quest_contents_ext "
                                    +"WHERE conte_idseq = ? "
                                    +"AND   deleted_ind = 'No' "
                                    +"AND   latest_version_ind = 'Yes' "
                                    +"AND   qtl_name = 'TEMPLATE' "
                                    +"ORDER BY long_name ";

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

  public List getClassificationNodes() throws Exception {
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List csNodes = null;
    String csFilter = "";
    String classSchemeQueryStmt = "SELECT cs_idseq "
                                    +"      ,preferred_name "
                                    +"      ,long_name "
                                    +"      ,preferred_definition "
                                    +"FROM  sbr.classification_schemes "
                                    +"WHERE conte_idseq = ? "
                                    +"AND    deleted_ind = 'No' "
                                    +"AND    latest_version_ind = 'Yes' "
                                    +"AND    asl_name = 'RELEASED' "
                                    +"ORDER BY long_name ";

    try {
      pstmt =
         (OraclePreparedStatement)myConn.prepareStatement(classSchemeQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
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
        List csiNodes = cn.getClassSchemeItems();
        //csiNodes = cn.getClassSchemeItems();
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
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpNodes = new ArrayList(11);
    List tmpTypes = null;
    OraclePreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    DefaultMutableTreeNode diseaseLabelNode = null;
    ClassSchemeItem csiTO = new CSITransferObject();
    try {
      //Getting the CRF Disease types
      pstmt1 =
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      pstmt1.defineColumnType(1,Types.VARCHAR);
      pstmt1.defineColumnType(2,Types.VARCHAR);
      pstmt1.defineColumnType(3,Types.VARCHAR);
      pstmt1.defineColumnType(4,Types.VARCHAR);
      pstmt1.defineColumnType(5,Types.VARCHAR);
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
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpNodes = new ArrayList(11);
    List tmpTypes = null;
    OraclePreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    DefaultMutableTreeNode phaseLabelNode = null;
    ClassSchemeItem csiTO = new CSITransferObject();
    try {
      //Getting the CRF Disease types
      pstmt1 =
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      pstmt1.defineColumnType(1,Types.VARCHAR);
      pstmt1.defineColumnType(2,Types.VARCHAR);
      pstmt1.defineColumnType(3,Types.VARCHAR);
      pstmt1.defineColumnType(4,Types.VARCHAR);
      pstmt1.defineColumnType(5,Types.VARCHAR);
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
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List protoNodes = new ArrayList(11);
    try {
      pstmt =
         (OraclePreparedStatement)myConn.prepareStatement(protoQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
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
        pn = new ProtocolNode(proto,myDbUtil,treeParams);

        protoNode = pn.getTreeNode();
        crfNodes = pn.getCRFs();
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
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpTypes = new ArrayList(11);

    try {
      pstmt =
         (OraclePreparedStatement)myConn.prepareStatement(templateTypesQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
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
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List templateNodes = new ArrayList(11);
    try {
      pstmt =
         (OraclePreparedStatement)myConn.prepareStatement(templateQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
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



}
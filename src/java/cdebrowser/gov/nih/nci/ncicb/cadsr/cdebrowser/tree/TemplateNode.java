package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.webtree.WebNode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import oracle.jdbc.OraclePreparedStatement;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import java.net.URLEncoder;

public class TemplateNode  {

  Connection myConn = null;
  Context myContext = null;
  DefaultMutableTreeNode myTemplateNode = null;
  DBUtil myDbUtil = null;
  final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
  String myTemplateGroup = null;
  String csCsiIdseq = null;
  ClassSchemeItem csiTO = null;

  /**
   * Constructor creates DefaultMutableTreeNode object based on info provided
   * by Context resource.
   */
  public TemplateNode(Context pContext
                    ,String templateGroup
                    ,ClassSchemeItem csiTO
                    ,DBUtil dbUtil) throws Exception {

    myContext = pContext;
    myDbUtil = dbUtil;
    myConn = dbUtil.getConnection();
    myTemplateGroup = templateGroup;
    //this.csCsiIdseq = csCsiIdseq;
    this.csiTO = csiTO;
    this.csCsiIdseq = csiTO.getCsCsiIdseq();
    myTemplateNode = new DefaultMutableTreeNode
    (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                ,templateGroup
                ));
  }

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each 
   * DefaultMutableTreeNode object in the list represents a CDE Data Template 
   * node for this context in the tree.  
   *  
   */
  public List getDataTemplateNodes() throws Exception {
    final String templateQueryStmt = "SELECT qc.qc_idseq "
                                  +"      ,qc.long_name "
                                  +"      ,qc.preferred_name "
                                  +"      ,qc.preferred_definition "
                                  +"FROM  sbrext.quest_contents_ext qc "
                                  +"     ,sbr.ac_csi acs "
                                  +"WHERE qc.conte_idseq = ? "
                                  +"AND   qc.qcdl_name = ? "
                                  +"AND   qc.deleted_ind = 'No' "
                                  +"AND   qc.latest_version_ind = 'Yes' "
                                  +"AND   qc.qtl_name = 'TEMPLATE' "
                                  +"AND   qc.qc_idseq = acs.ac_idseq "
                                  +"AND   acs.cs_csi_idseq = ? "
                                  +"ORDER BY long_name ";;
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List tmpNodes = new ArrayList(11);;
    try {
      pstmt =  
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(templateQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
      
      pstmt.setString(1,myContext.getConteIdseq());
      pstmt.setString(2,myTemplateGroup);
      pstmt.setString(3,csCsiIdseq);
      rs = pstmt.executeQuery();
      //tmpNodes = new ArrayList(11);
      while (rs.next()){
        DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                     ,rs.getString(2)
                     ,"javascript:performAction('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+myContext.getConteIdseq()+
                       "&csName="+URLEncoder.encode(csiTO.getClassSchemeLongName())+
                       "&diseaseName="+URLEncoder.encode(csiTO.getClassSchemeItemName())+
                       "&templateType="+URLEncoder.encode(myTemplateGroup)+
                       "&templateName="+URLEncoder.encode(rs.getString(2))+
                       "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
                     ,rs.getString(4)));
        tmpNodes.add(tmpNode);
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
    return tmpNodes;
  }

  /**
   * This method returns TemplateNode object as DefaultMutableTreeNode object.
   */
  public DefaultMutableTreeNode getTreeNode(){
    return myTemplateNode;
  }

}
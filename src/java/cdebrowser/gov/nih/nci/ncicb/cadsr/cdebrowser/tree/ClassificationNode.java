package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;

import oracle.jdbc.OraclePreparedStatement;

import java.net.URLEncoder;

public class ClassificationNode extends BaseTreeNode  {
  ClassificationScheme myCsVO = null;
  //DBUtil myDbUtil = null;
  DefaultMutableTreeNode csTreeNode = null;
  
  /**
   * Constructor creates DefaultMutableTreeNode object based on info provided
   * by ClassificationScheme resource.
   */
  public ClassificationNode(ClassificationScheme cs
                           ,DBUtil dbUtil
                           ,Hashtable params) {
    super(dbUtil,params);
    myCsVO = cs;
    //myDbUtil = dbUtil;
    csTreeNode = new DefaultMutableTreeNode(
      new WebNode(myCsVO.getCsIdseq()
        ,myCsVO.getLongName()
        ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=CLASSIFICATION&P_IDSEQ="+
        myCsVO.getCsIdseq()+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
        "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
        ,myCsVO.getPreferredDefinition()));
  }

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each 
   * DefaultMutableTreeNode object in the list represents a classification 
   * scheme item node for this classification scheme in the tree.  
   *  
   */
  public List getClassSchemeItems()throws Exception {
    final String csiQueryStmt = "SELECT  csi.csi_idseq "
                               +"       ,csi_name "
                               +"       ,csitl_name "
                               +"       ,description "
                               +"       ,csc.cs_csi_idseq "
                               +"FROM   sbr.class_scheme_items csi "
                               +"      ,sbr.cs_csi csc "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               +"AND   csc.cs_idseq = ? "
                               +"AND   csc.p_cs_csi_idseq is null "
                               +"ORDER BY csi.csi_name ";
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List csiNodes = null;
    List children = new ArrayList(11);
    try {
      pstmt =  
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
      pstmt.defineColumnType(5,Types.VARCHAR);
      pstmt.setFetchSize(25);
      pstmt.setString(1,myCsVO.getCsIdseq());
      rs = pstmt.executeQuery();
      csiNodes = new ArrayList(11);
      String contextName = myCsVO.getContextName();
      while (rs.next()){
        DefaultMutableTreeNode csiNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
            ,rs.getString(2)
            ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=CSI&P_IDSEQ="+
            rs.getString(5)+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
            "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
            ,rs.getString(4)));
        List tempNodes;
        if ("CTEP".equals(contextName)){
          //Getting Core and Non-Core Nodes for Disease Nodes
          if ("DISEASE_TYPE".equals(rs.getString(3)) && 
              "DISEASE".equals(myCsVO.getPreferredName())){
            DefaultMutableTreeNode coreNode = new DefaultMutableTreeNode(
              new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                ,"Core Data Set"
                ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=CORE&P_IDSEQ="+
                rs.getString(1)+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
                "&P_CS_CSI_IDSEQ="+rs.getString(5)+
                "&diseaseName="+URLEncoder.encode(rs.getString(2))+
                "&csName="+URLEncoder.encode(myCsVO.getLongName())+
                "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
                ,"Core Data Set"));
            csiNode.add(coreNode);

            DefaultMutableTreeNode nonCoreNode = new DefaultMutableTreeNode(
              new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                ,"Non-Core Data Set"
                ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=NON-CORE&P_IDSEQ="+
                rs.getString(1)+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
                "&P_CS_CSI_IDSEQ="+rs.getString(5)+
                "&diseaseName="+URLEncoder.encode(rs.getString(2))+
                "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
                ,"Non-Core Data Set"));
            csiNode.add(nonCoreNode);
          }
          //Getting CDE Template Nodes
          //Should never get executed since no records will match this criteria
          if ("DISEASE_TYPE".equals(rs.getString(3)) && 
              "CRF_DISEASE".equals(myCsVO.getPreferredName())){
            tempNodes = getDataTemplates(rs.getString(5));
            if (tempNodes.size() > 0) {
              DefaultMutableTreeNode tmpLabelNode = new DefaultMutableTreeNode
              (new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR),"Data Templates"));
              for(Iterator it=tempNodes.iterator(); it.hasNext();){
                tmpLabelNode.add((DefaultMutableTreeNode)it.next());
              }
              csiNode.add(tmpLabelNode);
            }
          }
        }
        children = this.getClassSchemeItemsTree(rs.getString(5));
        for(Iterator it = children.iterator(); it.hasNext();) {
          csiNode.add((DefaultMutableTreeNode)it.next());
        }
        csiNodes.add(csiNode);
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
    return csiNodes;
  }

  /**
   * This method returns ClassificationNode object as DefaultMutableTreeNode 
   * object.
   */
  public DefaultMutableTreeNode getTreeNode(){
    return csTreeNode;
  }

  public List getDataTemplates(String csCsiIdseq) throws Exception {
    final String tempQueryStmt = "SELECT tem.qc_idseq "
                                +"      ,tem.long_name "
                                +"      ,tem.preferred_definition "
                                +"      ,acv.cs_long_name "
                                +"      ,acv.csi_name "
                                +"FROM   quest_contents_ext tem "
                                +"       ,sbrext.ac_class_view_ext acv "
                                +"WHERE  tem.qtl_name = 'TEMPLATE' "
                                +"AND    tem.deleted_ind = 'No' "
                                +"AND    tem.latest_version_ind = 'Yes' "
                                +"AND    tem.qc_idseq = acv.ac_idseq "
                                +"AND    acv.CS_CSI_IDSEQ = ? "
                                +"ORDER BY tem.long_name ";
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List tempNodes = new ArrayList(11);
    try {
      pstmt =  
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(tempQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
      pstmt.setFetchSize(25);
      pstmt.setString(1,csCsiIdseq);
      rs = pstmt.executeQuery();
      while (rs.next()){
        DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
                     ,rs.getString(2)
                     ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=TEMPLATE&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
                       "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
                     ,rs.getString(3)));
        tempNodes.add(tempNode);
      }
      
    } 
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } 
    finally {
    }
    return tempNodes;
  }

  public List getClassSchemeItemsTree(String csCsiIdseq)throws Exception {
    final String csiQueryStmt = "SELECT  csi.csi_idseq "
                               +"       ,csi_name "
                               +"       ,csitl_name "
                               +"       ,description "
                               +"       ,csc.cs_csi_idseq "
                               +"FROM   sbr.class_scheme_items csi "
                               +"      ,sbr.cs_csi csc "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               //+"AND   csc.cs_idseq = ? "
                               +"AND   csc.p_cs_csi_idseq = ? "
                               +"ORDER BY csi.csi_name ";
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    List csiNodes = null;
    try {
      pstmt =  
         (OraclePreparedStatement)myDbUtil.getConnection().
                            prepareStatement(csiQueryStmt);
      pstmt.defineColumnType(1,Types.VARCHAR);
      pstmt.defineColumnType(2,Types.VARCHAR);
      pstmt.defineColumnType(3,Types.VARCHAR);
      pstmt.defineColumnType(4,Types.VARCHAR);
      pstmt.defineColumnType(5,Types.VARCHAR);
      pstmt.setFetchSize(25);
      //pstmt.setString(1,myCsVO.getCsIdseq());
      pstmt.setString(1,csCsiIdseq);
      rs = pstmt.executeQuery();
      csiNodes = new ArrayList(11);
      String contextName = myCsVO.getContextName();
      List children = new ArrayList(11);
      while (rs.next()){
        DefaultMutableTreeNode csiNode = new DefaultMutableTreeNode(
          new WebNode(myDbUtil.getUniqueId(IDSEQ_GENERATOR)
            ,rs.getString(2)
            ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=CSI&P_IDSEQ="+
            rs.getString(5)+"&P_CONTE_IDSEQ="+myCsVO.getConteIdseq()+
            "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')"
            ,rs.getString(4)));
        //Recursive call    
        children = this.getClassSchemeItemsTree(rs.getString(5));
        for(Iterator it = children.iterator(); it.hasNext();) {
          csiNode.add((DefaultMutableTreeNode)it.next());
        }
        csiNodes.add(csiNode);
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
    return csiNodes;
  }

  /*public String getJsFunctionName() {
    return jsFunctionName;
  }

  public void setJsFunctionName(String newJsFunctionName) {
    jsFunctionName = newJsFunctionName;
  }

  public String getExtraURLParameters() {
    return extraURLParameters;
  }

  public void setExtraURLParameters(String newExtraURLParameters) {
    extraURLParameters = newExtraURLParameters;
  }*/
  
}
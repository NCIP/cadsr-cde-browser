package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;

import javax.swing.tree.DefaultMutableTreeNode;


public class ProtocolNode extends BaseTreeNode  {
  //DBUtil _myDbUtil = null;
  DefaultMutableTreeNode _protoTreeNode = null;
  Protocol _myProtoVO = null;
  //publish Change request
  String displayContextIdSeq = null;
  /**
   * Constructor creates DefaultMutableTreeNode object based on info provided
   * by Protocol resource.
   */
  public ProtocolNode(Protocol protoVO
                     ,DBUtil dbUtil
                     ,Hashtable params,
                     String newDisplayContextIdseq) {
    super(dbUtil,params);
    _myProtoVO = protoVO;
    //_myDbUtil = dbUtil;
    //Publish Change Order
    displayContextIdSeq = newDisplayContextIdseq;
    if(displayContextIdSeq.equalsIgnoreCase(_myProtoVO.getConteIdseq()))
    {
    _protoTreeNode = new DefaultMutableTreeNode(
                  new WebNode(_myProtoVO.getProtoIdseq()
                     ,_myProtoVO.getLongName()
                     ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=PROTOCOL&P_IDSEQ="+
                       _myProtoVO.getProtoIdseq()+"&P_CONTE_IDSEQ="+_myProtoVO.getConteIdseq()+
                       "&protocolLongName="+_myProtoVO.getLongName()+getExtraURLParameters()+"')"
                     ,_myProtoVO.getPreferredDefinition()));    
    }
    else
    {
    _protoTreeNode = new DefaultMutableTreeNode(
                  new WebNode(_myProtoVO.getProtoIdseq()+displayContextIdSeq
                     ,_myProtoVO.getLongName()
                     ,"javascript:"+getJsFunctionName()+"('P_PARAM_TYPE=PUBLISHING_PROTOCOL&P_IDSEQ="+
                       _myProtoVO.getProtoIdseq()+"&P_CONTE_IDSEQ="+_myProtoVO.getConteIdseq()+
                       "&protocolLongName="+_myProtoVO.getLongName()+getExtraURLParameters()+"')"
                     ,_myProtoVO.getPreferredDefinition()));                     
    }
  }    

  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each 
   * DefaultMutableTreeNode object in the list represents a case report form (CRF) 
   * node for this protocol in the tree.  
   *  
   */
  public List getCRFs() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List _crfNodes = new ArrayList(7);
    final String crfQueryStmt =  "SELECT qc_idseq "
                                +"      ,long_name "
                                +"      ,preferred_name "
                                +"      ,preferred_definition "
                                +"FROM  sbrext.quest_contents_ext "
                                +"WHERE proto_idseq = ? "
                                +"AND   qtl_name = 'CRF' "
                                +"AND   deleted_ind = 'No' "
                                +"AND   latest_version_ind = 'Yes' "
                                +"ORDER BY upper(long_name) ";
    try {
      pstmt =  
         (PreparedStatement)myConn.prepareStatement(crfQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      
      pstmt.setString(1,_myProtoVO.getProtoIdseq());
      rs = pstmt.executeQuery();
      
      while (rs.next()){
        DefaultMutableTreeNode crfNode = new DefaultMutableTreeNode(
          new WebNode(_myProtoVO.getProtoIdseq()+rs.getString(1)
                     ,rs.getString(2)
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=CRF&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+_myProtoVO.getConteIdseq()+
                       "&P_PROTO_IDSEQ="+_myProtoVO.getProtoIdseq()+
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

//Published Change Order
  /**
   * This method returns a list of DefaultMutableTreeNode objects. Each 
   * DefaultMutableTreeNode object in the list represents a case report published form (CRF) 
   * node for this protocol in the tree.  
   *  
   */
  public List getPublishedForms() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List _crfNodes = new ArrayList(7);
    final String crfQueryStmt =  "select published.QC_IDSEQ "
                                  + ", published.QC_LONG_NAME "
                                  +" ,published.QC_PREFERRED_NAME "
		                              +" ,published.QC_PREFERRED_DEFINITION "
                                  +" ,published.FORM_CONTE_IDSEQ "
		                              +" ,published.PUBLISH_CONTE_IDSEQ  "
                                  +" ,published.FORM_CONTEXT "
                                  +" from published_forms_view published "
                                  +" where "
                                   +  " published.QTL_NAME = '"+PersistenceConstants.FORM_TYPE_CRF+"' "
                                   +  " and  published.PROTOCOL_IDSEQ=? "
                                   +  " and  published.PUBLISH_CONTE_IDSEQ=?	"
                                   +  "ORDER BY upper(published.QC_LONG_NAME) "; 
    try {
      pstmt =  
         (PreparedStatement)myConn.prepareStatement(crfQueryStmt);
      //pstmt.defineColumnType(1,Types.VARCHAR);
      //pstmt.defineColumnType(2,Types.VARCHAR);
      //pstmt.defineColumnType(3,Types.VARCHAR);
      //pstmt.defineColumnType(4,Types.VARCHAR);
      //pstmt.defineColumnType(5,Types.VARCHAR);
      //pstmt.defineColumnType(6,Types.VARCHAR);
      //pstmt.defineColumnType(7,Types.VARCHAR);
      
      pstmt.setString(1,_myProtoVO.getProtoIdseq());
      pstmt.setString(2,displayContextIdSeq);
      
      rs = pstmt.executeQuery();
      
      while (rs.next()){
        DefaultMutableTreeNode crfNode = new DefaultMutableTreeNode(
          new WebNode(rs.getString(1)+displayContextIdSeq
                     ,rs.getString(2)+" ("+rs.getString(7)+")"
                     ,"javascript:"+getFormJsFunctionName()+"('P_PARAM_TYPE=CRF&P_IDSEQ="+
                       rs.getString(1)+"&P_CONTE_IDSEQ="+rs.getString(5)+
                       "&P_PROTO_IDSEQ="+_myProtoVO.getProtoIdseq()+
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
  /**
   * This method returns ProtocolNode object as DefaultMutableTreeNode object.
   */
  public DefaultMutableTreeNode getTreeNode(){
    return _protoTreeNode;
  }

  
}
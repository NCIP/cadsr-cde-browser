package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.webtree.WebNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class ContextNode extends BaseTreeNode  {


  final String templateTypesQueryStmt = "SELECT distinct qcdl_name "
                                       +"FROM   quest_contents_ext "
                                       +"WHERE  qtl_name = 'TEMPLATE' "
                                       +"AND    conte_idseq = ? "
                                       +"AND    deleted_ind = 'No' "
                                       +"AND    latest_version_ind = 'Yes' "
                                       +"AND    qcdl_name is not null "
                                       +"ORDER BY upper(qcdl_name) ";

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


  public static void main(String[] args) {
    //ContextNode contextNode = new ContextNode();
  }


  public List getTemplateTypes() {
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


}

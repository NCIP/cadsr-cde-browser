package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JContextTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.ConnectionHelper;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.webtree.WebNode;
import gov.nih.nci.ncicb.webtree.WebTree;

import oracle.jdbc.OraclePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;


public class CDEBrowserTree extends WebTree implements TreeConstants {
  final static String contextQueryStmt =
    "SELECT conte_idseq " + 
    "      ,name " + 
    "      ,description " +
    "FROM  sbr.contexts " + 
    "ORDER BY name ";
  final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
  private String treeType;
  private String functionName;
  private String extraURLParameters = 
    "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes";

  public CDEBrowserTree() {
  }

  public DefaultMutableTreeNode getTree(Hashtable params) throws Exception {
    treeType = (String)params.get("treeType");
    functionName = (String)params.get("functionName");
    
    return buildTree(params);
  }

  public DefaultMutableTreeNode buildTree(Hashtable treeParams) throws Exception {
    DBUtil dbHelper = new DBUtil();
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    Context ctx = null;
    DefaultMutableTreeNode tree = null;
    BaseTreeNode baseNode = null; 
    

    try {
      CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      String datasourceName = params.getSbrDSN();

      if (dbHelper.getConnectionFromContainer(datasourceName)) {
        conn = dbHelper.getConnection();
      } else {
        throw new Exception("Unable to connect to the database");
      }
      baseNode = new BaseTreeNode(dbHelper,treeParams);
      WebNode contexts =
        new WebNode(
          dbHelper.getUniqueId(IDSEQ_GENERATOR)
          , "caDSR Contexts",
          "javascript:"+baseNode.getJsFunctionName()
          +"('P_PARAM_TYPE=P_PARAM_TYPE&P_IDSEQ=P_IDSEQ&"
          +baseNode.getExtraURLParameters()+"')");
      tree = new DefaultMutableTreeNode(contexts);
      pstmt = (OraclePreparedStatement) conn.prepareStatement(contextQueryStmt);
      pstmt.defineColumnType(1, Types.VARCHAR);
      pstmt.defineColumnType(2, Types.VARCHAR);
      pstmt.defineColumnType(3, Types.VARCHAR);
      rs = pstmt.executeQuery();
      ctx = new BC4JContextTransferObject();

      while (rs.next()) {
        ctx.setConteIdseq(rs.getString(1));
        ctx.setName(rs.getString(2));
        ctx.setDescription(rs.getString(3));

        ContextNode ctxNode = new ContextNode(ctx, dbHelper, treeParams);
        DefaultMutableTreeNode ctxTreeNode = ctxNode.getTreeNode();

        //Adding data template nodes

        DefaultMutableTreeNode tmpLabelNode;
        DefaultMutableTreeNode disLabelNode;
        DefaultMutableTreeNode phaseLabelNode;
        DefaultMutableTreeNode[] templateNodes;
        List otherTempNodes;

        if ("CTEP".equals(rs.getString(2))) {
          tmpLabelNode =
            new DefaultMutableTreeNode(
              new WebNode(
                dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));
          templateNodes = ctxNode.getCTEPDataTemplateNodes();
          phaseLabelNode = templateNodes[0];
          disLabelNode = templateNodes[1];
          tmpLabelNode.add(disLabelNode);
          tmpLabelNode.add(phaseLabelNode);
          ctxTreeNode.add(tmpLabelNode);
        }
        else {
          otherTempNodes = ctxNode.getDataTemplateNodes();
          if (otherTempNodes.size() > 0) {
            tmpLabelNode =
            new DefaultMutableTreeNode(
              new WebNode(
                dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));

            Iterator tempIter = otherTempNodes.iterator();
            while (tempIter.hasNext()) {
              tmpLabelNode.add((DefaultMutableTreeNode) tempIter.next());
            }
            ctxTreeNode.add(tmpLabelNode);
          }
        }

        List csNodes = ctxNode.getClassificationNodes();
        DefaultMutableTreeNode csLabelNode;

        //Adding classification nodes
        if (csNodes.size() > 0) {
          csLabelNode =
            new DefaultMutableTreeNode(
              new WebNode(
                dbHelper.getUniqueId(IDSEQ_GENERATOR), "Classifications"));

          Iterator iter = csNodes.iterator();

          while (iter.hasNext()) {
            csLabelNode.add((DefaultMutableTreeNode) iter.next());
          }

          ctxTreeNode.add(csLabelNode);
        }

        //Adding protocols nodes
        //Filtering CTEP context in data element search tree
        if ((!ctx.getName().equals("CTEP")
                && treeType.equals(TreeConstants.DE_SEARCH_TREE))
             || (treeType.equals(TreeConstants.FORM_SEARCH_TREE))) {

          if ((ctx.getName().equals("CTEP") 
               && baseNode.isCTEPUser().equals("Yes"))
             || (!ctx.getName().equals("CTEP"))) {
               
          
          List protoNodes = ctxNode.getProtocolNodes();
          DefaultMutableTreeNode protoLabelNode;

          if (!protoNodes.isEmpty()) {
            protoLabelNode =
              new DefaultMutableTreeNode(
                new WebNode(
                  dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Forms"));

            Iterator tmpIter = protoNodes.iterator();

            while (tmpIter.hasNext()) {
              protoLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
            }

            ctxTreeNode.add(protoLabelNode);
          }
        }
        }

        tree.add(ctxTreeNode);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }

        if (pstmt != null) {
          pstmt.close();
        }

        if (conn != null) {
          dbHelper.returnConnection();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    }

    return tree;
  }
}

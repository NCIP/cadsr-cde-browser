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


public class CDEBrowserTree extends WebTree {
  final static String contextQueryStmt =
    "SELECT conte_idseq " + "      ,name " + "      ,description " +
    "FROM  sbr.contexts " + "ORDER BY name ";
  final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";

  public CDEBrowserTree() {
  }

  public DefaultMutableTreeNode getTree(Hashtable params)
    throws Exception {
    return buildTree();
  }

  public DefaultMutableTreeNode buildTree() throws Exception {
    DBUtil dbHelper = new DBUtil();
    OraclePreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    Context ctx = null;
    DefaultMutableTreeNode tree = null;

    try {
      CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      String datasourceName = params.getSbrDSN();

      //if (dbHelper.getConnectionFromContainer("jdbc/SBR_DCoreDS")) {
      if (dbHelper.getConnectionFromContainer(datasourceName)) {
        conn = dbHelper.getConnection();
      } else {
        throw new Exception("Unable to connect to the database");
      }

      WebNode contexts =
        new WebNode(
          dbHelper.getUniqueId(IDSEQ_GENERATOR), "caDSR Contexts",
          "javascript:performAction('P_PARAM_TYPE=P_PARAM_TYPE&P_IDSEQ=P_IDSEQ&"+
          "PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes')");
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

        ContextNode ctxNode = new ContextNode(ctx, dbHelper);
        DefaultMutableTreeNode ctxTreeNode = ctxNode.getTreeNode();

        //Adding data template nodes

        /*DefaultMutableTreeNode tmpLabelNode;
           DefaultMutableTreeNode disLabelNode;
           if ("CTEP".equals(rs.getString(2))) {
             tmpLabelNode = new DefaultMutableTreeNode
             (new WebNode(dbHelper.getUniqueId(IDSEQ_GENERATOR),"Protocol Form Templates"));
             disLabelNode = ctxNode.getDataTemplateNodes();
             tmpLabelNode.add(disLabelNode);
             ctxTreeNode.add(tmpLabelNode);
           }*/
        DefaultMutableTreeNode tmpLabelNode;
        DefaultMutableTreeNode disLabelNode;
        DefaultMutableTreeNode phaseLabelNode;
        DefaultMutableTreeNode[] templateNodes;

        if ("CTEP".equals(rs.getString(2))) {
          tmpLabelNode =
            new DefaultMutableTreeNode(
              new WebNode(
                dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));
          templateNodes = ctxNode.getDataTemplateNodes();
          phaseLabelNode = templateNodes[0];
          disLabelNode = templateNodes[1];
          tmpLabelNode.add(disLabelNode);
          tmpLabelNode.add(phaseLabelNode);
          ctxTreeNode.add(tmpLabelNode);
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
        //Filtering CTEP context
        if (!ctx.getName().equals("CTEP")) {
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
      } finally {
      }
    }

    return tree;
  }
}

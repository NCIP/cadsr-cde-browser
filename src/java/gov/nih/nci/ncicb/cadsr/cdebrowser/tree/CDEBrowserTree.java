package gov.nih.nci.ncicb.cadsr.cdebrowser.tree;

import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JContextTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import gov.nih.nci.ncicb.cadsr.util.ConnectionHelper;
import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;
import gov.nih.nci.ncicb.webtree.WebNode;
import gov.nih.nci.ncicb.webtree.WebTree;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;


public class CDEBrowserTree extends WebTree implements TreeConstants {


  String contextQueryStmt =
    "SELECT conte_idseq " +
    "      ,name " +
    "      ,description " +
    "FROM  sbr.contexts " ;

  final static String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
  private String treeType;
  private String functionName;
  private String extraURLParameters =
    "&PageId=DataElementsGroup&NOT_FIRST_DISPLAY=1&performQuery=yes";
  private String contextExcludeListStr = null;

  private boolean  showFormsAlphebetically = false;
  public CDEBrowserTree() {
  CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
  showFormsAlphebetically = new Boolean(params.getShowFormsAlphebetical()).booleanValue();

  }

  public DefaultMutableTreeNode getTree(Hashtable params) throws Exception {
    treeType = (String)params.get("treeType");
    functionName = (String)params.get("functionName");
    contextExcludeListStr = (String)params.get(TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR);
    return buildTree(params);
  }

  public DefaultMutableTreeNode buildTree(Hashtable treeParams) throws Exception {
    DBUtil dbHelper = new DBUtil();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    Context ctx = null;
    DefaultMutableTreeNode tree = null;
    BaseTreeNode baseNode = null;

    //TimeUtils.recordStartTime("Tree");
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

      if(!treeType.equals(TreeConstants.DE_SEARCH_TREE) || contextExcludeListStr==null)
      {
        contextQueryStmt = contextQueryStmt +" ORDER BY upper(name) ";
      }
      else
      {
        contextQueryStmt = contextQueryStmt +" where upper(name) NOT IN ( " +contextExcludeListStr + ") ORDER BY upper(name) ";
      }

      pstmt = (PreparedStatement) conn.prepareStatement(contextQueryStmt);

      //pstmt.defineColumnType(1, Types.VARCHAR);
      //pstmt.defineColumnType(2, Types.VARCHAR);
      //pstmt.defineColumnType(3, Types.VARCHAR);
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
        else
        {
          otherTempNodes = ctxNode.getDataTemplateNodes();


          if (!otherTempNodes.isEmpty() ) {
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



        //Adding classification nodes

        List csNodes = ctxNode.getClassificationNodes(treeType);
        DefaultMutableTreeNode csLabelNode;

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
        //End Adding Classification Node

        //Adding protocols nodes
        //Filtering CTEP context in data element search tree

        if ((!ctx.getName().equals("CTEP")
                && treeType.equals(TreeConstants.DE_SEARCH_TREE))
              //Publish Change order
             || (baseNode.isCTEPUser().equals("Yes")&& treeType.equals(TreeConstants.DE_SEARCH_TREE))
             || (treeType.equals(TreeConstants.FORM_SEARCH_TREE))) {

          if ((ctx.getName().equals("CTEP")
               && baseNode.isCTEPUser().equals("Yes"))
             || (!ctx.getName().equals("CTEP"))) {


                List protoNodes = ctxNode.getProtocolNodes();
                /**
                List formNodes = new ArrayList();
                formNodes = ctxNode.getFormsWithNoProtocolNodes();
                **/
               /**
                if(showFormsAlphebetically)
                  formNodes=ctxNode.getFormNodes();
              **/

                DefaultMutableTreeNode protocolFormsLabelNode =null;
                DefaultMutableTreeNode protocolLabelNode =null;
                DefaultMutableTreeNode formsLabelNode =null;

                if (!protoNodes.isEmpty())
                {                
                  protocolFormsLabelNode =
                    new DefaultMutableTreeNode(
                      new WebNode(
                        dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Forms"));
              
                /** Will be added directlt to Proto col forms Node
                  if(!formNodes.isEmpty()&&showFormsAlphebetically)
                  {
                   formsLabelNode =
                     new DefaultMutableTreeNode(
                      new WebNode(
                        dbHelper.getUniqueId(IDSEQ_GENERATOR), "Listed Alphabetically"));
                    Iterator tmpIter = formNodes.iterator();
                    while (tmpIter.hasNext()) {
                      formsLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
                      }
                    protocolFormsLabelNode.add(formsLabelNode);
                  }
                  
                  if(!protoNodes.isEmpty())
                  {
                   protocolLabelNode =
                     new DefaultMutableTreeNode(
                      new WebNode(
                        dbHelper.getUniqueId(IDSEQ_GENERATOR), "Listed by Protocol"));

                   Iterator tmpIter = protoNodes.iterator();
                    while (tmpIter.hasNext()) {
                      protocolLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
                      }
                   protocolFormsLabelNode.add(protocolLabelNode);

                  }
                 **/
                 // Add form with no protocol will taken out for this release
                 /**
                  if(!formNodes.isEmpty())
                  {
                    Iterator tmpIter = formNodes.iterator();
                    while (tmpIter.hasNext()) {
                      protocolFormsLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
                      }
                  } 
                  **/
                  // Add form with no protocol
                  if(!protoNodes.isEmpty())
                  {
                   Iterator tmpIter = protoNodes.iterator();
                    while (tmpIter.hasNext()) {
                      protocolFormsLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
                      }
                  }              
              ctxTreeNode.add(protocolFormsLabelNode);
            }
         }
      }
        //End Add Protocol Nodes


        //Display Catalog

          //Get Publishing Node info
          if (ctx.getName().equals("caBIG"))
          {
          Map info = ctxNode.getPublisingNodeInfo();
          if(!info.isEmpty())
          {
            Map formPublishInfo = (Map)info.get(PersistenceConstants.CSI_TYPE_PUBLISH_FORM);
            if(formPublishInfo==null) formPublishInfo = new HashMap();
            Map templatePublishInfo = (Map)info.get(PersistenceConstants.CSI_TYPE_PUBLISH_TEMPLATE);
            if(templatePublishInfo==null) templatePublishInfo = new HashMap();

            if( !formPublishInfo.isEmpty()|| !templatePublishInfo.isEmpty())
            {
              DefaultMutableTreeNode publishNode = new DefaultMutableTreeNode(
                              new WebNode( dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                   (String)formPublishInfo.get("publishNodeLabel")));

              List publishedProtocolNodes = null;
              List publishedTemplateNodes = null;
              List publishedFormNodes = null;

              boolean addPublishingNodes = false;
              if(!formPublishInfo.isEmpty())
              {
                   publishedProtocolNodes = ctxNode.getPublishedFormProtocolNodes();
                   publishedFormNodes = new ArrayList();

                   if(showFormsAlphebetically)
                      publishedFormNodes = ctxNode.getPublishedFormNodes();

                   if(!publishedProtocolNodes.isEmpty()||!publishedFormNodes.isEmpty())
                   {
                       DefaultMutableTreeNode publishFormNode = new DefaultMutableTreeNode(
                                      new WebNode( dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                           (String)formPublishInfo.get("publishChildNodeLabel")));
                        publishNode.add(publishFormNode);

                       if(!publishedFormNodes.isEmpty()&&showFormsAlphebetically)
                       {
                           DefaultMutableTreeNode listedAlphabetically = new DefaultMutableTreeNode(
                                      new WebNode( dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                           "Listed Alphabetically"));
                           Iterator formsIt = publishedFormNodes.iterator();
                           while(formsIt.hasNext())
                           {
                             listedAlphabetically.add((DefaultMutableTreeNode)formsIt.next());
                           }
                           publishFormNode.add(listedAlphabetically);
                       }
                       if(!publishedProtocolNodes.isEmpty())
                       {
                           DefaultMutableTreeNode listedByProtocol = new DefaultMutableTreeNode(
                                      new WebNode( dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                           "Listed by Protocol"));
                           Iterator protocolIt = publishedProtocolNodes.iterator();
                           while(protocolIt.hasNext())
                           {
                             listedByProtocol.add((DefaultMutableTreeNode)protocolIt.next());
                           }
                           publishFormNode.add(listedByProtocol);
                       }
                       addPublishingNodes=true;
                   }
              }
              if(!templatePublishInfo.isEmpty())
              {
                  publishedTemplateNodes = ctxNode.getPublishedTemplateNodes();

                  if(publishedTemplateNodes!=null&&!publishedTemplateNodes.isEmpty())
                  {
                      DefaultMutableTreeNode publishTemplateNode = new DefaultMutableTreeNode(
                                      new WebNode( dbHelper.getUniqueId(IDSEQ_GENERATOR),
                                           (String)templatePublishInfo.get("publishChildNodeLabel")));

                      Iterator templateIt = publishedTemplateNodes.iterator();

                      while(templateIt.hasNext())
                      {
                        publishTemplateNode.add((DefaultMutableTreeNode)templateIt.next());
                      }
                      publishNode.add(publishTemplateNode);
                      addPublishingNodes=true;
                  }
              }
              if(addPublishingNodes)
                  ctxTreeNode.add(publishNode);
            }
          }
        }
        //End Catalog

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
    //TimeUtils.recordEndTime("Tree");
    //System.out.println("Time to build tree in Milli  :  "+TimeUtils.getLapsedTime("Tree"));
    //System.out.println("Time to build tree in Minute  :  "+TimeUtils.getLapsedTimeInMinutes("Tree"));
    return tree;
  }

}

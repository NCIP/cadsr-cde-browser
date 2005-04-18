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
      System.out.println("Tree Start "+TimeUtils.getEasternTime());
      CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
      String datasourceName = params.getSbrDSN();


      if (dbHelper.getConnectionFromContainer(datasourceName)) {
        conn = dbHelper.getConnection();
      } else {
        throw new Exception("Unable to connect to the database");
      }
      baseNode = new BaseTreeNode(dbHelper,treeParams);
      CDEBrowserTreeCache cache = CDEBrowserTreeCache.getAnInstance(conn,baseNode);
      cache.init(conn,baseNode,treeParams);
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
        contextQueryStmt = contextQueryStmt +" ORDER BY name ";
      }
      else
      {
        contextQueryStmt = contextQueryStmt +" where name NOT IN ( " +contextExcludeListStr + ") ORDER BY name ";
      }

      pstmt = (PreparedStatement) conn.prepareStatement(contextQueryStmt);

      //pstmt.defineColumnType(1, Types.VARCHAR);
      //pstmt.defineColumnType(2, Types.VARCHAR);
      //pstmt.defineColumnType(3, Types.VARCHAR);
      rs = pstmt.executeQuery();
      ctx = new BC4JContextTransferObject();

      while (rs.next()) {
        String currContextId = rs.getString(1);
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

          System.out.println("CTEP Templates Start "+TimeUtils.getEasternTime());
          cache.setCtepIdSeq(currContextId);
          cache.initCtepInfo(conn,baseNode,ctxNode.getTemplateTypes());
          tmpLabelNode =
            new DefaultMutableTreeNode(
              new WebNode(
                dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Form Templates"));
          List ctepNodes = cache.getAllTemplatesForCtep();
          tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(0));
          tmpLabelNode.add((DefaultMutableTreeNode)ctepNodes.get(1));
          ctxTreeNode.add(tmpLabelNode);

          /**
          templateNodes = ctxNode.getCTEPDataTemplateNodes();
          phaseLabelNode = templateNodes[0];
          disLabelNode = templateNodes[1];
          tmpLabelNode.add(disLabelNode);
          tmpLabelNode.add(phaseLabelNode);
          **/
          System.out.println("CTEP Templates End "+TimeUtils.getEasternTime());
        }
        else
        {
          System.out.println("Other Templates Start "+TimeUtils.getEasternTime());
           otherTempNodes = cache.getTemplateNodes(currContextId);

          if (otherTempNodes!=null&&!otherTempNodes.isEmpty() ) {
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
         System.out.println("Other Templates End "+TimeUtils.getEasternTime());
        }



        //Adding classification nodes
        System.out.println("Classification Start "+TimeUtils.getEasternTime());
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
        System.out.println("Classification End "+TimeUtils.getEasternTime());
        //End Adding Classification Node

        //Adding protocols nodes
        //Filtering CTEP context in data element search tree
        System.out.println("Proto forms Start "+TimeUtils.getEasternTime());
        if ((!ctx.getName().equals("CTEP")
                && treeType.equals(TreeConstants.DE_SEARCH_TREE))
              //Publish Change order
             || (baseNode.isCTEPUser().equals("Yes")&& treeType.equals(TreeConstants.DE_SEARCH_TREE))
             || (treeType.equals(TreeConstants.FORM_SEARCH_TREE)))
             {

          if ((ctx.getName().equals("CTEP")
               && baseNode.isCTEPUser().equals("Yes"))
             || (!ctx.getName().equals("CTEP")))
             {


                List protoNodes = cache.getProtocolNodes(currContextId);

                List formNodes = new ArrayList();
                formNodes = cache.getFormNodesWithNoProtocol(currContextId);

                DefaultMutableTreeNode protocolFormsLabelNode =null;
                DefaultMutableTreeNode formsLabelNode =null;

                if ((protoNodes!=null&&!protoNodes.isEmpty())||
                    (formNodes!=null&&!formNodes.isEmpty()))
                {
                  protocolFormsLabelNode =
                    new DefaultMutableTreeNode(
                      new WebNode(
                        dbHelper.getUniqueId(IDSEQ_GENERATOR), "Protocol Forms"));

                 // Add form with no protocol
                  if(formNodes!=null&&!formNodes.isEmpty())
                  {
                    Iterator tmpIter = formNodes.iterator();
                    while (tmpIter.hasNext()) {
                      protocolFormsLabelNode.add((DefaultMutableTreeNode) tmpIter.next());
                      }
                  }
                  // Add form with no protocol
                  if(protoNodes!=null&&!protoNodes.isEmpty())
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
      System.out.println("Proto forms End "+TimeUtils.getEasternTime());
        //End Add Protocol Nodes


        //Display Catalog

          //Get Publishing Node info
       System.out.println("Publish strat "+TimeUtils.getEasternTime());
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
        System.out.println("Publish end "+TimeUtils.getEasternTime());
        //End Catalog

        tree.add(ctxTreeNode);
    }
     System.out.println("Tree End "+TimeUtils.getEasternTime());
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

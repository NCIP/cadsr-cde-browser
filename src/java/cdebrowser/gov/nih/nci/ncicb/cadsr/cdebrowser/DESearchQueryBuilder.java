package gov.nih.nci.ncicb.cadsr.cdebrowser;

import gov.nih.nci.ncicb.cadsr.util.StringReplace;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * This class will be used to build the sql query for CDE Browser's 
 * data element search page. The basis for the resulting query is the user request.
 * @author Ram Chilukuri
 */
public class DESearchQueryBuilder extends Object {

  private String searchStr = "";
  private String whereClause = "";
  private String [] strArray = null;
  private StringBuffer workflowList = null;
  private String xmlQueryStmt = "";
  private String vdPrefName = "";
  private String csiName = "";
  private String decPrefName = "";
  private String sqlStmt = "";
  private String treeParamIdSeq = "";
  private String treeParamType = "";
  private String treeConteIdSeq = "";
  private Object[] queryParams = new Object[]{"%","%","%","%","%"};
  private String contextUse = "";
  private String orderBy = " de_preferred_name, de_version ";
  private String sqlWithoutOrderBy;

  public DESearchQueryBuilder(HttpServletRequest request,
                              String treeParamType,
                              String treeParamIdSeq,
                              String treeConteIdSeq)  {

    this.treeParamIdSeq = treeParamIdSeq;
    this.treeParamType =  treeParamType;
    this.treeConteIdSeq = treeConteIdSeq;
    strArray = request.getParameterValues("SEARCH");
    vdPrefName = request.getParameter("txtValueDomain");
    decPrefName = request.getParameter("txtDataElementConcept");
    csiName = request.getParameter("txtClassSchemeItem");
    String selIndex = null;
    contextUse = request.getParameter("contextUse");
    if (contextUse == null) contextUse = "";
    
    String usageWhere = "";
    
    String searchStr0 = "";
    String searchStr2 = "";
    String searchStr3 = "";
    String searchStr4 = "";
    String searchStr5 = "";
    String searchStr6 = "";
    String latestWhere = "";
    String csiWhere = "";
    String fromClause = "";
    String vdFrom = "";
    //String vdWhere = "";
    String decFrom = "";
    //String decWhere = "";
    StringBuffer whereBuffer = new StringBuffer();
    

    if (strArray == null) {
      searchStr = "";
      whereClause = "";
      selIndex = "";
    }
    else {
      searchStr0 = StringUtils.replaceNull(request.getParameter("jspKeyword"));  
      String [] searchStr1 = request.getParameterValues("jspStatus");
      String [] searchIn = request.getParameterValues("jspSearchIn");
      String validValue = 
        StringUtils.replaceNull(request.getParameter("jspValidValue"));
      boolean doStatusSearch = false;
      if (searchStr1 != null) {
        if (!StringUtils.containsKey(searchStr1,"ALL")) {
          doStatusSearch = true; 
        }
      }
      searchStr2 = 
        StringUtils.replaceNull(request.getParameter("jspValueDomain"));
      searchStr3 = StringUtils.replaceNull(request.getParameter("jspCdeId"));
      searchStr4 = 
        StringUtils.replaceNull(request.getParameter("jspDataElementConcept"));
      searchStr5 = 
        StringUtils.replaceNull(request.getParameter("jspClassification"));
      searchStr6 = 
        StringUtils.replaceNull(request.getParameter("jspLatestVersion"));
      
      if (searchStr6.equals("Yes")) {
        latestWhere = " and de.latest_version_ind = '"+searchStr6+"'";    
      } 
      else {
        latestWhere = "";
      }
      if (searchStr5.equals("")) {
            csiWhere = "";
            fromClause = "";
          }
      else{
            csiWhere = " and de.de_idseq = acs.ac_idseq " +
                       " and acs.cs_csi_idseq = '"+searchStr5+"'";
            fromClause = " ,sbr.ac_csi acs ";
      }
      String wkFlow = "";
      String wkFlowWhere = "";
      String cdeIdWhere = "";
      String vdWhere="";
      String decWhere="";
      String searchWhere ="";
      String docWhere = "";
      String vvWhere = "";
      
      if (doStatusSearch){ 
        wkFlowWhere = this.buildStatusWhereClause(searchStr1);
      }
      //if (!getSearchStr(3).equals("")){
      if (!searchStr3.equals("")){
        String newCdeStr = StringReplace.strReplace(searchStr3,"*","%");
        cdeIdWhere = " and de.de_idseq IN "
                     +"(SELECT ac_idseq "
                     +"FROM designations "
                     +"WHERE detl_name IN ('CDE_ID','HISTORICAL_CDE_ID') "
                     +"AND to_char(to_number(ltrim(substr(name,1,7)))) like '"+newCdeStr+"')";
      }
      //if (!getSearchStr(2).equals("")){
      if (!searchStr2.equals("")){
        //vdWhere = " and vd.vd_idseq = '"+searchStr2+"'";
        vdWhere = " and vd.vd_idseq = '"+searchStr2+"'"
                 +" and vd.vd_idseq = de.vd_idseq ";
        vdFrom = " ,sbr.value_domains vd ";
        //queryParams[1] = searchStr2;
      }
      //if (!getSearchStr(4).equals("")){
      if (!searchStr4.equals("")){
        decWhere = " and dec.dec_idseq = '"+searchStr4+"'"
                  +" and de.dec_idseq = dec.dec_idseq ";
        decFrom = " ,sbr.data_element_concepts dec ";
        //queryParams[2] = searchStr4;
      }
      if (!searchStr0.equals("")){
        docWhere = this.buildSearchTextWhere(searchStr0,searchIn);
      }
      if (!validValue.equals("")){
        vvWhere = this.buildValidValueWhere(validValue);
      }

      whereBuffer.append(wkFlowWhere);
      whereBuffer.append(cdeIdWhere);
      whereBuffer.append(decWhere);
      whereBuffer.append(vdWhere);
      whereBuffer.append(latestWhere);
      whereBuffer.append(docWhere);
      whereBuffer.append(usageWhere);
      whereBuffer.append(vvWhere);
    }

    if (treeConteIdSeq != null) {
      usageWhere = this.getUsageWhereClause();
      whereBuffer.append(usageWhere);
    }
    whereClause = whereBuffer.toString();
      String fromWhere = "";
      if (treeParamType == null ||treeParamType.equals("P_PARAM_TYPE")){
        fromWhere =  " from sbr.data_elements de , "+
                            "sbr.reference_documents rd , "+
                            "sbr.contexts conte, "+
                            "sbrext.de_cde_id_view dc " +
                            //"sbr.value_domains vd, "+
                            //"sbr.data_element_concepts dec " +
                            vdFrom +
                            decFrom +
                            fromClause+
                     " where de.deleted_ind = 'No'  "+
                     " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                     //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                     " and de.asl_name != 'RETIRED DELETED' " + 
                     " and conte.conte_idseq = de.conte_idseq " +
                     " and de.de_idseq = dc.ac_idseq (+) "+
                     //" and vd.vd_idseq = de.vd_idseq " +
                     //" and dec.dec_idseq = de.dec_idseq " + 
                     csiWhere + whereClause;
                           
      }
      else if (treeParamType.equals("CONTEXT")){
        fromWhere= " from sbr.data_elements de , "+
                             "sbr.reference_documents rd , "+
                             "sbr.contexts conte, "+
                             "sbrext.de_cde_id_view dc " +
                             //"sbr.value_domains vd, "+
                             //"sbr.data_element_concepts dec " +
                             vdFrom +
                             decFrom +
                             fromClause+
                   " where de.deleted_ind = 'No' "+
                   " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                   //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                   " and de.asl_name != 'RETIRED DELETED' " + 
                   " and conte.conte_idseq = de.conte_idseq " +
                   " and de.de_idseq = dc.ac_idseq (+) " +
                   //" and conte.conte_idseq = '"+treeParamIdSeq+"'" +
                   //" and vd.vd_idseq = de.vd_idseq " +
                   //" and dec.dec_idseq = de.dec_idseq " +
                   //usageWhere +
                    csiWhere + whereClause;
                           
      }
      else if (treeParamType.equals("PROTOCOL")){
        fromWhere = " from  sbr.data_elements de , " +
                               " sbr.reference_documents rd , " +
                               " sbr.contexts conte, " +
                               " sbrext.de_cde_id_view dc, " +
                               " sbrext.quest_contents_ext frm ," +
                               " sbrext.protocols_ext pt ," +
                               " sbrext.quest_contents_ext qc " +
                               //"sbr.value_domains vd, "+
                               //"sbr.data_element_concepts dec " +
                               vdFrom +
                               decFrom +
                               fromClause+
                   " where de.deleted_ind = 'No' "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                         //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) " +
                         " and pt.proto_idseq = frm.proto_idseq " +
                         " and frm.qtl_name = 'CRF' " +
                         " and qc.dn_crf_idseq = frm.qc_idseq " +
                         " and qc.qtl_name = 'QUESTION' " +
                         " and qc.de_idseq = de.de_idseq " +
                         " and pt.proto_idseq = '"+treeParamIdSeq+"'" + 
                         //" and vd.vd_idseq = de.vd_idseq " +
                         //" and dec.dec_idseq = de.dec_idseq " +
                         csiWhere + whereClause;;
      }
      else if (treeParamType.equals("CRF")||treeParamType.equals("TEMPLATE")){
        fromWhere = " from  sbr.data_elements de , " +
                               " sbr.reference_documents rd , " +
                               " sbr.contexts conte, " +
                               " sbrext.de_cde_id_view dc, " +
                               " sbrext.quest_contents_ext qc " +
                               //" sbr.value_domains vd, "+
                               //" sbr.data_element_concepts dec " +
                               vdFrom +
                               decFrom +
                               fromClause+
                    " where de.deleted_ind = 'No'  "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                         //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) " +
                         " and qc.dn_crf_idseq = '"+treeParamIdSeq+"'" +
                         " and qc.qtl_name = 'QUESTION' " +
                         " and qc.de_idseq = de.de_idseq " +
                        // " and vd.vd_idseq = de.vd_idseq " +
                        // " and dec.dec_idseq = de.dec_idseq " +
                         csiWhere + whereClause;
                           
      }
      else if (treeParamType.equals("CSI")){
        if (searchStr5.equals("")) 
          csiWhere = " and acs.cs_csi_idseq = '"+treeParamIdSeq+"'";
        else
          csiWhere = " and acs.cs_csi_idseq IN ('"+treeParamIdSeq+"','"+searchStr5+"')";
        fromWhere = " from  sbr.data_elements de , " +
                               " sbr.reference_documents rd , " +
                               " sbr.contexts conte, " +
                               " sbrext.de_cde_id_view dc, " +
                               " sbr.ac_csi acs " +
                               //" sbr.value_domains vd, "+
                               //" sbr.data_element_concepts dec " +
                               vdFrom +
                               decFrom +
                         " where de.deleted_ind = 'No' "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                        //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) " +
                         csiWhere +
                         " and acs.ac_idseq = de.de_idseq " +
                         //" and vd.vd_idseq = de.vd_idseq " +
                         //" and dec.dec_idseq = de.dec_idseq " +
                         whereClause;
                           
      }
      else if (treeParamType.equals("CLASSIFICATION")){
        if (searchStr5.equals("")) 
          csiWhere = "";
        else
          csiWhere = " and acs.cs_csi_idseq = '"+searchStr5+"'";

        fromWhere = " from  sbr.data_elements de , " +
                               " sbr.reference_documents rd , " +
                               " sbr.contexts conte, " +
                               " sbrext.de_cde_id_view dc, " +
                               " sbr.ac_csi acs, " +
                               " sbr.cs_csi csc " +
                               //" sbr.value_domains vd, "+
                               //" sbr.data_element_concepts dec " +
                               vdFrom +
                               decFrom +
                         " where de.deleted_ind = 'No' "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                         //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) " +
                         " and csc.cs_idseq = '"+treeParamIdSeq+"'" +
                         " and csc.cs_csi_idseq = acs.cs_csi_idseq " +
                         " and acs.ac_idseq = de.de_idseq " +
                         //" and vd.vd_idseq = de.vd_idseq " +
                         //" and dec.dec_idseq = de.dec_idseq " +
                         csiWhere + whereClause;
                           
      }
      else if (treeParamType.equals("CORE")) {
        fromWhere = " from sbr.data_elements de , "+
                                "sbr.reference_documents rd , "+
                                "sbr.contexts conte, "+
                                "sbrext.de_cde_id_view dc " +
                                //"sbr.value_domains vd, "+
                                //"sbr.data_element_concepts dec " +
                                vdFrom +
                                decFrom +
                                fromClause+
                         " where de.deleted_ind = 'No' "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                         //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) "+
                         //" and vd.vd_idseq = de.vd_idseq " +
                         //" and dec.dec_idseq = de.dec_idseq " + 
                         " and de.de_idseq in ( select de_idseq " +
                                              " from   sbrext.core_noncore_de_view " +
                                              " where csi_idseq = '"+treeParamIdSeq+"'" +
                                              " and de_group = 'CORE') "+
                         csiWhere + whereClause;
      }
      else if (treeParamType.equals("NON-CORE")) {
        fromWhere = " from sbr.data_elements de , "+
                                "sbr.reference_documents rd , "+
                                "sbr.contexts conte, "+
                                "sbrext.de_cde_id_view dc " +
                                //"sbr.value_domains vd, "+
                                //"sbr.data_element_concepts dec " +
                                vdFrom +
                                decFrom +
                                fromClause+
                         " where de.deleted_ind = 'No' "+
                         " and de.de_idseq = rd.ac_idseq (+) and rd.dctl_name (+) = 'LONG_NAME'" +
                         //" and de.asl_name not in ('RETIRED PHASED OUT','RETIRED DELETED') " + 
                         " and de.asl_name != 'RETIRED DELETED' " + 
                         " and conte.conte_idseq = de.conte_idseq " +
                         " and de.de_idseq = dc.ac_idseq (+) "+
                         //" and vd.vd_idseq = de.vd_idseq " +
                         //" and dec.dec_idseq = de.dec_idseq " + 
                         " and de.de_idseq in ( select de_idseq " +
                                              " from   sbrext.core_noncore_de_view " +
                                              " where csi_idseq = '"+treeParamIdSeq+"'" +
                                              " and de_group = 'NON-CORE') "+
                         csiWhere + whereClause;
      }
      //String orderBy = " order by de.preferred_name, de.version ";
      StringBuffer finalSqlStmt = new StringBuffer ();
        
      String selectClause = "SELECT de.de_idseq "
                           +"      ,de.preferred_name de_preferred_name"
                           +"      ,de.long_name "
                           +"      ,rd.doc_text "
                           +"      ,conte.name "
                           +"      ,de.asl_name "
                           +"      ,to_char(dc.min_cde_id) "
                           +"      ,de.version de_version "
                           +"      ,meta_config_mgmt.get_usedby(de.de_idseq) ";
      finalSqlStmt.append(selectClause);
      finalSqlStmt.append(fromWhere);
      sqlWithoutOrderBy = finalSqlStmt.toString();
      finalSqlStmt.append(orderBy);
      sqlStmt = finalSqlStmt.toString();
      xmlQueryStmt = "select de.de_idseq "+fromWhere;
      //buildWorkflowList(getSearchStr(1),dbUtil);
     
  }

  public String getSearchStr(int arrayIndex){
   if (strArray != null) {
     return strArray[arrayIndex];
   }
   else {
     return "";
   }
  }

  public String getXMLQueryStmt(){
    return xmlQueryStmt;
  }

  public String getQueryStmt(){
    return sqlStmt;
  }

  public String getVDPrefName(){
    if (vdPrefName == null) return "";
    return vdPrefName;
  }
  public String getDECPrefName(){
    if (decPrefName == null) return "";
    return decPrefName;
  }
  public String getCSIName(){
    if (csiName == null) return "";
    return csiName;
  }
  public Object[] getQueryParams() {
    return queryParams;
  }
  public String getContextUse() {
    return contextUse;
  }

  public String getSQLWithoutOrderBy() {
    return sqlWithoutOrderBy;
  }

  public String getOrderBy() {
    return orderBy;
  }

  private String getUsageWhereClause() {
    String usageWhere = "";
    if ("used_by".equals(contextUse)) {
          usageWhere = 
            " and de.de_idseq IN (select ac_idseq " +
            "                     from   designations des " +
            "                     where  des.conte_idseq = '"+treeConteIdSeq+"'" +
				    "                     and    des.DETL_NAME = 'USED_BY')  ";
    }
    //else if ("owned_by".equals(contextUse) || "".equals(contextUse)) {
    else if ("owned_by".equals(contextUse)) {
      usageWhere = " and conte.conte_idseq = '"+treeConteIdSeq+"' ";
    }
    else if ("both".equals(contextUse) || "".equals(contextUse) ) {
      if ("CONTEXT".equals(treeParamType)) {
        usageWhere = 
          " and de.de_idseq IN (select ac_idseq " +
          "                     from   designations des " +
          "                     where  des.conte_idseq = '"+treeConteIdSeq+"'" +
          "                     and    des.DETL_NAME = 'USED_BY' " +
          "                     UNION "+
          "                     select de_idseq "+
          "                     from   data_elements de1 "+
          "                     where  de1.conte_idseq ='"+treeConteIdSeq+"') ";
      }
    }

    return usageWhere;
  }

  private String buildStatusWhereClause(String [] statusList) {
    String wkFlowWhere = "";
    String wkFlow = "";
    if (statusList.length == 1) {
      wkFlow = statusList[0];
      wkFlowWhere = " and de.asl_name = '"+wkFlow+"'";
    }
    else {
      for (int i=0; i<statusList.length; i++) {
        if (i==0)
          wkFlow = "'"+statusList[0]+"'";
        else 
          wkFlow = wkFlow + ","+ "'"+ statusList[i]+"'";
      }
      wkFlowWhere = " and de.asl_name IN ("+wkFlow+")";
          
    }

    return wkFlowWhere;
  }

  private String buildSearchTextWhere(String text, String[] searchDomain) {
    String docWhere = "";
    String newSearchStr = "";
    String searchWhere = "";

    newSearchStr = StringReplace.strReplace(text,"*","%");
    newSearchStr = StringReplace.strReplace(newSearchStr,"'","''");
    if (StringUtils.containsKey(searchDomain,"ALL") ||
       (StringUtils.containsKey(searchDomain,"Long Name") &&
          StringUtils.containsKey(searchDomain,"Preferred Name") &&
          StringUtils.containsKey(searchDomain,"Hist") &&
          StringUtils.containsKey(searchDomain,"Doc Text"))) {
      searchWhere = " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " + 
                           " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') " +
                           " OR upper (de.preferred_name) like upper ( '"+newSearchStr+"')) ";

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere
                  +" union "
                  +" select de_idseq "
                  +" from sbr.reference_documents rd2,sbr.data_elements de2 "
                  +" where  de2.de_idseq  = rd2.ac_idseq (+) "
                  +" and    rd2.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  +" and    upper (nvl(rd2.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
      return docWhere;
    }
    
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Preferred Name") &&
            StringUtils.containsKey(searchDomain,"Hist")) {

      searchWhere = 
        " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " + 
               " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') " +
               " OR upper (de.preferred_name) like upper ( '"+newSearchStr+"')) ";

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Preferred Name") &&
            StringUtils.containsKey(searchDomain,"Doc Text")) {

      searchWhere = 
        " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " + 
               " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') " +
               " OR upper (de.preferred_name) like upper ( '"+newSearchStr+"')) ";

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Doc Text") &&
            StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " + 
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"'))";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere
                  +" union "
                  +" select de_idseq "
                  +" from sbr.reference_documents rd2,sbr.data_elements de2 "
                  +" where  de2.de_idseq  = rd2.ac_idseq (+) "
                  +" and    rd2.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  +" and    upper (nvl(rd2.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Preferred Name") &&
            StringUtils.containsKey(searchDomain,"Doc Text") &&
            StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and (upper (de.preferred_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere
                  +" union "
                  +" select de_idseq "
                  +" from sbr.reference_documents rd2,sbr.data_elements de2 "
                  +" where  de2.de_idseq  = rd2.ac_idseq (+) "
                  +" and    rd2.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  +" and    upper (nvl(rd2.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Preferred Name")) {
      searchWhere = 
        " (upper (de.preferred_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (de.long_name) like upper ( '"+newSearchStr+"')) ";

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.data_elements de1 "
                  +" where  " + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Doc Text")) {
      searchWhere = 
        " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Long Name") &&
            StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and (upper (de.long_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Preferred Name") &&
            StringUtils.containsKey(searchDomain,"Doc Text")) {
      searchWhere = 
        " and (upper (de.preferred_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere+ " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Preferred Name") &&
            StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and (upper (de.preferred_name) like upper ( '"+newSearchStr+"') " +
          " OR upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  + searchWhere+ " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Doc Text") &&
            StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') ";
           
      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere
                  +" union "
                  +" select de_idseq "
                  +" from sbr.reference_documents rd2,sbr.data_elements de2 "
                  +" where  de2.de_idseq  = rd2.ac_idseq (+) "
                  +" and    rd2.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  +" and    upper (nvl(rd2.doc_text,'%')) like upper ('"+newSearchStr+"')) ";
      return docWhere;
    }
    else if (StringUtils.containsKey(searchDomain,"Preferred Name")) {
      searchWhere = 
        " upper (de.preferred_name) like upper ( '"+newSearchStr+"') ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.data_elements de1 "
                  +" where "+ searchWhere + " ) ";
      return docWhere;
    }
    else if (StringUtils.containsKey(searchDomain,"Long Name")) {
      searchWhere = 
        " upper (de.long_name) like upper ( '"+newSearchStr+"') ";
           
      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.data_elements de1 "
                  +" where "+ searchWhere + " ) " ;
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Hist")) {
      searchWhere = 
        " and upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') ";
                           

      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'HISTORIC SHORT CDE NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    else if(StringUtils.containsKey(searchDomain,"Doc Text")) {
      searchWhere = 
        " and upper (nvl(rd1.doc_text,'%')) like upper ('"+newSearchStr+"') ";
                           
      docWhere = " and de.de_idseq IN "
                  +"(select de_idseq "
                  +" from sbr.reference_documents rd1,sbr.data_elements de1 "
                  +" where  de1.de_idseq  = rd1.ac_idseq (+) "
                  +" and    rd1.dctl_name (+) = 'LONG_NAME' "
                  + searchWhere + " ) ";
      return docWhere;
    }
    
    return docWhere;
  }

  private String buildValidValueWhere(String value) {
    String newSearchStr = StringReplace.strReplace(value,"*","%");
    String vvWhere = " and de.vd_idseq IN( "+
                     " select distinct vd.vd_idseq " +
                     " from   sbr.value_domains vd, "+
                             "sbr.vd_pvs vp, " +
                             "sbr.permissible_values pv " +
                     " where  vd.vd_idseq = vp.vd_idseq " +
                     " and    pv.pv_idseq = vp.pv_idseq " +
                     " and    upper(pv.value) like upper('"+newSearchStr+"'))";

    return vvWhere;
    
  }
}

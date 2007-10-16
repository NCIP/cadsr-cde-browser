package gov.nih.nci.ncicb.cadsr.xml;

import gov.nih.nci.ncicb.cadsr.util.ConnectionHelper;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.xml.sql.query.OracleXMLQuery;

public class XMLGeneratorBean  {
  private static Log log = LogFactory.getLog(XMLGeneratorBean.class.getName());
  private String targetView = "";
  private String whereClause = "";
  private String sqlQuery = "";
  private String orderBy = "";
  private String dataSource = "";
  private String rowset = "";
  private String row = "";
  private int maxRows = -1;
  private boolean showNull = false;
  private OracleXMLQuery xmlQuery;
  private int lastRow = 0;
  private Connection cn = null;
  private ResultSet rset = null;
  private Statement stmt = null;
  private boolean jndiDatasource = false;


  public XMLGeneratorBean() {
  }

  public String getXMLString() {
    String xmlString = "";
    ConnectionHelper connHelper = null;
    try{
    
    buildQuery();
    //System.out.println("Sql Stmt: " + sqlQuery);
    
    /*connHelper = new ConnectionHelper(dataSource);
    cn = connHelper.getConnection();*/
    
    initializeDBConnection();
    
    xmlQuery = new OracleXMLQuery(cn,sqlQuery);
    xmlQuery.setEncoding("UTF-8");
    if (!rowset.equals("")) {
      xmlQuery.setRowsetTag(rowset);
    }

    if (!row.equals("")) {
      xmlQuery.setRowTag(row);
    }
    if (maxRows != -1) {
      xmlQuery.setMaxRows(maxRows);
    }
    xmlQuery.useNullAttributeIndicator(showNull);
    xmlString = xmlQuery.getXMLString();
    
    }
    catch (Exception e) {
      log.error("getXMLString()", e);
    }
    finally {
      closeResources();
    }
    return xmlString;
  }
  public void createOracleXMLQuery() throws SQLException{
    try {
      buildQuery();
      //System.out.println("Sql Stmt: " + sqlQuery);
      /*ConnectionHelper connHelper = new ConnectionHelper(dataSource);
      cn = connHelper.getConnection();*/
      
      initializeDBConnection();
      stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
      rset = stmt.executeQuery(sqlQuery);
      
      //rset.first();
    
      xmlQuery = new OracleXMLQuery(cn,rset);
      xmlQuery.keepCursorState(true);
      //xmlQuery.keepObjectOpen(true);
      xmlQuery.setRaiseNoRowsException(true);
      xmlQuery.setRaiseException(true);
      xmlQuery.setEncoding("UTF-8");
      if (!rowset.equals("")) {
        xmlQuery.setRowsetTag(rowset);
      }

      if (!row.equals("")) {
        xmlQuery.setRowTag(row);
      }
      if (maxRows != -1) {
        xmlQuery.setMaxRows(maxRows);
      }
      xmlQuery.useNullAttributeIndicator(showNull);
    }
    catch (SQLException sqle) {
      closeResources();
      log.error("Exception createOracleXMLQuery()", sqle);
      throw sqle;
    }

  }
  public void setTargetDBObject(String target){
    targetView = target;
  }
  public void setWhereClause (String wc) {
    whereClause = " WHERE " +wc;
  }
  public void buildQuery() {
    if (!targetView.equals("")) {
       sqlQuery = "SELECT * FROM "+targetView+whereClause+orderBy; 
    }
    else {
      sqlQuery = sqlQuery+whereClause+orderBy;
    }
    
    
  }
  public void setQuery(String qry) {
    sqlQuery = qry;
  }
  public String getQuery(String qry) {
    return sqlQuery;
  }
  public void setOrderBy(String oby) {
    orderBy = " ORDER BY " + oby;
  }
  public void setDataSource(String ds) {
    dataSource = ds;
  }
  public void setRowsetTag(String rs) {
    rowset = rs;
  }
  public void setRowTag(String r) {
    row = r;
  }
  public void setMaxRowSize (int rs) {
    maxRows = rs;
  }
  public void displayNulls(boolean b) {
    showNull = b;
  }
  /**
  *  This method returns rows from the Oracle XML Query
  */
  public String getRows(int startRow, int endRow) throws SQLException{
    xmlQuery.setMaxRows(endRow - startRow);
    return xmlQuery.getXMLString();
  }

  /**
  *  This method gets next set of rows from the XML rowset
  */
  public String getNextPage() throws SQLException{
    String xmlStr = null;
    xmlStr = getRows(lastRow, lastRow + maxRows);
    lastRow+=maxRows;
    return xmlStr;
  }

  public void closeResources() {
    try{
      if (rset != null)rset.close();
      if (stmt != null)stmt.close();
      if (cn != null) cn.close();
    }
    catch (SQLException sqle) {

    }
  }
  //For testing purposes only
  private void writeToFile(String xmlStr, String fn) throws Exception
  {
    FileOutputStream newFos;
    DataOutputStream newDos;

    try {
      newFos = new FileOutputStream(fn);
      newDos = new DataOutputStream(newFos);
      newDos.writeBytes(xmlStr+"\n");
      newDos.close();
      
    }
    catch (java.io.IOException e) {
      log.error(e.getMessage(), e);
      throw e;
      
    }
  }
  
  private void initializeDBConnection () {
    if (jndiDatasource) {
      ConnectionHelper connHelper = new ConnectionHelper(dataSource);
      cn = connHelper.getConnection();
    }
    
  }
  
  public static void main(String[] args) {
    try {
      String filename = "c:\\cadsr\\cdebrowser\\download\\xml\\cadsr.xml";
      XMLGeneratorBean xmlBean = new XMLGeneratorBean();
      //xmlBean.setDataSource("jdbc/SBR_DCoreDS");
      xmlBean.setDataSource("jdbc/SBREXT_CBPRODCoreDS");
      //xmlBean.setDataSource("jdbc/SBR_CBTESTCoreDS");
      String stmt = " SELECT \"CDEId\" "+
                          ", \"LongName\" "+
                          ", \"PreferredName\" "+
                          ", \"PreferredDefinition\" "+
                          ", \"Version\" "+
                          ", \"ContextName\" "+
                          ", \"ContextVersion\" "+
                          ", \"DataElementConcept\" "+
                          ", \"ValueDomain\" " +
                          ", \"ReferenceDocumentsList\" " +
                          ", \"ClassificationsList\" " +
                    " FROM sbrext.DE_XML_GENERATOR_VIEW ";
      //xmlBean.setTargetDBObject("CABIO_DEC_XML_GENERATOR_VIEW");
    
      //xmlBean.setTargetDBObject("CDE_BROWSER_XML_VIEW");
      xmlBean.setQuery(stmt);
      xmlBean.setWhereClause("1 = 1");
      //xmlBean.setOrderBy("\"PreferredName\"");
      //xmlBean.setRowsetTag("DataElementConceptsList");
      xmlBean.setRowsetTag("DataElementsList");
      //xmlBean.setRowTag("DataElementConcept");
      xmlBean.setRowTag("DataElement");
      xmlBean.setMaxRowSize(5);
      //System.out.println(xmlBean.getXMLString());
      xmlBean.writeToFile(xmlBean.getXMLString(),filename);
    } 
    catch (Exception ex) {
      ex.printStackTrace();
    } 
    finally {
    }
    
    
  }


  public void setJndiDatasource(boolean jndiDatasource) {
    this.jndiDatasource = jndiDatasource;
  }


  public boolean isJndiDatasource() {
    return jndiDatasource;
  }
  
  public void setConnection (Connection conn) {
    cn = conn;
  }
}
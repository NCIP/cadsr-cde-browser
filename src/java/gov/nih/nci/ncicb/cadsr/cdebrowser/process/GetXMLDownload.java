package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

import gov.nih.nci.ncicb.cadsr.base.process.*;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DESearchQueryBuilder;
import gov.nih.nci.ncicb.cadsr.cdebrowser.DataElementSearchBean;
import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import gov.nih.nci.ncicb.cadsr.database.*;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

//CDE Browser Application Imports
import gov.nih.nci.ncicb.cadsr.util.*;
import gov.nih.nci.ncicb.cadsr.xml.XMLGeneratorBean;

//import oracle.cle.process.ProcessConstants;
import java.sql.Connection;
import oracle.cle.persistence.HandlerFactory;

import oracle.cle.process.PersistingProcess;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.ProcessParameter;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.Service;

// Framework imports
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.util.statemachine.TransitionConditionException;

import oracle.xml.sql.OracleXMLSQLNoRowsException;

import java.io.*;

// java imports
import java.util.*;
import java.util.zip.*;

import javax.servlet.http.*;


/**
 * @author Ram Chilukuri
 */
public class GetXMLDownload extends BasePersistingProcess {
  static final int BUFFER = 2048;

  public GetXMLDownload() {
    this(null);

    DEBUG = false;
  }

  public GetXMLDownload(Service aService) {
    super(aService);

    DEBUG = false;
  }

  /**
   * Registers all the parameters and results  (<code>ProcessInfo</code>) for
   * this process during construction.
   */
  public void registerInfo() {
    try {
      //registerParameterObject(ProcessConstants.ALL_DATA_ELEMENTS);
      registerParameterObject("desb");

      //registerResultObject(ProcessConstants.XML_DOCUMENT);
      registerStringParameter("SBREXT_DSN");
      registerStringParameter("src");
      registerStringParameter("XML_DOWNLOAD_DIR");
      registerStringResult("FILE_NAME");
      registerParameterObject("dbUtil");
      registerStringParameter("SBR_DSN");
      registerStringResult("ZIP_FILE_NAME");
      registerStringParameter("XML_PAGINATION_FLAG");
      registerStringParameter("XML_FILE_MAX_RECORDS");
      registerStringResult("FILE_TYPE");
      registerResultObject("tib");
      registerResultObject("uem");
      registerParameterObject(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
    }
    catch (ProcessInfoException pie) {
      reportException(pie, true);
    }
    catch (Exception e) {
    }
  }

  /**
   * persist: called by start to do all persisting work for this process.  If
   * this method throws an exception, then the condition returned by the
   * <code>getPersistFailureCondition()</code> is set.  Otherwise, the
   * condition returned by <code>getPersistSuccessCondition()</code> is set.
   */
  public void persist() throws Exception {
    DBUtil dbUtil = null;
    XMLGeneratorBean xmlBean = null;
    Vector zipFileVec = new Vector(10);
    DataElementSearchBean desb = null;
    int rowcount;
    String xmlString = null;
    String paginate;
    int maxRecords;
    String zipFileSuffix = "";
    String zipFilename = "";
    String fileSuffix = "";
    String filename = "";
    DESearchQueryBuilder deSearch = null;
    String source = null;
    String where = "";
    Connection cn = null;

    try {
      source = getStringInfo("src");
      if ("deSearch".equals(source)) {
        desb = (DataElementSearchBean) getInfoObject("desb");

        deSearch =
          (DESearchQueryBuilder) getInfoObject(
            ProcessConstants.DE_SEARCH_QUERY_BUILDER);

        where = deSearch.getXMLQueryStmt();

        where = "DE_IDSEQ IN (" + where + ")";
      }
      else if ("cdeCart".equals(source)) {
        HttpServletRequest myRequest =
          (HttpServletRequest) getInfoObject("HTTPRequest");
        HttpSession userSession = myRequest.getSession(false);
        CDECart cart = (CDECart)userSession.getAttribute(CaDSRConstants.CDE_CART);
        Collection items = cart.getDataElements();
        CDECartItem item = null;
        boolean firstOne = true;
        StringBuffer whereBuffer = new StringBuffer("");
        Iterator itemsIt = items.iterator();
        while (itemsIt.hasNext()) {
          item = (CDECartItem)itemsIt.next();
          if (firstOne) {
            whereBuffer.append("'" +item.getId()+"'");
            firstOne = false;
          }
          else
            whereBuffer.append(",'" +item.getId()+"'");
        }
        where = "DE_IDSEQ IN (" + whereBuffer.toString() + ")";

      }
      else {
        throw new Exception("No result set to download");
      }

      String stmt = " SELECT \"PublicId\" "+
                        ", \"LongName\" "+
                        ", \"PreferredName\" "+
                        ", \"PreferredDefinition\" "+
                        ", \"Version\" "+
                        ", \"WorkflowStatus\" "+
                        ", \"ContextName\" "+
                        ", \"ContextVersion\" "+
                        ", \"Origin\" "+
                        ", \"RegistrationStatus\" "+
                        ", \"DataElementConcept\" "+
                        ", \"ValueDomain\" " +
                        ", \"ReferenceDocumentsList\" " +
                        ", \"ClassificationsList\" " +
                        ", \"DesignationsList\" " +
                        ", \"DataElementDerivation\" " +
                   " FROM sbrext.DE_XML_GENERATOR_VIEW ";

      xmlBean = new XMLGeneratorBean();

      String sbrextJNDIName = getStringInfo("SBREXT_DSN");
      
      // Added for JBoss deployment
      
      //xmlBean.setDataSource(sbrextJNDIName);
      ApplicationParameters ap = ApplicationParameters.getInstance("cdebrowser");
      cn = DBUtil.createOracleConnection(ap.getDBUrl(),ap.getUser(),ap.getPassword());
      xmlBean.setConnection(cn);
      
      xmlBean.setQuery(stmt);
      xmlBean.setWhereClause(where);

      //xmlBean.setOrderBy("\"PreferredName\"");
      xmlBean.setRowsetTag("DataElementsList");
      xmlBean.setRowTag("DataElement");
      xmlBean.displayNulls(true);

      dbUtil = (DBUtil) getInfoObject("dbUtil");

      String dsName = getStringInfo("SBR_DSN");
      dbUtil.getConnectionFromContainer(dsName);

      paginate = getStringInfo("XML_PAGINATION_FLAG");
      maxRecords = Integer.parseInt(getStringInfo("XML_FILE_MAX_RECORDS"));

      zipFileSuffix = dbUtil.getUniqueId("SBREXT.XML_FILE_SEQ.NEXTVAL");
      zipFilename =
        getStringInfo("XML_DOWNLOAD_DIR") + "DataElements" + "_" +
        zipFileSuffix + ".zip";

      if (paginate.equals("yes")) {
        xmlBean.setMaxRowSize(maxRecords);
        xmlBean.createOracleXMLQuery();

        while ((xmlString = xmlBean.getNextPage()) != null) {
          fileSuffix = dbUtil.getUniqueId("SBREXT.XML_FILE_SEQ.NEXTVAL");

          if (fileSuffix == null) {
            throw new Exception("Error generating file suffix");
          }

          filename =
            getStringInfo("XML_DOWNLOAD_DIR") + "DataElements" + "_" +
            fileSuffix + ".xml";
          writeToFile(xmlString, filename);
          zipFileVec.addElement(filename);
        }
      }
      else {
        xmlString = xmlBean.getXMLString();
        fileSuffix = dbUtil.getUniqueId("SBREXT.XML_FILE_SEQ.NEXTVAL");

        if (fileSuffix == null) {
          throw new Exception("Error generating file suffix");
        }

        filename =
          getStringInfo("XML_DOWNLOAD_DIR") + "DataElements" + "_" +
          fileSuffix + ".xml";
        writeToFile(xmlString, filename);
        setResult("FILE_TYPE", "xml");

        //createZipFile(filename,zipFilename);
      }

      xmlString = null;
      setCondition(SUCCESS);
    }
    catch (OracleXMLSQLNoRowsException e) {
      createZipFile(zipFileVec, zipFilename);
    }
    catch (Exception ex) {
      TabInfoBean tib;
      UserErrorMessage uem;
      HttpServletRequest myRequest = null;

      try {
        tib = new TabInfoBean("cdebrowser_error_tabs");
        myRequest = (HttpServletRequest) getInfoObject("HTTPRequest");
        tib.processRequest(myRequest);

        if (tib.getMainTabNum() != 0) {
          tib.setMainTabNum(0);
        }

        uem = new UserErrorMessage();
        uem.setMsgOverview("Application Error");
        uem.setMsgText(
          "An unknown application error has occured. Please re-start the "+
          "download operation");
        setResult("tib", tib);
        setResult("uem", uem);
        setCondition(FAILURE);
        throw ex;
      }
      catch (TransitionConditionException tce) {
        reportException(tce, DEBUG);
      }
      catch (Exception e) {
        reportException(e, DEBUG);
      }

      reportException(ex, DEBUG);
    }
    finally {
      try {
        dbUtil.returnConnection();
        xmlBean.closeResources();

        //setCondition(FAILURE);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  protected TransitionCondition getPersistSuccessCondition() {
    return getCondition(SUCCESS);
  }

  protected TransitionCondition getPersistFailureCondition() {
    return getCondition(FAILURE);
  }

  private void writeToFile(
    String xmlStr,
    String fn) throws Exception {
    FileOutputStream newFos;
    DataOutputStream newDos;

    try {
      newFos = new FileOutputStream(fn);
      newDos = new DataOutputStream(newFos);
      newDos.writeBytes(xmlStr + "\n");
      newDos.close();
      setResult("FILE_NAME", fn);
    }
    catch (java.io.IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void createZipFile(
    Vector fileList,
    String zipFilename) throws Exception {
    BufferedInputStream origin = null;
    FileOutputStream dest = null;
    ZipOutputStream out = null;

    try {
      dest = new FileOutputStream(zipFilename);
      out = new ZipOutputStream(new BufferedOutputStream(dest));

      byte[] data = new byte[BUFFER];

      for (int i = 0; i < fileList.size(); i++) {
        FileInputStream fi =
          new FileInputStream((String) fileList.elementAt(i));
        origin = new BufferedInputStream(fi, BUFFER);

        ZipEntry entry = new ZipEntry((String) fileList.elementAt(i));
        out.putNextEntry(entry);

        int count;

        while ((count = origin.read(data, 0, BUFFER)) != -1) {
          out.write(data, 0, count);
        }

        origin.close();
      }

      out.close();
      setResult("FILE_NAME", zipFilename);
      setResult("FILE_TYPE", "zip");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  private void createZipFile(
    String filename,
    String zipFilename) throws Exception {
    BufferedInputStream origin = null;
    FileOutputStream dest = null;
    ZipOutputStream out = null;

    try {
      dest = new FileOutputStream(zipFilename);
      out = new ZipOutputStream(new BufferedOutputStream(dest));

      byte[] data = new byte[BUFFER];

      FileInputStream fi = new FileInputStream(filename);
      origin = new BufferedInputStream(fi, BUFFER);

      ZipEntry entry = new ZipEntry(filename);
      out.putNextEntry(entry);

      int count;

      while ((count = origin.read(data, 0, BUFFER)) != -1) {
        out.write(data, 0, count);
      }

      origin.close();

      out.close();
      setResult("FILE_NAME", zipFilename);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  private PrintWriter getPrintWriter(String newFilename)
    throws Exception {
    PrintWriter pw = null;

    try {
      pw = new PrintWriter(new BufferedWriter(new FileWriter(newFilename)));
    }
    catch (Exception ex) {
      throw ex;
    }

    return pw;
  }
}

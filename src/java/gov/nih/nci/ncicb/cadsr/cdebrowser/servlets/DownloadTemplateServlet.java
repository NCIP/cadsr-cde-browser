package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;
import gov.nih.nci.ncicb.cadsr.util.ConnectionHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleResultSet;

import oracle.sql.BLOB;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

public class DownloadTemplateServlet extends HttpServlet  {
  private static Log log = LogFactory.getLog(DownloadTemplateServlet.class.getName());
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  private ConnectionHelper ch = null;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ch = new ConnectionHelper("jdbc/SBR_DCoreDS");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idSeq = request.getParameter("templateIdseq");
    if (idSeq != null || !idSeq.equals("")){
      getOutputStream(idSeq,response);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request,response);
    
  }

  public void getOutputStream(String refDocId,HttpServletResponse response){
    InputStream is = null;
    OutputStream out = null;
    try {
      out = response.getOutputStream();
      Connection conn = ch.getConnection();
      String sqlStmt = "SELECT rb.name, rb.mime_type,rb.blob_content " + 
                       "from reference_blobs rb, reference_documents rd, administered_components ac " +
                       "where ac.ac_idseq = rd.ac_idseq " +
                       "and rd.rd_idseq = rb.rd_idseq " +
                       "and rd.dctl_name = 'IMAGE_FILE' "+
                       "and ac.ac_idseq = ?";
      log.info(sqlStmt);
      PreparedStatement ps = conn.prepareStatement(sqlStmt);
      BLOB theBlob = null;
      ps.setString(1,refDocId);
      ResultSet rs = ps.executeQuery();
      boolean exists = false;
      if (rs.next()) {
        exists = true;
        String mimeType = rs.getString(2);
        response.setContentType(mimeType);
        theBlob = ((OracleResultSet)rs).getBLOB(3);
        is = theBlob.getBinaryStream();

        //Writing to the OutputStream
        byte[] buf = new byte[4 * 1024];  // 4K buffer
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
          out.write(buf, 0, bytesRead);
        }
      }
    } 
    catch (Exception ex) {
      log.error("Exception Caught:", ex);
    } 
    finally {
      try {
        if (is != null) is.close();
        if (out != null) out.close();
        //if (db != null) db.closeDB();  
      } 
      catch (Exception ex) {
         log.error("Exception Caught cleaning up:", ex);
      } 
      finally {
      }
    }
    
  }
}
package gov.nih.nci.ncicb.cadsr.cdebrowser.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import oracle.sql.*;
import java.sql.*;
import oracle.jdbc.OracleResultSet;
import gov.nih.nci.ncicb.cadsr.util.ConnectionHelper;

public class DownloadTemplateServlet extends HttpServlet  {
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
      System.out.println(sqlStmt);
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
      ex.printStackTrace();
    } 
    finally {
      try {
        if (is != null) is.close();
        if (out != null) out.close();
        //if (db != null) db.closeDB();  
      } 
      catch (Exception ex) {
        ex.printStackTrace();
      } 
      finally {
      }
    }
    
  }
}
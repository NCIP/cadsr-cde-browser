/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.process;

//java imports
import java.util.*;
import java.io.*;
import java.util.zip.*;
import javax.servlet.http.*;

// Framework imports
import oracle.cle.persistence.*;
import oracle.cle.util.statemachine.TransitionCondition;
import oracle.cle.process.ProcessInfo;
import oracle.cle.process.ProcessResult;
import oracle.cle.process.ProcessInfoException;
import oracle.cle.process.Service;
//import oracle.cle.process.ProcessConstants;
import oracle.clex.process.*;
import oracle.cle.process.ProcessParameter;

//Application imports
import gov.nih.nci.ncicb.cadsr.common.ProcessConstants;

public class DownloadXML extends CreateGenericBinaryPage  {
  
  public DownloadXML(){
    super();
    DEBUG = false;
  }

  protected void registerInfo(){
    super.registerInfo();
    try{
      //registerStringParameter("ZIP_FILE_NAME");
      registerStringParameter("FILE_NAME");
      registerStringParameter("FILE_TYPE");
    }
    catch(ProcessInfoException pie){
      reportException(pie, DEBUG);
    }
  }

  protected String getContentType(){
    HttpServletResponse myResponse = null;
    ProcessInfo info = (ProcessInfo)getInfo("HTTPResponse");
    if (info!=null && info.isReady()) {
      myResponse = (HttpServletResponse)info.getValue();
    }
    String fileExtension = getStringInfo("FILE_TYPE");
    myResponse.setHeader("Content-disposition",
                  "attachment; filename=" +
                  "CDEBrowser_SearchResults."+fileExtension );
    //return "application/x-zip-compressed";
    return "application/octet-stream";
  }
  protected InputStream getBinaryInputStream(){
    return getFileContentsAsInputStream();
    
  }
  
  protected BufferedInputStream getFileContentsAsInputStream(){
    //String filename = getStringInfo("ZIP_FILE_NAME");
    String filename = getStringInfo("FILE_NAME");
    BufferedInputStream bis = null;
    try {
      FileInputStream fis = new FileInputStream(filename);
      bis = new BufferedInputStream(fis);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return bis;
  }
  
  
}
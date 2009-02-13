package gov.nih.nci.ncicb.cadsr.common.util;

public class ContentTypeHelper 
{

	 /* The list of known content types.	 */	
   
   private static String[] ctypes = {		".txt", 		"text/plain", 		
        ".html", 		"text/html", 		".htm", 		"text/html", 		
        ".shtm", 		"text/html", 		".shtml", 	"text/html", 		
        ".rtf", 		"application/rtf", 		".pdf", 		"application/pdf", 		
        ".zip", 		"application/zip", 		".tar", 		"application/x-tar", 		
        ".tgz", 		"application/x-tar", 		".gz", 		"application/x-gzip", 		
        ".gtar", 		"application/x-gtar", 		".sh", 		"application/x-sh", 		
        ".csh", 		"application/x-csh", 		
        ".bin", 		"application/octet-stream", 		
        ".class", 	"application/octet-stream", 		
        ".exe", 		"application/octet-stream", 		
        ".sea", 		"application/octet-stream", 		
        ".hqx", 		"application/mac-binhex40", 		
        ".doc", 		"application/msword", 		
        ".dot", 		"application/msword", 		
        ".pot", 		"application/mspowerpoint", 		
        ".pps", 		"application/mspowerpoint", 		
        ".ppt", 		"application/mspowerpoint", 		
        ".ppz", 		"application/mspowerpoint", 		
        ".ai", 		"application/postscript", 		
        ".eps", 		"application/postscript", 		
        ".ps", 		"application/postscript", 		
        ".js", 		"application/x-javascript", 		
        ".swf", 		"application/x-shockwave-flash", 		
        ".sit", 		"application/x-stuffit", 		
        ".tcl", 		"application/x-tcl", 		
        ".bmp", 		"image/bmp", 		
        ".gif", 		"image/gif", 		
        ".jpe", 		"image/jpeg", 		
        ".jpeg", 		"image/jpeg", 		
        ".jpg", 		"image/jpeg", 		
        ".png", 		"image/png", 		
        ".tif", 		"image/tiff", 		
        ".tiff", 		"image/tiff", 
        ".xls", 		"application/vnd.ms-excel"
        };
  public ContentTypeHelper()
  {
  }
  
	/**	 * Get the content type for the specified file.	 
   *   * @param  filename  String: the file name	 
   *   * @return  String: the content type	 
   *   */
   public static String getContentType(String filename) 
   {		
     for(int i = 0; i < ctypes.length; i+=2) 
      {			
       if(filename.toLowerCase().trim().endsWith(ctypes[i])) 
        {				
         return ctypes[i+1];			
        }		
      }		
      return null;	
  }      
}
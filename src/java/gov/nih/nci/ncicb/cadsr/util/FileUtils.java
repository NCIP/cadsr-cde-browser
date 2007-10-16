package gov.nih.nci.ncicb.cadsr.util;

//Import IO related classes
import java.io.IOException;

// Necessary support classes
import java.util.Properties;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;

/**
 * This utility class, is used to perform file operations such as loading of
 * properties files and retrieving file content from a HTTP request object.
 *
 * @version 1.0
 * @since 1.0
 */
public class FileUtils {

  /**
   * This method reads a properties file which is passed as
   * the parameter to it and load it into a java Properties
   * object and returns it.
   * 
   * @param file File path 
   * @return Properties The properties object
   * @exception IOException if loading properties file fails
   * @since 1.0
   */
  public static Properties loadParams(String file) 
      throws IOException {

    // Loads a ResourceBundle and creates Properties from it
    Properties     prop   = new Properties();
    ResourceBundle bundle = ResourceBundle.getBundle(file);

    // Retrieve the keys and populate the properties object
    Enumeration enum1 = bundle.getKeys();
    String      key  = null;
    while (enum1.hasMoreElements()) {
      key = (String) enum1.nextElement();

      prop.put(key, bundle.getObject(key));
    }

    return prop;
  }

  /**
   *  This method parses the Request object for the File parameter and 
   *  extracts the file content and returns it.
   *
   * @param request The request object from which the file content has to be
   * retrieved.
   * @return Contents of the file.
   * @exception IOException if retrieving file content fails
   * @since 1.0
   */
  public String loadFile(HttpServletRequest request) throws IOException {

    // Get the input stream of the request object
    ServletInputStream in = request.getInputStream();

    // String buffer to hold the contents of the file
    StringBuffer fileContent = new StringBuffer();
    byte[]       line        = new byte[128];

    // Read the first line
    int i = in.readLine(line, 0, 128);
    if (i < 3) {
      return null;
    }

    // -2 discards the newline characters  from the boundary
    int    boundaryLength = i - 2;
    String boundary       = new String(line, 0, boundaryLength);

    // The end of file boundary would have 2 '-' appended to it
    String fileBoundary = boundary + "--";
    String newLine      = null;

    // Parse the request stream , until the file boundary is reached
    do {
      i = in.readLine(line, 0, 128);

      if (i != -1) {
        newLine = new String(line, 0, i);

        if (newLine.indexOf("filename=\"") != -1) {

          // Reached the file parameter
          break;
        }
      }
      else {

        // No file input type
        return null;
      }
    }
    while (newLine.startsWith("Content-Disposition: form-data; name=\""));

    // Get the file name
    String fileName = new String(line, 0, i - 2);
    if (fileName == null) {
      return null;
    }

    //Read file content type
    i = in.readLine(line, 0, 128);

    // blank line
    i = in.readLine(line, 0, 128);

    // Load the stream content into the Stringbuffer
    do {
      i       = in.readLine(line, 0, 128);
      newLine = new String(line, 0, i);

      fileContent.append(newLine);
    }
    while ((i != -1) &&!newLine.startsWith(fileBoundary));

    // Return file content
    return fileContent.toString();
  }
}


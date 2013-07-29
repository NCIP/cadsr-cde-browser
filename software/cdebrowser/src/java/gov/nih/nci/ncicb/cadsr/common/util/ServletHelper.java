/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletHelper 
{
  public static void forwardToJsp(
                ServletContext servletContext,
                HttpServletRequest request,
                HttpServletResponse response,
                String targetPage) throws IOException, ServletException
  {
    servletContext.getRequestDispatcher(targetPage).forward(request,response);
  }
}
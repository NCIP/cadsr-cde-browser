/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util;
import java.util.List;

public class CDECompareJspUtils {
private final static int HEADER_SIZE = 150;
private final static int COLUMN_SIZE = 350;

  public CDECompareJspUtils()
  {
  }
  public static String getTotalPageWidth(Integer listSize)
  {
    Integer width = new Integer(HEADER_SIZE +listSize.intValue()*COLUMN_SIZE);
    return width.toString();
  }


  public static int getHeaderSize()
  {
    return HEADER_SIZE;
  }


  public static int getColumnSize()
  {
    return COLUMN_SIZE;
  }
}
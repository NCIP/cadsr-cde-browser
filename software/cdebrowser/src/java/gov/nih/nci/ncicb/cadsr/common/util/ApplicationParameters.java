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

package gov.nih.nci.ncicb.cadsr.common.util;

import java.util.Hashtable;
import java.util.Properties;

public class ApplicationParameters  {
  private static Hashtable existingApps = new Hashtable(11);
  private Properties systemParams = null;

  private ApplicationParameters(String propFileName) throws Exception {
    systemParams = FileUtils.loadParams(propFileName);
  }

  public static synchronized ApplicationParameters getInstance
        (String propFileName) throws Exception {
    ApplicationParameters ap = (ApplicationParameters)
                                existingApps.get(propFileName);
    if (ap == null) {
      ap = new ApplicationParameters(propFileName);
      existingApps.put(propFileName,ap);
    }
    return ap;
  }

  public String getParameter(String parameterName) {
    return systemParams.getProperty(parameterName);
  }

  public String getUser() {
    return systemParams.getProperty("USER");
  }

  public String getPassword() {
    return systemParams.getProperty("PASSWORD");
  }

  public String getDBUrl() {
    return systemParams.getProperty("DB_URL");
  }

  public String getDataSourceName() {
    return systemParams.getProperty("DATASOURCE");
  }
}
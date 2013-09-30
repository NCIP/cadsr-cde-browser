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

package gov.nih.nci.ncicb.cadsr.ocbrowser.struts.formbeans;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts.validator.DynaValidatorForm;


public class OCBrowserBaseDynaFormBean extends DynaValidatorForm {
  public void clear() {
    Map map = getMap();
    Iterator keys = map.keySet().iterator();

    while (keys.hasNext()) {
      String key = (String) keys.next();
      map.put(key, "");
    }
  }
}

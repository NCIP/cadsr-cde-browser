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

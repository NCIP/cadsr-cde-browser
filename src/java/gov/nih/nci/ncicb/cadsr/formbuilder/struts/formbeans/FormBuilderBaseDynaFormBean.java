package gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans;

import org.apache.struts.validator.DynaValidatorForm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Not decided what should go in here
 */
public class FormBuilderBaseDynaFormBean extends DynaValidatorForm {
  public void clear() {
    Map map = getMap();
    Iterator keys = map.keySet().iterator();

    while (keys.hasNext()) {
      String key = (String) keys.next();
      map.put(key, "");
    }
  }
}

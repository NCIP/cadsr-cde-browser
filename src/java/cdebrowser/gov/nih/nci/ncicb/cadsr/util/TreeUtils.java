package gov.nih.nci.ncicb.cadsr.util;

import java.util.Hashtable;
import java.util.StringTokenizer;


public class TreeUtils {
  public static Hashtable parseParameters(String params)
    throws Exception {
    // parses a string of parameters defined with the following syntax:
    // name1:value1;name2:value2;
    Hashtable results = new Hashtable();

    if ((params != null) && !params.equals("null")) {
      StringTokenizer st = new StringTokenizer(params, ";");

      while (st.hasMoreTokens()) {
        String pair = st.nextToken();
        StringTokenizer stPair = new StringTokenizer(pair, ":");

        if (stPair.countTokens() == 2) {
          String name = stPair.nextToken();
          String value = stPair.nextToken();
          results.put(name, value);
        }
        else {
          System.err.println("invalid 'name=value' pair parameter");
          throw (new Exception("invalid 'name=value' pair parameter"));
        }
      }
    }

    return results;
  }
}

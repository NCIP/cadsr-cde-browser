package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import javax.servlet.http.HttpSession;


public class SessionUtils {
  public SessionUtils() {
  }

  public static void addGlobalSessionKey(
    HttpSession session,
    String key) {
    if (session != null) {
      Collection keys =
        (Collection) session.getAttribute(CaDSRConstants.GLOBAL_SESSION_KEYS);

      if (keys == null) {
        keys = new HashSet();
      }

      keys.add(key);
      session.setAttribute(CaDSRConstants.GLOBAL_SESSION_KEYS, keys);
    }
  }
}

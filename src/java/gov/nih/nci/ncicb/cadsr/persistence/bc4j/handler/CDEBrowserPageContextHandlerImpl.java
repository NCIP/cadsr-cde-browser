package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.persistence.bc4j.*;

//Implementation Imports
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.resource.handler.CDEBrowserPageContextHandler;

import oracle.cle.persistence.*;

import oracle.cle.resource.*;

//J2EE MVC Framework
import oracle.cle.util.CLEUtil;

import oracle.clex.persistence.bc4j.*;

//bc4j
import oracle.jbo.*;

import oracle.jbo.server.*;

import java.util.*;


public class CDEBrowserPageContextHandlerImpl extends Handler
  implements CDEBrowserPageContextHandler {
  public CDEBrowserPageContextHandlerImpl() {
  }

  public Class getReferenceClass() {
    return CDEBrowserPageContextHandler.class;
  }

  public Object findPageContext(String nodeType, String nodeIdseq,
    Object sessionId) throws Exception {
    CDEBrowserPageContext pgContext = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      pgContext = module.getPageContextInfo(nodeType, nodeIdseq);
      releaseConnection(sessionId);
    } catch (Exception e) {
      throw e;
    } finally {
      releaseConnection(sessionId);
    }

    return pgContext;
  }

  public static void main(String[] args) {
    Integer sessionId = new Integer(1);

    try {
      CDEBrowserPageContextHandler ph =
        (CDEBrowserPageContextHandler) HandlerFactory.getHandler(CDEBrowserPageContext.class);
      CDEBrowserPageContext pc =
        (CDEBrowserPageContext) ph.findPageContext("CORE",
          "99BA9DC8-855D-4E69-E034-080020C9C0E0", sessionId);
      System.out.println("Page Context Text is: " +
        pc.getPageContextDisplayText());
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.persistence.bc4j.*;

//Implementation Imports
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;

import oracle.cle.persistence.*;

import oracle.cle.resource.*;

//J2EE MVC Framework
import oracle.cle.util.CLEUtil;

import oracle.clex.persistence.bc4j.*;

//bc4j
import oracle.jbo.*;

import oracle.jbo.server.*;

import java.util.*;


public class ClassificationHandlerImpl extends Handler
  implements ClassificationHandler {
  public ClassificationHandlerImpl() {
    super();
  }

  public Class getReferenceClass() {
    return Classification.class;
  }

  public Vector getClassificationSchemes(Object aDeIdseq, Object sessionId)
    throws Exception {
    Vector classificationSchemes = new Vector();

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      classificationSchemes = module.getClassificationSchemes(aDeIdseq);
      releaseConnection(sessionId);
    } //end try
    catch (Exception e) {
      throw e;
    } finally {
      releaseConnection(sessionId);
    }

    return classificationSchemes;
  }

  public Vector getClassificationSchemes(Object aDeIdseq, PageIterator pgIter,
    Object sessionId) throws Exception {
    Vector classificationSchemes = new Vector();

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      classificationSchemes = module.getClassificationSchemes(aDeIdseq, pgIter);
    } catch (Exception e) {
      throw e;
    } finally {
      releaseConnection(sessionId);
    }

    return classificationSchemes;
  }

  //Main Method for Testing only
  public static void main(String[] args) {
    Integer sessionId = new Integer(1);
    String testDeIdseq = new String("99BA9DC8-23A2-4E69-E034-080020C9C0E0");

    try {
      ClassificationHandler ch =
        (ClassificationHandler) HandlerFactory.getHandler(Classification.class);
      Vector classificationVector =
        ch.getClassificationSchemes(testDeIdseq, sessionId);
      Classification classification = null;

      for (int i = 0; i < classificationVector.size(); i++) {
        classification = (Classification) classificationVector.elementAt(i);
        System.err.print(classification.getClassSchemeName() + ", " +
          classification.getClassSchemeDefinition() + ", " +
          classification.getClassSchemeItemName() + ", " +
          classification.getClassSchemeItemType());
      }
       // end for
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}

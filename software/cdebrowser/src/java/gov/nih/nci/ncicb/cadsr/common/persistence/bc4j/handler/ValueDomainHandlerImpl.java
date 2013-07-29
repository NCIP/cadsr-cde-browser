/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.handler;

import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.CDEBrowserBc4jModuleImpl;
import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.common.resource.handler.ValueDomainHandler;

import oracle.cle.persistence.Handler;

import oracle.cle.util.CLEUtil;


public class ValueDomainHandlerImpl extends Handler
  implements ValueDomainHandler {
  public ValueDomainHandlerImpl() {
  }

  public Class getReferenceClass() {
    return ValueDomain.class;
  }

  public Object findObject(
    Object key,
    Object sessionId) throws Exception {
    ValueDomain vd = null;

    try {
      CDEBrowserBc4jModuleImpl module =
        (CDEBrowserBc4jModuleImpl) getConnection(sessionId);
      vd = module.getValueDomains(key);
      releaseConnection(sessionId);
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      releaseConnection(sessionId);
    }

    return vd;
  }

  public static void main(String[] args) {
  }
}

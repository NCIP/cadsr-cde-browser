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

package gov.nih.nci.ncicb.cadsr.common.dto.jdbc;

import gov.nih.nci.ncicb.cadsr.common.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.common.dto.base.AdminComponentTransferObject;
public class ClassSchemeValueObject extends AdminComponentTransferObject
                                    implements ClassificationScheme{
  protected String csIdseq = null;
  protected String csType = null;
  protected String contextName = null;
  public ClassSchemeValueObject() {
    idseq = csIdseq;
  }

  public String getCsIdseq(){
    return csIdseq;
  }
  public void setCsIdseq(String sCsIdseq) {
    csIdseq = sCsIdseq;
  }

  public String getClassSchemeType() {
    return csType;
  }
  public void setClassSchemeType(String cstlName) {
    csType = cstlName;
  }

  public String getContextName() {
    return contextName;
  }

  public void setContextName(String name) {
    contextName = name;
  }
}
package gov.nih.nci.ncicb.cadsr.dto.jdbc;

import gov.nih.nci.ncicb.cadsr.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
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
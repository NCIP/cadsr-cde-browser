package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;

import java.util.Collection;
import java.util.List;
public interface ProtocolDAO  {
  public Protocol getProtocolByPK(String idseq);  
}
package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.util.Collection;
import java.util.List;
public interface ProtocolDAO  {
  public Protocol getProtocolByPK(String idseq);  
}
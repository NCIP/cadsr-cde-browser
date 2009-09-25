package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import java.util.Collection;

public interface WorkFlowStatusDAO  {
  public Collection getWorkFlowStatusesForACType(String adminComponentType);
}
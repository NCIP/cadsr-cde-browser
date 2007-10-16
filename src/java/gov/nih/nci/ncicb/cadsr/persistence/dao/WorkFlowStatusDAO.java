package gov.nih.nci.ncicb.cadsr.persistence.dao;
import java.util.Collection;

public interface WorkFlowStatusDAO  {
  public Collection getWorkFlowStatusesForACType(String adminComponentType);
}
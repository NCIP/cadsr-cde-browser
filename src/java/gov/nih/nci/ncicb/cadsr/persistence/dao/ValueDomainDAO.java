package gov.nih.nci.ncicb.cadsr.persistence.dao;

import java.util.Collection;
import java.util.Map;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import gov.nih.nci.ncicb.cadsr.resource.ValueMeaning;

public interface ValueDomainDAO extends AdminComponentDAO {
  /**
   * Gets all Value Domains and their Valid Values
   *
   * @param <b>vdIdseqs</b> list of Value Domain Idseq.
   *
   * @return <b>Map</b> Map of Value Domain objects each having
   *   list of Valid Value objects (key: vd idseq, value: vv list)
   *   
   */
  public Map getPermissibleValues(Collection vdIdseqs);
  
  public ValueDomain getValueDomainById(String vdId);
  
  public ValueMeaning getValueMeaning(String shortMeaning);
}

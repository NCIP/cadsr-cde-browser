package gov.nih.nci.ncicb.cadsr.persistence.dao;

import java.util.Collection;
import java.util.Map;
import java.util.List;

public interface ValueDomainDAO {
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

}
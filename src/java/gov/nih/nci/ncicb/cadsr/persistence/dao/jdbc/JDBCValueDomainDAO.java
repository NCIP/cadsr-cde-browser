package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.dto.ValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValueDomainTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;
import org.springframework.jdbc.object.MappingSqlQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.sql.DataSource;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import java.util.Iterator;

public class JDBCValueDomainDAO extends JDBCAdminComponentDAO implements ValueDomainDAO {
  public JDBCValueDomainDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all Value Domains and their Valid Values
   *
   * @param <b>vdIdseqs</b> list of Value Domain Idseq.
   *
   * @return <b>Map</b> Map of Value Domain objects each having
   *   list of Valid Value objects (key: vd idseq, value: vv list).
   *   For non-enummerated VD, vd_idseq with empty list of VV should be returned.
   *   
   */
  public Map getPermissibleValues(Collection vdIdseqs){

    if (vdIdseqs == null) {
      return new HashMap();
    }
    
    if (vdIdseqs.size() == 0) {
      return new HashMap();
    }

		Iterator vdIdseqIterator = vdIdseqs.iterator();
    String whereString = "";
    Map vvMap = new HashMap();

		while(vdIdseqIterator.hasNext()) {

      // put every vd_idseq with empty list into the map
      List vvList = new ArrayList();
      String vdomainIdSeq = (String) vdIdseqIterator.next();
      // vd_idseq is the key and vvList is the value (list of valid values)
      vvMap.put(vdomainIdSeq, vvList);

      if (StringUtils.doesValueExist(whereString)){
        whereString = whereString + " or VD_IDSEQ = '" + vdomainIdSeq + "'";
      }
      else {
        whereString = whereString + " where VD_IDSEQ = '" + vdomainIdSeq + "'";
      }
		}

    PermissibleValueQuery query = new PermissibleValueQuery(vvMap);
    query.setDataSource(getDataSource());
    query.setSql(whereString, "");

    // query.execute returns map of n number of records retrieved from db
    query.execute();  
    Map records = query.getValidValueMap();
    return records;
  }


  /**
   * Inner class to get all valid values for each value domain
   * 
   */
  class PermissibleValueQuery extends MappingSqlQuery {
  
    // vd_idseq is the key and vvList is the value (list of valid values)
    private Map vvMap = null;
    
    PermissibleValueQuery(Map vvvMap) {
      super();
      this.vvMap = vvvMap;
    }

    public void setSql(String whereString, String dummy) {
      super.setSql("SELECT * FROM SBREXT.FB_VD_VV_VIEW " + whereString +"order by upper( PV_VALUE ) ");
    }
    
    public Map getValidValueMap(){
      return vvMap;
    }
    
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {

      // reaches to this point for each record retrieved.
      String vdomainIdSeq = rs.getString(1);  // VD_IDSEQ
      
      ValidValueTransferObject vvto = new ValidValueTransferObject();
      vvto.setShortMeaningValue(rs.getString(3)); // PV_VALUE
      vvto.setShortMeaning(rs.getString(4));  // PV_SHORT_MEANING
      vvto.setShortMeaningDescription(rs.getString(5)); // PV_MEANING_DESCRIPTION
      vvto.setVpIdseq(rs.getString(6)); //VP_IDSEQ
      
      List vvList = (List) vvMap.get(vdomainIdSeq);
      vvList.add(vvto);
      vvMap.put(vdomainIdSeq, vvList);
      return vvMap;
    }
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCValueDomainDAO vdTest = new JDBCValueDomainDAO(locator);
    Collection vdIdseqList = new ArrayList();
    vdIdseqList.add("29A8FB24-0AB1-11D6-A42F-0010A4C1E842");
    //vdIdseqList.add("99BA9DC8-209C-4E69-E034-080020C9C0E0");
    // not existing vd_idseq
    vdIdseqList.add("99BA9DC8-209C-4E69-E034-080020C9C0E"); 

    JDBCValueDomainDAO vddao = new JDBCValueDomainDAO(locator);
    //Map vdMap = vddao.getPermissibleValues(null);
    Map vdMap = vddao.getPermissibleValues(vdIdseqList);
    System.out.println(vdMap);
  }

}

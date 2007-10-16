package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.ClassSchemeValueObject;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Collection;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;


public class JDBCClassificationSchemeDAO extends JDBCAdminComponentDAO
  implements ClassificationSchemeDAO {

  public JDBCClassificationSchemeDAO(ServiceLocator locator) {
    super(locator);
  }


  /**
   * Retrieves all the assigned classifications for an admin component
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * @return <b>Collection</b> Collection of CSITransferObject
   */
  public Collection retrieveClassifications(String acId) {

    ClassificationQuery classificationQuery = new ClassificationQuery();
    classificationQuery.setDataSource(getDataSource());
    classificationQuery.setSql();

    return classificationQuery.execute(acId);
  }



  /**
   * Inner class to get all classifications that belong to
   * the specified data element
   */
  class ClassificationQuery extends MappingSqlQuery {
    ClassificationQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT csi.csi_name, csi.csitl_name, csi.csi_idseq, " +
        "       cscsi.cs_csi_idseq, cs.preferred_definition, cs.long_name, " +
        "        accsi.ac_csi_idseq, cs.cs_idseq, cs.version " +
        " FROM ac_csi accsi, cs_csi cscsi, " +
        "      class_scheme_items csi, classification_schemes cs  " +
        " WHERE accsi.ac_idseq = ?  " +
        " AND   accsi.cs_csi_idseq = cscsi.cs_csi_idseq " +
        " AND   cscsi.csi_idseq = csi.csi_idseq " +
        " AND   cscsi.cs_idseq = cs.cs_idseq " );

//      declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
            CSITransferObject csito;
            csito = new CSITransferObject();

            csito.setClassSchemeItemName(rs.getString(1));
      csito.setClassSchemeItemType(rs.getString(2));
      csito.setCsiIdseq(rs.getString(3));
      csito.setCsCsiIdseq(rs.getString(4));
      csito.setClassSchemeDefinition(rs.getString(5));
      csito.setClassSchemeLongName(rs.getString(6));
      csito.setAcCsiIdseq(rs.getString(7));
      csito.setCsIdseq(rs.getString(8));
      csito.setCsVersion(new Float(rs.getString(9)));
      return csito;
    }
  }

    public Collection<ClassificationScheme> getRootClassificationSchemes(String conteIdseq){
        RootClassificationSchemeQuery rootCSQuery = new RootClassificationSchemeQuery();
        rootCSQuery.setDataSource(getDataSource());
        rootCSQuery.setSql();

        return rootCSQuery.execute(conteIdseq);
    }

    public Collection<ClassificationScheme> getChildrenClassificationSchemes(String pCSIdseq){
        ChildrenClassificationSchemeQuery childrenCSQuery = new ChildrenClassificationSchemeQuery();
        childrenCSQuery.setDataSource(getDataSource());
        childrenCSQuery.setSql();

        return childrenCSQuery.execute(pCSIdseq);
    }

    public List<ClassSchemeItem> getChildrenCSI(String pCsiIdseq){
       ChildrenCSIsByCSIQuery query = new ChildrenCSIsByCSIQuery(getDataSource());
        return query.getCSCSIs(pCsiIdseq);

    }

    class RootClassificationSchemeQuery extends MappingSqlQuery {
            RootClassificationSchemeQuery() {
                super();
            }

        public void setSql() {
            super.setSql("SELECT distinct CS_IDSEQ , preferred_name, long_name, " + 
                         "preferred_definition, cstl_name,asl_name,conte_idseq " + 
                         " FROM classification_Schemes" + 
                         " WHERE CONTE_IDSEQ = ? " +
                         " and ASL_NAME = 'RELEASED' " +
                         " and CSTL_NAME != 'Publishing' " +
                         "  order by long_name  ");

            declareParameter(new SqlParameter("CS_IDSEQ", Types.VARCHAR));
        }

        protected Object mapRow(
          ResultSet rs,
          int rownum) throws SQLException {
          ClassificationScheme cs = new ClassSchemeValueObject();

          cs.setCsIdseq(rs.getString(1));
          cs.setPreferredName(rs.getString(2));
          cs.setLongName(rs.getString(3));
          cs.setPreferredDefinition(rs.getString(4));
          cs.setClassSchemeType(rs.getString(5));
          cs.setRegistrationStatus(rs.getString(6));
          cs.setConteIdseq(rs.getString(7));
          return cs;
        }
            
    }

    class ChildrenClassificationSchemeQuery extends MappingSqlQuery {
            ChildrenClassificationSchemeQuery() {
                super();
            }

        public void setSql() {
            super.setSql("SELECT distinct CS_IDSEQ , preferred_name, long_name, " + 
                         "preferred_definition, cstl_name,asl_name,conte_idseq " + 
                         " FROM classification_Schemes, cs_recs c  " + 
                         " WHERE  cs_idseq = c_cs_idseq " + 
                         " AND rl_name = 'HAS_A' " +
                         " AND p_cs_idseq = ?"  +
                         " order by long_name  ");
            declareParameter(new SqlParameter("P_CS_IDSEQ", Types.VARCHAR));
        }

        protected Object mapRow(
          ResultSet rs,
          int rownum) throws SQLException {
          ClassificationScheme cs = new ClassSchemeValueObject();

          cs.setCsIdseq(rs.getString(1));
          cs.setPreferredName(rs.getString(2));
          cs.setLongName(rs.getString(3));
          cs.setPreferredDefinition(rs.getString(4));
          cs.setClassSchemeType(rs.getString(5));
          cs.setRegistrationStatus(rs.getString(6));
          cs.setConteIdseq(rs.getString(7));
          return cs;
        }
            
    }

    public List<ClassSchemeItem> getCSCSIHierarchyByCS(String csIdseq)
    {
       CSCSIsHierByCSQuery  query = new CSCSIsHierByCSQuery(getDataSource());
       return query.getCSCSIs(csIdseq);

    }
    private class CSCSIsHierByCSQuery extends MappingSqlQuery {
       CSCSIsHierByCSQuery (DataSource ds) {
        super(
          ds, "select cs_idseq, cs_preffered_name, cs_long_name, cstl_name, "
          + "CS_PREFFRED_DEFINITION, "
          + "csi_idseq, csi_name, csitl_name, csi_description, "
          + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
          + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
          + " where CS_ASL_NAME = 'RELEASED' "
          + " and CSTL_NAME != 'Publishing' "
          + " and cs_idseq = ?"
          + " order by CSI_LEVEL, upper(csi_name)"
          );
         declareParameter(new SqlParameter("CS_IDSEQ", Types.VARCHAR));
         compile();
      }


      protected Object mapRow( ResultSet rs, int rownum) throws SQLException {

          ClassSchemeItem csiTO = new CSITransferObject();
          csiTO.setCsIdseq(rs.getString("cs_idseq"));
          csiTO.setClassSchemeType(rs.getString("cstl_name"));
          csiTO.setClassSchemeLongName(rs.getString("cs_long_name"));
          csiTO.setClassSchemePrefName(rs.getString("cs_preffered_name"));
          csiTO.setClassSchemeDefinition(rs.getString("CS_PREFFRED_DEFINITION"));

          csiTO.setCsiIdseq(rs.getString("csi_idseq"));
          csiTO.setClassSchemeItemName(rs.getString("csi_name"));
          csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
          csiTO.setCsiDescription(rs.getString("csi_description"));

          csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
          csiTO.setParentCscsiId(rs.getString("parent_csi_idseq"));
          csiTO.setCsConteIdseq(rs.getString("cs_conte_idseq"));

       return csiTO;
      }
       protected List getCSCSIs(String contextIdseq) {
          Object[] obj =
            new Object[] {
               contextIdseq
            };

          return execute(obj);
        }
    }

    public List<ClassSchemeItem> getFirstLevelCSIByCS(String csIdseq)
    {
       FirstLevelCSIsByCSQuery  query = new FirstLevelCSIsByCSQuery(getDataSource());
       return query.getCSCSIs(csIdseq);

    }
    private class FirstLevelCSIsByCSQuery extends MappingSqlQuery {
       FirstLevelCSIsByCSQuery (DataSource ds) {
        super(
          ds, "select cs_idseq, cs_preffered_name, cs_long_name, cstl_name, "
          + "CS_PREFFRED_DEFINITION, "
          + "csi_idseq, csi_name, csitl_name, csi_description, "
          + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
          + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
          + " where CS_ASL_NAME = 'RELEASED' "
          + " and CSTL_NAME != 'Publishing' "
          + " and PARENT_CSI_IDSEQ IS NULL "
          + " and cs_idseq = ?"
          + " order by CSI_LEVEL, upper(csi_name)"
          );
         declareParameter(new SqlParameter("CS_IDSEQ", Types.VARCHAR));
         compile();
      }


      protected Object mapRow( ResultSet rs, int rownum) throws SQLException {

          ClassSchemeItem csiTO = new CSITransferObject();
          csiTO.setCsIdseq(rs.getString("cs_idseq"));
          csiTO.setClassSchemeType(rs.getString("cstl_name"));
          csiTO.setClassSchemeLongName(rs.getString("cs_long_name"));
          csiTO.setClassSchemePrefName(rs.getString("cs_preffered_name"));
          csiTO.setClassSchemeDefinition(rs.getString("CS_PREFFRED_DEFINITION"));

          csiTO.setCsiIdseq(rs.getString("csi_idseq"));
          csiTO.setClassSchemeItemName(rs.getString("csi_name"));
          csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
          csiTO.setCsiDescription(rs.getString("csi_description"));

          csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
          csiTO.setParentCscsiId(rs.getString("parent_csi_idseq"));
          csiTO.setCsConteIdseq(rs.getString("cs_conte_idseq"));

       return csiTO;
      }
       protected List getCSCSIs(String contextIdseq) {
          Object[] obj =
            new Object[] {
               contextIdseq
            };

          return execute(obj);
        }
    }

    private class ChildrenCSIsByCSIQuery extends MappingSqlQuery {
       ChildrenCSIsByCSIQuery (DataSource ds) {
        super(
          ds, "select cs_idseq, cs_preffered_name, cs_long_name, cstl_name, "
          + "CS_PREFFRED_DEFINITION, "
          + "csi_idseq, csi_name, csitl_name, csi_description, "
          + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
          + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
          + " where CS_ASL_NAME = 'RELEASED' "
          + " and CSTL_NAME != 'Publishing' "
          + " and PARENT_CSI_IDSEQ =? "
          + " order by CSI_LEVEL, upper(csi_name)"
          );
         declareParameter(new SqlParameter("CSI_IDSEQ", Types.VARCHAR));
         compile();
      }


      protected Object mapRow( ResultSet rs, int rownum) throws SQLException {

          ClassSchemeItem csiTO = new CSITransferObject();
          csiTO.setCsIdseq(rs.getString("cs_idseq"));
          csiTO.setClassSchemeType(rs.getString("cstl_name"));
          csiTO.setClassSchemeLongName(rs.getString("cs_long_name"));
          csiTO.setClassSchemePrefName(rs.getString("cs_preffered_name"));
          csiTO.setClassSchemeDefinition(rs.getString("CS_PREFFRED_DEFINITION"));

          csiTO.setCsiIdseq(rs.getString("csi_idseq"));
          csiTO.setClassSchemeItemName(rs.getString("csi_name"));
          csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
          csiTO.setCsiDescription(rs.getString("csi_description"));

          csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
          csiTO.setParentCscsiId(rs.getString("parent_csi_idseq"));
          csiTO.setCsConteIdseq(rs.getString("cs_conte_idseq"));

       return csiTO;
      }
       protected List getCSCSIs(String parenetCsiIdseq) {
          Object[] obj =
            new Object[] {
            parenetCsiIdseq
            };

          return execute(obj);
        }
    }

}

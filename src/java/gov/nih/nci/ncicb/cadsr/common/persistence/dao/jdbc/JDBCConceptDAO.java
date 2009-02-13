package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.ComponentConceptTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ConceptDerivationRuleTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ConceptTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.common.resource.Concept;
import gov.nih.nci.ncicb.cadsr.common.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

import javax.sql.DataSource;


public class JDBCConceptDAO extends JDBCAdminComponentDAO implements ConceptDAO{

  private ConceptQuery conceptQuery;

  public JDBCConceptDAO(ServiceLocator locator) {
    super(locator);
  }


   public ConceptDerivationRule getPropertyConceptDerivationRuleForDEC(String decId)
   {
     DECPropertyConDrIdseqQuery conDrQuery = new DECPropertyConDrIdseqQuery(getDataSource());
     String cdrIdSeq = (String)conDrQuery.findObject(decId);
     ConceptDerivationRule conceptDr = null;
     if(cdrIdSeq!=null)
     {
        ConceptQuery query = new ConceptQuery(getDataSource());
        Collection col = query.execute(cdrIdSeq);
        conceptDr = query.getConceptDerivationRule();
     }
     return conceptDr;
   }
   public ConceptDerivationRule getObjectClassConceptDerivationRuleForDEC(String decId)
   {
     DECObjectClassConDrIdseqQuery conDrQuery = new DECObjectClassConDrIdseqQuery(getDataSource());
     String cdrIdSeq = (String)conDrQuery.findObject(decId);
     ConceptDerivationRule conceptDr = null;
     if(cdrIdSeq!=null)
     {
        ConceptQuery query = new ConceptQuery(getDataSource());
        Collection col = query.execute(cdrIdSeq);
        conceptDr = query.getConceptDerivationRule();
     }
     return conceptDr;
   }
   
   public ConceptDerivationRule getRepresentationDerivationRuleForVD(String vdId)
    {
      VDRepresentationConDrIdseqQuery conDrQuery = new VDRepresentationConDrIdseqQuery(getDataSource());
      String cdrIdSeq = (String)conDrQuery.findObject(vdId);
      ConceptDerivationRule conceptDr = null;
      if(cdrIdSeq!=null)
      {
         ConceptQuery query = new ConceptQuery(getDataSource());
         Collection col = query.execute(cdrIdSeq);
         conceptDr = query.getConceptDerivationRule();
      }
      return conceptDr;
    }

   public ConceptDerivationRule findConceptDerivationRule(String derivationId) {
    ConceptQuery query = new ConceptQuery(getDataSource());
    Collection col = query.execute(derivationId);
    return query.getConceptDerivationRule();
  }
 
   public Map getAllDerivationRulesForIds(List cdrIdseqs) {
    ConceptQueryFromList query = new ConceptQueryFromList(getDataSource());
    query.addToWhereClause(cdrIdseqs);
    Collection col = query.execute();
    return query.getAllRules();
  } 
  
  public List populateConceptsForValidValues(List vvList)
  {
    List cdrIds = new ArrayList();
    Map ruleByIds = null;
    if(vvList!=null)
    {
      Iterator vvIt = vvList.iterator();
      while(vvIt.hasNext())
      {
        ValidValue vv = (ValidValue)vvIt.next();
        ConceptDerivationRule rule = vv.getConceptDerivationRule();
        if(rule!=null)
        {
          cdrIds.add(rule.getIdseq());
        }
      }
      if(!cdrIds.isEmpty())
      {
        ruleByIds = getAllDerivationRulesForIds(cdrIds);    
      }
      if(ruleByIds==null || ruleByIds.isEmpty())
       //return new ArrayList(); // Fixed by Ram Chilukuri
       return vvList;
             
      Iterator vvIt2 = vvList.iterator();
      while(vvIt2.hasNext())
      {
        ValidValue vv = (ValidValue)vvIt2.next();
        ConceptDerivationRule rule = vv.getConceptDerivationRule();
        if(rule!=null&&ruleByIds!=null)
        {
          vv.setConceptDerivationRule((ConceptDerivationRule)ruleByIds.get(rule.getIdseq()));
        }
      }      
    }
    return vvList;
  }  

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCConceptDAO cTest = new JDBCConceptDAO(locator);
    //ConceptDerivationRule rule = cTest.findConceptDerivationRule("EAEA6FFC-5948-24B5-E034-0003BA0B1A09");
    
    ConceptDerivationRule rule =  cTest.getObjectClassConceptDerivationRuleForDEC("EF115993-2433-5E0F-E034-0003BA0B1A09");
    //System.out.println(rule);
    //List list = new ArrayList();
    //list.add("EAEA6FFC-5948-24B5-E034-0003BA0B1A09");
    //Map rules = cTest.getAllDerivationRulesForIds(list);
    System.out.println(rule);

  }

  class ConceptQuery extends MappingSqlQuery {
    ConceptDerivationRule derivationRule = null;

    ConceptQuery(DataSource ds) {
      String sql =
        "select der.CONDR_IDSEQ , der.METHODS, der.RULE, der.CONCAT_CHAR" +
        " , der.CRTL_NAME, der.NAME  ,comp.CC_IDSEQ " +
        " ,comp.DISPLAY_ORDER " +
        " ,con.CON_IDSEQ , con.LONG_NAME, con.PREFERRED_NAME  " +
        " ,con.DEFINITION_SOURCE " +
        " ,con.PREFERRED_DEFINITION, con.CONTE_IDSEQ " +
        " , contexts.name conteName, con.VERSION " +
        " ,con.ASL_NAME, con.LATEST_VERSION_IND, con.CHANGE_NOTE " +
        " ,con.ORIGIN ,con.CON_ID, con.EVS_SOURCE " +
         " , comp.PRIMARY_FLAG_IND " +
        " from " +
        " sbrext.con_derivation_rules_view_ext der, sbrext.component_concepts_view_ext comp " +
        " , sbr.contexts_view contexts, sbrext.concepts_view_ext con  where " +
        "comp.CONDR_IDSEQ=der.CONDR_IDSEQ " +
        " and comp.CON_IDSEQ=con.CON_IDSEQ and der.CONDR_IDSEQ = ?" +
        " and con.CONTE_IDSEQ=contexts.CONTE_IDSEQ " +
        " order by der.CONDR_IDSEQ , comp.DISPLAY_ORDER DESC";

      this.setDataSource(ds);
      this.setSql(sql);

      declareParameter(new SqlParameter("der.CONDR_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      ComponentConcept comp = new ComponentConceptTransferObject();

      if (derivationRule == null) {
        derivationRule = new ConceptDerivationRuleTransferObject();
        derivationRule.setIdseq(rs.getString("CONDR_IDSEQ"));
        derivationRule.setType(rs.getString("CRTL_NAME"));
        derivationRule.setName(rs.getString("NAME"));
        derivationRule.setRule(rs.getString("RULE"));
        derivationRule.setMethods(rs.getString("METHODS"));
        derivationRule.setConcatenationChar(rs.getString("CONCAT_CHAR"));
      }
        comp.setIdseq(rs.getString("CC_IDSEQ"));
        comp.setDisplayOrder(
          (new Integer(rs.getString("DISPLAY_ORDER"))).intValue());
        String primaryInd = rs.getString("PRIMARY_FLAG_IND");
        if(primaryInd!=null)
            comp.setIsPrimary(StringUtils.toBoolean(primaryInd));
            
        Concept concept = new ConceptTransferObject();
        concept.setIdseq(rs.getString("CON_IDSEQ"));
        concept.setLongName(rs.getString("LONG_NAME"));
        concept.setPreferredName(rs.getString("PREFERRED_NAME"));
        concept.setPreferredDefinition(rs.getString("PREFERRED_DEFINITION"));
        concept.setVersion(new Float(rs.getString("VERSION")));
        concept.setAslName(rs.getString("ASL_NAME"));
        concept.setLatestVersionInd(rs.getString("LATEST_VERSION_IND"));
        concept.setOrigin(rs.getString("ORIGIN"));
        concept.setCode(rs.getString("PREFERRED_NAME"));
        concept.setPublicId(rs.getInt("CON_ID"));
        concept.setEvsSource(rs.getString("EVS_SOURCE"));
        concept.setDefinitionSource(rs.getString("DEFINITION_SOURCE"));

        Context context = new ContextTransferObject();
        context.setConteIdseq(rs.getString("CONTE_IDSEQ"));
        context.setName(rs.getString("conteName"));
        concept.setContext(context);

        comp.setConcept(concept);
        derivationRule.addComponentConcept(comp);


      return comp;
    }

    protected ConceptDerivationRule getConceptDerivationRule() {
      return derivationRule;
    }
  }

  class DECPropertyConDrIdseqQuery extends MappingSqlQuery {


    DECPropertyConDrIdseqQuery(DataSource ds) {
      String sql = "select condr_idseq "
                    +" from sbr.data_element_concepts_view dec "
	   			  	      +", sbrext.properties_view_ext prop "
					          +" where dec_idseq=? "
					          + " and dec.PROP_IDSEQ=prop.PROP_IDSEQ ";

      this.setDataSource(ds);
      this.setSql(sql);

      declareParameter(new SqlParameter("dec_idseq", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return rs.getString("condr_idseq");
    }

  }

  class DECObjectClassConDrIdseqQuery extends MappingSqlQuery {

    DECObjectClassConDrIdseqQuery(DataSource ds) {
      String sql = "select condr_idseq "
                    +" from sbr.data_element_concepts_view dec "
	   			  	      +", sbrext.object_classes_view_ext oc "
					          +" where dec_idseq=? "
					          + " and dec.OC_IDSEQ=oc.OC_IDSEQ ";

      this.setDataSource(ds);
      this.setSql(sql);

      declareParameter(new SqlParameter("dec_idseq", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
     return rs.getString("condr_idseq");
    }
    
  }  
  class VDRepresentationConDrIdseqQuery extends MappingSqlQuery {

        VDRepresentationConDrIdseqQuery(DataSource ds) {
          String sql = "select rep.condr_idseq "
                        +" from sbr.value_domains_view vd "
                        +",sbrext.representations_view_ext rep "
                        +" where vd_idseq=? "
					          + " and rep.REP_IDSEQ=vd.REP_IDSEQ ";

          this.setDataSource(ds);
          this.setSql(sql);

          declareParameter(new SqlParameter("vd_idseq", Types.VARCHAR));
        }

        protected Object mapRow(
          ResultSet rs,
          int rownum) throws SQLException {
         return rs.getString("condr_idseq");
        }

  }
  


  
  class ConceptQueryFromList extends MappingSqlQuery {
    Map rules = new HashMap();
    
    String sql = null;
    ConceptQueryFromList(DataSource ds) {
      sql =
        "select der.CONDR_IDSEQ , der.METHODS, der.RULE, der.CONCAT_CHAR" +
        " , der.CRTL_NAME, der.NAME  ,comp.CC_IDSEQ " +
        " ,comp.DISPLAY_ORDER " +
        " ,con.CON_IDSEQ , con.LONG_NAME, con.PREFERRED_NAME  " +
        " ,con.DEFINITION_SOURCE " +
        " ,con.PREFERRED_DEFINITION, con.CONTE_IDSEQ " +
        " , contexts.name conteName, con.VERSION " +
        " ,con.ASL_NAME, con.LATEST_VERSION_IND, con.CHANGE_NOTE " +
        " ,con.ORIGIN ,con.CON_ID, con.EVS_SOURCE " +
         " , comp.PRIMARY_FLAG_IND " +
        " from " +
        " sbrext.con_derivation_rules_view_ext der, sbrext.component_concepts_view_ext comp " +
        " , sbr.contexts_view contexts, sbrext.concepts_view_ext con  where " +
        "comp.CONDR_IDSEQ=der.CONDR_IDSEQ " +
        " and comp.CON_IDSEQ=con.CON_IDSEQ " +
        " and con.CONTE_IDSEQ=contexts.CONTE_IDSEQ ";


      this.setDataSource(ds);

    }
    
    public void addToWhereClause(List cdridseqs)
    {
      
      String startStr = " and der.CONDR_IDSEQ in ( ";
      String endStr = " ) order by comp.DISPLAY_ORDER DESC ";
      String where=startStr;
      Iterator it = cdridseqs.iterator();
      while(it.hasNext())
      {
        if(where.equals(startStr))
        {
          where=startStr+"'"+(String)it.next()+"'";
        }
        else
        {
          where=where+","+"'"+(String)it.next()+"'";
        }
      }
      String newSql = sql+where+endStr;
      this.setSql(newSql);
    }

    public Map getAllRules()
    {
      return rules;
    }
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      
      ComponentConcept comp = new ComponentConceptTransferObject();
      String ruleId = rs.getString("CONDR_IDSEQ");
      ConceptDerivationRule derivationRule = null;
      if(rules.containsKey(ruleId))
      {
        derivationRule = (ConceptDerivationRule)rules.get(ruleId);
      }

      if (derivationRule == null) {
        derivationRule = new ConceptDerivationRuleTransferObject();
        derivationRule.setIdseq(rs.getString("CONDR_IDSEQ"));
        derivationRule.setType(rs.getString("CRTL_NAME"));
        derivationRule.setName(rs.getString("NAME"));
        derivationRule.setRule(rs.getString("RULE"));
        derivationRule.setMethods(rs.getString("METHODS"));
        derivationRule.setConcatenationChar(rs.getString("CONCAT_CHAR"));
      }
        comp.setIdseq(rs.getString("CC_IDSEQ"));
        comp.setDisplayOrder(
          (new Integer(rs.getString("DISPLAY_ORDER"))).intValue());
        String primaryInd = rs.getString("PRIMARY_FLAG_IND");
        if(primaryInd!=null)
            comp.setIsPrimary(StringUtils.toBoolean(primaryInd));

        Concept concept = new ConceptTransferObject();
        concept.setIdseq(rs.getString("CON_IDSEQ"));
        concept.setLongName(rs.getString("LONG_NAME"));
        concept.setPreferredName(rs.getString("PREFERRED_NAME"));
        concept.setPreferredDefinition(rs.getString("PREFERRED_DEFINITION"));
        concept.setVersion(new Float(rs.getString("VERSION")));
        concept.setAslName(rs.getString("ASL_NAME"));
        concept.setLatestVersionInd(rs.getString("LATEST_VERSION_IND"));
        concept.setOrigin(rs.getString("ORIGIN"));
        concept.setCode(rs.getString("PREFERRED_NAME"));
        concept.setPublicId(rs.getInt("CON_ID"));
        concept.setEvsSource(rs.getString("EVS_SOURCE"));
        concept.setDefinitionSource(rs.getString("DEFINITION_SOURCE"));

        Context context = new ContextTransferObject();
        context.setConteIdseq(rs.getString("CONTE_IDSEQ"));
        context.setName(rs.getString("conteName"));
        concept.setContext(context);

        comp.setConcept(concept);
        derivationRule.addComponentConcept(comp);
        rules.put(ruleId,derivationRule);

      return comp;
    }
  
  }
}

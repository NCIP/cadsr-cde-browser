package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import java.util.List;
import java.util.Map;

public interface ConceptDAO extends AdminComponentDAO
{

  public ConceptDerivationRule findConceptDerivationRule(String derivationId);
  
  public ConceptDerivationRule getPropertyConceptDerivationRuleForDEC(String decId);
  
  public ConceptDerivationRule getObjectClassConceptDerivationRuleForDEC(String decId);

  public ConceptDerivationRule getRepresentationDerivationRuleForVD(String vdId);
  
  public Map getAllDerivationRulesForIds(List cdrIdseqs);
  
  public List populateConceptsForValidValues(List vvList);
}
package gov.nih.nci.ncicb.cadsr.persistence.bc4j.common;
import oracle.jbo.Row;
//  ---------------------------------------------------------------------
//  ---    File generated by Oracle ADF Business Components Design Time.
//  ---    Custom code may be added to this class.
//  ---------------------------------------------------------------------

public interface CompConceptsViewObjRow extends Row 
{
  String getConIdseq();

  void setConIdseq(String value);

  String getPreferredName();

  void setPreferredName(String value);

  String getLongName();

  void setLongName(String value);

  String getPreferredDefinition();

  void setPreferredDefinition(String value);

  String getConteIdseq();

  void setConteIdseq(String value);

  oracle.jbo.domain.Number getVersion();

  void setVersion(oracle.jbo.domain.Number value);

  String getAslName();

  void setAslName(String value);

  String getLatestVersionInd();

  void setLatestVersionInd(String value);

  String getDefinitionSource();

  void setDefinitionSource(String value);

  oracle.jbo.domain.Number getConId();

  void setConId(oracle.jbo.domain.Number value);

  String getEvsSource();

  void setEvsSource(String value);

  String getCondrIdseq();

  void setCondrIdseq(String value);

  String getCcIdseq();

  void setCcIdseq(String value);
}
/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;

public interface ClassificationScheme  extends AdminComponent{
  public String getCsIdseq();
  public void setCsIdseq(String sCsIdseq);

  public String getClassSchemeType();
  public void setClassSchemeType(String cstlName);

  public String getContextName();

  public void setContextName(String name);
  
}
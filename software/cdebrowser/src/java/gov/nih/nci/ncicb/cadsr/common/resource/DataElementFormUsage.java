/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;

public interface DataElementFormUsage  {
  public String getProtocolLongName();
  public String getFormLongName();
  public String getUsageType();
  public String getProtocolLeadOrg();
  public String getQuestionLongName();
  public String getPublicId();
  public String getVersion();
  public String getFormURL();
  public String getFormDetailURL();
  public String getCrfIdSeq();
}
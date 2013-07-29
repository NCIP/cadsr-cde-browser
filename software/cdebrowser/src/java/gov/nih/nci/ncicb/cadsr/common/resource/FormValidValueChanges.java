/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;
import java.io.Serializable;
import java.util.List;

public interface FormValidValueChanges extends Serializable
{
  public String getQuestionId();
  public void setQuestionId(String questionId);
  
  public List getUpdatedValidValues();
  public void setUpdatedValidValues(List fvvs);
  
  public List getNewValidValues();
  public void setNewValidValues(List fvvs);
  
  public List getDeletedValidValues();
  public void setDeletedValidValues(List fvvs);
  
  public boolean isEmpty();
}
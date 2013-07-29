/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import java.util.Collection;

public interface WorkFlowStatusDAO  {
  public Collection getWorkFlowStatusesForACType(String adminComponentType);
}
/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassificationScheme;

import java.util.Collection;
import java.util.List;

public interface ClassificationSchemeDAO extends AdminComponentDAO
{
  /** Returns the classification schemes that are roots of the HAS-A relationship
   *
   * */
  public Collection<ClassificationScheme> getRootClassificationSchemes(String conteIdseq);
  
  public Collection<ClassificationScheme> getChildrenClassificationSchemes(String pCSIdseq);
  
  public List<ClassSchemeItem> getCSCSIHierarchyByCS(String csIdseq);
  
  public List<ClassSchemeItem> getFirstLevelCSIByCS(String csIdseq);
  
  public List<ClassSchemeItem> getChildrenCSI(String pCsiIdseq);
  
}
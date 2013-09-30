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

import gov.nih.nci.ncicb.cadsr.common.resource.QuestionRepitition;
import java.util.List;

public interface QuestionRepititionDAO
{

    public List<QuestionRepitition> getQuestionRepititions(String questionId);
    
    public int updateModuleRepeatCount(
      String moduleId,
      int newCount, String username);
    
    public int deleteRepititionsForQuestion(String questionId);
    
    public int createRepitition(String questionId,QuestionRepitition repitition,String username);
}

/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
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

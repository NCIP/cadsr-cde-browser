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

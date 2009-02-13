package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;
import gov.nih.nci.ncicb.cadsr.common.resource.QuestionRepitition;

public class QuestionRepititionTransferObject implements QuestionRepitition
{
    private Question question;
    private String defaultValue;
    private FormValidValue defaultValidValue;
    private int repeatSequence;
    public QuestionRepititionTransferObject()
    {
    }

    public void setQuestion(Question question)
    {
        this.question = question;
    }

    public Question getQuestion()
    {
        return question;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValidValue(FormValidValue defaultValidValue)
    {
        this.defaultValidValue = defaultValidValue;
    }

    public FormValidValue getDefaultValidValue()
    {
        return defaultValidValue;
    }

    public void setRepeatSequence(int repeatSequence)
    {
        this.repeatSequence = repeatSequence;
    }

    public int getRepeatSequence()
    {
        return repeatSequence;
    }
}

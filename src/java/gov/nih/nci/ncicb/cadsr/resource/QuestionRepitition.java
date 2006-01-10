package gov.nih.nci.ncicb.cadsr.resource;

public interface QuestionRepitition
{
    public void setQuestion(Question question);

    public Question getQuestion();

    public void setDefaultValue(String defaultValue);

    public String getDefaultValue();

    public void setDefaultValidValue(FormValidValue defaultValidValue);

    public FormValidValue getDefaultValidValue();

    public void setRepeatSequence(int repeatSequence);

    public int getRepeatSequence();
}

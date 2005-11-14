package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;

public class FormJspUtil
{
    public static final String FORM = "form";
    public static final String MODULE = "module";
    public static final String QUESTION = "question";
    public static final String VALIDVALUE = "validvalue";
    
    public FormJspUtil()
    {
    }
    
    public static String getFormElementType(FormElement obj)
    {
        if(obj instanceof Form)
            return FORM;
        if(obj instanceof Module)
            return MODULE;
        if(obj instanceof Question)
            return QUESTION;    
        if(obj instanceof FormValidValue)
            return VALIDVALUE;               
        return " ";
    }
    

}

package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.util.Iterator;
import java.util.List;

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

    public static String  getDelimitedProtocolLongNames(List protocols, String delimiter){

          if (protocols==null || protocols.isEmpty()){
              return "";
          }

          StringBuffer sbuf = new StringBuffer();
          String delimtedProtocolLongName = null;
          Iterator it = protocols.iterator();
          while (it.hasNext()){
              Protocol  p = (Protocol)it.next();
               sbuf.append(delimiter).append(p.getLongName());
          }
          //System.out.println("subString = "  + sbuf.substring(1) );
          return sbuf.substring(delimiter.length());
        }

    public static String  getDelimitedCSILongNames(List classSchemeItems, String delimiter){

          if (classSchemeItems==null || classSchemeItems.isEmpty()){
              return "";
          }

          StringBuffer sbuf = new StringBuffer();
          String delimtedProtocolLongName = null;
          Iterator it = classSchemeItems.iterator();
          while (it.hasNext()){
              ClassSchemeItem  csi = (ClassSchemeItem)it.next();
               sbuf.append(delimiter).append(csi.getClassSchemeItemName()+" "+csi.getCsVersion());
          }

          return sbuf.substring(delimiter.length());
        }

    public static String getDefaultValue(Question question)
    {
        if(question.getDefaultValidValue()!=null && question.getDefaultValidValue().getLongName()!=null)
            return question.getDefaultValidValue().getLongName();
        if(question.getDefaultValue()!=null)
            return question.getDefaultValue();
        return "&nbsp;";
    }

    public static boolean hasModuleRepetition(Form form)
    {
       if(form.getModules()==null)
        return false;
       List modules = form.getModules();
       Iterator it = form.getModules().iterator();
       while(it.hasNext())
       {
           Module module =(Module)it.next();
           if(module.getNumberOfRepeats()>0)
            return true;
       }
       return false;
    }
}

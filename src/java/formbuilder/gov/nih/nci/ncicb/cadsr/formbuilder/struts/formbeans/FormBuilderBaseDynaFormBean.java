package gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans;
import java.util.HashMap;
import org.apache.struts.action.DynaActionForm;

/**
 * Not decided what should go in here 
 */
public class FormBuilderBaseDynaFormBean extends DynaActionForm 
{
  public void clear()
  {
    dynaValues = new HashMap();
  }
}
package gov.nih.nci.ncicb.cadsr.formbuilder.ejb;
import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;

public interface FormBuilderLocalHome extends EJBLocalHome  {
  FormBuilderLocal create() throws CreateException;
}
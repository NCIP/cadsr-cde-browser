package gov.nih.nci.ncicb.cadsr.formbuilder.ejb;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface FormBuilderHome extends EJBHome  {
  FormBuilder create() throws RemoteException, CreateException;
}
package gov.nih.nci.ncicb.cadsr.formbuilder.service.ejb;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;

public abstract class FormBuilderDynamicServiceDelegateImpl implements InvocationHandler
{

  protected Map formBuilderMethodMap;
  protected Object formbuilderEJBObj;
  
  
  public FormBuilderDynamicServiceDelegateImpl() throws FormBuilderException
  {
    init();
  }
  protected abstract void init() throws FormBuilderException;
  
  protected void loadMethods(Object obj)
  {
    formBuilderMethodMap = new HashMap();
    Method[] formBuilderMethods = obj.getClass().getMethods();
    for(int i=0;i<formBuilderMethods.length;i++)
    {
      formBuilderMethodMap.put(formBuilderMethods[i].getName(),formBuilderMethods[i]);      
    }
  }
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
  {
    try
    {
      Method serviceMethod = (Method)formBuilderMethodMap.get(method.getName());
      if(serviceMethod !=null)
      {
        return serviceMethod.invoke(formbuilderEJBObj,args);
      }
      else
      {
        throw new NoSuchMethodException(" FormBuilder service does not implement "+ method.getName());        
      }
    }
    catch(InvocationTargetException ex)
    {
      if(ex.getTargetException() instanceof DMLException)
      {
        throw new FormBuilderException(ex);
      }
      else
      {
        throw new FatalException(ex.getTargetException());
      }
    }
    
  }
}
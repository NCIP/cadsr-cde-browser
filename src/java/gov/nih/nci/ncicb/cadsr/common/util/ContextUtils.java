package gov.nih.nci.ncicb.cadsr.common.util;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import java.util.Collection;
import java.util.Iterator;

public class ContextUtils 
{
  public ContextUtils()
  {
  }
  
  public static Context getContextByName(Collection contexts, String contextName)
  {
    if(contexts==null||contexts.isEmpty()||contextName==null)
      return null;
    Iterator colIt = contexts.iterator();
    while(colIt.hasNext())
    {
      Context context = (Context)colIt.next();
     System.out.println("Context : " + context.getName());
      if(context.getName().equalsIgnoreCase(contextName))
        return context;
    }
    return null;
  }
}
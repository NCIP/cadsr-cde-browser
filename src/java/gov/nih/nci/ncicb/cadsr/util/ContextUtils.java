package gov.nih.nci.ncicb.cadsr.util;
import gov.nih.nci.ncicb.cadsr.resource.Context;
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
      if(context.getName().equalsIgnoreCase(contextName))
        return context;
    }
    return null;
  }
}
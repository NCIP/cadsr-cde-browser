package gov.nih.nci.ncicb.cadsr.util;
import gov.nih.nci.ncicb.cadsr.exception.NestedRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class StringPropertyComparator implements Comparator,SortableColumnHeader
{
  
  private Method primaryMethod;
  private String primaryField;
  private String secondaryField;
  private Method secondaryMethod;
  private Class comparingClass;
  private int order=ASCENDING;
  
  public StringPropertyComparator(Class newComparingClass)
  {
    comparingClass = newComparingClass;
  }
  public int compare(Object obj1, Object obj2)
  {
    Object[] args = null;
    
    try
    {
      if(primaryMethod==null)
        return 0;
        
      String obj1PrimaryFieldValue = (String)primaryMethod.invoke(obj1,args);
      String obj2PrimaryFieldValue = (String)primaryMethod.invoke(obj2,args);
      
      String obj1SecondaryFieldValue = null;
      String obj2SecondaryFieldValue = null;
      
      if(secondaryMethod!=null)
      {
        obj1SecondaryFieldValue = (String)secondaryMethod.invoke(obj1,args);
        obj2SecondaryFieldValue = (String)secondaryMethod.invoke(obj2,args); 
      }
      else
      {
       if(order==this.ASCENDING)
       {
        return obj1PrimaryFieldValue.compareToIgnoreCase(obj2PrimaryFieldValue);
       }
       else
       {
        return obj2PrimaryFieldValue.compareToIgnoreCase(obj1PrimaryFieldValue);
       }
      }
      
      if(order==this.ASCENDING)
      {
         if(obj1PrimaryFieldValue.equalsIgnoreCase(obj2PrimaryFieldValue))
         {
           return obj1SecondaryFieldValue.compareToIgnoreCase(obj2SecondaryFieldValue);
         }
         else
         {
            return obj1PrimaryFieldValue.compareToIgnoreCase(obj2PrimaryFieldValue);
         }
      }
      else
      {
         if(obj1PrimaryFieldValue.equalsIgnoreCase(obj2PrimaryFieldValue))
         {
           return obj2SecondaryFieldValue.compareToIgnoreCase(obj1SecondaryFieldValue);
         }
         else
         {
            return obj2PrimaryFieldValue.compareToIgnoreCase(obj1PrimaryFieldValue);
         }        
      }
    }
    catch (InvocationTargetException e)
    {
       throw new RuntimeException("Comparator Excception "+e);      
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException("Comparator Excception "+e);    
    }
   
  }

  public void setPrimary(String primary)
  {
     primaryField=primary;
     try{
      Class[] args = null;
      primaryMethod = comparingClass.getMethod(getMethodName(primary),args);
     }
     catch(Exception exp)
     {
       throw new RuntimeException("Invalid property : "
              +comparingClass.getName()+"."+primary);
     }
  }


  public String getPrimary()
  {
      return primaryField;     
  }

  public void setSecondary(String secondary)
  {
     this.secondaryField=secondary;
     try{
      Class[] args = null;
      secondaryMethod = comparingClass.getMethod(getMethodName(secondary),args);
     }
     catch(Exception exp)
     {
       throw new RuntimeException("Invalid property : "
              +comparingClass.getName()+"."+secondary);
     }
  }

  public void setRelativePrimary(String primary)
  {
    
    secondaryField=primaryField;
    secondaryMethod = primaryMethod;
    primaryField=primary;
     try{
      Class[] args = null;
      primaryMethod = comparingClass.getMethod(getMethodName(primary),args);
     }
     catch(Exception exp)
     {
       throw new RuntimeException("Invalid property : "
              +comparingClass.getName()+"."+primary);
     }    
  }
  public String getSecondary()
  {
    return this.secondaryField;
  }


  public void setOrder(int order)
  {
    this.order = order;
  }


  public int getOrder()
  {
    return order;
  }
  public String getMethodName(String fieldName)
  {
    return "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1); 
  }

}
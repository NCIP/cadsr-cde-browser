package gov.nih.nci.ncicb.cadsr.util;
import gov.nih.nci.ncicb.cadsr.exception.NestedRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This Class allows to sorta List Objects. Uses String "CompareIgnoreCase" to Compare attributes so
 * only string attributes of the Object should be used for sorting.
 * A Primary and Secondary field can be set. The order set used for sorting both 
 * primary and secondary level. If non String fields are used for Sorting, a RuntimeException will
 * be thrown
 */
public class StringPropertyComparator implements Comparator,SortableColumnHeader
{
  
  private Method primaryMethod;
  private String primaryField;
  private String secondaryField;
  private Method secondaryMethod;
  private String tertiaryField;
  private Method tertiaryMethod;
  private Class comparingClass;
  private int order=ASCENDING;
  private boolean defaultOrder;
  
  public StringPropertyComparator(Class newComparingClass)
  {
    comparingClass = newComparingClass;
  }
  /**
   * Depending on the order set compares both primary and secondary fields.
   * For order=ASCENDING
   * -ve,0, +ve int value is returned depending on the if Obj1 is greater, equal
   * or lesser than obj2 respectively
   * For order=DESCENDING
   * -ve,0, +ve int value is returned depending on the if Obj1 is lesser, equal
   * or greater than obj2 respectively
   * If the order
   * 
   */
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
  
  public String getTertiary()
  {
    return tertiaryField;
  }
  public void setTertiary(String tertiary)
  {
    tertiaryField=tertiary;
     try{
      Class[] args = null;
      tertiaryMethod = comparingClass.getMethod(getMethodName(tertiary),args);
     }
     catch(Exception exp)
     {
       throw new RuntimeException("Invalid property : "
              +comparingClass.getName()+"."+tertiary);
     }    
  }
  public void setRelativePrimary(String primary)
  {
    
    if(primaryField!=null&& primary.equalsIgnoreCase(primaryField))
     {
       return;
     }
     tertiaryField=secondaryField;
     tertiaryMethod=secondaryMethod;
     secondaryField=primaryField;
     secondaryMethod = primaryMethod;
     primaryField=primary;
     if(primaryField.equalsIgnoreCase(tertiaryField))
     {
       tertiaryField=null;
       tertiaryMethod=null;
     }
     if(primaryField.equalsIgnoreCase(tertiaryField))
      {
       tertiaryField=null;
       tertiaryMethod=null;
      }
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

  public boolean isDefaultOrder()
  {
    return defaultOrder;
  }

  public void setDefaultOrder(boolean defaultOrder)
  {
    this.defaultOrder = defaultOrder;
  }
  
   public boolean isColumnNumeric(String columnName) {
     Set<String> numericNames = new HashSet(Arrays.asList(NUMERIC_COLUMNS));
     return numericNames.contains(columnName);
   }

}
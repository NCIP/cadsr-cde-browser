package gov.nih.nci.ncicb.cadsr.cdebrowser;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.DerivedDataElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
/**
 * Used to wrap compareList with the
 * Corresponding operations
 */
public class CDECompareList 
{
  private List cdeList = new ArrayList();
  private boolean detailCDEList=false;

  public CDECompareList()
  {
  }
  
  public void add(DataElement cde)
  {
    cdeList.add(cde);
  }
  public void add(int index,DataElement cde)
  {
    cdeList.add(index,cde);
  }  
  public void remove(int index)
  {
    cdeList.remove(index);
  }
    public boolean isEmpty()
  {
    return cdeList.isEmpty();
  }
  /**
   * Removes items form the list 
   * @param indexes of items to be removed
   * @return false if there is a duplicate
   */  
  public boolean add(String[] cdeIdseqs)
  {
    boolean result=true;
    
      for(int i=0;i<cdeIdseqs.length;++i)
      {     
          DataElement de = new DataElementTransferObject();
          de.setDeIdseq(cdeIdseqs[i]);
          if(!cdeList.contains(de))
              cdeList.add(de); 
          else
              result=false;
              
      }

    return result;
  }  
  /**
   * Removes items form the list 
   * @param indexes of items to be removed
   * @return false if the operation was not successfull
   */  
  public boolean remove(String[] itemIndexes)
  {
    boolean result=false;
    
    try
    {
      List tempList = new ArrayList();
      for(int i=0;i<itemIndexes.length;++i)
      {     
          tempList.add(cdeList.get(Integer.parseInt(itemIndexes[i]))); 
      }
      ListIterator it = tempList.listIterator();
      
      while(it.hasNext())
      {     
          cdeList.remove(it.next());
      }      
      result=true;
    }
    catch (Exception e)
    {      
    }
    return true;
  }
  /**
   * Set the display order of the items in the list to the new Order
   * @param new indexes for the current items in the list
   */
  public void setItemOrder(String[] newIndexes)
  {
    boolean result = false;
    
    
    if(cdeList.size()!=newIndexes.length)
      return;
    
    List tempList = new ArrayList(cdeList.size());
    //Just added a dumy object;
    for(int i=0;i<newIndexes.length;i++)
    {
      tempList.add("");
    }
    
      for(int i=0;i<newIndexes.length;i++)
      {  
          Object currCDE = cdeList.get(i);
          tempList.add(Integer.parseInt(newIndexes[i]),currCDE);       
      }
    ListIterator tempIt = tempList.listIterator();
    while(tempIt.hasNext())
    {
      Object obj = tempIt.next();
      if(obj instanceof java.lang.String)
        tempIt.remove();
    }
    cdeList=tempList;
  } 
  
  
  
  public ListIterator listIterator()
  {
    return cdeList.listIterator();
  }


  public boolean getIsDetailCDEList()
  {
    return detailCDEList;
  }

  public void setDetailCDEList(List list)
  {
    cdeList=list;
    detailCDEList=true;
  }

  public List getCdeList()
  {
    return cdeList;
  }
}
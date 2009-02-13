package gov.nih.nci.ncicb.cadsr.contexttree;

public class TreeIdGenerator 
{
  private long id = 0;
  public TreeIdGenerator()
  {
  }
  public TreeIdGenerator(long initialVal)
  {
    id = initialVal;
  }  
  public String getNewId()
  {
    id++;
    return String.valueOf(id);
  }
}
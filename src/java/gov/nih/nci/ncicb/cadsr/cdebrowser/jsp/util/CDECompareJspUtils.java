package gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util;
import java.util.List;

public class CDECompareJspUtils 
{
  public CDECompareJspUtils()
  {
  }
  public static String getTotalPageWidth(Integer listSize)
  {
    Integer width = new Integer(20+listSize.intValue()*40);
    return width.toString();
  }
}
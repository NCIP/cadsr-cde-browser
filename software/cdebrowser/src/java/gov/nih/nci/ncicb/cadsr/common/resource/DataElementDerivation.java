package gov.nih.nci.ncicb.cadsr.common.resource;
import java.util.Collection;

public interface DataElementDerivation extends Audit
{
   public DataElement getDerivedDataElement();
   public void setDerivedDataElement(DataElement de);

   public int getDisplayOrder();
   public void setDisplayOrder(int displayOrder);

}
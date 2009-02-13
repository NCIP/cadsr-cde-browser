package gov.nih.nci.ncicb.cadsr.objectCart;

import java.sql.Timestamp;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.resource.AdminComponent;

public interface CDECartItem  {
  public String getId();
  public void setId(String id);

  public String getType();
  public void setType(String type);

  public Timestamp getCreatedDate();
  public String getCreatedBy();

  public void setCreatedBy(String user);

  /*public DataElement getDataElement();
  public void setDataElement(DataElement de);*/

  public AdminComponent getItem();
  public void setItem(AdminComponent ac);

  public boolean getDeletedInd();
  public void setDeletedInd(boolean ind);

  public boolean getPersistedInd();
  public void setPersistedInd(boolean ind);
  
}
package gov.nih.nci.ncicb.cadsr.resource;
import java.util.Collection;

public interface Instructionable {
  public Collection getInstructions();
  public void setInstructions(Collection instrcutions);
}
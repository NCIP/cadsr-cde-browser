package gov.nih.nci.ncicb.cadsr.resource.handler;

import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;

public interface DataElementConceptHandler extends HandlerDefinition{
  public Object findObjectForDE(DataElement de, Object sessionId) throws Exception;
}

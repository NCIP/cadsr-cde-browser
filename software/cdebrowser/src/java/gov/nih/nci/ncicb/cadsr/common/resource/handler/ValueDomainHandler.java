package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;

public interface ValueDomainHandler  {
  public Object findObject(Object key, Object sessionId) throws Exception;
}
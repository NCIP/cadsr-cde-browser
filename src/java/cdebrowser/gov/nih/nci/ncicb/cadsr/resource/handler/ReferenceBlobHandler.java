package gov.nih.nci.ncicb.cadsr.resource.handler;

import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;

public interface ReferenceBlobHandler extends HandlerDefinition{
  public Object findObjectForAdminComponent(Object adminComponentIdseq
                                            ,String docType
                                            ,Object sessionId) throws Exception;
}

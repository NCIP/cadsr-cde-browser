package gov.nih.nci.ncicb.cadsr.resource.handler;
import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;

public interface CDEBrowserPageContextHandler extends HandlerDefinition  {
  public Object findPageContext(String nodeType
                               ,String nodeIdseq
                               ,Object sessionId) throws Exception;
}
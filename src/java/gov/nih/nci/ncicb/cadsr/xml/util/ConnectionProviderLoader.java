package gov.nih.nci.ncicb.cadsr.xml.util;

import java.util.*;
import oracle.cle.util.xml.CLEDefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ConnectionProviderLoader extends CLEDefaultHandler {
  protected static final String ELEMENT_NAME = "elementname";
  protected static final String PROVIDER = "provider";
  protected static final String PROPERTY = "property";
  Stack elements;
  Stack propertyPairs;
  Hashtable connPropertiesTable;

  public ConnectionProviderLoader(){
    elements = new Stack();
    propertyPairs = new Stack();
    connPropertiesTable = new Hashtable(20);
    super.dtdName = "cle-providers.dtd";
  }

  public void startElement(String uri, String name, String qualifiedName, 
        Attributes attributes) throws SAXException{
    Hashtable attrTable = makeAttributeTable(name, attributes);
    elements.push(attrTable);
  }

  public void endElement(String namespaceURI, String name, 
    String qualifiedName) throws SAXException {
    Hashtable amapTable = (Hashtable)elements.pop();
    if(name.equals("provider")){
      String providerName = ((String[])amapTable.get("name"))[1];
      String className = ((String[])amapTable.get("class"))[1];
      Properties connectionProperties = new Properties();
      String propertyPair[] = null;
      for(; !propertyPairs.empty(); connectionProperties.put(propertyPair[0], propertyPair[1]))
          propertyPair = (String[])propertyPairs.pop();

      connectionProperties.put("connectionprovider", className);
      connPropertiesTable.put(providerName, connectionProperties);
    } else 
    if(name.equals("property")){
      String propertyName = ((String[])amapTable.get("name"))[1];
      String propertyValue = ((String[])amapTable.get("value"))[1];
      String propertyPair[] = new String[2];
      propertyPair[0] = propertyName;
      propertyPair[1] = propertyValue;
      propertyPairs.push(propertyPair);
    }
  }

  public Hashtable getPropertiesTable(){
    return connPropertiesTable;
  }

    
}

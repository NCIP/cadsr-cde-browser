package gov.nih.nci.ncicb.cadsr.common.database;

import java.util.Map;
import java.util.HashMap;

public class SqlQueryResult  {
  private Map columIndexMap = new HashMap(11);
  private Map columNameMap = new HashMap(11);
  
  public SqlQueryResult() {
  }

  public void setAttribute(int columnIndex, Object val) {
    columIndexMap.put(new Integer(columnIndex),val);
  }

  public void setAttribute(String columnName, Object val) {
    columIndexMap.put(columnName,val);
  }

  public Object getAttribute(int columnIndex) {
    return columIndexMap.get(new Integer(columnIndex));
  }

  public Object getAttribute(String columnName) {
    return columIndexMap.get(columnName);
  }
}
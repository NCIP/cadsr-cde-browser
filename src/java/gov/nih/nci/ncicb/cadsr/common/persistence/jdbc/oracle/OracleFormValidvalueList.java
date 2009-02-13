package gov.nih.nci.ncicb.cadsr.common.persistence.jdbc.oracle;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.jpub.runtime.MutableArray;

public class OracleFormValidvalueList implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SBREXT.FB_VALIDVALUELIST";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OracleFormValidvalueList _OracleFormValidvalueListFactory = new OracleFormValidvalueList();

  public static ORADataFactory getORADataFactory()
  { return _OracleFormValidvalueListFactory; }
  /* constructors */
  public OracleFormValidvalueList()
  {
    this((FbValidvalue[])null);
  }

  public OracleFormValidvalueList(FbValidvalue[] a)
  {
    _array = new MutableArray(2002, a, FbValidvalue.getORADataFactory());
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _array.toDatum(c, _SQL_NAME);
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    OracleFormValidvalueList a = new OracleFormValidvalueList();
    a._array = new MutableArray(2002, (ARRAY) d, FbValidvalue.getORADataFactory());
    return a;
  }

  public int length() throws SQLException
  {
    return _array.length();
  }

  public int getBaseType() throws SQLException
  {
    return _array.getBaseType();
  }

  public String getBaseTypeName() throws SQLException
  {
    return _array.getBaseTypeName();
  }

  public ArrayDescriptor getDescriptor() throws SQLException
  {
    return _array.getDescriptor();
  }

  /* array accessor methods */
  public FbValidvalue[] getArray() throws SQLException
  {
    return (FbValidvalue[]) _array.getObjectArray(
      new FbValidvalue[_array.length()]);
  }

  public FbValidvalue[] getArray(long index, int count) throws SQLException
  {
    return (FbValidvalue[]) _array.getObjectArray(index,
      new FbValidvalue[_array.sliceLength(index, count)]);
  }

  public void setArray(FbValidvalue[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(FbValidvalue[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public FbValidvalue getElement(long index) throws SQLException
  {
    return (FbValidvalue) _array.getObjectElement(index);
  }

  public void setElement(FbValidvalue a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}

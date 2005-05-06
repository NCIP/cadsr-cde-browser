package gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class FbValidvalueRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SBREXT.FB_VALIDVALUE";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final FbValidvalueRef _FbValidvalueRefFactory = new FbValidvalueRef();

  public static ORADataFactory getORADataFactory()
  { return _FbValidvalueRefFactory; }
  /* constructor */
  public FbValidvalueRef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    FbValidvalueRef r = new FbValidvalueRef();
    r._ref = (REF) d;
    return r;
  }

  public static FbValidvalueRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (FbValidvalueRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to FbValidvalueRef: "+exn.toString()); }
  }

  public FbValidvalue getValue() throws SQLException
  {
     return (FbValidvalue) FbValidvalue.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(FbValidvalue c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}

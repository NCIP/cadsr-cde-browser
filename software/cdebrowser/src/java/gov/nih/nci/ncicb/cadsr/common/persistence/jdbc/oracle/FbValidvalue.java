package gov.nih.nci.ncicb.cadsr.common.persistence.jdbc.oracle;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class FbValidvalue implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SBREXT.FB_VALIDVALUE";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 1,12,12,12,2,1,12,1,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[9];
  protected static final FbValidvalue _FbValidvalueFactory = new FbValidvalue();

  public static ORADataFactory getORADataFactory()
  { return _FbValidvalueFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[9], _sqlType, _factory); }
  public FbValidvalue()
  { _init_struct(true); }
  public FbValidvalue(String parentidseq, String longname, String preferredname, String preferreddefinition, java.math.BigDecimal version, String conteIdseq, String aslname, String vpidseq, String validvalueinstrlongname) throws SQLException
  { _init_struct(true);
    setParentidseq(parentidseq);
    setLongname(longname);
    setPreferredname(preferredname);
    setPreferreddefinition(preferreddefinition);
    setVersion(version);
    setConteIdseq(conteIdseq);
    setAslname(aslname);
    setVpidseq(vpidseq);
    setValidvalueinstrlongname(validvalueinstrlongname);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(FbValidvalue o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new FbValidvalue();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getParentidseq() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setParentidseq(String parentidseq) throws SQLException
  { _struct.setAttribute(0, parentidseq); }


  public String getLongname() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setLongname(String longname) throws SQLException
  { _struct.setAttribute(1, longname); }


  public String getPreferredname() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setPreferredname(String preferredname) throws SQLException
  { _struct.setAttribute(2, preferredname); }


  public String getPreferreddefinition() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setPreferreddefinition(String preferreddefinition) throws SQLException
  { _struct.setAttribute(3, preferreddefinition); }


  public java.math.BigDecimal getVersion() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setVersion(java.math.BigDecimal version) throws SQLException
  { _struct.setAttribute(4, version); }


  public String getConteIdseq() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setConteIdseq(String conteIdseq) throws SQLException
  { _struct.setAttribute(5, conteIdseq); }


  public String getAslname() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setAslname(String aslname) throws SQLException
  { _struct.setAttribute(6, aslname); }


  public String getVpidseq() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setVpidseq(String vpidseq) throws SQLException
  { _struct.setAttribute(7, vpidseq); }


  public String getValidvalueinstrlongname() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setValidvalueinstrlongname(String validvalueinstrlongname) throws SQLException
  { _struct.setAttribute(8, validvalueinstrlongname); }

}

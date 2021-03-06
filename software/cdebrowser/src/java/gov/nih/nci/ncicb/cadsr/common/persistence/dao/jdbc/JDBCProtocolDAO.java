/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ProtocolDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
import java.util.Iterator;


public class JDBCProtocolDAO extends JDBCBaseDAO implements ProtocolDAO {
  public JDBCProtocolDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets a protocol by the primary key.
   *
   * @return a protocol.
   */
  public Protocol getProtocolByPK(String idseq) {
      ProtocolQuery query = new ProtocolQuery();
      query.setDataSource(getDataSource());
      query.setSql();
      List protocols = query.execute(idseq);
      if (protocols==null || protocols.isEmpty()){
          return null;
      }else{
        return (Protocol)protocols.get(0);
      }  
  }
  /**
   * Inner class that accesses database to get a protocol by primary key
   *
   */
    class ProtocolQuery extends MappingSqlQuery {
    
    String sql = " SELECT p.proto_idseq, p.version, p.conte_idseq, p.preferred_name, p.preferred_definition, p.asl_name, p.long_name, p.LATEST_VERSION_IND, p.begin_date, p.END_DATE, p.PROTOCOL_ID, p.TYPE, p.PHASE, p.LEAD_ORG, p.origin, p.PROTO_ID, c.name contextname " + 
                 " from sbrext.protocols_view_ext p, sbr.contexts_view c where p.PROTO_IDSEQ=? and  p.CONTE_IDSEQ = c.CONTE_IDSEQ";

    
    ProtocolQuery(){
      super();
    }

    public void setSql(){
      super.setSql(sql);
      declareParameter(new SqlParameter("PROTO_IDSEQ", Types.VARCHAR));      
      compile();
    }
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
        Protocol protocol = new ProtocolTransferObject();
        protocol.setProtoIdseq(rs.getString(1));
        protocol.setVersion(rs.getFloat(2)); 
        protocol.setConteIdseq(rs.getString(3)); 
        protocol.setPreferredName(rs.getString(4));
        protocol.setPreferredDefinition(rs.getString(5));
        protocol.setAslName(rs.getString(6));
        protocol.setLongName(rs.getString(7));
        protocol.setLatestVersionInd(rs.getString(8));
        protocol.setBeginDate(rs.getDate(9));
        protocol.setEndDate(rs.getDate(10));
        protocol.setProtocolId(rs.getString(11));
        protocol.setType(rs.getString(12));
        protocol.setPhase(rs.getString(13));
        protocol.setLeadOrg(rs.getString(14));
        protocol.setOrigin(rs.getString(15));
        Float publicId = rs.getFloat(16);
        protocol.setPublicId(publicId.intValue());
        String contextName = rs.getString(17);
        Context context = new ContextTransferObject();
        context.setConteIdseq(rs.getString(3));
        context.setName(contextName);
        protocol.setContext(context);
      return protocol;
    }
  }


}

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, gov.nih.nci.ncicb.cadsr.common.persistence.hibernate.*, org.codehaus.jettison.json.*" %>
    
 <%!
 	private static InstantSearchDAO dao = InstantSearchDAO.getInstance();
  %>
  
<%
	String searchStr = request.getParameter("searchStr");
	
	if (searchStr != null) {
		List deNames = dao.getMatchedDEs(searchStr);
		JSONObject container = new JSONObject();
		JSONArray jArray = new JSONArray();
		List<String> deLongNames = new ArrayList<String>();
		
		for (int i=0;i<deNames.size();i++) {
			DENames deName = (DENames)deNames.get(i);
			if (!deLongNames.contains(deName.getLongName())) { //return only unique names
				deLongNames.add(deName.getLongName());
				JSONObject row = new JSONObject();
				row.put("searchStr", searchStr);
				row.put("longName", deName.getLongName());
				row.put("prettyLongName", "<font face=\"Arial, Helvetica, sans-serif\" size=\"2\">"+deName.getLongName()+"</font>");
				row.put("publicId", deName.getPublicId());
				
				jArray.put(row);
				container.put("items", jArray);
			}
		}
		
		out.println(container.toString());
		out.flush();
	}
	
 %>

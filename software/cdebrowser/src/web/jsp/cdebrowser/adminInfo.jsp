<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="/jsp/cdebrowser/cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  List deContacts = (List)infoBean.getInfo("deContacts");
  List decContacts = (List)infoBean.getInfo("decContacts");
  List ocContacts = (List)infoBean.getInfo("ocContacts");
  List propContacts = (List)infoBean.getInfo("propContacts");
  Map csContacts = (Map)infoBean.getInfo("csContacts");
  List ctxContacts = (List)infoBean.getInfo("ctxContacts");
  List vdContacts = (List)infoBean.getInfo("vdContacts");
  List repTermContacts = (List)infoBean.getInfo("repTermContacts");
  List vmContacts = (List)infoBean.getInfo("vmContacts");
  List conContacts = (List)infoBean.getInfo("conContacts");
  String pageId = StringEscapeUtils.escapeJavaScript(infoBean.getPageId());
  String pageName = StringEscapeUtils.escapeJavaScript(PageConstants.PAGEID);
  String pageUrl = "&"+StringEscapeUtils.escapeJavaScript(pageName+"="+pageId);
  CDEBrowserParams params = CDEBrowserParams.getInstance();    
%>

<%! 

	public void printContacts(javax.servlet.jsp.JspWriter out, String name, List contacts) {
		try {
			out.println("<br/>");
			out.println("<table cellpadding=\"0\" cellspacing=\"0\" width=\"80%\" align=\"center\">");
			out.println("<tr>");
			out.println("<td class=\"OraHeaderSubSub\" width=\"100%\">"+name+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=\"100%\"><img height=1 src=\"i/beigedot.gif\"  alt=\"beigedot\" width=\"99%\" align=top border=0> </td>");
			out.println("</tr>");
			out.println("</table>");

			if (contacts != null && contacts.size() > 0) {
				
				for (int i=0;i<contacts.size();i++) {
					Contact contact = (Contact)contacts.get(i);
					Person person = contact.getPerson();
					Organization org = contact.getOrganization();
					
					if (i > 0) out.println("<br/>");
					
					if (person != null && person.getId() != null) {
						out.println("<table width=\"80%\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"OraBGAccentVeryDark\">");
						out.println("<tr class=\"OraTabledata\">");
						out.println("<td class=\"TableRowPromptText\" width=\"20%\">Name</td>");
						out.print("<td class=\"OraFieldText\">");
						out.print(getDisplayString(getDisplayString(person.getFirstName())+" "+getDisplayString(person.getLastName())));
						out.println("</td>");
						out.println("</tr>");
						out.println("<tr class=\"OraTabledata\">");
						out.println("<td class=\"TableRowPromptText\" width=\"20%\">Title</td>");
						out.print("<td class=\"OraFieldText\">");
						out.print(getDisplayString(person.getPosition()));
						out.println("</td>");
						out.println("</tr>");
						printContactComms(out, person.getContactCommunications());
						out.println("</table>");
					}
					if (org != null && org.getId() != null) {
						out.println("<table width=\"80%\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"OraBGAccentVeryDark\">");
						out.println("<tr class=\"OraTabledata\">");
						out.println("<td class=\"TableRowPromptText\" width=\"20%\">Organization Name</td>");
						out.print("<td class=\"OraFieldText\">");
						out.print(getDisplayString(org.getName()));
						out.println("</td>");
						out.println("</tr>");
						printAddresses(out, org.getAddresses());
						printContactComms(out, org.getContactCommunications());
						out.println("</table>");
					}
				}
			}
			else {
				out.println("<table width=\"80%\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"OraBGAccentVeryDark\">");
				out.println("<tr class=\"OraTabledata\">");
				out.println("<td class=\"OraFieldText\" width=\"1000%\">No Contact Information</td>");
				out.println("</tr>");
				out.println("</table>");
			}
			
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void printCSContacts(javax.servlet.jsp.JspWriter out, String name, Map contacts) {
		try {
			out.println("<br/>");
			out.println("<table cellpadding=\"0\" cellspacing=\"0\" width=\"80%\" align=\"center\">");
			out.println("<tr>");
			out.println("<td class=\"OraHeaderSubSub\" width=\"100%\">"+name+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=\"100%\"><img height=1 src=\"i/beigedot.gif\" alt=\"beigedot\" width=\"99%\" align=top border=0> </td>");
			out.println("</tr>");
			out.println("<tr><td>");
			
			if (contacts != null) {
				Iterator contactsIter = contacts.keySet().iterator();
				while (contactsIter.hasNext()) {
					Classification cs = (Classification)contactsIter.next();
					printContacts(out, cs.getClassSchemeName(), (List)contacts.get(cs));
				}
			}
			else {
				out.println("<table width=\"100%\" cellpadding=\"1\" cellspacing=\"1\" class=\"OraBGAccentVeryDark\">");
				out.println("<tr class=\"OraTabledata\">");
				out.println("<td class=\"OraFieldText\" width=\"1000%\">No Contact Information</td>");
				out.println("</tr>");
				out.println("</table>");
			}
			out.println("</td></tr>");
			out.println("</table>");
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void printContactComms(javax.servlet.jsp.JspWriter out, Collection comms) throws java.io.IOException {
		if (comms != null) {
			Iterator commsIter = comms.iterator();
			while (commsIter.hasNext()) {
				ContactCommunication comm = (ContactCommunication)commsIter.next();
				out.println("<tr class=\"OraTabledata\">");
				out.println("<td class=\"TableRowPromptText\" width=\"20%\">"+getDisplayString(comm.getType())+"</td>");
				out.print("<td class=\"OraFieldText\">"+getDisplayString(comm.getValue())+"</td>");
				out.println("</tr>");
			}
		}
	}
	
	private void printAddresses(javax.servlet.jsp.JspWriter out, Collection addrs) throws java.io.IOException {
		if (addrs!=null && addrs.size()>0) {
			Iterator orgAddIter = addrs.iterator();
			while (orgAddIter.hasNext()) {
				Address orgAddr = (Address)orgAddIter.next();
				out.println("<tr class=\"OraTabledata\">");
				out.println("<td class=\"TableRowPromptText\" width=\"20%\">Organization Address</td>");
				out.print("<td class=\"OraFieldText\">");
				printAddress(out, orgAddr);
				out.println("</td></tr>");
			}
		}
		else {
			out.println("<tr class=\"OraTabledata\">");
			out.println("<td class=\"TableRowPromptText\" width=\"20%\">Organization Address</td>");
			out.print("<td class=\"OraFieldText\">&nbsp;</td>");
			out.println("</tr>");
		}
	}
	
	private void printAddress(javax.servlet.jsp.JspWriter out, Address addr) throws java.io.IOException{
		String line1 = getDisplayString(addr.getAddressLine1());
		String line2 = getDisplayString(addr.getAddressLine2());
		String city = getDisplayString(addr.getCity());
		String state = getDisplayString(addr.getState());
		String postalCode = getDisplayString(addr.getPostalCode());
		String country = getDisplayString(addr.getCountry());
		
		if(!line1.equals("")) {
			out.println(line1+"<br/>");
		}
		
		if(!line2.equals("")) {
			out.println(line2+"<br/>");
		}
		
		if(!city.equals("")) {
			out.println(city+",");
		}
		
		if(!state.equals("")) {
			out.println(state+" ");
		}
		
		if(!postalCode.equals("")) {
			out.println(postalCode+"<br/>");
		}
		
		if(!country.equals("")) {
			out.println(country);
		}
	}
	
	private String getDisplayString(String str) {
		if (str == null || str.trim().equals("") || str.trim().equals("null") ) {
			return "";
		}
		return str;
	}

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Admin Info
</TITLE>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="/js/checkbox.js"></SCRIPT>
</HEAD>
<BODY topmargin="0">

<%@ include  file="cdebrowserCommon_html/tab_include.html" %>

<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= StringEscapeUtils.escapeJavaScript(infoBean.getPageId())%>"/>

<% printContacts(out, "Data Element Contacts", deContacts); %>
<% printContacts(out, "Data Element Concept Contacts", decContacts); %>
<% printContacts(out, "Object Class Contacts", ocContacts); %>
<% printContacts(out, "Property Contacts", propContacts); %>
<% printContacts(out, "Value Domain Contacts", vdContacts); %>
<% printContacts(out, "Representation Term Contacts", repTermContacts); %>
<% printContacts(out, "Value Meaning Contacts", vmContacts); %>
<% printContacts(out, "Concepts Contacts", conContacts); %>
<% printCSContacts(out, "Classification Scheme Contacts", csContacts); %>
<% printContacts(out, "Context Contacts", ctxContacts); %>


</form>

<%@ include file="/jsp/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
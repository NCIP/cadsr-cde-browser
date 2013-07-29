<%--L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>

<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.util.OCUtils"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.ocbrowser.util.ObjectExtractor"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.*"%>
<%@ page import="gov.nih.nci.cadsr.domain.ObjectClass"%>
<%@ page import="java.util.Collection"%>



<%@ page contentType="text/html;charset=windows-1252"%>

<html>
	<head>
		<TITLE>Object Class Details</TITLE>
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
		<LINK rel="stylesheet" TYPE="text/css"
			HREF="<html:rewrite page='/css/blaf.css' />">
		<SCRIPT LANGUAGE="JavaScript1.1"
			SRC="<%=request.getContextPath()%>/js/checkbox.js"></SCRIPT>
		<SCRIPT LANGUAGE="JavaScript1.1"
			SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
	</head>

	<body topmargin="0">
		<SCRIPT LANGUAGE="JavaScript">
<!--
function navigateOCR(ocId,ocrIndex,direction) {
  document.forms[0].<%=OCBrowserNavigationConstants.METHOD_PARAM%>.value="<%=OCBrowserNavigationConstants.NAVIGATE_OCR%>";     
  document.forms[0].<%=OCBrowserFormConstants.OC_IDSEQ%>.value=ocId;
  document.forms[0].<%=OCBrowserFormConstants.OCR_DIRECTION%>.value=direction;
  document.forms[0].<%=OCBrowserFormConstants.OCR_INDEX%>.value=ocrIndex;
  document.forms[0].target="_parent";
  document.forms[0].submit();
}
//-->
</SCRIPT>

		<% String contextPath = request.getContextPath();
  		   String clearurl = contextPath+"/ocbrowser/clearNavigationPathAction.do?"+OCBrowserNavigationConstants.METHOD_PARAM+"="+OCBrowserNavigationConstants.CLEAR_NAVIGATION_PATH;
  		   CDEBrowserParams params = CDEBrowserParams.getInstance();
%>

		<html:form action="/ocbrowser/navigateOCRAction.do">

			<html:hidden property="<%=OCBrowserFormConstants.OC_IDSEQ%>" />
			<html:hidden property="<%=OCBrowserFormConstants.OCR_DIRECTION%>" />
			<html:hidden property="<%=OCBrowserFormConstants.OCR_INDEX%>" />
			<html:hidden
				property="<%=OCBrowserFormConstants.OCR_BR_CRUMBS_INDEX%>" />
			<html:hidden value=""
				property="<%=OCBrowserNavigationConstants.METHOD_PARAM%>" />

			<%@ include file="/jsp/ocbrowser/common_header_inc.jsp"%>


			<jsp:include page="/jsp/ocbrowser/mltitab_inc.jsp" flush="true">
				<jsp:param name="label" value="Associations" />
			</jsp:include>

			<table cellpadding="0" cellspacing="0" width="100%" align="center"
				border=0>
				<tr valign="top">
					<td valign="top" align="left" width="100%" class="AbbreviatedText">
						All Associations displayed below are for Object Class "
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="longName" />
						". Details of this class can be seen under "Object Class" tab.
						Outgoing associations have "
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="longName" />
						" as the source Object Class; Incoming associations have "
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="longName" />
						" as the target Object Class; Bidirectional associations have "
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="longName" />
						" as target or source Object Class.
					</td>
				</tr>
			</table>

			<table valign="top" width="90%" align="center" cellpadding="4"
				cellspacing="1" class="OraBGAccentVeryDark">
				<tr class="OraTabledata">
					<td class="TableRowPromptText" width="20%">
						Public ID:
					</td>
					<td class="OraFieldText">
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="publicID" />
					</td>
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptText" width="20%">
						Long Name:
					</td>
					<td class="OraFieldText">
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="longName" />
					</td>
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptText" width="20%">
						Short Name:
					</td>
					<td class="OraFieldText">
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="preferredName" />
					</td>
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptText" width="20%">
						Context:
					</td>
					<td class="OraFieldText">
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="context.name" />
					</td>
				</tr>
				<tr class="OraTabledata">
					<td class="TableRowPromptText" width="20%">
						Version:
					</td>
					<td class="OraFieldText">
						<bean:write name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
							property="version" />
					</td>
				</tr>
			</table>
			<br>
			<table vAlign=top cellSpacing=1 cellPadding=1 width="90%"
				align=center border=0 class="OraBGAccentVeryDark">
				<TR class=OraTableColumnHeader>
					<th class="OraTableColumnHeader">
						Alternate Names
					</th>
					<th class="OraTableColumnHeader">
						Type
					</th>
					<th class="OraTableColumnHeader">
						Context
					</th>
				</TR>
				<logic:notEmpty name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
					property="designationCollection">
					<logic:iterate id="alternateName"
						name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
						property="designationCollection"
						type="gov.nih.nci.cadsr.domain.Designation" indexId="nameIndex">
						<TR class=OraTabledata>
							<td class="OraFieldText">
								<bean:write name="alternateName" property="name" />
							</td>
							<td class="OraFieldText">
								<bean:write name="alternateName" property="type" />
							</td>
							<td class="OraFieldText">
								<bean:write name="alternateName" property="context.name" />
							</td>
						</TR>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="<%=OCBrowserFormConstants.OBJECT_CLASS%>"
					property="designationCollection">
					<TR class=OraTabledata>
						<td colspan=3 class="OraFieldText">
							No Alternate names for this Object Class exist
						</td>
					</TR>
				</logic:empty>
			</table>
			<br>

			<cde:ocrNavigation
				navigationListId="<%=OCBrowserFormConstants.OCR_NAVIGATION_BEAN%>"
				outGoingImage="/i/outgoing.gif" inCommingImage="/i/incomming.gif"
				biDirectionalImage="/i/bidirectional.gif" scope="session" />

			<table cellpadding="0" cellspacing="0" width="90%" align="center"
				border=0>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<a class="link" href="#outgoing">Outgoing Associations</a>
					</td>
					<td>
						<a class="link" href="#incoming">Incoming Associations</a>
					</td>
					<td>
						<a class="link" href="#bidirectionl">Bidirectional Associations</a>
					</td>
					<logic:present
						name="<%=OCBrowserFormConstants.OCR_NAVIGATION_BEAN%>">
						<bean:size id="size"
							name="<%=OCBrowserFormConstants.OCR_NAVIGATION_BEAN%>" />
						<logic:greaterThan value="1" name="size">
							<td>
								<a href="<%=clearurl%>">Clear navigation path</a>
							</td>
						</logic:greaterThan>
					</logic:present>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>


			<!-- Out going start -->
			<A NAME="outgoing"></A>
			<table cellpadding="0" cellspacing="0" width="100%" align="center">
				<tr>
					<td class="OraHeaderSubSub" width="100%">
						Outgoing Associations
					</td>
				</tr>
				<tr>
					<td width="100%">
						<img height=1 src="<%=contextPath%>/i/beigedot.gif" alt="beigodot" width="99%"
							align=top border=0>
					</td>
				</tr>
			</table>
			<logic:notEmpty name="<%=OCBrowserFormConstants.OUT_GOING_OCRS%>"
				scope="session">
				<logic:iterate id="currOutgoingOCR"
					name="<%=OCBrowserFormConstants.OUT_GOING_OCRS%>"
					type="gov.nih.nci.cadsr.domain.ObjectClassRelationship"
					indexId="ocrIndex">
					<%
           Collection projects = ObjectExtractor.getProjects(currOutgoingOCR);
           pageContext.setAttribute("projects",projects);                  
        %>

					<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
						align=center border=0 class="OraBGAccentVeryDark">
						<tr class=OraTabledata>
							<td class=OraFieldText>
								<table vAlign=top width="100%">
									<tr class=OraTabledata align="left">
										<% if(OCUtils.isNavigationAllowed(request,OCBrowserFormConstants.OCR_NAVIGATION_BEAN,currOutgoingOCR))
                 {
                 %>
										<td class=OraFieldText colspan="2">
											<a
												href="javascript:navigateOCR('<%=currOutgoingOCR.getTargetObjectClass().getId()%>','<%=ocrIndex%>','<%=OCBrowserFormConstants.OUT_GOING_OCRS%>')">Navigate
												to this association</a>
										</td>
										<%}else{%>
										<td class=OraFieldText colspan="2">
											&nbsp;
										</td>
										<% }%>
									</tr>
									<tr class=OraTabledata align="center">
										<td class=OraFieldText colspan="2" align="center">
											<cde:DefineNavigationCrumbs
												beanId="<%=OCBrowserFormConstants.OUT_GOING_OCRS+ocrIndex%>"
												direction="<%=OCBrowserFormConstants.OUT_GOING_OCRS%>"
												ocrId="currOutgoingOCR" />
											<cde:ocrNavigation
												navigationListId="<%=OCBrowserFormConstants.OUT_GOING_OCRS+ocrIndex%>"
												outGoingImage="/i/outgoing.gif"
												inCommingImage="/i/incomming.gif"
												biDirectionalImage="/i/bidirectional.gif" scope="page"
												activeNodes="false" />
										</td>
									</tr>


									<TR>
										<td width="100%">
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=left border=0 class="OraBGAccentVeryDark">
												<TR class=OraTabledata>
													<td class="TableRowPromptTextLeft" width="20%">
														Target Object Class
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR"
															property="targetObjectClass.longName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Relationship Type
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR" property="name" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Long Name
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR" property="longName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Preferred Definition
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR"
															property="preferredDefinition" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Workflow Status
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR"
															property="workflowStatusName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Version
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR" property="version" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Context
													</td>
													<td class="OraFieldText">
														<bean:write name="currOutgoingOCR" property="context.name" />
													</td>
												</tr>
											</table>
										</td>

									</TR>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														Alternate Names for Object Class:
														<bean:write name="currOutgoingOCR"
															property="targetObjectClass.longName" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=center border=0 class="OraBGAccentVeryDark">
												<TR class=OraTableColumnHeader>
													<th class="OraTableColumnHeader">
														Alternate Names
													</th>
													<th class="OraTableColumnHeader">
														Type
													</th>
													<th class="OraTableColumnHeader">
														Context
													</th>
												</TR>
												<logic:notEmpty name="currOutgoingOCR"
													property="targetObjectClass.designationCollection">
													<logic:iterate id="alternateName" name="currOutgoingOCR"
														property="targetObjectClass.designationCollection"
														type="gov.nih.nci.cadsr.domain.Designation"
														indexId="nameIndex">
														<TR class=OraTabledata>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="name" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="type" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="context.name" />
															</td>
														</TR>
													</logic:iterate>
												</logic:notEmpty>
												<logic:empty name="currOutgoingOCR"
													property="targetObjectClass.designationCollection">
													<TR class=OraTabledata>
														<td colspan=3 class="OraFieldText">
															No Alternate names for this Object Class exist
														</td>
													</TR>
												</logic:empty>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="OraHeaderSubSubSub" vAlign=bottom align=left
											colspan=2 width="100%">
											Using Projects
										</td>
									</tr>
									<tr>
										<td vAlign=top colspan=2 width="100%">
											<logic:notEmpty name="projects">

												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															<logic:iterate id="currProject" name="projects"
																type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																indexId="prIndex">
																<UL>
																	<li class="OraFieldText">
																		<bean:write name="currProject" property="name" />
																		(Project)
																	</li>
																	<logic:notEmpty name="currProject" property="children">
																		<logic:iterate id="currSubProject" name="currProject"
																			property="children"
																			type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																			indexId="subIndex">
																			<ul>
																				<li class="OraFieldText">
																					<bean:write name="currSubProject" property="name" />
																					(SubProject)
																				</li>
																				<logic:notEmpty name="currSubProject"
																					property="packages">
																					<ul>
																						<logic:iterate id="currPackage"
																							name="currSubProject" property="packages"
																							type="gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage"
																							indexId="pkIndex">
																							<li class="OraFieldText">
																								<bean:write name="currPackage" property="name" />
																								(Package)
																							</li>
																						</logic:iterate>
																					</ul>
																				</logic:notEmpty>
																			</ul>
																		</logic:iterate>
																	</logic:notEmpty>
																</UL>
															</logic:iterate>
														</td>
													</tr>
												</table>
											</logic:notEmpty>
											<logic:empty name="projects">
												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															Currently not used by any Projects
														</td>
													</TR>
												</table>
											</logic:empty>
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
					<br>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="<%=OCBrowserFormConstants.OUT_GOING_OCRS%>"
				scope="session">
				<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
					align=center border=0 class="OraBGAccentVeryDark">
					<TR class=OraTabledata>
						<td colspan=2 class="OraFieldText">
							No outgoing associations exist for this Object Class
						</td>
					</TR>
				</table>
			</logic:empty>
			<!-- Out going end -->

			<br>
			<!-- Incoming Start -->
			<A NAME="incoming"></A>

			<table cellpadding="0" cellspacing="0" width="100%" align="center">
				<tr>
					<td class="OraHeaderSubSub" width="100%">
						Incoming Associations
					</td>
				</tr>
				<tr>
					<td width="100%">
						<img height=1 src="<%=contextPath%>/i/beigedot.gif" width="99%" alt="beigodot"
							align=top border=0>
					</td>
				</tr>
			</table>
			<logic:notEmpty name="<%=OCBrowserFormConstants.IN_COMMING_OCRS%>"
				scope="session">
				<logic:iterate id="currIncommingOCR"
					name="<%=OCBrowserFormConstants.IN_COMMING_OCRS%>"
					type="gov.nih.nci.cadsr.domain.ObjectClassRelationship"
					indexId="ocrIndex">
					<%
               Collection projects = ObjectExtractor.getProjects(currIncommingOCR);     
               pageContext.setAttribute("projects",projects);
            %>

					<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
						align=center border=0 class="OraBGAccentVeryDark">
						<tr class=OraTabledata>
							<td class=OraFieldText>
								<table vAlign=top width="100%">
									<tr class=OraTabledata align="left">
										<% if(OCUtils.isNavigationAllowed(request,OCBrowserFormConstants.OCR_NAVIGATION_BEAN,currIncommingOCR))
                       {
                       %>
										<td class=OraFieldText colspan="2">
											<a
												href="javascript:navigateOCR('<%=currIncommingOCR.getSourceObjectClass().getId()%>','<%=ocrIndex%>','<%=OCBrowserFormConstants.IN_COMMING_OCRS%>')">Navigate
												to this association</a>
										</td>
										<%}else{%>
										<td class=OraFieldText colspan="2">
											&nbsp;
										</td>
										<% }%>
									</tr>
									<tr class=OraTabledata align="center">
										<td class=OraFieldText colspan="2" align="center">
											<cde:DefineNavigationCrumbs
												beanId="<%=OCBrowserFormConstants.IN_COMMING_OCRS+ocrIndex%>"
												direction="<%=OCBrowserFormConstants.IN_COMMING_OCRS%>"
												ocrId="currIncommingOCR" />
											<cde:ocrNavigation
												navigationListId="<%=OCBrowserFormConstants.IN_COMMING_OCRS+ocrIndex%>"
												outGoingImage="/i/outgoing.gif"
												inCommingImage="/i/incomming.gif"
												biDirectionalImage="/i/bidirectional.gif" scope="page"
												activeNodes="false" />
										</td>
									</tr>
									<TR>
										<td width="100%">
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=left border=0 class="OraBGAccentVeryDark">
												<TR class=OraTabledata>
													<td class="TableRowPromptTextLeft" width="20%">
														Source Object Class
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR"
															property="sourceObjectClass.longName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Relationship Type
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR" property="name" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Long Name
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR" property="longName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Preferred Definition
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR"
															property="preferredDefinition" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Workflow Status
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR"
															property="workflowStatusName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Version
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR" property="version" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Context
													</td>
													<td class="OraFieldText">
														<bean:write name="currIncommingOCR"
															property="context.name" />
													</td>
												</tr>
											</table>
										</td>

									</TR>

									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														Alternate Names for Object Class:
														<bean:write name="currIncommingOCR"
															property="sourceObjectClass.longName" />
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td colspan=2>
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=center border=0 class="OraBGAccentVeryDark">
												<TR class=OraTableColumnHeader>
													<th class="OraTableColumnHeader">
														Alternate Names
													</th>
													<th class="OraTableColumnHeader">
														Type
													</th>
													<th class="OraTableColumnHeader">
														Context
													</th>
												</TR>
												<logic:notEmpty name="currIncommingOCR"
													property="sourceObjectClass.designationCollection">
													<logic:iterate id="alternateName" name="currIncommingOCR"
														property="sourceObjectClass.designationCollection"
														type="gov.nih.nci.cadsr.domain.Designation"
														indexId="nameIndex">
														<TR class=OraTabledata>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="name" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="type" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="context.name" />
															</td>
														</TR>
													</logic:iterate>
												</logic:notEmpty>
												<logic:empty name="currIncommingOCR"
													property="sourceObjectClass.designationCollection">
													<TR class=OraTabledata>
														<td colspan=3 class="OraFieldText">
															No Alternate names for this Object Class exist
														</td>
													</TR>
												</logic:empty>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="OraHeaderSubSubSub" vAlign=bottom align=left
											colspan=2 width="100%">
											Using Projects
										</td>
									</tr>
									<tr>
										<td vAlign=top colspan=2 width="100%">
											<logic:notEmpty name="projects">

												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															<logic:iterate id="currProject" name="projects"
																type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																indexId="prIndex">
																<UL>
																	<li class="OraFieldText">
																		<bean:write name="currProject" property="name" />
																		(Project)
																	</li>
																	<logic:notEmpty name="currProject" property="children">
																		<logic:iterate id="currSubProject" name="currProject"
																			property="children"
																			type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																			indexId="subIndex">
																			<ul>
																				<li class="OraFieldText">
																					<bean:write name="currSubProject" property="name" />
																					(SubProject)
																				</li>
																				<logic:notEmpty name="currSubProject"
																					property="packages">
																					<ul>
																						<logic:iterate id="currPackage"
																							name="currSubProject" property="packages"
																							type="gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage"
																							indexId="pkIndex">
																							<li class="OraFieldText">
																								<bean:write name="currPackage" property="name" />
																								(Package)
																							</li>
																						</logic:iterate>
																					</ul>
																				</logic:notEmpty>
																			</ul>
																		</logic:iterate>
																	</logic:notEmpty>
																</UL>
															</logic:iterate>
														</td>
													</tr>
												</table>
											</logic:notEmpty>
											<logic:empty name="projects">
												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															Currently not used by any Projects
														</td>
													</TR>
												</table>
											</logic:empty>
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
					<br>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="<%=OCBrowserFormConstants.IN_COMMING_OCRS%>"
				scope="session">
				<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
					align=center border=0 class="OraBGAccentVeryDark">
					<TR class=OraTabledata>
						<td colspan=2 class="OraFieldText">
							No incoming associations exist for this Object Class
						</td>
					</TR>
				</table>
			</logic:empty>
			<!--  Ingoing end -->

			<br>
			<A NAME="bidirectionl"></A>

			<table cellpadding="0" cellspacing="0" width="100%" align="center">
				<tr>
					<td class="OraHeaderSubSub" width="100%">
						Bidirectional Associations
					</td>
				</tr>
				<tr>
					<td width="100%">
						<img height=1 src="<%=contextPath%>/i/beigedot.gif" width="99%" alt="beigodot"
							align=top border=0>
					</td>
				</tr>
			</table>
			<%
        ObjectClass currObjClass = (ObjectClass)request.getSession().getAttribute(OCBrowserFormConstants.OBJECT_CLASS);
      %>
			<logic:notEmpty name="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS%>"
				scope="session">
				<logic:iterate id="currBidirectionalOCR"
					name="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS%>"
					type="gov.nih.nci.cadsr.domain.ObjectClassRelationship"
					indexId="ocrIndex">
					<%
               Collection projects = ObjectExtractor.getProjects(currBidirectionalOCR);   
               pageContext.setAttribute("projects",projects);
            %>
					<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
						align=center border=0 class="OraBGAccentVeryDark">
						<tr class=OraTabledata>
							<td class=OraFieldText>
								<table vAlign=top width="100%">
									<tr class=OraTabledata align="left">

										<% if(OCUtils.isNavigationAllowed(request,OCBrowserFormConstants.OCR_NAVIGATION_BEAN,currBidirectionalOCR))
                         {
                         %>
										<td class=OraFieldText colspan="2">
											<a
												href="javascript:navigateOCR('<%=OCUtils.getBiderectionalTarget(currBidirectionalOCR,currObjClass).getId()%>','<%=ocrIndex%>','<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS%>')">Navigate
												to this association</a>
										</td>
										<%}else{%>
										<td class=OraFieldText colspan="2">
											&nbsp;
										</td>
										<% }%>
									</tr>
									<tr class=OraTabledata align="center">
										<td class=OraFieldText colspan="2" align="center">
											<cde:DefineNavigationCrumbs
												beanId="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS+ocrIndex%>"
												direction="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS%>"
												ocrId="currBidirectionalOCR" />
											<cde:ocrNavigation
												navigationListId="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS+ocrIndex%>"
												outGoingImage="/i/outgoing.gif"
												inCommingImage="/i/incomming.gif"
												biDirectionalImage="/i/bidirectional.gif" scope="page"
												activeNodes="false" />
										</td>
									</tr>
									<TR>
										<td width="100%">
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=left border=0 class="OraBGAccentVeryDark">
												<TR class=OraTabledata>
													<% ObjectClass biTargetOC = OCUtils.getBiderectionalTarget(currBidirectionalOCR,currObjClass);
                             pageContext.setAttribute("biTargetOC",biTargetOC); %>
													<td class="TableRowPromptTextLeft" width="20%">
														Associated Object Class
													</td>
													<td class="OraFieldText">

														<%=biTargetOC.getLongName()%>
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Relationship Type
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR" property="name" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Long Name
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR"
															property="longName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Preferred Definition
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR"
															property="preferredDefinition" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Workflow Status
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR"
															property="workflowStatusName" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Version
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR" property="version" />
													</td>
												</tr>
												<TR class=OraTabledata>
													<td class="OraTableColumnHeader" width="20%">
														Context
													</td>
													<td class="OraFieldText">
														<bean:write name="currBidirectionalOCR"
															property="context.name" />
													</td>
												</tr>
											</table>
										</td>

									</TR>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														Alternate Names for Object Class:
														<bean:write name="biTargetOC" property="longName" />
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td colspan=2>
											<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
												align=center border=0 class="OraBGAccentVeryDark">
												<TR class=OraTableColumnHeader>
													<th class="OraTableColumnHeader">
														Alternate Names
													</th>
													<th class="OraTableColumnHeader">
														Type
													</th>
													<th class="OraTableColumnHeader">
														Context
													</th>
												</TR>
												<logic:notEmpty name="biTargetOC"
													property="designationCollection">
													<logic:iterate id="alternateName" name="biTargetOC"
														property="designationCollection"
														type="gov.nih.nci.cadsr.domain.Designation"
														indexId="nameIndex">
														<TR class=OraTabledata>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="name" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="type" />
															</td>
															<td class="OraFieldText">
																<bean:write name="alternateName" property="context.name" />
															</td>
														</TR>
													</logic:iterate>
												</logic:notEmpty>
												<logic:empty name="biTargetOC"
													property="designationCollection">
													<TR class=OraTabledata>
														<td colspan=3 class="OraFieldText">
															No Alternate names for this Object Class exist
														</td>
													</TR>
												</logic:empty>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<table cellpadding="0" cellspacing="0" width="100%"
												align="center">
												<tr>
													<td class="OraHeaderSubSubSub" width="100%">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="OraHeaderSubSubSub" vAlign=bottom align=left
											colspan=2 width="100%">
											Using Projects
										</td>
									</tr>
									<tr>
										<td vAlign=top colspan=2 width="100%">
											<logic:notEmpty name="projects">

												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															<logic:iterate id="currProject" name="projects"
																type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																indexId="prIndex">
																<UL>
																	<li class="OraFieldText">
																		<bean:write name="currProject" property="name" />
																		(Project)
																	</li>
																	<logic:notEmpty name="currProject" property="children">
																		<logic:iterate id="currSubProject" name="currProject"
																			property="children"
																			type="gov.nih.nci.ncicb.cadsr.common.resource.Project"
																			indexId="subIndex">
																			<ul>
																				<li class="OraFieldText">
																					<bean:write name="currSubProject" property="name" />
																					(SubProject)
																				</li>
																				<logic:notEmpty name="currSubProject"
																					property="packages">
																					<ul>
																						<logic:iterate id="currPackage"
																							name="currSubProject" property="packages"
																							type="gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage"
																							indexId="pkIndex">
																							<li class="OraFieldText">
																								<bean:write name="currPackage" property="name" />
																								(Package)
																							</li>
																						</logic:iterate>
																					</ul>
																				</logic:notEmpty>
																			</ul>
																		</logic:iterate>
																	</logic:notEmpty>
																</UL>
															</logic:iterate>
														</td>
													</tr>
												</table>
											</logic:notEmpty>
											<logic:empty name="projects">
												<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
													align=center border=0 class="OraBGAccentVeryDark">
													<TR class=OraTabledata>
														<td class="OraFieldText">
															Currently not used by any Projects
														</td>
													</TR>
												</table>
											</logic:empty>
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
					<br>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="<%=OCBrowserFormConstants.BIDIRECTIONAL_OCRS%>"
				scope="session">
				<table vAlign=top cellSpacing=1 cellPadding=1 width="100%"
					align=center border=0 class="OraBGAccentVeryDark">
					<TR class=OraTabledata>
						<td colspan=2 class="OraFieldText">
							No bidirectional associations exist for this Object Class
						</td>
					</TR>
				</table>
			</logic:empty>
			<br>
			<br>
			<!--  Ingoing end -->
		</html:form>
	</body>
</html>

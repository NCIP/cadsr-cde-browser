<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%  

  request.setAttribute("msg", "Login Failed, please try again. ");
  request.getRequestDispatcher("formCDECartAction.do?method=displayCDECart").forward(request, response);

  if(true)
    return;

%>
<!-- code below is never executed -->
<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language=javascript> 
if (parent.frames[1]) 
  parent.location.href = self.location.href; 
</script> 
</head>

<body bgcolor="#FFFFFF" text="#000000">
You are not authenticated to use this web application 

<br>
<br>

<a href="<%= "formCDECartAction.do?method=displayCDECart"%>"> Retry Login again</a>

</body>
</html>

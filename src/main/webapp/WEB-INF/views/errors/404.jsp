<%
  request.setAttribute("pageTitle", "Error 404 | Citas UTEC");
  request.setAttribute("pageContent", "/WEB-INF/views/errors/not-found.jsp");
%>
<%@ include file="/WEB-INF/views/errors/errors-layout.jsp"%>
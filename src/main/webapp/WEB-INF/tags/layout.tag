<%@ tag description="base layout" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>
<%@ attribute name="isPublic" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isLogin" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isError" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showSidebar" required="false" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html>
<head>
  <title>
    <c:out value="${title != null ? title : 'Citas UTEC'}"/>
  </title>
  <meta charset="UTF-8">
  <meta name="description" content="Citas UTEC">
  <meta http-equiv="Content-Language" content="es">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <!-- libraries -->
  <link rel="preconnect" href="https://fonts.googleapis.com"/>
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/tailwind.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/libs/apexcharts/dist/apexcharts.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/libs/dropzone/dist/dropzone.css"/>
</head>
<body class="bg-gray-50 font-(family-name:--font-outfit)">
<!-- import sidebar -->
<c:if test="${isPublic eq false and showSidebar eq true}">
  <jsp:include page="/WEB-INF/views/partials/sidebar.jsp"/>
</c:if>

<!-- mobile overlay -->
<div class="fixed inset-0 bg-black bg-opacity-50 z-30 hidden" id="overlay"></div>

<main id="main-content" class="@container transition-all duration-300 ease-in-out ${showSidebar eq true ? 'ml-64' : ''}">
  <c:if test="${isPublic eq false}">
    <jsp:include page="/WEB-INF/views/partials/header.jsp"/>
  </c:if>
  <c:if test="${isPublic eq true and isLogin eq false and isError eq false}">
    <jsp:include page="/WEB-INF/views/partials/public-header.jsp"/>
  </c:if>
  <jsp:doBody />
</main>
<script src="${pageContext.request.contextPath}/public/assets/libs/feather-icons/dist/feather.min.js"></script>
<script>
  feather.replace();
</script>
<script src="${pageContext.request.contextPath}/public/assets/libs/simplebar/dist/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/libs/apexcharts/dist/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/libs/dropzone/dist/dropzone-min.js"></script>
<script type="module" src="${pageContext.request.contextPath}/public/assets/js/app.js"></script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pageTitle" scope="request" type="java.lang.String"/>
<jsp:useBean id="pageContent" scope="request" type="java.lang.String"/>
<!DOCTYPE html>
<html>
<head>
  <title>
    <c:out value="${pageTitle != null ? pageTitle : 'Citas UTEC'}"/>
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/libs/simplebar/dist/simplebar.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/theme.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/styleoverride.css"/>
</head>
<body x-data="{ page: 'dashboard', 'isSidebarOpen': false, 'loaded': true, 'stickyMenu': false, 'scrollTop': false }">
<main>
  <div id="app-layout" class="flex overflow-x-hidden">
    <div id="app-layout-content" class="min-h-screen w-full flex items-center justify-center [transition:margin_0.25s_ease-out]">
      <c:import url="${pageContent}" />
    </div>
  </div>
</main>
<script src="${pageContext.request.contextPath}/public/assets/libs/feather-icons/dist/feather.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/libs/simplebar/dist/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/js/theme.min.js"></script>
</body>
</html>
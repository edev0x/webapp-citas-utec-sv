<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<aside class="navbar-vertical navbar">
  <div id="myScrollableElement" class="h-screen" data-simplebar>
    <a class="navbar-brand text-2xl font-bold text-white mx-auto" href="#">
      <span>
        Citas UTEC
      </span>
    </a>
    <!-- Get the current URI -->
    <c:set var="currentUri" value="${pageContext.request.requestURI}" />
    <c:set var="servletPath" value="${pageContext.request.servletPath}" />

    <c:set var="isHomepage" value="${servletPath eq '/' or servletPath eq '/index.jsp' or servletPath eq '/home.jsp' or empty servletPath}" />

    <!-- Navbar nav -->
    <ul class="navbar-nav flex-col" id="sideNavbar">
      <li class="nav-item">
        <a class="nav-link ${isHomepage ? 'active' : ''}" href="#">
          <i data-feather="layout" class="w-4 h-4 mr-2"></i>
          Dashboard
        </a>
      </li>

      <!-- Admin -->
      <li class="nav-item">
        <div class="navbar-heading">Administraci&oacute;n</div>
      </li>

      <li class="nav-item">
        <a href="#" class="nav-link ${fn:contains(currentUri,'/logs') ? 'active' : ''}">
          <i data-feather="clipboard" class="w-4 h-4 mr-2"></i>
          Bitacora
        </a>
      </li>

      <li class="nav-item">
        <a href="#" class="nav-link ${fn:contains(currentUri,'/service') ? 'active' : ''}">
          <i data-feather="tag" class="w-4 h-4 mr-2"></i>
          Servicios
        </a>
      </li>

      <li class="nav-item">
        <a href="#!" class="nav-link collapsed" data-bs-toggle="collapse" data-bs-target="#collapse-appointments" aria-expanded="false" aria-controls="collapse-appointments">
          <i data-feather="calendar" class="w-4 h-4 mr-2"></i>
          Citas
        </a>
        <div class="collapse" id="collapse-appointments" data-bs-parent="#sideNavbar">
          <ul class="nav flex-col space-y-2">
            <li class="nav-item">
              <a href="#" class="nav-link ${fn:contains(currentUri,'/appointments') ? 'active' : ''}">
                Ver todas las citas
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link ${fn:contains(currentUri,'/appointments') ? 'active' : ''}">
                Crear cita
              </a>
            </li>
          </ul>
        </div>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="#!" data-bs-toggle="collapse" data-bs-target="#collapse-admin" aria-expanded="false" aria-controls="collapse-admin">
          <i data-feather="users" class="w-4 h-4 mr-2"></i>
          Usuarios
        </a>
        <div id="collapse-admin" class="collapse" data-bs-parent="#sideNavbar">
          <ul class="nav flex-col">
            <li class="nav-item">
              <a class="nav-link ${fn:contains(currentUri,'/users') ? 'active' : ''}" href="#">
                Listar usuarios
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link ${fn:contains(currentUri,'/users') ? 'active' : ''}" href="#">
                Crear usuario
              </a>
            </li>
          </ul>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link ${fn:contains(currentUri,'/settings') ? 'active' : ''}" href="#">
          <i data-feather="settings" class="w-4 h-4 mr-2"></i>
          Ajustes
        </a>
      </li>
    </ul>
  </div>
</aside>

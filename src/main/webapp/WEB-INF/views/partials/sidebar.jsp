<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="security" class="com.utec.citasutec.model.ejb.SecurityBean" scope="session"/>
<!-- Sidebar -->
<div id="app-sidebar" class="fixed left-0 top-0 h-full bg-gray-900 text-white transition-all duration-300 z-40 w-64 overflow-y-auto">
  <div class="p-6">
    <h2 class="text-xl font-bold mb-8">Menu</h2>
    <nav class="space-y-2">

      <c:set var="path" value="${pageContext.request.requestURL}" />


      <c:if test="${security.hasRole('ADMIN')}">
        <!-- Dashboard -->
        <a href="#" class="flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-800 transition text-sm ${fn:containsIgnoreCase(path, '/admin/home') ? 'bg-gray-800 font-medium' : ''}">
          <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
          </svg>
          <span>Dashboard</span>
        </a>

        <!-- Administrar  -->
        <div class="menu-item">
          <button class="flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-800 transition w-full menu-parent">
            <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
            </svg>
            <span class="flex-1 text-left text-sm">Administrar</span>
            <svg class="w-4 h-4 transition-transform chevron" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
            </svg>
          </button>
          <div class="submenu hidden ml-8 mt-2 space-y-1">
            <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-800 transition text-sm text-gray-300 hover:text-white ${fn:containsIgnoreCase(path, '/manage/users') ? 'bg-gray-800 font-medium' : ''} ">
              <span>Usuarios</span>
            </a>
            <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-800 transition text-sm text-gray-300 hover:text-white ${fn:containsIgnoreCase(path, '/manage/roles') ? 'bg-gray-800 font-medium' : ''}">
              <span>Roles</span>
            </a>
          </div>
        </div>
      </c:if>

      <!-- Settings -->
      <a href="#" class="flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-800 transition text-sm ${fn:contains(pageContext.request.requestURI, 'bg-gray-800 font-medium') ? 'bg-gray-800' : ''}">
        <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path>
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
        </svg>
        <span>Ajustes</span>
      </a>

      <a href="#" class="flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-800 transition text-sm ${fn:contains(pageContext.request.requestURI, 'bg-gray-800 font-medium') ? 'bg-gray-800' : ''}">
        <i data-feather="help-circle" class="w-5 h-5 flex-shrink-0"></i>
        <span>Centro de Ayuda</span>
      </a>
<%--
      <!-- Documents (with submenu) -->
      <div class="menu-item">
        <button class="flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-800 transition w-full menu-parent">
          <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
          </svg>
          <span class="flex-1 text-left">Documents</span>
          <svg class="w-4 h-4 transition-transform chevron" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
          </svg>
        </button>
        <div class="submenu hidden ml-8 mt-2 space-y-1">
          <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-800 transition text-sm text-gray-300 hover:text-white">
            <span>Mis Documentos</span>
          </a>
          <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-800 transition text-sm text-gray-300 hover:text-white">
            <span>Compartidos</span>
          </a>
          <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-800 transition text-sm text-gray-300 hover:text-white">
            <span>Archivados</span>
          </a>
        </div>
      </div>--%>
      <!-- Welcome message -->
      <c:if test="${security.loggedIn}">
        <!-- Sidebar Footer -->
        <div class="absolute bottom-0 left-0 right-0 p-6 bg-gray-800 border-t border-gray-700">
          <div class="flex items-center space-x-3 mb-3">
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-white truncate mb-1">
                Bienvenido/a: <br />
                <strong>${security.username}</strong>
              </p>
              <c:forEach items="${security.roles}" var="role">
                <span class="inline-block mt-1 px-2 py-1 text-xs font-semibold text-gray-100 bg-green-600 rounded-full">
                 ${role}
               </span>
              </c:forEach>
            </div>
          </div>
        </div>
      </c:if>
    </nav>
  </div>
</div>
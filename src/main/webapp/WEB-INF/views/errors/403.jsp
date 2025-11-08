<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Unauthorized" isLogin="false" isPublic="true" isError="true">
  <div class="min-h-screen w-full flex items-center justify-center [transition:margin_0.25s_ease-out]">
    <div class="text-center p-4">
      <h1 class="text-5xl font-bold mb-4">
        Permiso denegado
      </h1>
      <p class="mb-8 text-lg font-medium text-gray-700">
        No tienes los permisos necesarios para acceder a esta página. Si crees que se trata de un error, contacta con el administrador del sistema.
      </p>
      <a href="${pageContext.request.contextPath}/">
        <button type="button" class="btn bg-(--primary) hover:bg-(--primary)/90 text-gray-100 px-4 py-3 text-sm rounded-lg font-medium transition duration-200 cursor-pointer focus:outline-none focus:ring-2 focus:ring-offset-1 focus:ring-(--primary)/80 disabled:opacity-50 disabled:cursor-not-allowed">
          Volver al inicio
        </button>
      </a>
    </div>
  </div>
</t:layout>
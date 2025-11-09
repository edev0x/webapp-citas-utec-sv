<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="Login | Citas UTEC" isPublic="true" isLogin="true" isError="false" showSidebar="false">
  <div class="min-h-[90vh] flex items-center justify-center px-4 sm:px-6 lg:px-8 py-4">
    <div class="w-full max-w-md mx-auto lg:mx-0">
      <div class="bg-white rounded-lg shadow-lg p-8">
        <h2 class="text-2xl font-bold text-gray-800 mb-4">
          Iniciar Sesi&oacute;n
        </h2>
        <form id="loginForm" class="space-y-6" method="POST" action="${pageContext.request.contextPath}/login">

          <!-- Email Input -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700 mb-2">
              Correo electr&oacute;nico:
              <sup class="text-red-500 text-xs">*</sup>
            </label>
            <input
                type="email"
                id="email"
                name="email"
                placeholder="usuario@utec.edu.sv"
                required
                value="${credentials.email()}"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
            >
            <c:if test="${not empty validationErrors['email']}">
              <p class="text-red-500 text-sm mt-1" id="emailError">
                  ${validationErrors['email']}
              </p>
            </c:if>
          </div>

          <!-- Password Input -->
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
              Contrase&ntilde;a:
              <sup class="text-red-500 text-xs">*</sup>
            </label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="********"
                value="${credentials.password()}"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
            >
            <c:if test="${not empty validationErrors['password']}">
              <p class="text-red-500 text-sm mt-1" id="passwordError">
                  ${validationErrors['password']}
              </p>
            </c:if>
          </div>

          <!-- Remember Me Checkbox -->
          <div class="flex items-start mb-6">
            <div class="flex items-center h-5">
              <input id="remember" type="checkbox" value="${credentials.rememberMe()}" class="w-4 h-4 border border-gray-300 rounded-sm bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"  />
            </div>
            <label for="remember" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Recordarme</label>
          </div>

          <c:if test="${not empty invalidCredentials}">
            <p class="text-red-500 text-sm mt-1 font-semibold" id="signInError">
              ${invalidCredentials}
            </p>
          </c:if>

          <!-- Submit Button -->
          <button
              id="loginButton"
              type="submit"
              class="w-full flex flex-row items-center justify-center bg-(--primary) hover:bg-(--primary)/90 text-gray-100 py-3 rounded-lg font-medium transition duration-200 cursor-pointer focus:outline-none focus:ring-2 focus:ring-offset-1 focus:ring-(--primary)/80 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <i data-feather="log-in" id="login-icon" class="w-5 h-5 mr-2"></i>
            <i data-feather="loader" id="login-spinner" class="hidden animate-spin w-5 h-5 mr-2"></i>
            <span id="login-text">Iniciar Sesi&oacute;n</span>
          </button>

          <a href="${pageContext.request.contextPath}/forgot-password" class="text-sm text-blue-500 hover:underline text-center">
            &iquest;Olvidaste tu contrase&ntilde;a?
          </a>
        </form>
      </div>
    </div>
  </div>
</t:layout>
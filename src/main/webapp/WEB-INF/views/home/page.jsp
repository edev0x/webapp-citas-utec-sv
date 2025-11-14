<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Hero Section -->
<section class="min-h-[90vh] flex items-center justify-center px-4 sm:px-6 lg:px-8 py-4">
  <div class="max-w-7xl w-full grid grid-cols-1 lg:grid-cols-2 gap-12 items-center px-6">
    <!-- Left Content -->
    <div class="text-center lg:text-left">
      <h1 class="text-3xl sm:text-5xl font-bold text-gray-900 mb-4 line-h">
        ¡Cuidamos tu salud, potenciamos tu futuro!
      </h1>
      <p class="text-lg sm:text-xl text-gray-600 leading-relaxed">
        Atención médica integral, oportuna y confiable para estudiantes.
      </p>
    </div>

    <!-- Right Form -->
    <div class="w-full max-w-md mx-auto lg:mx-0">
      <div class="bg-white rounded-lg shadow-lg p-8">
        <form id="signupForm" class="space-y-6" method="POST" action="${pageContext.request.contextPath}/signup">
          <!-- name and last name inputs -->
          <div class="grid gap-4 mb-6 md:grid-cols-2">
            <div>
              <label for="firstName" class="block text-sm font-medium text-gray-700 mb-2">
                Nombre:
                <sup class="text-red-500 text-xs">*</sup>
              </label>
              <input
                  type="text"
                  class="w-full p-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                  id="firstName"
                  name="firstName"
                  value="${registerDto.firstName()}"
                  placeholder="Ingrese sus nombres"
                  required
              >
              <c:if test="${not empty validationErrors['firstName']}">
                <p class="text-red-500 text-sm mt-1" id="nameError">
                  ${validationErrors['firstName']}
                </p>
              </c:if>
            </div>
            <div>
              <label for="lastName" class="block text-sm font-medium text-gray-700 mb-2">
                Apellido:
                <sup class="text-red-500 text-xs">*</sup>
              </label>
              <input
                  type="text"
                  class="w-full p-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                  id="lastName"
                  name="lastName"
                  value="${registerDto.lastName()}"
                  placeholder="Ingrese sus apellidos"
                  required
              >
              <c:if test="${not empty validationErrors['lastName']}">
                <p class="text-red-500 text-sm mt-1" id="lastNameError">
                  ${validationErrors['lastName']}
                </p>
              </c:if>
            </div>
          </div>

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
                value="${registerDto.email()}"
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
                value="${registerDto.password()}"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
            >
            <c:if test="${not empty validationErrors['password']}">
              <p class="text-red-500 text-sm mt-1" id="passwordError">
                ${validationErrors['password']}
              </p>
            </c:if>
          </div>

          <%--  <div class="flex items-start mb-6">
            <div class="flex items-center h-5">
              <input id="remember" type="checkbox" value="" class="w-4 h-4 border border-gray-300 rounded-sm bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"  />
            </div>
            <label for="remember" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Recordarme</label>
          </div> --%>

          <c:if test="${emailTaken eq true and not empty emailTakenError}">
            <p class="text-red-500 text-sm mt-1" id="invalidCredentials">
              ${emailTakenError}
            </p>
          </c:if>

          <c:if test="${registrationSuccess eq true}">
            <div class="relative flex flex-col sm:flex-row sm:items-center bg-green-50 rounded-lg shadow rounded-md py-5 pl-6 pr-8 sm:pr-6">
              <div class="flex flex-row items-center border-b sm:border-b-0 w-full sm:w-auto pb-4 sm:pb-0">
                <div class="text-green-500">
                  <svg class="w-6 sm:w-5 h-6 sm:h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                </div>
                <div class="text-sm font-medium ml-3">
                  Cuenta creada
                  <div class="text-sm tracking-wide text-gray-500 mt-4 sm:mt-0">
                    Tu cuenta ha sido creada exitosamente. Ya puedes iniciar sesi&oacute;n.
                  </div>
                </div>
              </div>
            </div>
          </c:if>

          <!-- Submit Button -->
          <button
              type="submit"
              class="w-full bg-primary hover:bg-primary/90 text-gray-100 py-3 rounded-lg font-medium transition duration-200 cursor-pointer focus:outline-none focus:ring-2 focus:ring-offset-1 focus:ring-primary/80"
          >
            Registrarme
          </button>

          <!-- Terms Text -->
          <p class="text-center text-sm text-gray-600">
            Al dar click en Registrarme, est&aacute;s aceptando nuestras Condiciones de Uso y Pol&iacute;ticas de Privacidad.
          </p>
        </form>
      </div>
    </div>
  </div>
  <div class="hidden lg:block">
    <pre>
      <code>

      </code>
    </pre>
  </div>
</section>
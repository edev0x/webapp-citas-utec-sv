<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<header id="public-header" class="bg-white shadow-sm sticky top-0 z-20">
  <nav class="max-w-full px-4 sm:px-6 lg:px-8">
    <div class="flex justify-between items-center h-16">
      <div class="flex items-center space-x-6">
        <div class="flex items-center">
          <a href="${pageContext.request.contextPath}/index.jsp" class="text-xl font-bold text-gray-900">
            Citas UTEC
          </a>
        </div>
        <div class="flex items-center">
          <a href="${pageContext.request.contextPath}/about.jsp">
            <button type="button" class="bg-gray-100/50 hover:bg-gray-100 px-3 py-2 text-[13px] font-medium rounded-md transition duration-200 cursor-pointer focus:outline-none">
              Acerca de
            </button>
          </a>
        </div>
      </div>
      <div class="flex items-center">
        <a href="${pageContext.request.contextPath}/login.jsp">
          <button
              type="submit"
              class="bg-(--primary) hover:bg-(--primary)/90 text-gray-100 px-3 py-2 text-[13px] font-medium rounded-md transition duration-200 cursor-pointer focus:outline-none focus:ring-2 focus:ring-offset-1 focus:ring-(--primary)/80"
          >
            Iniciar Sesi&oacute;n
          </button>
        </a>
      </div>
    </div>
  </nav>
</header>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<header id="private-header" class="bg-white shadow-sm sticky top-0 z-20">
  <nav class="max-w-full px-4 sm:px-6 lg:px-8">
    <div class="flex justify-between items-center h-16">
      <div class="flex items-center space-x-6">
        <button id="sidebar-toggle" class="btn-sm-ghost mr-auto size-7 -ml-1.5"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="18" height="18" x="3" y="3" rx="2" /><path d="M9 3v18" /></svg>
        </button>
      </div>
      <div class="relative">
        <button id="avatar-button" class="flex items-center space-x-2 focus:outline-none">
          <img
              src="https://ui-avatars.com/api/?name=UT&background=93283a&color=fff&size=128"
              alt="User Avatar"
              class="w-10 h-10 rounded-full border-2 border-gray-200 hover:border-blue-500 transition cursor-pointer"
          >
        </button>

        <!-- Dropdown Menu -->
        <div id="avatar-menu" class="hidden absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-1 border border-gray-200">
          <a href="#" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 transition">
            <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
            </svg>
            Mi Perfil
          </a>
          <hr class="my-1 border-gray-200">
          <a href="${pageContext.request.contextPath}/logout" class="flex items-center px-4 py-2 text-sm text-red-600 hover:bg-red-50 transition">
            <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
            </svg>
            Cerrar Sesión
          </a>
        </div>
      </div>
    </div>
  </nav>
</header>

<%--
<div class="header">
  <!-- navbar -->
  <nav class="bg-white px-6 py-[10px] flex items-center justify-between shadow-sm">
    <a id="nav-toggle" href="#" class="text-gray-800">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
           class="w-6 h-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"/>
      </svg>
    </a>
    <!-- navbar nav -->
    <ul class="flex ml-auto items-center">
      <!-- list -->
      <li class="dropdown ml-2">
        <a class="rounded-full" href="#" role="button" id="dropdownUser" data-bs-toggle="dropdown" aria-haspopup="true"
           aria-expanded="false">
          <div class="w-10 h-10 relative">
            <img alt="avatar" src="./assets/images/avatar/avatar-1.jpg" class="rounded-full"/>
            <div class="absolute border-gray-200 border-2 rounded-full right-0 bottom-0 bg-green-600 h-3 w-3"></div>
          </div>
        </a>
        <div class="dropdown-menu dropdown-menu-end p-2" aria-labelledby="dropdownUser">
          <div class="px-4 pb-0 pt-2">
            <div class="leading-4">
              <h5 class="mb-1">John E. Grainger</h5>
              <a href="#">View my profile</a>
            </div>
            <div class="border-b mt-3 mb-2"></div>
          </div>

          <ul class="list-unstyled">
            <li>
              <a class="dropdown-item" href="./index.html">
                <i class="w-4 h-4" data-feather="power"></i>
                Cerrar Sesi&oacute;n
              </a>
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </nav>
</div>--%>


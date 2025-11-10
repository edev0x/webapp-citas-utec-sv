<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateUtils" uri="http://utec.edu/jsp/tlds/dateUtils" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:layout isPublic="false" isError="false" isLogin="false" showSidebar="true">
  <div class="min-h-[90vh] w-full flex flex-col p-2 overflow-hidden">
    <div class="mx-auto w-full p-4 pb-20 md:p-6 md:pb-6">
      <div class="grid grid-cols-12 gap-4 md:gap-6">
        <div class="col-span-12 space-y-6 xl:col-span-12">
          <div class="border-t border-gray-100 p-5 sm:p-6 dark:border-gray-800">
            <div class="overflow-hidden rounded-2xl border border-gray-200 bg-white pt-4">
              <div class="flex flex-col gap-5 px-6 mb-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                  <h3 class="text-xl font-bold text-gray-800 dark:text-white/90">
                    Gestión de Usuarios
                  </h3>
                </div>
                <div class="flex flex-col gap-3 sm:flex-row sm:items-center">
                  <form method="get" id="uf-search-form">
                    <div class="relative">
                      <!-- Search input -->
                      <span class="absolute -translate-y-1/2 pointer-events-none top-1/2 left-4">
                        <svg class="fill-gray-500 dark:fill-gray-400" width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <path fill-rule="evenodd" clip-rule="evenodd" d="M3.04199 9.37381C3.04199 5.87712 5.87735 3.04218 9.37533 3.04218C12.8733 3.04218 15.7087 5.87712 15.7087 9.37381C15.7087 12.8705 12.8733 15.7055 9.37533 15.7055C5.87735 15.7055 3.04199 12.8705 3.04199 9.37381ZM9.37533 1.54218C5.04926 1.54218 1.54199 5.04835 1.54199 9.37381C1.54199 13.6993 5.04926 17.2055 9.37533 17.2055C11.2676 17.2055 13.0032 16.5346 14.3572 15.4178L17.1773 18.2381C17.4702 18.531 17.945 18.5311 18.2379 18.2382C18.5308 17.9453 18.5309 17.4704 18.238 17.1775L15.4182 14.3575C16.5367 13.0035 17.2087 11.2671 17.2087 9.37381C17.2087 5.04835 13.7014 1.54218 9.37533 1.54218Z" fill=""></path>
                        </svg>
                      </span>
                      <input id="search-term" type="text" placeholder="Search..." class="dark:bg-dark-900 shadow-theme-xs focus:border-gray-100 focus:ring-gray-100/10 dark:focus:border-brand-800 h-10 w-full rounded-lg border border-gray-300 bg-transparent py-2.5 pr-4 pl-[42px] text-sm text-gray-800 placeholder:text-gray-400 focus:ring-3 focus:outline-hidden xl:w-[300px] dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" name="search-term" value="${param.searchTerm != null ? param.searchTerm : ''}" />
                    </div>
                  </form>

                    <div id="uf-select" class="select">
                      <button
                        type="button"
                        id="uf-select-trigger"
                        class="btn-outline justify-between font-normal w-full"
                        aria-haspopup="listbox"
                        aria-expanded="false"
                        aria-controls="uf-select-listbox">
                        <span class="flex items-center gap-2">
                          <i data-feather="sliders" class="w-4 h-4"></i>
                          <span class="truncate">Filtrar por</span>
                        </span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                          <path d="m7 15 5 5 5-5" />
                          <path d="m7 9 5-5 5 5" />
                        </svg>
                      </button>

                      <div id="uf-select-popover" data-popover aria-hidden="true" class="border-gray-100">
                        <div role="listbox" id="uf-select-listbox" aria-orientation="vertical" aria-labelledby="uf-select-trigger">
                          <div role="group" aria-labelledby="uf-group-label-select-items-1">
                            <div role="heading" id="uf-group-label-select-items-1">
                              Campos de búsqueda
                            </div>
                            <div id="uf-select-items-1" role="option" data-value="firstName" aria-selected="true">
                              Nombre
                            </div>
                            <div id="uf-select-items-2" role="option" data-value="lastName">
                              Apellido
                            </div>
                            <div id="uf-select-items-3" role="option" data-value="email">
                              Email
                            </div>
                            <div id="uf-select-items-4" role="option" data-value="rol.name">
                              Rol
                            </div>
                          </div>
                        </div>
                      </div>

                      <input type="hidden" name="uf-select-value" value="firstName" />
                    </div>
                </div>
              </div>
              <div class="max-w-full overflow-x-auto custom-scrollbar">
                <c:if test="${paginatedUsers != null and not empty paginatedUsers.getItems()}">
                  <c:set var="usersList" value="${paginatedUsers.getItems()}" />
                  <table class="min-w-full">
                    <!-- header -->
                    <thead class="border-gray-100 border-y bg-gray-50 dark:border-gray-800 dark:bg-gray-900">
                      <tr>
                        <th class="px-6 py-3 whitespace-nowrap">
                          <span class="block font-medium text-gray-700 text-sm">
                            Id
                          </span>
                        </th>
                        <th class="px-6 py-3 whitespace-nowrap text-left">
                          <span class="block font-medium text-gray-700 text-sm">
                            Usuario
                          </span>
                        </th>
                        <th class="px-6 py-3 whitespace-nowrap">
                          <span class="block font-medium text-gray-700 text-sm">
                            Rol
                          </span>
                        </th>
                        <th class="px-6 py-3 whitespace-nowrap">
                          <span class="block font-medium text-gray-700 text-sm">
                            Estado
                          </span>
                        </th>
                        <th class="px-6 py-3 whitespace-nowrap">
                          <span class="block font-medium text-gray-700 text-sm">
                            Acciones
                          </span>
                        </th>
                      </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100">
                    <c:forEach var="user" items="${usersList}">
                      <tr class="hover:bg-gray-50 dark:hover:bg-white/[0.03] cursor-pointer">
                        <td class="px-6 py-4 whitespace-nowrap">
                          <div class="flex items-center justify-center">
                              <span class="text-sm text-gray-700 dark:text-gray-400">
                                  ${user.id()}
                              </span>
                          </div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                          <div class="flex-1 min-w-[100px] flex items-center justify-start gap-3">
                            <div class="flex items-center justify-center w-10 h-10 rounded-full bg-primary gap-2 hover:bg-primary/80">
                                <span class="text-sm font-semibold text-gray-200">
                                    ${fn:substring(user.firstName().toUpperCase(),0,1)}${fn:substring(user.lastName().toUpperCase(),0,1)}
                                </span>
                            </div>
                            <div class="flex flex-col gap-2">
                                <span class="text-md font-semibold text-gray-700 dark:text-gray-400">
                                  ${user.firstName()}&nbsp;${user.lastName()}
                                </span>
                              <span class="text-gray-500 text-sm">
                                  ${user.email()}
                              </span>
                            </div>
                          </div>
                        </td>
                        <c:if test="${not empty user.role()}">
                          <td class="px-6 py-4 whitespace-nowrap">
                            <div class="flex items-center justify-center gap-2">
                                <span class="badge-secondary text-sm font-medium">
                                    ${user.role().name()}
                                </span>
                            </div>
                          </td>
                        </c:if>
                        <td class="px-6 py-4 whitespace-nowrap">
                          <div class="flex items-center justify-center">
                            <c:choose>
                              <c:when test="${user.isActive()}">
                                <p class="badge-secondary bg-green-100 text-sm text-green-600 inline-flex items-center font-medium">
                                  Activo
                                </p>
                              </c:when>
                              <c:otherwise>
                                <p class="badge-destructive text-sm inline-flex items-center font-medium">
                                  Inactivo
                                </p>
                              </c:otherwise>
                            </c:choose>
                          </div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                          <div class="flex items-center justify-center">
                            <a href="${pageContext.request.contextPath}/app/manage/users?action=delete&resource=user&id=${user.id()}"
                               class="btn-ghost text-gray-600 hover:bg-destructive/10 hover:text-destructive px-3 py-2 rounded-md inline-flex items-center text-sm font-medium">
                              <i data-feather="trash-2" class="inline-block w-4 h-4 mr-1"></i>
                            </a>
                          </div>
                        </td>
                          <%--  <td class="px-6 py-4 whitespace-nowrap">
                             <div class="flex items-center justify-center">
                               <span class="text-sm text-gray-700 dark:text-gray-400">
                                 <fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                               </span>
                             </div>
                           </td>
                           <td class="px-6 py-4 whitespace-nowrap">
                             <div class="flex items-center justify-center">
                               <span class="text-sm text-gray-700 dark:text-gray-400">
                                 <fmt:formatDate value="${user.updatedAt}" pattern="dd/MM/yyyy HH:mm" />
                               </span>
                             </div>
                           </td> --%>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</t:layout>
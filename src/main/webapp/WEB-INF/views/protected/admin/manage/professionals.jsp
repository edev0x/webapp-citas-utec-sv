<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateUtils" uri="http://utec.edu/jsp/tlds/dateUtils" %>

<t:layout isPublic="false" isError="false" isLogin="false" showSidebar="true">
  <div class="min-h-[90vh] w-full flex flex-col p-2 overflow-hidden">
    <div class="mx-auto w-full p-4 pb-20 md:p-6 md:pb-6">
      <div class="grid grid-cols-12 gap-4 md:gap-6">
        <div class="col-span-12 space-y-6 xl:col-span-12">
          <div class="border-t border-gray-100 p-5 sm:p-6 dark:border-gray-800">
            <div class="overflow-hidden rounded-2xl border border-gray-200 bg-white pt-4">
              <div class="flex flex-col gap-5 px-6 mb-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                  <h1 class="text-2xl font-bold text-gray-800">Gestionar Profesionales</h1>
                </div>
                <div class="flex flex-col gap-3 sm:flex-row sm:items-center">
                  <div>
                    <button type="button" class="btn-primary" id="open-professional-creation-dialog-button" >
                      <i data-feather="plus" class="inline-block w-4 h-4 mr-1"></i>
                      Agregar
                    </button>
                  </div>
                </div>
              </div>
              <div class="max-w-full overflow-x-auto custom-scrollbar">
                <c:choose>
                  <c:when test="${empty professionals or professionals == null}">
                    <div class="flex justify-center items-center h-full min-w-full" style="height: 400px;">
                      <p class="text-gray-500">No hay profesionales registrados</p>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <table class="min-w-full">
                      <thead class="border-gray-100 border-y bg-gray-50">
                        <tr>
                          <th class="px-6 py-3 text-left whitespace-nowrap">
                            <span class="block text-gray-700 font-medium text-sm">
                              Id
                            </span>
                          </th>
                          <th class="px-6 py-3 text-left whitespace-nowrap">
                            <span class="block text-gray-700 font-medium text-sm">
                              Profesional
                            </span>
                          </th>
                          <th class="px-6 py-3 text-left whitespace-nowrap">
                            <span class="block text-gray-700 font-medium text-sm">
                              Especialidad
                            </span>
                          </th>
                          <th class="px-6 py-3 whitespace-nowrap">
                            <span class="block text-gray-700 font-medium text-sm">
                              Acciones
                            </span>
                          </th>
                        </tr>
                      </thead>
                      <tbody class="divide-y divide-gray-100">
                        <c:forEach var="professional" items="${professionals}">
                            <tr class="hover:bg-gray-50 cursor-pointer"
                                data-id="${professional.id()}"
                                data-name="${professional.fullName()}"
                                data-email="${professional.email()}"
                                data-specialty="${professional.specialty()}"
                                data-is-active="${professional.isActive()}"
                            >
                              <td class="px-6 py-4 whitespace-nowrap">
                                <span class="text-sm text-gray-900">${professional.id()}</span>
                              </td>
                              <td class="px-6 py-4 whitespace-nowrap">
                                <div class="flex flex-col gap-2">
                                  <span class="text-md font-semibold text-gray-700 dark:text-gray-400">${professional.fullName()}</span>
                                  <span class="text-sm text-gray-500">
                                    ${professional.email()}
                                  </span>
                                </div>
                              </td>
                              <td class="px-6 py-4 whitespace-nowrap">
                                <span class="text-sm text-gray-900">${professional.specialty()}</span>
                              </td>
                              <td class="px-6 py-4 whitespace-nowrap">
                                <div class="flex items-center justify-center gap-1">
                                  <button type="button" id="open-delete-professional-dialog-btn" class="btn-ghost text-gray-600 hover:bg-destructive/10 hover:text-destructive px-3 py-2 rounded-md inline-flex items-center text-sm font-medium">
                                    <i data-feather="trash-2" class="w-4 h-4"></i>
                                  </button>
                                  <button type="button" id="open-professional-edit-dialog-button" class="btn-ghost text-gray-600 hover:bg-primary/10 hover:text-gray-800 px-3 py-2 rounded-md inline-flex items-center text-sm font-medium">
                                    <i data-feather="edit" class="w-4 h-4"></i>
                                  </button>
                                </div>
                              </td>
                            </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="/WEB-INF/views/partials/professionals/create-professional-dialog.jsp" />
  <jsp:include page="/WEB-INF/views/partials/professionals/edit-professional-dialog.jsp" />
  <jsp:include page="/WEB-INF/views/partials/professionals/delete-professional-dialog.jsp" />
</t:layout>
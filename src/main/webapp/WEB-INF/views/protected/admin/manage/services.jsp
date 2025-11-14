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
                  <h1 class="text-2xl font-bold text-gray-800">Gestionar Servicios</h1>
                </div>
                <div class="flex flex-col gap-3 sm:flex-row sm:items-center">
                  <div>
                    <button type="button" class="btn-primary" id="open-service-creation-dialog-button" >
                      <i data-feather="plus" class="inline-block w-4 h-4 mr-1"></i>
                      Agregar
                    </button>
                  </div>
                </div>
              </div>
              <div class="max-w-full overflow-x-auto custom-scrollbar">
                <c:choose>
                  <c:when test="${empty services or services == null}">
                    <div class="flex justify-center items-center h-full min-w-full" style="height: 400px;">
                      <p class="text-gray-500">No hay servicios registrados</p>
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
                              Servicio
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
                        <c:forEach var="service" items="${services}">
                          <tr>
                            <td class="px-6 py-4 whitespace-nowrap">
                              <span class="text-gray-700 text-sm font-medium"> ${service.id} </span>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap">
                              <div class="flex flex-col">
                                <span class="text-gray-700 font-medium"> ${service.name} </span>
                                <span class="text-gray-500 text-sm mt-1"> ${service.description} </span>
                              </div>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap">
                              <div class="flex gap-2">
                                <button type="button" id="open-service-edit-dialog-button" class="btn-ghost text-gray-600 hover:bg-primary/10 hover:text-gray-800 px-3 py-2 rounded-md inline-flex items-center text-sm font-medium"
                                  data-id="${service.id}"
                                  data-name="${service.name}"
                                  data-description="${service.description}"
                                >  
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
</t:layout>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout isPublic="false" isError="false" isLogin="false" showSidebar="true">
  <div class="min-h-screen w-full flex flex-col p-6 overflow-hidden">
    <div class="mx-auto w-full p-4 pb-20 md:p-6 md:pb-6">
      <div class="grid grid-cols-12 gap-4 md:gap-6">
        <div class="col-span-12 space-y-6 xl:col-span-7">
          <!-- Metrics group -->
          <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 md:gap-6">
            <!-- Users metric -->
            <div class="rounded-2xl border border-gray-200 bg-white p-5 dark:border-gray-700 dark:bg-gray-800 md:p-6">
              <div class="flex h-12 w-12 items-center justify-center rounded-xl bg-gray-100 dark:bg-gray-800">
                <i data-feather="users" class="w-6 h-6 text-gray-800 dark:text-white/90"></i>
              </div>
              <div class="mt-5 flex items-end justify-between">
                <div>
                  <span class="text-sm font-medium text-gray-500 dark:text-gray-400">Usuarios Activos</span>
                  <h4 class="mt-2 text-2xl font-bold text-gray-800 dark:text-white/90">${totalActiveUsers}</h4>
                </div>
                <span></span>
              </div>
            </div>
  
            <!-- Appointments metric -->
            <div class="rounded-2xl border border-gray-200 bg-white p-5 dark:border-gray-800 dark:bg-white/[0.03] md:p-6">
              <div class="flex h-12 w-12 items-center justify-center rounded-xl bg-gray-100 dark:bg-gray-800">
                <i data-feather="calendar" class="w-6 h-6 text-gray-800 dark:text-white/90"></i>
              </div>
  
              <div class="mt-5 flex items-end justify-between">
                <div>
                  <span class="text-sm text-gray-500 dark:text-gray-400">Total de Citas</span>
                  <h4 class="mt-2 text-title-sm font-bold text-gray-800 dark:text-white/90">
                    ${totalAppointments}
                  </h4>
                </div>
              </div>
            </div>
          </div>
          <!-- Chart -->
          <div
            class="overflow-hidden rounded-2xl border border-gray-200 bg-white px-5 pt-4 sm:px-6 sm:pt-6 dark:border-gray-800 dark:bg-white/[0.03]">
            <div class="flex items-center justify-between">
              <h3 class="text-lg font-bold uppercase text-gray-800 dark:text-white/90">
                Citas por mes
              </h3>
  
              <div class="dropdown-menu relative h-fit" id="chart-1-drop-down">
                <button type="button" id="chart-1-drop-down-trigger" aria-haspopup="menu"
                  aria-controls="chart-1-drop-down-menu" aria-expanded="false" class="btn-ghost">
                  <svg class="fill-current" width="24" height="24" viewBox="0 0 24 24" fill="none"
                    xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd"
                      d="M10.2441 6C10.2441 5.0335 11.0276 4.25 11.9941 4.25H12.0041C12.9706 4.25 13.7541 5.0335 13.7541 6C13.7541 6.9665 12.9706 7.75 12.0041 7.75H11.9941C11.0276 7.75 10.2441 6.9665 10.2441 6ZM10.2441 18C10.2441 17.0335 11.0276 16.25 11.9941 16.25H12.0041C12.9706 16.25 13.7541 17.0335 13.7541 18C13.7541 18.9665 12.9706 19.75 12.0041 19.75H11.9941C11.0276 19.75 10.2441 18.9665 10.2441 18ZM11.9941 10.25C11.0276 10.25 10.2441 11.0335 10.2441 12C10.2441 12.9665 11.0276 13.75 11.9941 13.75H12.0041C12.9706 13.75 13.7541 12.9665 13.7541 12C13.7541 11.0335 12.9706 10.25 12.0041 10.25H11.9941Z"
                      fill=""></path>
                  </svg>
                </button>
                <div id="chart-1-drop-down-menu-popover" data-popover aria-hidden="true" class="min-w-[36px]">
                  <div role="menu" id="chart-1-drop-down-menu" aria-labelledby="chart-1-drop-down-trigger"
                    class="rounded-md shadow-lg bg-white dark:bg-gray-800">
                    <div role="menuitem">
                      Ver detalles
                    </div>
                    <div role="menuitem">
                      Eliminar
                    </div>
                  </div>
                </div>
              </div>
            </div>
  
            <div class="max-w-full overflow-x-auto custom-scrollbar mb-4">
              <div id="chart-1" class="h-full pl-2 xl:min-w-full" style="min-height: 300px;"></div>
            </div>
          </div>
        </div>
        <div class="col-span-12 xl:col-span-5">
          <div
            class="rounded-2xl border border-gray-200 bg-white px-5 pt-5 dark:border-gray-800 dark:bg-white/[0.03] sm:px-6 sm:pt-6 h-full">
            <div class="flex items-center justify-between">
              <h3 class="text-lg uppercase font-bold text-gray-800 dark:text-white/90">
                Citas por estado
              </h3>
            </div>
            <div class="h-full">
              <div id="appointments-bystate-chart"
                class="h-full w-full flex items-center justify-center pl-2 xl:min-w-full"
                style="min-height:300px; height:100%;">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</t:layout>
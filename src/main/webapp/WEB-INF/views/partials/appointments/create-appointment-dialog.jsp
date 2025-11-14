<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<jsp:useBean id="security" class="com.utec.citasutec.model.ejb.SecurityBean" scope="session"/>
<
<%
  LocalDateTime todaysDate = LocalDateTime.now();
%>
<dialog id="appointment-creation-dialog" class="dialog" aria-labelledby="appointment-creation-dialog-title"
        aria-describedby="appointment-creation-dialog-description">
  <article>
    <header>
      <h2 id="alert-dialog-title">Agendar nueva cita</h2>
      <p id="alert-dialog-description">
        Ingrese los datos de la cita.
      </p>
    </header>
    <form method="post" id="appointment-creation-form">
      <div class="flex flex-col gap-2">
        <div class="grid grid-cols-2 gap-2 mb-4">
          <div class="space-y-2">
            <label for="userId" class="label required">Usuario que solicita:</label>
            <div id="users-select" class="select w-full">
              <c:if test="${security.hasRole('ESTUDIANTE')}">
                <button type="button" class="btn-outline justify-between font-normal w-full" id="users-select-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="users-select-listbox" disabled="disabled">
                  <span class="truncate"></span>

                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                    <path d="m7 15 5 5 5-5" />
                    <path d="m7 9 5-5 5 5" />
                  </svg>
                </button>
              </c:if>
              <c:if test="${security.hasRole('ADMIN')}">
                <button type="button" class="btn-outline justify-between font-normal w-full" id="users-select-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="users-select-listbox">
                  <span class="truncate"></span>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                    <path d="m7 15 5 5 5-5" />
                    <path d="m7 9 5-5 5 5" />
                  </svg>
                </button>
              </c:if>
              <div id="users-select-popover" data-popover aria-hidden="true">
                <header>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search">
                    <circle cx="11" cy="11" r="8" />
                    <path d="m21 21-4.3-4.3" />
                  </svg>
                  <input type="text" value="" placeholder="Buscar..." autocomplete="off" autocorrect="off" spellcheck="false" aria-autocomplete="list" role="combobox" aria-expanded="false" aria-controls="users-select-listbox" aria-labelledby="users-select-trigger" />
                </header>

                <div role="listbox" id="users-select-listbox" aria-orientation="vertical" aria-labelledby="users-select-trigger">
                  <div role="group" aria-labelledby="group-label-select-items-1">
                    <div role="heading" id="group-label-select-items-1">Usuarios</div>

                    <c:forEach items="${users}" var="user">
                      <c:choose>
                        <c:when test="${security.hasRole('ESTUDIANTE')}">
                          <c:if test="${user.email() == security.getUsername()}">
                            <div id="select-items-1-${user.id()}" role="option" data-value="${user.id()}">
                              ${user.fullName()}
                            </div>
                          </c:if>
                        </c:when>
                        <c:otherwise>
                          <div id="select-items-1-${user.id()}" role="option" data-value="${user.id()}">
                            ${user.fullName()}
                          </div>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </div>
                </div>
              </div>
              <input type="hidden" name="userId" value="" id="userId" />
            </div>
            <p id="userId-error" class="text-red-500 hidden mt-1"></p>
          </div>
          <div class="space-y-2">
            <label for="professionalId" class="label required">Profesional:</label>
            <div id="professionals-select" class="select w-full">
              <button type="button" class="btn-outline justify-between font-normal w-full" id="professionals-select-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="professionals-select-listbox">
                <span class="truncate"></span>

                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                  <path d="m7 15 5 5 5-5" />
                  <path d="m7 9 5-5 5 5" />
                </svg>
              </button>
              <div id="professionals-select-popover" data-popover aria-hidden="true">
                <header>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search">
                    <circle cx="11" cy="11" r="8" />
                    <path d="m21 21-4.3-4.3" />
                  </svg>
                  <input type="text" value="" placeholder="Buscar..." autocomplete="off" autocorrect="off" spellcheck="false" aria-autocomplete="list" role="combobox" aria-expanded="false" aria-controls="professionals-select-listbox" aria-labelledby="professionals-select-trigger" />
                </header>

                <div role="listbox" id="professionals-select-listbox" aria-orientation="vertical" aria-labelledby="professionals-select-trigger">
                  <div role="group" aria-labelledby="group-label-select-items-1">
                    <div role="heading" id="group-label-select-items-1">Profesionales</div>

                    <c:forEach items="${professionals}" var="professional">
                      <div id="select-items-1-${professional.id()}" role="option" data-value="${professional.id()}">
                          ${professional.fullName()}
                      </div>
                    </c:forEach>
                  </div>
                </div>
              </div>
              <input type="hidden" name="professionalId" value="" id="professionalId" />
            </div>
            <p id="professionalId-error" class="text-red-500 hidden mt-1"></p>
          </div>
        </div>
        <div class="grid grid-cols-2 gap-2 mb-4">
          <div class="space-y-2">
            <label for="appointmentDate" class="label required">Fecha:</label>
            <input type="date" id="appointmentDate" name="appointmentDate" class="input" value=""
                   min="<%= todaysDate.plusDays(1).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE) %>"
                   max="<%= todaysDate.plusDays(90).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE) %>"
            />
            <p id="appointmentDate-error" class="text-red-500 hidden mt-1"></p>
          </div>
          <div class="space-y-2">
            <label for="appointmentState" class="label">Estado:</label>
            <div id="appointment-state-select" class="select w-full">
              <c:if test="${security.hasRole('ESTUDIANTE')}">
                <button type="button" class="btn-outline justify-between font-normal w-full" id="appointment-state-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="appointment-state-listbox" disabled="disabled">
                  <span class="truncate"></span>

                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                    <path d="m7 15 5 5 5-5" />
                    <path d="m7 9 5-5 5 5" />
                  </svg>
                </button>
              </c:if>
              <c:if test="${security.hasRole('ADMIN')}">
                <button type="button" class="btn-outline justify-between font-normal w-full" id="appointment-state-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="appointment-state-listbox">
                  <span class="truncate"></span>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                    <path d="m7 15 5 5 5-5" />
                    <path d="m7 9 5-5 5 5" />
                  </svg>
                </button>
              </c:if>
              <div id="appointment-state-popover" data-popover aria-hidden="true">
                <header>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search">
                    <circle cx="11" cy="11" r="8" />
                    <path d="m21 21-4.3-4.3" />
                  </svg>
                  <input type="text" value="" placeholder="Buscar..." autocomplete="off" autocorrect="off" spellcheck="false" aria-autocomplete="list" role="combobox" aria-expanded="false" aria-controls="appointment-state-listbox" aria-labelledby="appointment-state-trigger" />
                </header>

                <div role="listbox" id="appointment-state-listbox" aria-orientation="vertical" aria-labelledby="appointment-state-trigger">
                  <div role="group" aria-labelledby="group-label-select-items-1">
                    <div role="heading" id="group-label-select-items-1">Estado</div>

                    <c:forEach items="${appointmentStates}" var="appointmentState">
                      <c:choose>
                        <c:when test="${security.hasRole('ESTUDIANTE')}">
                          <c:if test="${appointmentState['value'] == 'PENDIENTE'}">
                            <div id="select-items-1-${appointmentState['value']}" role="option" data-value="${appointmentState['value']}" data-disabled="true" disabled>
                              ${appointmentState['translation']}
                            </div>
                          </c:if>
                        </c:when>
                        <c:otherwise>
                          <div id="select-items-1-${appointmentState['value']}" role="option" data-value="${appointmentState['value']}">
                            ${appointmentState['translation']}
                          </div>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </div>
                </div>
              </div>
              <input type="hidden" name="appointmentState" value="" id="appointmentState" />
            </div>
            <p id="appointmentState-error" class="text-red-500 hidden mt-1"></p>
          </div>
        </div>
        <div class="grid grid-cols-2 gap-2 mb-4">
          <div class="space-y-2">
            <label for="appointmentStartTime" class="label required">Hora Inicio:</label>
            <input type="time" id="appointmentStartTime" name="appointmentStartTime" class="input" min="07:00" max="18:00"/>
            <p id="appointmentStartTime-error" class="text-red-500 hidden mt-1"></p>
          </div>
          <div class="space-y-2">
            <label for="appointmentEndTime" class="label required">Hora Fin:</label>
            <input type="time" id="appointmentEndTime" name="appointmentEndTime" class="input" min="07:00" max="18:00"/>
            <p id="appointmentEndTime-error" class="text-red-500 hidden mt-1"></p>
          </div>
        </div>
        <div class="grid gap-2 mb-4">
          <label for="appointmentDescription" class="label required">Motivo:</label>
          <textarea id="appointmentDescription" name="appointmentDescription" class="dark:bg-dark-900 shadow-theme-xs focus:border-brand-300 focus:ring-brand-500/10 dark:focus:border-brand-800 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 placeholder:text-gray-400 focus:ring-3 focus:outline-hidden dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30" placeholder="Describa el motivo de la consulta" rows="6" required></textarea>
          <p id="appointmentDescription-error" class="text-red-500 hidden mt-1"></p>
        </div>
        <input type="hidden" name="userId" id="userId" value="" />
        <input type="hidden" name="action" id="action" value="create" />
        <p id="general-error" class="text-red-500 hidden mt-1"></p>
      </div>

      <footer class="flex justify-end gap-2">
        <button type="button" class="btn-secondary" id="cancel-appointment-button">Cancelar</button>
        <button type="submit" class="btn-primary" id="save-appointment-button">Guardar</button>
      </footer>
    </form>
  </article>
</dialog>
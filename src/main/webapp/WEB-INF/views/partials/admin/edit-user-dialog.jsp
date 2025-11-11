<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Edit user dialog -->
<dialog id="uf-edit-user-dialog" class="dialog" aria-labelledby="uf-edit-user-dialog-title"
        aria-describedby="uf-edit-user-dialog-description">
  <article>
    <header>
      <h2 id="alert-dialog-title">Editar usuario</h2>
      <p id="alert-dialog-description">
        Edite el nombre, apellido, email, rol y habilite o deshabilite el acceso del usuario.
      </p>
    </header>
    <form method="post" id="update-user-form" >
      <div class="flex flex-col gap-2">
        <div class="grid grid-cols-2 gap-2 mb-4 gap-y-4 gap-x-4">
          <div class="space-y-2">
            <label for="edit-firstName" class="label required">Nombre:</label>
            <input type="text" id="edit-firstName" name="edit-firstName" class="input" placeholder="Ingrese el nombre" required />
            <p class="text-sm text-red-600 mt-1 hidden" id="edit-firstNameError"></p>
          </div>
          <div class="space-y-2">
            <label for="edit-lastName" class="label required">Apellido:</label>
            <input type="text" id="edit-lastName" name="edit-lastName" class="input" placeholder="Ingrese el apellido" required />
            <p class="text-sm text-red-600 mt-1 hidden" id="edit-lastNameError"></p>
          </div>
        </div>
        <div class="grid gap-2 mb-4">
          <label for="edit-email" class="label required">Email:</label>
          <input type="email" id="edit-email" name="edit-email" class="input" placeholder="usuario@utec.edu.sv" required />
          <p class="text-sm text-red-600 mt-1 hidden" id="edit-emailError"></p>
        </div>
        <div class="grid gap-2 mb-4 col-span-2">
          <label for="edit-roleId" class="label required">Rol:</label>
          <div id="role-select-edit" class="select">
            <button
                type="button"
                id="role-select-edit-trigger"
                class="btn-outline justify-between font-normal w-full"
                aria-haspopup="listbox"
                aria-expanded="false"
                aria-controls="role-select-edit-listbox">
                                <span class="flex items-center gap-2">
                                  <i data-feather="user-check" class="w-4 h-4"></i>
                                  <span class="truncate">Seleccione un rol</span>
                                </span>
            </button>
            <div id="role-select-edit-popover" data-popover aria-hidden="true">
              <div role="listbox" id="role-select-edit-listbox" aria-orientation="vertical" aria-labelledby="role-select-edit-trigger">
                <c:forEach var="role" items="${roles}">
                  <div role="option" id="role-select-edit-item-${role.id()}" data-value="${role.id()}">
                      ${role.name()}
                  </div>
                </c:forEach>
              </div>
            </div>
            <input type="hidden" name="edit-roleId" id="edit-roleId" value="" />
          </div>
          <p class="text-sm text-red-600 mt-1 hidden" id="edit-roleError"></p>
        </div>
        <div class="grid grid-cols-2 gap-2 mb-4">
          <div class="space-y-2">
            <label for="edit-password" class="label">Nueva contraseña (temporal):</label>
            <div class="relative">
              <input type="password" id="edit-password" name="edit-password" class="input pr-10"
                     placeholder="Ingrese una contraseña temporal" />
              <button type="button" id="togglePasswordVisibilityButton"
                      class="absolute top-1/2 right-3 -translate-y-1/2 text-gray-500 hover:text-gray-700">
                <i data-feather="eye" id="eyeOnIcon" class="w-5 h-5"></i>
                <i data-feather="eye-off" id="eyeOffIcon" class="w-5 h-5 hidden"></i>
              </button>
            </div>
            <p class="text-sm text-red-600 mt-1 hidden" id="edit-passwordError"></p>
          </div>
          <div class="space-y-2 flex items-center justify-center mt-6">
            <label class="label">
              <input type="checkbox" name="edit-isActive" role="switch" class="input" id="edit-isActive" />
              Habilitar acceso
            </label>
          </div>
        </div>
        <p class="text-sm text-red-600 mt-1 hidden" id="edit-generalError"></p>
        <input type="hidden" name="action" id="action" value="edit" />
        <input type="hidden" name="resource" id="resource" value="user" />
        <input type="hidden" name="edit-user-id" id="edit-user-id" value="" />
      </div>

      <footer class="flex justify-end gap-2">
        <button type="button" class="btn-secondary" id="update-user-cancel-button">Cancelar</button>
        <button type="submit" class="btn-primary" id="update-user-submit-button">
          <svg id="loader" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" role="status" aria-label="Loading" class="animate-spin w-5 h-5 hidden"><path d="M21 12a9 9 0 1 1-6.219-8.56" /></svg>
          Guardar
        </button>
      </footer>
    </form>
  </article>
</dialog>
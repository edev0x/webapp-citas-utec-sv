<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<dialog id="professional-edit-dialog" class="dialog" aria-labelledby="professional-edit-dialog-title"
        aria-describedby="professional-edit-dialog-description">
  <article>
    <header>
      <h2 id="alert-dialog-title">Editar Profesional</h2>
      <p id="alert-dialog-description">
        Modifique los datos del profesional.
      </p>
    </header>
    <form method="post" id="professional-edit-form">
      <div class="flex flex-col gap-2">
        <div class="grid grid-cols-2 gap-2 mb-4">
          <div class="space-y-2">
            <label for="professional-fullName-edit" class="label required">Nombre Completo:</label>
            <input type="text" id="professional-fullName-edit" name="professional-fullName-edit" class="input" value="" placeholder="Ingrese el nombre completo" />
            <p id="professional-fullName-edit-error" class="text-red-500 hidden mt-1"></p>
          </div>
          <div class="space-y-2">
            <label for="specialtyId-edit" class="label required">Especialidad:</label>
            <div id="specialties-edit-select" class="select w-full">
              <button type="button" class="btn-outline justify-between font-normal w-full" id="specialties-select-trigger" aria-haspopup="listbox" aria-expanded="false" aria-controls="specialties-select-listbox">
                <span class="truncate"></span>

                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevrons-up-down-icon lucide-chevrons-up-down text-muted-foreground opacity-50 shrink-0">
                  <path d="m7 15 5 5 5-5" />
                  <path d="m7 9 5-5 5 5" />
                </svg>
              </button>
              <div id="specialties-select-popover" data-popover aria-hidden="true">
                <header>
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search">
                    <circle cx="11" cy="11" r="8" />
                    <path d="m21 21-4.3-4.3" />
                  </svg>
                  <input type="text" value="" placeholder="Buscar..." autocomplete="off" autocorrect="off" spellcheck="false" aria-autocomplete="list" role="combobox" aria-expanded="false" aria-controls="specialties-select-listbox" aria-labelledby="specialties-select-trigger" />
                </header>

                <div role="listbox" id="specialties-select-listbox" aria-orientation="vertical" aria-labelledby="specialties-select-trigger">
                  <div role="group" aria-labelledby="group-label-select-items-1">
                    <div role="heading" id="group-label-select-items-1">Especialidad: </div>
                    <div id="select-items-1-psychology" role="option" data-value="PSICOLOGIA">Psicología</div>
                    <div id="select-items-1-dentistry" role="option" data-value="GESTION_EMPRESARIAL">Gestión Empresarial</div>
                    <div id="select-items-1-physiotherapy" role="option" data-value="FISIOTERAPIA">Fisioterapia</div>
                    <div id="select-items-1-nutrition" role="option" data-value="NUTRICION">Nutrición</div>
                    <div id="select-items-1-odontology" role="option" data-value="PSIQUIATRIA">Psiquiatría</div>
                    <div id="select-items-1-cardiology" role="option" data-value="INTERNISTA">Medicina Interna</div>
                  </div>
                </div>
              </div>
              <input type="hidden" name="specialtyId-edit" value="" id="specialtyId-edit" />
            </div>
            <p id="professional-specialtyId-edit-error" class="text-red-500 hidden mt-1"></p>
          </div>
        </div>
        <div class="grid gap-2 mb-4">
          <div class="space-y-2">
            <label for="professional-email-edit" class="label required">Correo electr&oacute;nico:</label>
            <input type="email" id="professional-email-edit" name="professional-email-edit" class="input" value="" placeholder="email@utec.edu.sv" />
            <p id="professional-email-edit-error" class="text-red-500 hidden mt-1"></p>
          </div>
        </div>
        <div class="grid gap-2 mb-4">
          <div class="gap-2 flex flex-row items-start justify-between rounded-lg border p-4 shadow-xs">
            <div class="flex flex-col gap-0.5">
              <label for="demo-form-switch" class="leading-normal">Habilitar</label>
              <p class="text-muted-foreground text-sm">Marque esta opción para habilitar al profesional</p>
            </div>
            <input type="checkbox" name="professional-isActive-edit" role="switch" class="input" id="professional-isActive-edit" />
          </div>
        </div>
        <p id="general-error-edit" class="text-red-500 hidden mt-1"></p>
        <input type="hidden" id="professional-id-edit" name="professional-id-edit" value="" />
      </div>

      <footer class="flex justify-end gap-2">
        <button type="button" class="btn-secondary" id="cancel-professional-edit-button">Cancelar</button>
        <button type="submit" class="btn-primary" id="save-professional-edit-button">
            <i id="professional-edit-loader" class="w-4 h-4 mr-2 animate-spin hidden" data-feather="loader"></i>
            Guardar
        </button>
      </footer>
    </form>
  </article>
</dialog>
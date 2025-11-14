import { defaultJsonRequestHeaders, getBaseUrl } from "../components/utils";

function createProfessional() {
    const professionalCreationDialog = document.getElementById(
      "professional-creation-dialog"
    );
    const professionalCreationForm = document.getElementById('professional-creation-form');
    const isActiveInput = document.getElementById("professional-isActive");
    const submitButton = document.getElementById('save-professional-button');
    const spinner = document.getElementById('delete-professional-loader'); 
    const specialtyListBox = document.getElementById(
      "specialties-select-listbox"
    );

    const openDialogButton = document.getElementById(
      "open-professional-creation-dialog-button"
    );

    const closeDialogButton = document.getElementById(
      "cancel-professional-create-button"
    );

    openDialogButton.addEventListener("click", () => {
        professionalCreationDialog.showModal();
    });

    closeDialogButton.addEventListener("click", () => {
        professionalCreationForm.reset();
        submitButton.disabled = false;
        submitButton.textContent = 'Guardar';
        spinner.classList.add("hidden");
        professionalCreationDialog.close();
    });

    professionalCreationForm.addEventListener('submit',  (event) => {
        if(!event) return;
        event.preventDefault();

        try {
            const formData = new FormData(event.target);

            const specialtyValue = specialtyListBox.querySelector('[aria-selected="true"]')?.textContent;

        
            const payload = {
              fullName: formData.get("professional-fullName"),
              email: formData.get("professional-email"),
              isActive: isActiveInput.checked,
              specialty: specialtyValue?.trim() || "SIN ESPECIALIDAD",
            };
            
            const jsonPayload = JSON.stringify(payload);

            closeDialogButton.disabled = true;
            submitButton.disabled = true;
            submitButton.textContent = "Guardando...";
            spinner.classList.remove("hidden");

            fetch(`${getBaseUrl()}/api/professionals`, {
                method: 'POST',
                headers: defaultJsonRequestHeaders,
                body: jsonPayload
            })
            .then(async response => {
                if (response.ok) {
                    toastr.success("Profesional creado exitosamente.");
                    professionalCreationForm.reset();
                    professionalCreationDialog.close();
                    setTimeout(() => {
                        window.location.reload();
                    }, 1500);
                } else {
                    const data = await response.json();

                    if (data.validationErrors) {

                        Object.keys(data.validationErrors).forEach(field => {
                            const errorElement = document.getElementById(`professional-${field}-error`);
                            if (errorElement) {
                                errorElement.textContent = data.validationErrors[field];
                                errorElement.classList.remove('hidden');
                            }
                        });
                    } else if (data.message) {
                        toastr.error(data.message);
                    } else {
                        toastr.error("Ocurrió un error al crear el profesional. Por favor, intente de nuevo.");
                    }
                }
            });
        } catch (error) {
            console.error("Error creating professional:", error);
            toastr.error("Ocurrió un error inesperado al crear el profesional. Por favor, intente de nuevo.");
        } finally {
            resetProfessionalForm();
        }
    });
}


function resetProfessionalForm() {
    const professionalCreationForm = document.getElementById('professional-creation-form');
    const submitButton = document.getElementById('save-professional-button');
    const closeDialogButton = document.getElementById(
      "cancel-professional-create-button"
    );
    
    closeDialogButton.disabled = false;
    submitButton.disabled = false;
    submitButton.textContent = 'Guardar';
    spinner.classList.add("hidden");

    const errorElements = professionalCreationForm.getElementsByTagName('p');
    [...errorElements].forEach(element => {
        if (element.id.includes('-error') && !element.classList.contains('hidden')) {
            element.classList.add('hidden');
            element.textContent = '';
        }
    });
}

document.addEventListener('DOMContentLoaded', createProfessional);
import { defaultJsonRequestHeaders, getBaseUrl } from "../components/utils";

function updateProfessional() {
  const professionalEditDialog = document.getElementById(
    "professional-edit-dialog"
  );
  const professionalEditForm = document.getElementById(
    "professional-edit-form"
  );
  const isActiveInput = document.getElementById("professional-isActive-edit");
  const submitButton = document.getElementById("save-professional-edit-button");
  const spinner = document.getElementById("professional-edit-loader");
  const specialtiesSelect = document.getElementById("specialties-edit-select");

  const specialtyListBox = specialtiesSelect.querySelector(
    "#specialties-select-listbox"
  );

  const openDialogButton = document.querySelectorAll(
    "#open-professional-edit-dialog-button"
  );

  const closeDialogButton = document.getElementById(
    "cancel-professional-edit-button"
  );

  openDialogButton.forEach((button) => {
    button.addEventListener("click", () => {
      const closestRow = button.closest("tr");

      if (!closestRow) return;

      const dataToEdit = {
        id: closestRow.dataset.id,
        fullName: closestRow.dataset.name,
        email: closestRow.dataset.email,
        isActive: closestRow.dataset.isActive === "true",
        specialty: closestRow.dataset.specialty,
      };

      professionalEditForm["professional-id-edit"].value = dataToEdit.id;
      professionalEditForm["professional-fullName-edit"].value =
        dataToEdit.fullName;
      professionalEditForm["professional-email-edit"].value = dataToEdit.email;
      isActiveInput.checked = dataToEdit.isActive;

      professionalEditDialog.showModal();
    });
  });

  closeDialogButton.addEventListener("click", () => {
    professionalEditForm.reset();
    submitButton.disabled = false;
    submitButton.textContent = "Guardar";
    spinner.classList.add("hidden");
    professionalEditDialog.close();
  });

  professionalEditForm.addEventListener("submit", (event) => {
    if (!event) return;
    event.preventDefault();

    try {
      const formData = new FormData(event.target);

      let specialtyValue = specialtyListBox.querySelector(
        '[aria-selected="true"]'
      )?.textContent;

      const payload = {
        id: formData.get("professional-id-edit"),
        fullName: formData.get("professional-fullName-edit"),
        email: formData.get("professional-email-edit"),
        isActive: isActiveInput.checked,
        specialty: specialtyValue?.trim() || "SIN ESPECIALIDAD",
      };

      const jsonPayload = JSON.stringify(payload);

      closeDialogButton.disabled = true;
      submitButton.disabled = true;
      submitButton.textContent = "Guardando...";
      spinner.classList.remove("hidden");

      fetch(`${getBaseUrl()}/api/professionals`, {
        method: "PUT",
        headers: defaultJsonRequestHeaders,
        body: jsonPayload,
      }).then(async (response) => {
        if (response.ok) {
          toastr.success("Profesional creado exitosamente.");
          professionalEditForm.reset();
          professionalEditDialog.close();
          setTimeout(() => {
            window.location.reload();
          }, 1500);
        } else {
          const data = await response.json();

          if (data.validationErrors) {
            Object.keys(data.validationErrors).forEach((field) => {
              const errorElement = document.getElementById(
                `professional-${field}-edit-error`
              );
              if (errorElement) {
                errorElement.textContent = data.validationErrors[field];
                errorElement.classList.remove("hidden");
              }
            });
          } else if (data.message) {
            toastr.error(data.message);
          } else {
            toastr.error(
              "Ocurrió un error al crear el profesional. Por favor, intente de nuevo."
            );
          }
        }
      });
    } catch (error) {
      console.error("Error creating professional:", error);
      toastr.error(
        "Ocurrió un error inesperado al crear el profesional. Por favor, intente de nuevo."
      );
    } finally {
      resetProfessionalForm();
    }
  });
}

function resetProfessionalForm() {
  const professionalEditForm = document.getElementById(
    "professional-edit-form"
  );
  const submitButton = document.getElementById("save-professional-edit-button");
  const closeDialogButton = document.getElementById(
    "cancel-professional-edit-button"
  );

  closeDialogButton.disabled = false;
  submitButton.disabled = false;
  submitButton.textContent = "Guardar";
  spinner.classList.add("hidden");

  const errorElements = professionalEditForm.getElementsByTagName("p");
  [...errorElements].forEach((element) => {
    if (
      element.id.includes("-error-edit") &&
      !element.classList.contains("hidden")
    ) {
      element.classList.add("hidden");
      element.textContent = "";
    }
  });
}

document.addEventListener("DOMContentLoaded", updateProfessional);

import {
  getBaseUrl,
  defaultJsonRequestHeaders,
  cleanUpFormErrors
} from "../components/utils";

function createRole() {
  const createRoleDialog = document.getElementById("create-role-dialog");
  if (!createRoleDialog) return;

  const createRoleForm = document.getElementById("create-role-form");
  const cancelButton = document.getElementById("cancel-create-role-btn");
  const submitButton = document.getElementById("create-role-submit-btn");
  const loaderIcon = document.getElementById("create-role-loader-icon");

  cancelButton.addEventListener("click", function () {
    createRoleDialog.close();
    createRoleForm.reset();
    loaderIcon.classList.add("hidden");
  });

  createRoleForm.addEventListener("submit", function (event) {
    event.preventDefault();

    try {
      submitButton.disabled = true;
      cancelButton.disabled = true;
      submitButton.textContent = "Por favor espere...";
      loaderIcon.classList.remove("hidden");

      const formData = new FormData(event.target);
      const payload = {
        name: formData.get("roleName"),
      };


    } catch (error) {
      console.error("Error creating role:", error);
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = "Guardar";
      cancelButton.disabled = false;
      cleanUpFormErrors();
    }
  })
}
import {decodeHtml, defaultJsonRequestHeaders} from "../components/utils";
import {getBaseUrl} from "../components/utils";

function updateUser(btn = null) {
  const updateUserForm = document.getElementById("update-user-form");

  if (!updateUserForm) return;

  const editIdInput = document.getElementById("edit-user-id");
  const editFirstNameInput = document.getElementById("edit-firstName");
  const editLastNameInput = document.getElementById("edit-lastName");
  const editEmailInput = document.getElementById("edit-email");
  const editRoleSelect = document.getElementById("edit-roleId");
  const editIsActiveCheckbox = document.getElementById("edit-isActive");
  // const editPasswordInput = document.getElementById("edit-password");
  const spinner = document.getElementById("loader");
  const submitButton = document.getElementById("update-user-submit-button");
  const cancelButton = document.getElementById("update-user-cancel-button");
  const editUserDialog = document.getElementById("uf-edit-user-dialog");


  updateUserForm.addEventListener("submit", function (event) {
    if (!event) return;

    event.preventDefault();

    if (isUndefinedOrEmpty(editFirstNameInput.value) || isUndefinedOrEmpty(editIdInput.value) || isUndefinedOrEmpty(editLastNameInput.value) || isUndefinedOrEmpty(editEmailInput.value) || isUndefinedOrEmpty(editRoleSelect.value)) {
      return;
    }

    try {
      const formData = new FormData(event.target);
      const updateUserPayload = {
        id: formData.get("edit-user-id"),
        firstName: formData.get("edit-firstName"),
        lastName: formData.get("edit-lastName"),
        email: formData.get("edit-email"),
        password: formData.get("edit-password"),
        isActive: editIsActiveCheckbox.checked,
        role: {
          id: parseInt(formData.get("edit-roleId")),
        },
      };

      const jsonPayload = JSON.stringify(updateUserPayload);

      submitButton.disabled = true;
      spinner.classList.remove("hidden");
      submitButton.textContent = "Por favor espere...";

      fetch(`${getBaseUrl()}/api/users`, {
        method: "PUT",
        headers: defaultJsonRequestHeaders,
        body: jsonPayload,
      }).then(async (res) => {
        const data = await res.json();

        if (res.ok) {
          editUserDialog.close();
          toastr.success("Usuario actualizado exitosamente.");
          setTimeout(() => {
            updateUserForm.reset();
            window.location.reload();
          }, 1000);
        } else {
          if (data.validationErrors) {
            Object.entries(data.validationErrors).forEach(
              ([field, message]) => {
                const errorElement = document.getElementById(
                  `edit-${field}Error`
                );
                if (errorElement) {
                  errorElement.textContent = decodeHtml(message);
                  errorElement.classList.remove("hidden");
                }
              }
            )
          }
        }
      })

    } catch (error) {
      console.error("Error updating user:", error);
      toastr.error("Ha ocurrido un error al procesar la solicitud.");
    } finally {
      submitButton.disabled = false;
      spinner.classList.add("hidden");
      submitButton.textContent = "Guardar";
      [
        "firstName",
        "lastName",
        "email",
        "password",
        "roleId",
        "resourceExists",
      ].forEach((field) => {
        const errorElement = document.getElementById(`edit-${field}Error`);
        if (errorElement) {
          errorElement.classList.add("hidden");
          errorElement.textContent = "";
        }
      })
    }

  })

  cancelButton.addEventListener("click", function () {
    editUserDialog.close();
    updateUserForm.reset();
    submitButton.disabled = false;
    spinner.classList.add("hidden");
  });

  const editDialogTriggers = document.querySelectorAll("#edit-user-trigger");

  [...editDialogTriggers].forEach(trigger => {
    trigger.addEventListener("click", function() {
      const userData = extractUserDataFromRow(trigger.closest("tr"));
      populateEditForm(userData);
      editUserDialog.showModal();
    });
  });

  function extractUserDataFromRow(row) {
    return {
      id: row.dataset.id,
      firstName: row.dataset.firstname,
      lastName: row.dataset.lastname,
      email: row.dataset.email,
      roleId: row.dataset.roleid,
      isActive: row.dataset.isactive
    };
  }

  function populateEditForm(userData) {
    editIdInput.value = userData.id;
    editFirstNameInput.value = userData.firstName;
    editLastNameInput.value = userData.lastName;
    editEmailInput.value = userData.email;
    editRoleSelect.value = userData.roleId;
    editIsActiveCheckbox.checked = userData.isActive === "true";
  }
}

function isUndefinedOrEmpty(value) {
  return value === undefined || value === null || value === "";
}

document.addEventListener("DOMContentLoaded", updateUser);
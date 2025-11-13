import {getBaseUrl, defaultJsonRequestHeaders, decodeHtml} from "../components/utils";

function createUser() {
  const createUserForm = document.getElementById("createUserForm");

  if (!createUserForm) {
    console.debug("Create user form not found in DOM");
    return;
  }

  const firstNameInput = document.getElementById("firstName");
  const lastNameInput = document.getElementById("lastName");
  const passwordInput = document.getElementById("password");
  const emailInput = document.getElementById("email");
  const roleSelect = document.getElementById("roleId");
  const spinner = createUserForm.querySelector('[aria-label="Loading"]');
  const submitButton = createUserForm.querySelector('button[type="submit"]');
  const cancelButton = createUserForm.querySelector("#cancelButton");
  const isActiveCheckbox = document.getElementById("isActive");
  const createUserDialog = document.getElementById("uf-create-user-dialog");
  const generalErrorElement = document.getElementById("generalError");

  cancelButton.addEventListener("click", function () {
    createUserDialog.close();
    createUserForm.reset();
    submitButton.disabled = false;
    spinner.classList.add("hidden");
  });

  createUserForm.addEventListener("submit", function (event) {
    if (!event) return;
    event.preventDefault();

    if (
      firstNameInput.value === "" ||
      lastNameInput.value === "" ||
      passwordInput.value === "" ||
      emailInput.value === "" ||
      roleSelect.value === ""
    ) {
      return;
    }

    try {
      const formData = new FormData(event.target);
      const payload = {
        firstName: formData.get("firstName"),
        lastName: formData.get("lastName"),
        email: formData.get("email"),
        password: formData.get("password"),
        isActive: isActiveCheckbox.checked,
        role: {
          id: parseInt(formData.get("roleId")),
        },
      };

      const jsonPayload = JSON.stringify(payload);

      submitButton.disabled = true;
      spinner.classList.remove("hidden");
      submitButton.textContent = "Por favor espere...";

      fetch(`${getBaseUrl()}/api/users`, {
        method: "POST",
        headers: defaultJsonRequestHeaders,
        body: jsonPayload,
      }).then(async (res) => {
        if (res.ok) {
          createUserForm.reset();
          createUserDialog.close();
          toastr.success("Usuario creado exitosamente.");
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          const data = await res.json();

          if (data.validationErrors) {
            if (data.validationErrors.resourceExists) {
              generalErrorElement.textContent = decodeHtml(data.validationErrors.resourceExists);
              generalErrorElement.classList.remove("hidden");
            }

            Object.entries(data.validationErrors).forEach(
              ([field, message]) => {
                const errorElement = document.getElementById(`${field}Error`);
                if (errorElement) {
                  errorElement.textContent = message;
                  errorElement.classList.remove("hidden");
                }
              }
            )
          }
        }
      });
    } catch (error) {
      console.error("Error creating user:", error);
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
        const errorElement = document.getElementById(`${field}Error`);
        if (errorElement) {
          errorElement.classList.add("hidden");
          errorElement.textContent = "";
        }
      })
    }
  })
}

document.addEventListener("DOMContentLoaded", createUser);


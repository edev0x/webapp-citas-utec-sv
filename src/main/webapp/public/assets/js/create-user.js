import { decodeHtml } from "./utils.js";

function togglePasswordVisibility() {
  const passwordInput = document.getElementById("password");
  const eyeOnIcon = document.getElementById("eyeOnIcon");
  const eyeOffIcon = document.getElementById("eyeOffIcon");
  const togglePasswordVisibilityButton = document.getElementById(
    "togglePasswordVisibilityButton"
  );

  togglePasswordVisibilityButton.addEventListener("click", function () {
    const type = passwordInput.type === "password" ? "text" : "password";
    passwordInput.type = type;

    eyeOnIcon.classList.toggle("hidden");
    eyeOffIcon.classList.toggle("hidden");
  });
}

function createUser() {
  const createUserForm = document.getElementById("createUserForm");
  const firstNameInput = document.getElementById("firstName");
  const lastNameInput = document.getElementById("lastName");
  const passwordInput = document.getElementById("password");
  const emailInput = document.getElementById("email");
  const roleSelect = document.getElementById("roleId");
  const spinner = createUserForm.querySelector('[aria-label="Loading"]');
  const submitButton = createUserForm.querySelector('button[type="submit"]');
  const cancelButton = createUserForm.querySelector("#cancelButton");
  const isActiveCheckbox = document.getElementById("isActive");

  cancelButton.addEventListener("click", function () {
    document.getElementById("uf-create-user-dialog").close();

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

    try {
      submitButton.disabled = true;
      spinner.classList.remove("hidden");
      submitButton.textContent = "Por favor espere...";

      const origin = window.location.origin; // e.g. "https://example.com"
      const contextPath = window.location.pathname.split("/")[1]; // first part after '/'
      const baseUrl = `${origin}/${contextPath}`;

      const response = fetch(`${baseUrl}/api/users`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      response
        .then(async (res) => {
          const data = await res.json();

          if (res.ok) {
            document.getElementById("uf-create-user-dialog").close();

            setTimeout(() => {
              document.dispatchEvent(
                new CustomEvent("basecoat:toast", {
                  detail: {
                    config: {
                      category: "success",
                      description: "Usuario creado exitosamente.",
                      title: "Éxito",
                      cancel: {
                        label: "Cerrar",
                      },
                    },
                  },
                })
              );
            }, 300);

            createUserForm.reset();

            window.location.reload();
          } else {
            if (data.validationErrors) {
              Object.entries(data.validationErrors).forEach(
                ([field, message]) => {
                  const errorElement = document.getElementById(`${field}Error`);
                  if (errorElement) {
                    errorElement.textContent = decodeHtml(message);
                    errorElement.classList.remove("hidden");
                  }
                }
              );
            } else {
              const generalErrorElement =
                document.getElementById("generalError");
              if (generalErrorElement) {
                generalErrorElement.textContent =
                  decodeHtml(data.resourceExists) ||
                  "Ha ocurrido un error al procesar la solicitud.";
                generalErrorElement.classList.remove("hidden");
              }
            }
          }
        })
        .catch((error) => {
          document.dispatchEvent(
            new CustomEvent("basecoat:toast", {
              detail: {
                config: {
                  category: "error",
                  description: "Ha ocurrido un error al procesar la solicitud.",
                  title: "Error",
                  cancel: {
                    label: "Cerrar",
                  },
                },
              },
            })
          );

          console.error("Error creating user:", error);
          submitButton.disabled = false;
          spinner.classList.add("hidden");
        });
    } catch (error) {
      console.error("Error creating user:", error);
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
      });
    }
  });
}

export { togglePasswordVisibility, createUser };

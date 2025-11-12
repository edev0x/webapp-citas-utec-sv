import { getBaseUrl } from "../components/utils";

function deleteUser() {
  const deleteUserForm = document.getElementById("delete-user-form");

  if(!deleteUserForm) return;

  const deleteUserDialog = document.getElementById("uf-delete-user-dialog");
  const cancelDeleteUserBtn = document.getElementById("cancel-delete-user-btn");
  const submitDeleteUserBtn = document.getElementById("submit-delete-user-btn");
  const spinner = deleteUserForm.querySelector('#delete-user-loader-icon');

  if (!deleteUserDialog) {
    console.debug("Delete user dialog not found in DOM");
    return;
  }

  cancelDeleteUserBtn.addEventListener("click", function () {
    deleteUserDialog.close();
    deleteUserForm.reset();
    spinner.classList.add("hidden");
  });

  deleteUserForm.addEventListener("submit", function (event) {
    if (!event) return;

    event.preventDefault();

    const formData = new FormData(event.target);
    const userId = formData.get("userId");

    try {
      submitDeleteUserBtn.disabled = true;
      spinner.classList.remove("hidden");
      submitDeleteUserBtn.textContent = "Por favor espere...";

      fetch(`${getBaseUrl()}/api/users/${userId}`, {
        method: "DELETE",
      }).then(async (res) => {
        const data = await res.json();

        if (res.status === 204) {
          deleteUserDialog.close();
          toastr.success("Usuario eliminado exitosamente.");
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          toastr.error(data.message || "Ha ocurrido un error al procesar la solicitud.");
        }
      })
    } catch (error) {
      console.error("Error deleting user:", error);
      toastr.error("Ocurrio un error inesperado al procesar la solicitud, intentelo de nuevo más tarde.");
    } finally {
      submitDeleteUserBtn.disabled = false;
      spinner.classList.add("hidden");
      submitDeleteUserBtn.textContent = "Eliminar";
    }
  })
}

document.addEventListener("DOMContentLoaded", deleteUser);
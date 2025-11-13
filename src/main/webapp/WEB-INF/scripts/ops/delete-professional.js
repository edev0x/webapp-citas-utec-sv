import { getBaseUrl, defaultJsonRequestHeaders } from "../components/utils";

function deleteProfessional() {
  const deleteProfessionalDialog = document.getElementById(
    "delete-professional-dialog"
  );
  const confirmDeleteButton = document.getElementById(
    "delete-professional-submit-btn"
  );
  const cancelDeleteButton = document.getElementById(
    "cancel-delete-professional-btn"
  );
  const openDeleteDialogButtons = document.querySelectorAll(
    "#open-delete-professional-dialog-btn"
  );

  const deleteProfessionalForm = document.getElementById(
    "delete-professional-form"
  );

  const spinner = document.getElementById("delete-professional-loader");

  openDeleteDialogButtons.forEach(button => {
    button.addEventListener("click", () => {
      const closestRow = button.closest("tr");
      const professionalId = closestRow.getAttribute("data-id");

      deleteProfessionalForm["professionalId-delete"].value = professionalId;
      deleteProfessionalDialog.showModal();
    });
  });

  cancelDeleteButton.addEventListener("click", () => {
    confirmDeleteButton.disabled = false;
    confirmDeleteButton.textContent = "Eliminar";
    spinner.classList.add("hidden");
    deleteProfessionalDialog.close();
  });

  deleteProfessionalForm.addEventListener("submit", (event) => {
    if (!event) return;
    event.preventDefault();

    try {
      const formData = new FormData(event.target);
      const professionalId = formData.get("professionalId-delete");

      spinner.classList.remove("hidden");
      cancelDeleteButton.disabled = true;
      confirmDeleteButton.disabled = true;
      confirmDeleteButton.textContent = "Eliminando...";

      fetch(`${getBaseUrl()}/api/professionals?id=${professionalId}`, {
        method: "DELETE",
        headers: defaultJsonRequestHeaders,
      }).then(async (response) => {
        if (response.status === 204) {
          deleteProfessionalDialog.close();
          toastr.success("Profesional eliminado exitosamente.");
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          const data = await response.json();
          toastr.error(
            data.message || "Ha ocurrido un error al eliminar el profesional."
          );
        }
      });
    } catch (error) {
      console.error("Error during professional deletion:", error);
      toastr.error(
        "Ocurrió un error inesperado al eliminar el profesional, intentalo de nuevo."
      );
    } finally {
      cancelDeleteButton.disabled = false;
      confirmDeleteButton.disabled = false;
      confirmDeleteButton.textContent = "Eliminar";
      spinner.classList.add("hidden");
    }
  });
}

document.addEventListener("DOMContentLoaded", deleteProfessional);
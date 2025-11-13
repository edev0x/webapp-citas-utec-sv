import {defaultJsonRequestHeaders, getBaseUrl} from "../components/utils";

function deleteAppointment() {
  const deleteAppointmentDialog = document.getElementById("uf-delete-appointment-dialog");
  const cancelDeleteAppointmentBtn = document.getElementById("cancel-delete-appointment-btn");
  const submitDeleteAppointmentBtn = document.getElementById("delete-appointment-submit-btn");
  const deleteAppointmentForm = document.getElementById("delete-appointment-form");
  const loaderIcon = document.getElementById("delete-user-loader-icon");
  const editAppointmentDialog = document.getElementById("appointment-edit-dialog");

  cancelDeleteAppointmentBtn.addEventListener("click", function () {
    if (deleteAppointmentDialog) deleteAppointmentDialog.close();
  });

  deleteAppointmentForm.addEventListener("submit", function (event) {
    if (!event) return;
    event.preventDefault();

    try {
      const formData = new FormData(event.target);
      const id = formData.get("appointmentId-delete");

      cancelDeleteAppointmentBtn.disabled = true;
      submitDeleteAppointmentBtn.disabled = true;
      submitDeleteAppointmentBtn.textContent = "";
      loaderIcon.classList.remove("hidden");

      fetch(`${getBaseUrl()}/appointments?id=${id}`, {
        headers: defaultJsonRequestHeaders,
        method: "DELETE",
      }).then(async (res) => {
        if (res.status === 204) {
          deleteAppointmentDialog.close();
          editAppointmentDialog.close();
          setTimeout(() => {
            window.location.reload();
          }, 1000);
          toastr.success("Cita eliminada exitosamente.");
        } else {
          toastr.error("Ha ocurrido un error al eliminar la cita agendada.");
        }
      })

    } catch (error) {
      console.error("Error deleting appointment:", error);
      toastr.error("Ocurrió un error inesperado al eliminar la cita agendada, intentalo de nuevo.");
    } finally {
      cancelDeleteAppointmentBtn.disabled = false;
      submitDeleteAppointmentBtn.disabled = false;
      submitDeleteAppointmentBtn.textContent = "Eliminar";
      loaderIcon.classList.add("hidden");

    }
  })
}

document.addEventListener("DOMContentLoaded", deleteAppointment);
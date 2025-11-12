function deleteAppointment() {
  const deleteAppointmentDialog = document.getElementById("uf-delete-appointment-dialog");
  const cancelDeleteAppointmentBtn = document.getElementById("cancel-delete-appointment-btn");
  const deleteAppointmentForm = document.getElementById("delete-appointment-form");

  cancelDeleteAppointmentBtn.addEventListener("click", function () {
    if (deleteAppointmentDialog) deleteAppointmentDialog.close();
  });

  deleteAppointmentForm.addEventListener("submit", function (event) {
    if (!event) return;
    event.preventDefault();

    const formData = new FormData(event.target);
    const appointmentId = formData.get("appointmentId");

    console.log(appointmentId);
    console.log(`Event fired: delete appointment with id ${appointmentId}`)
  })
}

document.addEventListener("DOMContentLoaded", deleteAppointment);
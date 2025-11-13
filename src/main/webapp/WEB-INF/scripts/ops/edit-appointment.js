import {defaultJsonRequestHeaders, getBaseUrl} from "../components/utils";

function editAppointment() {
  const editAppointmentForm = document.getElementById("appointment-edit-form");

  if (!editAppointmentForm) return;

  const submitButton = document.getElementById("save-appointment-edit-button");
  const cancelButton = document.getElementById("cancel-appointment-edit-button");
  const deleteAppointmentButton = document.getElementById("delete-appointment-button");
  const deleteAppointmentDialog = document.getElementById("uf-delete-appointment-dialog");

  editAppointmentForm.addEventListener("submit", function (event) {
    if (!event) return;

    event.preventDefault();

    try {
      submitButton.disabled = true;
      submitButton.textContent = "Por favor espere...";

      const formData = new FormData(event.target);

      const payload = {
        id: Number(formData.get("appointmentId-edit")),
        appointmentDate: formData.get("appointmentDate-edit"),
        appointmentStartTime: formData.get("appointmentStartTime-edit"),
        appointmentEndTime: formData.get("appointmentEndTime-edit"),
        state: formData.get("appointmentState-edit"),
        reason: formData.get("appointmentDescription-edit"),
        userId: Number(formData.get("userId-edit")),
        professionalId: Number(formData.get("professionalId-edit"))
      };

      const jsonPayload = JSON.stringify(payload);

      fetch(`${getBaseUrl()}/appointments`, {
        method: "PUT",
        headers: defaultJsonRequestHeaders,
        body: jsonPayload
      }).then(async (res) => {
        const data = await res.json();

        if (res.status === 200) {
          document.getElementById("appointment-edit-dialog").close();
          toastr.success("Cita editada exitosamente.");
          setTimeout(() => {
            window.location.reload();
          }, 3000);
        } else {
          if (data.validationErrors) {
            console.log(data.validationErrors);
            Object.entries(data.validationErrors).forEach(([field, message]) => {
              const errorElement = document.getElementById(`${field}-error`);
              if (errorElement) {
                errorElement.textContent = message;
                errorElement.classList.remove("hidden");
              }
            });
          }
        }

      })


    } catch (error) {
      console.error("Error editing appointment:", error);
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = "Guardar";
      cleanUpErrorMessages();
    }
  });

  cancelButton.addEventListener("click", function () {
    document.getElementById("appointment-edit-dialog").close();
    editAppointmentForm.reset();
    submitButton.disabled = false;
    submitButton.textContent = "Guardar";
  })

  deleteAppointmentButton.addEventListener("click", function () {
    deleteAppointmentDialog.showModal();
  });
}

function cleanUpErrorMessages() {
  const errorElements = document.getElementsByTagName("p");
  [...errorElements].forEach(element => {
    if (element.id.includes("-error") && !element.classList.contains("hidden")) {
      element.classList.add("hidden");
      element.textContent = "";
    }
  })
}



document.addEventListener("DOMContentLoaded", editAppointment);
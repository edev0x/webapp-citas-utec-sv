import { Calendar } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import listPlugin from "@fullcalendar/list";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import esLocale from '@fullcalendar/core/locales/es';
import { getBaseUrl, setProfessionalDefaultValue, setDefaultUserValue, setDefaultAppointmentState } from "./utils";

const eventsColors = {
    PENDIENTE: "bg-gray-200 text-gray-600 border-gray-200",
    CANCELADA: "danger",
    SIN_ASISTENCIA: "danger",
    CONFIRMADA: "success",
    EN_ESPERA: "warning",
    REAGENDADA: "primary",
    SUSPENDIDA: "bg-gray-200 text-gray-600 border-gray-200"
}

document.addEventListener("DOMContentLoaded", function() {
    const calendarEl = document.getElementById("calendar");
    const editAppointmentForm = document.getElementById("appointment-edit-form");
    const userIdInput = document.getElementById("userId-edit");
    const professionalIdInput = document.getElementById("professionalId-edit");
    const appointmentDateInput = document.getElementById("appointmentDate-edit");
    const appointmentStartTimeInput = document.getElementById("appointmentStartTime-edit");
    const appointmentEndTimeInput = document.getElementById("appointmentEndTime-edit");
    const appointmentStateSelect = document.getElementById("appointmentState-edit");
    const appointmentReasonInput = document.getElementById("appointmentDescription-edit");
    const appointmentIdInput = document.getElementById("appointmentId-edit");

    if (calendarEl) {
        const calendarHeaderToolBar = {
            left: 'prev,next addEventButton',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        }

        const openAddEventModal = () => { document.getElementById('appointment-creation-dialog').showModal(); }
        const openEditEventModal = (event) => { document.getElementById('appointment-edit-dialog').showModal(); }

        const calendar = new Calendar(calendarEl, {
            hiddenDays: [0],
            businessHours: [
                {
                    daysOfWeek: [1, 2, 3, 4, 5],
                    startTime: '08:00',
                    endTime: '18:00'
                },
                {
                    daysOfWeek: [6],
                    startTime: '07:00',
                    endTime: '12:00'
                }
            ],
            eventTimeFormat: {
                hour: '2-digit',
                minute: '2-digit',
                meridiem: 'uppercase',
            },
            selectConstraint: "businessHours",
            eventConstraint: "businessHours",
            plugins: [dayGridPlugin, timeGridPlugin, listPlugin, interactionPlugin],
            selectable: true,
            initialView: "dayGridMonth",
            headerToolbar: calendarHeaderToolBar,
            customButtons: {
                addEventButton: {
                    text: 'Agendar Cita',
                    click: openAddEventModal
                },
            },
            contentHeight: "200px",
            events: async function(fetchInfo, successCallback, failureCallback) {
                try {
                    const response = await fetch(`${getBaseUrl()}/appointments`);
                    const data = await response.json();

                    const transformedEvents = data.map(appointment => ({
                        title: `${appointment.professionalName}; Estudiante: ${appointment.userFullName}`,
                        start: appointment.appointmentDate + "T" + appointment.appointmentStartTime,
                        end: appointment.appointmentEndTime + "T" + appointment.appointmentEndTime,
                        extendedProps: {
                            id: appointment.id,
                            userId: appointment.userId,
                            professionalId: appointment.professionalId,
                            state: appointment.state,
                            reason: appointment.description,
                            appointmentDate: appointment.appointmentDate,
                            appointmentStartTime: appointment.appointmentStartTime,
                            appointmentEndTime: appointment.appointmentEndTime
                        }
                    }));

                    successCallback(transformedEvents);
                } catch (error) {
                    console.error("Error fetching appointments:", error);
                    failureCallback();
                }
            },
            eventContent: function(eventInfo) {
                let eventTitle = eventInfo.event.title;
                let eventDescription = eventInfo.event.extendedProps.reason;

                return {
                    html: `
                        <div class="fc-event-main-frame cursor-pointer w-full">
                            <div class="fc-event-title-container">
                                <div class="fc-event-time">${eventInfo.timeText}</div>
                                <div class="fc-event-title overflow-hidden truncate font-medium">${eventTitle}</div>
                            </div>
                            ${eventDescription ? `<div class="fc-event-description overflow-hidden truncate">${eventDescription}</div>` : ""}
                        </div>
                    `
                }
            },
            eventClick: function(info) {
                const eventObj = info.event;

                if (eventObj.url) {
                    window.open(eventObj.url, '_blank');
                    info.jsEvent.preventDefault();
                } else {
                    console.log(eventObj.extendedProps);

                    setProfessionalDefaultValue(eventObj.extendedProps.professionalId);
                    setDefaultUserValue(eventObj.extendedProps.userId);
                    setDefaultAppointmentState(eventObj.extendedProps.state);

                    appointmentDateInput.value = eventObj.extendedProps.appointmentDate;
                    appointmentStartTimeInput.value = eventObj.extendedProps.appointmentStartTime;
                    appointmentEndTimeInput.value = eventObj.extendedProps.appointmentEndTime;
                    appointmentReasonInput.value = eventObj.extendedProps.reason;
                    appointmentIdInput.value = eventObj.extendedProps.id;

                    openEditEventModal();
                }
            },
            eventClassNames: function({ event: calendarEvent}) {
                const colorClass = eventsColors[calendarEvent.extendedProps.state];
                return ["warning", "danger", "success", "primary"].includes(colorClass) ? [`event-fc-color`, `fc-bg-${colorClass}`] : colorClass;
            },
            locale: esLocale
        });
        calendar.render();
    }
})
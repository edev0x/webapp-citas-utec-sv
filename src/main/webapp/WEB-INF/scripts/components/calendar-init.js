import { Calendar } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import listPlugin from "@fullcalendar/list";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

document.addEventListener("DOMContentLoaded", function() {
    const calendarEl = document.getElementById("calendar");

    if (calendarEl) {
        const calendarHeaderToolBar = {
            left: 'prev,next addEventButton',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        }

        const openAddEventModal = () => { document.getElementById('addEventModal').showModal(); }
        const closeAddEventModal = () => { document.getElementById('addEventModal').close(); }

        const calendar = new Calendar(calendarEl, {
            plugins: [dayGridPlugin, timeGridPlugin, listPlugin, interactionPlugin],
            selectable: true,
            initialView: "dayGridMonth",
            headerToolbar: calendarHeaderToolBar,
            customButtons: {
                addEventButton: {
                    text: 'Agregar Cita',
                    click: openAddEventModal
                },
            },
        })
        calendar.render();
    }
})
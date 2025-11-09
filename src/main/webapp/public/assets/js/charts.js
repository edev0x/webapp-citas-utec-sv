"use strict";

const initChartData = async () => {
    const chart = document.getElementById("chart-1");
    if (!chart) return;

    const response = await fetch("analytics?resource=appointments&by=month");
    const data = await response.json();

    const months = [
        "enero",
        "febrero",
        "marzo",
        "abril",
        "mayo",
        "junio",
        "julio",
        "agosto",
        "septiembre",
        "octubre",
        "noviembre",
        "diciembre"
    ];

    const categories = data.map(d => `${months[d.month - 1]} ${d.year}`);
    const counts = data.map(d => d.numberOfAppointments);

    const options = {
        series: [
            {
                name: "Citas realizadas",
                data: counts,
            },
        ],
        colors: ["#465fff"],
        chart: {
            fontFamily: "Outfit, sans-serif",
            type: "bar",
            height: 180,
            toolbar: {
                show: false,
            },
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: "39%",
                borderRadius: 5,
                borderRadiusApplication: "end",
            },
        },
        dataLabels: {
            enabled: false,
        },
        stroke: {
            show: true,
            width: 4,
            colors: ["transparent"],
        },
        xaxis: {
            categories,
            title: { text: "Mes" },
            axisBorder: { show: false },
            axisTicks: { show: false },
        },
        legend: {
            show: true,
            position: "top",
            horizontalAlign: "left",
            fontFamily: "Outfit",
            markers: {
                radius: 99,
            },
        },
        yaxis: {
            title: { text: "Total de citas" },
        },
        grid: {
            yaxis: {
                lines: {
                    show: true,
                },
            },
        },
        fill: {
            opacity: 1,
        },
        tooltip: {
            x: { show: false },
            y: {
                formatter: function (val) {
                    return val;
                },
            },
        },
    };

    const chartRender = new ApexCharts(chart, options);
    chartRender.render();
};


const initAppointmentsPieChart = async () => {
    const chart = document.getElementById("appointments-bystate-chart");
    if (!chart) return;

    const response = await fetch("analytics?resource=appointments&by=state");
    const data = await response.json();

    const options = {
        series: data.map(d => d.totalAppointments),
        labels: data.map(d => d.state),
        colors: ['#465fff', '#36B37E', '#FF5630', '#FFAB00', '#6554C0'],
        chart: {
            fontFamily: "Outfit, sans-serif",
            type: "pie",
            height: 300,
            toolbar: {
                show: false,
            },
        },
        legend: {
            position: 'bottom',
            horizontalAlign: 'center',
            fontFamily: "Outfit"
        },
        dataLabels: {
            enabled: true,
            formatter: function (val) {
                return Math.round(val) + '%'
            }
        },
        tooltip: {
            y: {
                formatter: function (value) {
                    return value + ' citas'
                }
            }
        },
        responsive: [{
            breakpoint: 480,
            options: {
                chart: {
                    height: 250
                }
            }
        }]
    }

    const appointmentsPieChartRender = new ApexCharts(chart, options);
    appointmentsPieChartRender.render();
}

document.addEventListener("DOMContentLoaded", async function () {
    await initChartData();
    await initAppointmentsPieChart();
})
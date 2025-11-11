"use strict";
import ApexCharts from "apexcharts";

import {getBaseUrl} from "./utils";

document.addEventListener("DOMContentLoaded", function () {
    const shortMonths = ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"];

    const options = {
        defaultLocale: "es",
        locales: [{
            name: "es",
            options: {
                months: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
                shortMonths: shortMonths,
                days: ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"],
                shortDays: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
            }
        }]
    }
    const initAppointmentsCharts = async () => {
        const appoinmentsByMonthChart = document.getElementById("chart-1");

        if (appoinmentsByMonthChart) {
            const appointmentsByMonthChartData = await fetch(`${getBaseUrl()}/api/analytics?resource=appointments&by=month`).then(response => response.json());

            const categories = appointmentsByMonthChartData.map((d) => `${shortMonths[d.month - 1]} ${d.year}`);
            const counts = appoinmentsByMonthChart.map((d) => d.numberOfAppointments);

            const chartConfig = {
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

            const chartRender = new ApexCharts(appoinmentsByMonthChart, chartConfig);
            await chartRender.render();
        }
    }
})
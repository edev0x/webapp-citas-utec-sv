"use strict";

import { getBaseUrl } from "./utils";

const initChartData = async () => {
  const chart = document.getElementById("appointments-by-month-chart");
  if (!chart) return;

  const response = await fetch(`${getBaseUrl()}/analytics?resource=appointments&by=month`);
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
    colors: ["#465FFF", "#9CB9FF"],
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
  await chartRender.render();
};


const initAppointmentsPieChart = async () => {
  const chart = document.getElementById("appointments-by-state-chart");
  if (!chart) return;

  const response = await fetch(`${getBaseUrl()}/analytics?resource=appointments&by=state`);
  const data = await response.json();

  const options = {
    series: data.map(d => d.totalAppointments),
    labels: data.map(d => d.state),
    theme: {
      palette: 'palette6',
    },
    chart: {
      fontFamily: "Outfit, sans-serif",
      type: "donut",
      height: 300,
      toolbar: {
        show: false,
      },
      animations: {
        speed: 200
      }
    },
    legend: {
      position: 'bottom',
      horizontalAlign: 'center',
      fontFamily: "Outfit",
      color: '#f3f3f3',
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
          height: 300
        }
      },
    }]
  }

  const appointmentsPieChartRender = new ApexCharts(chart, options);
  await appointmentsPieChartRender.render();
}

document.addEventListener("DOMContentLoaded", function () {
  Promise.all([initChartData(), initAppointmentsPieChart()]);
})
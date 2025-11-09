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
    "diciembre",
  ];

  const categories = data.map((d) => `${months[d.month - 1]} ${d.year}`);
  const counts = data.map((d) => d.numberOfAppointments);

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

  if (data.length === 0) return;

  const totalAppointments = data.reduce(
    (acc, curr) => acc + curr.totalAppointments,
    0
  );

  const series = Array.from(data, (d) => d.totalAppointments);
  const labels = Array.from(data, (d) => d.state.replace("_", " "));

  const donutChartOptions = {
    series: series,
    colors: ["#465FFF", "#3641f5", "#7592ff", "#8789cfff"],
    labels: labels,
    chart: {
      fontFamily: "Outfit, sans-serif",
      type: "donut",
      width: 450,
    },
    stroke: {
      show: false,
      width: 2,
      colors: ["transparent"],
    },
    plotOptions: {
      pie: {
        donut: {
          lineCap: "smooth",
          size: "65%",
          background: "transparent",
          labels: {
            show: true,
            total: {
              show: true,
              label: `Total ${totalAppointments} citas`,
              color: "#1D2939",
              fontSize: "16px",
              fontWeight: "bold",
            },
          },
        },
        expandOnClick: false,
      },
    },
    dataLabels: {
      enabled: false,
    },
    tooltip: {
      enabled: false,
    },
    legend: {
      show: true,
      position: "bottom",
      horizontalAlign: "left",
      fontFamily: "Outfit",
      fontSize: "14px",
      fontWeight: 400,
      markers: {
        size: 5,
        shape: "circle",
        radius: 999,
        strokeWidth: 0,
      },
      itemMargin: {
        horizontal: 6,
        vertical: 6,
      },
    },
    responsive: [
      {
        breakpoint: 640,
        options: {
          chart: {
            width: 370,
            height: 290,
          },
        },
      },
    ],
  };

  const appointmentsPieChartRender = new ApexCharts(chart, donutChartOptions);
  appointmentsPieChartRender.render();
};

document.addEventListener("DOMContentLoaded", async function () {
  await initChartData();
  await initAppointmentsPieChart();
});

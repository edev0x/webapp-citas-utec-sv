const sidebar = document.getElementById("sidebar");
const mainContent = document.getElementById("main-content");
const sidebarToggle = document.getElementById("sidebar-toggle");
const overlay = document.getElementById("overlay");
let sidebarOpen = false;

function toggleSidebar() {
  sidebarOpen = !sidebarOpen;

  if (sidebarOpen) {
    sidebar.classList.remove("-translate-x-full");
    if (window.innerWidth >= 1024) {
      mainContent.classList.remove("ml-0");
      mainContent.classList.add("ml-64");
    } else {
      overlay.classList.remove("hidden");
    }
  } else {
    sidebar.classList.add("-translate-x-full");
    mainContent.classList.remove("ml-64");
    mainContent.classList.add("ml-0");
    overlay.classList.add("hidden");
  }
}

if (sidebarToggle) {
  sidebarToggle.addEventListener("click", toggleSidebar);
}

overlay.addEventListener("click", function () {
  if (window.innerWidth < 1024) {
    toggleSidebar();
  }
});

// Handle window resize
window.addEventListener("resize", function () {
  if (window.innerWidth < 1024) {
    mainContent.classList.remove("ml-64");
    mainContent.classList.add("ml-0");
    if (sidebarOpen) {
      overlay.classList.remove("hidden");
    }
  } else {
    overlay.classList.add("hidden");
    if (sidebarOpen) {
      mainContent.classList.remove("ml-0");
      mainContent.classList.add("ml-64");
    }
  }
});
const sidebar = document.getElementById("app-sidebar");
const mainContent = document.getElementById("main-content");
const sidebarToggle = document.getElementById("sidebar-toggle");
const overlay = document.getElementById("overlay");
let sidebarOpen = true;

// Toggle sidebar function - hide completely
function toggleSidebar() {
  sidebarOpen = !sidebarOpen;

  if (sidebarOpen) {
    sidebar.style.transform = 'translateX(0)';
    sidebar.classList.remove('-translate-x-full');
    if (window.innerWidth >= 1024) {
      mainContent.classList.remove('ml-0');
      mainContent.classList.add('ml-64');
    } else {
      overlay.classList.remove('hidden');
    }
  } else {
    sidebar.style.transform = 'translateX(-100%)';
    mainContent.classList.remove('ml-64');
    mainContent.classList.add('ml-0');
    overlay.classList.add('hidden');
  }
}

// Submenu toggle - always enabled
document.querySelectorAll('.menu-parent').forEach(button => {
  button.addEventListener('click', function(e) {
    e.preventDefault();

    const menuItem = this.closest('.menu-item');
    const submenu = menuItem.querySelector('.submenu');
    const chevron = this.querySelector('.chevron');

    submenu.classList.toggle('hidden');
    chevron.classList.toggle('rotate-180');
  });
});


// Overlay click to close sidebar
overlay.addEventListener('click', function() {
  if (sidebarOpen) {
    toggleSidebar();
  }
});

// Handle window resize
window.addEventListener('resize', function() {
  if (window.innerWidth < 1024) {
    mainContent.classList.remove('ml-64');
    mainContent.classList.add('ml-0');
    if (sidebarOpen) {
      overlay.classList.remove('hidden');
    }
  } else {
    overlay.classList.add('hidden');
    if (sidebarOpen) {
      mainContent.classList.remove('ml-0');
      mainContent.classList.add('ml-64');
    }
  }
});

document.addEventListener('DOMContentLoaded', function() {
  if (sidebar && sidebarToggle) {
    sidebarToggle.addEventListener('click', toggleSidebar);
  }
})
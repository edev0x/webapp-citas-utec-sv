const avatarButton = document.getElementById("avatar-button");
const avatarDropdown = document.getElementById("avatar-menu");

const handleAvatarDropdown = () => {
  if (avatarButton && avatarDropdown) {
    avatarButton.addEventListener("click", function (e) {
      e.stopPropagation();
      avatarDropdown.classList.toggle("hidden");
    });

    document.addEventListener("click", function (event) {
      if (
        avatarDropdown &&
        !avatarDropdown.contains(event.target) &&
        !avatarButton.contains(event.target)
      ) {
        avatarDropdown.classList.add("hidden");
      }
    });
  }
};

document.addEventListener("DOMContentLoaded", handleAvatarDropdown);
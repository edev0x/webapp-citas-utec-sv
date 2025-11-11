export const getBaseUrl = () => {
  const origin = window.location.origin;
  const contextPath = window.location.pathname.split("/")[1];
  return `${origin}/${contextPath}`;
}

export const decodeHtml = (html) => {
  const parser = new DOMParser();
  const decodedString = parser.parseFromString(html, "text/html");
  return decodedString.body.textContent;
}

export const togglePasswordVisibility = () => {
  const passwordInput = document.getElementById("password");

  if (!passwordInput) {
    console.debug("Create user form not found in DOM");
    return;
  }

  const eyeOnIcon = document.getElementById("eyeOnIcon");
  const eyeOffIcon = document.getElementById("eyeOffIcon");
  const togglePasswordVisibilityButton = document.getElementById("togglePasswordVisibilityButton");

  if (eyeOnIcon && eyeOffIcon && togglePasswordVisibilityButton) {
    togglePasswordVisibilityButton.addEventListener("click", function () {
      passwordInput.type = passwordInput.type === "password" ? "text" : "password";

      eyeOnIcon.classList.toggle("hidden");
      eyeOffIcon.classList.toggle("hidden");
    })
  }
}

export const defaultJsonRequestHeaders = {
  "Content-Type": "application/json;charset=utf-8",
}
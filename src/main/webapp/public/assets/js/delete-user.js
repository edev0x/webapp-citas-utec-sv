function deleteUser() {
  const deleteUserForm = document.getElementById("delete-user-form");
  const deleteUserDialog = document.getElementById("uf-delete-user-dialog");
  const cancelDeleteUserBtn = document.getElementById("cancel-delete-user-btn");
  const submitDeleteUserBtn = document.getElementById("submit-delete-user-btn");
  const spinner = deleteUserForm.querySelector('[aria-label="Loading"]');

  cancelDeleteUserBtn.addEventListener("click", () => {
    deleteUserDialog.close();
    deleteUserForm.reset();
  });

  deleteUserForm.addEventListener("submit", function (event) {
    if (!event) return;
    event.preventDefault();

    const formData = new FormData(event.target);
    const userId = formData.get("userId");

    submitDeleteUserBtn.disabled = true;
    spinner.classList.remove("hidden");

    fetch("/admin/users/delete", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: new URLSearchParams({
        action: "delete",
        resource: "user",
        userId: userId,
      }),
    })
      .then((response) => {
        if (response.ok) {
          window.location.reload();
        } else {
          throw new Error("Error deleting user");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        submitDeleteUserBtn.disabled = false;
        spinner.classList.add("hidden");
      });
  });
}

function handleSearch() {
    const selectComponent = document.querySelector("#uf-select");
    const searchInput = document.querySelector("#search-term");
    const form = document.querySelector("#uf-search-form");
    const filterField = document.querySelector("#uf-filter-field");

    selectComponent.addEventListener("change", function (event) {
        const selectedValue = event.target.value;
        const selectedOption = document.querySelector(`[data-value="${selectedValue}"]`);

        filterField.value = selectedValue;
        console.log("Selected filter field:", selectedOption.textContent.trim());
    });

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const searchTerm = searchInput.value;
        const filterBy = filterField.value;

        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set("searchTerm", searchTerm);
        urlParams.set("searchField", filterBy);

        window.location.search = urlParams.toString();
        searchInput.addEventListener("keydown", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                form.submit();
            }
        });
    });
}

export default handleSearch;
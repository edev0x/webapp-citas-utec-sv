import './authentication.js';
import './avatar.js';
import './sidebar.js';
import './charts.js';

import handleSearch from './pagination.js';
import { togglePasswordVisibility, createUser } from './create-user.js';

document.addEventListener("DOMContentLoaded", function () {
    handleSearch();
    togglePasswordVisibility();
    createUser();
});
import './components/utils';
import './components/charts';
import './components/calendar-init';
import './components/sidebar';
import './components/avatar-dropdown';

// Auth and user's operations
import './ops/authentication';
import "./ops/create-user";
import "./ops/edit-user";
import "./ops/delete-user";

// Appointment's operations
import "./ops/create-appointment";
import "./ops/edit-appointment";
import "./ops/delete-appointment";

// Professional's operations
import "./ops/create-professional";
import "./ops/edit-professional";
import "./ops/delete-professional";

import { togglePasswordVisibility } from "./components/utils";

document.addEventListener('DOMContentLoaded', () => {
  console.info(`App loaded!`);

  togglePasswordVisibility();
});
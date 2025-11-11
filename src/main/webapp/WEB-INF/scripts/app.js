import './components/utils';
import './components/charts';
import './components/calendar-init';
import './components/sidebar';

import './ops/authentication';
import "./ops/create-user";
import "./ops/update-user";
import "./ops/delete-user";
import { togglePasswordVisibility } from "./components/utils";

document.addEventListener('DOMContentLoaded', () => {
  console.info(`App loaded!`);

  togglePasswordVisibility();
});
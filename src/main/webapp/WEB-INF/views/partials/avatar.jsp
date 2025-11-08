<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="dropdown-menu-checkboxes" class="dropdown-menu">
  <button type="button" id="dropdown-menu-checkboxes-trigger" aria-haspopup="menu" aria-controls="dropdown-menu-checkboxes-menu" aria-expanded="false" class="btn-outline">Open</button>
  <div id="dropdown-menu-checkboxes-popover" data-popover aria-hidden="true" class="min-w-56">
    <div role="menu" id="dropdown-menu-checkboxes-menu" aria-labelledby="dropdown-menu-checkboxes-trigger">
      <div role="group" aria-labelledby="account-options">
        <div role="heading" id="account-options">Account Options</div>
        <div role="menuitem">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
            <circle cx="12" cy="7" r="4" />
          </svg>
          Profile
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⇧⌘P</span>
        </div>
        <div role="menuitem">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect width="20" height="14" x="2" y="5" rx="2" />
            <line x1="2" x2="22" y1="10" y2="10" />
          </svg>
          Billing
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⌘B</span>
        </div>
        <div role="menuitem">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z" />
            <circle cx="12" cy="12" r="3" />
          </svg>
          Settings
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⌘S</span>
        </div>
      </div>
      <hr role="separator" />
      <div role="group" aria-labelledby="appearance-options">
        <div role="heading" id="appearance-options">Appearance</div>
        <div role="menuitemcheckbox" aria-checked="true" class="group">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="invisible group-aria-checked:visible" aria-hidden="true" focusable="false"><path d="M20 6 9 17l-5-5" /></svg>
          Status Bar
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⇧⌘P</span>
        </div>
        <div role="menuitemcheckbox" aria-checked="false" class="group" aria-disabled="true">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="invisible group-aria-checked:visible" aria-hidden="true" focusable="false"><path d="M20 6 9 17l-5-5" /></svg>
          Activity Bar
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⌘B</span>
        </div>
        <div role="menuitemcheckbox" aria-checked="false" class="group">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="invisible group-aria-checked:visible" aria-hidden="true" focusable="false"><path d="M20 6 9 17l-5-5" /></svg>
          Panel
          <span class="text-muted-foreground ml-auto text-xs tracking-widest">⌘S</span>
        </div>
      </div>
      <hr role="separator" />
      <div role="menuitem">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
          <polyline points="16 17 21 12 16 7" />
          <line x1="21" x2="9" y1="12" y2="12" />
        </svg>
        Logout
        <span class="text-muted-foreground ml-auto text-xs tracking-widest">⇧⌘P</span>
      </div>
    </div>
  </div>
</div>


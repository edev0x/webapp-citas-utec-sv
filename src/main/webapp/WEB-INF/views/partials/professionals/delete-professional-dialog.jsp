<dialog id="delete-professional-dialog" class="dialog" aria-labelledby="delete-professional-dialog-title"
        aria-describedby="delete-professional-dialog-description">
  <article>
    <header>
      <h2 id="alert-dialog-title">Confirmaci&oacute;n</h2>
      <p id="alert-dialog-description">
        &iquest;Est&aacute; seguro que desea eliminar este registro?, esta acci&oacute;n no se puede deshacer.
      </p>
    </header>
    <form id="delete-professional-form" method="post" class="inline">
      <input type="hidden" name="action" value="delete" />
      <input type="hidden" name="resource" value="professional" />
      <input type="hidden" name="professionalId-delete" id="professionalId-delete" value="" />
      <footer class="flex justify-end gap-2">
        <button type="button" class="btn-secondary" id="cancel-delete-professional-btn">Cancelar</button>
        <button type="submit" class="btn-destructive" id="delete-professional-submit-btn">
          <i data-feather="loader" class="animate-spin mr-2 hidden" id="delete-professional-loader"></i>
          Eliminar
        </button>
      </footer>
    </form>
  </article>
</dialog>
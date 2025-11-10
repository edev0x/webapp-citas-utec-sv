<!-- Delete User Dialog -->
<dialog id="uf-delete-user-dialog" class="dialog" aria-labelledby="uf-delete-user-dialog-title"
    aria-describedby="uf-delete-user-dialog-description">
    <article>
        <header>
            <h2 id="alert-dialog-title">Confirmaci&oacute;n</h2>
            <p id="alert-dialog-description">
                &iquest;Est&aacute; seguro que desea eliminar este registro?, esta acci&oacute;n no se puede deshacer.
            </p>
        </header>
        <form id="delete-user-form" method="post" class="inline">
            <input type="hidden" name="action" value="delete" />
            <input type="hidden" name="resource" value="user" />
            <input type="hidden" name="userId" id="deleteUserId" value="" />
            <footer class="flex justify-end gap-2">
                <button type="button" class="btn-secondary" id="cancel-delete-user-btn">Cancelar</button>
                <button type="submit" class="btn-destructive" id="delete-user-submit-btn">
                    <i data-feather="loader" class="animate-spin mr-2 hidden" id="delete-user-loader-icon"></i>
                    Eliminar
                </button>
            </footer>
        </form>
    </article>
</dialog>
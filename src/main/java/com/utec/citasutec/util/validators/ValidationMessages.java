package com.utec.citasutec.util.validators;

public interface ValidationMessages {
   String REQUIRED_FIELD_ERROR = "El campo %s es obligatorio";
   String INVALID_EMAIL_FORMAT = "El formato del email es incorrecto";
   String INVALID_PASSWORD_FORMAT = "El formato de la contrase&ntilde;a es invalido. Debe contener al menos 8 caracteres, una letra, un numero y un caracter especial @#$%^&+=";
   String INVALID_CREDENTIALS = "Las credenciales ingresadas son incorrectas o no pertenecen a ninguna cuenta. Int&eacute;ntalo de nuevo";
   String REQUIRED_FIELD_ERROR_GENERIC = "Este campo es obligatorio";
   String ALREADY_EXISTS = "Ya existe una cuenta este correo. Si reconoces esta cuenta, intenta iniciar sesi&oacute;n.";
   String ACCOUNT_CREATION_ERROR = "No se pudo crear la cuenta. Int&eacute;ntalo de nuevo.";
   String NAME_LENGTH = "El nombre debe contener al menos 4 caracteres y un m&aacute;ximo de 100 caracteres";
   String LAST_NAME_LENGTH = "El apellido debe contener al menos 4 caracteres y un m&aacute;ximo de 100 caracteres";
   String USER_ID_REQUIRED = "El identificador del estudiante es obligatorio";
   String PROFESSIONAL_ID_REQUIRED = "El identificador del profesional es obligatorio";
   String INVALID_APPOINTMENT_STATE = "El estado del cita es invalido. Debe proveer uno de los siguientes valores: ";
}

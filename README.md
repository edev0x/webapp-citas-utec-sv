# Jakarta EE Web Application - Citas UTEC

Aplicación WEB desarrollada con Jakarta EE, JPA, y MySQL.

## Características
- Gestión de citas médicas para estudiantes con profesionales de distintas especialidades.
- Registro y autenticación en base a roles RBAC haciendo uso de la espeicificación de **Jakarta Security**.
- Persistencia de datos en MySQL.
- Interfaz web responsiva mínima para agendar, listar y cancelar citas, gestión de usuarios, y un dashboard intuitivo para el administrador y profesionales.

## Requisitos
- Java 17+ (JDK)
- Maven 3.9+
- Servidor de aplicaciones compatible con Jakarta EE
    - Glassfish 
    - Payara 
- Base de datos MySQL v8 o superior

## Instalación
1. Configurar un Pool y un Recurso JDBC en el servidor de aplicaciones bajo el nombre `jdbc/citas-utec`
2. Clonar el repositorio:
    ```sh
       git clone https://github.com/edev0x/webapp-citas-utec-sv
    ```
3. Modificar el archivo `persistence.xml` dentro de `src/main/resources/META-INF/` y especificar el pool de conexiones configurado en el paso 1.
    ```xml
    <!-- No modificar el resto de configuración del archivo PERSISTENCE.xml -->
    <persistence-unit name="citasUtecPU" transaction-type="JTA">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <!-- Aqui define el recurso jdbc configurado en su servidor de aplicaciones -->
        <jta-data-source>jdbc/citas-utec</jta-data-source>

         <!-- ... -->
    </persistence-unit>
    ```
4. Definir una variable de entorno con el path de su servidor de aplicaciones:
    
    En sistemas UNIX:

    ```sh
    export GLASSFISH_HOME=/path/servidor
    ```

    En Windows:
    ```sh
    set GLASSFISH_HOME=/path/servidor
    ```

    > Esta variable de entorno es utilizada por el proceso de empaquetado y despliegue dentro del archivo `POM.xml`, usted puede modificar este archivo como le sea conveniente.

5. Compilar y empaquetar:
    ```sh
    ./mvnw clean package -Dglassfish.home=$GLASSFISH_HOME && ./mvnw glassfish:deploy # o tambien glassfish:redeploy en caso que la aplicación ya se encuentre en ejecución dentro del servidor
    ```
6. Acceder en el navegador: 
    
    http://localhost:8080/webapp-citas-utec-sv-1.0-SNAPSHOT


## Estructura del proyecto
- `src/main/java` — código fuente Java (servlets, beans, entidades, servicios, repositorios)
- `src/main/resources` — configuración (persistence.xml, mensajes)
- `src/main/webapp` — vistas JSP/JSF, recursos estáticos (CSS, JS)
- `pom.xml` — dependencias y build

## Buenas prácticas
- Usar perfiles de Maven para entornos (dev/prod).
- Aplicar migraciones de esquema (Flyway/Liquibase) en despliegues.
- Proteger endpoints sensibles con los roles predefinidos o alguno que usted cree conveniente y esté mapeado en la base de datos.

## Contribución
- Abrir issues para reportar errores o solicitar mejoras.
- Enviar pull requests con descripciones claras y pruebas unitarias.
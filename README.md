# Projecte Escalada
## Pol Corsin / Enzo Gutierrez

## Descripción
Este proyecto es una aplicación de consola para gestionar escaladores, escuelas, sectores y vías de escalada.
Se conecta a una base de datos SQLite y usa un patrón DAO para separar la lógica de acceso a datos de la lógica de negocio.

## Estructura del proyecto
- `src/`
  - `Main.java`: punto de entrada de la aplicación.
  - `controller/`: controla la lógica de usuario y flujos de menú.
  - `DAO/interfaces/`: define las interfaces de acceso a datos.
  - `DAO/sqlite/`: implementaciones SQLite de las interfaces DAO.
  - `DAO/sqlite/*/`: clases concretas para crear, buscar, actualizar y eliminar registros.
  - `model/`: clases que representan las entidades del dominio.
  - `utils/DBConnection.java`: gestión centralizada de la conexión a SQLite.
  - `view/MenuTerminal.java`: muestra los menús de terminal y lee entradas.
- `db/`: scripts SQL y base de datos si los añades.
- `lib/`: contiene el driver JDBC de SQLite.

## Cómo ejecutarlo
1. Coloca el driver SQLite JDBC en `lib/`.
   - Por ejemplo: `lib/sqlite-jdbc-3.41.2.1.jar`
   - Descárgalo desde Maven Central o GitHub: https://github.com/xerial/sqlite-jdbc

2. Compila el proyecto desde la carpeta raíz del proyecto:

```powershell
cd f:\Projects\ProjPersistencia-ESCALADA
$files = Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName }
& 'C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3.5\jbr\bin\javac.exe' -cp ".;lib\sqlite-jdbc-3.41.2.1.jar" -d out $files
```

3. Ejecuta la aplicación con el classpath que incluya `out` y el JAR:

```powershell
cd f:\Projects\ProjPersistencia-ESCALADA
& 'C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3.5\jbr\bin\java.exe' -cp "out;lib\sqlite-jdbc-3.41.2.1.jar" Main
```

> Importante: el ejecutable `java` del sistema puede ser una versión antigua. Usa el JDK/JRE correcto o añade `javac` y `java` del JDK moderno a tu `PATH`.

## Problemas que hemos tenido
- `No suitable driver found for jdbc:sqlite:...`
  - Esto ocurre cuando falta el driver `sqlite-jdbc` en el classpath.
- `NullPointerException` en `FindEscalador.byAlias(...)`
  - Se produjo porque `DBConnection.getConnection()` retornaba `null` al fallar la conexión.

## Cómo hemos desglosado el proyecto
- `Main.java` inicia las DAOs y controladores y muestra los menús.
- Cada entidad tiene:
  - modelo (`model/`)
  - DAO interfaz (`DAO/interfaces/`)
  - DAO SQLite (`DAO/sqlite/`)
- `controller/` gestiona la interacción con el usuario y valida entradas.
- `view/MenuTerminal.java` centraliza la impresión de menús y lectura de datos.
- `utils/DBConnection.java` centraliza la conexión a SQLite.

## Gestión de errores
- Los controladores validan las entradas del usuario antes de llamar a los DAOs.
- Si falta un registro, se muestra un mensaje explicativo y se vuelve al menú.
- Las operaciones de base de datos capturan excepciones `SQLException` y lanzan `RuntimeException` con mensajes legibles.
- Se evita continuar con conexiones nulas o datos inválidos.

## Por qué y cuándo abrimos `DBConnection`
- `DBConnection` es la clase que crea y devuelve la conexión JDBC a la base de datos.
- Solo abrimos la conexión cuando se necesita por primera vez, en `getConnection()`.
- Si la conexión ya existe y está abierta, se reutiliza.
- Al abrirla activamos `PRAGMA foreign_keys = ON;` para que SQLite compruebe las claves foráneas.
- Si falla la conexión, el error se reporta claramente para que no haya `NullPointerException` posteriores.

## Sugerencias de uso
- Crea primero las escuelas y sectores antes de crear vías.
- Al listar vías, se pide la escuela y luego el sector para filtrar correctamente.
- Si el proyecto lo abres desde un IDE, añade el JAR `lib/sqlite-jdbc-3.41.2.1.jar` a las dependencias del proyecto.

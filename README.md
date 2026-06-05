# FT Music App - Playlist Songs

## 1. Descripción del proyecto

**FT Music App** es una aplicación móvil desarrollada en **Android Studio** que permite gestionar playlists de música mediante una arquitectura cliente-servidor. La aplicación permite crear, consultar, modificar y eliminar playlists, así como asignar y remover canciones desde un catálogo musical.

El sistema está compuesto por tres componentes principales:

1. **Aplicación móvil Android**, desarrollada en Kotlin.
2. **Backend Web API**, desarrollado con ASP.NET Core y C#.
3. **Base de datos relacional MySQL**, implementada con tablas relacionadas y procedimientos almacenados.

La comunicación entre la aplicación móvil y el backend se realiza mediante peticiones HTTP. El backend recibe las solicitudes desde Android, valida la información, ejecuta los procedimientos almacenados correspondientes y devuelve las respuestas en formato JSON.

---

## 2. Arquitectura general del sistema

La solución fue diseñada bajo una arquitectura por capas, separando la interfaz móvil, la lógica de comunicación, los servicios del backend y el acceso a datos.

```text
Android App
    ↓
Retrofit
    ↓
ASP.NET Core Web API
    ↓
Dapper + MySqlConnector
    ↓
Stored Procedures
    ↓
MySQL Database
```

### Descripción del flujo

1. El usuario interactúa con la aplicación Android.
2. Android envía solicitudes HTTP al backend mediante Retrofit.
3. La Web API procesa la solicitud recibida.
4. El backend ejecuta los procedimientos almacenados en MySQL usando Dapper.
5. La base de datos devuelve la información solicitada.
6. La API responde a Android en formato JSON.
7. Android actualiza la interfaz con los datos recibidos.

---

## 3. Tecnologías utilizadas

### Aplicación móvil

- Android Studio
- Kotlin
- XML Layouts
- Retrofit
- Gson Converter
- RecyclerView
- ViewModel
- LiveData
- Material Components

### Backend

- ASP.NET Core Web API
- C#
- Dapper
- MySqlConnector
- Swagger / OpenAPI
- Arquitectura por capas

### Base de datos

- MySQL
- MySQL Workbench
- Stored Procedures
- Llaves primarias
- Llaves foráneas
- Restricciones de integridad

---

## 4. Funcionalidades implementadas

### Gestión de playlists

- Crear playlists.
- Consultar playlists existentes.
- Modificar nombre y descripción de un playlist.
- Eliminar playlists.
- Visualizar el detalle de un playlist.
- Consultar canciones asignadas a un playlist.

### Gestión de canciones

- Consultar catálogo de canciones.
- Crear canciones nuevas en el catálogo.
- Agregar canciones del catálogo a un playlist.
- Remover canciones de un playlist.
- Validar que una misma canción no sea agregada dos veces al mismo playlist.

---

## 5. Estructura general del proyecto

```text
PruebaFTTechnologies/
│
├── Backend/
│   └── Proyecto ASP.NET Core Web API
│
├── FrontEnd/
│   └── Proyecto Android Studio
│
├── Database/
│   └── Script completo de la base de datos
│
└── README.md
```

---

## 6. Base de datos

La base de datos utilizada se llama:

```sql
playlist_songs
```

El modelo relacional está compuesto por tres tablas principales:

```text
PlayList
Songs
PlayListSongs
```

### Tabla `PlayList`

Almacena la información de los playlists creados por el usuario.

Campos principales:

```text
IdPlayList
NamePlayList
Information
DateCreate
```

### Tabla `Songs`

Almacena el catálogo general de canciones disponibles.

Campos principales:

```text
IdSong
Title
Artist
Album
Genre
LapseTime
```

### Tabla `PlayListSongs`

Funciona como tabla intermedia para relacionar playlists con canciones.

Campos principales:

```text
IdPlayListSong
IdPlayList
IdSong
AssignmentDate
```

Esta tabla contiene una restricción única sobre los campos `IdPlayList` e `IdSong`, con el objetivo de evitar que una misma canción sea agregada más de una vez al mismo playlist.

---

## 7. Procedimientos almacenados

La base de datos utiliza procedimientos almacenados para centralizar las operaciones principales del sistema.

```text
sp_CreatePlayList
sp_UpdatePlayList
sp_DeletePlayList
sp_GetPlayLists
sp_AddSongToPlayList
sp_RemoveSongFromPlayList
sp_GetSongsByPlayList
sp_InsertSongCatalog
sp_GetSongsCatalog
```

Estos procedimientos permiten realizar operaciones de inserción, consulta, actualización y eliminación sobre playlists, canciones y relaciones entre ambas entidades.

---

## 8. Backend ASP.NET Core Web API

El backend fue desarrollado con ASP.NET Core Web API y expone los endpoints necesarios para que la aplicación Android pueda interactuar con la base de datos.

### URL local del backend

```text
http://localhost:5280/api/
```

### Swagger

```text
http://localhost:5280/swagger/index.html
```

Swagger permite probar los endpoints de la API directamente desde el navegador.

---

## 9. Endpoints principales

### Canciones

#### Consultar catálogo de canciones

```http
GET /api/songs
```

#### Crear canción en el catálogo

```http
POST /api/songs
```

Ejemplo de JSON:

```json
{
  "title": "Blinding Lights",
  "artist": "The Weeknd",
  "album": "After Hours",
  "genre": "Pop",
  "lapseTime": "03:20"
}
```

---

### Playlists

#### Consultar playlists

```http
GET /api/playlists
```

#### Crear playlist

```http
POST /api/playlists
```

Ejemplo de JSON:

```json
{
  "namePlayList": "Music for Study",
  "information": "Relaxing songs for concentration"
}
```

#### Modificar playlist

```http
PUT /api/playlists/{idPlayList}
```

Ejemplo de JSON:

```json
{
  "namePlayList": "Music for Work",
  "information": "Songs for programming and focus"
}
```

#### Eliminar playlist

```http
DELETE /api/playlists/{idPlayList}
```

#### Consultar canciones de un playlist

```http
GET /api/playlists/{idPlayList}/songs
```

#### Agregar canción a un playlist

```http
POST /api/playlists/{idPlayList}/songs
```

Ejemplo de JSON:

```json
{
  "idSong": 1
}
```

#### Remover canción de un playlist

```http
DELETE /api/playlists/{idPlayList}/songs/{idSong}
```

---

## 10. Configuración del backend

La cadena de conexión a MySQL se configura en el archivo `appsettings.json`.

Ejemplo:

```json
{
  "ConnectionStrings": {
    "MySqlConnection": "Server=localhost;Port=3306;Database=playlist_songs;User ID=root;Password=TU_PASSWORD;SslMode=None;"
  }
}
```

El valor `TU_PASSWORD` debe reemplazarse por la contraseña correspondiente del usuario local de MySQL.

---

## 11. Arquitectura del backend

El backend está organizado en capas para mantener una estructura limpia, mantenible y escalable.

```text
Controllers
    ↓
Services
    ↓
Repositories
    ↓
Stored Procedures
    ↓
MySQL
```

### Capas principales

#### Controllers

Reciben las solicitudes HTTP desde Android o Swagger y devuelven respuestas JSON.

#### Services

Contienen la lógica de validación y coordinación entre controladores y repositorios.

#### Repositories

Se encargan de ejecutar los procedimientos almacenados mediante Dapper.

#### Data

Contiene la configuración de conexión a MySQL.

#### DTOs

Definen los objetos utilizados para recibir y devolver información en la API.

#### Models

Representan las entidades principales del sistema.

---

## 12. Aplicación Android

La aplicación Android fue desarrollada en Kotlin utilizando una arquitectura basada en separación de responsabilidades.

```text
Activity
    ↓
ViewModel
    ↓
Repository
    ↓
Retrofit ApiService
    ↓
Backend API
```

### URL utilizada desde Android Emulator

Cuando se ejecuta la aplicación desde el emulador de Android, se utiliza la siguiente URL base:

```text
http://10.0.2.2:5280/api/
```

Esto permite que el emulador acceda al backend local ejecutado en la computadora.

La URL se encuentra configurada en:

```text
utils/Constants.kt
```

Ejemplo:

```kotlin
object Constants {
    const val BASE_URL = "http://10.0.2.2:5280/api/"
}
```

---

## 13. Estructura de paquetes Android

```text
data
├── api
├── model
└── repository

ui
├── adapter
├── playlist
└── song

utils

viewmodel
```

### Descripción de paquetes

#### `data/api`

Contiene la configuración de Retrofit y la definición de endpoints.

Archivos principales:

```text
ApiService.kt
RetrofitClient.kt
```

#### `data/model`

Contiene los modelos de datos y objetos request/response utilizados por la aplicación.

#### `data/repository`

Contiene las clases encargadas de comunicarse con la API.

#### `viewmodel`

Contiene los ViewModels que administran los estados de la interfaz.

#### `ui`

Contiene las pantallas y adaptadores visuales de la aplicación.

#### `utils`

Contiene clases auxiliares, constantes y manejadores de estado o errores.

---

## 14. Pantallas principales

### MainActivity

Pantalla principal de la aplicación.

Permite:

- Visualizar playlists.
- Crear playlists.
- Editar playlists.
- Eliminar playlists.
- Acceder al detalle de un playlist.

### PlayListDetailActivity

Pantalla de detalle de playlist.

Permite:

- Consultar información del playlist.
- Visualizar canciones asignadas.
- Remover canciones del playlist.
- Acceder al catálogo para agregar canciones.

### SongsCatalogActivity

Pantalla del catálogo de canciones.

Permite:

- Visualizar canciones disponibles.
- Crear canciones nuevas.
- Agregar canciones al playlist seleccionado.

---

## 15. Validaciones implementadas

### Validaciones en Android

La aplicación valida los datos antes de enviarlos al backend.

Para crear canciones se valida:

```text
Title requerido
Artist requerido
Album requerido
Genre requerido
Minutes requerido
Seconds requerido
Seconds entre 0 y 59
Duración mayor a 00:00
```

Para crear playlists se valida:

```text
NamePlayList requerido
```

### Validaciones en backend

El backend también valida los datos recibidos antes de ejecutar operaciones sobre la base de datos.

Esto permite mantener mayor seguridad e integridad, incluso si las solicitudes se realizan desde Swagger u otro cliente externo.

---

## 16. Manejo de errores

La aplicación Android interpreta los mensajes enviados por el backend y los muestra al usuario de forma clara.

Por ejemplo, si se intenta agregar una canción repetida al mismo playlist, el sistema muestra un mensaje indicando que la canción ya se encuentra asignada.

Esto evita mostrar errores genéricos como:

```text
HTTP error: 400
```

y mejora la experiencia del usuario.

---

## 17. Instrucciones de ejecución

### 1. Base de datos

1. Abrir MySQL Workbench.
2. Ejecutar el script ubicado en la carpeta `Database`.
3. Confirmar que se creó la base de datos `playlist_songs`.
4. Verificar que las tablas y procedimientos almacenados existan correctamente.

### 2. Backend

1. Abrir la solución del backend en Visual Studio.
2. Configurar la cadena de conexión en `appsettings.json`.
3. Verificar que MySQL esté ejecutándose.
4. Ejecutar el proyecto ASP.NET Core Web API.
5. Confirmar que Swagger abra correctamente en:

```text
http://localhost:5280/swagger/index.html
```

### 3. Aplicación Android

1. Abrir el proyecto Android en Android Studio.
2. Verificar la URL base en `Constants.kt`.
3. Ejecutar un emulador Android.
4. Correr la aplicación.
5. Confirmar que el backend esté encendido antes de utilizar la app.

---

## 18. Flujo principal de uso

1. Abrir la aplicación Android.
2. Crear un nuevo playlist.
3. Editar la información del playlist si es necesario.
4. Ingresar al detalle del playlist.
5. Presionar la opción para agregar canciones.
6. Seleccionar una canción del catálogo.
7. Agregar la canción al playlist.
8. Visualizar la canción dentro del detalle del playlist.
9. Remover canciones si se requiere.
10. Eliminar playlists si corresponde.

---

## 19. Consideraciones importantes

- El backend debe estar ejecutándose antes de iniciar la aplicación Android.
- MySQL debe estar activo para que la API pueda consultar y guardar información.
- Desde Swagger se utiliza `localhost`.
- Desde Android Emulator se utiliza `10.0.2.2`.
- El script de base de datos debe ejecutarse antes de iniciar las pruebas.
- Las canciones del catálogo pueden registrarse desde Swagger o desde la aplicación Android.

---

## 20. Estado del proyecto

El proyecto cumple con las funcionalidades principales solicitadas:

```text
Aplicación Android funcional
Backend Web API funcional
Base de datos relacional MySQL
Stored Procedures implementados
Comunicación Android → API → MySQL
CRUD de playlists
Gestión de canciones por playlist
Catálogo de canciones
Validaciones básicas
Manejo de errores
```

---

## 21. Autor

Proyecto desarrollado como prueba técnica para el área de desarrollo de software.

```text
FT Music App - Playlist Songs
```

# Tpaga Test

Prueba técnica — app Android (Kotlin + Jetpack Compose).

## Cómo correr la app

- Abrir el proyecto en Android Studio, sincronizar Gradle y correr `app` (emulador o dispositivo).
- O desde terminal:
  ```bash
  ./gradlew :app:assembleDebug    # compilar
  ./gradlew :app:installDebug     # instalar
  ```
- Login con cualquier usuario/contraseña (solo valida que no estén vacíos).

## Patrón arquitectónico

**Clean Architecture (3 capas) + MVI + Koin.**

- `data` → Retrofit (red), Room (caché), implementaciones de repositorios.
- `domain` → modelos, interfaces de repositorio y **casos de uso**.
- `presentation` → ViewModels + pantallas Compose (MVI), una por feature.

- **Clean**: separa lógica de negocio de datos y UI; dependencias unidireccionales y código testeable.
- **MVI**: cada pantalla expone un estado inmutable (`sealed interface`) y la vista hace un solo `when` exhaustivo — los 4 estados son ciudadanos de primera clase, no condiciones dispersas.
- **Casos de uso**: la lógica vive en el dominio, los repositorios solo acceden/mapean datos.
- **Koin**: inyección de dependencias simple y declarativa, sin boilerplate, manteniendo las capas desacopladas.
- **Room + DataStore**: caché offline real y persistencia de sesión/filtro.

**Caché (offline)**: *network-first con fallback a cache*. Se intenta la red; si falla, se muestran los últimos datos de Room marcados como `offline` (de ahí sale el banner), y un `NetworkMonitor` actualiza solo cuando vuelve la conexión.

## Qué mejoraría con más tiempo

- Tests unitarios de ViewModels y de repositorios.
- Navegación con rutas type-safe en vez de strings.
- Unificar los 4 estados en un `UiState<T>` compartido.
- Paginación de la lista (Paging 3).
- Actualización automática del dashboard al reconectar (hoy solo la lista).
- Migraciones de Room documentadas y TypeConverter para el enum.

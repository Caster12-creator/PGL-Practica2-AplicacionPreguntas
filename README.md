# Práctica 2 Aplicación de preguntas

Aplicación de PGL hecha con Kotlin y Jetpack Compose. Incluye la parte obligatoria y los extras PREV y RANDOM.

## Funcionamiento

- Cinco preguntas de videojuegos, geografía, fútbol y ciencia.
- Una imagen relacionada con cada pregunta.
- TRUE y FALSE comprueban la respuesta y muestran el resultado en verde o rojo.
- Los botones señalan en verde la respuesta correcta y en rojo la incorrecta.
- NEXT avanza y vuelve a la primera pregunta después de la última.
- PREV retrocede y vuelve a la última desde la primera.
- RANDOM elige una pregunta distinta de la actual.
- Al cambiar de pregunta se borran el mensaje y los colores anteriores.

## Organización

- `MainActivity.kt`: abre la pantalla y aplica el tema y los márgenes del sistema.
- `model/Pregunta.kt`: guarda el enunciado y la respuesta correcta.
- `ui/pantallas/PantallaPreguntas.kt`: contiene las preguntas y el estado del juego.
- `ui/componentes/ComponentesPreguntas.kt`: contiene los botones reutilizables.
- `ui/Medidas.kt`: reúne las medidas y los colores que se usan varias veces.
- `res/values/strings.xml`: guarda los textos.
- `res/drawable`: contiene las cinco ilustraciones vectoriales.

## Abrir el proyecto

1. Descargar o clonar el repositorio.
2. Abrir su carpeta principal en Android Studio.
3. Esperar a que termine la sincronización de Gradle.
4. Seleccionar un emulador o dispositivo y ejecutar `app`.

Se necesita el SDK de Android 37 para compilar. La app puede instalarse desde Android 7.0 (API 24).
El proyecto incluye Gradle Wrapper. `local.properties` se genera en cada ordenador y no se entrega.

## Comprobación

Se ha comprobado en el emulador que las respuestas y las imágenes son correctas, que NEXT y PREV recorren la lista y que RANDOM cambia de pregunta.

```text
./gradlew assembleDebug connectedDebugAndroidTest lintDebug
```

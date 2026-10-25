package com.example.aplicacionpreguntas

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplicacionpreguntas.ui.pantallas.PantallaPreguntas
import com.example.aplicacionpreguntas.ui.theme.AplicacionPreguntasTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ComprobacionAppTest {
    @get:Rule val compose = createComposeRule()

    private fun captura(nombre: String) {
        compose.waitForIdle()
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        val carpeta = File(contexto.filesDir, "evidencias_sencillas").apply { mkdirs() }
        File(carpeta, "$nombre.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun preguntasRespuestasPrevNextYRandom() {
        compose.setContent { AplicacionPreguntasTheme(dynamicColor = false) { PantallaPreguntas() } }
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        fun pulsar(texto: Int) { compose.onNodeWithText(contexto.getString(texto)).performClick(); compose.waitForIdle() }
        fun comprobarSinResultado() {
            compose.onNodeWithText(contexto.getString(R.string.respuesta_correcta)).assertDoesNotExist()
            compose.onNodeWithText(contexto.getString(R.string.respuesta_incorrecta)).assertDoesNotExist()
        }

        captura("inicio")
        compose.onNodeWithContentDescription(contexto.getString(R.string.imagen_pregunta)).assertIsDisplayed()
        pulsar(R.string.boton_false)
        compose.onNodeWithText(contexto.getString(R.string.respuesta_correcta)).assertIsDisplayed()
        captura("correcta")
        pulsar(R.string.boton_true)
        compose.onNodeWithText(contexto.getString(R.string.respuesta_incorrecta)).assertIsDisplayed()
        captura("incorrecta")

        pulsar(R.string.boton_next)
        comprobarSinResultado()
        compose.onNodeWithText(contexto.getString(R.string.pregunta_2)).assertIsDisplayed()
        captura("zelda")
        pulsar(R.string.boton_next); captura("geografia")
        pulsar(R.string.boton_next); captura("futbol")
        pulsar(R.string.boton_next); captura("ciencia")
        pulsar(R.string.boton_next)
        compose.onNodeWithText(contexto.getString(R.string.pregunta_1)).assertIsDisplayed()

        pulsar(R.string.boton_prev)
        compose.onNodeWithText(contexto.getString(R.string.pregunta_5)).assertIsDisplayed()
        captura("prev")
        pulsar(R.string.boton_prev)
        compose.onNodeWithText(contexto.getString(R.string.pregunta_4)).assertIsDisplayed()
        captura("prev_futbol")
        pulsar(R.string.boton_random)
        compose.onNodeWithText(contexto.getString(R.string.pregunta_4)).assertDoesNotExist()
        comprobarSinResultado()
        captura("random")
    }
}

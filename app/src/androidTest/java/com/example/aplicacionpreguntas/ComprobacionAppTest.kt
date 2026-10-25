package com.example.aplicacionpreguntas

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplicacionpreguntas.ui.pantallas.PantallaPreguntas
import com.example.aplicacionpreguntas.ui.theme.AplicacionPreguntasTheme
import org.junit.Assert.assertNotEquals
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
        fun pulsar(tag: String) { compose.onNodeWithTag(tag).performClick(); compose.waitForIdle() }

        captura("inicio")
        compose.onNodeWithTag("imagen").assertIsDisplayed()
        pulsar("false")
        compose.onNodeWithTag("resultado").assertTextEquals(contexto.getString(R.string.respuesta_correcta))
        captura("correcta")
        pulsar("true")
        compose.onNodeWithTag("resultado").assertTextEquals(contexto.getString(R.string.respuesta_incorrecta))
        captura("incorrecta")

        pulsar("next")
        compose.onNodeWithTag("resultado").assertTextEquals("")
        compose.onNodeWithTag("pregunta").assertTextEquals(contexto.getString(R.string.pregunta_2))
        captura("zelda")
        pulsar("next"); captura("geografia")
        pulsar("next"); captura("futbol")
        pulsar("next"); captura("ciencia")
        pulsar("next")
        compose.onNodeWithTag("pregunta").assertTextEquals(contexto.getString(R.string.pregunta_1))

        pulsar("prev")
        compose.onNodeWithTag("pregunta").assertTextEquals(contexto.getString(R.string.pregunta_5))
        captura("prev")
        pulsar("prev")
        compose.onNodeWithTag("pregunta").assertTextEquals(contexto.getString(R.string.pregunta_4))
        captura("prev_futbol")
        val anterior = compose.onNodeWithTag("pregunta").fetchSemanticsNode().config[SemanticsProperties.Text]
        pulsar("random")
        val siguiente = compose.onNodeWithTag("pregunta").fetchSemanticsNode().config[SemanticsProperties.Text]
        assertNotEquals(anterior, siguiente)
        compose.onNodeWithTag("resultado").assertTextEquals("")
        captura("random")
    }
}

package com.example.aplicacionpreguntas.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.aplicacionpreguntas.R
import com.example.aplicacionpreguntas.model.Pregunta
import com.example.aplicacionpreguntas.ui.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import com.example.aplicacionpreguntas.ui.componentes.ImagenPregunta
import com.example.aplicacionpreguntas.ui.componentes.MensajeRespuesta

@Composable
fun PantallaPreguntas(modifier: Modifier = Modifier) {
    val preguntas = listOf(
        Pregunta(stringResource(R.string.pregunta_1), false),
        Pregunta(stringResource(R.string.pregunta_2), true),
        Pregunta(stringResource(R.string.pregunta_3), false),
        Pregunta(stringResource(R.string.pregunta_4), true),
        Pregunta(stringResource(R.string.pregunta_5), false)
    )
    val imagenes = listOf(
        R.drawable.imagen_minecraft, R.drawable.imagen_zelda,
        R.drawable.imagen_geografia, R.drawable.imagen_futbol, R.drawable.imagen_ciencia
    )

    var indicePregunta by remember { mutableStateOf(0) }
    var respuestaUsuario by remember { mutableStateOf<Boolean?>(null) }
    val preguntaActual = preguntas[indicePregunta]

    // Al cambiar de pregunta se borra la respuesta anterior.
    fun cambiarPregunta(nuevoIndice: Int) {
        indicePregunta = nuevoIndice
        respuestaUsuario = null
    }

    Column(
        modifier = modifier.fillMaxSize().padding(Medidas.margen).testTag("pantalla"),
        verticalArrangement = Arrangement.spacedBy(Medidas.separacion)
    ) {
        Text(preguntaActual.enunciado, modifier = Modifier.testTag("pregunta"))

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            ImagenPregunta(imagenes[indicePregunta])
        }

        MensajeRespuesta(respuestaUsuario, preguntaActual.respuestaCorrecta)

        var colorTrue = MaterialTheme.colorScheme.primary
        var colorFalse = MaterialTheme.colorScheme.primary
        if (respuestaUsuario != null) {
            if (preguntaActual.respuestaCorrecta) {
                colorTrue = VerdeCorrecto
                colorFalse = RojoIncorrecto
            } else {
                colorTrue = RojoIncorrecto
                colorFalse = VerdeCorrecto
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(Medidas.separacion)) {
            Button(
                onClick = { respuestaUsuario = true },
                colors = ButtonDefaults.buttonColors(containerColor = colorTrue),
                modifier = Modifier.weight(1f).testTag("true")
            ) {
                Text(stringResource(R.string.boton_true))
            }
            Button(
                onClick = { respuestaUsuario = false },
                colors = ButtonDefaults.buttonColors(containerColor = colorFalse),
                modifier = Modifier.weight(1f).testTag("false")
            ) {
                Text(stringResource(R.string.boton_false))
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(Medidas.separacion)) {
            OutlinedButton(
                onClick = {
                    var anterior = indicePregunta - 1
                    if (anterior < 0) {
                        anterior = preguntas.size - 1
                    }
                    cambiarPregunta(anterior)
                },
                modifier = Modifier.weight(1f).testTag("prev")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Spacer(Modifier.width(Medidas.pequena))
                Text(stringResource(R.string.boton_prev))
            }
            Button(
                onClick = {
                    var siguiente = indicePregunta + 1
                    if (siguiente == preguntas.size) {
                        siguiente = 0
                    }
                    cambiarPregunta(siguiente)
                },
                modifier = Modifier.weight(1f).testTag("next")
            ) {
                Text(stringResource(R.string.boton_next))
                Spacer(Modifier.width(Medidas.pequena))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }

        OutlinedButton(
            onClick = {
                var aleatoria = (0 until preguntas.size).random()
                while (aleatoria == indicePregunta) {
                    aleatoria = (0 until preguntas.size).random()
                }
                cambiarPregunta(aleatoria)
            },
            modifier = Modifier.fillMaxWidth().testTag("random")
        ) {
            Text(stringResource(R.string.boton_random))
        }
    }
}

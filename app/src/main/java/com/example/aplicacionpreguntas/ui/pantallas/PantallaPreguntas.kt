package com.example.aplicacionpreguntas.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.aplicacionpreguntas.R
import com.example.aplicacionpreguntas.model.Pregunta
import com.example.aplicacionpreguntas.ui.*
import com.example.aplicacionpreguntas.ui.componentes.BotonesNavegacion
import com.example.aplicacionpreguntas.ui.componentes.BotonesRespuesta

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
            Image(
                painter = painterResource(imagenes[indicePregunta]),
                contentDescription = stringResource(R.string.imagen_pregunta),
                modifier = Modifier.size(Medidas.imagen).testTag("imagen")
            )
        }

        val haAcertado = respuestaUsuario == preguntaActual.respuestaCorrecta
        Text(
            text = if (respuestaUsuario == null) "" else stringResource(
                if (haAcertado) R.string.respuesta_correcta else R.string.respuesta_incorrecta
            ),
            color = if (haAcertado) VerdeCorrecto else RojoIncorrecto,
            modifier = Modifier.heightIn(min = Medidas.altoMensaje).testTag("resultado")
        )

        BotonesRespuesta(
            respuestaCorrecta = preguntaActual.respuestaCorrecta,
            respondida = respuestaUsuario != null,
            onResponder = { respuestaUsuario = it }
        )

        BotonesNavegacion(
            onAnterior = { cambiarPregunta((indicePregunta - 1 + preguntas.size) % preguntas.size) },
            onSiguiente = { cambiarPregunta((indicePregunta + 1) % preguntas.size) },
            onRandom = {
                val otrasPreguntas = preguntas.indices.filter { it != indicePregunta }
                cambiarPregunta(otrasPreguntas.random())
            }
        )
    }
}

package com.example.aplicacionpreguntas.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.aplicacionpreguntas.R
import com.example.aplicacionpreguntas.ui.*

@Composable
fun ImagenPregunta(imagen: Int) {
    Image(
        painter = painterResource(imagen),
        contentDescription = stringResource(R.string.imagen_pregunta),
        modifier = Modifier.size(Medidas.imagen)
    )
}

@Composable
fun MensajeRespuesta(respuestaUsuario: Boolean?, respuestaCorrecta: Boolean) {
    var mensaje = ""
    var color = RojoIncorrecto

    if (respuestaUsuario != null) {
        if (respuestaUsuario == respuestaCorrecta) {
            mensaje = stringResource(R.string.respuesta_correcta)
            color = VerdeCorrecto
        } else {
            mensaje = stringResource(R.string.respuesta_incorrecta)
            color = RojoIncorrecto
        }
    }

    Text(
        text = mensaje,
        color = color,
        modifier = Modifier.heightIn(min = Medidas.altoMensaje)
    )
}

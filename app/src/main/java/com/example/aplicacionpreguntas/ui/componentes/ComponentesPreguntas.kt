package com.example.aplicacionpreguntas.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.aplicacionpreguntas.R
import com.example.aplicacionpreguntas.ui.*

@Composable
fun BotonesRespuesta(respuestaCorrecta: Boolean, respondida: Boolean, onResponder: (Boolean) -> Unit) {
    val colorTrue = when {
        !respondida -> MaterialTheme.colorScheme.primary
        respuestaCorrecta -> VerdeCorrecto
        else -> RojoIncorrecto
    }
    val colorFalse = when {
        !respondida -> MaterialTheme.colorScheme.primary
        !respuestaCorrecta -> VerdeCorrecto
        else -> RojoIncorrecto
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Medidas.separacion)) {
        Button(
            onClick = { onResponder(true) },
            colors = ButtonDefaults.buttonColors(containerColor = colorTrue),
            modifier = Modifier.weight(1f).testTag("true")
        ) { Text(stringResource(R.string.boton_true)) }
        Button(
            onClick = { onResponder(false) },
            colors = ButtonDefaults.buttonColors(containerColor = colorFalse),
            modifier = Modifier.weight(1f).testTag("false")
        ) { Text(stringResource(R.string.boton_false)) }
    }
}

@Composable
fun BotonesNavegacion(onAnterior: () -> Unit, onSiguiente: () -> Unit, onRandom: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Medidas.separacion)) {
        OutlinedButton(onAnterior, Modifier.weight(1f).testTag("prev")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Spacer(Modifier.width(Medidas.pequena))
            Text(stringResource(R.string.boton_prev))
        }
        Button(onSiguiente, Modifier.weight(1f).testTag("next")) {
            Text(stringResource(R.string.boton_next))
            Spacer(Modifier.width(Medidas.pequena))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
    OutlinedButton(onRandom, Modifier.fillMaxWidth().testTag("random")) {
        Text(stringResource(R.string.boton_random))
    }
}

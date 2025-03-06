package com.sergio.panelemergenciacarretera


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.muddz.styleabletoast.StyleableToast

@Composable
fun MenuMensajePersonalizadoDosFilas(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        LogoEscuelaYTitulos()
        BotonVolverPantallaUnaFila(navController = navController)
        EntradasTextoDosFilas()
        LogoUniversidad()
    }
}

@Composable
fun BotonVolverPantallaUnaFila(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(PantallaMenjsajesPersonalizado.route) },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado),
        )
    ) {
        Text(
            text = "VOLVER",
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.requiredWidth(110.dp),
        )
    }

}

@Composable
fun EntradasTextoDosFilas() {
    val context = LocalContext.current
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .padding(end = 10.dp)
        ) {
            Card(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .height(70.dp)
                    .width(200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TEXTO ZONA SUPERIOR:",
                        color = colorResource(id = R.color.morado),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    BasicTextField(
                        value = textoZonaSuperior,
                        onValueChange = {
                            textoZonaSuperior = it
                            (context as MainActivity).sendCommand("TEXTO FILA SUPERIOR#")
                            context.sendCommand(textoZonaSuperior + "#")
                        },
                        textStyle = TextStyle(
                            color = colorResource(id = R.color.rosa_claro),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
            Card(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .width(200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "VELOCIDAD DE DESPLAZAMIENTO",
                        color = colorResource(id = R.color.morado),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,

                        )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "100",
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                        )
                        Text(
                            text = velocidadFilaSuperior.toString(),
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            //modifier = Modifier.padding(end = 40.dp)
                        )
                        Text(
                            text = "500",
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Right,
                            //modifier = Modifier.padding(end = 40.dp)
                        )
                    }
                    Slider(
                        value = velocidadFilaSuperior.toFloat(),
                        onValueChange = { newValue ->
                            velocidadFilaSuperior = newValue.toInt()
                            (context as MainActivity).sendCommand("VELOCIDAD FILA SUPERIOR#")
                            context.sendCommand(velocidadFilaSuperior.toString() + "#")
                        },
                        valueRange = 100f..500f,
                        steps = (500 - 100) / 25,
                        modifier = Modifier
                            .width(200.dp)
                            .height(30.dp),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(250.dp),
            ) {
                Card(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 10.dp, bottom = 10.dp)
                        .width(200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    ListaEfectosFilaSuperior()
                }
            }
        }
        Column(
            modifier = Modifier
                .width(200.dp)
                .padding(end = 10.dp)
        ) {
            Card(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .height(70.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TEXTO ZONA INFERIOR:",
                        color = colorResource(id = R.color.morado),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    BasicTextField(
                        value = textoZonaInferior,
                        onValueChange = {
                            textoZonaInferior = it
                            (context as MainActivity).sendCommand("TEXTO FILA INFERIOR#")
                            context.sendCommand(textoZonaInferior + "#")
                        },
                        textStyle = TextStyle(
                            color = colorResource(id = R.color.rosa_claro),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
            Card(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .width(200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "VELOCIDAD DE DESPLAZAMIENTO",
                        color = colorResource(id = R.color.morado),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,

                        )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "100",
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                        )
                        Text(
                            text = velocidadFilaInferior.toString(),
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            //modifier = Modifier.padding(end = 40.dp)
                        )
                        Text(
                            text = "500",
                            color = colorResource(id = R.color.morado),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Right,
                        )
                    }
                    Slider(
                        value = velocidadFilaInferior.toFloat(),
                        onValueChange = { newValue ->
                            velocidadFilaInferior = newValue.toInt()
                            (context as MainActivity).sendCommand("VELOCIDAD FILA INFERIOR#")
                            context.sendCommand(velocidadFilaInferior.toString() + "#")
                        },
                        valueRange = 100f..500f,
                        steps = (500 - 100) / 25,
                        modifier = Modifier
                            .width(200.dp)
                            .height(30.dp),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(250.dp),
            ) {
                Card(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 10.dp, bottom = 10.dp)
                        .width(200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    ListaEfectosFilaInferior()
                }
            }
        }

    }
}

@Composable
fun ListaEfectosFilaSuperior() {
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.fillMaxWidth()) {
        items(MatrizEfectos.size) { number ->
            BotoneraEfectosFilaSuperior(number)
        }
    }
}

@Composable
fun ListaEfectosFilaInferior() {
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.fillMaxWidth()) {
        items(MatrizEfectos.size) { number ->
            BotoneraEfectosFilaInferior(number)
        }
    }
}

@Composable
fun BotoneraEfectosFilaSuperior(number: Int) {
    val context = LocalContext.current // Obtener el contexto
    //TODO Añadir matriz para mostrar textos e imagenes distintas según la opcion.
    Button(
        onClick = {
            TextoEstadoActual = "ESTADO ACTUAL: ENCENDIDO"
            ColorTextoEstadoActual = R.color.verde
            StyleableToast.makeText(
                context,
                "SE HA PULSADO: " + MatrizEfectos[number],
                Toast.LENGTH_LONG
            ).show();
            (context as MainActivity).sendCommand("EFECTO FILA SUPERIOR#")
            context.sendCommand(number.toString() + "#")
        },
        modifier = Modifier
            .padding(start = 10.dp, top = 1.dp, bottom = 1.dp, end = 10.dp)
            .width(250.dp)
            .height(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado)
        )
    ) {
        Text(
            text = MatrizEfectos[number],
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Right,
        )
    }
}

@Composable
fun BotoneraEfectosFilaInferior(number: Int) {
    val context = LocalContext.current // Obtener el contexto
    //TODO Añadir matriz para mostrar textos e imagenes distintas según la opcion.
    Button(
        onClick = {
            TextoEstadoActual = "ESTADO ACTUAL: ENCENDIDO"
            ColorTextoEstadoActual = R.color.verde
            StyleableToast.makeText(
                context,
                "SE HA PULSADO: " + MatrizEfectos[number],
                Toast.LENGTH_LONG
            ).show();
            (context as MainActivity).sendCommand("EFECTO FILA INFERIOR#")
            context.sendCommand(number.toString() + "#")
        },
        modifier = Modifier
            .padding(start = 10.dp, top = 1.dp, bottom = 1.dp, end = 10.dp)
            .width(250.dp)
            .height(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado)
        )
    ) {
        Text(
            text = MatrizEfectos[number],
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Right,
        )
    }
}

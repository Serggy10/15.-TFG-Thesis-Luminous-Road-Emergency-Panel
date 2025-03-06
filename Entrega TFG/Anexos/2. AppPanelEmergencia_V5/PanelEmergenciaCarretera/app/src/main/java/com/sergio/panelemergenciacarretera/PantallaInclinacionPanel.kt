package com.sergio.panelemergenciacarretera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MenuInclinacionPanel(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        LogoEscuelaYTitulos()
        BotonVolver(navController = navController)
        BotoneraOpcionesInclinacion()
        LogoUniversidad()
    }
}

@Composable
fun BotoneraOpcionesInclinacion() {
    val context = LocalContext.current
    var currentProgress by remember { mutableStateOf(0.0f) }
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                CircularProgressIndicator(
                    progress = 0.5f,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationZ = (-90f) },
                    color = Color.Gray,
                    strokeWidth = 15.dp
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "%.0f".format(
                            indicadorInclinacion*360),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                CircularProgressIndicator(
                    progress = indicadorInclinacion,
                    modifier = Modifier
                        .size(150.dp)
                        .width(150.dp)
                        .graphicsLayer { rotationZ = (-90f) },
                    strokeWidth = 15.dp,
                    color = colorResource(id = R.color.morado),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .offset(y = -35.dp)
            ) {
                Button(
                    onClick = {
                        indicadorInclinacion= 0.0f
                        (context as MainActivity).sendCommand("INCLINACION#")
                        context.sendCommand("0" + "#")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado)
                    )
                ) {
                    Text(
                        text = " 0 GRADOS ",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(110.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = {
                        indicadorInclinacion = 0.25f / 2
                        (context as MainActivity).sendCommand("INCLINACION#")
                        context.sendCommand("45" + "#")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado),
                    )
                ) {
                    Text(
                        text = " 45 GRADOS",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(110.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .offset(y = -35.dp)
            ) {
                Button(
                    onClick = {
                        indicadorInclinacion= 0.5f / 2
                        (context as MainActivity).sendCommand("INCLINACION#")
                        context.sendCommand("90" + "#")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado)
                    )
                ) {
                    Text(
                        text = " 90 GRADOS ",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(110.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        indicadorInclinacion= 0.75f / 2
                        (context as MainActivity).sendCommand("INCLINACION#")
                        context.sendCommand("135" + "#")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado),
                    )
                ) {
                    Text(
                        text = " 135 GRADOS",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(110.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -5.dp)
            ) {
                Text(
                    text = "0 ",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(125.dp)
                )
                Text(
                    text = " GRADOS  ",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.requiredWidth(125.dp)
                )
                Text(
                    text = " 180",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.requiredWidth(125.dp)
                )
            }
            Slider(
                value = indicadorInclinacion,
                onValueChange = { newValue ->
                    indicadorInclinacion= newValue
                    if (indicadorInclinacion>= 5) {
                        TextoInclinacion = "DESPLEGADO"
                        ColorTextoInclinacion = R.color.rojo
                    } else {
                        TextoInclinacion = "RECOGIDO"
                        ColorTextoInclinacion = R.color.verde
                    }
                    (context as MainActivity).sendCommand("INCLINACION#")
                    context.sendCommand((indicadorInclinacion*360).toString() + "#")
                },
                valueRange = 0f..0.5f,
                steps = 180,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
        }
    }
}
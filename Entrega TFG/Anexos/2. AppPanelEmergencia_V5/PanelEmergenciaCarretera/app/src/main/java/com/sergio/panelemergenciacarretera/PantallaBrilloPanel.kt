package com.sergio.panelemergenciacarretera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MenuBrilloPanel(navController: NavHostController) {
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
        BotoneraOpcionesIluminacion()
        LogoUniversidad()
    }
}

@Composable
fun BotoneraOpcionesIluminacion() {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically // Alinea verticalmente el contenido de la Row
            ) {
                Text(
                    text = " NIVEL DE LUZ AMBIENTE :",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(250.dp)
                )
                Text(
                    text = iluminacionExterior.toString(),
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.requiredWidth(50.dp)
                )
            }
        }
    }
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically // Alinea verticalmente el contenido de la Row
            ) {
                Text(
                    text = " BRILLO AUTOMÁTICO :",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(180.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    Text(
                        text = "OFF",
                        color = colorResource(id = R.color.morado),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.requiredWidth(50.dp)
                    )
                    Switch(
                        checked = brilloAuto,
                        onCheckedChange = {
                            brilloAuto = !brilloAuto
                            (context as MainActivity).sendCommand("BRILLO AUTO#")
                            if (brilloAuto)
                                context.sendCommand("VERDADERO#")
                            else context.sendCommand("FALSO#")
                        },
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "ON",
                        color = colorResource(id = R.color.morado),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.requiredWidth(50.dp)
                    )
                }
            }
        }
    }
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = "NIVEL DE LUZ MANUAL",
                color = colorResource(id = R.color.morado),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
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
                    text = nivelIluminacion.toString(),
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.requiredWidth(125.dp)
                )
                Text(
                    text = " 10",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.requiredWidth(125.dp)
                )
            }
            Slider(
                value = nivelIluminacion.toFloat(),
                onValueChange = { newValue ->
                    nivelIluminacion = newValue.toInt()
                    brilloAuto = false
                    (context as MainActivity).sendCommand("BRILLO AUTO#")
                    context.sendCommand("FALSO#")
                    (context as MainActivity).sendCommand("BRILLO#")
                    context.sendCommand(nivelIluminacion.toString() + "#")
                },
                valueRange = 1f..10f,
                steps = 9,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
        }
    }
}
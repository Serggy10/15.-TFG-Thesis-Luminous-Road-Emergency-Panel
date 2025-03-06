package com.sergio.panelemergenciacarretera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaPrincipal(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoEscuelaYTitulos()
        EstadoPanel()
        BotonApagar()
        BotoneraOpciones(navController)
        LogoUniversidad()
    }
}

@Composable
fun EstadoPanel() {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .height(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Text(
            text = TextoEstadoActual,
            color = colorResource(id = ColorTextoEstadoActual),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }

    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .height(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Text(
            text = "INCLIANCIÓN ACTUAL DEL PANEL: " + TextoInclinacion,
            color = colorResource(id = ColorTextoInclinacion),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun BotonApagar() {
    val context = LocalContext.current
    Button(
        onClick = {
            TextoEstadoActual= "ESTADO ACTUAL: APAGADO"
                    ColorTextoEstadoActual = R.color.rojo
            (context as MainActivity).sendCommand("APAGAR#")
            context.sendCommand("100#")
                  },
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado)
        )
    ) {
        Text(
            text = "APAGAR EL PANEL",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun BotoneraOpciones(navController: NavHostController) {
    val context = LocalContext.current
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 10.dp)
            ) {
                //**************BOTON PARA IR AL MENÚ DE MENSAJES PERSONALIZADOS DE TEXTO************
                Button(
                    onClick = { navController.navigate(PantallaMenjsajesTexto.route)},
                    modifier = Modifier.height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado)
                    )

                ) {
                    Text(
                        text = "MENSAJE DE TEXTO",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(120.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                //**************BOTON PARA IR AL MENÚ DE MENSAJES PERSONALIZADOS DINAMICOS **********
                Button(
                    modifier = Modifier.height(60.dp),
                    onClick = { navController.navigate(PantallaMenjsajesDinamicos.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado),
                    )
                ) {
                    Text(
                        text = "MENSAJE DINAMICO",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(120.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 10.dp)
            ) {
                //**************BOTON PARA IR AL MENÚ DE INCLINACION DEL PANEL **********************
                Button(
                    modifier = Modifier.height(60.dp),
                    onClick = { (context as MainActivity).sendCommand("SOLICITA DATOS INCLINACION# 1#")
                        navController.navigate(PantallaInclinacionPanel.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado)
                    )
                ) {
                    Text(
                        text = "INCLIANCIÓN DEL PANEL LUMINOSO",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(120.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                //**************BOTON PARA IR AL MENÚ DE BRILLO DEL PANEL ***************************
                Button(
                    modifier = Modifier.height(60.dp),
                    onClick = { (context as MainActivity).sendCommand("SOLICITA DATOS BRILLO# 1#")
                        navController.navigate(PantallaBrilloPanel.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado),
                    )
                ) {
                    Text(
                        text = "BRILLO DEL PANEL LUMINOSO",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(120.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 10.dp)
            ) {
                //**************BOTON PARA IR AL MENÚ DE MENSAJES PERSONALIZADOS ********************
                Button(
                    modifier = Modifier.height(60.dp),
                    onClick = { navController.navigate(PantallaMenjsajesPersonalizado.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado)
                    )
                ) {
                    Text(
                        text = "MENSAJE PERSONALIZADO",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(120.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                //**************BOTON PARA IR AL MENÚ DE AJUSTES ************************************
                Button(
                    modifier = Modifier.height(60.dp),
                    onClick = { navController.navigate(PantallaAjustes.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.morado),
                    )
                ) {
                    Text(
                        text = "AJUSTES BLUETOOTH",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .height(35.dp)
                            .requiredWidth(120.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun LogoUniversidad() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .height(100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.escudouva),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

package com.sergio.panelemergenciacarretera

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.muddz.styleabletoast.StyleableToast

@Composable
fun MenuMensajeDinamicos(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoEscuelaYTitulos()
        BotonVolver(navController = navController)
        ListaOpcionesDinamico()
        LogoUniversidad()
    }
}

@Composable
fun ListaOpcionesDinamico() {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(MatrizEfectosPredeterminado.size) { number ->
            BotoneraOpcionesDinamico(number)
        }
    }
}

@Composable
fun BotoneraOpcionesDinamico(number: Int) {
    val context = LocalContext.current // Obtener el contexto
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 5.dp, bottom = 5.dp)
            .height(70.dp),//.wrapContentSize(Alignment.Center),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp, vertical = 1.dp)
                .wrapContentSize(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //TODO Añadir matriz para mostrar textos e imagenes distintas según la opcion.
            Button(
                onClick = {
                    TextoEstadoActual = "ESTADO ACTUAL: ENCENDIDO"
                    ColorTextoEstadoActual = R.color.verde
                    StyleableToast.makeText(
                        context,
                        "SE HA PULSADO: " + MatrizEfectosPredeterminado[number],
                        Toast.LENGTH_LONG
                    ).show();
                    (context as MainActivity).sendCommand("EFECTO PREDETERMINADO#")
                    context.sendCommand(number.toString() + "#")
                },
                modifier = Modifier
                    .padding(start = 2.dp, top = 5.dp, bottom = 5.dp)
                    .width(350.dp),//.width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.morado)
                )
            ) {
                Text(
                    text = MatrizEfectosPredeterminado[number],//AÑADIR MATRIZ: " + number,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            /*Image(
                painter = painterResource(id = R.drawable.escudouva),
                //painter = painterResource(id = MatrizImagenesDinamico[number]),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 1.dp, bottom = 1.dp),
            )*/
        }
    }
}

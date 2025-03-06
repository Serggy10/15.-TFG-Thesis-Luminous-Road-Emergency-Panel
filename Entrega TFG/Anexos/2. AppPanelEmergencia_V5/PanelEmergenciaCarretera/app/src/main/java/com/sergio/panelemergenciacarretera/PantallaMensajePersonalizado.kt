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
fun MenuMensajePersonalizado(navController: NavHostController) {
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
        EntradasTextoYOpciones(navController = navController)
        LogoUniversidad()
    }
}

@Composable
fun ListaEfectos() {
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = Modifier.fillMaxWidth()) {
        items(MatrizEfectos.size){
                number -> BotoneraEfectos(number)
        }
    }
}

@Composable
fun BotoneraEfectos(number: Int){
    val context = LocalContext.current // Obtener el contexto
    //TODO Añadir matriz para mostrar textos e imagenes distintas según la opcion.
    Button(
        onClick = {
            TextoEstadoActual= "ESTADO ACTUAL: ENCENDIDO"
            ColorTextoEstadoActual = R.color.verde
            StyleableToast.makeText(
                context,
                "SE HA PULSADO: " + MatrizEfectos[number] ,
                Toast.LENGTH_LONG
            ).show();
            (context as MainActivity).sendCommand("EFECTO UNA FILA#")
            context.sendCommand(number.toString() + "#")
                  /*TODO*/ },
        modifier = Modifier
            .padding(start = 10.dp, top = 2.dp, bottom = 2.dp, end=10.dp)
            .width(250.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado)
        )
    ) {
        Text(
            text = MatrizEfectos[number],//AÑADIR MATRIZ: " + number,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun EntradasTextoYOpciones(navController: NavHostController) {
    val context = LocalContext.current
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
                text = "TEXTO MOSTRADO EN EL PANEL:",
                color = colorResource(id = R.color.morado),
                fontSize = 16.sp
            )
            BasicTextField(
                value = textoUnaFila,
                onValueChange = {
                    textoUnaFila = it
                    (context as MainActivity).sendCommand("TEXTO UNA FILA#")
                    context.sendCommand(textoUnaFila + "#")
                },
                textStyle = TextStyle(
                    color = colorResource(id = R.color.rosa_claro),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    // Otros atributos de estilo que desees
                )
            )
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
                text = "VELOCIDAD DE DESPLAZAMIENTO",
                color = colorResource(id = R.color.morado),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -5.dp)
            ) {
                Text(
                    text = "100",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(125.dp)
                )
                Text(
                    text = velocidadUnaFila.toString(),
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.requiredWidth(125.dp)
                )
                Text(
                    text = " 500",
                    color = colorResource(id = R.color.morado),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.requiredWidth(125.dp)
                )
            }
            Slider(
                value = velocidadUnaFila.toFloat(),
                onValueChange = { newValue ->
                    velocidadUnaFila = newValue.toInt()
                    (context as MainActivity).sendCommand("VELOCIDAD UNA FILA#")
                    context.sendCommand(velocidadUnaFila.toString() + "#")
                },
                valueRange = 100f..500f,
                steps = (500-100)/25,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
        }
    }
    Box(
        modifier = Modifier
            //.size(250.dp)
            .fillMaxWidth()
            .width(400.dp)
            .height(170.dp),
            //.align(Alignment.CenterHorizontally)
    ){
        Card(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){
            ListaEfectos()
        }
    }
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)

    ){
        Box(
            modifier = Modifier
                .height(65.dp)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Button(
                onClick = { navController.navigate(PantallaDosFilas.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.morado)
                )
            )
            {
                Text(
                    text = "MENSAJE EN DOS FILAS",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
}
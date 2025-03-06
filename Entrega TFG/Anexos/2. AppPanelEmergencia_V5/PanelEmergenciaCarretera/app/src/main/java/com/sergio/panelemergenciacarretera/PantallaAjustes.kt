package com.sergio.panelemergenciacarretera

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ArrayAdapter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun MenuAjustes(
    navController: NavHostController,
    mAddressDevices: ArrayAdapter<String>?,
    mNameDevices: ArrayAdapter<String>?,
) {
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
        BotoneraConexion(
            mAddressDevices = mAddressDevices,
            mNameDevices = mNameDevices,
            navController = navController,
        )
        LogoUniversidad()
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BotoneraConexion(
    mAddressDevices: ArrayAdapter<String>?,
    mNameDevices: ArrayAdapter<String>?,
    navController: NavHostController,
) {
    val context = LocalContext.current
    Column {
        Card(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .height(100.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 10.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            (context as MainActivity).activaBT()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.morado),
                        )
                    ) {
                        Text(
                            text = "ACTIVAR BLUETOOTH",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .requiredWidth(120.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            (context as MainActivity).buscaDispositivos()
                            navController.navigate(PantallaAjustes.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.morado),
                        )
                    ) {
                        Text(
                            text = "BUSCAR DISPOSITIVOS",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.requiredWidth(120.dp)
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .height(260.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = " DISPOSITIVOS ENCONTRADOS :",
                    color = colorResource(id = R.color.rosa_claro),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(250.dp)
                )
                Divider(
                    startIndent = 4.dp,
                    thickness = 1.dp,
                    color = colorResource(id = R.color.rosa_claro),
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                LazyColumn(modifier = Modifier.height(150.dp)) {
                    items(mAddressDevices?.count ?: 0) { number ->
                        BotoneraDispositivos(number, mAddressDevices, mNameDevices, navController)
                    }
                }
                Divider(
                    startIndent = 4.dp,
                    thickness = 1.dp,
                    color = colorResource(id = R.color.rosa_claro),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = (context as MainActivity).conexionExitosa,
                    color = colorResource(id = R.color.rosa_claro),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.requiredWidth(250.dp)
                )
            }
        }
    }
}

@Composable
fun BotoneraDispositivos(
    number: Int,
    mAddressDevices: ArrayAdapter<String>?,
    mNameDevices: ArrayAdapter<String>?,
    navController: NavHostController,
) {
    val context = LocalContext.current // Obtain the context
    Button(
        onClick = {
            Log.d(
                "BotoneraDispositivos",
                "Se ha pulsado el botón: " + mNameDevices?.getItem(number).toString()
            )
            (context as MainActivity).connectToDevice(number)
            navController.navigate(PantallaAjustes.route)
        },
        modifier = Modifier
            .padding(start = 10.dp, top = 2.dp, bottom = 2.dp, end = 10.dp)
            .width(250.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.morado)
        )
    ) {
        Text(
            text = mNameDevices?.getItem(number).toString(),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
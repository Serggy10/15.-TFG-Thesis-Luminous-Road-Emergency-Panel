package com.sergio.panelemergenciacarretera

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.muddz.styleabletoast.StyleableToast
import java.io.IOException
import java.util.UUID
import androidx.compose.runtime.remember

var TextoEstadoActual by mutableStateOf("ESTADO ACTUAL: APAGADO")
var TextoInclinacion by mutableStateOf("RECOGIDO")
var ColorTextoEstadoActual by mutableStateOf(R.color.rojo)
var ColorTextoInclinacion by mutableStateOf(R.color.verde)
var indicadorInclinacion by mutableStateOf(0.0f)
var brilloAuto by mutableStateOf(false)
var iluminacionExterior by mutableStateOf(0)
var nivelIluminacion by mutableStateOf(0)
var velocidadUnaFila by mutableStateOf(300)
var textoUnaFila by mutableStateOf("Texto inicial")
var velocidadFilaSuperior by mutableStateOf(300)
var velocidadFilaInferior by mutableStateOf(300)
var textoZonaSuperior by mutableStateOf("Texto superior")
var textoZonaInferior by mutableStateOf("Texto inferior")

const val REQUEST_ENABLE_BT = 1
const val REQUEST_ENABLE_SCAN = 2

class MainActivity : ComponentActivity() {

    lateinit var mBtAdapter: BluetoothAdapter

    var mAddressDevices: ArrayAdapter<String>? = null
    var mNameDevices: ArrayAdapter<String>? = null
    var permisosActivados = false
    var conexionExitosa = ""


    val someActivityResultLauncher2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == REQUEST_ENABLE_BT) {
            Log.i("MainActivity", "ACTIVIDAD REGISTRADA")
        }
    }

    companion object {

        var m_bluetoothSocket: BluetoothSocket? = null
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        lateinit var m_address: String
        lateinit var m_name: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navegacion(
                mAddressDevices = mAddressDevices,
                mNameDevices = mNameDevices,
            )
        }
        mAddressDevices = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        mNameDevices = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        mBtAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        if (mBtAdapter == null) {
            StyleableToast.makeText(
                this,
                "Bluetooth no está disponible en este dipositivo",
                Toast.LENGTH_LONG
            ).show()
        } else {
            StyleableToast.makeText(
                this,
                "Bluetooth está disponible en este dispositivo",
                Toast.LENGTH_LONG
            )
                .show()
        }
        Thread {
            while (true) {
                // Llama a la función para leer datos en un bucle infinito
                readData()
                // Añade un pequeño retardo para no sobrecargar el hilo
                Thread.sleep(1000)
            }
        }.start()
    }

    fun readData() {
        if (m_bluetoothSocket != null && m_bluetoothSocket!!.isConnected) {
            try {
                val inputStream = m_bluetoothSocket!!.inputStream
                val buffer = ByteArray(1024)
                val bytesRead = inputStream.read(buffer)
                val receivedData = String(buffer, 0, bytesRead)
                Log.d("Desarrollo", "Received data: $receivedData")
                if (receivedData.startsWith("INCLINACION:")) {
                    indicadorInclinacion = receivedData.substring(12).toFloat()
                    Log.d("Desarrollo", "inclinacion recibida: $indicadorInclinacion")
                }
                if (receivedData.startsWith("DATOS BRILLO:")) {
                    val datos = receivedData.substring(6).trim().split("|")
                    for (dato in datos) {
                        val keyValue = dato.split(":")
                        if (keyValue.size == 2) {
                            val key = keyValue[0].trim()
                            val value = keyValue[1].trim()
                            // Ahora puedes manejar la clave y el valor según tus necesidades
                            when (key) {
                                "BRILLO" -> {
                                    nivelIluminacion = value.toInt()
                                    Log.d("Desarrollo", "nivel iluminacion: $nivelIluminacion")
                                    // Hacer algo con el valor del brillo
                                }

                                "ILUM_EXTERIOR" -> {
                                    iluminacionExterior = value.toInt()
                                    Log.d("Desarrollo", "iluminacion exterior: $iluminacionExterior")
                                    // Hacer algo con el valor de la iluminación exterior
                                }

                                "BRILLO_AUTO" -> {
                                    brilloAuto = value.toBoolean()
                                    Log.d("Desarrollo", "Brilloauto: $brilloAuto")
                                    // Hacer algo con el valor del brillo automático
                                }
                                // Puedes agregar más casos según sea necesario
                            }
                        }
                    }
                }
                /*if (receivedData.startsWith("BRILLO:")) {
                    try {
                        val substringValue = receivedData.substring(7).trim()
                        nivelIluminacion = substringValue.toInt()
                        Log.d("Desarrollo", "Ilum exterior recibido: $nivelIluminacion")
                    } catch (e: NumberFormatException) {
                        Log.e("Desarrollo", "Error al convertir la subcadena a entero: $e")
                        // Puedes manejar la excepción de acuerdo a tus necesidades
                    }
                }*/
                /*if (receivedData.startsWith("BRILLO AUTO:0")) {
                    brilloAuto = false
                    Log.d("Desarrollo", "Brillo auto recibido: $brilloAuto")
                }
                if (receivedData.startsWith("BRILLO AUTO:1")) {
                    brilloAuto = true
                    Log.d("Desarrollo", "Brillo auto recibido: $brilloAuto")
                }*/
                /*                if (receivedData.startsWith("ILUM EXTERIOR:")) {
                                    try {
                                        val substringValue = receivedData.substring(14)
                                        iluminacionExterior = substringValue.toInt()
                                        Log.d("Desarrollo", "Ilum exterior recibido: $iluminacionExterior")
                                    } catch (e: NumberFormatException) {
                                        Log.e("Desarrollo", "Error al convertir la subcadena a entero: $e")
                                        // Puedes manejar la excepción de acuerdo a tus necesidades
                                    }
                                }*/

                // Ahora puedes procesar la información recibida según tus necesidades
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("Desarrollo", "Error reading data")
            }
        } else {
            Log.d("Desarrollo", "Bluetooth socket not connected.")
        }
    }

    fun activaBT() {
        Log.d("BotonActivaBT", "Se ha pulsado el boton")
        if (mBtAdapter?.isEnabled == true && permisosActivados == true) {
            //Si ya está activado
            StyleableToast.makeText(
                this,
                "BLUETOOTH YA SE ENCUENTRA ACTIVADO",
                Toast.LENGTH_LONG
            ).show()
            StyleableToast.makeText(
                this,
                "LOS PERMISOS NECESARIOS YA SE ENCUNTRAN ACTIVOS",
                Toast.LENGTH_LONG
            ).show()
        } else {
            //Encender Bluetooth
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si no tienes el permiso, solicítalo
                Log.d(
                    "BotonActivaBT",
                    "No tengo los permisos, intentando activarlos"
                )
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ENABLE_BT
                )
            } else {
                // Ya tienes el permiso, realiza la operación
                val enableBtIntent =
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                Log.d(
                    "BotonActivaBT",
                    "El permiso está concedido, se realizará la operacion"
                )
                someActivityResultLauncher2.launch(enableBtIntent)
            }
            permisosActivados = true
        }
        Log.d("BotonActivaBT", "Boton ACTIVAR BLUETOOTH activado")
    }

    @SuppressLint("MissingPermission")
    fun buscaDispositivos() {
        if (permisosActivados == false) {
            StyleableToast.makeText(
                this,
                "ACTIVE PRIMERO LOS PERMISOS",
                Toast.LENGTH_LONG
            )
                .show()
        } else {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si no tienes el permiso, solicítalo
                Log.d(
                    "BotonActivaBT",
                    "No tengo los permisos, intentando activarlos"
                )
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ENABLE_BT
                )
            } else {
                // Ya tienes el permiso, realiza la operación
                val enableBtIntent =
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                Log.d(
                    "BotonActivaBT",
                    "El permiso está concedido, se realizará la operacion"
                )
            }
            if (mBtAdapter.isEnabled) {
                Log.d("Desarrollo", "Se ha pulsado DispBT. El BT está activado")
                Log.d(
                    "Desarrollo",
                    "Se limpiará a continuacion las direcciones de los dispositivos BT"
                )
                mAddressDevices!!.clear()
                Log.d(
                    "Desarrollo",
                    "Se limpiará a continuacion los nombres de los dispositivos BT"
                )
                mNameDevices!!.clear()

                Log.d("Desarrollo", "Se añadirán nuevos dispositivos BT")
                val pairedDevices: Set<BluetoothDevice>? = mBtAdapter?.bondedDevices
                pairedDevices?.forEach { device ->
                    val deviceName = device.name
                    Log.d("Desarrollo", "deviceName: $deviceName")
                    val deviceHardwareAddress = device.address // MAC address
                    Log.d("Desarrollo", "deviceHardwareAddres: $deviceHardwareAddress")
                    mAddressDevices!!.add(deviceHardwareAddress)
                    mNameDevices!!.add(deviceName.toString())
                }
            } else {
                val noDevices = "Ningun dispositivo pudo ser emparejado"
                mAddressDevices!!.add(noDevices)
                mNameDevices!!.add(noDevices)
                StyleableToast.makeText(
                    this,
                    "VAYA A AJUSTES  DE BLUETOOTH Y VINCULE ALGÚN DISPOSITIVO",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    fun sendCommand(input: String) {
        if (m_bluetoothSocket != null && m_bluetoothSocket!!.isConnected) {
            // El socket está conectado, puedes enviar el mensaje.
            if (m_bluetoothSocket != null) {
                try {
                    Log.d("Desarrollo", "m_bluetoothSocket: $m_bluetoothSocket")
                    m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                    Log.d("Desarrollo", "Se ha enviado el mensaje")
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d("Desarrollo", "ha fallado el enviar mensaje")
                }
            } else {
                Log.d("Desarrollo", "m_bluetoothSocket nulo")
            }
        } else {
            Log.d("Desarrollo", "El socket Bluetooth no está conectado.")
        }
    }

    fun connectToDevice(number: Int) {
        try {
            Log.d("Desarrollo", "Intentando conectar con dispositivo")
            if (m_bluetoothSocket == null) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("Desarrollo", "No tengo los permisos de SCAN, intentando activarlos")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                        REQUEST_ENABLE_SCAN
                    )
                } else {
                    m_address = mAddressDevices!!.getItem(number).toString()
                    m_name = mNameDevices!!.getItem(number).toString()
                    Log.d("Desarrollo", "m_address: $m_address")
                    StyleableToast.makeText(this, "CONECTANDO A: " + m_name, Toast.LENGTH_LONG)
                        .show()
                    Log.d("Desarrollo", "Cancelar discovery")
                    mBtAdapter.cancelDiscovery()
                    Log.d("Desarrollo", "Discovery cancelado?")
                    val device: BluetoothDevice = mBtAdapter.getRemoteDevice(m_address)
                    Log.d("Desarrollo", "BluetoothDevice: $device")
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    Log.d("Desarrollo", "m_bluetoothSocket: $m_bluetoothSocket")
                    m_bluetoothSocket!!.connect()
                    Log.d("Desarrollo", "Conectado")
                }
            }

            StyleableToast.makeText(this, "CONEXION EXITOSA", Toast.LENGTH_LONG).show()
            conexionExitosa = "CONECTADO a $m_name"
        } catch (e: IOException) {
            e.printStackTrace()
            StyleableToast.makeText(this, "ERROR DE CONEXION", Toast.LENGTH_LONG).show()
        }
    }
}


@Composable
fun Navegacion(
    mAddressDevices: ArrayAdapter<String>?,
    mNameDevices: ArrayAdapter<String>?,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PantallaPrincipal.route) {
        composable(PantallaPrincipal.route) {
            PantallaPrincipal(navController)
        }
        composable(PantallaMenjsajesTexto.route) {
            MenuMensajeTexto(navController)
        }
        composable(PantallaMenjsajesDinamicos.route) {
            MenuMensajeDinamicos(navController)
        }
        composable(PantallaInclinacionPanel.route) {
            MenuInclinacionPanel(navController)
        }
        composable(PantallaBrilloPanel.route) {
            MenuBrilloPanel(navController)
        }
        composable(PantallaMenjsajesPersonalizado.route) {
            MenuMensajePersonalizado(navController)
        }
        composable(PantallaAjustes.route) {
            MenuAjustes(
                navController = navController,
                mAddressDevices = mAddressDevices,
                mNameDevices = mNameDevices,
            )
        }
        composable(PantallaDosFilas.route) {
            MenuMensajePersonalizadoDosFilas(navController)
        }
    }
}


@Composable
fun BotonVolver(navController: NavHostController) {
    Button(
        onClick = {
            Log.d("Desarrollo", "indicador $indicadorInclinacion")
            if (indicadorInclinacion>= 5/360.0) {
                TextoInclinacion = "DESPLEGADO"
                ColorTextoInclinacion = R.color.rojo
            } else {
                TextoInclinacion = "RECOGIDO"
                ColorTextoInclinacion = R.color.verde
            }
            navController.navigate(PantallaPrincipal.route) },
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
fun LogoEscuelaYTitulos() {
    Image(
        painter = painterResource(R.drawable.logoeii),
        contentDescription = "",
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "PANEL DE EMERGENCIA EN CARRETERA",
        color = colorResource(id = R.color.morado),
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        text = "IEIA SERGIO CARRASCO HERNÁNDEZ",
        color = colorResource(id = R.color.morado),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}




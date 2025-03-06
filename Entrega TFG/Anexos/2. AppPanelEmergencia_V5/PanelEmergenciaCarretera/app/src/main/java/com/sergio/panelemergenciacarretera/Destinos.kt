package com.sergio.panelemergenciacarretera

interface Destinos {
    val route: String
}

object PantallaPrincipal : Destinos {
    override val route: String = "PantallaPrincipal"
}

object PantallaMenjsajesTexto : Destinos {
    override val route: String = "Pantalla Mensajes Texto"
}

object PantallaMenjsajesDinamicos : Destinos {
    override val route: String = "Pantalla Mensajes Dinamicos"
}

object PantallaInclinacionPanel : Destinos {
    override val route: String = "Pantalla Inclinación Panel"
}

object PantallaBrilloPanel : Destinos {
    override val route: String = "Pantalla Brillo Panel"
}

object PantallaMenjsajesPersonalizado : Destinos {
    override val route: String = "Pantalla Mensajes Personalizados"
}

object PantallaAjustes : Destinos {
    override val route: String = "Pantalla Ajustes"
}

object PantallaDosFilas : Destinos {
    override val route: String = "Pantalla Mensaje Personalizado Dos Filas"
}
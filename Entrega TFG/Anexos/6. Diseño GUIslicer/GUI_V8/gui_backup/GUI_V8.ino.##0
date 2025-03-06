//<App !Start!>
// FILE: [GUI_V8.ino]
// Created by GUIslice Builder version: [0.17.b24]
//
// GUIslice Builder Generated File
//
// For the latest guides, updates and support view:
// https://github.com/ImpulseAdventure/GUIslice
//
//<App !End!>

// ------------------------------------------------
// Headers to include
// ------------------------------------------------
#include "GUI_V8_GSLC.h"
#include "Addons/XRingGauge.h"
//#include "Addons/XCheckbox.h"
#include <FS.h>

TFT_eSPI tft = TFT_eSPI();

#define CALIBRATION_FILE "/TouchCalData1"  // Fichero donde se guardan los archivos de calibración.

bool REPEAT_CAL = false;  // Si se desea repetir la calibración se debe cambiar a true.

void touch_calibrate() {
  uint16_t calData[5];
  uint8_t calDataOK = 0;

  if (!SPIFFS.begin())  // Comprobación de la existencia del sistema de ficheros. Si no existe se formatea la memoria SPIFFS.
  {
    Serial.println("Formatting file system");
    SPIFFS.format();
    SPIFFS.begin();
  }

  if (SPIFFS.exists(CALIBRATION_FILE))  // Comprobación de la existencia del fichero de calibración. Si existe se puede sobreescribir si se desea.
  {
    if (REPEAT_CAL) {
      // Delete if we want to re-calibrate
      SPIFFS.remove(CALIBRATION_FILE);
    } else {
      fs::File f = SPIFFS.open(CALIBRATION_FILE, "r");
      if (f) {
        if (f.readBytes((char*)calData, 14) == 14)
          calDataOK = 1;
        f.close();
      }
    }
  }

  if (calDataOK && !REPEAT_CAL)  // Si los datos son correctos y no se desea repetir la calibración se cargan los datos en la patalla TFT.
  {
    tft.setTouch(calData);
  } else  // Si no se empieza de nuevo la calibración.
  {
    tft.fillScreen(TFT_BLACK);
    tft.setCursor(20, 0);
    tft.setTextFont(2);
    tft.setTextSize(1);
    tft.setTextColor(TFT_WHITE, TFT_BLACK);
    tft.println("Toque las esquinas indicadas");
    tft.setTextFont(1);
    tft.println();

    if (REPEAT_CAL) {
      tft.setTextColor(TFT_RED, TFT_BLACK);
    }
    //
    tft.calibrateTouch(calData, TFT_MAGENTA, TFT_BLACK, 15);  // Función para calibrar la pantalla.
    gslc_SetTouchRemapCal(&m_gui, calData[0], calData[1], calData[2], calData[3]);

    Serial.println(calData[0]);
    Serial.println(calData[1]);
    Serial.println(calData[2]);
    Serial.println(calData[3]);
    Serial.println(calData[4]);

    tft.setTextColor(TFT_GREEN, TFT_BLACK);
    tft.println("Calibración completada!");

    fs::File f = SPIFFS.open(CALIBRATION_FILE, "w");  // Se guardan los datos en la memoria SPIFFS.
    if (f) {
      f.write((const unsigned char*)calData, 14);
      f.close();
    }
  }
}


// ------------------------------------------------
// Program Globals
// ------------------------------------------------

// Save some element references for direct access
//<Save_References !Start!>
gslc_tsElemRef* ArcoInclinacion   = NULL;
gslc_tsElemRef* BarraIluminacionPantalla= NULL;
gslc_tsElemRef* BarraIluminacionPersonalizadaPanel= NULL;
gslc_tsElemRef* BarraInclinacion  = NULL;
gslc_tsElemRef* BarraListaEfectos = NULL;
gslc_tsElemRef* BarraListaEfectosInferior= NULL;
gslc_tsElemRef* BarraListaEfectosSuperior= NULL;
gslc_tsElemRef* BarraMensajesDinamicos= NULL;
gslc_tsElemRef* BarraMensajesTexto= NULL;
gslc_tsElemRef* BarraVelocidadInferior= NULL;
gslc_tsElemRef* BarraVelocidadPersonalizada= NULL;
gslc_tsElemRef* BarraVelocidadSuperior= NULL;
gslc_tsElemRef* BotonAutoIluminacionPanel= NULL;
gslc_tsElemRef* CirculoLogo       = NULL;
gslc_tsElemRef* EntradaTextoInferior= NULL;
gslc_tsElemRef* EntradaTextoSuperior= NULL;
gslc_tsElemRef* ListaEfectos      = NULL;
gslc_tsElemRef* ListaEfectosInferior= NULL;
gslc_tsElemRef* ListaEfectosSuperior= NULL;
gslc_tsElemRef* ListaMensajesDinamico= NULL;
gslc_tsElemRef* ListaMensajesTexto= NULL;
gslc_tsElemRef* TextoPersonalizado= NULL;
gslc_tsElemRef* VariableEstadoActual= NULL;
gslc_tsElemRef* VariableTextoLuzAmbiente= NULL;
gslc_tsElemRef* m_pElemKeyPadAlpha= NULL;
//<Save_References !End!>

// Define debug message function
static int16_t DebugOut(char ch) {
  if (ch == (char)'\n') Serial.println("");
  else Serial.write(ch);
  return 0;
}

// ------------------------------------------------
// Callback Methods
// ------------------------------------------------
// Common Button callback
bool CbBtnCommon(void* pvGui, void* pvElemRef, gslc_teTouch eTouch, int16_t nX, int16_t nY) {
  // Typecast the parameters to match the GUI and element types
  gslc_tsGui* pGui = (gslc_tsGui*)(pvGui);
  gslc_tsElemRef* pElemRef = (gslc_tsElemRef*)(pvElemRef);
  gslc_tsElem* pElem = gslc_GetElemFromRef(pGui, pElemRef);

  if (eTouch == GSLC_TOUCH_UP_IN) {
    // From the element's ID we can determine which button was pressed.
    switch (pElem->nId) {
//<Button Enums !Start!>
      case BotonMenuMensajesTexto:
        gslc_SetPageCur(&m_gui, MenuMensajesTexto);
        break;
      case BotonMenuEfectosDinamicos:
        gslc_SetPageCur(&m_gui, MenuEfectosDinamicos);
        break;
      case BotonMenuInclinacionPanel:
        gslc_SetPageCur(&m_gui, MenuInclinacionPanel);
        break;
      case BotonMenuIluminacionPanel:
        gslc_SetPageCur(&m_gui, MenuIluminacionPanel);
        break;
      case BotonMenuAjustes:
        gslc_SetPageCur(&m_gui, MenuAjustes);
        break;
      case BotonMenuMensajePersonalizado:
        gslc_SetPageCur(&m_gui, MenuMensajePersonalizado);
        break;
      case E_BotonApagar:
        break;
      case BotonVolver1:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case BotonVolver2:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case BotonVolver3:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case BotonHome:
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_ArcoInclinacion);
        gslc_ElemXRingGaugeSetVal(pGui, pElemRef, 0);
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_BarraInclinacion);
        gslc_ElemXSeekbarSetPos(pGui, pElemRef, 0);
        break;
      case Boton90:
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_ArcoInclinacion);
        gslc_ElemXRingGaugeSetVal(pGui, pElemRef, 90);
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_BarraInclinacion);
        gslc_ElemXSeekbarSetPos(pGui, pElemRef, 90);
        break;
      case Boton45:
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_ArcoInclinacion);
        gslc_ElemXRingGaugeSetVal(pGui, pElemRef, 45);
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_BarraInclinacion);
        gslc_ElemXSeekbarSetPos(pGui, pElemRef, 45);
        break;
      case Boton135:
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_ArcoInclinacion);
        gslc_ElemXRingGaugeSetVal(pGui, pElemRef, 135);
        pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_BarraInclinacion);
        gslc_ElemXSeekbarSetPos(pGui, pElemRef, 135);
        break;
      case BotonVolver4:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case E_BotonCalibrado:
        REPEAT_CAL = true;
        touch_calibrate();  // Comprobación de si la pantalla necesita una calibración o no.
        InitGUIslice_gen();
        break;
      case BotonVolver5:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case E_TextoPersonalizado:
        // Clicked on edit field, so show popup box and associate with this text field
        gslc_ElemXKeyPadInputAsk(&m_gui, m_pElemKeyPadAlpha, E_POP_KEYPAD_ALPHA, TextoPersonalizado);
        break;
      case E_BotonDosFilas:
        gslc_SetPageCur(&m_gui, MenuMensajePersonalizadoDos);
        break;
      case BotonVolver6:
        gslc_SetPageCur(&m_gui, E_MenuPrincipal);
        break;
      case E_BotonAutoIluminacionPanel:
        // TODO Add code for Toggle button ON/OFF state
        if (gslc_ElemXTogglebtnGetState(&m_gui, BotonAutoIluminacionPanel)) {
          ;
        }
        break;
      case E_EntradaTextoSuperior:
        // Clicked on edit field, so show popup box and associate with this text field
        gslc_ElemXKeyPadInputAsk(&m_gui, m_pElemKeyPadAlpha, E_POP_KEYPAD_ALPHA, EntradaTextoSuperior);
        break;
      case E_EntradaTextoInferior:
        // Clicked on edit field, so show popup box and associate with this text field
        gslc_ElemXKeyPadInputAsk(&m_gui, m_pElemKeyPadAlpha, E_POP_KEYPAD_ALPHA, EntradaTextoInferior);
        break;
      case E_BotonVolverUnaFila:
        gslc_SetPageCur(&m_gui, MenuMensajePersonalizado);
        break;
//<Button Enums !End!>
      default:
        break;
    }
  }
  return true;
}


// KeyPad Input Ready callback
bool CbKeypad(void* pvGui, void* pvElemRef, int16_t nState, void* pvData) {
  gslc_tsGui* pGui = (gslc_tsGui*)pvGui;
  gslc_tsElemRef* pElemRef = (gslc_tsElemRef*)(pvElemRef);
  gslc_tsElem* pElem = gslc_GetElemFromRef(pGui, pElemRef);

  // From the pvData we can get the ID element that is ready.
  int16_t nTargetElemId = gslc_ElemXKeyPadDataTargetIdGet(pGui, pvData);
  if (nState == XKEYPAD_CB_STATE_DONE) {
    // User clicked on Enter to leave popup
    // - If we have a popup active, pass the return value directly to
    //   the corresponding value field
    switch (nTargetElemId) {
//<Keypad Enums !Start!>

      case E_TextoPersonalizado:
        gslc_ElemXKeyPadInputGet(pGui, TextoPersonalizado, pvData);
        gslc_PopupHide(&m_gui);
        break;
      case E_EntradaTextoSuperior:
        gslc_ElemXKeyPadInputGet(pGui, EntradaTextoSuperior, pvData);
        gslc_PopupHide(&m_gui);
        break;
      case E_EntradaTextoInferior:
        gslc_ElemXKeyPadInputGet(pGui, EntradaTextoInferior, pvData);
        gslc_PopupHide(&m_gui);
        break;
//<Keypad Enums !End!>
      default:
        break;
    }
  } else if (nState == XKEYPAD_CB_STATE_CANCEL) {
    // User escaped from popup, so don't update values
    gslc_PopupHide(&m_gui);
  }
  return true;
}
//<Spinner Callback !Start!>
//<Spinner Callback !End!>
bool CbListbox(void* pvGui, void* pvElemRef, int16_t nSelId) {
  gslc_tsGui* pGui = (gslc_tsGui*)(pvGui);
  gslc_tsElemRef* pElemRef = (gslc_tsElemRef*)(pvElemRef);
  gslc_tsElem* pElem = gslc_GetElemFromRef(pGui, pElemRef);
  char acTxt[MAX_STR + 1];

  if (pElemRef == NULL) {
    return false;
  }

  // From the element's ID we can determine which listbox was active.
  switch (pElem->nId) {
//<Listbox Enums !Start!>

    case E_ListaEfectos:
      if (nSelId != XLISTBOX_SEL_NONE) {
        gslc_ElemXListboxGetItem(&m_gui, pElemRef, nSelId, acTxt, MAX_STR);
      }
      break;
    case E_ListaEfectosSuperior:
      if (nSelId != XLISTBOX_SEL_NONE) {
        gslc_ElemXListboxGetItem(&m_gui, pElemRef, nSelId, acTxt, MAX_STR);
      }
      break;
    case E_ListaEfectosInferior:
      if (nSelId != XLISTBOX_SEL_NONE) {
        gslc_ElemXListboxGetItem(&m_gui, pElemRef, nSelId, acTxt, MAX_STR);
      }
      break;
    case E_ListaMensajesTexto:
      if (nSelId != XLISTBOX_SEL_NONE) {
        gslc_ElemXListboxGetItem(&m_gui, pElemRef, nSelId, acTxt, MAX_STR);
      }
      break;
    case E_ListaMensajesDinamicos:
      if (nSelId != XLISTBOX_SEL_NONE) {
        gslc_ElemXListboxGetItem(&m_gui, pElemRef, nSelId, acTxt, MAX_STR);
      }
      break;
//<Listbox Enums !End!>
    default:
      break;
  }
  return true;
}
//<Draw Callback !Start!>
//<Draw Callback !End!>


// Callback function for when a slider's position has been updated
bool CbSlidePos(void* pvGui, void* pvElemRef, int16_t nPos) {
  gslc_tsGui* pGui = (gslc_tsGui*)(pvGui);
  gslc_tsElemRef* pElemRef = (gslc_tsElemRef*)(pvElemRef);
  gslc_tsElem* pElem = gslc_GetElemFromRef(pGui, pElemRef);
  int16_t nVal;


  // From the element's ID we can determine which slider was updated.
  switch (pElem->nId) {
//<Slider Enums !Start!>

    case E_BarraInclinacion:
      // Fetch the slider position
      nVal = gslc_ElemXSeekbarGetPos(pGui, BarraInclinacion);
      pElemRef = gslc_PageFindElemById(pGui, MenuInclinacionPanel, E_ArcoInclinacion);
      gslc_ElemXRingGaugeSetVal(pGui, pElemRef, nVal);

      break;
    case E_BarraIluminacionPersonalizadaPanel:
      // Fetch the slider position
      nVal = gslc_ElemXSeekbarGetPos(pGui, BarraIluminacionPersonalizadaPanel);
      pElemRef = gslc_PageFindElemById(pGui, MenuIluminacionPanel, E_BotonAutoIluminacionPanel);
      gslc_ElemXTogglebtnSetState(pGui, pElemRef, false);
      break;
    case E_BarraListaEfectos:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, BarraListaEfectos);
      Serial.println("BarraListaEfectos:" + String(nVal));
      pElemRef = gslc_PageFindElemById(pGui, MenuMensajePersonalizado, E_ListaEfectos);
      gslc_ElemXListboxSetScrollPos(pGui, pElemRef, 2 * nVal);
      break;
    case E_BarraVelocidadPersonalizada:
      // Fetch the slider position
      nVal = gslc_ElemXSeekbarGetPos(pGui, BarraVelocidadPersonalizada);
      break;
    case E_BarraListaEfectosSuperior:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, BarraListaEfectosSuperior);
      pElemRef = gslc_PageFindElemById(pGui, MenuMensajePersonalizadoDos, E_ListaEfectosSuperior);
      gslc_ElemXListboxSetScrollPos(pGui, pElemRef, nVal);
      break;
    case E_BarraVelocidadSuperior:
      // Fetch the slider position
      nVal = gslc_ElemXSeekbarGetPos(pGui, BarraVelocidadSuperior);
      break;
    case E_BarraVelocidadInferior:
      // Fetch the slider position
      nVal = gslc_ElemXSeekbarGetPos(pGui, BarraVelocidadSuperior);
      break;
    case E_BarraListaEfectosInferior:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, BarraListaEfectosInferior);
      pElemRef = gslc_PageFindElemById(pGui, MenuMensajePersonalizadoDos, E_ListaEfectosInferior);
      gslc_ElemXListboxSetScrollPos(pGui, pElemRef, nVal);
      break;
    case E_BarraIluminacionPantalla:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, pElemRef);
      analogWrite(22, map(nVal, 1, 10, 10, 255));
      break;
    case E_BarraMensajesTexto:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, BarraMensajesTexto);
      pElemRef = gslc_PageFindElemById(pGui, MenuMensajesTexto, E_ListaMensajesTexto);
      gslc_ElemXListboxSetScrollPos(pGui, pElemRef, nVal);
      break;
    case E_BarraMensajesDinamicos:
      // Fetch the slider position
      nVal = gslc_ElemXSliderGetPos(pGui, BarraMensajesDinamicos);
      pElemRef = gslc_PageFindElemById(pGui, MenuEfectosDinamicos, E_ListaMensajesDinamicos);
      gslc_ElemXListboxSetScrollPos(pGui, pElemRef, nVal);
      break;
//<Slider Enums !End!>
    default:
      break;
  }

  return true;
}
//<Tick Callback !Start!>
//<Tick Callback !End!>

void setup() {
  // ------------------------------------------------
  // Initialize
  // ------------------------------------------------
  Serial.begin(9600);
  pinMode(22, OUTPUT);
  analogWrite(22, 10);
  // Wait for USB Serial
  //delay(1000);  // NOTE: Some devices require a delay after Serial.begin() before serial port can be used

  gslc_InitDebug(&DebugOut);

  // ------------------------------------------------
  // Create graphic elements
  // ------------------------------------------------
  InitGUIslice_gen();
  tft.init();                       // Inicializa la pantalla TFT.
  tft.setRotation(1);               // Se puede cambiar la rotación de la pantalla.
  touch_calibrate();                // Comprobación de si la pantalla necesita una calibración o no.
  tft.fillScreen(TFT_BLACK);        // Se rellena la pantalla en color negro para eliminar imagenes anteriores.
  tft.setFreeFont(&FreeMono9pt7b);  // Establece la fuente de la pantalla
}

// -----------------------------------
// Main event loop
// -----------------------------------
void loop() {

  // ------------------------------------------------
  // Update GUI Elements
  // ------------------------------------------------

  //TODO - Add update code for any text, gauges, or sliders

  // ------------------------------------------------
  // Periodically call GUIslice update function
  // ------------------------------------------------
  gslc_Update(&m_gui);
}

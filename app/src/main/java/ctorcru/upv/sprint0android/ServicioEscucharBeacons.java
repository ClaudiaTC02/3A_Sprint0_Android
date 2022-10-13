package ctorcru.upv.sprint0android;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ctorcru.upv.sprint0android.Logica.Logica;
import ctorcru.upv.sprint0android.Modelo.Medicion;
import ctorcru.upv.sprint0android.Modelo.TramaIBeacon;
import ctorcru.upv.sprint0android.Modelo.Utilidades;

// ---------------------------------------------------------------------------------------------
/**
 * @brief Esta clase es la encargada de escuchar los beacons y coger la medición
 * Autora: Claudia Torres Cruz
 * Archivo: ServicioEscucharBeacons.java
 **/
// ---------------------------------------------------------------------------------------------
public class ServicioEscucharBeacons extends IntentService {
    // ---------------------------------------------------------------------------------------------
    // atributos
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = "Sprintct";
    private long tiempoDeEspera = 10000;
    private boolean seguir = true;
    private BluetoothLeScanner elEscanner;
    private ScanCallback callbackDelEscaneo = null;
    private String dispotivo = "GTI-3A-ClaudiaTorresCruz";
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este es el constructor de nuestra clase
     * @return objeto ServicioEscucharBeacons
     * Diseño: --> ServicioEcucharBeacons() --> ServicioEscucharBeacons
     **/
    // ---------------------------------------------------------------------------------------------
    public ServicioEscucharBeacons() {
        super("HelloIntentService");
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de parar el servicio
     * Diseño:  --> parar() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void parar () {
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() " );
        if ( this.seguir == false ) {
            return;
        }
        this.seguir = false;
        this.stopSelf();
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba " );
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de detener la búsquedas de dispositivos
     * Diseño:  --> detenerBusquedaDispositivosBTLE() -->
     **/
    // ---------------------------------------------------------------------------------------------
    private void detenerBusquedaDispositivosBTLE(){
        if(this.callbackDelEscaneo==null){ return; }
        this.elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo=null;
    }//()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se ejecuta cuando se destruye el servicio
     * Diseño:  --> onDestroy() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void onDestroy() {
        Log.d("AAA", " ServicioEscucharBeacons.onDestroy() " );
        this.detenerBusquedaDispositivosBTLE();
        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    } //()
    // ---------------------------------------------------------------------------------------------
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onHandleIntent(Intent intent) {
        this.tiempoDeEspera = intent.getLongExtra("tiempoDeEspera", /* default */ 50000);
        Log.d(ETIQUETA_LOG, " dispositivoEscuchando=" + dispotivo );
        this.seguir = true;
        // compruebo que los permisos de bluetooth han sido concedidos
        inicializarBlueTooth();
        buscarEsteDispositivoBTLE(dispotivo);
        // esto lo ejecuta un WORKER THREAD !
        long contador = 1;
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId() );
        try {
            while ( this.seguir ) {
                Thread.sleep(tiempoDeEspera);
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador );
                contador++;
            } //while()
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )" );
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");
            Thread.currentThread().interrupt();
        } //try catch()
        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de buscar el dispositivo bluetooth
     * @param dispositivoBuscado
     * Diseño:  String --> buscarEsteDispositivoBTLE() -->
     **/
    // ---------------------------------------------------------------------------------------------
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                mostrarInformacionDispositivoBTLE( resultado );
                /*byte[] bytes = resultado.getScanRecord().getBytes();
                TramaIBeacon tib = new TramaIBeacon(bytes);
                Medicion medicionEnviar = new  Medicion(Utilidades.bytesToInt(tib.getMinor()));
                new Logica().insertarMedida(medicionEnviar);*/
            } //()
            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");
            } //()
            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");
            } //()
        };
        List<ScanFilter> filters = new ArrayList<>();
        ScanFilter sf = new ScanFilter.Builder().setDeviceName( dispositivoBuscado ).build();
        filters.add(sf);
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build();
        //Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado
        //      + " -> " + Utilidades.stringToUUID( dispositivoBuscado ) );
        this.elEscanner.startScan(filters,settings,this.callbackDelEscaneo);
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de mostrar la ifnormación de los dispositivo bluetooth
     * @param resultado
     * Diseño:  ScanResult --> buscarEsteDispositivoBTLE() -->
     **/
    // ---------------------------------------------------------------------------------------------
    private void mostrarInformacionDispositivoBTLE( ScanResult resultado ) {

        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());

        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi );

        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        TramaIBeacon tib = new TramaIBeacon(bytes);

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ****************************************************");

        //Aquí agrego el filtro para que al encontrar mi dispositivo se pueda subir a la base de datos
        if(bluetoothDevice.getName() != null && bluetoothDevice.getName().equals(dispotivo)) {
            Log.d("AAAA", "Entro en if");
            subirMedida(new Medicion(Utilidades.bytesToInt(tib.getMinor())));
        } //if()
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de inicializar bluetooth
     * Diseño:  --> inicializarBluetooth() -->
     **/
    // ---------------------------------------------------------------------------------------------
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");
        bta.enable();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");
        this.elEscanner = bta.getBluetoothLeScanner();
        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");
        }
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de recoger la medida
     * @param medicion
     * Diseño:  Medicion --> subirMedida() -->
     **/
    // ---------------------------------------------------------------------------------------------
    private void subirMedida(Medicion medicion){
        Log.d(ETIQUETA_LOG, " subirMedida(): empieza");
        new Logica().insertarMedida(medicion);
        Log.d(ETIQUETA_LOG, " subirMedida(): acaba");
        //Toast.makeText(MainActivity.getInstance(), "Se sube la medida", Toast.LENGTH_SHORT).show();
    }

}

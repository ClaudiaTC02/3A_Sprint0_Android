package ctorcru.upv.sprint0android;

import android.util.Log;

import ctorcru.upv.sprint0android.Logica.Logica;
import ctorcru.upv.sprint0android.Modelo.Medicion;

public class Beacons {
    private static final String ETIQUETA = "Sprintct";

    public Beacons() {
        Log.d(ETIQUETA, " Beacons constructor: termina");
    }
    public int anyadirNumeroAleatorio(){
        int aleatorio = (int) (Math.random()*50);
        Log.d(ETIQUETA, " El numero es: "+ aleatorio);
        new Logica().insertarMedida(new Medicion(aleatorio));
        return aleatorio;
    }
}

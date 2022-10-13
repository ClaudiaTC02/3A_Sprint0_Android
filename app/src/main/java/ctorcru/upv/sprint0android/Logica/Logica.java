package ctorcru.upv.sprint0android.Logica;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ctorcru.upv.sprint0android.MainActivity;
import ctorcru.upv.sprint0android.Modelo.Medicion;
// ---------------------------------------------------------------------------------------------
/**
 * @brief Esta clase se encarga de subir la Medicion a la base de datos
 * Autora: Claudia Torres Cruz
 * Archivo: Logica.java
 **/
// ---------------------------------------------------------------------------------------------
public class Logica {
    // Atributos
    //URL casa
    public static final String URL = "http://192.168.85.93:80/sprint0/insertarMedida.php";
    //URL universidad
    //private static final String URL = "http://10.236.55.145:80/sprint0/insertarMedida.php";
    private static final String ETIQUETA_LOG = "Sprintct";
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase para poder ser llamado desde otra de forma simple
     * @return objeto Logica
     * Diseño:  --> Logica() --> Logica
     **/
    // ---------------------------------------------------------------------------------------------
    public Logica() {
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de insertar la medida en la base de datos usando un POST
     * @param medicion
     * Diseño: Medicion --> insertarMedida() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void insertarMedida(Medicion medicion){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(ETIQUETA_LOG, "OPERACION EXISTOSA");
                Toast.makeText(MainActivity.getInstance(), "La medida se ha subido a la base de datos", Toast.LENGTH_SHORT).show();
            } //()
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ETIQUETA_LOG, error.toString());
                Toast.makeText(MainActivity.getInstance(), "Ocurrió un problema", Toast.LENGTH_SHORT).show();
            } //()
        }){
            //Parametros que enviamos al servidor
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", "");
                parametros.put("medida", medicion.getMedida());
                parametros.put("fecha", medicion.getFecha());
                return parametros;
            } //()
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getInstance());
        requestQueue.add(stringRequest);
    } //()
} //()

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

public class Logica {
    public static final String URL_Uni = "http://192.168.174.101:80/sprint0/insertarMedida.php";
    private static final String URL = "http://10.236.55.145:80/sprint0/insertarMedida.php";
    private static final String ETIQUETA_LOG = "Sprintct";

    public Logica() {
    }

    public void insertarMedida(Medicion medicion){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(ETIQUETA_LOG, "OPERACION EXISTOSA");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ETIQUETA_LOG, error.toString());
            }
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
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getInstance());
        requestQueue.add(stringRequest);
    }
}

package ctorcru.upv.sprint0android.Modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Medicion {
    String medida;
    String fecha;

    public Medicion(int medida) {
        this.medida =String.valueOf(medida);
        this.fecha = fechaDB();
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getFecha() {
        return fecha;
    }

    private String fechaDB(){
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaActual = formato.format(fecha);
        return fechaActual;
    }
    public String toJSON(){
        String res = "{" +
                "\"id\":\""+"0"+"\"," +
                "\"valor\":\""+this.medida+"\","+
                "\"fecha\":\""+this.fecha+"\""+
                "}";
        return res;
    }

}

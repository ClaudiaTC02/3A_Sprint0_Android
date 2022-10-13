package ctorcru.upv.sprint0android.Modelo;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------
/**
 * @brief Esta clase se encarga de realizar conversiones que nos son útiles para la aplicación
 * Autora: Claudia Torres Cruz
 * Archivo: Utilidades.java
 **/
// ---------------------------------------------------------------------------------------------
public class Utilidades {
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte el texto en un array de bytes
     * @param  texto
     * Diseño: String --> stringToBytes() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public static byte[] stringToBytes ( String texto ) {
        return texto.getBytes();
        // byte[] b = string.getBytes(StandardCharsets.UTF_8); // Ja
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte el texto en un uuid
     * @param  uuid
     * @return res
     * Diseño: String --> stringToUUID() --> UUID
     **/
    // ---------------------------------------------------------------------------------------------
    public static UUID stringToUUID(String uuid ) {
        if ( uuid.length() != 16 ) {
            throw new Error( "stringUUID: string no tiene 16 caracteres ");
        } //if()
        byte[] comoBytes = uuid.getBytes();
        String masSignificativo = uuid.substring(0, 8);
        String menosSignificativo = uuid.substring(8, 16);
        UUID res = new UUID( Utilidades.bytesToLong( masSignificativo.getBytes() ), Utilidades.bytesToLong( menosSignificativo.getBytes() ) );
        // Log.d( MainActivity.ETIQUETA_LOG, " \n\n***** stringToUUID *** " + uuid  + "=?=" + Utilidades.uuidToString( res ) );
        // UUID res = UUID.nameUUIDFromBytes( comoBytes ); no va como quiero
        return res;
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte el uuid en texto
     * @param  uuid
     * Diseño: UUID --> uuidToString() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public static String uuidToString ( UUID uuid ) {
        return bytesToString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte el uuid en hextring (texto usando hexadecimales)
     * @param  uuid
     * Diseño: UUID --> uuidToHexString() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public static String uuidToHexString ( UUID uuid ) {
        return bytesToHexString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte un array de bytes en texto
     * @param  bytes
     * @return sb (convertido a string)
     * Diseño: byte[] --> bytesToString() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public static String bytesToString( byte[] bytes ) {
        if (bytes == null ) {
            return "";
        } //if()
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append( (char) b );
        } //for()
        return sb.toString();
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte dos long en un array de bytes
     * @param masSignificativos
     * @param menosSignificativos
     * @return buffer.array()
     * Diseño: long, long --> dosLongToBytes() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public static byte[] dosLongToBytes( long masSignificativos, long menosSignificativos ) {
        ByteBuffer buffer = ByteBuffer.allocate( 2 * Long.BYTES );
        buffer.putLong( masSignificativos );
        buffer.putLong( menosSignificativos );
        return buffer.array();
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte un array de bytes en un número entero
     * @param bytes
     * Diseño: byte[] --> bytesToInt() --> int
     **/
    // ---------------------------------------------------------------------------------------------
    public static int bytesToInt( byte[] bytes ) {
        return new BigInteger(bytes).intValue();
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte un array de bytes en un número entero (de 4 bytes)
     * @param bytes
     * Diseño: byte[] --> bytesToLong() --> long
     **/
    // ---------------------------------------------------------------------------------------------
    public static long bytesToLong( byte[] bytes ) {
        return new BigInteger(bytes).longValue();
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte un array de bytes en un número entero comprobando que cumple con las características de un int
     * @param bytes
     * @return res
     * Diseño: byte[] --> bytesToIntOK() --> int
     **/
    // ---------------------------------------------------------------------------------------------
    public static int bytesToIntOK( byte[] bytes ) {
        if (bytes == null ) {
            return 0;
        }
        if ( bytes.length > 4 ) {
            throw new Error( "demasiados bytes para pasar a int ");
        }
        int res = 0;
        for( byte b : bytes ) {
           /*
           Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): byte: hex=" + Integer.toHexString( b )
                   + " dec=" + b + " bin=" + Integer.toBinaryString( b ) +
                   " hex=" + Byte.toString( b )
           );
           */
            res =  (res << 8) // * 16
                    + (b & 0xFF); // para quedarse con 1 byte (2 cuartetos) de lo que haya en b
        } // for

        if ( (bytes[ 0 ] & 0x8) != 0 ) {
            // si tiene signo negativo (un 1 a la izquierda del primer byte
            res = -(~(byte)res)-1; // complemento a 2 (~) de res pero como byte, -1
        }
       /*
        Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): res = " + res + " ~res=" + (res ^ 0xffff)
                + "~res=" + ~((byte) res)
        );
        */
        return res;
    } // ()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método convierte un array de bytes en hextring (texto usando hexadecimales)
     * @param bytes
     * @return sb (convertido a string)
     * Diseño: byte[] --> bytesToHexString() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public static String bytesToHexString( byte[] bytes ) {
        if (bytes == null ) {
            return "";
        } //if()
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
            sb.append(':');
        } //for()
        return sb.toString();
    } // ()
}

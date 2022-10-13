package ctorcru.upv.sprint0android.Modelo;

import java.util.Arrays;

// ---------------------------------------------------------------------------------------------
/**
 * @brief Esta clase se encarga de separar la trama del beacon en sus componentes
 * Autora: Claudia Torres Cruz
 * Archivo: TramaIBeacon.java
 **/
// ---------------------------------------------------------------------------------------------
public class TramaIBeacon {
    // Atributos
    private byte[] prefijo = null; // 9 bytes
    private byte[] uuid = null; // 16 bytes
    private byte[] major = null; // 2 bytes
    private byte[] minor = null; // 2 bytes
    private byte txPower = 0; // 1 byte

    private byte[] losBytes;

    private byte[] advFlags = null; // 3 bytes
    private byte[] advHeader = null; // 2 bytes
    private byte[] companyID = new byte[2]; // 2 bytes
    private byte iBeaconType = 0 ; // 1 byte
    private byte iBeaconLength = 0 ; // 1 byte

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el prefijo
     * @return prefijo
     * Diseño: --> getPrefijo() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getPrefijo() {
        return prefijo;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el uuid
     * @return uuid
     * Diseño: --> getUUID() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getUUID() {
        return uuid;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el major
     * @return major
     * Diseño: --> getMajor() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getMajor() {
        return major;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el minor
     * @return minor
     * Diseño: --> getMinor() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getMinor() {
        return minor;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el txPower
     * @return txPower
     * Diseño: --> getTxPower() --> byte
     **/
    // ---------------------------------------------------------------------------------------------
    public byte getTxPower() {
        return txPower;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene los Bytes
     * @return losBytes
     * Diseño: --> getLosBytes() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getLosBytes() {
        return losBytes;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el advFlags
     * @return advFlags
     * Diseño: --> getAdvFlags() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getAdvFlags() {
        return advFlags;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el advHeader
     * @return advHeader
     * Diseño: --> getAdvHeader() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getAdvHeader() {
        return advHeader;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el companyID
     * @return companyID
     * Diseño: --> getCompanyID() --> byte[]
     **/
    // ---------------------------------------------------------------------------------------------
    public byte[] getCompanyID() {
        return companyID;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el iBeaconType
     * @return iBeaconType
     * Diseño: --> getiBeaconType() --> byte
     **/
    // ---------------------------------------------------------------------------------------------
    public byte getiBeaconType() {
        return iBeaconType;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el iBeaconLength
     * @return iBeaconLength
     * Diseño: --> getiBeaconLength() --> byte
     **/
    // ---------------------------------------------------------------------------------------------
    public byte getiBeaconLength() {
        return iBeaconLength;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor que le llega la cadena entera de bytes y lo desglosa
     * @param  bytes
     * @return TramaIBeacon
     * Diseño: byte[] --> TramaIBeacon() --> TramaIBeacon
     **/
    // ---------------------------------------------------------------------------------------------
    public TramaIBeacon(byte[] bytes ) {
        this.losBytes = bytes;

        prefijo = Arrays.copyOfRange(losBytes, 0, 8+1 ); // 9 bytes
        uuid = Arrays.copyOfRange(losBytes, 9, 24+1 ); // 16 bytes
        major = Arrays.copyOfRange(losBytes, 25, 26+1 ); // 2 bytes
        minor = Arrays.copyOfRange(losBytes, 27, 28+1 ); // 2 bytes
        txPower = losBytes[ 29 ]; // 1 byte

        advFlags = Arrays.copyOfRange( prefijo, 0, 2+1 ); // 3 bytes
        advHeader = Arrays.copyOfRange( prefijo, 3, 4+1 ); // 2 bytes
        companyID = Arrays.copyOfRange( prefijo, 5, 6+1 ); // 2 bytes
        iBeaconType = prefijo[ 7 ]; // 1 byte
        iBeaconLength = prefijo[ 8 ]; // 1 byte

    } // ()
}

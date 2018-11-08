
package XifratAssimetric;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Claus {
    //Generador parell de claus
    public static KeyPair generador() {
        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keys = keyGen.genKeyPair();
        } catch (Exception ex) {
            System.err.println("Generador no disponible.");
        }
        return keys;
    }
    //Encriptar amb clau pública
    public static byte[] encriptar(byte[] data, PublicKey pub) {
        byte[] dades = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding","SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            dades = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant: " + ex);
        }
        return dades;
    }
    //Desencriptar amb clau privada
    public static byte[] desencriptar(byte[] data, PrivateKey priv) {
        byte[] dades = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding","SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, priv);
            dades = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant: " + ex);
        }
        return dades;
    }
    //Xifrat amb clau embolcallada
    public static byte[][] encryptWrappedData(byte[] data, PublicKey pub) {
        byte[][] encWrappedData = new byte[2][];
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey sKey = kgen.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            byte[] encMsg = cipher.doFinal(data);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.WRAP_MODE, pub);
            byte[] encKey = cipher.wrap(sKey);
            encWrappedData[0] = encMsg;
            encWrappedData[1] = encKey;
        } catch (Exception ex) {
            System.err.println("Ha succeït un error xifrant: " + ex);
           }
        return encWrappedData;
    }
    //Desxifrar clau embolcallada
    public static byte[] decryptWrappedData(byte[] encMsg, byte[] encKey, PrivateKey priv) {
        byte[] missatge = null;
        try {          
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.UNWRAP_MODE, priv);
            Key sKey = cipher.unwrap(encKey, "AES", Cipher.SECRET_KEY);
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            missatge = cipher.doFinal(encMsg);

        } catch (Exception ex) {
            System.err.println("Ha succeït un error desxifrant: " + ex);
           }
        return missatge;
    }
}

package XifratSimetric;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Simetric {
    //Genera una clau aleatòriament
    public static SecretKey generarClauAleatoria(int keySize) {
    SecretKey sKey = null;
    if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {    
      try {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(keySize);
        sKey = kgen.generateKey();
      
      } catch (NoSuchAlgorithmException ex) {
        System.err.println("Generador no disponible.");  
      }
    }     
    return sKey;       
  }
    //Genera una clau de xifrat
    public static SecretKey generarClauText(String text, int mida){
        SecretKey sKey = null;
        if ((mida == 128)||(mida == 192)||(mida == 256)) {
            try {
                byte[] data = text.getBytes("ISO-8859-1");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, mida/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
                }
            }
        return sKey;
    }
    //Encripta les dades
    public static byte[] encriptar (SecretKey clau, String text) {
        byte[] dades = null;
        byte[] dadesEncriptades = null;
        try {
            dades = text.getBytes("ISO-8859-1");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clau);
            dadesEncriptades = cipher.doFinal(dades);
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return dadesEncriptades;
    }
    
    //Desencripta les dades
    public String desencriptar (SecretKey clau, byte[] dades) {
        String text = null;
        byte[] dadesDesencriptades = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, clau);
            dadesDesencriptades = cipher.doFinal(dades);
            text = new String(dadesDesencriptades, "ISO-8859-1");
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return text;
    } 
}
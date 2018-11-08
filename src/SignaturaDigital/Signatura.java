
package SignaturaDigital;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class Signatura {
    // Signar un missatge amb la clau privada
    public static byte[] signar(String dades, PrivateKey priv) {
        byte[] data = dades.getBytes();
        byte[] signatura = null;
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priv);
            signer.update(data);
            signatura = signer.sign();
        } catch (Exception ex) {
            System.err.println("Error signant les dades: " + ex);
        }
        return signatura;
    }
    //Validar una firma amb la 
    public static boolean validar(byte[] signatura, String dades, PublicKey pub) {
        byte[] data = dades.getBytes();
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(pub);
            signer.update(data);
            if (signer.verify(signatura)) {
                return true;
            }
        } catch (Exception ex) {
            System.err.println("Error signant les dades: " + ex);
        }
        return false;
    }
}

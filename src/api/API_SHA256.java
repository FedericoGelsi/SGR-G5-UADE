package api;

import java.security.NoSuchAlgorithmException;

public interface API_SHA256 {

    byte[] getSHA(String tx) throws NoSuchAlgorithmException;

    String getSHA_Str(String tx) throws  NoSuchAlgorithmException;
}

package impl;

import api.API_SHA256;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 implements API_SHA256 {

    private String tx;
    private String hash_str;
    private byte[] hash_byte;


    @Override
    public byte[] getSHA(String tx) throws NoSuchAlgorithmException{
        this.tx = tx;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        this.hash_byte = md.digest(this.tx.getBytes(StandardCharsets.UTF_8));
        return this.hash_byte;
    }

    @Override
    public String getSHA_Str(String tx) throws  NoSuchAlgorithmException{
        this.tx = tx;
        toHexString(getSHA(this.tx));
        return this.hash_str;
    }

    private void toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        this.hash_str = hexString.toString();
    }
}

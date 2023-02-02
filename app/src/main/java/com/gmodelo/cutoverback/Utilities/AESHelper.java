package com.gmodelo.cutoverback.Utilities;

import android.content.res.Resources;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gmodelo.cutoverback.Activity.R;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    private static String charset = "UTF-8";
    private final String MESSAGE_ALGORITH = "SHA-256";

    public String encrypt(String texto, String codeWord, String c) {

        final String secretKey =  codeWord;
        String encryptedString = null;

        try {

            MessageDigest md = MessageDigest.getInstance(MESSAGE_ALGORITH);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(charset));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, c);
            Cipher cipher = Cipher.getInstance(c);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes(charset);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.getEncoder().encode(buf);
            encryptedString = new String(base64Bytes);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException ex) {
            Log.e("AESHelper", "", ex);
        }
        return encryptedString;
    }

    public String decrypt(String textoEncriptado, String codeWord, String c) {
        String encryptedString = null;

        try {
            byte[] message = Base64.getDecoder().decode(textoEncriptado.getBytes(charset));
            MessageDigest md = MessageDigest.getInstance(MESSAGE_ALGORITH);
            byte[] digestOfPassword = md.digest(codeWord.getBytes(charset));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, c);

            Cipher decipher = Cipher.getInstance(c);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            encryptedString = new String(plainText, charset);

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException ex) {
            Log.e("AESHelper", "", ex);
        }

        return encryptedString;
    }
}

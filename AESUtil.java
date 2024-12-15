package com.resto.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {

    public static String AES_Encrypt(String input, String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.US_ASCII));

            byte[] key = Arrays.copyOf(hash, 32);
            System.arraycopy(hash, 0, key, 16, 16);

            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.US_ASCII));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(encryptedBytes);
            }

        } catch (Exception e) {
            return "ERROR encrypt " + e.toString();
        };

        return "error";
    }
    public static String AES_Decrypt(String input, String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.US_ASCII));

            byte[] key = Arrays.copyOf(hash, 32);
            System.arraycopy(hash, 0, key, 16, 16);

            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                decodedBytes = Base64.getDecoder().decode(input);
            }else {
                return "ERROR";
            }
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.US_ASCII);

        } catch (Exception e) {
            return "ERROR";
        }
    }

}
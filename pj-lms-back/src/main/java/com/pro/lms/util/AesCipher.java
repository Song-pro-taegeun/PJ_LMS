package com.pro.lms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class AesCipher {

    @Value("${aes.key}")
    private String AES_KEY;

    private SecretKeySpec secretKey;
    private IvParameterSpec iv;

    @PostConstruct
    public void init() {
        try {
            byte[] key = Base64.getDecoder().decode(AES_KEY);
            if (key.length != 16 && key.length != 24 && key.length != 32) {
                throw new IllegalArgumentException("Invalid AES key length: " + key.length + " bytes");
            }

            secretKey = new SecretKeySpec(key, "AES");
            byte[] ivBytes = new byte[16];
            iv = new IvParameterSpec(ivBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing AesCipher", e);
        }
    }

    public String encrypt(String strToEncrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String strToDecrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decoded = Base64.getDecoder().decode(strToDecrypt);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}

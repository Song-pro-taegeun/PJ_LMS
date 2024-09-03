package com.pro.lms.auth.jwt;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//패스워드 암호화 변경
public class JwtPasswordEncoder implements PasswordEncoder {
    private static MessageDigest md = null;

    {
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        return toString(getBytes(rawPassword));
    }

    private byte[] getBytes(CharSequence rowPassword) {

        md.update(rowPassword.toString().getBytes());
        byte byteData[] = md.digest();

        return byteData;
    }

    private String toString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword.isEmpty()) {
            return false;
        }

        if (!encodedPassword.equals(encode(rawPassword))) {
            return false;
        }

        return true;
    }

}

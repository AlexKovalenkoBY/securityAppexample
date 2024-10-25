package com.example.sec;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class KeyGenerator {
    public static void main(String[] args) {
        // Генерация безопасного ключа для алгоритма HS512
        SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

        // Преобразование ключа в Base64 строку для сохранения
        String base64Key = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Generated Key: " + base64Key);
    }
}
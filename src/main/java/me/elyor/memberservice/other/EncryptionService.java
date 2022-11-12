package me.elyor.memberservice.other;

import lombok.extern.slf4j.Slf4j;
import me.elyor.memberservice.crypto.encrypt.Encryptor;
import me.elyor.memberservice.crypto.encrypt.EncryptorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class EncryptionService {

    private byte[] secretKey;

    public EncryptionService(@Value("${app.security.key}") String base64EncodedSecretKey) {
        this.secretKey = Base64.getDecoder().decode(base64EncodedSecretKey);
    }

    /**
     * Encrypts plain input then encodes to Base64
     * @param plain plain input to encode
     * @return Encrypted and Base64 encoded String
     * */
    public String encrypt(String plain) {
        Encryptor encryptor = EncryptorFactory.newFixedIvAesEncryptor(secretKey);
        byte[] encrypted = encryptor.encrypt(plain.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Decrypts encrypted and Base64 encoded input
     * @param encrypted encrypted and Base64 encoded input
     * @return original input String
     * */
    public String decrypt(String encrypted) {
        Encryptor encryptor = EncryptorFactory.newFixedIvAesEncryptor(secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}

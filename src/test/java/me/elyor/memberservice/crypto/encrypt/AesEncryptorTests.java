package me.elyor.memberservice.crypto.encrypt;

import me.elyor.memberservice.crypto.keygen.KeyGenerator;
import me.elyor.memberservice.crypto.keygen.SecureRandomKeyGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AesEncryptorTests {

    @Test
    void whenEncryptWithFixedIvThenExpectTheSameHash() {
        KeyGenerator keyGenerator = new SecureRandomKeyGenerator(16);
        byte[] secretKey = keyGenerator.generateKey();
        String beforeEncryption = "encryptionWithFixedIV";

        Encryptor encryptor = new AesEncryptor(secretKey, null, AesEncryptor.Algorithm.GCM_FIXED_IV);
        byte[] encryptedBytes = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);
        String decrypted = new String(decryptedBytes, StandardCharsets.UTF_8);
        assertEquals(beforeEncryption, decrypted);

        encryptor = new AesEncryptor(secretKey, null, AesEncryptor.Algorithm.GCM_FIXED_IV);
        byte[] first = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        encryptor = new AesEncryptor(secretKey, null, AesEncryptor.Algorithm.GCM_FIXED_IV);
        byte[] second = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        assertArrayEquals(first, second);

        String firstBase64Encoded = Base64.getEncoder().encodeToString(first);
        String secondBase64Encoded = Base64.getEncoder().encodeToString(second);
        assertEquals(firstBase64Encoded, secondBase64Encoded);
    }

    @Test
    void whenEncryptAndDecryptWithTheSameSecretKeyThenCorrectEncryption() {
        KeyGenerator keyGenerator = new SecureRandomKeyGenerator(16);
        byte[] secretKey = keyGenerator.generateKey();
        String beforeEncryption = "encryptMe";

        Encryptor encryptor = new AesEncryptor(secretKey);
        byte[] encryptedBytes = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);
        String decrypted = new String(decryptedBytes, StandardCharsets.UTF_8);
        assertEquals(beforeEncryption, decrypted);
    }

    @Test
    void whenDecryptWithDifferentSecretKeyThenExpectError() {
        KeyGenerator keyGenerator = new SecureRandomKeyGenerator(16);
        byte[] secretKey = keyGenerator.generateKey();
        byte[] anotherSecretKey = keyGenerator.generateKey();
        String beforeEncryption = "sensitiveData";

        Encryptor encryptor = new AesEncryptor(secretKey);
        byte[] encryptedBytes = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        Encryptor another = new AesEncryptor(anotherSecretKey);

        assertThrows(IllegalStateException.class, () -> another.decrypt(encryptedBytes));
    }

    @Test
    void whenDecryptTamperedDataThenExpectError() {
        KeyGenerator keyGenerator = new SecureRandomKeyGenerator(16);
        byte[] secretKey = keyGenerator.generateKey();
        String beforeEncryption = "encryptMe";

        Encryptor encryptor = new AesEncryptor(secretKey);
        byte[] encryptedBytes = encryptor.encrypt(beforeEncryption.getBytes(StandardCharsets.UTF_8));
        encryptedBytes[0] = 0x6c; //tamper the data

        assertThrows(IllegalStateException.class, () -> encryptor.decrypt(encryptedBytes));
    }

}

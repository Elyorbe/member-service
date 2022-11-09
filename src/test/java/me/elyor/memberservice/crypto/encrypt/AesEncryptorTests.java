package me.elyor.memberservice.crypto.encrypt;

import me.elyor.memberservice.crypto.keygen.KeyGenerator;
import me.elyor.memberservice.crypto.keygen.SecureRandomKeyGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AesEncryptorTests {

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

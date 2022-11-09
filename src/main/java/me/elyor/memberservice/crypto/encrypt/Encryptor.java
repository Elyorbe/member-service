package me.elyor.memberservice.crypto.encrypt;

public interface Encryptor {

    byte[] encrypt(byte[] plainBytes);

    byte[] decrypt(byte[] encryptedBytes);

}

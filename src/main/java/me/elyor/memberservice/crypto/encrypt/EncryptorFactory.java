package me.elyor.memberservice.crypto.encrypt;

public final class EncryptorFactory {

    private EncryptorFactory() {}

    public static AesEncryptor newFixedIvAesEncryptor(byte[] secretKey) {
        return new AesEncryptor(secretKey, null, AesEncryptor.Algorithm.GCM_FIXED_IV);
    }

    public static AesEncryptor newAesEncryptor(byte[] secretKey) {
        return new AesEncryptor(secretKey);
    }

}

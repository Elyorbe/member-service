package me.elyor.memberservice.crypto.keygen;

import java.security.SecureRandom;

public class SecureRandomKeyGenerator implements KeyGenerator {

    private static final int DEFAULT_KEY_LENGTH = 8;

    private final SecureRandom random;

    private final int keyLength;

    public SecureRandomKeyGenerator() {
        this(DEFAULT_KEY_LENGTH);
    }

    public SecureRandomKeyGenerator(int keyLength) {
        this.random = new SecureRandom();
        this.keyLength = keyLength;
    }

    @Override
    public int keyLength() {
        return this.keyLength;
    }

    @Override
    public byte[] generateKey() {
        byte[] bytes = new byte[this.keyLength];
        this.random.nextBytes(bytes);
        return bytes;
    }

}

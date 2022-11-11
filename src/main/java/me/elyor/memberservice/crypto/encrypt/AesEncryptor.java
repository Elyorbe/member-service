package me.elyor.memberservice.crypto.encrypt;

import me.elyor.memberservice.crypto.keygen.FixedKeyGenerator;
import me.elyor.memberservice.crypto.keygen.KeyGenerator;
import me.elyor.memberservice.crypto.keygen.SecureRandomKeyGenerator;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class AesEncryptor implements Encryptor {

    private static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";

    private final KeyGenerator ivGenerator;
    private final SecretKey secretKey;

    private final Cipher encryptor;
    private final Cipher decryptor;

    private final Algorithm algorithm;

    public AesEncryptor(byte[] secretKey) {
        this(secretKey, null,  Algorithm.GCM);
    }

    public AesEncryptor(String secretKey) {
        this(secretKey.getBytes(StandardCharsets.UTF_8), null, Algorithm.GCM);
    }

    public AesEncryptor(byte[] secretKey, KeyGenerator ivGenerator, Algorithm algorithm) {
        this.secretKey = new SecretKeySpec(secretKey, "AES");
        this.ivGenerator = (ivGenerator != null) ? ivGenerator : algorithm.defaultIvGenerator();
        this.encryptor = algorithm.createCipher();
        this.decryptor = algorithm.createCipher();
        this.algorithm = algorithm;
    }

    @Override
    public byte[] encrypt(byte[] plainBytes) {
        byte[] iv = ivGenerator.generateKey();
        initCipher(encryptor, Cipher.ENCRYPT_MODE, this.secretKey, algorithm.getParameterSpec(iv));
        byte[] cipherByte = doFinal(encryptor, plainBytes);
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherByte.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherByte);
        return byteBuffer.array();
    }

    @Override
    public byte[] decrypt(byte[] encryptedBytes) {
        byte[] iv = extractIvFromEncryptedBytes(encryptedBytes);
        initCipher(decryptor, Cipher.DECRYPT_MODE, secretKey, algorithm.getParameterSpec(iv));
        byte[] cipherByte = extractCipherByteFromEncryptedBytes(encryptedBytes);
        return doFinal(decryptor, cipherByte);
    }

    public enum Algorithm {

        //Only GCM for now. Later we may support other algorithms.
        GCM(AES_GCM_ALGORITHM, new SecureRandomKeyGenerator(16)),
        GCM_FIXED_IV(AES_GCM_ALGORITHM, new FixedKeyGenerator());

        private final KeyGenerator ivGenerator;

        private final String name;

        Algorithm(String name, KeyGenerator ivGenerator) {
            this.name = name;
            this.ivGenerator = ivGenerator;
        }

        @Override
        public String toString() {
            return this.name;
        }

        //Should be updated if other algorithm support is added
        public AlgorithmParameterSpec getParameterSpec(byte[] iv) {
            return new GCMParameterSpec(128, iv);
        }

        public Cipher createCipher() {
            try {
                return Cipher.getInstance(this.toString());
            }
            catch (NoSuchAlgorithmException ex) {
                throw new IllegalArgumentException("Not a valid encryption algorithm", ex);
            }
            catch (NoSuchPaddingException ex) {
                throw new IllegalStateException("Padding exception", ex);
            }
        }

        public KeyGenerator defaultIvGenerator() {
            return this.ivGenerator;
        }

    }

    private void initCipher(Cipher cipher, int mode, SecretKey secretKey, AlgorithmParameterSpec parameterSpec) {
        try {
            cipher.init(mode, secretKey, parameterSpec);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Unable to initialize due to invalid secret key", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalStateException("Unable to initialize due to invalid decryption parameter spec", e);
        }
    }

    private byte[] doFinal(Cipher cipher, byte[] input) {
        try {
            return cipher.doFinal(input);
        }
        catch (IllegalBlockSizeException ex) {
            throw new IllegalStateException("Unable to invoke Cipher due to illegal block size", ex);
        }
        catch (BadPaddingException ex) {
            throw new IllegalStateException("Unable to invoke Cipher due to bad padding", ex);
        }
    }

    private byte[] extractIvFromEncryptedBytes(byte[] encryptedBytes) {
        return subArray(encryptedBytes, 0, ivGenerator.keyLength());
    }

    private byte[] extractCipherByteFromEncryptedBytes(byte[] encryptedBytes) {
        return subArray(encryptedBytes, ivGenerator.keyLength(), encryptedBytes.length);
    }

    private byte[] subArray(byte[] source, int beginIndex, int endIndex) {
        int length = endIndex - beginIndex;
        byte[] subarray = new byte[length];
        System.arraycopy(source, beginIndex, subarray, 0, length);
        return subarray;
    }

}

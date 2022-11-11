package me.elyor.memberservice.crypto.keygen;

public class FixedKeyGenerator implements KeyGenerator{

    private static final byte[] IV = new byte[] {
            0x6c, 0x57, 0x6a,
            0x23, 0x7a, 0x32,
            0x7e, 0x1c, 0x2c
    };

    @Override
    public int keyLength() {
        return IV.length;
    }

    @Override
    public byte[] generateKey() {
        return IV;
    }

}

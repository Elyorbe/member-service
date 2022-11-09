package me.elyor.memberservice.crypto.keygen;

public interface KeyGenerator {

    int keyLength();

    byte[] generateKey();

}

package io.duskmare.petridish.hashfuncs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class SHA1 implements HashingAlgorithm {

    @Override
    public int hash(int key) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(BigInteger.valueOf(key).toByteArray());
        return new BigInteger(messageDigest).intValue();
    }

    @Override
    public int hash(String key) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(key.getBytes());
        return new BigInteger(messageDigest).intValue();
    }

    @Override
    public long hashUnsigned(int key) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(BigInteger.valueOf(key).toByteArray());
        return new BigInteger(messageDigest).longValue();
    }

    @Override
    public long hashUnsigned(String key) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(key.getBytes());
        return new BigInteger(messageDigest).longValue();
    }
    
}

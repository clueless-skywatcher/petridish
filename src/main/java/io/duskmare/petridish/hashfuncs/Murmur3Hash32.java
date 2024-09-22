package io.duskmare.petridish.hashfuncs;

public class Murmur3Hash32 implements HashingAlgorithm {
    private final int C1 = 0xcc9e2d51;
    private final int C2 = 0x1b873593;
    private final int R1 = 15;
    private final int R2 = 13;

    private final int M = 5;
    private final int N = 0xe6546b64;

    private int seed = 42;

    public Murmur3Hash32(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(int key) {
        int k1 = mixK1(key);
        int h1 = mixH1(seed, k1);
        return fmix(h1, 4);
    }

    @Override
    public int hash(String key) {
        int h1 = seed;
        int len = key.length();
        int bytesRemaining = len % 4;
        int bytesProcessed = len - bytesRemaining;

        for (int i = 0; i < bytesProcessed; i += 4) {
            int k1 = (key.charAt(i) & 0xff) 
                | ((key.charAt(i + 1) & 0xff) << 8)
                | ((key.charAt(i + 2) & 0xff) << 16)
                | ((key.charAt(i + 3) & 0xff) << 24);
                
            k1 = mixK1(k1);
            h1 = mixH1(h1, k1);
        }

        int k1 = 0;
        switch (bytesRemaining) {
            case 3:
                k1 ^= (key.charAt(bytesProcessed + 2) & 0xff) << 16;
            case 2:
                k1 ^= (key.charAt(bytesProcessed + 1) & 0xff) << 8;
            case 1:
                k1 ^= (key.charAt(bytesProcessed) & 0xff);
                k1 = mixK1(k1);
                h1 ^= k1;
        }

        return fmix(h1, key.length());
    }

    private int fmix(int h1, int length) {
        h1 ^= length;
        h1 ^= h1 >>> 16;
        h1 *= 0x85ebca6b;
        h1 ^= h1 >>> 13;
        h1 *= 0xc2b2ae35;
        h1 ^= h1 >>> 16;
        return h1;
    }

    private int mixK1(int k1) {
        k1 *= C1;
        k1 = Integer.rotateLeft(k1, R1);
        k1 *= C2;
        return k1;
    }

    private int mixH1(int h1, int k1) {
        h1 ^= k1;
        h1 = Integer.rotateLeft(h1, R2);
        h1 = h1 * M + N;
        return h1;
    }

    @Override
    public long hashUnsigned(int key) {
        return Integer.toUnsignedLong(hash(key));
    }

    @Override
    public long hashUnsigned(String key) {
        return Integer.toUnsignedLong(hash(key));
    }
}

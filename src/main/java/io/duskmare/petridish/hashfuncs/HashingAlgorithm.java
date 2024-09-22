package io.duskmare.petridish.hashfuncs;

public interface HashingAlgorithm {
    public int hash(int key);
    public int hash(String key);
    public long hashUnsigned(int key);
    public long hashUnsigned(String key);
}

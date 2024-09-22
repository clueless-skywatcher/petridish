package io.duskmare.petridish.datastructs;

import java.util.ArrayList;
import java.util.List;

import io.duskmare.petridish.hashfuncs.HashingAlgorithm;
import io.duskmare.petridish.hashfuncs.Murmur3Hash32;
import io.duskmare.petridish.resp.data.RespBulkString;
import io.duskmare.petridish.resp.data.RespInteger;
import io.duskmare.petridish.resp.data.RespObject;
import io.duskmare.petridish.resp.data.RespSimpleString;

public class HyperLogLog {
    private List<RespObject> hashes = new ArrayList<>();

    private long hash(RespObject key) {
        HashingAlgorithm algorithm = new Murmur3Hash32(0);
        
        if (key instanceof RespBulkString || key instanceof RespSimpleString) {
            return algorithm.hashUnsigned(key.getValue().toString());
        }
        else if (key instanceof RespInteger) {
            return algorithm.hashUnsigned((int) key.getValue());
        }
        return algorithm.hashUnsigned(key.toString());
    }

    public int add(RespObject key) {
        return hashes.add(key) ? 1 : 0;
    }

    public int getCount(RespObject key) {
        int maxTrailingZeros = 0;
        for (int i = 0; i < hashes.size(); i++) {
            long hash = hash(hashes.get(i));
            maxTrailingZeros = Math.max(maxTrailingZeros, Long.numberOfTrailingZeros(hash));
        }
        return (int) Math.pow(2, maxTrailingZeros + 1);
    }
}

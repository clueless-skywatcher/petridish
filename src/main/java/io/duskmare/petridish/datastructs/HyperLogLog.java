package io.duskmare.petridish.datastructs;

import java.util.ArrayList;
import java.util.List;

import io.duskmare.petridish.hashfuncs.HashingAlgorithm;
import io.duskmare.petridish.hashfuncs.Murmur3X32;
import io.duskmare.petridish.hashfuncs.SHA1;
import io.duskmare.petridish.resp.data.RespBulkString;
import io.duskmare.petridish.resp.data.RespInteger;
import io.duskmare.petridish.resp.data.RespObject;
import io.duskmare.petridish.resp.data.RespSimpleString;

public class HyperLogLog {
    private final int REGISTER_COUNT = 4096;
    private List<Integer> registers;

    public HyperLogLog() {
        registers = new ArrayList<>();
        for (int i = 0; i < REGISTER_COUNT; i++) {
            registers.add(0);
        }
    }

    private int hash(RespObject key) {
        HashingAlgorithm algorithm = new SHA1();
        
        if (key instanceof RespBulkString || key instanceof RespSimpleString) {
            return algorithm.hash(key.getValue().toString());
        }
        else if (key instanceof RespInteger) {
            return algorithm.hash((int) key.getValue());
        }
        return algorithm.hash(key.toString());
    }

    public int add(RespObject key) {
        int hashVal = hash(key);
        int regPower = (int)(Math.log(REGISTER_COUNT) / Math.log(2));
        
        int slot = hashVal & (REGISTER_COUNT - 1);
        int slotHash = hashVal >> regPower;
        int returnVal = 0;

        if (registers.get(slot) < Integer.numberOfTrailingZeros((int) slotHash)) {
            returnVal = 1;
        }
        registers.set(slot, Math.max(registers.get(slot), Integer.numberOfTrailingZeros((int) slotHash)));
        return returnVal;
    }

    public int cardinality() {
        double maxZeroSum = 0.0;
        for (int zeros: registers) {
            maxZeroSum += Math.pow(2, -zeros);
        }
        double harmonicMean = REGISTER_COUNT / maxZeroSum;

        double alphaM;

        alphaM = 0.7213 / (1 + (1.079 / REGISTER_COUNT));
        
        double estimate = alphaM * REGISTER_COUNT * harmonicMean;

        int v = 0;
        if (estimate <= 2.5 * REGISTER_COUNT) {
            for (int reg : registers) {
                if (reg == 0) {
                    v++;
                }
            }
            if (v > 0) {
                estimate = REGISTER_COUNT * Math.log(REGISTER_COUNT / (v * 1.0));
            }
        }
        else if (estimate > Math.pow(2, 32) / 30) {
            estimate = -Math.pow(2, 32) * Math.log(1 - estimate / Math.pow(2, 32));
        }

        return (int) estimate * 2;
    }
}

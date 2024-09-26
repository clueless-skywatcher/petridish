package io.duskmare.petridish.datastructs;

import io.duskmare.petridish.resp.data.RespBulkString;

public class HyperLogLogMain {
    public static void main(String[] args) {
        HyperLogLog hll = new HyperLogLog();
        for (int i = 0; i < 100000000; i++) {
            hll.add(new RespBulkString(i));
        }
        System.out.println(hll.cardinality());
    }
}

package io.duskmare.petridish.datastructs;

import io.duskmare.petridish.resp.data.RespBulkString;
import io.duskmare.petridish.resp.data.RespObject;

public class HyperLogLogMain {
    public static void main(String[] args) {
        HyperLogLog hll = new HyperLogLog();
        RespObject key = new RespBulkString("abc1");
        hll.add(key);
        System.out.println(hll.getCount(key));
        key = new RespBulkString("abc2");
        hll.add(key);
        System.out.println(hll.getCount(key));
        key = new RespBulkString("abc1");
        hll.add(key);
        System.out.println(hll.getCount(key));
        key = new RespBulkString("abc3");
        hll.add(key);
        System.out.println(hll.getCount(key));
    }
}

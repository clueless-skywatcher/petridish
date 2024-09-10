package io.duskmare.petridish.resp.readers;

import java.io.BufferedReader;
import java.io.IOException;

import io.duskmare.petridish.resp.data.RespBulkString;

public class BulkStringRespReader implements RespReader<RespBulkString> {
    private BufferedReader in;
    
    public BulkStringRespReader(BufferedReader in) {
        this.in = in;
    }

    @Override
    public RespBulkString read() throws IOException {
        int size = new IntegerRespReader(in).read().getValue();
        char[] name = new char[size];
        in.read(name, 0, size);
        in.read();
        in.read();
        return new RespBulkString(new String(name));
    }
    
}

package io.duskmare.petridish.resp.readers;

import java.io.IOException;

import io.duskmare.petridish.exceptions.RespException;
import io.duskmare.petridish.resp.data.RespArray;
import io.duskmare.petridish.resp.data.RespObject;

import java.io.BufferedReader;

public class ArrayRespReader implements RespReader<RespArray> {
    private BufferedReader in;

    public ArrayRespReader(BufferedReader in) {
        this.in = in;
    }

    @Override
    public RespArray read() throws IOException {
        int nElements = new IntegerRespReader(in).read().getValue();
        if (nElements < 0) {
            throw new RespException("Expected a non-negative number for number of elements");
        }
        
        RespArray array = new RespArray();

        while (nElements-- > 0) {
            RespObject obj = RespReaderFactory.read(in);
            array.add(obj);
        }

        return array;
    }
    
}

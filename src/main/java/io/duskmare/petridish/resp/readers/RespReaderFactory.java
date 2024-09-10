package io.duskmare.petridish.resp.readers;

import java.io.BufferedReader;
import java.io.IOException;

import io.duskmare.petridish.resp.data.RespObject;

public class RespReaderFactory {
    public static RespObject read(BufferedReader in) throws IOException {
        int start = in.read();
        if (start == '$') {
            return new BulkStringRespReader(in).read();
        }
        else if (start == ':') {
            return new IntegerRespReader(in).read();
        }
        else if (start == '*') {
            return new ArrayRespReader(in).read();
        }
        return null;
    }
}

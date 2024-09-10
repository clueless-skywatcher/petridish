package io.duskmare.petridish.resp.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;

import io.duskmare.petridish.resp.data.RespInteger;

public class IntegerRespReader implements RespReader<RespInteger> {
    private BufferedReader in;

    public IntegerRespReader(BufferedReader in) {
        this.in = in;
    }

    @Override
    public RespInteger read() throws IOException {
        int sign = 1;
        
        PushbackReader pb = new PushbackReader(in);

        int optionalSign = pb.read();

        if (optionalSign != '+' && optionalSign != '-') {
            pb.unread(optionalSign);
        }
        else {
            sign = optionalSign == '+' ? 1 : -1;
        }

        StringBuilder intVal = new StringBuilder();

        int c;
        while ((c = pb.read()) != '\r') {
            intVal.append(c - '0');
        }

        pb.read();
        return new RespInteger(Integer.parseInt(intVal.toString()) * sign);
    }
    
}

package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.data.RespObject;

public class EchoResp implements RespCommand {
    private RespObject message;

    public EchoResp(List<RespObject> args) {
        if (args.size() != 1) {
            throw new InvalidRespCommandException("ECHO command requires 1 argument");
        }
        this.message = args.get(0);
    }
    
    @Override
    public RespObject execute() {
        return this.message;
    }
}

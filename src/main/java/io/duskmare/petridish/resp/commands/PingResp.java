package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.GlobalRespObjects;
import io.duskmare.petridish.resp.data.RespObject;

public class PingResp implements RespCommand {
    private RespObject pong = GlobalRespObjects.PONG;

    public PingResp(List<RespObject> args) {
        if (args.size() > 1) {
            throw new InvalidRespCommandException("PING command requires at most 1 argument");
        }
        if (args.size() == 1){
            this.pong = args.get(0);
        }
    }

    @Override
    public RespObject execute() {
        return pong;
    }
}

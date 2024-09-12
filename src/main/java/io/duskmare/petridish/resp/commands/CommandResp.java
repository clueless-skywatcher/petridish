package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.resp.data.RespObject;
import io.duskmare.petridish.resp.data.RespSimpleString;

public class CommandResp implements RespCommand {

    public CommandResp(List<RespObject> args) {
    }

    @Override
    public RespObject execute() {
        return new RespSimpleString("COMMAND");
    }
    
}

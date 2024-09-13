package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.PetriDishServer;
import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.GlobalRespObjects;
import io.duskmare.petridish.resp.data.RespObject;

public class SetResp implements RespCommand {
    private RespObject key;
    private RespObject value;

    public SetResp(List<RespObject> args) {
        if (args.size() != 2) {
            throw new InvalidRespCommandException("SET command requires 2 arguments");
        }
        this.key = args.get(0);
        this.value = args.get(1);
    }

    @Override
    public RespObject execute() {
        PetriDishServer.GLOBAL_MAP.set(key.getValue().toString(), value);
        return GlobalRespObjects.OK;
    }
    
}

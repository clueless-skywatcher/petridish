package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.PetriDishServer;
import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.data.RespObject;

public class GetResp implements RespCommand {
    private RespObject key;

    public GetResp(List<RespObject> args) {
        if (args.size() != 1) {
            throw new InvalidRespCommandException("GET command requires 1 argument");
        }
        this.key = args.get(0);
    }

    @Override
    public RespObject execute() {
        return PetriDishServer.GLOBAL_MAP.get(key.getValue().toString());
    }
    
}

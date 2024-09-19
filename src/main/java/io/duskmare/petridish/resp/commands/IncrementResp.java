package io.duskmare.petridish.resp.commands;

import java.util.List;

import io.duskmare.petridish.PetriDishServer;
import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.data.RespBulkString;
import io.duskmare.petridish.resp.data.RespInteger;
import io.duskmare.petridish.resp.data.RespObject;

public class IncrementResp implements RespCommand {
    private RespObject key;

    public IncrementResp(List<RespObject> args) {
        if (args.size() != 1) {
            throw new InvalidRespCommandException("INCR command requires a single argument");
        }
        this.key = args.get(0);
    }

    @Override
    public RespObject execute() {
        if (PetriDishServer.GLOBAL_MAP.containsKey(key.getValue().toString())) {
            RespObject obj = PetriDishServer.GLOBAL_MAP.get(key.getValue().toString());
            try {
                int newVal = Integer.parseInt(obj.getValue().toString()) + 1;
                PetriDishServer.GLOBAL_MAP.set(key.getValue().toString(), new RespBulkString(newVal));
                return new RespInteger(newVal);
            }
            catch (NumberFormatException e) {
                throw new InvalidRespCommandException("WRONGTYPE Operation against a key holding the wrong kind of value");
            }
        }
        return new RespInteger(1);
    }
}

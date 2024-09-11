package io.duskmare.petridish.resp;

import java.util.List;

import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.commands.GetResp;
import io.duskmare.petridish.resp.commands.PingResp;
import io.duskmare.petridish.resp.commands.RespCommand;
import io.duskmare.petridish.resp.commands.SetResp;
import io.duskmare.petridish.resp.data.RespArray;
import io.duskmare.petridish.resp.data.RespObject;

public class RespCommandsHandler {
    public static RespCommand generateCommand(RespObject objs) {
        RespArray array = (RespArray) objs;
        String commandName = array.getValue().get(0).toString().toUpperCase();
        List<RespObject> args = array.getValue().subList(1, array.getValue().size());
        RespCommand command;
        switch (commandName) {
            case "PING":
                command = new PingResp(args);
                break;
            case "SET":
                command = new SetResp(args);
                break;
            case "GET":
                command = new GetResp(args);
                break;
            default:
                throw new InvalidRespCommandException("Unknown command: " + commandName);
        }

        return command;
    }
}

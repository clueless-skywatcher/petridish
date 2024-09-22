package io.duskmare.petridish.resp;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import io.duskmare.petridish.datastructs.GenericTable;
import io.duskmare.petridish.datastructs.HashTable;
import io.duskmare.petridish.exceptions.InvalidRespCommandException;
import io.duskmare.petridish.resp.commands.EchoResp;
import io.duskmare.petridish.resp.commands.GetResp;
import io.duskmare.petridish.resp.commands.IncrementResp;
import io.duskmare.petridish.resp.commands.PingResp;
import io.duskmare.petridish.resp.commands.RespCommand;
import io.duskmare.petridish.resp.commands.SetResp;
import io.duskmare.petridish.resp.data.RespArray;
import io.duskmare.petridish.resp.data.RespObject;

public class RespCommandsHandler {
    private static GenericTable<String, Class<? extends RespCommand>> COMMANDS = new HashTable<>();

    static {
        COMMANDS.set("PING", PingResp.class);
        COMMANDS.set("SET", SetResp.class);
        COMMANDS.set("GET", GetResp.class);
        COMMANDS.set("INCR", IncrementResp.class);
        COMMANDS.set("ECHO", EchoResp.class);
    }

    public static RespCommand generateCommand(RespObject objs) {
        RespArray array = (RespArray) objs;
        String commandName = array.getValue().get(0).getValue().toString().toUpperCase();
        List<RespObject> args = array.getValue().subList(1, array.getValue().size());
        
        if (!COMMANDS.containsKey(commandName)) {
            throw new InvalidRespCommandException("Unknown command: " + commandName);
        }

        Class<? extends RespCommand> commandClass = COMMANDS.get(commandName);
        try {
            RespCommand command = commandClass.getDeclaredConstructor(List.class).newInstance(args);
            return command;
        } catch (InvalidRespCommandException e) {
            throw e;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new InvalidRespCommandException("Server issue. Please check logs");
        }
    }
}

package io.duskmare.petridish.resp.commands;

import io.duskmare.petridish.resp.data.RespObject;

public interface RespCommand {
    public RespObject execute();
}

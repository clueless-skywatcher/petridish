package io.duskmare.petridish.resp.readers;

import java.io.IOException;

import io.duskmare.petridish.resp.data.RespObject;

public interface RespReader<T extends RespObject> {
    public T read() throws IOException;
}

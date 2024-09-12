package io.duskmare.petridish.resp;

import io.duskmare.petridish.resp.data.RespNullBulk;
import io.duskmare.petridish.resp.data.RespSimpleString;

public class GlobalRespObjects {
    public static final RespSimpleString OK = new RespSimpleString("OK");
    public static final RespSimpleString PONG = new RespSimpleString("PONG");
    public static final RespNullBulk NULL = new RespNullBulk();
}

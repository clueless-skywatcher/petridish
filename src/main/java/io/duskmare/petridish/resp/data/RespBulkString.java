package io.duskmare.petridish.resp.data;

import lombok.Getter;

public class RespBulkString implements RespObject {
    private @Getter String value;

    public RespBulkString(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}

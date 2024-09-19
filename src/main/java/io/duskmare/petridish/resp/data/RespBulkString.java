package io.duskmare.petridish.resp.data;

import lombok.Getter;

public class RespBulkString implements RespObject {
    private @Getter String value;

    public RespBulkString(String value) {
        this.value = value;
    }

    public RespBulkString(int value) {
        this.value = Integer.toString(value);
    }

    public String toString() {
        return String.format("$%d\r\n%s\r\n", value.length(), value);
    }
}

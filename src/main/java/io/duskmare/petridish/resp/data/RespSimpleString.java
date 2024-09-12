package io.duskmare.petridish.resp.data;

import lombok.Getter;

public class RespSimpleString implements RespObject {
    private @Getter String value;

    public RespSimpleString(String value) {
        this.value = value;
    }

    public String toString() {
        return String.format("+%s\r\n", value);
    }
}

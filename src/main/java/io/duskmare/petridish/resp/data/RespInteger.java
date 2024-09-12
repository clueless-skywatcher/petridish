package io.duskmare.petridish.resp.data;

import lombok.Getter;

public class RespInteger implements RespObject {    
    private @Getter Integer value;
    
    public RespInteger(int value) {
        this.value = value;
    }

    public String toString() {
        return String.format(":%d\r\n", value);
    }
}

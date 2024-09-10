package io.duskmare.petridish.resp.data;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

public class RespArray implements RespObject {
    private @Getter List<RespObject> value;

    public RespArray(List<RespObject> obj) {
        this.value = obj;
    }

    public RespArray() {
        this.value = new ArrayList<>();
    }

    public void add(RespObject obj) {
        this.value.add(obj);
    }

    public String toString() {
        return value.toString();
    }
}

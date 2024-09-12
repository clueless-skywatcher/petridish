package io.duskmare.petridish.resp.data;

import java.util.List;
import java.util.StringJoiner;
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
        StringJoiner joiner = new StringJoiner("\r\n");
        for (RespObject obj : value) {
            joiner.add(obj.toString());
        }
        return String.format("*%d\r\n%s\r\n", value.size(), joiner.toString());
    }
}

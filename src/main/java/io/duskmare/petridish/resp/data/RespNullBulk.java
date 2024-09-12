package io.duskmare.petridish.resp.data;

public class RespNullBulk implements RespObject {

    @Override
    public Object getValue() {
        return null;
    }

    public String toString() {
        return "$-1\r\n";
    } 
}

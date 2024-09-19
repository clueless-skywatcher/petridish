package io.duskmare.petridish.resp;

import lombok.Getter;

public enum RespDataType {
    STRING('+'),
    INTEGER(':'),
    BULK_STRING('$'),
    ERROR('-'),
    ARRAY('*'),
    NULL('$');

    private @Getter char beginning;

    private RespDataType(char beginning) {
        this.beginning = beginning;
    }
}

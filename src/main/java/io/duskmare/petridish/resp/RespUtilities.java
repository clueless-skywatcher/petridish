package io.duskmare.petridish.resp;

import io.duskmare.petridish.datastructs.GenericTable;
import io.duskmare.petridish.datastructs.HashTable;
import io.duskmare.petridish.resp.data.RespObject;

public class RespUtilities {
    private static GenericTable<String, RespDataType> DATA_TYPES = new HashTable<>();

    static {
        DATA_TYPES.set("RespArray", RespDataType.ARRAY);
        DATA_TYPES.set("RespBulkString", RespDataType.BULK_STRING);
        DATA_TYPES.set("RespNullBulk", RespDataType.NULL);
        DATA_TYPES.set("RespInteger", RespDataType.INTEGER);
        DATA_TYPES.set("RespSimpleString", RespDataType.STRING);
    }

    public static RespDataType getType(RespObject obj) {
        return DATA_TYPES.get(obj.getClass().getSimpleName());
    }
}

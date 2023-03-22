package com.xiaoba.serialize.impl;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 11:21
 * @Description :
 */

import com.alibaba.fastjson.JSON;
import com.xiaoba.serialize.Serializer;
import com.xiaoba.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}

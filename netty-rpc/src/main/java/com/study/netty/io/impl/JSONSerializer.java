package com.study.netty.io.impl;

import com.alibaba.fastjson.JSON;
import com.study.netty.io.Serializer;

/**
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:47
 **/
public class JSONSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}

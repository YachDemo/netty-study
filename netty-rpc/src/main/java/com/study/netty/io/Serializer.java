package com.study.netty.io;

import java.io.IOException;

/**
 * 
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:46
 **/
public interface Serializer {
    /**
     * java对象转换为二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 二进制转换成java对象
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}

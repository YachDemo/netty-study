package com.study.netty.model;

import lombok.Data;

/**
 * 请求对象
 *
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:11
 **/
@Data
public class RpcRequest {
    /**
     * 请求对象id
     */
    private String requestId;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数类型
     */
    private Object[] parameters;

}

package com.study.netty.model;

import lombok.Data;

/**
 * 响应对象
 *
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:15
 **/
@Data
public class RpcResponse {

    /**
     * 响应id
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 响应的结果
     */
    private Object result;
}

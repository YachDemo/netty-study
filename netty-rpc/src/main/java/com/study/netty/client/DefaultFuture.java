package com.study.netty.client;

import com.study.netty.model.RpcResponse;

/**
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午4:17
 **/
public class DefaultFuture {
    private RpcResponse rpcResponse;
    private volatile boolean isSucceed = false;
    private final Object object = new Object();

    public RpcResponse getRpcResponse(int timeout) {
        synchronized (object) {
            while (!isSucceed) {
                try {
                    object.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        }
    }

    public void setResponse(RpcResponse response) {
        if (isSucceed) {
            return;
        }
        synchronized (object) {
            this.rpcResponse = response;
            this.isSucceed = true;
            object.notify();
        }
    }
}

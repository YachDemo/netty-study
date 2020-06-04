package com.study.netty.proxy;

import org.springframework.cglib.proxy.Proxy;

/**
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午4:47
 **/
public class ProxyFactory {
    public static <T> T create(Class<T> interfaceClass) throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new RpcClientDynamicProxy<>(interfaceClass));
    }
}

package com.study.netty;

import com.study.netty.proxy.ProxyFactory;
import com.study.netty.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class NettyRpcApplicationTest extends BaseApplicationTest {
    @Test
    public void testCr() throws Exception {
        HelloService helloService = ProxyFactory.create(HelloService.class);
        log.info("响应结果“: {}",helloService.hello("pjmike"));
    }
}

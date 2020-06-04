package com.study.netty.client;

import com.study.netty.io.RpcDecoder;
import com.study.netty.io.RpcEncoder;
import com.study.netty.io.impl.JSONSerializer;
import com.study.netty.model.RpcRequest;
import com.study.netty.model.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * Netty 客户端
 *
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:56
 **/
@Slf4j
public class NettyClient {

    private EventLoopGroup eventLoopGroup;
    private Channel channel;
    private ClientHandler clientHandler;
    private String host;
    private Integer port;
    private static final int MAX_RETRY = 5;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        clientHandler = new ClientHandler();
        eventLoopGroup = new NioEventLoopGroup();
        // 启动类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                // 指定传输的Chanel
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加编码器
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        // 添加解码器
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        // 处理请求类
                        pipeline.addLast(clientHandler);
                    }
                });
        connect(bootstrap, host, port, MAX_RETRY);
    }

    /**
     * 失败重连机制，参考Netty入门实战掘金小册
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    public void connect(Bootstrap bootstrap, String host, Integer port, int retry) {
        ChannelFuture channelFuture = bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接服务端成功！");
            } else if (retry == 0) {
                log.info("重试{}次失败，放弃连接！", retry);
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.error("{} : 连接失败， 第{}次重连。。。。。。。", new Date(), order);
            }
        });
        channel = channelFuture.channel();
    }

    /**
     * 发送消息
     *
     * @return
     */
    public RpcResponse send(final RpcRequest request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientHandler.getRpcResponse(request.getRequestId());
    }

    @PreDestroy
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }

}

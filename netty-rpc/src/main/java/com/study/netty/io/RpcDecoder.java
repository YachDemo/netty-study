package com.study.netty.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 *
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午3:50
 **/
public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> clazz;
    private Serializer serializer;

    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 因为之前编码的时候写入了一个int型，4个字节来表示长度
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        // 标记当前读的位置
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        // 将byteBuf的数据读入data字节数组
        byteBuf.readBytes(data);
        Object obj = serializer.deserialize(clazz, data);
        list.add(obj);
    }
}

package com.xiaoba.codec;

/*
 * @Author:xiaoba
 * @Date : 2023/3/25 12:37
 * @Description :
 */

import com.xiaoba.protocol.Packet;
import com.xiaoba.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}

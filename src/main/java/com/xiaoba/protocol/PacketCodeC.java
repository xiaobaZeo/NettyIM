package com.xiaoba.protocol;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 14:44
 * @Description : 封装二进制
 */

import com.xiaoba.protocol.request.*;
import com.xiaoba.protocol.response.*;
import com.xiaoba.serialize.Serializer;
import com.xiaoba.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Map;
import java.util.HashMap;

import static com.xiaoba.protocol.command.Command.*;

public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    //    static {
//        packetTypeMap = new HashMap<>();
//        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
//
//        serializerMap = new HashMap<>();
//        Serializer serializer = new JSONSerializer();
//        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
//    }

    //初始化不同指令的map！！！
    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        //1、创建ByteBuf对象、调用Netty分配器创建，ioBuffer()返回适配io读写相关内存，他尽可能创建一个直接内存，可以理解不收jvm堆管理的内存空间，写入io缓冲区的效果更好
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        //2、序列化Java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3、实际编码过程
        //写入魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //写入版本号
        byteBuf.writeByte(packet.getVersion());
        //写入序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        //写入指令
        byteBuf.writeByte(packet.getCommand());
        //写入数据长度
        byteBuf.writeInt(bytes.length);
        //写入数据
        byteBuf.writeBytes(bytes);


//        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据长度
        int dataLength = byteBuf.readInt();

        byte[] bytes = new byte[dataLength];

        byteBuf.readBytes(bytes);

        //根据指令获取对应的登录请求包  可以放入 子类 和 本身 T
        Class<? extends Packet> requestType = getRequestType(command);
        //根据序列化算法获取对应的序列化对象
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}

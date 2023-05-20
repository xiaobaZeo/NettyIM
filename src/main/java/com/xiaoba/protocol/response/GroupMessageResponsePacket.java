package com.xiaoba.protocol.response;

import com.xiaoba.protocol.Packet;
import com.xiaoba.session.Session;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
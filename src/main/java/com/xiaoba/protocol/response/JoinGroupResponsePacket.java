package com.xiaoba.protocol.response;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.JOIN_GROUP_RESPONSE;


@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}

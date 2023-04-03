package com.xiaoba.protocol.request;

import lombok.Data;
import com.xiaoba.protocol.Packet;

import static com.xiaoba.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}

package com.xiaoba.protocol.request;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;


@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}

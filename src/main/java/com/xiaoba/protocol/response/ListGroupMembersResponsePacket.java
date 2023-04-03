package com.xiaoba.protocol.response;

import com.xiaoba.protocol.Packet;
import com.xiaoba.session.Session;
import lombok.Data;

import java.util.List;

import static com.xiaoba.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}

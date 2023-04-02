package com.xiaoba.protocol.response;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.xiaoba.protocol.command.Command.CREATE_GROUP_RESPONSE;

/*
 * @Author:xiaoba
 * @Date : 2023/4/2 14:32
 * @Description :
 */

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;
    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}

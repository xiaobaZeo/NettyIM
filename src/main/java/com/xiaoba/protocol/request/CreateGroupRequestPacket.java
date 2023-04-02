package com.xiaoba.protocol.request;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.xiaoba.protocol.command.Command.CREATE_GROUP_REQUEST;

/*
 * @Author:xiaoba
 * @Date : 2023/4/2 14:30
 * @Description :
 */

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}

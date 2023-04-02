package com.xiaoba.protocol.request;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.LOGOUT_REQUEST;

/*
 * @Author:xiaoba
 * @Date : 2023/4/2 14:29
 * @Description :
 */

@Data
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}

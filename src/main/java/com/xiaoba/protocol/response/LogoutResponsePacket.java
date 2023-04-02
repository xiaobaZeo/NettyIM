package com.xiaoba.protocol.response;

/*
 * @Author:xiaoba
 * @Date : 2023/4/2 14:33
 * @Description :
 */

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.LOGOUT_RESPONSE;
@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;

    private String reason;
    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}

package com.xiaoba.protocol.response;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 22:20
 * @Description :
 */

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.LOGIN_RESPONSE;
@Data
public class LoginResponsePacket extends Packet {
private boolean success;
private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}

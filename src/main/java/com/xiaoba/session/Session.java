package com.xiaoba.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author:xiaoba
 * @Date : 2023/4/1 21:10
 * @Description : 表示用户当前的会话信息
 */
@Data
@NoArgsConstructor
public class Session {
    //用户唯一性标识
    private String userId;

    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}

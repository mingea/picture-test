package com.wzb.picturebackend.model.dto.user;

import lombok.Data;

/**
 * @author wzb
 * 用户登录
 */
@Data
public class UserLoginRequest {
    private static final long serialVersionUID = 8735650154179439661L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}

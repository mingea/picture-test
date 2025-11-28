package com.wzb.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wzb
 * 用户注册请求
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 8688427991763672915L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}

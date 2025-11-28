package com.wzb.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzb.picturebackend.model.dto.user.UserQueryRequest;
import com.wzb.picturebackend.model.dto.user.UserRegisterRequest;
import com.wzb.picturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.picturebackend.model.vo.LoginUserVO;
import com.wzb.picturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author leoneve
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-11-28 09:08:03
*/
public interface UserService extends IService<User> {

    Long userRegister(UserRegisterRequest userRegisterRequest);

    // 获取加密密码
    String encryptPassword(String userPassword);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request 请求信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);
    /**
     * 获取脱敏后的登录用户信息
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);
    /**
     * 获取脱敏后的用户信息
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);
    /**
     * 获取脱敏后的用户信息列表
     * @param userList 用户信息列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVOList(List<User> userList);
    /**
     * 获取当前登录用户
     * @param request 请求信息
     * @return 用户信息
     */
    User getLoginUser(HttpServletRequest request);
    /**
     * 用户注销
     *
     * @param request 请求信息
     * @return 是否注销成功
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取查询条件
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}

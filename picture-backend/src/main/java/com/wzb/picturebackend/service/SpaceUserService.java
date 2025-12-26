package com.wzb.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.picturebackend.model.dto.spaceuser.SpaceUserAddRequest;
import com.wzb.picturebackend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.wzb.picturebackend.model.entity.SpaceUser;
import com.wzb.picturebackend.model.vo.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author leoneve
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-12-13 20:40:09
*/
public interface SpaceUserService extends IService<SpaceUser> {

    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    void validSpaceUser(SpaceUser spaceUser, boolean add);

    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);
}

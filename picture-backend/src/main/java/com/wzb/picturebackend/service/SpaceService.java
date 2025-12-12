package com.wzb.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzb.picturebackend.model.dto.space.SpaceAddRequest;
import com.wzb.picturebackend.model.dto.space.SpaceQueryRequest;
import com.wzb.picturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.picturebackend.model.entity.User;
import com.wzb.picturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author leoneve
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-12-09 18:38:42
*/
public interface SpaceService extends IService<Space> {

    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    void fillSpaceBySpaceLevel(Space space);

    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    void validSpace(Space space, boolean add);

    void checkSpaceAuth(User loginUser, Space space);
}

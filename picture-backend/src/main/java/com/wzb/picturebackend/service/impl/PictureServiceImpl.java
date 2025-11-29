package com.wzb.picturebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzb.picturebackend.model.entity.Picture;
import com.wzb.picturebackend.service.PictureService;
import com.wzb.picturebackend.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author leoneve
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-11-28 22:52:25
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}





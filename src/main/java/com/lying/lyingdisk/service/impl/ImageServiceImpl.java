package com.lying.lyingdisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lying.lyingdisk.common.model.file.ImageFileModel;
import com.lying.lyingdisk.dao.SysFileMapper;
import com.lying.lyingdisk.dao.SysUserMapper;
import com.lying.lyingdisk.entity.SysUser;
import com.lying.lyingdisk.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public List<ImageFileModel> getImageGroupByDate(String username) {
        SysUser user = sysUserMapper.getUserByName(username);
        if (ObjectUtil.isNull(user)){
            throw new RuntimeException("用户信息不存在！");
        }
        List<ImageFileModel> imageInfo = sysFileMapper.getImageGroupByDate(user.getUserId());
        imageInfo.forEach(image -> {
            String viewPathList = image.getViewPathList();
            String[] PathList = viewPathList.split(",");
            image.setNginxViewList(Arrays.asList(PathList));
        });
        return imageInfo;
    }

    @Override
    public ImageFileModel getAllImage(String username) {
        SysUser user = sysUserMapper.getUserByName(username);
        if (ObjectUtil.isNull(user)){
            throw new RuntimeException("用户信息不存在！");
        }
        List<ImageFileModel> allImage = sysFileMapper.getAllImage(user.getUserId());
        List<String> pathList = allImage.stream()
                .map(model -> model.getViewPath())
                .collect(Collectors.toList());
        ImageFileModel imageFileModel = new ImageFileModel();
        imageFileModel.setNginxViewList(pathList);
        return imageFileModel;
    }

    @Override
    public ImageFileModel searchImage(String keyword, String username) {
        SysUser user = sysUserMapper.getUserByName(username);
        if (ObjectUtil.isNull(user)){
            throw new RuntimeException("用户信息不存在！");
        }

        //如果传入的参数能转换成数字，则代表是日期
        List<ImageFileModel> imageFileModels = null;
        try {
            if (Integer.parseInt(keyword.replaceAll("-","")) > 0) {
                imageFileModels = sysFileMapper.searchImage(keyword, null,user.getUserId());
            }
        } catch (NumberFormatException e) {
            imageFileModels = sysFileMapper.searchImage(null, keyword, user.getUserId());
        }
        ImageFileModel finalImageFileModel = new ImageFileModel();
        if (CollectionUtil.isNotEmpty(imageFileModels)){
            List<String> viewPathList = imageFileModels.stream()
                    .map(ImageFileModel -> ImageFileModel.getViewPath())
                    .collect(Collectors.toList());
            finalImageFileModel.setNginxViewList(viewPathList);

        }
        return finalImageFileModel;
    }
}

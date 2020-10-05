package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.file.ImageFileModel;

import java.util.List;

public interface ImageService {
    /**
     * 根据日期展示图片的信息
     */
    List<ImageFileModel> getImageGroupByDate(String username);

    /**
     * 展示所有的图片
     */
    ImageFileModel getAllImage(String username);

    /**
     * 根据条件查询图片
     */
    ImageFileModel searchImage(String keyword, String username);
}

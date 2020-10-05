package com.lying.lyingdisk.controller;

import com.lying.lyingdisk.common.enums.ErrorCodeEnum;
import com.lying.lyingdisk.common.model.Result;
import com.lying.lyingdisk.common.model.file.ImageFileModel;
import com.lying.lyingdisk.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    /**
     * 图片展示，根据时间轴
     */
    @GetMapping("/getImageGroupByDate")
    @ResponseBody
    public Result getImageGroupByDate(String username){
        List<ImageFileModel> imageInfo = imageService.getImageGroupByDate(username);
        return  new Result(ErrorCodeEnum.SUCCESS.getCode(),"查询成功","1",imageInfo);
    }

    /**
     * 查询所有图片，根据时间排序展示
     */
    @GetMapping("/getAllImage")
    @ResponseBody
    public Result getAllImage(String username){
        ImageFileModel imageInfo = imageService.getAllImage(username);
        return  new Result(ErrorCodeEnum.SUCCESS.getCode(),"查询成功","1",imageInfo);
    }

    /**
     * 查询图片
     */
    @GetMapping("/searchImage")
    @ResponseBody
    public Result searchImage(String keyWord, String username){
        ImageFileModel ImageFileModel = imageService.searchImage(keyWord,username);
        return  new Result(ErrorCodeEnum.SUCCESS.getCode(),"查询成功","1",ImageFileModel);
    }
}

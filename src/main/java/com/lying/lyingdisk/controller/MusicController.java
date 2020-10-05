package com.lying.lyingdisk.controller;

import cn.hutool.core.util.StrUtil;
import com.lying.lyingdisk.common.enums.ErrorCodeEnum;
import com.lying.lyingdisk.common.model.Result;
import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @RequestMapping("/getAllMusicFile")
    @ResponseBody
    public Result getAllMusicFile(String username){
        if (StrUtil.isEmpty(username)){
            throw new RuntimeException("用户名不能为空");
        }
        List<AllFileModel> allMusicFile = musicService.getAllMusicFile(username);
        return new Result(ErrorCodeEnum.SUCCESS.getCode(),"查询成功","1",allMusicFile);
    }
}

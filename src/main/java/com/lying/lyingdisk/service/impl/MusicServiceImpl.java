package com.lying.lyingdisk.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.dao.SysFileMapper;
import com.lying.lyingdisk.entity.SysUser;
import com.lying.lyingdisk.service.MusicService;
import com.lying.lyingdisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    SysFileMapper sysFileMapper;
    @Autowired
    UserService userService;
    @Override
    public List<AllFileModel> getAllMusicFile(String username) {
        SysUser user = userService.getByName(username);
        if (ObjectUtil.isNull(user)){
            throw new RuntimeException("用户信息不能为空！");
        }
        List<AllFileModel> allMusicFile = sysFileMapper.getAllMusicFile(user.getUserId());
        return allMusicFile;
    }
}

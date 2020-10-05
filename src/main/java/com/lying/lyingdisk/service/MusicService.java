package com.lying.lyingdisk.service;


import com.lying.lyingdisk.common.model.file.AllFileModel;

import java.util.List;

public interface MusicService {
    /**
     * 查询所有的音乐文件
     */
    List<AllFileModel> getAllMusicFile(String username);
}

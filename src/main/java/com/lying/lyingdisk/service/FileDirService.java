package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.file.AllFileModel;

import java.util.List;

public interface FileDirService {
    /**
     * 创建文件夹
     */
    int createDir(String dirname, Long pid,Long uid);

    /**
     * 查询文件夹信息
     */
    List<AllFileModel> queryDirNames(Long pid, Long uid);

    /**
     * 删除当前文件夹及其子文件夹
     */
    void deleteFileDir(List<String> ids);
}

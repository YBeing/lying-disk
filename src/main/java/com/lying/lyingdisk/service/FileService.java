package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.entity.SysFile;

import java.util.List;

public interface FileService {
    /**
     * 通过id查询文件信息
     */
    List<AllFileModel> getFileNameByPid(Long pid, Long uid);

    /**
     * 添加文件信息
     */
    int addFileInfo(SysFile sysFile);

    /**
     * 删除文件
     */
    void deleteFiles(List<String> ids);
}

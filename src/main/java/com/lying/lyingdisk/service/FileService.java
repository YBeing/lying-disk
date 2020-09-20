package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.entity.SysFile;

import java.util.List;

public interface FileService {
    List<AllFileModel> getFileNameByPid(Long pid, Long uid);
    int addFileInfo(SysFile sysFile);
}

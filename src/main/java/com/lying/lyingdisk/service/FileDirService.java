package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.file.AllFileModel;

import java.util.List;

public interface FileDirService {
    int createDir(String dirname, Long pid,Long uid);
    List<AllFileModel> queryDirNames(Long pid, Long uid);
}

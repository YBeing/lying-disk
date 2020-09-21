package com.lying.lyingdisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lying.lyingdisk.common.convert.FileModelConvert;
import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.dao.SysDirMapper;
import com.lying.lyingdisk.entity.SysDir;
import com.lying.lyingdisk.service.FileDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class FileDirServiceImpl implements FileDirService {
    @Autowired
    private SysDirMapper sysDirMapper;
    @Override
    public int createDir(String dirname,Long pid,Long uid) {
        SysDir sysDir =new SysDir();
        sysDir.setDirName(dirname);
        sysDir.setPid(pid);
        sysDir.setUid(uid);
        int insert = sysDirMapper.insert(sysDir);
        Assert.isTrue(insert>0,"添加失败");
        return insert;
    }

    @Override
    public List<AllFileModel> queryDirNames(Long pid ,Long uid) {
        List<SysDir> sysDirList = sysDirMapper.getByPid(pid,uid);
        if (CollectionUtil.isEmpty(sysDirList)){
            return null;
        }
        List<AllFileModel> allFileModelList = FileModelConvert.toAllFileDirModel(sysDirList);

        return allFileModelList;
    }

    @Override
    public void deleteFileDir(List<String> ids) {
        sysDirMapper.deleteFileDirs(ids);
    }
}

package com.lying.lyingdisk.common.convert;

import com.alibaba.fastjson.JSON;
import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.entity.SysDir;
import com.lying.lyingdisk.entity.SysFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileModelConvert {
    public static List<AllFileModel> toAllFileDirModel(List<SysDir> list){
        List<AllFileModel> allFileModelList =new ArrayList<>();
        list.forEach(sysDir -> {
            AllFileModel allFileModel = new AllFileModel();
            allFileModel.setFileName(sysDir.getDirName());
            allFileModel.setType("dir");
            //这个pid是为了给前台创建目录以及进入目录查看其子目录使用的
            allFileModel.setPid(sysDir.getId().toString());
            //这个id是为了我们删除和下载使用的
            allFileModel.setId(sysDir.getId().toString());
            allFileModelList.add(allFileModel);
        });

        return allFileModelList;

    }
    public static List<AllFileModel> toAllFileModel(List<SysFile> list){
        List<AllFileModel> allFileModelList =new ArrayList<>();
        list.forEach(sysFile -> {
            AllFileModel allFileModel = new AllFileModel();
            allFileModel.setFileName(sysFile.getFileName());
            allFileModel.setFileSize(sysFile.getFileSize().toString());
            allFileModel.setModifyTime(sysFile.getModifyTime());
            allFileModel.setType(sysFile.getFileType());
            allFileModel.setPid("none");
            allFileModel.setId(sysFile.getId().toString());
            allFileModel.setNginxViewPath(sysFile.getNginxViewPath());
            allFileModelList.add(allFileModel);

        });

        return allFileModelList;

    }
    public static SysFile toEntity(Map map){
        String entityStr = JSON.toJSONString(map);
        SysFile sysFile = JSON.parseObject(entityStr, SysFile.class);
        return sysFile;

    }
}

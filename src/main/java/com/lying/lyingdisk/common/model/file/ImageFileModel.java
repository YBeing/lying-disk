package com.lying.lyingdisk.common.model.file;

import lombok.Data;

import java.util.List;

@Data
public class ImageFileModel {
    private String modifyTime;
    private String viewPathList;
    private String viewPath;
    private List<String> nginxViewList;

}

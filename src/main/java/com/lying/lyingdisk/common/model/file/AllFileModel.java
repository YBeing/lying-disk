package com.lying.lyingdisk.common.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllFileModel {
    private String fileName;
    private String fileSize;
    private String modifyTime;
    private String type;
    private String pid;
    private String id;
    private String nginxViewPath;
}

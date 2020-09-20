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
    private String nmodifyTime;
    private String type;
    private String pid;
}

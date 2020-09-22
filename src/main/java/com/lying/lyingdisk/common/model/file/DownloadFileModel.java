package com.lying.lyingdisk.common.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileModel {
    private List<InputStream> inputStreamList;
    private List<String>      fileNameList;
}

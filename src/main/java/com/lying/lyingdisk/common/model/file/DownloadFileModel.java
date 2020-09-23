package com.lying.lyingdisk.common.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileModel {
    private InputStream inputStream;
    private String     fileName;
}

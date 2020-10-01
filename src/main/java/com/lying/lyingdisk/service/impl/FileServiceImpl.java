package com.lying.lyingdisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.lying.lyingdisk.common.convert.FileModelConvert;
import com.lying.lyingdisk.common.model.file.AllFileModel;
import com.lying.lyingdisk.common.model.file.DownloadFileModel;
import com.lying.lyingdisk.common.model.file.MusicInfoModel;
import com.lying.lyingdisk.dao.SysFileMapper;
import com.lying.lyingdisk.entity.SysFile;
import com.lying.lyingdisk.service.FileService;
import com.lying.lyingdisk.util.FastDFSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;

    @Override
    public List<AllFileModel> getFileNameByPid(Long pid, Long uid) {
        List<SysFile> fileList = sysFileMapper.getByPid(pid, uid);
        if (CollectionUtil.isEmpty(fileList)) {
            return null;
        }

        List<AllFileModel> allFileModelList = FileModelConvert.toAllFileModel(fileList);

        return allFileModelList;
    }

    @Override
    public int addFileInfo(SysFile sysFile) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        sysFile.setModifyTime(currentDate);
        int insert = sysFileMapper.insert(sysFile);
        Assert.isTrue(insert > 0, "新增文件信息失败");
        return insert;
    }

    @Override
    @Transactional
    /**
     * filepath: group1/M00/00/00/wKgDMV9o4B-AHEQRAAcppDDlZRk814.png
     */
    public void deleteFiles(List<String> ids) {
        List<String> serverPathList = sysFileMapper.getFilesByIds(ids);
        fastDFSClientUtil.delFileList(serverPathList);
        sysFileMapper.deleteFiles(ids);


    }

    @Override
    @Transactional
    /**
     * filepath: group1/M00/00/00/wKgDMV9o4B-AHEQRAAcppDDlZRk814.png
     */
    public void deleteFilesByPid(List<String> pids) {
        List<String> serverPathList = sysFileMapper.getFilesByPids(pids);
        fastDFSClientUtil.delFileList(serverPathList);
        sysFileMapper.deleteFiles(pids);


    }


    @Override
    public DownloadFileModel downloadFile(String id) {
        if (StrUtil.isEmpty(id)) {
            throw new RuntimeException("id不能为空");
        }

        SysFile file = sysFileMapper.selectByPrimaryKey(Integer.parseInt(id));

        DownloadFileModel downloadFileModels = new DownloadFileModel();

        StorePath storePath = StorePath.parseFromUrl(file.getServerPath());
        InputStream inputStream = fastDFSClientUtil.download(storePath.getGroup(), storePath.getPath());

        downloadFileModels.setFileName(file.getFileName());
        downloadFileModels.setInputStream(inputStream);
        return downloadFileModels;

    }

    @Override
    public List<MusicInfoModel> getImageGroupByDate() {
        List<MusicInfoModel> imageInfo = sysFileMapper.getImageGroupByDate();
        imageInfo.forEach(image->{
            String viewPathList = image.getViewPathList();
            String[] PathList = viewPathList.split(",");
            image.setNginxViewList(Arrays.asList(PathList));
        });
        return imageInfo;
    }

    @Override
    public MusicInfoModel getAllImage() {
        List<MusicInfoModel> allImage = sysFileMapper.getAllImage();
        List<String> pathList = allImage.stream()
                .map(model -> model.getViewPath())
                .collect(Collectors.toList());
        MusicInfoModel musicInfoModel = new MusicInfoModel();
        musicInfoModel.setNginxViewList(pathList);
        return musicInfoModel;
    }
}

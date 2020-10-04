package com.lying.lyingdisk.dao;

import com.lying.lyingdisk.common.model.file.MusicInfoModel;
import com.lying.lyingdisk.entity.SysFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    List<SysFile> getByPid(@Param("pid") Long pid, @Param("uid") Long uid);

    void deleteFiles(List<String> ids);

    void deleteFilesByPid(List<String> pids);

    List<String> getFilesByIds(List<String> list);

    List<SysFile> getFilesInfoByIds(List<String> list);

    List<String> getFilesByPids(List<String> list);

    List<MusicInfoModel> getImageGroupByDate(Long uid);

    List<MusicInfoModel> getAllImage(Long uid);

    List<MusicInfoModel> searchImage(@Param("modifyTime") String modifyTime,@Param("fileName") String fileName,
                                     @Param("uid") Long uid);


}
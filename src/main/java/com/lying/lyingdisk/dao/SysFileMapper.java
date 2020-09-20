package com.lying.lyingdisk.dao;

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
}
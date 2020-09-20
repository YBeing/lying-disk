package com.lying.lyingdisk.dao;

import com.lying.lyingdisk.entity.SysDir;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDirMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDir record);

    int insertSelective(SysDir record);

    SysDir selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDir record);

    int updateByPrimaryKey(SysDir record);
    List<SysDir> getByPid(@Param("pid") Long pid,@Param("uid") Long uid);
}
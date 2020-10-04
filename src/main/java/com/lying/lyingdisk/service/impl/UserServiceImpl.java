package com.lying.lyingdisk.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lying.lyingdisk.common.model.user.UserCreateModel;
import com.lying.lyingdisk.dao.SysUserMapper;
import com.lying.lyingdisk.entity.SysUser;
import com.lying.lyingdisk.service.UserService;
import com.lying.lyingdisk.util.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public void registerUser(UserCreateModel model) {
        SysUser sysuser = getByName(model.getUsername());
        if (ObjectUtil.isNotNull(sysuser)){
            throw new RuntimeException("该用户已经存在！");
        }
        String salt = SaltUtil.getSalt();
        SysUser user =new SysUser();
        user.setUserName(model.getUsername());
        user.setUserSalt(salt);
        Md5Hash md5Hash =new Md5Hash(model.getPassword(),salt,1024);
        user.setUserPassword(md5Hash.toHex());
        int i = userMapper.insert(user);
        Assert.isTrue(i==1,"插入数据失败");

    }

    @Override
    public SysUser getByName(String username) {

        SysUser user = userMapper.getUserByName(username);
        return user;
    }
}

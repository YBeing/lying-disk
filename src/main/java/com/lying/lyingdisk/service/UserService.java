package com.lying.lyingdisk.service;

import com.lying.lyingdisk.common.model.user.UserCreateModel;
import com.lying.lyingdisk.entity.SysUser;

public interface UserService {
    void registerUser(UserCreateModel model);
    SysUser getByName(String username);
}

package com.lying.lyingdisk.shiro.relams;

import cn.hutool.core.util.ObjectUtil;
import com.lying.lyingdisk.dao.SysUserMapper;
import com.lying.lyingdisk.entity.SysUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //用户名
        String principal = (String)authenticationToken.getPrincipal();
        SysUser user = sysUserMapper.getUserByName(principal);

        if (!ObjectUtil.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUserName(),user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()),this.getName());
        }
        return null;
    }
}

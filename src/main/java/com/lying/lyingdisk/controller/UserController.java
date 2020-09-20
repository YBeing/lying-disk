package com.lying.lyingdisk.controller;

import cn.hutool.core.util.StrUtil;
import com.lying.lyingdisk.common.enums.ErrorCodeEnum;
import com.lying.lyingdisk.common.model.Result;
import com.lying.lyingdisk.common.model.user.UserCreateModel;
import com.lying.lyingdisk.service.UserService;
import com.lying.lyingdisk.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录成功后返回token
     */
    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestBody UserCreateModel model ){
        Subject subject= SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(model.getUsername(),model.getPassword()));
        } catch (UnknownAccountException e) {
            return new Result(ErrorCodeEnum.USERNAME_ERR.getCode(),ErrorCodeEnum.USERNAME_ERR.getDesc(),"0");
        }catch (IncorrectCredentialsException e){
            return new Result(ErrorCodeEnum.PASSWORD_ERR.getCode(),ErrorCodeEnum.PASSWORD_ERR.getDesc(),"0");

        }
        //获取token值
        Map<String,String> resultMap =new HashMap();
        String token = JwtUtils.getToken(model.getUsername(),model.getPassword());
        resultMap.put("token",token);
        resultMap.put("username",model.getUsername());

        return new Result(ErrorCodeEnum.SUCCESS.getCode(),"登录成功","1",resultMap);
    }
    /**
     * 用户注册
     */
    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody UserCreateModel model){
        if (StrUtil.isEmpty(model.getUsername())){
            return new Result(ErrorCodeEnum.USERNAME_NULL.getCode(),ErrorCodeEnum.USERNAME_NULL.getDesc(),"0");
        }
        if (StrUtil.isEmpty(model.getPassword())){
            return new Result(ErrorCodeEnum.PASSWORD_NULL.getCode(),ErrorCodeEnum.PASSWORD_NULL.getDesc(),"0");
        }
        userService.registerUser(model);
        return new Result(ErrorCodeEnum.SUCCESS.getCode(),"注册成功","1");

    }


}

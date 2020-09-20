package com.lying.lyingdisk.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    //成功
    SUCCESS("200","成功"),
    //参数相关
    USERNAME_ERR("101","用户名不存在"),
    PASSWORD_ERR("102","密码错误"),
    USERNAME_NULL("103","用户名不能为空"),
    PASSWORD_NULL("104","密码不能为空"),
    //系统相关
    TOKEN_OVERTIME("401","token过期"),
    CREATEDIR_ERR("403","创建文件夹失败"),
    SYSTEM_ERR("402","系统异常");
    private String code;
    private String desc;


}

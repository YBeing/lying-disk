package com.lying.lyingdisk.exceptionhandler;

import com.lying.lyingdisk.common.enums.ErrorCodeEnum;
import com.lying.lyingdisk.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 全局异常处理配置
 * @author EternalRay
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandleConf {

    /**
     * 顶级异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e){
        //annotation抛出的异常
        if(e instanceof UndeclaredThrowableException) {
            e = (Exception) ((UndeclaredThrowableException)e).getUndeclaredThrowable();
        }
        log.error("[handleException]: ",e);
        if (e.getMessage().equals("token过期了！！")){
            return new Result(ErrorCodeEnum.TOKEN_OVERTIME.getCode(),ErrorCodeEnum.TOKEN_OVERTIME.getDesc(),"0");
        }
        return new Result(ErrorCodeEnum.SYSTEM_ERR.getCode(),ErrorCodeEnum.SYSTEM_ERR.getDesc(),"0");
    }
}

package com.lying.lyingdisk.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String errorCode;
    /**
     * 0:失败，1:成功
     */
    private String  msg;
    private String status;

    private Object data;

    public Result(String errorCode,String msg, String status){
        this.errorCode=errorCode;
        this.status=status;
        this.msg=msg;
    }

    public  Result(String status) {
        this.status=status;
    }

    public Result(Object data,String status) {
        this.data=data;
        this.status=status;
    }
}

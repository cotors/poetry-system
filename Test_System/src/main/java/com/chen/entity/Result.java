package com.chen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer ERROR_CODE = 500;

    public static Result success(){
        return new Result(SUCCESS_CODE,"操作成功",null);
    }

    public static Result success(Object data){
        return new Result(SUCCESS_CODE,"操作成功",data);
    }

    public static Result error(String msg){
        return new Result(ERROR_CODE,msg,null);
    }

    public static Result error(){
        return new Result(ERROR_CODE,"操作失败",null);
    }

}

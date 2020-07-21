package com.quality.inspector.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author hs
 * @data: 2019/05/20 12:00
 * @param:
 * @description: 基本响应封装
 */
@Data
@Getter
@Setter
public class BaseResponse {
    public boolean success;
    public String message;
    public String message_detail;
    public int    code;
    public String msg;

    public BaseResponse(){
    }

    public BaseResponse(CodeEnum code){
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public static BaseResponse out(CodeEnum code, HashMap image_deal) {
        return new BaseResponse(code);
    }

}

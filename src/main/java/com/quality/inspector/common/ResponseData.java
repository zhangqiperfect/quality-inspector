package com.quality.inspector.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应数据结构封装
 *
 * @param <T>
 * @author hs
 * @data 2019-05-20 13:49
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> implements Serializable {
    private boolean success;
    private String message;
    private String message_detail;
    private int code;
    private T data;


    public static <T> ResponseData<T> out(boolean success, String message, String message_detail, CodeEnum code, T data) {
        return new ResponseData<T>(success, message, message_detail, code.getCode(), data);
    }

}

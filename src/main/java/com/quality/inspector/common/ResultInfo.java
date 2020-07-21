package com.quality.inspector.common;

import java.io.Serializable;

/**
 * 结果类封装
 *
 * @author hs
 * @data 2019-05-21 13:48:42
 * @Description:
 */
public class ResultInfo implements Serializable {

    /**
     * 后端返回结果正常为true，发生异常返回false
     */
    private boolean flag;

    /**
     * 后端返回结果数据对象
     */
    private Object  data;

    /**
     * 发生异常的错误消息
     */
    private String  msg;

    public ResultInfo(){
    }

    public ResultInfo(boolean flag){
        this.flag = flag;
    }

    /**
     * @param flag
     * @param msg
     */
    public ResultInfo(boolean flag, String msg){
        this.flag = flag;
        this.msg = msg;
    }

    /**
     * @param flag
     * @param data
     * @param msg
     */
    public ResultInfo(boolean flag, Object data, String msg){
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }

    public ResultInfo(boolean flag, Object data){
        this.flag = flag;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

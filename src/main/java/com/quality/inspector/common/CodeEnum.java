package com.quality.inspector.common;

/**
 * @author hs
 * @data: 2019/05/20 12:00
 * @param:
 * @description: 自定义状态码响应类
 */
public enum CodeEnum {

                      /**
                       * 成功
                       */
                      SUCCESSS(200, "同步"),

                      /**
                       * 同步
                       */
                      RETURN_URI(201, "同步，并返回创建的资源的URI"),

                      /**
                       * 异步
                       */
                      ASYNCHRONIZATION(202, "异步"),

                      /**
                       * 同步删除成功
                       */
                      DELETE_SUCCESS(204, "同步删除成功"),

                      /**
                       * 请求格式错误
                       */
                      ILLEGAL_REQUEST(400, "不合法的请求格式"),

                      /**
                       * 未认证
                       */
                      UNVERIFIED(401, "未认证"),

                      /**
                       * 权限不够
                       */
                      AUTHENTICATED(403, "已认证,但不具备权限"),

                      /**
                       * 未获取到数据
                       */
                      NOT_FOUND(404, "请求资源不存在"),

                      /**
                       * 服务器异常
                       */
                      ERROR(500, "服务器内部错误!");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String  msg;

    CodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

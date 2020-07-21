package com.quality.inspector.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author hs
 * @data: 2019/11/11 16:22
 * @param:
 * @description:
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocket {

    /**
     * 静态变量，用来记录当前在线连接数，设计成线程安全
     */
    private static int                            onlineCount  = 0;
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    /**
     * 建立连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("【websocket消息】 有新的连接，总数{}", getOnlineCount());
    }

    /**
     * 连接关闭
     *
     * @param
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("【websocket消息】 连接断开，总数{}", getOnlineCount());
    }

    /**
     * 接收客户端消息
     *      *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【websocket消息】 收到客户端发来的消息：{}", message);
        // 群发消息
        for (WebSocket item : webSocketSet) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", message);
                item.sendMessage(jsonObject);
            } catch (Exception e) {
                log.error("websocket群发异常");
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.debug("连接发送错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param responseData
     */
    public void sendMessage(JSONObject responseData) {
        log.info("【websocket消息】 发送消息：{}", responseData);
        for (WebSocket webSocket : webSocketSet) {
            try {
                webSocket.session.getBasicRemote().sendText(JSONObject.toJSONString(responseData));
                if (webSocketSet.size() > 1) {
                    sendMessageAll(JSONObject.toJSONString(responseData));
                }
            } catch (IOException e) {
                log.error("webSocket发送错误");
            }
        }
    }

    public void sendMessageAll(String message) {
        for (WebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(JSONObject message) {
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

}

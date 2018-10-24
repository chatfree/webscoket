package com.jackfree.webscoket.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huijie.deng
 * @CreateDate: 2018/10/22
 */
public class MyWebSocketHandler  extends TextWebSocketHandler{

    static final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);

    static final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long mid = getMid(session);
        log.info("connection "+mid);
        sessions.put(mid,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long mid = getMid(session);
        log.info("close "+mid);
        sessions.remove(mid);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try {
            log.info("handleTransportError",exception);
            Long mid = getMid(session);
            session.close();
            sessions.remove(mid);
            log.info("websocket error: " + mid);
        } catch (Exception e) {
            log.error("handleTransportError error",e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("接收到客户端消息："+message.getPayload());
        session.sendMessage(new TextMessage("服务器回复消息："+message.getPayload()));
    }

    public Long getMid(WebSocketSession session){
        return  (Long) session.getAttributes().getOrDefault("mid", null);
    }

}

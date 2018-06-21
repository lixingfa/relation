/**
 * 
 */
package com.garlane.relation.common.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;  
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;  
import org.springframework.web.socket.handler.TextWebSocketHandler;  
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author lixingfa
 * @date 2018年6月21日上午10:53:39
 * 
 */
@Component
public class WebsocketHandler extends TextWebSocketHandler {  
    protected static final Logger LOG = Logger.getLogger(WebsocketHandler.class);

    public static final Map<Object, WebSocketSession> userSocketSessionMap;
    static {
        userSocketSessionMap = new HashMap<Object, WebSocketSession>();
    }
    /**
     * 建立连接后
     */
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String uid = (String) session.getAttributes().get("uid");
        if (userSocketSessionMap.get(uid) == null) {
            userSocketSessionMap.put(uid, session);
        }
        LOG.warn("======建立连接完成======");
    }
    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    @Override 
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            if(message.getPayloadLength()==0)return;
            Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
            String msgString = message.getPayload().toString();
            LOG.warn("收到的消息是：" + msgString);
            LOG.warn("发送的对象是：" + msg.getTo());
            msg.setDate(new Date());
            sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));

            LOG.warn("======消息处理结束======");
    }
    /**
     * 消息传输错误处理
     */
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Iterator<Entry<Object, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        LOG.warn("======消息传输错误======");
        // 移除Socket会话
        while (it.hasNext()) {
            Entry<Object, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }
    }
    /**
     * 关闭连接后
     */
    public void afterConnectionClosed(WebSocketSession session,
            CloseStatus closeStatus) throws Exception {
        LOG.warn("Websocket:" + session.getId() + "已经关闭");
        Iterator<Entry<Object, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        // 移除Socket会话
        LOG.warn("======关闭连接======");
        while (it.hasNext()) {
            Entry<Object, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                LOG.warn("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }
    }
    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线用户发送消息
     * 
     * @param message
     * @throws IOException
     */
    public void broadcast(final TextMessage message) throws IOException {
        Iterator<Entry<Object, WebSocketSession>> it = userSocketSessionMap
                .entrySet().iterator();
        LOG.warn("======群发======");
        // 多线程群发
        while (it.hasNext()) {
            final Entry<Object, WebSocketSession> entry = it.next();
            if (entry.getValue().isOpen()) {
                // entry.getValue().sendMessage(message);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
    /**
     * 给某个用户发送消息
     * 
     * @param userName
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(String uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        LOG.warn("======给某个用户发送消息======");
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
}  

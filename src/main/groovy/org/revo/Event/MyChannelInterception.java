package org.revo.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.util.Map;

/**
 * Created by ashraf on 09/10/16.
 */
public class MyChannelInterception extends ChannelInterceptorAdapter {
    @Autowired
    private Map<String, Boolean> onlineUsers;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageHeaderAccessor wrap = SimpMessageHeaderAccessor.wrap(message);
        if (wrap.getMessageType() == SimpMessageType.CONNECT_ACK || wrap.getMessageType() == SimpMessageType.CONNECT)
            onlineUsers.put(wrap.getUser().getName(), true);
        if (wrap.getMessageType() == SimpMessageType.DISCONNECT_ACK || wrap.getMessageType() == SimpMessageType.DISCONNECT)
            onlineUsers.put(wrap.getUser().getName(), false);

//        String id = ((User) ((OAuth2Authentication) wrap.getUser()).getPrincipal()).getId();
        return message;
    }
}
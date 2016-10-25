package org.revo.Event;

import org.revo.Domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * Created by ashraf on 06/09/16.
 */
@Component
public class MessageEvent {
    private final String path = "/topic/message";
    @Autowired
    SimpMessageSendingOperations operations;

    @EventListener
    public void AfterSaveMessage(AfterSaveEvent<Message> event) {
        Message source = event.getSource();
        operations.convertAndSendToUser(source.getTo().getUsername(), path, source);
    }
}

package org.revo.Service;

import org.revo.Domain.Message;
import org.revo.Domain.User;

import java.util.List;

/**
 * Created by ashraf on 30/08/16.
 */
public interface MessageService {
    Message save(Message message);

    List<Message> Messages(String id);

    List<Message> MessagesBetweenMeAnd(User to, String id, int limit);
}
   
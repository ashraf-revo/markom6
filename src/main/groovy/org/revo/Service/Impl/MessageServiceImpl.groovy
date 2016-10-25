package org.revo.Service.Impl

import org.bson.types.ObjectId
import org.revo.Domain.Message
import org.revo.Domain.User
import org.revo.Repository.MessageRepository
import org.revo.Service.CloudinaryService
import org.revo.Service.MessageService
import org.revo.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.data.mongodb.core.query.Criteria.where

/**
 * Created by ashraf on 30/08/16.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    UserService userService
    @Autowired
    CloudinaryService cloudinaryService

    @Override
    Message save(Message message) {
        message.to = userService.findOneById(message.to.id)
        if (message.file && message.file.length() > 0) {
            message.image = cloudinaryService.Upload(message.file);
            message.file = ""
        }
        messageRepository.save(message)
    }

    @Override
    List<Message> Messages(String id) {
        User current = userService.current()
        Query query = new Query()
        if (id != null && !id.trim().isEmpty())
            query.addCriteria(where("id").gt(new ObjectId(id)))
        query.addCriteria((where("from").exists(true) & "to").exists(true).orOperator(
                where("from").is(current),
                where("to").is(current)
        ))
        mongoOperations.find(query, Message.class)
    }

    @Override
    List<Message> MessagesBetweenMeAnd(User to, String id, int limit) {
        ObjectId oid;
        if (id != null && !id.trim().isEmpty()) oid = new ObjectId(id);
        else oid = new ObjectId();
        User from = userService.current()
        Query query = new Query()
        query.addCriteria(where("id").lt(oid))
                .addCriteria((where("from").exists(true) & "to").exists(true).orOperator(
                (where("from").is(from) & "to").is(to),
                (where("from").is(to) & "to").is(from)
        )).with(new Sort(DESC, "id"))
        if (limit > 0)
            query.limit(limit)
        mongoOperations.find(query, Message.class)
    }
}

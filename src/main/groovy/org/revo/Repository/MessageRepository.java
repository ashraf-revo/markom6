package org.revo.Repository;

import org.revo.Domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ashraf on 30/08/16.
 */
public interface MessageRepository extends MongoRepository<Message, String> {
}
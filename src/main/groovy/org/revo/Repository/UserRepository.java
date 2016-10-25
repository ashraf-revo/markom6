package org.revo.Repository;

import org.revo.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ashraf on 30/08/16.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameRegexOrNameRegexOrAboutRegexOrPhoneRegex(String r0, String r2, String r3, String r4);

    Optional<User> findByPhone(String phone);
}
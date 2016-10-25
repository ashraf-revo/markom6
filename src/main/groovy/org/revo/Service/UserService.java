package org.revo.Service;

import org.revo.Domain.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;
import java.util.Optional;

/**
 * Created by ashraf on 30/08/16.
 */
public interface UserService {
    User create(User user);

    User current();

    Optional<User> findByUsername(String s);

    Optional<User> findByPhone(String phone);

    List<User> AllUsers();

    List<User> search(String Regex);

    List<User> LastUsers();

    User user(String id);

    User findOneById(String id);

    OAuth2AccessToken createToken(User user, String pass);

}
   
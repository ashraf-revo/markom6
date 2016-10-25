package org.revo.Service.Impl

import org.revo.Domain.Message
import org.revo.Domain.User
import org.revo.Repository.UserRepository
import org.revo.Service.CloudinaryService
import org.revo.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.stereotype.Service

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.util.Assert.isNull

/**
 * Created by ashraf on 30/08/16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository
    @Autowired
    PasswordEncoder passwordEncoder
    @Autowired
    CloudinaryService cloudinaryService
    @Autowired
    MongoOperations mongoOperations
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    DefaultTokenServices defaultTokenServices;

    @Override
    User create(User user) {
        isNull(user.id);
        if (user.file&&user.file.length()>0) {
            user.image=cloudinaryService.UploadUserImage(user.file);
            user.file=""
        }
        user.password = passwordEncoder.encode(user.password);
        userRepository.save(user);
    }

    @Override
    User current() {
        SecurityContextHolder.context.authentication.principal as User
    }

    @Override
    Optional<User> findByUsername(String s) {
        userRepository.findByUsername(s)
    }

    @Override
    List<User> AllUsers() {
        User user = current()
        userRepository.findAll().findAll {
            it.id != user.id
        }
    }

    @Override
    List<User> search(String r) {
        return userRepository.findByUsernameRegexOrNameRegexOrAboutRegexOrPhoneRegex(r,r,r,r)
    }

    @Override
    List<User> LastUsers() {
        User current = current()
        Query query = new Query((where("from").exists(true) & "to").exists(true).orOperator(where("from").is(current), where("to").is(current)))
        query.with(new Sort(DESC, "createdate"))
        mongoOperations.find(query, Message.class).collect {
            current.id == it.from.id ? it.to : it.from
        }.unique { it.id }
    }

    @Override
    User user(String id) {
        userRepository.findOne(id)
    }

    @Override
    User findOneById(String id) {
        userRepository.findOne(id)
    }

    @Override
    OAuth2AccessToken createToken(User user, String pass) {
        String clientId = "revo";
        Map<String, String> param = new HashMap<String, String>();
        param.put("username", user.username);
        param.put("password", pass);
        param.put("client_id", clientId);
        param.put("client_secret", "revo");
        param.put("grant_type", "password");
        OAuth2Request oAuth2Request = new OAuth2Request(param, clientId,
                null, true, Arrays.asList("read", "write").toSet(),
                null, null, null, null);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.username, pass);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, SecurityContextHolder.context.authentication = authenticationManager.authenticate(authenticationToken));
        return defaultTokenServices.createAccessToken(auth);
    }

    @Override
    Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone)
    }
}

package org.revo.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.revo.Domain.User;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashraf on 30/08/16.
 */
@Configuration
public class Util {
    @Autowired
    UserService userService;

    @Bean
    Map<String, Boolean> onlineUsers() {
        return new HashMap<>();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingMongoEventListener eventListener(Validator validator) {
        return new ValidatingMongoEventListener(validator);
    }

    @Bean
    public AuditorAware<User> current() {
        return () -> userService.current();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return s -> userService.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Bean
    public Cloudinary cloudinary(CloudinaryProperties cloudinaryProperties) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryProperties.getCloud_name(),
                "api_key", cloudinaryProperties.getApi_key(),
                "api_secret", cloudinaryProperties.getApi_secret()));
    }
}

package org.revo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by ashraf on 31/08/16.
 */
@Configuration
@EnableResourceServer
public class Resource extends ResourceServerConfigurerAdapter {
    @Autowired
    LogoutHandler logoutHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hello/info").permitAll()
                .antMatchers("/manage/**").permitAll()
                .antMatchers("/api/user/**").hasRole("USER")
                .antMatchers("/api/message/**").hasRole("USER")
                .and().logout().logoutSuccessHandler(logoutHandler).and().csrf().disable();
    }
}

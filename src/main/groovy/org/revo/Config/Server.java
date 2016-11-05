package org.revo.Config;

import org.revo.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashraf on 31/08/16.
 */
@Configuration
@EnableAuthorizationServer
public class Server extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DataSource dataSource;
    @Autowired
    Environment environment;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Autowired
    TokenStore tokenStore;

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            User user = (User) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("user", user);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore).tokenEnhancer(tokenEnhancer());
    }

    @Bean
    DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        defaultTokenServices.setAccessTokenValiditySeconds(-1);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("revo").secret("revo").
                accessTokenValiditySeconds(-1).
                scopes("read", "write").authorities("ROLE_USER").
                authorizedGrantTypes("password", "authorization_code", "refresh_token");
    }
}

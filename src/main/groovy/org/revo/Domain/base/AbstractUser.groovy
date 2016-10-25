package org.revo.Domain.base

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created by revo on 7/28/16.
 */
@JsonIgnoreProperties(["authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"])
abstract class AbstractUser implements UserDetails {
    int type = 1

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        AuthorityUtils.createAuthorityList("ROLE_USER")
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }

    public List<String> getRoles() {
        return authorities*.getAuthority()
    }

}

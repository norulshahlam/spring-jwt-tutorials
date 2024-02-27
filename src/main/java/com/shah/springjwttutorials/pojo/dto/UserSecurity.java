package com.shah.springjwttutorials.pojo.dto;

import com.shah.springjwttutorials.pojo.entity.Role;
import com.shah.springjwttutorials.pojo.entity.UserRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author NORUL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSecurity implements UserDetails {

    private String email;
    private String name;
    private String password;
    private String company;
    private Boolean isEnabled;
    private List<Role> roles;

    public UserSecurity(UserRegistration userRegistration) {
        this.email = userRegistration.getEmail();
        this.name = userRegistration.getName();
        this.password = userRegistration.getPassword();
        this.company = userRegistration.getCompany();
        this.isEnabled = userRegistration.getIsEnabled();
        this.roles = userRegistration.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(
                role.getRoleName().name())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}

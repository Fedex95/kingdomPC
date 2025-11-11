package com.tienda.kpback.Config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tienda.kpback.Entity.UsuarioEnt;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private UsuarioEnt.Rol rol;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String username, String password, UsuarioEnt.Rol rol, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.authorities = authorities != null ? authorities : Collections.emptyList();
    }

    public Long getUserId() {
        return userId;
    }

    public UsuarioEnt.Rol getRol() {
        return rol;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }
}
package com.chuangshu.livable.entity;

import javax.persistence.*;

import com.chuangshu.livable.dto.InsertUserDTO;
import com.chuangshu.livable.dto.UpdateUserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class User implements UserDetails {
    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    public User(){}

    public User(InsertUserDTO insertUserDto){
        this.name = insertUserDto.getName();
        this.gender = insertUserDto.getGender();
        this.email = insertUserDto.getEmail();
        this.password = insertUserDto.getPassword();
    }

    public User(UpdateUserDTO updateUserDto){
        this.name = updateUserDto.getName();
        this.gender = updateUserDto.getGender();
        this.email = updateUserDto.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
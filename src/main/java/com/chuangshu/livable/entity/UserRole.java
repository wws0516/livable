package com.chuangshu.livable.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * @Author: wws
 * @Date: 2019-12-23 17:04
 */
@Data
public class UserRole {

    @Id
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public UserRole(){}

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

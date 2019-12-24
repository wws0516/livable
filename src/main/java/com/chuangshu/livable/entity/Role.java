package com.chuangshu.livable.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * @Author: wws
 * @Date: 2019-12-23 11:22
 */
@Data
public class Role {

    @Id
    private Integer roleId;
    private String name;


}

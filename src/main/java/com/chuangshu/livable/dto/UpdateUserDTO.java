package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-05 19:38
 */
@Data
public class UpdateUserDTO extends BaseDTO {

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

}

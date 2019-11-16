package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.Collection;

/**
 * @Author: wws
 * @Date: 2019-10-29 17:33
 */
@Data
public class UserDTO extends BaseDTO {

        public static final String USER_ID = "userId";
        public static final String NAME = "name";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        /**
         * 用户ID
         */
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

}

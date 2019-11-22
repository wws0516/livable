package com.chuangshu.livable;

import com.chuangshu.livable.dto.InsertUserDTO;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivableApplicationTests {

    @Autowired
    public UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void register() {
        InsertUserDTO insertUserDTO = new InsertUserDTO();
        insertUserDTO.setEmail("1578494176@qq.com");
        insertUserDTO.setName("吴伟盛");
        insertUserDTO.setGender("男");
        insertUserDTO.setPassword("111");

        User user = new User(insertUserDTO);
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

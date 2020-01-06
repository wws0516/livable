package com.chuangshu.livable.dto;

import com.chuangshu.livable.entity.PersonalInformation;
import com.chuangshu.livable.entity.User;
import lombok.Data;

@Data
public class PersonalInformationDTO {

    private Integer userId;
    private String name;
    private String phone;
    private String password;
    private Integer age;
    private String job;
    private String hobby;

    public PersonalInformationDTO(User user, PersonalInformation personalInformation){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.phone = personalInformation.getPhone();
        this.password = user.getPassword();
        this.age = personalInformation.getAge();
        this.job = personalInformation.getJob();
        this.hobby = personalInformation.getHobby();
    }

    public PersonalInformationDTO(){

    }
}

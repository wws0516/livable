package com.chuangshu.livable.StatusCode;


/**
 * @author 叶三秋
 * @date 2019/09/10
 */
public enum HouseStatusCode {
    HOUSE_UNCHECKED(100,"未审核"),
    HOUSE_CHECKED(200,"已审核");


    private Integer code;
    private String msg;

    private HouseStatusCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

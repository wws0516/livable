package com.chuangshu.livable.StatusCode;


/**
 * @author 叶三秋
 * @date 2019/09/10
 */
public enum HouseStatusCode {
    HOUSE_UNCHECKED("555","未审核"),
    HOUSE_CHECKED_SUCCESS("567","审核通过"),
    HOUSE_CHECKED_FAILURE("566","审核不通过");


    private String code;
    private String msg;

    private HouseStatusCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

package com.chuangshu.livable.StatusCode;


/**
 * @author 叶三秋
 * @date 2019/09/10
 */
public enum StatusCode {
    HOUSE_UNCHECKED("U","未审核"),
    HOUSE_CHECKED("C","已审核");


    private String code;
    private String msg;

    private StatusCode(String code, String msg){
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

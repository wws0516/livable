package com.chuangshu.livable.StatusCode;

/**
 * @author 叶三秋
 * @date 2019/11/22
 */
public enum LandlordStatusCode {
    LANDLORD_UNCHECKED("666","未审核"),
    LANDLORD_CHECKED_SUCCESS("678","审核通过"),
    LANDLORD_CHECKED_FAILURE("677","审核不通过");

    private String code;

    private String msg;

    private LandlordStatusCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "landlord_information")
public class LandlordInformation {
    public static final String USER_ID = "userId";
    public static final String ID_NUMBER = "idNumber";
    public static final String ID_CARD_PICTURE_F = "idCardPictureF";
    public static final String ID_CARD_PICTURE_R = "idCardPictureR";
    public static final String ALIPAY_NAME = "alipayName";
    public static final String ALIPAY_ACCOUNT = "alipayAccount";
    public static final String STATUS = "status";
    /**
     * 房东ID
     */
    @Id
    @Column(name = "user_id")
    private Integer landlordId;
    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private Long idNumber;
    /**
     * 身份证正面
     */
    @Column(name = "id_card_picture_f")
    private String idCardPictureF;
    /**
     * 身份证反面
     */
    @Column(name = "id_card_picture_r")
    private String idCardPictureR;
    /**
     * 支付宝账户名
     */
    @Column(name = "alipay_name")
    private String alipayName;
    /**
     * 支付宝账号
     */
    @Column(name = "alipay_account")
    private String alipayAccount;
    /**
     * 状态
     */
    private String status;
}
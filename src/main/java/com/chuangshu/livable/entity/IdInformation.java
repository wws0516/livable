package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "id_information")
public class IdInformation {
    /**
     * 预约id
     */
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 证件类型
     */
    @Column(name = "id_card_type")
    private String idCardType;

    /**
     * 证件号
     */
    @Column(name = "id_number")
    private Integer idNumber;

    /**
     * 证件照正面
     */
    @Column(name = "id_card_pic_z")
    private String idCardPicZ;

    /**
     * 证件照背面
     */
    @Column(name = "id_card_pic_f")
    private String idCardPicF;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String NAME = "name";

    public static final String ID_CARD_TYPE = "idCardType";

    public static final String ID_NUMBER = "idNumber";

    public static final String ID_CARD_PIC_Z = "idCardPicZ";

    public static final String ID_CARD_PIC_F = "idCardPicF";
}
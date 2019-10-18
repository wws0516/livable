package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class Looked {
    public static final String LOOKED_ID = "lookedId";
    public static final String LOOKING_ID = "lookingId";
    @Id
    @Column(name = "looked_id")
    private Integer lookedId;
    @Column(name = "looking_id")
    private Integer lookingId;
}
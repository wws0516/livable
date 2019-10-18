package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "opinion_count")
public class OpinionCount {
    public static final String OPINION_ID = "opinionId";
    public static final String COUNT = "count";
    /**
     * 意见ID
     */
    @Id
    @Column(name = "opinion_id")
    private Integer opinionId;
    /**
     * 点赞次数
     */
    private Integer count;
}
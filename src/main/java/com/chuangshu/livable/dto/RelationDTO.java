package com.chuangshu.livable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationDTO {
    private String houseTitle;
    private Integer houseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_time")
    private Date publishTime;
}

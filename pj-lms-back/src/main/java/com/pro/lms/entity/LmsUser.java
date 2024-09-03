package com.pro.lms.entity;

import com.pro.lms.auth.entity.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="PL_MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("유저테이블")
@Builder
public class LmsUser {

    @Id
    @ApiModelProperty("유저번호")
    @Column(name = "PM_USER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pmUserNo;

    @ApiModelProperty("유저ID")
    @Column(name = "PM_USER_ID")
    private String pmUserId;

    @ApiModelProperty("유저PWD")
    @Column(name = "PM_PWD")
    private String pmPwd;

    @ApiModelProperty("유저이름")
    @Column(name = "PM_NAME")
    private String pmName;

    @ApiModelProperty("유저휴대폰번호")
    @Column(name = "PM_PHONE")
    private String pmPhone;

    @ApiModelProperty("유저등록일자")
    @Column(name = "PM_REG_DT")
    private Date pmRegDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY")
    private Authority authority;

    @ApiModelProperty("유저권한")
    @Column(name = "USER_ROLE")
    private String userRole;

}
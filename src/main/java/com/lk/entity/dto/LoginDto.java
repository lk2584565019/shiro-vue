package com.lk.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LvKang
 * @createTime 2021-10-20
 */
@Data
public class LoginDto implements Serializable {

    private String accountCode; //用户名
    private String password;//密码
    private String mobile; //手机号
    private String validCode; //验证码

}

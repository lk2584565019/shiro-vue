package com.lk.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LvKang
 * @createTime 2021-10-20
 *
 * 自定义shiro存储对象  保存当前登录用户的部分信息
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String accountCode;

    private String username;

    private String mobile;
}

package com.lk.service;

import com.lk.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-20
 */
public interface UserService extends IService<User> {

    void registerUser(User user);
}

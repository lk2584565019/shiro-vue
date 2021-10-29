package com.lk.service.impl;

import com.lk.common.Constant;
import com.lk.entity.User;
import com.lk.exception.MyException;
import com.lk.mapper.UserMapper;
import com.lk.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lk.uitl.SaltUtils;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void registerUser(User user) {
        //得到用户的明文密码
        String password = user.getPassword();
        //获取随机盐
        String salt = SaltUtils.getSalt(Constant.SALT_LENGTH);
        //得到用户的加密密码
        String newPassword = SaltUtils.getPassword(password, salt);
        user.setPassword(newPassword);
        user.setSalt(salt);
        this.save(user);
    }
}

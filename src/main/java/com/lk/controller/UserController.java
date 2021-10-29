package com.lk.controller;


import com.lk.common.Result;
import com.lk.entity.User;
import com.lk.entity.bse.MyUser;
import com.lk.exception.MyException;
import com.lk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-20
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequiresAuthentication //是否登录验证 未登录抛异常
    @GetMapping("/index")
    public  Result index(Long id){
        User user = userService.getById(id);
        if(user == null){
            return Result.error().message("查询失败"+ MyUser.getUsername());
        }
        return Result.success().message("查询成功"+ MyUser.getUsername()).data(user);
    }

    @RequiresAuthentication //是否登录验证 未登录抛异常
    @PostMapping("/updateUser") //测试是否有自动填充功能
    public  Result updateUser(Long id){
        User user = userService.getById(id);
        user.setUserType(1);
        userService.saveOrUpdate(user);
        return Result.success().message("保存成功").data(user);
    }

    /**
     * @Validated 启动校验
     * @param user
     * @return
     */
    @RequiresAuthentication //是否登录验证 未登录抛异常
    @PostMapping("/save")
    public  Result save(@Validated User user){

        return Result.success().message("保存成功").data(user);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result registerUser(@Validated User user, String phoneCode){
        Assert.notNull(user,"数据为空，请检查");
        if(StringUtils.isEmpty(phoneCode)){
            return  Result.error().message("注册失败,验证码为空！");
        }
        //对比验证码
        String staticCode = "123456";
        if(!staticCode.equals(phoneCode)){
            return  Result.error().message("注册失败,验证码错误！");
        }
        //注册开始
        userService.registerUser(user);
        return  Result.success().message("注册成功");
    }

    @PostMapping("/getOneUser")
    public Result getOneUser(){
        List<User> list = userService.list();
        return  Result.success().data(list.get(0));
    }

    @GetMapping("/saveCatch")
    public Result saveCatch(){
        List<User> list = userService.list();
        User user = list.get(0);
        try {
            int i =  1/0;
            user.setUsername("11111111111");
            userService.updateById(user);
            System.out.println("保存成功");
        }catch (Exception e){
            user.setUsername("222222222222");
            userService.updateById(user);
            System.out.println("失败保存");
        }
        return  Result.success().data(user);
    }
}

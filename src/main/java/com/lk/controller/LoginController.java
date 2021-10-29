package com.lk.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lk.common.Result;
import com.lk.entity.User;
import com.lk.entity.dto.LoginDto;
import com.lk.service.UserService;
import com.lk.uitl.JwtUtils;
import com.lk.uitl.SaltUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author LvKang
 * @createTime 2021-10-20
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/login")
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        Assert.notNull(loginDto,"数据异常");//断言拦截
        Assert.hasLength(loginDto.getAccountCode(),"用户名为空");
        Assert.hasLength(loginDto.getPassword(),"密码为空");

        //查询用户详情
        User user = userService.getOne(new QueryWrapper<User>().eq("account_code", loginDto.getAccountCode()));
        Assert.notNull(user,"用户不存在");
        //判断账号密码是否错误 因为是md5加密所以这里md5判断
        Assert.hasLength(user.getPassword(),"用户数据异常，请联系管理员");
        Assert.hasLength(user.getSalt(),"用户数据异常，请联系管理员");
        if(!SaltUtils.validPassword(loginDto.getPassword(),user.getSalt(),user.getPassword())){
            //密码不同则抛出异常
            return Result.error().message("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        //将token 放在我们的header里面
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.success().data(MapUtil.builder()
                .put("id",user.getId())
                .put("accountCode",user.getAccountCode())
                .put("username",user.getUsername())
                 .map()

        );
    }

    //需要认证权限才能退出登录
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        //退出登录
        SecurityUtils.getSubject().logout();
        return Result.success().message("退出成功");
    }

    @GetMapping("/setSession")
    public Result setSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("1111","11111");
        return Result.success().message("操作成功"+session.getId());
    }

    @GetMapping("/getSession")
    public Result getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return Result.success().message("操作成功"+session.getId()).data( session.getAttribute("1111"));
    }

    @GetMapping("/removeSession")
    public Result removeSession(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        Cookie cookie = new Cookie("shiro_vue",session.getId());
        cookie.setMaxAge(60);//60秒
        response.addCookie(cookie);
        session.invalidate();//移除
        return Result.success().message("操作成功"+session.getId());
    }

    @GetMapping("/setCookie")
    public Result setCookie(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        Cookie cookie = new Cookie("this_cookie",session.getId());
        cookie.setMaxAge(60);//60秒
        response.addCookie(cookie);
        session.invalidate();//移除
        return Result.success().message("操作成功"+session.getId());
    }

    @GetMapping("/getCookie")
    public Result getCookie(HttpServletRequest request,HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if("this_cookie".equals(cookie.getName())){
                return Result.success().message("操作成功"+cookie.getValue());
            }
        }
        return Result.error().message("操作失败");
    }

}

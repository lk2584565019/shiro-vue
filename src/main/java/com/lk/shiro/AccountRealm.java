package com.lk.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.lk.entity.User;
import com.lk.service.UserService;
import com.lk.uitl.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author LvKang
 * @createTime 2021-10-20
 */
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    //为了让realm支持jwt的凭证校验
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //赋予权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile accountProfile = (AccountProfile)principalCollection.getPrimaryPrincipal();
        return null;
    }
    //登录认证校验
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        Claims claims = jwtUtils.getClaimByToken((String)jwtToken.getPrincipal());
        //校验是否为空和时间是否过期
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            throw new ExpiredCredentialsException("token已失效,请重新登录");
        }
        //获取userId
        String userId = claims.getSubject();
        if(StringUtils.isEmpty(userId)){
            throw new ExpiredCredentialsException("token已失效,请重新登录");
        }
        //获取用户内容
        User user = userService.getById(Long.valueOf(userId));
        if(user == null){
            throw new UnknownAccountException("账户不存在");
        }
        if(user.getStatus() == -1){
            throw new LockedAccountException("账户被锁定");
        }
        //创建自定义shiro存储对象 可以存储自定义的信息给权限赋予的时候拿到 也可以直接在代码中获取
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user,profile);
        //用户信息  密钥token 用户名字
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}


package com.lk.entity.bse;

import com.alibaba.fastjson.JSON;
import com.lk.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author LvKang
 * @createTime 2021-10-21
 *
 * 登录的当前用户
 */
public class MyUser {

    //获取当前登录用户的id
    public static Long getUserId(){
        AccountProfile accountProfile = getAccountProfile();
        if(accountProfile == null){
            return null;
        }else{
            return accountProfile.getId();
        }
    }

    //获取当前登录用户的账号
    public static String getAccountCode(){
        AccountProfile accountProfile = getAccountProfile();
        if(accountProfile == null){
            return null;
        }else{
            return accountProfile.getAccountCode();
        }
    }

    //获取当前登录用户的名称
    public static String getUsername(){
        AccountProfile accountProfile = getAccountProfile();
        if(accountProfile == null){
            return null;
        }else{
            return accountProfile.getUsername();
        }
    }

    //得到当前用户的对象
    public static AccountProfile getAccountProfile(){
        Object obj = SecurityUtils.getSubject().getPrincipal();
        AccountProfile  accountProfile= null;
        if (obj instanceof AccountProfile) {
            accountProfile = (AccountProfile)obj;
        } else { //强转
            try {
                accountProfile = JSON.parseObject(JSON.toJSON(obj).toString(), AccountProfile.class);
            }catch (Exception e){
                accountProfile = null;
            }
        }
        return accountProfile;
    }
}

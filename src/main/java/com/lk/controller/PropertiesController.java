package com.lk.controller;

import com.lk.common.Result;
import com.lk.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LvKang
 * @createTime 2021-10-29
 */
@RestController
@RequestMapping("/test")
public class PropertiesController {
    @Autowired
    SystemProperties systemProperties;
    @GetMapping("/getSystem")
    public Result getSystem(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",systemProperties.getName());
        map.put("email",systemProperties.getEmail());
        return Result.success().data(map).message("操作成功");
    }
}

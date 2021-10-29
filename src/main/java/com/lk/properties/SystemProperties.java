package com.lk.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author LvKang
 * @createTime 2021-10-08
 */
//@PropertySource("classpath:config/system.properties")
@PropertySource(value = {"classpath:config/system.properties","file:${spring.profiles.path}/system.properties"},ignoreResourceNotFound = true)
@Component
@Data
public class SystemProperties {

    @Value("${name}")
    private  String name;
    @Value("${email}")
    private  String email;
}


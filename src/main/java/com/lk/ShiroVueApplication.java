package com.lk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.lk.mapper"})
public class ShiroVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroVueApplication.class, args);
    }

}

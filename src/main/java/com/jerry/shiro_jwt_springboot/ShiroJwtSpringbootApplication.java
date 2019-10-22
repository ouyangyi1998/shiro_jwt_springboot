package com.jerry.shiro_jwt_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.jerry.shiro_jwt_springboot.mapper")
public class ShiroJwtSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtSpringbootApplication.class, args);
    }

}

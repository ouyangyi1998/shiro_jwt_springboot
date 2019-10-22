package com.jerry.shiro_jwt_springboot.controller;

import com.jerry.shiro_jwt_springboot.model.resultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {
    private final resultMap resultMap;
    @Autowired
    public GuestController(resultMap resultMap)
    {
        this.resultMap=resultMap;
    }
    @GetMapping("/welcome")
    public resultMap login()
    {
        return resultMap.success().code(200).message("欢迎访问游客界面");
    }
}

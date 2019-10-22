package com.jerry.shiro_jwt_springboot.controller;

import com.jerry.shiro_jwt_springboot.mapper.UserMapper;
import com.jerry.shiro_jwt_springboot.model.resultMap;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserMapper userMapper;
    private final resultMap resultMap;

    @Autowired
    public AdminController(UserMapper userMapper, resultMap resultMap) {
        this.userMapper = userMapper;
        this.resultMap = resultMap;
    }
    @GetMapping("/getUser")
    @RequiresRoles("admin")
    public resultMap getUser()
    {
        List<String> list=userMapper.getUser();
        return resultMap.success().code(200).message(list);
    }
    @GetMapping("/banUser")
    @RequiresRoles("admin")
    public resultMap updatePassword(String username)
    {
        userMapper.banUser(username);
        return resultMap.success().code(200).message("成功封号");
    }
}

package com.jerry.shiro_jwt_springboot.controller;

import com.jerry.shiro_jwt_springboot.mapper.UserMapper;
import com.jerry.shiro_jwt_springboot.model.resultMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserMapper userMapper;
    private final resultMap resultMap;
    @Autowired
    public UserController(UserMapper userMapper, resultMap resultMap)
    {
        this.resultMap=resultMap;
        this.userMapper=userMapper;
    }
    @GetMapping("/getMessage")
    @RequiresRoles(logical = Logical.OR,value = {"user","admin"})
    public resultMap getMessage()
    {
        return resultMap.success().code(200).message("成功获得消息");
    }
    @PostMapping("/updatePassword")
    @RequiresRoles(logical = Logical.OR,value = {"user","admin"})
    public resultMap updatePassword(String username,String oldPassword,String newPassword)
    {
        String dataBasePassword=userMapper.getPassword(username);
        if(dataBasePassword.equals(oldPassword))
        {
            userMapper.updatePassword(username,newPassword);
        }else {
            return resultMap.fail().message("密码错误");
        }
        return resultMap.success().code(200).message("获得消息");
    }
    @GetMapping("/getVipMessage")
    @RequiresRoles(logical = Logical.OR,value = {"user","admin"})
    @RequiresPermissions("vip")
    public resultMap getVipMessage()
    {
        return resultMap.success().code(200).message("成功获得vip消息");
    }
}

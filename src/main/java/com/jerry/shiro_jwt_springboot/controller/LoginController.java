package com.jerry.shiro_jwt_springboot.controller;

import com.jerry.shiro_jwt_springboot.mapper.UserMapper;
import com.jerry.shiro_jwt_springboot.model.resultMap;
import com.jerry.shiro_jwt_springboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {
    private final UserMapper userMapper;
    private final resultMap resultMap;
    @Autowired
    public LoginController(UserMapper userMapper, resultMap resultMap)
    {
        this.resultMap=resultMap;
        this.userMapper=userMapper;
    }
    @PostMapping("/login")
    public resultMap login(@RequestParam("username")String username,@RequestParam("password")String password)
    {
        String realPassword=userMapper.getPassword(username);
        if(realPassword==null)
        {
            return resultMap.fail().code(401).message("用户名错误");
        }else if(!realPassword.equals(password))
        {
            return resultMap.fail().code(401).message("密码错误");
        }else
        {
            return resultMap.success().code(200).message(JwtUtil.createToken(username));
        }
    }
    @RequestMapping(path = "/unauthorized/{message}")
    public resultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException
    {
        return resultMap.success().code(401).message(message);
    }
}

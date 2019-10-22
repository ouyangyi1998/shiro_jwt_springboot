package com.jerry.shiro_jwt_springboot.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class resultMap extends HashMap<String,Object> {
    public resultMap()
    {}
    public resultMap success()
    {
        this.put("result","success");
        return this;
    }
    public resultMap fail()
    {
        this.put("result","fail");
        return this;
    }
    public resultMap code(int code)
    {
        this.put("code",code);
        return this;
    }
    public resultMap message(Object message)
    {
        this.put("message",message);
        return this;
    }
}

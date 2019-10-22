package com.jerry.shiro_jwt_springboot.controller;

import com.jerry.shiro_jwt_springboot.model.resultMap;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {
    private final resultMap resultMap;
    @Autowired
    public ExceptionController(resultMap resultMap)
    {
        this.resultMap=resultMap;
    }
    @ExceptionHandler(ShiroException.class)
    public resultMap handle401()
    {
        return resultMap.fail().code(401).message("没有权限");
    }
    @ExceptionHandler(Exception.class)
    public resultMap globalException(HttpServletRequest request
    ,Throwable ex)
    {
        return resultMap.fail().code(getStatus(request).value()).message("访问出错"+ex.getMessage());
    }
    private HttpStatus getStatus(HttpServletRequest request)
    {
        Integer statusCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==null)
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

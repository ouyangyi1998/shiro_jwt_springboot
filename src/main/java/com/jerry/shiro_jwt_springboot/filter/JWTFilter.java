package com.jerry.shiro_jwt_springboot.filter;

import com.jerry.shiro_jwt_springboot.shiro.JWTToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        if(isLoginAttempt(request,response))
        {
            try{
                executeLogin(request,response);
                return true;
            }catch (Exception e)
            {
                responseError(response,e.getMessage());
            }
        }
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req=(HttpServletRequest)request;
        String token=req.getHeader("Token");
        return token!=null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req=(HttpServletRequest)request;
        String token=req.getHeader("Token");
        JWTToken jwtToken=new JWTToken(token);
        getSubject(request,response).login(jwtToken);
        return true;
    }
    private void responseError(ServletResponse response,String message)
    {
        try{
            HttpServletResponse response1=(HttpServletResponse)response;
            message= URLEncoder.encode(message,"utf8");
            response1.sendRedirect("/unauthorized/"+message);
        }catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}

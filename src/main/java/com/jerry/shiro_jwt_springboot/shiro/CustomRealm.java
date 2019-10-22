package com.jerry.shiro_jwt_springboot.shiro;

import com.jerry.shiro_jwt_springboot.mapper.UserMapper;
import com.jerry.shiro_jwt_springboot.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {
    private final UserMapper userMapper;
    @Autowired
    public CustomRealm(UserMapper userMapper)
    {
        this.userMapper=userMapper;
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token=(String)authenticationToken.getCredentials();
        String username= JwtUtil.getUsername(token);
        if(username==null||!JwtUtil.verify(token,username))
        {
            throw new AuthenticationException("token认证失败");
        }String password=userMapper.getPassword(username);
        if(password==null)
        {
            throw new AuthenticationException("用户不存在");
        }
        int ban=userMapper.checkUserBanStatus(username);
        if(ban==1)
        {
            throw new AuthenticationException("用户封号");
        }
        return new SimpleAuthenticationInfo(token,token,"MyRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String username=JwtUtil.getUsername(principalCollection.toString());
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        String role=userMapper.getRole(username);
        /*默认权限*/
        String rolePermission=userMapper.getRolePermission(username);
        String permission=userMapper.getPermission(username);
        Set<String> roleSet=new HashSet<>();
        Set<String> permissionSet=new HashSet<>();
        roleSet.add(role);
        permissionSet.add(rolePermission);
        permissionSet.add(permission);
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}

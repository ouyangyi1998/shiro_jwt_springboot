package com.jerry.shiro_jwt_springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    String getPassword(String username);
    String getRole(String username);
    void updatePassword(@Param("username")String username,@Param("newPassword")String newPassword);
    List<String>getUser();
    void banUser(String username);
    int checkUserBanStatus(String username);
    String getRolePermission(String username);
    String getPermission(String username);
}

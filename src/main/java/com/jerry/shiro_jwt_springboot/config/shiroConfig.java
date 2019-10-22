package com.jerry.shiro_jwt_springboot.config;

import com.jerry.shiro_jwt_springboot.filter.JWTFilter;
import com.jerry.shiro_jwt_springboot.shiro.CustomRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap=new LinkedHashMap<>();
        filterMap.put("jwt",new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String,String> filterRuleMap=new HashMap<>();
        filterRuleMap.put("/**","jwt");
        filterRuleMap.put("/unauthorized/**","anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm)
    {
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        DefaultSubjectDAO subjectDAO=new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator=new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator()
    {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }
}

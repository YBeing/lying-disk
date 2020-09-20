package com.lying.lyingdisk.config;

import com.lying.lyingdisk.shiro.relams.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {
    //1.创建shiroFilter拦截器，负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        //创建shiro的filter
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注入安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //后端使用AOP进行拦截，就不使用shiro自带的了，不然会有冲突
/*        Map<String,String> map =new HashMap<>();
        //配置公共放行资源
*//*        map.put("/login.html","anon");
        map.put("/index.html","anon");*//*
        map.put("/login","anon");
        map.put("/register","anon");
        map.put("/test","anon");
        //配置系统受限资源
        // “/**”代表拦截项目中的一切资源，authc代表filter的一个别名
        map.put("/**","authc");
        //默认认证的界面
        shiroFilterFactoryBean.setLoginUrl("/index.html");
        //配置认证和授权规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);*/

        return shiroFilterFactoryBean;
    }

    //2.配置web管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //3.配置自定义realm
    @Bean
    public Realm getRealm(){
        CustomRealm customRealm = new CustomRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理器
        customRealm.setCachingEnabled(true);
        customRealm.setAuthorizationCachingEnabled(true);
        customRealm.setAuthenticationCachingEnabled(true);
        customRealm.setCacheManager(new EhCacheManager());

        return customRealm;
    }

}

package com.lying.lyingdisk.aop.auth;

import cn.hutool.core.util.StrUtil;
import com.lying.lyingdisk.service.UserService;
import com.lying.lyingdisk.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Order(0)
@Component
@Slf4j
public class AuthCheckTokenAop {
    private static final String  HEAD_AUTHORIZATION ="Authorization";

    @Autowired
    private UserService userService;

    /**
     * 请求切点方法(已提供@RequestMapping,@GetMapping,@PostMapping注解，需要其它请增加)
     */
    @Pointcut(" @annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "   @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "   @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void requestMapping(){

    }

    /**
     * 范围切点方法
     */
    @Pointcut("execution(* com.lying.lyingdisk.controller.*.*(..))")
    public void methodPointCut() {
    }

    /**
     * 除了我们的登录注册方法，其他均需要被我们的aop拦截进行权限校验
     */
    @Before("requestMapping() && methodPointCut() && !execution(* com.lying.lyingdisk.controller.UserController.*(..))")
    void doBefore(JoinPoint joinPoint) throws Exception{

            log.info("进入AOP方法认证...");
            authLogic(joinPoint);
    }

    /**
     * 认证逻辑
     * @param joinPoint
     * @throws Exception
     */
    private void authLogic(JoinPoint joinPoint) throws Exception {
        log.info("认证开始...");
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("ClassName: "+clazzName);
        log.info("MethodName:"+methodName);
        //获取当前http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(HEAD_AUTHORIZATION);
        String username = request.getHeader("username");
        //此处的TOKEN验证业务逻辑自行编写
        if (StrUtil.isEmpty(token)){
            throw new Exception("token为空！");
        }

        boolean checkToken = JwtUtils.checkToken(token,username);
        log.debug(token);
        if(checkToken){
            log.debug("请求认证通过！");
        }else {
            throw new Exception("token过期了！！");
        }
    }

}

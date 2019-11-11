package com.blog.aspectj;

import com.blog.model.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Aspect
@Component
public class LogAspectj {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Pointcut("execution(* com.blog.service.*.*.*(..))")
    public void logPointCut() {}
    @Before("logPointCut()")
    public void beforeAdvice() {
        System.out.println("前置通知");
    }
    @After("logPointCut()")
    public void afterAdvice() {
        System.out.println("后置通知");
    }
    @AfterReturning(value="logPointCut()",argNames = "joinPoint,rtv",returning="rtv")
    public void afterReturnParam(JoinPoint joinPoint, Object rtv) {
        Log log = new Log();
        Object[] args = joinPoint.getArgs();
        if(args == null) {
            return;
        }
        // 获取类名
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println(simpleName);
        log.setClassName(simpleName);
        //获取方法名
        String name = joinPoint.getSignature().getName();
        System.out.println(name);
        log.setMethodName(name);
        // 获取参数
        StringBuffer sb = new StringBuffer();
        sb.append("参数【");
        for(int i=0; i<args.length; i++) {
            sb.append(args[i] + ",");
        }
        sb.append("】");
        System.out.println(sb.toString());
        log.setReqParams(sb.toString());

        // 获取请求的地址
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        System.out.println(url);
        log.setReqUrl(url);
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY,8);
        //UserInfo.setCurrentTime(calendar.getTime());
        //mongoTemplate.save(UserInfo,"zcy1");
        log.setCreateTime(calendar.getTime());
        mongoTemplate.save(log);
    }
}

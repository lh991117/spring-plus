package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAccessLoggingAspect {

    @Before("execution(* org.example.expert.domain.user.controller.UserController.getUser(..))")
    public void logBeforeChangeUserRole(JoinPoint joinPoint) {
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String userId= Optional.ofNullable(request.getAttribute("userId"))
                    .map(String::valueOf)
                    .orElse(null);

            String requestUrl = request.getRequestURI();
            LocalDateTime requestTime = LocalDateTime.now();

            log.info("Admin Access Log - User ID: {}, Request Time: {}, Request URL: {}, Method: {}",
                    userId, requestTime, requestUrl, joinPoint.getSignature().getName());
        }catch (Exception e){
            log.error("Failed to log admin access logging", e);
        }
    }
}

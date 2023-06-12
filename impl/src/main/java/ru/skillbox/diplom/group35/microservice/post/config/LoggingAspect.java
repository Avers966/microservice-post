package ru.skillbox.diplom.group35.microservice.post.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AspectLogging
 *
 * @author Vladimir Polochanin
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {

    }

    @Pointcut("within(ru.skillbox.diplom.group35.microservice.post..*)" +
            " || within(ru.skillbox.diplom.group35.microservice.post.service..*)" +
            " || within(ru.skillbox.diplom.group35.microservice.post.resource..*)")
    public void applicationPackagePointcut() {

    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "NULL");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed();
        if (log.isInfoEnabled()) {
            log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
        }
        return result;
    }
}

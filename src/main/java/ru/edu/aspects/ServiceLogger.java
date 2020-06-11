package ru.edu.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ServiceLogger {

    private static Logger logger = LoggerFactory.getLogger(ServiceLogger.class);
    private long time;

    @Pointcut("execution(public * ru.edu.service.CrudService.*(..))")
    public void serviceMethod() { }

    @Before("serviceMethod()")
    public void beforeCallAt(JoinPoint jp) {
        String serviceName = jp.getSignature().getDeclaringType().getSimpleName();
        String methodName = jp.getSignature().getName();
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        logger.info("Call: " + serviceName + "->" + methodName + ", args=[" + args + "]");

        //Да, тут скорее всего время будет перезаписываться при нормальной работе сервиса
        //Но это же тестовый пример
        time = System.currentTimeMillis();
    }

    @After("serviceMethod()")
    public void afterCallAt(JoinPoint jp) {
        logger.info("Method: " + jp.toString() + " done by: " + (System.currentTimeMillis() - time) + " sec");
    }
}

package com.aaron.app.crud_app.logging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LogMethodEntryAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogMethodEntryAspect.class);

    @Before("@annotation(com.aaron.app.crud_app.logging.LogMethodEntry)")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entered method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}

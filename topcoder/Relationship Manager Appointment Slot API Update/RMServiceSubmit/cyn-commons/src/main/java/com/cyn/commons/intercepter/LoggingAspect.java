package com.cyn.commons.intercepter;

import com.cyn.commons.exception.BaseServiceException;
import java.util.Arrays;
import javax.persistence.PersistenceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service and repository Spring components. Intercepts service,
 * controller and repository classes and logs inputs and outputs. Handles exceptions and only logs
 * system related exceptions and business related exceptions are caught by other components and
 * converted into proper response formats,
 */
@Aspect
@Component
public class LoggingAspect {

  /** Slf4j logger to output debug info * */
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  /** Pointcut that matches all repositories, services and Web REST endpoints. */
  @Pointcut(
      "within(@org.springframework.stereotype.Repository *)"
          + " || within(@org.springframework.stereotype.Service *)"
          + " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /** Pointcut that matches all Spring beans in the application's main packages. */
  @Pointcut(
      "within(com.cyn.ccds..*.server..*)"
          + " || within(com.cyn.ccds.*.server.service..*)"
          + " || within(com.cyn.ccds..*.server.controller..*)")
  public void applicationPackagePointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice
   * @param e exception
   */
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    if (e instanceof BaseServiceException) { // Business related exception
      log.error(
          "Business Exception in {}.{}() with reason = {}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          e.getMessage());
    } else if (e instanceof PersistenceException) { // Database exception
      log.error(
          "Persistence Exception in {}.{}() with reason = {}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          e.getMessage());
    } else { // System exception
      log.error("Something went wrong", e);
    }
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isDebugEnabled()) {
      log.debug(
          "Enter: {}.{}() with argument[s] = {}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug(
            "Exit: {}.{}() with result = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result);
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error(
          "Illegal argument: {} in {}.{}()",
          Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
      throw e;
    }
  }
}

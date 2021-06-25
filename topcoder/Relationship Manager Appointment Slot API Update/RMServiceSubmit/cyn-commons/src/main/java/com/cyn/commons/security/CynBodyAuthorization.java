package com.cyn.commons.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** An annotation to enable body based authorization. */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CynBodyAuthorization {

  /**
   * The path to the customer unique id location within a request body. For example use
   * 'customerUniqueId' for { "field" : :"value", "customerUniqueId" : "value" }
   *
   * @return path to be used by CynBodyAuthorizer
   */
  String customerUniqueIdPath() default "";
}

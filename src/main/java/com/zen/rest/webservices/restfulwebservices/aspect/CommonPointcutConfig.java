package com.zen.rest.webservices.restfulwebservices.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {

    @Pointcut("execution(* com.zen.rest.webservices.restfulwebservices.controller.*.*(..))")
    public void controllerPackageConfig() {}

}

package com.lmdsoft.modules.common.annotation;

import java.lang.annotation.*;

/**
 * 类SysLog的功能描述:
 * 系统日志注解
 * @Auther lmdsoft
 * @Date 2018-08-25 16:16:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}

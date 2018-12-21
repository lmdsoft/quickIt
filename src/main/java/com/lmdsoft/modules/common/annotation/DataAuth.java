package com.lmdsoft.modules.common.annotation;

import java.lang.annotation.*;

/**
 * 类的功能描述.
 * 数据权限注解类
 * @Auther lmdsoft
 * @Date 2018/11/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuth {
    /**  表的别名 */
    String tableAlias() default  "";
}

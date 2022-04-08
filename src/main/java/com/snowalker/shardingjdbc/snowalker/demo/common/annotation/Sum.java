package com.snowalker.shardingjdbc.snowalker.demo.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sum {

    /**
     * 数据库字段，默认为空，自动根据驼峰转下划线
     *
     * @return
     */
    String[] field() default {""};

    String totalMark() default "";

}

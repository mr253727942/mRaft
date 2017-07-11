package com.mrraft.remoting.annotation;

/**
 * Created by T460P on 2016/12/29.
 */

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface CFNotNull {
}

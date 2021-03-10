package io.kaitoshy.web.mvc.header.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheControl {

    String[] value() default {};
}

package com.fengshen.core.validator;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { OrderValidator.class })
public @interface Order {
    String message() default "排序类型不支持";

    String[] accepts() default { "desc", "asc" };

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
package com.fengshen.core.validator;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { SortValidator.class })
public @interface Sort {
    String message() default "排序字段不支持";

    String[] accepts() default { "add_time", "id" };

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
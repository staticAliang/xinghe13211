package com.fengshen.core.config;

import org.hibernate.validator.*;
import javax.validation.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration
{
    @Bean
    public Validator validator() {
        final ValidatorFactory validatorFactory = ((HibernateValidatorConfiguration)((HibernateValidatorConfiguration)Validation.byProvider((Class)HibernateValidator.class).configure()).addProperty("hibernate.validator.fail_fast", "true")).buildValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        return validator;
    }
}

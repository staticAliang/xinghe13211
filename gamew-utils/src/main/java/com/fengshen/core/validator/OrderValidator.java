package com.fengshen.core.validator;

import java.util.*;
import javax.validation.*;
import java.lang.annotation.*;

public class OrderValidator implements ConstraintValidator<Order, String>
{
    private List<String> valueList;
    
    public void initialize(final Order order) {
        this.valueList = new ArrayList<String>();
        String[] accepts;
        for (int length = (accepts = order.accepts()).length, i = 0; i < length; ++i) {
            final String val = accepts[i];
            this.valueList.add(val.toUpperCase());
        }
    }
    
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return this.valueList.contains(s.toUpperCase());
    }
}

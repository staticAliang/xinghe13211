package com.fengshen.core.validator;

import java.util.*;
import javax.validation.*;
import java.lang.annotation.*;

public class SortValidator implements ConstraintValidator<Sort, String>
{
    private List<String> valueList;
    
    public void initialize(final Sort sort) {
        this.valueList = new ArrayList<String>();
        String[] accepts;
        for (int length = (accepts = sort.accepts()).length, i = 0; i < length; ++i) {
            final String val = accepts[i];
            this.valueList.add(val.toUpperCase());
        }
    }
    
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return this.valueList.contains(s.toUpperCase());
    }
}

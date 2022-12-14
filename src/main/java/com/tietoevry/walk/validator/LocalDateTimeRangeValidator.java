package com.tietoevry.walk.validator;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class LocalDateTimeRangeValidator implements ConstraintValidator<LocalDateTimeRange, Object> {

    @Override
    public void initialize(final LocalDateTimeRange constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    	final BeanWrapper wrapper = new BeanWrapperImpl(obj);
    	final LocalDateTime start = (LocalDateTime) wrapper.getPropertyValue("start");
    	final LocalDateTime finish = (LocalDateTime) wrapper.getPropertyValue("finish");
    	final boolean isInvalid = start == null || finish == null || finish.isBefore(start);
        if (isInvalid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate()
            ).addPropertyNode("finish")
                    .addConstraintViolation();
        }
        return !isInvalid;
    }
}

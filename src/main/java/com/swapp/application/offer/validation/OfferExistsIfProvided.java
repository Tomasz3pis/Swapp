package com.swapp.application.offer.validation;

import static com.swapp.application.config.MessageProvider.PROVIDED_PARENT_CATEGORY_DOES_NOT_EXIST;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OfferExistsValidator.class)
public @interface OfferExistsIfProvided {

    String message() default PROVIDED_PARENT_CATEGORY_DOES_NOT_EXIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
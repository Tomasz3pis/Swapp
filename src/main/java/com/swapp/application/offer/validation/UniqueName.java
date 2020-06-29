package com.swapp.application.offer.validation;

import static com.swapp.application.config.MessageProvider.EMPTY_CATEGORY_NAME;
import static com.swapp.application.config.MessageProvider.OFFER_WITH_PROVIDED_NAME_ALREADY_EXISTS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;

@NotBlank(message = EMPTY_CATEGORY_NAME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNameValidator.class)
public @interface UniqueName {

    String message() default OFFER_WITH_PROVIDED_NAME_ALREADY_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

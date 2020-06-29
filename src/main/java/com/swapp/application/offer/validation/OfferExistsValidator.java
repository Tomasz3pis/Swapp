package com.swapp.application.offer.validation;

import com.swapp.application.offer.OfferService;
import com.swapp.application.auth.UserProvider;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class OfferExistsValidator implements ConstraintValidator<OfferExistsIfProvided, Long> {

    private final OfferService offerService;
    private final UserProvider userProvider;

    @Override
    public void initialize(OfferExistsIfProvided constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long offerId, ConstraintValidatorContext context) {
        return offerId == null
                || offerService.offerExistByIdAndUserId(offerId, userProvider.getCurrentUserId());
    }
}

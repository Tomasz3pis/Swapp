package com.swapp.application.offer.validation;

import com.swapp.application.offer.OfferRepository;
import com.swapp.application.auth.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private final transient OfferRepository offerRepository;
    private final transient UserProvider userProvider;

    @Autowired
    public UniqueNameValidator(OfferRepository offerRepository, UserProvider userProvider) {
        this.offerRepository = offerRepository;
        this.userProvider = userProvider;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return offerRepository.findByTitleIgnoreCaseAndUserId(name, userProvider.getCurrentUserId()).size() == 0;
    }

    @Override
    public void initialize(UniqueName constraintAnnotation) {
    }
}

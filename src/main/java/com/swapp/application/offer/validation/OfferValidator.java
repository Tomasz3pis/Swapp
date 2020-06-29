package com.swapp.application.offer.validation;

import static com.swapp.application.config.MessageProvider.OFFER_WITH_PROVIDED_NAME_ALREADY_EXISTS;
import static com.swapp.application.config.MessageProvider.getMessage;

import com.swapp.application.offer.Offer;
import com.swapp.application.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferValidator {

    private final OfferService offerService;

    public List<String> validateOfferForUpdate(long id, long userId, Offer offer) {
        List<String> validationResults = new ArrayList<>();

        Optional<Offer> offerToUpdate = offerService.getOfferByIdAndUserId(id, userId);

        if (offerToUpdate.isEmpty()) {
            throw new IllegalStateException("Offer with id: " + id + " does not exist in database");
        }

        if (!offerToUpdate.get().getTitle().equals(offer.getTitle())) {
            checkForDuplicatedName(validationResults, offer, userId);
        }

        return validationResults;
    }

    private void checkForDuplicatedName(List<String> validationResults, Offer offer, long userId) {
        if (offerService.isOfferNameAlreadyUsed(offer.getTitle(), userId)) {
            validationResults.add(getMessage(OFFER_WITH_PROVIDED_NAME_ALREADY_EXISTS));
        }
    }
}

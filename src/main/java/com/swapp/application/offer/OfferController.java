package com.swapp.application.offer;

import com.swapp.application.offer.validation.OfferValidator;
import com.swapp.application.auth.UserProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
public class OfferController implements OfferApi {

    private final OfferService offerService;
    private final OfferValidator offerValidator;
    private final UserProvider userProvider;


    @Override
    public ResponseEntity<Offer> getOfferById(long offerId) {
        long userId = userProvider.getCurrentUserId();

        log.info("Retrieving offer with id: {}", offerId);
        Optional<Offer> offer = offerService.getOfferByIdAndUserId(offerId, userId);

        if (offer.isEmpty()) {
            log.info("OFFER with id {} was not found", offerId);
            return ResponseEntity.notFound().build();
        }

        log.info("OFFER with id {} was successfully retrieved", offerId);
        return ResponseEntity.ok(offer.get());
    }

    @Override
    public ResponseEntity<List<Offer>> getOffers() {
        long userId = userProvider.getCurrentUserId();

        log.info("Retrieving offers from database");
        List<Offer> offers = offerService.getOffers(userId);

        return ResponseEntity.ok(offers);
    }

    @Override
    public ResponseEntity<?> addOffer(@Valid @RequestBody Offer offer) {
        long userId = userProvider.getCurrentUserId();

        log.info("Saving offer {} to the database", offer.getTitle());

        offerService.addOffer(offer, userId);
        log.info("Saving offer to the database was successful. OFFER id is {}", offer.getId());

        return ResponseEntity.ok(offer.getId());
    }

    @Override
    public ResponseEntity<?> updateOffer(@PathVariable long offerId, @Valid @RequestBody Offer offer) {

        long userId = userProvider.getCurrentUserId();

        Optional<Offer> offerByIdAndUserId = offerService.getOfferByIdAndUserId(offerId, userId);
        if (offerByIdAndUserId.isEmpty()) {
            log.info("No offers with id {} was found, not able to update", offerId);
            return ResponseEntity.notFound().build();
        }

        offer.setId(offerId);

        List<String> validationResult = offerValidator.validateOfferForUpdate(offerId, userId, offer);
        if (!validationResult.isEmpty()) {
            log.info("Offer is not valid {}", validationResult);
            return ResponseEntity.badRequest().body(validationResult);
        }

        offerService.updateOffer(offerId, userId, offer);
        log.info("Offer with id {} was successfully updated", offerId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteOffer(@PathVariable long offerId) {
        long userId = userProvider.getCurrentUserId();

        if (offerService.getOfferByIdAndUserId(offerId, userId).isEmpty()) {
            log.info("No offer with id {} was found, not able to delete", offerId);
            return ResponseEntity.notFound().build();
        }

        log.info("Attempting to delete offer with id {}", offerId);

        offerService.deleteOffer(offerId);

        log.info("Offer with id {} was deleted successfully", offerId);

        return ResponseEntity.ok().build();
    }
}

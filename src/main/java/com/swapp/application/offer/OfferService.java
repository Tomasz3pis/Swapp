package com.swapp.application.offer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OfferService {

    private OfferRepository offerRepository;

    public Optional<Offer> getOfferByIdAndUserId(long id, long userId) {
        return offerRepository.findByIdAndUserId(id, userId);
    }

    public Offer getOfferFromDbByIdAndUserId(long id, long userId) {
        Optional<Offer> offerByIdAndUserId = getOfferByIdAndUserId(id, userId);
        if (offerByIdAndUserId.isEmpty()) {
            throw new IllegalStateException("OFFER with id : " + id + " does not exist in database");
        }

        return offerByIdAndUserId.get();
    }

    public List<Offer> getOffers(long userId) {
        return offerRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Offer::getId))
                .collect(Collectors.toList());
    }

    public Offer addOffer(Offer offer, long userId) {
        offer.setUserId(userId);
        return offerRepository.save(offer);
    }

    public void deleteOffer(long id) {
        offerRepository.deleteById(id);
    }

    public void updateOffer(long id, long userId, Offer offer) {
        Optional<Offer> receivedOffer = getOfferByIdAndUserId(id, userId);
        if (receivedOffer.isEmpty()) {
            throw new IllegalStateException("OFFER with id : " + id + " does not exist in database");
        }

        Offer offerToUpdate = receivedOffer.get();
        offerToUpdate.setTitle(offer.getTitle());
        offerToUpdate.setDescription(offer.getDescription());
        offerToUpdate.setStartDate(offer.getStartDate());
        offerToUpdate.setExchangeProduct(offer.getExchangeProduct());

        offerRepository.save(offerToUpdate);
    }

    public boolean idExist(long id) {
        return offerRepository.existsById(id);
    }

    public boolean isOfferNameAlreadyUsed(String name, long userId) {
        return offerRepository.findByTitleIgnoreCaseAndUserId(name, userId).size() != 0;
    }

    public boolean offerExistByIdAndUserId(long categoryId, long userId) {
        return offerRepository.existsByIdAndUserId(categoryId, userId);
    }
}
